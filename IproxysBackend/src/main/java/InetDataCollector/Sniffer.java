/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InetDataCollector;

import JsonParser.CustomGson;
import PersistenceData.*;
import api.DetailedStatusController;
import api.LiveActionsWebSocketController;
import app.ResponseManager;
import dns.DnsHelper;
import externalDependencies.GeneralConfiguration;
import jess.JessSuggestions;
import jess.ServiceCore;
import jpcap.NetworkInterface;
import jpcap.NetworkInterfaceAddress;
import models.DetailedStatus;
import models.LiveAction;
import performblock.PerformHttpBlock;
import performblock.PerformIPPortBlock;
import performblock.PerformIpBlock;
import performblock.PerformPortBlock;
import persistence.dao.TemporaryBlockedEntityDao;
import persistence.dao.UnblockableEntityDao;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;

/**
 * @author root
 */
public class Sniffer extends Thread {

    private static NetworkInterface[] InetInterfaces = jpcap.JpcapCaptor.getDeviceList();
    private static int SELECTED_NETWORK_INTERFACE;
    private static String DEFAULT_IP_ADDRESS = "192.168.1.1";
    private static jpcap.JpcapCaptor capture;
    public static String InterfaceIP = "";
    public static String interfaceBroadcastIP = "";
    public static Date TimeTempRef = new Date();
    public static Date TimepRef = new Date();
    private static Thread loop = null;
    public static String interfaceMask = "";
    public static List<SummaryIPPort_BandWidth> IPPortPDUs = new ArrayList<>();
    public static List<SummaryIPPort_BandWidth> TempIPPortPDUs = new ArrayList<>();
    public static List<SummaryPort_BandWidth> PortPDUs = new ArrayList<>();
    public static List<SummaryPort_BandWidth> TempPortPDUs = new ArrayList<>();
    public static List<SummaryIP_BandWidth> IPPDUs = new ArrayList<>();
    public static List<SummaryIP_BandWidth> TempIPPDUs = new ArrayList<>();
    public static final int TimeTemp = 10000;
    private static Sniffer sniffer = null;
    public static double bandwidthMonitor = 0;
    private static Timer networkMonTimer = null;


    public static NetworkInterface[] getAllInterfaces() {
        return InetInterfaces;
    }


    private void startSniff(int SelectInter) {
        Sniffer.SELECTED_NETWORK_INTERFACE = SelectInter;
        try {
            capture = jpcap.JpcapCaptor.openDevice(InetInterfaces[Sniffer.SELECTED_NETWORK_INTERFACE], 1024 * 1024, false, 10000);
            setFilter();
            loop = new Thread(this);
            loop.start();
            networkMonTimer = new Timer();
            networkMonTimer.scheduleAtFixedRate(new BandwidthMonitor(), 1000, 1000);

        } catch (Exception ex) {

            ex.printStackTrace();
        }

    }

    private String getNetwork(String ip_re, String mask) {

        InetAddress ip;
        InetAddress mascara;
        try {
            ip = InetAddress.getByName(ip_re);
            mascara = InetAddress.getByName(mask);
        } catch (UnknownHostException ex) {

            return Sniffer.DEFAULT_IP_ADDRESS;
        }

        byte[] byteip = ip.getAddress();
        byte[] bytemask = mascara.getAddress();
        byte[] network = new byte[byteip.length];
        for (int i = 0; i < byteip.length; i++) {
            network[i] = (byte) (byteip[i] & bytemask[i]);
        }
        InetAddress realnetwork;
        try {
            realnetwork = InetAddress.getByAddress(network);
        } catch (UnknownHostException ex) {
            return Sniffer.DEFAULT_IP_ADDRESS;
        }
        return (realnetwork.getHostAddress());
    }

