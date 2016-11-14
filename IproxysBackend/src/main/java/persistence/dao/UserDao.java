package persistence.dao;

import PersistenceData.EntityManagerProvider;
import PersistenceData.User;
import models.ActiveToken;
import models.UserRoles;
import org.bson.types.ObjectId;
import persistence.Mongo;

import javax.persistence.Query;
import java.util.List;

/**
 * Created by Luis Pena on 8/18/2016.
 */
public class UserDao {


    public static User findUserByCredentials(String username, String password) {
        Query query = EntityManagerProvider.getInstance().getEntityManager().createNamedQuery("User.findUserByCredentials", ActiveToken.class);
        query.setParameter("username", username);
        query.setParameter("password", password);
        try {
            return (User) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static User findUserById(long id) {
        Query query = EntityManagerProvider.getInstance().getEntityManager().createNamedQuery("User.findById", User.class);
        query.setParameter("id", id);
        try {
            return (User) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<models.User> findAll() {
        return Mongo.getDataStore().createQuery(models.User.class).asList();
    }

    public static List<models.User> findByRole(UserRoles distribuitor) {
        return Mongo.getDataStore().createQuery(models.User.class).filter("role =", distribuitor).asList();
    }

    public static List<models.User> findByCreatedby(String id) {
        return Mongo.getDataStore().createQuery(models.User.class).field("createdBy").equal(new ObjectId(id)).asList();
    }

    public static List<models.User> findByEmailAndUsername(String email, String username) {
        return Mongo.getDataStore().createQuery(models.User.class).field("email").equal(email).field("username").equal(username).asList();
    }

    public static List<models.User> findByEmail(String email) {
        return Mongo.getDataStore().createQuery(models.User.class).field("email").equal(email).asList();
    }

    public static List<models.User> findByUsername(String username) {
        return Mongo.getDataStore().createQuery(models.User.class).field("username").equal(username).asList();
    }
}
