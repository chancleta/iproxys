/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iproxys.performblock;

import iproxy.externalDependencies.SquidController;
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
        SquidController.addDomain(temporaryBlockedEntity);
    }

    @Override
    public void unBlock() {
        SquidController.deleteDomain(temporaryBlockedEntity);
    }
}
