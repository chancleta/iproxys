/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iproxys.performblock.timers;

import iproxys.PersistenceData.PermanentBlockedEntity;
import iproxys.PersistenceData.TemporaryBlockedEntity;
import iproxys.performblock.PerformHttpBlock;
import iproxys.performblock.PerformIPPortBlock;
import iproxys.performblock.PerformIpBlock;
import iproxys.performblock.PerformPortBlock;
import java.util.ArrayList;
import java.util.TimerTask;

/**
 *
 * @author Luis
 */
public class PerformPermanentEntitiesBlockUnblock extends TimerTask {

    @Override
    public void run() {
        performBlock();
    }

    private void performBlock() {

        TemporaryBlockedEntity entitiesToPermaBlock = new TemporaryBlockedEntity();
        ArrayList<TemporaryBlockedEntity> blockCandidates = entitiesToPermaBlock.findEntitiesToPermaBlock();
        for (TemporaryBlockedEntity blockedEntity : blockCandidates) {
            switch (blockedEntity.getIdentifier()) {
                case TemporaryBlockedEntity.BLOCK_IP:
                    PerformIpBlock performIpBlock = new PerformIpBlock(blockedEntity);
                    performIpBlock.block();
                    break;
                case TemporaryBlockedEntity.BLOCK_IP_AND_PORT:
                    PerformIPPortBlock performIPPortBlock = new PerformIPPortBlock(blockedEntity);
                    performIPPortBlock.block();
                    break;
                case TemporaryBlockedEntity.BLOCK_PORT:
                    PerformPortBlock performPortBlock = new PerformPortBlock(blockedEntity);
                    performPortBlock.block();
                    break;
                case TemporaryBlockedEntity.BLOCK_HTTP_DOMAIN_TO_IP:
                    PerformHttpBlock performHttpBlock = new PerformHttpBlock(blockedEntity);
                    performHttpBlock.block();
                    break;
            }
            PermanentBlockedEntity.getPermanentBlockedEntityFromTemporaryBlockedEntity(blockedEntity).save();
        }
    }

    private void performUnblock() {
        PermanentBlockedEntity alreadyBlockedProvider = new PermanentBlockedEntity();
        ArrayList<PermanentBlockedEntity> blockedEntities = alreadyBlockedProvider.findAllByAlreadyUnblocked(false);
       

        for (PermanentBlockedEntity blockedEntity : blockedEntities) {
            /*
            chekear si ha pasado una semana para asi poder desbloquear
            */
            if (true) {
                switch (blockedEntity.getIdentifier()) {
                    case TemporaryBlockedEntity.BLOCK_IP:
                        PerformIpBlock performIpBlock = new PerformIpBlock(PermanentBlockedEntity.getTemporaryEntityFromPermanentEntity(blockedEntity));
                        performIpBlock.unBlock();
                        break;
                    case TemporaryBlockedEntity.BLOCK_IP_AND_PORT:
                        PerformIPPortBlock performIPPortBlock = new PerformIPPortBlock(PermanentBlockedEntity.getTemporaryEntityFromPermanentEntity(blockedEntity));
                        performIPPortBlock.unBlock();
                        break;
                    case TemporaryBlockedEntity.BLOCK_PORT:
                        PerformPortBlock performPortBlock = new PerformPortBlock(PermanentBlockedEntity.getTemporaryEntityFromPermanentEntity(blockedEntity));
                        performPortBlock.unBlock();
                        break;
                    case TemporaryBlockedEntity.BLOCK_HTTP_DOMAIN_TO_IP:
                        PerformHttpBlock performHttpBlock = new PerformHttpBlock(PermanentBlockedEntity.getTemporaryEntityFromPermanentEntity(blockedEntity));
                        performHttpBlock.unBlock();
                        break;
                }
            }
        }
    }
}
