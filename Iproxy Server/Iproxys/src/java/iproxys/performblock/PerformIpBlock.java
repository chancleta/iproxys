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
public class PerformIpBlock implements PerformBlock {

    private TemporaryBlockedEntity temporaryBlockedEntity;
    private EjecutarComando ejecutarComando;

    public PerformIpBlock(TemporaryBlockedEntity temporaryBlockedEntity) {
        this.temporaryBlockedEntity = temporaryBlockedEntity;
        ejecutarComando = EjecutarComando.getInstance();
    }

    @Override
    public void block() {
        ejecutarComando.Ejecutar_Comando("iptables -I FORWARD -s " + temporaryBlockedEntity.getBlockedIP() + " -j DROP");
    }

    @Override
    public void unBlock() {
        ejecutarComando.Ejecutar_Comando("iptables -D FORWARD -s " + temporaryBlockedEntity.getBlockedIP() + " -j DROP");
    }
}
