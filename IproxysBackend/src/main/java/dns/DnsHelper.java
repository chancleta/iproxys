/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dns;


import com.google.common.net.InternetDomainName;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Properties;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Luis
 */
public class DnsHelper {

   public static boolean isDomainReachable(String domain) {
        Socket socket = null;
        boolean isDomainReachable = false;
        try {
            socket = new Socket(domain, 80);
            socket = new Socket(domain, 8080);
            isDomainReachable = true;
        } catch (IOException ex) {
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        }
        return isDomainReachable;
    }

    public static String getDomainNameFromIp(String ipAddress) {
        String ipHostName = getHostName(ipAddress);
//        if(ipHostName.equals(ipAddress)){
//            return ipAddress;
//        }
//        String domainName = getTopLevelDomain(ipHostName);
//        return isDomainReachable(domainName)?"."+domainName:ipAddress;
        return ipHostName.equals(ipAddress)? ipAddress: ipHostName;
    }

    public static String getTopLevelDomain(String uri) {
//        System.out.println("ARREGLAR DNS HELPER GETTOPLEVELDOMAIN METHOD");
        return InternetDomainName.from(uri).topPrivateDomain().toString();
    }

    public static String getHostName(final String ip) {
        String retVal = null;
        final String[] bytes = ip.split("\\.");
        if (bytes.length == 4) {
            try {
                Properties env = new Properties();
                env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
                DirContext ctx = new InitialDirContext(env);
                String reverseDnsDomain = bytes[3] + "." + bytes[2] + "." + bytes[1] + "." + bytes[0] + ".in-addr.arpa";
                Attributes attrs = ctx.getAttributes(reverseDnsDomain, new String[]{
                    "PTR",});
                for (NamingEnumeration<? extends Attribute> ae = attrs.getAll(); ae.hasMoreElements();) {
                    Attribute attr = ae.next();
                    String attrId = attr.getID();
                    for (Enumeration<?> vals = attr.getAll(); vals.hasMoreElements();) {
                        String value = vals.nextElement().toString();
                        // System.out.println(attrId + ": " + value);

                        if ("PTR".equals(attrId)) {
                            int len = value.length();
                            if (value.charAt(len - 1) == '.') {
                                // Strip out trailing period
                                value = value.substring(0, len - 1);
                            }
                            retVal = value;
                        }
                    }
                }
                ctx.close();
            } catch (NamingException e) {
//                System.out.print("Did not get anything using  ptr request, trying InetAddress Option"); // NO-OP
            }
        }
        if (null == retVal) {
            try {
                retVal = InetAddress.getByName(ip).getCanonicalHostName();
            } catch (UnknownHostException e1) {
                retVal = ip;
            }
        }
        return retVal;
    }

    
 
}
