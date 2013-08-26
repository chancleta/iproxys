/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iproxys.externalAccess;

import com.iproxys.interfaces.UserManageBeanRemote;
import iproxy.client.Beans.User;
import iproxys.PersistenceData.UserTable;
import iproxys.dataFacade.UserTableFacadeLocal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

/**
 *
 * @author root
 */
@Stateless
@Remote
public class UserManageBean implements UserManageBeanRemote {

    @EJB
    UserTableFacadeLocal userBean;

    @Override
    public boolean Insert(User user) {

        UserTable newuser = new UserTable();
        newuser.setApellido(user.getApellido());
        newuser.setCorreo(user.getCorreo());
        newuser.setNombre(user.getNombre());
        newuser.setPassword(user.getPassword());
        newuser.setUsername(user.getUsername());
        return userBean.create(newuser);

    }

    @Override
    public List<User> getAllUsers() {
        List<UserTable> findAll = userBean.findAll();

        List<User> listUser = new ArrayList<User>();
        for (UserTable user : findAll) {
            User newuser = new User();
            newuser.setApellido(user.getApellido());
            newuser.setCorreo(user.getCorreo());
            newuser.setNombre(user.getNombre());
            newuser.setPassword(user.getPassword());
            newuser.setUsername(user.getUsername());
            listUser.add(newuser);
        }
        return listUser;
    }

    @Override
    public boolean remove(String username) {
        UserTable findbyuser = userBean.findbyUsername(username);
        if (findbyuser != null) {
            return userBean.remove(findbyuser);
        } else {
            return false;
        }
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
