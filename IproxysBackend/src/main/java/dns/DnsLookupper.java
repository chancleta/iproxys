/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dns;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author root
 */
public class DnsLookupper {

    private static DnsLookupper DnsLookupper = null;

    public static DnsLookupper getInstance() {
        if (DnsLookupper == null) {
            DnsLookupper = new DnsLookupper();

        }
        return DnsLookupper;
    }

    private DnsLookupper() {
    }

    public String doDns(String ip) {

        try {
            InetAddress addr = InetAddress.getByName(ip);
            
            return addr.getHostName();
            
        } catch (UnknownHostException e) {
            return null;
        }

    }

  
}
