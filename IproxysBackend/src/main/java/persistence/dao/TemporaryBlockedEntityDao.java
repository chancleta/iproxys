package persistence.dao;

import PersistenceData.EntityManagerProvider;
import PersistenceData.PersistenceProvider;
import PersistenceData.TemporaryBlockedEntity;
import models.ActiveToken;

import javax.persistence.Query;
import javax.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by root on 30/09/16.
 */
public class TemporaryBlockedEntityDao extends PersistenceProvider {

    public static List<TemporaryBlockedEntity> findAllBlocked() {
        Query query = EntityManagerProvider.getInstance().getEntityManager().createNamedQuery("TemporaryBlockedEntity.findAll", TemporaryBlockedEntity.class);

        try {
            return (List<TemporaryBlockedEntity>) query.getResultList();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new ArrayList<>();
        }
    }


    public static List<TemporaryBlockedEntity> findTemporaryBlockedEntities(){

        Query query = EntityManagerProvider.getInstance().getEntityManager().createNamedQuery("TemporaryBlockedEntity.findEntityToUnblock",TemporaryBlockedEntity.class);
        query.setParameter("allowTimeStart", new Date(System.currentTimeMillis() ), TemporalType.TIMESTAMP);
        query.setParameter("allowTimeEnd", new Date(System.currentTimeMillis()  - (10 * TemporaryBlockedEntity.MIN_IN_MS)), TemporalType.TIMESTAMP);
        try {
            return (List<TemporaryBlockedEntity>) query.getResultList();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new ArrayList<>();
        }

    }
}
