package persistence.dao;

import PersistenceData.EntityManagerProvider;
import PersistenceData.PersistenceProvider;
import PersistenceData.TemporaryBlockedEntity;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 30/09/16.
 */
public class TemporaryBlockedEntityDao extends PersistenceProvider {

    public static List<TemporaryBlockedEntity> findAllBlocked() {
        @SuppressWarnings("JpaQueryApiInspection") Query query = EntityManagerProvider.getInstance().getEntityManager().createNamedQuery("TemporaryBlockedEntity.findAll", TemporaryBlockedEntity.class);

        try {
            return (List<TemporaryBlockedEntity>) query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


    public static List<TemporaryBlockedEntity> findTemporaryBlockedEntities(){

        @SuppressWarnings("JpaQueryApiInspection") Query query = EntityManagerProvider.getInstance().getEntityManager().createNamedQuery("TemporaryBlockedEntity.findLiveActions",TemporaryBlockedEntity.class);
        try {
            return (List<TemporaryBlockedEntity>) query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

    }
}
