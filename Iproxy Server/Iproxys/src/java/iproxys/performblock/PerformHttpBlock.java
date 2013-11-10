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
public class PerformHttpBlock implements PerformBlock {

    private TemporaryBlockedEntity temporaryBlockedEntity;
    private String protocolName;

    public PerformHttpBlock(TemporaryBlockedEntity temporaryBlockedEntity) {
        this.temporaryBlockedEntity = temporaryBlockedEntity;
    }

    @Override
    public void block() {
    }

    @Override
    public void unBlock() {
    }
}
