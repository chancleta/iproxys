/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iproxys.performblock;

import iproxy.externalDependencies.EjecutarComando;
import iproxys.PersistenceData.TemporaryBlockedEntity;

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
        ejecutarComando.Ejecutar_Comando("iptables -I FORWARD -s "+temporaryBlockedEntity.getBlockedIP()+" -p "+protocolName+" --dport "+temporaryBlockedEntity.getBlockedPort()+" -j DROP");
    }

    @Override
    public void unBlock() {
        ejecutarComando.Ejecutar_Comando("iptables -D FORWARD -s "+temporaryBlockedEntity.getBlockedIP()+" -p "+protocolName+" --dport "+temporaryBlockedEntity.getBlockedPort()+" -j DROP");
    }
}
