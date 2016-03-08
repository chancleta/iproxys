/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PersistenceData;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author root
 */
public class EntityManagerProvider {

    private static EntityManagerProvider entityManagerProvider = null;
    public static final String PERSISTENCE_UNIT = "TestJEE-ejbPU";
    private EntityManagerFactory entityFactory;
    private EntityManager entityManager;

    private EntityManagerProvider() {
        entityFactory = Persistence.createEntityManagerFactory(EntityManagerProvider.PERSISTENCE_UNIT);
        entityManager = entityFactory.createEntityManager();
    }

    public static EntityManagerProvider getInstance() {
        if (entityManagerProvider == null) {
            entityManagerProvider = new EntityManagerProvider();
        }
        return entityManagerProvider;
    }
    public EntityManager getEntityManager(){
        return entityManager;
    }
}
