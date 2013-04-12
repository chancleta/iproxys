/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iproxys.dataFacade;

import iproxys.InetDataCollector.IniciarSnifferLocal;
import iproxys.PersistenceData.UserTable;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author LuisjPena
 */
@Stateless
public class UserTableFacade  implements UserTableFacadeLocal {
   
    @EJB
    IniciarSnifferLocal isl;
    
    @Override
    public List<UserTable> findAll(){
        
        Query createNamedQuery = isl.getEntityManager().createNamedQuery(UserTable.findAllUsers);
        return (List<UserTable>)createNamedQuery.getResultList();
    }
    
    @Override
    public UserTable findbyUsername(String username){
        
        Query createNamedQuery = isl.getEntityManager().createNamedQuery(UserTable.findbyUsername);
        createNamedQuery.setParameter("username", username);
        UserTable user = null;
        try{
            user = (UserTable) createNamedQuery.getSingleResult();
        }catch(Exception ex){
            System.err.println(ex.getMessage());
        }finally{
            return user;
        }
            
    }
    @Override
    public boolean create(UserTable user){
        EntityTransaction transaction = isl.getEntityManager().getTransaction();
        transaction.begin();
        try{
            isl.getEntityManager().persist(user);
            transaction.commit();
            return true;
        }catch(Exception ex)
        {
            System.err.println(ex.getMessage());
            return false;
        }
        
    }
    @Override
    public boolean remove(UserTable user){
        EntityTransaction transaction = isl.getEntityManager().getTransaction();
        try{
            transaction.begin();
            isl.getEntityManager().remove(user);
            transaction.commit();
            return true;
        }catch(Exception ex){
            System.err.println(ex.getMessage());
            return false;
        }
    
    }
   
}
