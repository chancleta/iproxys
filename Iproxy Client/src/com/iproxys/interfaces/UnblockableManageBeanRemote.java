/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iproxys.interfaces;

import iproxy.client.Beans.UnblockableBean;
import javax.ejb.Remote;

/**
 *
 * @author root
 */
@Remote
public interface UnblockableManageBeanRemote {

    public boolean Insert(UnblockableBean unblockableBean);

    public java.util.List<UnblockableBean> getAllUnblockableEntities();

    public boolean remove(UnblockableBean unblockableBean);

    public void setSystemPassword(java.lang.String password);

    public java.lang.String getSystemPassword();

    public java.lang.String getIptables();
    
}