    @Override
    public void run() {


        while (true) {
            //Capturar paquetes hasta que pasen TimeTemp Milisegundos
            capture.processPacket(-1, new RecievePackets());
            if ((new Date().getTime() - TimeTempRef.getTime()) >= TimeTemp) {
                jess.ServiceCore jess = ServiceCore.getInstance();
                doCalculateDB_Temp();
                calculateMaxBWAllowance();

                if (!TempIPPDUs.isEmpty() && !TempIPPortPDUs.isEmpty() && !TempPortPDUs.isEmpty()) {
                    jess.addList(TempIPPDUs.toArray());
                    jess.addList(TempIPPortPDUs.toArray());
                    jess.addList(TempPortPDUs.toArray());
                }

                List<UnblockableEntity> unblockableEntities = UnblockableEntityDao.findByAll();

                for (JessSuggestions sug : jess.GetAllSuggestions()) {
                    TemporaryBlockedEntity temporaryBlockedEntity = new TemporaryBlockedEntity();
                    System.out.println(sug.getAction() + "  tipo:" + sug.getTipo() + "  ipdst:" + sug.getIp_Dst() + "  ipsrc:" + sug.getIp_Src() + "  port:" + sug.getPort() + " protocol:" + sug.getProtocol());

                    if (!isThisEntityUnblockeable(unblockableEntities, sug)) {
                        switch (sug.getTipo()) {
                            case TemporaryBlockedEntity.BLOCK_IP:
                                temporaryBlockedEntity.setBlockedIP(sug.getIp_Dst());
                                temporaryBlockedEntity.setBlockedOnTimeDate(new Date());
                                PerformIpBlock performIpBlock = new PerformIpBlock(temporaryBlockedEntity);
//                                performIpBlock.block();
                                break;
                            case TemporaryBlockedEntity.BLOCK_IP_AND_PORT:
                                temporaryBlockedEntity.setBlockedIP(sug.getIp_Dst());
                                temporaryBlockedEntity.setBlockedPort(sug.getPort() == 443 ? 3129 : sug.getPort());
                                temporaryBlockedEntity.setBlockedOnTimeDate(new Date());

                                temporaryBlockedEntity.setProtocol(sug.getProtocol());
                                PerformIPPortBlock performIPPortBlock = new PerformIPPortBlock(temporaryBlockedEntity);

                                performIPPortBlock.block();
                                break;
                            case TemporaryBlockedEntity.BLOCK_PORT:
                                temporaryBlockedEntity.setBlockedPort(sug.getPort());
                                temporaryBlockedEntity.setProtocol(sug.getProtocol());
                                temporaryBlockedEntity.setBlockedOnTimeDate(new Date());

                                PerformPortBlock performPortBlock = new PerformPortBlock(temporaryBlockedEntity);
//                                performPortBlock.block();
                                break;
                            case TemporaryBlockedEntity.BLOCK_HTTP_DOMAIN_TO_IP:
                                temporaryBlockedEntity.setBlockedIP(sug.getIp_Dst());
                                // ARREGLAR DOMINIO
                                temporaryBlockedEntity.setBlockedOnTimeDate(new Date());
                                temporaryBlockedEntity.setBlockedDomain(DnsHelper.getDomainNameFromIp(sug.getIp_Src()));
                                temporaryBlockedEntity.setBlockedIPDest(sug.getIp_Src());
                                temporaryBlockedEntity.setBlockedIP(sug.getIp_Dst());
                                temporaryBlockedEntity.setBlockedPort(sug.getPort());
                                temporaryBlockedEntity.setBlockedOnTimeDate(new Date());

                                temporaryBlockedEntity.setProtocol(sug.getProtocol());
                                PerformHttpBlock performHttpBlock = new PerformHttpBlock(temporaryBlockedEntity);
                                performHttpBlock.block();
                                break;
                        }
                        temporaryBlockedEntity.setIdentifier(sug.getTipo());
                        temporaryBlockedEntity.save();
                    } else {
                        if (sug.getTipo() == TemporaryBlockedEntity.BLOCK_HTTP_DOMAIN_TO_IP) {
                            sug.setIp_Src(DnsHelper.getDomainNameFromIp(sug.getIp_Src()));
                        }
                        System.out.println("Entidad no puede ser bloqueada :" + CustomGson.Gson().toJson(sug));

                    }
                }


                List<DetailedStatus> detailedStatuses = new ArrayList<>();

                for (SummaryIP_BandWidth temp : Sniffer.TempIPPDUs) {
                    DetailedStatus detailedStatus = new DetailedStatus();
                    detailedStatus.setBdusage(temp.getBdusage());
                    detailedStatus.setIndetifier(1);
                    detailedStatus.setIp(temp.getIp_Dst());
                    detailedStatuses.add(detailedStatus);
                }
                for (SummaryIPPort_BandWidth temp : Sniffer.TempIPPortPDUs) {
                    DetailedStatus detailedStatus = new DetailedStatus();
                    detailedStatus.setProtocol(temp.getProtocol());
                    detailedStatus.setPort(temp.getPort());
                    detailedStatus.setBdusage(temp.getBdusage());
                    detailedStatus.setIndetifier(2);
                    detailedStatus.setIp(temp.getIp_Dst());
                    detailedStatus.setIpDest(temp.getIp_Src());
                    detailedStatuses.add(detailedStatus);
                }

                for (SummaryPort_BandWidth temp : Sniffer.TempPortPDUs) {
                    DetailedStatus detailedStatus = new DetailedStatus();
                    detailedStatus.setProtocol(temp.getProtocol());
                    detailedStatus.setPort(temp.getPort());
                    detailedStatus.setBdusage(temp.getBdusage());
                    detailedStatus.setIndetifier(0);
                    detailedStatuses.add(detailedStatus);
                }

                DetailedStatusController.sessions.stream().forEach(session -> {
                    try {
                        session.getRemote().sendString(ResponseManager.toJson(detailedStatuses));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });


                jess.eraseData();
                Sniffer.TempPortPDUs = new ArrayList<>();
                Sniffer.TempIPPDUs = new ArrayList<>();
                Sniffer.TempIPPortPDUs = new ArrayList<>();

                /**
                 * THIS NEEDS TO BE HANDLED IN ANOTHER FILE AND JUST MAKE A CALL TO UPDATE THE WEBSOCKET WITH
                 * THE NEW BLOCKED GUYS
                 */
                List<LiveAction> liveActions = new ArrayList<>();
                for (TemporaryBlockedEntity temp : TemporaryBlockedEntityDao.findTemporaryBlockedEntities()) {
                    LiveAction liveAction = new LiveAction();
                    liveAction.setBlockedDomain(temp.getBlockedDomain());
                    liveAction.setBlockedIP(temp.getBlockedIP());
                    liveAction.setBlockedOnTimeDate(temp.getBlockedOnTimeDate());
                    liveAction.setBlockedPort(temp.getBlockedPort());
                    liveAction.setIdentifier(temp.getIdentifier() - 1);
                    liveAction.setProtocol(temp.getProtocol());
                    liveActions.add(liveAction);
                }
                LiveActionsWebSocketController.sessions.stream().forEach(session -> {
                    try {

                        session.getRemote().sendString(ResponseManager.toJson(liveActions));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                Sniffer.TimeTempRef = new Date();


            }

        }
    }

    private void calculateMaxBWAllowance() {

        int userAmount = TempIPPDUs.size();
        for (SummaryIP_BandWidth summaryIP_bandWidth : TempIPPDUs) {
            if (summaryIP_bandWidth.getIp_Dst().equals(Sniffer.interfaceBroadcastIP) || summaryIP_bandWidth.getIp_Dst().equals(Sniffer.InterfaceIP)) {
                userAmount -= 1;
            }
        }
        if (userAmount <= 0) {
            userAmount = 0;
        }

        for (SummaryIP_BandWidth summaryIP_bandWidth : TempIPPDUs) {
            summaryIP_bandWidth.userAmount = userAmount;
            summaryIP_bandWidth.availableBandwidth = GeneralConfiguration.getAvailableBandwidth();
            summaryIP_bandWidth.maxBandwidthPerUser = GeneralConfiguration.getMaxBandWidthPerUser();
            summaryIP_bandWidth.calculateMaxBWAllowance();

        }
        for (SummaryIPPort_BandWidth summaryIPPort_BandWidth : TempIPPortPDUs) {
            summaryIPPort_BandWidth.userAmount = userAmount;
            summaryIPPort_BandWidth.availableBandwidth = GeneralConfiguration.getAvailableBandwidth();
            summaryIPPort_BandWidth.maxBandwidthPerUser = GeneralConfiguration.getMaxBandWidthPerUser();
            summaryIPPort_BandWidth.calculateMaxBWAllowance();
        }
        for (SummaryPort_BandWidth summaryPort_BandWidth : TempPortPDUs) {
            summaryPort_BandWidth.userAmount = userAmount;
            summaryPort_BandWidth.availableBandwidth = GeneralConfiguration.getAvailableBandwidth();
            summaryPort_BandWidth.maxBandwidthPerUser = GeneralConfiguration.getMaxBandWidthPerUser();
            summaryPort_BandWidth.calculateMaxBWAllowance();

        }
    }

    private boolean isThisEntityUnblockeable(List<UnblockableEntity> unblockableEntities, JessSuggestions jessSuggestions) {
        boolean isItUnblockable = false;
        for (UnblockableEntity unblockableEntity : unblockableEntities) {

            if (unblockableEntity.getIdentifier() == jessSuggestions.getTipo()) {

                switch (unblockableEntity.getIdentifier()) {
                    case TemporaryBlockedEntity.BLOCK_IP:
                        isItUnblockable = unblockableEntity.getBlockedIP().equals(jessSuggestions.getIp_Src());
                        break;
                    case TemporaryBlockedEntity.BLOCK_IP_AND_PORT:
                        isItUnblockable = unblockableEntity.getBlockedIP().equals(jessSuggestions.getIp_Dst()) && unblockableEntity.getBlockedPort() == jessSuggestions.getPort() && unblockableEntity.getProtocol() == jessSuggestions.getProtocol();
                        break;
                    case TemporaryBlockedEntity.BLOCK_PORT:
                        isItUnblockable = unblockableEntity.getBlockedPort() == jessSuggestions.getPort() && unblockableEntity.getProtocol() == jessSuggestions.getProtocol();
                        break;
                    case TemporaryBlockedEntity.BLOCK_HTTP_DOMAIN_TO_IP:
//                        System.out.println( DnsHelper.getDomainNameFromIp(jessSuggestions.getIp_Src()));
                        isItUnblockable = unblockableEntity.getBlockedIP().equals(jessSuggestions.getIp_Dst()) && unblockableEntity.getBlockedDomain().equals(DnsHelper.getDomainNameFromIp(jessSuggestions.getIp_Src()));
                        break;
                }
            }
        }
//        System.out.println(isItUnblockable);
        return isItUnblockable;
    }

    private void setFilter() throws IOException {
        NetworkInterfaceAddress IPaddr = getFirstIPv4Address(InetInterfaces[Sniffer.SELECTED_NETWORK_INTERFACE].addresses);
        Sniffer.InterfaceIP = IPaddr.address.getHostAddress();
        Sniffer.interfaceMask = IPaddr.subnet.getHostAddress();
        Sniffer.interfaceBroadcastIP = IPaddr.broadcast.getHostAddress();
        capture.setFilter("dst net " + getNetwork(InterfaceIP, interfaceMask) + " mask " + IPaddr.subnet.getHostAddress() + "", true);
        System.err.println("dst net " + getNetwork(InterfaceIP, interfaceMask) + " mask " + IPaddr.subnet.getHostAddress() + "");

    }

    private NetworkInterfaceAddress getFirstIPv4Address(NetworkInterfaceAddress[] IPAddrs) {
        System.out.println(IPAddrs.length);
        for (int index = 0; index < IPAddrs.length; index++) {
            System.out.println(IPAddrs[index].address.getAddress().toString());
            if (IPAddrs[index].address instanceof Inet4Address) {
                return IPAddrs[index];
            }
        }
        return null;
    }

    public void select() {
        //windows
//        startSniff(2);
//        System.err.println("ESCUCHANDO POR AL INTERFAZ " + InetInterfaces[2].name);
//        linux
        startSniff(0);
        System.err.println("ESCUCHANDO POR AL INTERFAZ " + InetInterfaces[0].name);
    }

    private Sniffer() {
    }

    public static Sniffer getInstance() {
        if (sniffer == null) {
            sniffer = new Sniffer();
        }
        return sniffer;
    }

    private void doCalculateDB() {
        BDCalculator_IP(IPPDUs);
        BDCalculator_IPPort(IPPortPDUs);
        BDCalculator_Port(PortPDUs);
    }

    private void doCalculateDB_Temp() {
        BDCalculator_IP(TempIPPDUs);
        BDCalculator_IPPort(TempIPPortPDUs);
        BDCalculator_Port(TempPortPDUs);
    }

    private void BDCalculator_IP(List<SummaryIP_BandWidth> relativo) {
        for (SummaryIP_BandWidth sug : relativo) {
            // Convirtiendo de Bytes a KiloBytes
//            sug.setBdusage((sug.getBdusage() * 8) / GeneralConfiguration.Kilobit);
            // Dividiendo entre la cantidad de segundos que duro el muestreo
            sug.setBdusage(sug.getBdusage() / (TimeTemp / 1000));
            // Obteniendo el porcentaje de utilizacion de ancho 
            sug.setBdusage((sug.getBdusage() / GeneralConfiguration.getAvailableBandwidth()) * 100);
        }
    }

    private void BDCalculator_Port(List<SummaryPort_BandWidth> relativo) {
        for (SummaryPort_BandWidth sug : relativo) {
            // Convirtiendo de B a KiloBytes
//            sug.setBdusage((sug.getBdusage() * 8) / GeneralConfiguration.Kilobit);
            // Dividiendo entre la cantidad de segundos que duro el muestreo
            sug.setBdusage(sug.getBdusage() / (TimeTemp / 1000));
            // Obteniendo el porcentaje de utilizacion de ancho 
            sug.setBdusage((sug.getBdusage() / GeneralConfiguration.getAvailableBandwidth()) * 100);
        }
    }

    private void BDCalculator_IPPort(List<SummaryIPPort_BandWidth> relativo) {
        for (SummaryIPPort_BandWidth sug : relativo) {
            // Convirtiendo de Bytes a KiloBytes
//            sug.setBdusage((sug.getBdusage() * 8) / GeneralConfiguration.Kilobit);
            // Dividiendo entre la cantidad de segundos que duro el muestreo
            sug.setBdusage(sug.getBdusage() / (TimeTemp / 1000));
            // Obteniendo el porcentaje de utilizacion de ancho 
            sug.setBdusage((sug.getBdusage() / GeneralConfiguration.getAvailableBandwidth()) * 100);
        }
    }
}
