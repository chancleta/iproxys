/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iproxys.performblock.timers;

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
public class PerformPermaBlock extends TimerTask{

    @Override
    public void run() {
        TemporaryBlockedEntity entitiesToPermaBlock = new TemporaryBlockedEntity();
        ArrayList<TemporaryBlockedEntity> blockCandidates = entitiesToPermaBlock.findEntitiesToPermaBlock();
        for (TemporaryBlockedEntity blockedEntity : blockCandidates) {
            switch (blockedEntity.getIdentifier()) {

                case TemporaryBlockedEntity.BLOCK_IP:
                    PerformIpBlock performIpBlock = new PerformIpBlock(blockedEntity);
                    performIpBlock.block();
                    //crear una entrada en la tabla de bloqueados permanentemente
                    break;
                case TemporaryBlockedEntity.BLOCK_IP_AND_PORT:
                    PerformIPPortBlock performIPPortBlock = new PerformIPPortBlock(blockedEntity);
                    performIPPortBlock.block();
                    //crear una entrada en la tabla de bloqueados permanentemente

                    break;
                case TemporaryBlockedEntity.BLOCK_PORT:
                   PerformPortBlock performPortBlock = new PerformPortBlock(blockedEntity);
                   performPortBlock.block();
                    //crear una entrada en la tabla de bloqueados permanentemente

                    break;
                case TemporaryBlockedEntity.BLOCK_HTTP_DOMAIN_TO_IP:
                    PerformHttpBlock performHttpBlock = new PerformHttpBlock(blockedEntity);
                    performHttpBlock.block();
                    //crear una entrada en la tabla de bloqueados permanentemente

                    break;
            }
        }
    }
    
}
