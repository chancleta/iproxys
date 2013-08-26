/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iproxys.InetDataCollector;

import iproxy.externalDependencies.ConfiguracionGeneral;
import iproxy.externalDependencies.EjecutarIPtable;
import iproxy.externalDependencies.SquidController;
import iproxys.PersistenceData.*;
import iproxys.dataFacade.HttpBlockDbFacade;
import iproxys.dataFacade.UnBlockableIPFacade;
import iproxys.dns.DnsLookupper;
import iproxys.jess.JessSuggestions;
import iproxys.jess.ServiceCore;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import javax.ejb.EJB;
import jpcap.NetworkInterface;
import jpcap.NetworkInterfaceAddress;

/**
 *
 * @author root
 */
public class Sniffer extends Thread {

    private static NetworkInterface[] InetInterfaces = jpcap.JpcapCaptor.getDeviceList();
    private static jpcap.JpcapCaptor capture;
    public static String InterfaceIP = "";
    public static Date TimeTempRef = new Date();
    public static Date TimepRef = new Date();
    private static Thread loop = null;
    public static String interfaceMask = "";
    public static List<SummaryIPPort_BandWidth> IPPortPDUs = new ArrayList<SummaryIPPort_BandWidth>();
    public static List<SummaryIPPort_BandWidth> TempIPPortPDUs = new ArrayList<SummaryIPPort_BandWidth>();
    public static List<SummaryPort_BandWidth> PortPDUs = new ArrayList<SummaryPort_BandWidth>();
    public static List<SummaryPort_BandWidth> TempPortPDUs = new ArrayList<SummaryPort_BandWidth>();
    public static List<SummaryIP_BandWidth> IPPDUs = new ArrayList<SummaryIP_BandWidth>();
    public static List<SummaryIP_BandWidth> TempIPPDUs = new ArrayList<SummaryIP_BandWidth>();
    private final int TimeTemp = 10000;
    private static Sniffer sniffer = null;
    private static ConfiguracionGeneral confgral;
    private static double time = 0.001;
    public static double networkMonitor = 0;
    public static double networkMonitorLastSeg = 0;
    private static Timer networkMonTimer = null;
    private EjecutarIPtable ipTableIns = EjecutarIPtable.getInstance();
    private DnsLookupper dnsLookIns = DnsLookupper.getInstance();
    private SquidController squidControllerInst = SquidController.getInstance();
    private HttpBlockDbFacade httpBlockIns = HttpBlockDbFacade.getInstance();
    private boolean control = true;
    private String domain;
    @EJB
    UnBlockableIPFacade unblockables = UnBlockableIPFacade.getInstance();
    private static TimerTask netMonTask = new TimerTask() {

        @Override
        public void run() {
            networkMonitorLastSeg = networkMonitor;
            Sniffer.networkMonitor = 0;
        }
    };

    public static NetworkInterface[] getAllInterfaces() {
        return InetInterfaces;
    }

    private void startSniff() {

        try {
            capture = jpcap.JpcapCaptor.openDevice(InetInterfaces[1], 1024 * 1024, false, 10000);
            setFilter();
            loop = new Thread(this);
            loop.start();
            networkMonTimer = new Timer();
            networkMonTimer.scheduleAtFixedRate(netMonTask, 1000, 1000);
        } catch (Exception ex) {
            System.err.println(ex);

        }

    }

    private void startSniff(int SelectInter) {
        try {
            capture = jpcap.JpcapCaptor.openDevice(InetInterfaces[SelectInter], 1024 * 1024, false, 10000);
            setFilter();
            loop = new Thread(this);
            loop.start();
            networkMonTimer = new Timer();
            networkMonTimer.scheduleAtFixedRate(netMonTask, 1000, 1000);

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
            return "192.168.0.0";
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
            return "192.168.0.0";
        }
        return (realnetwork.getHostAddress());
    }

