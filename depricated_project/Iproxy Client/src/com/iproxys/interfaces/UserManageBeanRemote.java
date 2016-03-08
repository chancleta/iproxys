/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iproxys.interfaces;

import javax.ejb.Remote;

/**
 *
 * @author root
 */
@Remote
public interface UserManageBeanRemote {

    public boolean remove(java.lang.String username);

    public java.util.List<iproxy.client.Beans.User> getAllUsers();

    public boolean Insert(iproxy.client.Beans.User user);
    
}
