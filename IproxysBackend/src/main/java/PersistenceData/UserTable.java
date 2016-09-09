/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PersistenceData;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author ljpena
 */
@Entity
@NamedQueries({
        @NamedQuery(name = UserTable.findAllUsers, query = "select u from UserTable u"),
        @NamedQuery(name = UserTable.findbyUsername, query = "select u from UserTable u where u.username = :username"),
        @NamedQuery(name = "UserTable.findAll", query = "SELECT u FROM UserTable u"),
        @NamedQuery(name = "UserTable.findById", query = "SELECT u FROM UserTable u where u.id = :id"),
        @NamedQuery(name = "UserTable.findUserByCredentials", query = "SELECT u FROM UserTable u where u.username = :username and u.password = :password")
})
public class UserTable extends PersistenceProvider implements Serializable {

    public static final String findAllUsers = "findAllUsers";
    public static final String findbyUsername = "findbyUsername";
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;
    @Column(name = "username", unique = true, length = 50, nullable = false)
    private String username;
    @Column(name = "password", length = 50, nullable = false)
    private String password;
    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;
    @Column(name = "apellido", length = 100, nullable = false)
    private String apellido;
    @Column(name = "correo", unique = true, length = 100, nullable = false)
    private String correo;

    public UserTable() {
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
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the apellido
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * @param apellido the apellido to set
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * @return the correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * @param correo the correo to set
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public UserTable findbyUsername(String username) {
        Query createNamedQuery = entityManager.createNamedQuery(UserTable.findbyUsername, UserTable.class);
        createNamedQuery.setParameter("username", username);
        UserTable user = null;
        try {
            Object o = createNamedQuery.getSingleResult();
            UserTable users = (UserTable) o;
            user = users;
        } catch (Exception err) {
            System.out.println(err.getMessage());
        } finally {
            return user;
        }

        // createNamedQuery.setParameter("username", username);


    }
}
