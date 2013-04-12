/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iproxys.InetDataCollector;

import iproxy.externalDependencies.EjecutarIPtable;
import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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
   
    private static EntityManagerFactory createEntityManagerFactory = Persistence.createEntityManagerFactory("iproxys");
    public static EntityManager createEntityManager = null;

    @PostConstruct
    private void Iniciar(){
       
       Sniffer instance = Sniffer.getInstance();
       instance.select();
        createEntityManager = createEntityManagerFactory.createEntityManager();
        EjecutarIPtable instance1 = EjecutarIPtable.getInstance();
        instance1.iptableEjecutar();
    }
    @Override
    public EntityManager getEntityManager(){
        return createEntityManager;
    }
    
}
