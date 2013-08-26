/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iproxys.PersistenceData;

import com.sun.org.apache.bcel.internal.generic.AALOAD;
import iproxys.jess.JessSuggestions;
import iproxys.jess.ServiceCore;
import java.util.logging.Level;
import java.util.logging.Logger;
import jess.JessException;
import jpcap.NetworkInterface;

/**
 *
 * @author root
 */
public class proba {

    public static void main(String[] as) {
//            ServiceCore instance = ServiceCore.getInstance();
//            
//            SummaryPort_BandWidth sumport = new SummaryPort_BandWidth();
//            sumport.setPort(80);
//            sumport.setBdusage(85);
//            sumport.setProtocol(6);
//            
//            SummaryIPPort_BandWidth sumipport = new SummaryIPPort_BandWidth();
//            sumipport.setPort(80);
//            sumipport.setBdusage(5);
//            sumipport.setIp_Dst("asdsad");
//            SummaryIPPort_BandWidth sumipport1 = new SummaryIPPort_BandWidth();
//            sumipport1.setPort(80);
//             sumipport1.setBdusage(20);
//            
//            SummaryIPPort_BandWidth sumipport2 = new SummaryIPPort_BandWidth();
//            sumipport2.setPort(80);
//             sumipport2.setBdusage(10);
//            
//            SummaryIPPort_BandWidth sumipport3 = new SummaryIPPort_BandWidth();
//            sumipport3.setPort(80);
//            sumipport3.setBdusage(30);
//             
//            SummaryIPPort_BandWidth sumipport4 = new SummaryIPPort_BandWidth();
//            sumipport4.setPort(80);
//             sumipport4.setBdusage(20);
//             
//            instance.addObject(sumipport);
//            instance.addObject(sumipport1);
//            instance.addObject(sumipport2);
//            instance.addObject(sumipport3);
//            instance.addObject(sumipport4);
//            instance.addObject(sumport);
//            double x1,x2,mayor=0;
//            for(JessSuggestions sug :instance.GetAllSuggestions()){
//               /* x1= sug.getDb();
//                x2=sug.getDb();
//                if(x1>x2)
//                    mayor=x1;
//                else
//                    mayor=x2;*/
//            System.err.println(sug.getDb());
        NetworkInterface[] InetInterfaces = jpcap.JpcapCaptor.getDeviceList();
    }
}
