/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iproxys.externalAccess;

import com.iproxys.interfaces.UnblockableManageBeanRemote;
import iproxy.externalDependencies.EjecutarComando;
import iproxys.PersistenceData.UnBlockableIP;
import iproxys.dataFacade.UnBlockableIPFacade;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;
import javax.ejb.Stateless;

/**
 *
 * @author root
 */
@Stateless
@Remote(UnblockableManageBeanRemote.class)
public class UnblockableManageBean implements UnblockableManageBeanRemote{

    
    UnBlockableIPFacade unBlock = UnBlockableIPFacade.getInstance() ;
    
    @Override
    public boolean Insert(String ip){
       UnBlockableIP newUnip = new UnBlockableIP();
       newUnip.setBlockDate(new Date());
       newUnip.setIp(ip);
       return unBlock.create(newUnip);
    
    }
    @Override
    public List<String> getAllIP(){
        List<UnBlockableIP> findAll = unBlock.findAll();
        List<String> listip = new ArrayList<String>();
        for(UnBlockableIP newUnip:findAll)
        {
            listip.add(newUnip.getIp());
        }
        return listip;
    }
    @Override
    public boolean remove(String ip){
        UnBlockableIP findbyIP = unBlock.findbyIP(ip);
        if(findbyIP != null){
            return unBlock.remove(findbyIP);
        }else{
            return false;
        }
    }
    
    @Override
    public void setSystemPassword(String password){
    
    
    
    }
    @Override
    public String getSystemPassword(){
    
    
    
        return "";
    }
    
    @Override
    public String getIptables(){
        
        EjecutarComando ejecutar = EjecutarComando.getInstance();
        return ejecutar.Ejecutar_Comando("iptables -L -v");
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
}