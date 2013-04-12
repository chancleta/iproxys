/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iproxys.dataFacade;

import javax.ejb.Local;

/**
 *
 * @author root
 */
public interface HttpBlockDbFacadeLocal {

    public java.util.List<iproxys.PersistenceData.HttpBlockDb> findAll();

    public iproxys.PersistenceData.HttpBlockDb findbyIPtable(java.lang.String domain);

    public boolean create(iproxys.PersistenceData.HttpBlockDb httpBlock);

    public boolean remove(iproxys.PersistenceData.HttpBlockDb httpBlock);
    
}
