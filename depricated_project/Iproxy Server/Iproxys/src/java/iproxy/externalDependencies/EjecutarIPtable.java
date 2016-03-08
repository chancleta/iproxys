/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iproxy.externalDependencies;

/**
 *
 * @author root
 */
public class EjecutarIPtable {

    public static final String[] flushIPtables = {"iptables -F", "iptables -X", "iptables -Z", "iptables -t nat -F"};
    public static final String[] natIPtables = {"sysctl -w net.ipv4.ip_forward=1", "iptables -t nat -A POSTROUTING -s 0.0.0.0/0 -o eth0 -j MASQUERADE", "iptables -t nat -A PREROUTING -p tcp --dport 80 -j REDIRECT --to-port 3128", "iptables -A FORWARD -i eth0 -o wlan0 -j ACCEPT", "iptables -A FORWARD -i wlan0 -o eth0 -j ACCEPT"};
    public static final String allow = "allow";
    public static final String block = "block";
    public static final String tcp = "tcp";
    public static final String udp = "udp";
    public static final int tcpproto = 6;
    public static final int udpprotp = 17;
    private static EjecutarComando ejecutar = EjecutarComando.getInstance();

    private static void flushIPtables() {
        for (int i = 0; i < flushIPtables.length; i++) {
            ejecutar.Ejecutar_Comando(flushIPtables[i]);
        }
    }

    private static void natIPtables() {
        for (int i = 0; i < natIPtables.length; i++) {
            ejecutar.Ejecutar_Comando(natIPtables[i]);
        }
    }

    public static void iptableEjecutar() {
        flushIPtables();
        System.err.println("Desde Iptables: ejecutar" + ejecutar.toString());
        natIPtables();
    }
}