package persistence.dao;

import PersistenceData.EntityManagerProvider;
import PersistenceData.UserTable;
import models.ActiveToken;
import models.User;
import models.UserRoles;
import org.bson.types.ObjectId;
import persistence.Mongo;

import javax.persistence.Query;
import java.util.List;

/**
 * Created by Luis Pena on 8/18/2016.
 */
public class UserDao {


    public static UserTable findUserByCredentials(String username, String password) {
        Query query = EntityManagerProvider.getInstance().getEntityManager().createNamedQuery("UserTable.findUserByCredentials", ActiveToken.class);
        query.setParameter("username", username);
        query.setParameter("password", password);
        try {
            return (UserTable) query.getSingleResult();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public static UserTable findUserById(long id) {
        Query query = EntityManagerProvider.getInstance().getEntityManager().createNamedQuery("UserTable.findById", UserTable.class);
        query.setParameter("id", id);
        try {
            return (UserTable) query.getSingleResult();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public static List<User> findAll() {
        return Mongo.getDataStore().createQuery(User.class).asList();
    }

    public static List<User> findByRole(UserRoles distribuitor) {
        return Mongo.getDataStore().createQuery(User.class).filter("role =", distribuitor).asList();
    }

    public static List<User> findByCreatedby(String id) {
        return Mongo.getDataStore().createQuery(User.class).field("createdBy").equal(new ObjectId(id)).asList();
    }

    public static List<User> findByEmailAndUsername(String email, String username) {
        return Mongo.getDataStore().createQuery(User.class).field("email").equal(email).field("username").equal(username).asList();
    }

    public static List<User> findByEmail(String email) {
        return Mongo.getDataStore().createQuery(User.class).field("email").equal(email).asList();
    }

    public static List<User> findByUsername(String username) {
        return Mongo.getDataStore().createQuery(User.class).field("username").equal(username).asList();
    }
}
