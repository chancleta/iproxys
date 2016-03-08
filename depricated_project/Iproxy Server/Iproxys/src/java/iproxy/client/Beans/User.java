/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iproxy.client.Beans;

import java.io.Serializable;

/**
 *
 * @author root
 */
public class User implements UserInterface, Serializable{
    private String username;
    private String password;
    private String nombre;
    private String apellido;
    private String correo;

    /**
     * @return the username
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the nombre
     */
    @Override
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    @Override
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the apellido
     */
    @Override
    public String getApellido() {
        return apellido;
    }

    /**
     * @param apellido the apellido to set
     */
    @Override
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * @return the correo
     */
    @Override
    public String getCorreo() {
        return correo;
    }

    /**
     * @param correo the correo to set
     */
    @Override
    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