    @Override
    public void run() {

        confgral = ConfiguracionGeneral.getInstance();
        confgral.setAnchoBanda(ConfiguracionGeneral.Megabit * 0.82);

        while (true) {

            capture.processPacket(-1, new RecievePackets());

            if ((new Date().getTime() - TimeTempRef.getTime()) >= TimeTemp) {

                Sniffer.TimeTempRef = new Date();
                iproxys.jess.ServiceCore jess = ServiceCore.getInstance();
                List<UnBlockableIP> fin = unblockables.findAll();


                doCalculateDB_Temp();
                jess.addList(TempIPPDUs.toArray());
                jess.addList(TempIPPortPDUs.toArray());
                jess.addList(TempPortPDUs.toArray());

                List<UnBlockableIP> listaNoblockear = unblockables.findAll();
                control = true;
                for (JessSuggestions sug : jess.GetAllSuggestions()) {
                    if (sug.getTipo() == 1) {
                        //IP SOLO
                                ipTableIns.controlIP(sug.getAction(), sug.getIp_Dst(), sug.getTimeref());
                                System.err.println(sug.getAction() + " IP:" + sug.getIp_Dst() + " time: " + sug.getTimeref());
                    } else if (sug.getTipo() == 2) {
                        //IP PUERTO QUE NO SEAN EL 80
                                ipTableIns.controlIPPort(sug.getAction(), sug.getIp_Dst(), sug.getPort(), sug.getProtocol(), sug.getTimeref());
                                System.err.println(sug.getAction() + " IP:" + sug.getIp_Dst() + " Port:" + sug.getPort() + " time: " + sug.getTimeref());
                    } else if (sug.getTipo() == 3) {
                        //PUERTO
                        ipTableIns.controlPuertos(EjecutarIPtable.block, sug.getPort(), sug.getProtocol(), sug.getTimeref());
                        System.err.println(sug.getAction() + " Port:" + sug.getPort() + " Proto:" + sug.getProtocol() + " time: " + sug.getTimeref());

                    } else if (sug.getTipo() == 4) {
                        //PUERTO IP SOLO PARA 80

                        List<HttpBlockDb> findAll = httpBlockIns.findAll();
                        
                        for (HttpBlockDb httpBlock : findAll) {
                            String doDns = dnsLookIns.doDns(sug.getIp_Src());
                            if (doDns != null && httpBlock.getDomain().equals(doDns)) {
                                //REFRESH SQUID
                                domain = doDns;
                                if (!httpBlock.getIp().equals(sug.getIp_Dst())) {
                                    squidControllerInst.addtoExistingDomain(sug.getIp_Dst(), httpBlock.getDomain());

                                }
                                control = false;
                            }
                        }
                        HttpBlockDb blockDb = new HttpBlockDb();
                        blockDb.setIp(sug.getIp_Dst());
                        blockDb.setTimeref(sug.getTimeref());

                        if (control) {
                            String Dns = dnsLookIns.doDns(sug.getIp_Src());
                            if (Dns != null) {
                                blockDb.setDomain(Dns);

                            } else {
                                blockDb.setDomain(sug.getIp_Src());
                                Dns = sug.getIp_Src();
                            }
                            
                            squidControllerInst.addNuevoDomain(sug.getIp_Dst(), Dns);
                        } else {
                            blockDb.setDomain(domain);
                        }
                        httpBlockIns.create(blockDb);
                        System.err.println("from www: " + sug.getAction() + " IP:" + sug.getIp_Dst() + " Port:" + sug.getPort() + " time: " + sug.getTimeref());
                        control = true;

                    }

                }
                //HACER LO DE SQUID.
                //LO DE PERMITIR DE NUEVO LOS TIGUERES BLOKEADOS
                System.err.println("____________________________________________");
                jess.eraseData();
                Sniffer.TempPortPDUs.clear();
                Sniffer.TempIPPDUs.clear();
                Sniffer.TempIPPortPDUs.clear();


                if ((new Date().getTime() - TimepRef.getTime()) >= (TimeTemp * 6)) {

                    doCalculateDB();

                }
            }
        }
    }

    private void setFilter() throws IOException {

        NetworkInterfaceAddress[] IPaddr = InetInterfaces[1].addresses;
        Sniffer.InterfaceIP = IPaddr[0].address.getHostAddress();
        Sniffer.interfaceMask = IPaddr[0].subnet.getHostAddress();

        capture.setFilter("dst net " + getNetwork(InterfaceIP, interfaceMask) + " mask " + IPaddr[0].subnet.getHostAddress() + "", true);

        System.err.println("dst net " + getNetwork(InterfaceIP, interfaceMask) + " mask " + IPaddr[0].subnet.getHostAddress() + "");

    }

    public void select() {

        startSniff(1);
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
            sug.setBdusage(sug.getBdusage() / 10);
            sug.setBdusage(sug.getBdusage() / 1024);
            sug.setBdusage((sug.getBdusage() / confgral.getAnchoBanda()) * 100);

        }

    }

    private void BDCalculator_Port(List<SummaryPort_BandWidth> relativo) {

        for (SummaryPort_BandWidth sug : relativo) {
            sug.setBdusage(sug.getBdusage() / 1024);
            sug.setBdusage(sug.getBdusage() / 10);
            sug.setBdusage((sug.getBdusage() / confgral.getAnchoBanda()) * 100);
        }

    }

    private void BDCalculator_IPPort(List<SummaryIPPort_BandWidth> relativo) {


        for (SummaryIPPort_BandWidth sug : relativo) {
            sug.setBdusage(sug.getBdusage() / 1024);
            sug.setBdusage(sug.getBdusage() / 10);
            sug.setBdusage((sug.getBdusage() / confgral.getAnchoBanda()) * 100);
        }

    }
}
