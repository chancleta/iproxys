/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iproxys.InetDataCollector;

import iproxy.externalDependencies.EjecutarIPtable;
import iproxys.PersistenceData.TemporaryBlockedEntity;
import static iproxys.PersistenceData.TemporaryBlockedEntity.HOUR_IN_MS;
import iproxys.PersistenceData.UserTable;
import iproxys.performblock.timers.TimerInitializer;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author root
 */
@Singleton
@Local
@Startup
public class IniciarSniffer implements IniciarSnifferLocal {
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")sniffer
   
    private static EntityManagerFactory createEntityManagerFactory;// = Persistence.createEntityManagerFactory("Iproxys");
    public static EntityManager createEntityManager;// = null;
    @PostConstruct
    private void Iniciar(){
       
       Sniffer instance = Sniffer.getInstance();
       instance.select();
       
              
       //  createEntityManager = createEntityManagerFactory.createEntityManager();
        UserTable u = new UserTable();
        u.setApellido("Pena");
        u.setCorreo("ljpenaurena@gmail.com");
        u.setNombre("Luis");
        u.setPassword("elchulo");
        u.setUsername("chancleta");
        u.save();
        
        UserTable findbyUsername = u.findbyUsername("chancleta");
        
        System.out.println(findbyUsername.getApellido());
        
        EjecutarIPtable instance1 = EjecutarIPtable.getInstance();
        instance1.iptableEjecutar();
        
        TemporaryBlockedEntity trwem = new TemporaryBlockedEntity();
        TimerInitializer.initialize();
        
        
        
        
        
    }
    @Override
    public EntityManager getEntityManager(){
        return createEntityManager;
    }
    
}
