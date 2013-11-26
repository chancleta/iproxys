/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iproxys.externalAccess;

import com.iproxys.interfaces.UnblockableManageBeanRemote;
import iproxy.client.Beans.UnblockableBean;
import iproxy.externalDependencies.EjecutarComando;
import iproxys.PersistenceData.UnblockableEntity;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Remote;
import javax.ejb.Stateless;

/**
 *
 * @author root
 */
@Stateless
@Remote(UnblockableManageBeanRemote.class)
public class UnblockableManageBean implements UnblockableManageBeanRemote {

    @Override
    public boolean Insert(String ip) {
        return true;


    }

    @Override
    public List<String> getAllIP() {
        UnblockableEntity unblockableProvider = new UnblockableEntity();
        List<UnblockableBean> unblockableBeans = unblockableProvider.getAllUnblockableEntities();
//        return UnblockableBeans;
        return new ArrayList<>();
    }

    @Override
    public boolean remove(String ip) {
        UnblockableBean unblockableBeanEntity = null;
        UnblockableEntity unblockableProvider = new UnblockableEntity();
        UnblockableEntity unblockableEntity = (UnblockableEntity) unblockableProvider.finbByID(unblockableBeanEntity.getId());
        unblockableEntity.delete();
        return true;
    }

    @Override
    public void setSystemPassword(String password) {
    }

    @Override
    public String getSystemPassword() {



        return "";
    }

    @Override
    public String getIptables() {

        EjecutarComando ejecutar = EjecutarComando.getInstance();
        return ejecutar.Ejecutar_Comando("iptables -L -v");
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
