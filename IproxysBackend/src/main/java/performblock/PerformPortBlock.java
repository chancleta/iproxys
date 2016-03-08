/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package performblock;

import externalDependencies.EjecutarComando;
import PersistenceData.TemporaryBlockedEntity;

/**
 *
 * @author root
 */
public class PerformPortBlock implements PerformBlock {

    private TemporaryBlockedEntity temporaryBlockedEntity;
    private EjecutarComando ejecutarComando;
    private String protocolName;
    public PerformPortBlock(TemporaryBlockedEntity temporaryBlockedEntity) {
        this.temporaryBlockedEntity = temporaryBlockedEntity;
        ejecutarComando = EjecutarComando.getInstance();
        protocolName = (temporaryBlockedEntity.getProtocol() == 6 )?"tcp":"udp";

    }

    @Override
    public void block() {
        ejecutarComando.Ejecutar_Comando("iptables -I FORWARD -p "+protocolName+" --dport "+temporaryBlockedEntity.getBlockedPort()+" -j DROP");
    }

    @Override
    public void unBlock() {
        ejecutarComando.Ejecutar_Comando("iptables -D FORWARD -p "+protocolName+" --dport "+temporaryBlockedEntity.getBlockedPort()+" -j DROP");
    }
    
}
