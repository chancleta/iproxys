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
@Local
public interface IPtablesDbFacadeLocal {

    public java.util.List<iproxys.PersistenceData.IPtablesDb> findAll();

    public iproxys.PersistenceData.IPtablesDb findbyIPtable(java.lang.String comando);

    boolean create(iproxys.PersistenceData.IPtablesDb iptable);

    public boolean remove(iproxys.PersistenceData.IPtablesDb iptable);
    
}
