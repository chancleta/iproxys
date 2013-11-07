/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iproxys.externalAccess;

import com.iproxys.interfaces.ArrivalDataBeanRemote;
import iproxys.InetDataCollector.Sniffer;
import javax.ejb.Remote;
import javax.ejb.Stateless;

/**
 *
 * @author LuisjPena
 */
@Stateless
@Remote
public class ArrivalDataBean implements ArrivalDataBeanRemote{

    @Override
    public double NetworkMonitor(){
        
       return (Sniffer.networkMonitorLastSeg);
    }
    
    
}
