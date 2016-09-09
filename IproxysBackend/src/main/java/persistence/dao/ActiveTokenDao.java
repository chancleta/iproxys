package persistence.dao;

import PersistenceData.EntityManagerProvider;
import PersistenceData.PersistenceProvider;
import models.ActiveToken;
import persistence.Mongo;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Luis Pena on 8/18/2016.
 */
public class ActiveTokenDao extends PersistenceProvider {


    public static ActiveToken findByUserIdAndRefreshToken(long userId, String refreshToken) {
        Query query = EntityManagerProvider.getInstance().getEntityManager().createNamedQuery("ActiveToken.findByUserIdAndRefreshToken", ActiveToken.class);
        query.setParameter("id", userId);
        query.setParameter("refreshToken", refreshToken);
        try {
            return (ActiveToken) query.getSingleResult();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public static ActiveToken findByUserIdAndToken(long userId, String token) {
        List<ActiveToken> activeTokens = Mongo.getDataStore().createQuery(ActiveToken.class).filter("userId =", userId).filter("token =", token).asList();
        return activeTokens.size() == 1 ? activeTokens.get(0) : null;
    }

    public static ActiveToken findByUserId(long userId) {
        Query query = EntityManagerProvider.getInstance().getEntityManager().createNamedQuery("ActiveToken.findByUserId", ActiveToken.class);
        query.setParameter("id", userId);
        try {
            return (ActiveToken) query.getSingleResult();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

}
