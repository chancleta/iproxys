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
public interface UnBlockableIPFacadeLocal {

    public java.util.List<iproxys.PersistenceData.UnBlockableIP> findAll();

    public iproxys.PersistenceData.UnBlockableIP findbyIP(java.lang.String ip);

    public boolean create(iproxys.PersistenceData.UnBlockableIP Unip);

    public boolean remove(iproxys.PersistenceData.UnBlockableIP Unip);
    
}