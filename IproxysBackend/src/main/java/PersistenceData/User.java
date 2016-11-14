/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PersistenceData;

import models.UserRoles;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author ljpena
 */
@Entity
@Table(name = "users")
@NamedQueries({
        @NamedQuery(name = User.findAllUsers, query = "select u from User u"),
        @NamedQuery(name = User.findbyUsername, query = "select u from User u where u.username = :username"),
        @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
        @NamedQuery(name = "User.findById", query = "SELECT u FROM User u where u.id = :id"),
        @NamedQuery(name = "User.findUserByCredentials", query = "SELECT u FROM User u where u.username = :username and u.password = :password")
})
public class User extends PersistenceProvider implements Serializable {

    public static final String findAllUsers = "findAllUsers";
    public static final String findbyUsername = "findbyUsername";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;
    @Column( unique = true, length = 50, nullable = false)
    private String username;
    @Column( length = 50, nullable = false)
    private String password;
    @Column(length = 100, nullable = false)
    private String firstName;
    @Column( length = 100, nullable = false)
    private String lastName;
    @Column(unique = true, length = 100, nullable = false)
    private String email;
    @Column
    @Enumerated(EnumType.STRING)
    private UserRoles role;

    public User() {
        super();
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    public User findbyUsername(String username) {
        Query createNamedQuery = entityManager.createNamedQuery(User.findbyUsername, User.class);
        createNamedQuery.setParameter("username", username);
        User user = null;
        try {
            Object o = createNamedQuery.getSingleResult();
            User users = (User) o;
            user = users;
        } catch (Exception err) {
            err.printStackTrace();
        }
        return user;


        // createNamedQuery.setParameter("username", username);


    }

    public UserRoles getRole() {
        return role;
    }

    public void setRole(UserRoles role) {
        this.role = role;
    }
}
