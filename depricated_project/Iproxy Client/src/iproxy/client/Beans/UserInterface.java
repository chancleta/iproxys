/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iproxy.client.Beans;

/**
 *
 * @author root
 */
public interface UserInterface {
      
    public String getUsername() ;

    /**
     * @param username the username to set
     */
    public void setUsername(String username) ;

    /**
     * @return the password
     */
    public String getPassword() ;

    /**
     * @param password the password to set
     */
    public void setPassword(String password) ;

    /**
     * @return the nombre
     */
    public String getNombre() ;

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre);

    /**
     * @return the apellido
     */
    public String getApellido() ;

    /**
     * @param apellido the apellido to set
     */
    public void setApellido(String apellido);

    /**
     * @return the correo
     */
    public String getCorreo() ;

    /**
     * @param correo the correo to set
     */
    public void setCorreo(String correo) ;
}
