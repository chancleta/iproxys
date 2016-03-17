/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PersistenceData;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Luis
 */
public abstract class PersistenceProvider implements Serializable {
    private EntityManagerProvider entityManagerProvider;
    protected EntityManager entityManager;
    public PersistenceProvider(){
        entityManagerProvider = EntityManagerProvider.getInstance();
        entityManager = entityManagerProvider.getEntityManager();
    }
    public boolean save() {
        try {
            EntityTransaction currentTransaction = beginTransaction();
            entityManager.persist(this);
            currentTransaction.commit();
            return true;
        } catch (Exception err) {
            System.err.println(err.getMessage());
            return false;
        }
    }

    public void delete() {
        EntityTransaction currentTransaction = beginTransaction();
        entityManager.remove(this);
        currentTransaction.commit();
    }

    public Object finbByID(long Id) {
        return entityManager.find(this.getClass(), Id);
    }

    public List<Object> findAll() {
        Query findAllNamedQuery = entityManager.createNamedQuery(this.getClass().getSimpleName() + ".findAll", this.getClass());
        return findAllNamedQuery.getResultList();
    }

    private EntityTransaction beginTransaction() {
        EntityTransaction CurrentTransaction = entityManager.getTransaction();
        CurrentTransaction.begin();
        return CurrentTransaction;
    }

    public boolean update() {
        try {
            EntityTransaction currentTransaction = beginTransaction();
            entityManager.merge(this);
            currentTransaction.commit();
            return true;
        } catch (Exception err) {
            System.err.println(err.getMessage());
            return false;
        }
    }
}