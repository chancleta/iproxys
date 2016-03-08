/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iproxys.interfaces;

import javax.ejb.Remote;

/**
 *
 * @author LuisjPena
 */
@Remote
public interface LoginServiceBeanRemote {

    public boolean doLogin(java.lang.String username, java.lang.String password);
    
}
