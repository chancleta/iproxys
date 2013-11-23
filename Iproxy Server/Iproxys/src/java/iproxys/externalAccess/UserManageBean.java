/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iproxys.externalAccess;

import com.iproxys.interfaces.UserManageBeanRemote;
import iproxy.client.Beans.User;
import iproxys.PersistenceData.UserTable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Remote;
import javax.ejb.Stateless;

/**
 *
 * @author root
 */
@Stateless
@Remote
public class UserManageBean implements UserManageBeanRemote {

    @Override
    public boolean Insert(User user) {

        UserTable newuser = new UserTable();
        newuser.setApellido(user.getApellido());
        newuser.setCorreo(user.getCorreo());
        newuser.setNombre(user.getNombre());
        newuser.setPassword(user.getPassword());
        newuser.setUsername(user.getUsername());
        return newuser.save();

    }

    @Override
    public List<User> getAllUsers() {
        UserTable userProvider = new UserTable();
        List<User> userList = new ArrayList<>();
        for (Object userObject : userProvider.findAll()) {
            UserTable user = (UserTable) userObject;
            User newuser = new User();
            newuser.setApellido(user.getApellido());
            newuser.setCorreo(user.getCorreo());
            newuser.setNombre(user.getNombre());
            newuser.setPassword(user.getPassword());
            newuser.setUsername(user.getUsername());
            userList.add(newuser);
        }
        return userList;
    }

    @Override
    public boolean remove(String username) {
        
        UserTable userProvider = new UserTable();
        userProvider= userProvider.findbyUsername(username);
        if (userProvider != null) {
            userProvider.delete();
            return true;
        } else {
            return false;
        }
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
