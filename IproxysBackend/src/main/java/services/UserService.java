package services;

import PersistenceData.UserTable;
import models.User;
import models.UserRoles;
import persistence.dao.UserDao;

import java.util.List;

/**
 * Created by Luis Pena on 8/23/2016.
 */
public class UserService {
    private static UserService userService;

    private UserService() {
    }

    public static UserService getInstance() {
        if (userService == null)
            userService = new UserService();
        return userService;
    }

    public List<User> findAll() {
        return UserDao.findAll();
    }

    public UserTable findById(long id){
        return UserDao.findUserById(id);
    }

    public List<User> findByRole(UserRoles distribuitor) {
        return UserDao.findByRole(distribuitor);
    }

    public List<User> findByCreatedby(String id) {
        return UserDao.findByCreatedby(id);
    }

    public List<User> findByEmailAndUsername(String email, String username) {
        return UserDao.findByEmailAndUsername(email,username);
    }

    public List<User> findByEmail(String email) {
        return UserDao.findByEmail(email);
    }

    public List<User> findByUsername(String username) {
        return UserDao.findByUsername(username);
    }
}
