/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iproxy.externalDependencies;

import iproxys.PersistenceData.IPtablesDb;
import iproxys.dataFacade.IPtablesDbFacade;
import java.util.Date;
import java.util.List;

/**
 *
 * @author root
 */
public class EjecutarIPtable {

    private static EjecutarIPtable ejecutarIPtable = null;
    public static final String[] flushIPtables = {"iptables -F", "iptables -X", "iptables -Z", "iptables -t nat -F"};
    public static final String[] natIPtables = {"echo 1 > /proc/sys/net/ipv4/ip_forward", " iptables -t nat -A POSTROUTING -s 0.0.0.0/0 -o eth0 -j MASQUERADE", "iptables -t nat -A PREROUTING -p tcp --dport 80 -j REDIRECT --to-port 3128", "iptables -A FORWARD -i eth0 -o wlan0 -j ACCEPT", "iptables -A FORWARD -i wlan0 -o eth0 -j ACCEPT"};
    public static final String allow = "allow";
    public static final String block = "block";
    public static final String tcp = "tcp";
    public static final String udp = "udp";
    public static final int tcpproto = 6;
    public static final int udpprotp = 17;
    private static EjecutarComando ejecutar = EjecutarComando.getInstance();
    private static IPtablesDbFacade iptableFacade = IPtablesDbFacade.getInstance();

    private EjecutarIPtable() {
    }

    public static EjecutarIPtable getInstance() {
        if (ejecutarIPtable == null) {
            ejecutarIPtable = new EjecutarIPtable();
        }
        return ejecutarIPtable;
    }

    public void controlPuertos(String action, int port, int protocol, Date timeref) {
        if (action.equals(block)) {
            IPtablesDb ipdb = new IPtablesDb();
            if (protocol == tcpproto) {
                ipdb.setComando("iptables -A FORWARD -p " + tcp + " --dport " + port + " -j DROP");
            } else {
                ipdb.setComando("iptables -A FORWARD -p " + udp + " --dport " + port + " -j DROP");
            }
            if (!existeRegistro(ipdb.getComando())) {

                ipdb.setProtocol(protocol);
                ipdb.setPort(port);
                ipdb.setTipo(3);
                ipdb.setTimeref(timeref);
                iptableFacade.create(ipdb);
            }
            iptableEjecutar();
        } else if (action.equals(allow)) {
        }

    }

    public void controlIP(String action, String ip, Date timeref) {

        if (action.equals(block)) {
            IPtablesDb ipdb = new IPtablesDb();
            ipdb.setComando("iptables -A FORWARD -i wlan0 -s " + ip + " -j DROP");
            if (!existeRegistro(ipdb.getComando())) {

                ipdb.setTipo(1);
                ipdb.setIp(ip);
                ipdb.setTimeref(timeref);
                iptableFacade.create(ipdb);
            }
            iptableEjecutar();
        } else if (action.equals(allow)) {
        }

    }

    public void controlIPPort(String action, String ip, int puerto, int protocolo, Date timeref) {

        if (action.equals(block)) {
            IPtablesDb ipdb = new IPtablesDb();
            if (protocolo == tcpproto) {
                ipdb.setComando("iptables -A FORWARD -i wlan0 -s " + ip + " -p " + tcp + " --dport " + puerto + " -j DROP");
            } else {
                ipdb.setComando("iptables -A FORWARD -i wlan0 -s " + ip + " -p " + udp + " --dport " + puerto + " -j DROP");
            }
            if (!existeRegistro(ipdb.getComando())) {
                ipdb.setTipo(2);
                ipdb.setTimeref(timeref);
                ipdb.setIp(ip);
                ipdb.setPort(puerto);
                ipdb.setProtocol(protocolo);

                iptableFacade.create(ipdb);
                
            }
            iptableEjecutar();
        } else if (action.equals(allow)) {
        }

    }

    private void flushIPtables() {

        if (ejecutar != null) {
            for (int i = 0; i < flushIPtables.length; i++) {
                ejecutar.Ejecutar_Comando(flushIPtables[i]);

            }

        }
    }

    private void natIPtables() {

        if (ejecutar != null) {

            for (int i = 0; i < natIPtables.length; i++) {
                ejecutar.Ejecutar_Comando(natIPtables[i]);

            }
        }
    }

    public void iptableEjecutar() {
        flushIPtables();


        System.err.println("Desde Iptables: ejecutar" + ejecutar.toString());
        if (ejecutar != null) {
//            List<IPtablesDb> findAll = iptableFacade.findAll();
//            for (IPtablesDb iptable : findAll) {
//                ejecutar.Ejecutar_Comando(iptable.getComando());
//            }
        }

        natIPtables();
    }

    private boolean existeRegistro(String comando) {


        if (ejecutar != null) {
            List<IPtablesDb> findAll = iptableFacade.findAll();
            for (IPtablesDb iptable : findAll) {
                if (iptable.getComando().equals(comando)) {
                    return true;
                }
            }
        }
        return false;

    }
}