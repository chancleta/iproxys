/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iproxys.dataFacade;

import iproxys.InetDataCollector.IniciarSniffer;
import iproxys.PersistenceData.HttpBlockDb;
import java.util.List;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author root
 */
public class HttpBlockDbFacade implements HttpBlockDbFacadeLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
     private static HttpBlockDbFacade httpblockDbFacade = null;

    private HttpBlockDbFacade() {
    }

    public static HttpBlockDbFacade getInstance() {
       
        if (httpblockDbFacade == null) {
            httpblockDbFacade = new HttpBlockDbFacade();
        }
        return httpblockDbFacade;
    }

    @Override
    public List<HttpBlockDb> findAll() {

        Query createNamedQuery = IniciarSniffer.createEntityManager.createNamedQuery(HttpBlockDb.findAllHttpBlock);
        return (List<HttpBlockDb>) createNamedQuery.getResultList();
    }

    @Override
    public HttpBlockDb findbyIPtable(String domain) {

        Query createNamedQuery = IniciarSniffer.createEntityManager.createNamedQuery(HttpBlockDb.findbyDomain);
        createNamedQuery.setParameter("domain", domain);
        HttpBlockDb httpBlock = null;
        try {
            httpBlock = (HttpBlockDb) createNamedQuery.getSingleResult();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        } finally {
            return httpBlock;
        }

    }

    @Override
    public boolean create(HttpBlockDb httpBlock) {
        EntityTransaction transaction = IniciarSniffer.createEntityManager.getTransaction();
        transaction.begin();
        try {
            IniciarSniffer.createEntityManager.persist(httpBlock);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return false;
        }

    }

    @Override
    public boolean remove(HttpBlockDb httpBlock) {
        EntityTransaction transaction = IniciarSniffer.createEntityManager.getTransaction();
        try {
            transaction.begin();
            IniciarSniffer.createEntityManager.remove(httpBlock);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return false;
        }

    }
}
