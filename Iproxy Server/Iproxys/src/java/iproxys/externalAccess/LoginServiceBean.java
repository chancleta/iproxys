/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iproxys.externalAccess;

import com.iproxys.interfaces.LoginServiceBeanRemote;
import iproxys.PersistenceData.UserTable;
import iproxys.dataFacade.UserTableFacadeLocal;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

/**
 *
 * @author root
 */
@Stateless
@Remote(com.iproxys.interfaces.LoginServiceBeanRemote.class)
public class LoginServiceBean  implements  LoginServiceBeanRemote{

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @EJB
    UserTableFacadeLocal user;
    
    @Override
    public boolean doLogin(String username, String password){
       System.err.println(username);
        UserTable findbyUsername = new UserTable();
        findbyUsername = findbyUsername.findbyUsername(username);
       if(findbyUsername == null)
           return false;
       if(findbyUsername.getPassword().equals(password))
           return true;
       return false;
    }
  /* public static void main(String[] ar){
   LoginServiceBean log = new LoginServiceBean();
   System.err.println(log.doLogin("chancleta", "elchulo"));
   
   }*/
}
