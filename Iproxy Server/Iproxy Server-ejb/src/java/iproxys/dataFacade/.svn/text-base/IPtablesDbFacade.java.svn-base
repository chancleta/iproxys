/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iproxys.dataFacade;

import iproxys.InetDataCollector.IniciarSniffer;
import iproxys.PersistenceData.IPtablesDb;
import java.util.List;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author root
 */
public class IPtablesDbFacade implements IPtablesDbFacadeLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private static IPtablesDbFacade iptablesDbFacade = null;

    private IPtablesDbFacade() {
    }

    public static IPtablesDbFacade getInstance() {
       
        if (iptablesDbFacade == null) {
            iptablesDbFacade = new IPtablesDbFacade();
        }
        return iptablesDbFacade;
    }

    @Override
    public List<IPtablesDb> findAll() {

        Query createNamedQuery = IniciarSniffer.createEntityManager.createNamedQuery(IPtablesDb.findAllIPtables);
        return (List<IPtablesDb>) createNamedQuery.getResultList();
    }

    @Override
    public IPtablesDb findbyIPtable(String comando) {

        Query createNamedQuery = IniciarSniffer.createEntityManager.createNamedQuery(IPtablesDb.findbyIPtables);
        createNamedQuery.setParameter("comando", comando);
        IPtablesDb iptable = null;
        try {
            iptable = (IPtablesDb) createNamedQuery.getSingleResult();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        } finally {
            return iptable;
        }

    }

    @Override
    public boolean create(IPtablesDb iptable) {
        EntityTransaction transaction = IniciarSniffer.createEntityManager.getTransaction();
        transaction.begin();
        try {
            IniciarSniffer.createEntityManager.persist(iptable);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return false;
        }

    }

    @Override
    public boolean remove(IPtablesDb iptable) {
        EntityTransaction transaction = IniciarSniffer.createEntityManager.getTransaction();
        try {
            transaction.begin();
            IniciarSniffer.createEntityManager.remove(iptable);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return false;
        }

    }
}
