/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InetDataCollector;

import PersistenceData.*;
import dns.DnsHelper;
import externalDependencies.GeneralConfiguration;
import jess.JessSuggestions;
import jess.ServiceCore;
import jpcap.NetworkInterface;
import jpcap.NetworkInterfaceAddress;
import performblock.PerformHttpBlock;
import performblock.PerformIPPortBlock;
import performblock.PerformIpBlock;
import performblock.PerformPortBlock;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

/**
 * @author root
 */
public class Sniffer extends Thread {

    private static NetworkInterface[] InetInterfaces = jpcap.JpcapCaptor.getDeviceList();
    private static int SELECTED_NETWORK_INTERFACE;
    private static String DEFAULT_IP_ADDRESS = "192.168.1.1";
    private static jpcap.JpcapCaptor capture;
    public static String InterfaceIP = "";
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
    private final int TimeTemp = 10000;
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

            System.err.println(ex);
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
                Sniffer.TimeTempRef = new Date();
                jess.ServiceCore jess = ServiceCore.getInstance();
                doCalculateDB_Temp();
                jess.addList(TempIPPDUs.toArray());
                jess.addList(TempIPPortPDUs.toArray());
                jess.addList(TempPortPDUs.toArray());
                UnblockableEntity unblockableEntityProvider = new UnblockableEntity();
                List<Object> unblockableEntities = new LinkedList<>();//unblockableEntityProvider.findAll();


                for (JessSuggestions sug : jess.GetAllSuggestions()) {
                    TemporaryBlockedEntity temporaryBlockedEntity = new TemporaryBlockedEntity();
                    System.out.println(sug.getAction() + "  tipo:" + sug.getTipo() + "  ipdst:" + sug.getIp_Dst() + "  ipsrc:" + sug.getIp_Src());
                    if (!isThisEntityUnblockeable(unblockableEntities, temporaryBlockedEntity)) {
                        switch (sug.getTipo()) {
                            case TemporaryBlockedEntity.BLOCK_IP:
                                temporaryBlockedEntity.setBlockedIP(sug.getIp_Dst());
                                temporaryBlockedEntity.setBlockedOnTimeDate(new Date());
                                PerformIpBlock performIpBlock = new PerformIpBlock(temporaryBlockedEntity);
//                                performIpBlock.block();
                                break;
                            case TemporaryBlockedEntity.BLOCK_IP_AND_PORT:
                                temporaryBlockedEntity.setBlockedIP(sug.getIp_Dst());
                                temporaryBlockedEntity.setBlockedPort(sug.getPort());
                                temporaryBlockedEntity.setBlockedOnTimeDate(new Date());

                                temporaryBlockedEntity.setProtocol(sug.getProtocol());
                                PerformIPPortBlock performIPPortBlock = new PerformIPPortBlock(temporaryBlockedEntity);
//                                performIPPortBlock.block();
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
                                PerformHttpBlock performHttpBlock = new PerformHttpBlock(temporaryBlockedEntity);
//                                performHttpBlock.block();
                                break;
                        }
                        temporaryBlockedEntity.setIdentifier(sug.getTipo());
//                        temporaryBlockedEntity.save();
                    } else {
                        System.out.println("Entidad no puede ser bloqueada ip:" + temporaryBlockedEntity.getBlockedIP() + " port:" + temporaryBlockedEntity.getBlockedPort());
                    }
                }
                jess.eraseData();
                Sniffer.TempPortPDUs.clear();
                Sniffer.TempIPPDUs.clear();
                Sniffer.TempIPPortPDUs.clear();

            }
        }
    }

    private boolean isThisEntityUnblockeable(List<Object> unblockableEntities, TemporaryBlockedEntity temporaryBlockedEntity) {
        boolean isItUnblockable = false;
        for (Object unblockeableObject : unblockableEntities) {

            UnblockableEntity unblockableEntity = (UnblockableEntity) unblockeableObject;
            if (unblockableEntity.getIdentifier() == temporaryBlockedEntity.getIdentifier()) {

                switch (unblockableEntity.getIdentifier()) {
                    case TemporaryBlockedEntity.BLOCK_IP:
                        isItUnblockable = unblockableEntity.getBlockedIP().equals(temporaryBlockedEntity.getBlockedIP());
                        break;
                    case TemporaryBlockedEntity.BLOCK_IP_AND_PORT:
                        isItUnblockable = unblockableEntity.getBlockedIP().equals(temporaryBlockedEntity.getBlockedIP()) && temporaryBlockedEntity.getBlockedPort() == temporaryBlockedEntity.getBlockedPort() && temporaryBlockedEntity.getProtocol() == temporaryBlockedEntity.getProtocol();
                        break;
                    case TemporaryBlockedEntity.BLOCK_PORT:
                        isItUnblockable = unblockableEntity.getBlockedPort() == temporaryBlockedEntity.getBlockedPort() && temporaryBlockedEntity.getProtocol() == temporaryBlockedEntity.getProtocol();
                        break;
                }
            }
            if (isItUnblockable) {
                return isItUnblockable;
            }
        }
        return isItUnblockable;
    }

    private void setFilter() throws IOException {
        NetworkInterfaceAddress IPaddr = getFirstIPv4Address(InetInterfaces[Sniffer.SELECTED_NETWORK_INTERFACE].addresses);
        Sniffer.InterfaceIP = IPaddr.address.getHostAddress();
        Sniffer.interfaceMask = IPaddr.subnet.getHostAddress();

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
        startSniff(2);
        System.err.println("ESCUCHANDO POR AL INTERFAZ " + InetInterfaces[2].name);
        //linux
//        startSniff(0);
//        System.err.println("ESCUCHANDO POR AL INTERFAZ " + InetInterfaces[0].name);
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
            sug.setBdusage((sug.getBdusage()  / GeneralConfiguration.getAvailableBandwidth()) * 100);
        }
    }

    private void BDCalculator_Port(List<SummaryPort_BandWidth> relativo) {
        for (SummaryPort_BandWidth sug : relativo) {
            // Convirtiendo de B a KiloBytes
//            sug.setBdusage((sug.getBdusage() * 8) / GeneralConfiguration.Kilobit);
            // Dividiendo entre la cantidad de segundos que duro el muestreo
            sug.setBdusage(sug.getBdusage()  / (TimeTemp / 1000));
            // Obteniendo el porcentaje de utilizacion de ancho 
            sug.setBdusage((sug.getBdusage()  / GeneralConfiguration.getAvailableBandwidth()) * 100);
        }
    }

    private void BDCalculator_IPPort(List<SummaryIPPort_BandWidth> relativo) {
        for (SummaryIPPort_BandWidth sug : relativo) {
            // Convirtiendo de Bytes a KiloBytes
//            sug.setBdusage((sug.getBdusage() * 8) / GeneralConfiguration.Kilobit);
            // Dividiendo entre la cantidad de segundos que duro el muestreo
            sug.setBdusage(sug.getBdusage()  / (TimeTemp / 1000));
            // Obteniendo el porcentaje de utilizacion de ancho 
            sug.setBdusage((sug.getBdusage() / GeneralConfiguration.getAvailableBandwidth()) * 100);
        }
    }
}
