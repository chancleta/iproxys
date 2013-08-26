/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iproxys.dataFacade;

import iproxys.InetDataCollector.IniciarSniffer;
import iproxys.PersistenceData.UnBlockableIP;
import java.util.List;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author root
 */
public class UnBlockableIPFacade implements UnBlockableIPFacadeLocal {
    
    private static UnBlockableIPFacade unblockableIPFacade = null;
    private UnBlockableIPFacade(){}
    public static UnBlockableIPFacade getInstance(){
        if(unblockableIPFacade == null)
            unblockableIPFacade = new UnBlockableIPFacade();
        return unblockableIPFacade;
    }
    
    @Override
    public List<UnBlockableIP> findAll(){
        
        Query createNamedQuery = IniciarSniffer.createEntityManager.createNamedQuery(UnBlockableIP.findAllUnblockableIPs);
        return (List<UnBlockableIP>)createNamedQuery.getResultList();
    }
    
    @Override
    public UnBlockableIP findbyIP(String ip){
        
        Query createNamedQuery = IniciarSniffer.createEntityManager.createNamedQuery(UnBlockableIP.findbyIP);
        createNamedQuery.setParameter("ip", ip);
        UnBlockableIP Unip = null;
        try{
            Unip = (UnBlockableIP) createNamedQuery.getSingleResult();
        }catch(Exception ex){
            System.err.println(ex.getMessage());
        }finally{
            return Unip;
        }
            
    }
    @Override
    public boolean create(UnBlockableIP Unip){
        EntityTransaction transaction = IniciarSniffer.createEntityManager.getTransaction();
        transaction.begin();
        try{
           IniciarSniffer.createEntityManager.persist(Unip);
            transaction.commit();
            return true;
        }catch(Exception ex)
        {
            System.err.println(ex.getMessage());
            return false;
        }
        
    }
    @Override
    public boolean remove(UnBlockableIP Unip){
        EntityTransaction transaction =IniciarSniffer.createEntityManager.getTransaction();
        try{
            transaction.begin();
           IniciarSniffer.createEntityManager.remove(Unip);
            transaction.commit();
            return true;
        }catch(Exception ex){
            System.err.println(ex.getMessage());
            return false;
        }
    
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
}
