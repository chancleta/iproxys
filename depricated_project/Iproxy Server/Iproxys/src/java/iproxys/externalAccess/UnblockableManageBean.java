/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iproxys.externalAccess;

import com.iproxys.interfaces.UnblockableManageBeanRemote;
import iproxy.client.Beans.UnblockableBean;
import iproxy.externalDependencies.EjecutarComando;
import iproxys.PersistenceData.UnblockableEntity;
import java.util.Date;
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
    public boolean Insert(UnblockableBean unblockableBean) {
        UnblockableEntity unblockableProvider = new UnblockableEntity();
        unblockableProvider.setBlockedIP(unblockableBean.getBlockedIP());
        unblockableProvider.setBlockedPort(unblockableBean.getBlockedPort());
        unblockableProvider.setInsertedOnDatee(new Date());
        unblockableProvider.setProtocol(unblockableBean.getProtocol());
        unblockableProvider.setIdentifier(unblockableBean.getIdentifier());
        return unblockableProvider.save();
    }

    @Override
    public List<UnblockableBean> getAllUnblockableEntities() {
        UnblockableEntity unblockableProvider = new UnblockableEntity();
        List<UnblockableBean> unblockableBeans = unblockableProvider.getAllUnblockableEntities();
        return unblockableBeans;
    }

    @Override
    public boolean remove(UnblockableBean unblockableBean) {
        UnblockableEntity unblockableProvider = new UnblockableEntity();
        UnblockableEntity unblockableEntity = (UnblockableEntity) unblockableProvider.finbByID(unblockableBean.getId());
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
