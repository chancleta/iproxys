package persistence.dao;

import PersistenceData.EntityManagerProvider;
import PersistenceData.PersistenceProvider;
import PersistenceData.UnblockableEntity;

import javax.persistence.Query;
import java.util.List;

/**
 * Created by Luis Pena on 8/18/2016.
 */
public class UnblockableEntityDao extends PersistenceProvider {


    public static List<UnblockableEntity> findByAll() {
        @SuppressWarnings("JpaQueryApiInspection") Query query = EntityManagerProvider.getInstance().getEntityManager().createNamedQuery("UnblockableEntity.findAll", UnblockableEntity.class);

        try {
            return (List<UnblockableEntity>) query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
