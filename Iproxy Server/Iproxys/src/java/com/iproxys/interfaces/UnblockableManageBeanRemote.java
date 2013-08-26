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
public interface UnblockableManageBeanRemote {

    public boolean Insert(java.lang.String ip);

    public java.util.List<java.lang.String> getAllIP();

    public boolean remove(java.lang.String ip);

    public void setSystemPassword(java.lang.String password);

    public java.lang.String getSystemPassword();

    public java.lang.String getIptables();
    
}
