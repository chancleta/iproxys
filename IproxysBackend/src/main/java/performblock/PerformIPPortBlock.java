/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package performblock;

import PersistenceData.TemporaryBlockedEntity;
import externalDependencies.EjecutarComando;

/**
 *
 * @author root
 */
public class PerformIPPortBlock implements PerformBlock {

    private TemporaryBlockedEntity temporaryBlockedEntity;
    private EjecutarComando ejecutarComando;
    private String protocolName;
    public PerformIPPortBlock(TemporaryBlockedEntity temporaryBlockedEntity) {
        this.temporaryBlockedEntity = temporaryBlockedEntity;
        protocolName = (temporaryBlockedEntity.getProtocol() == 6 )?"tcp":"udp";
        ejecutarComando = EjecutarComando.getInstance();
    }

    @Override
    public void block() {
        ejecutarComando.Ejecutar_Comando("iptables -A INPUT -s "+temporaryBlockedEntity.getBlockedIP()+" -p "+protocolName+" --dport "+temporaryBlockedEntity.getBlockedPort()+" -j REJECT");
    }

    @Override
    public void unBlock() {
        ejecutarComando.Ejecutar_Comando("iptables -D INPUT -s "+temporaryBlockedEntity.getBlockedIP()+" -p "+protocolName+" --dport "+temporaryBlockedEntity.getBlockedPort()+" -j REJECT");
    }


    public void blockSquid() {
        String results = "";
        do {
            results = ejecutarComando.Ejecutar_Comando("conntrack -D -p tcp --dport " + temporaryBlockedEntity.getBlockedPort() + " --src " + temporaryBlockedEntity.getBlockedIP() +" --dst  "+ temporaryBlockedEntity.getBlockedIPDest());
            results = results == null ? "" : results;
        }while (!results.contains("0 flow entries have been deleted"));
    }

}
