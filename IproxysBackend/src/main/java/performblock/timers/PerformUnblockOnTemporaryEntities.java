/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package performblock.timers;

import PersistenceData.TemporaryBlockedEntity;
import performblock.PerformHttpBlock;
import performblock.PerformIPPortBlock;
import performblock.PerformIpBlock;
import performblock.PerformPortBlock;
import java.util.ArrayList;
import java.util.TimerTask;

/**
 *
 * @author root
 */
public class PerformUnblockOnTemporaryEntities extends TimerTask {

    @Override
    public void run() {
        TemporaryBlockedEntity blockedEntities = new TemporaryBlockedEntity();
        ArrayList<TemporaryBlockedEntity> unblockCandidates = blockedEntities.findEntityToUnblock();
        for (TemporaryBlockedEntity blockedEntity : unblockCandidates) {
            String entidadDesbloqueada = "";
            switch (blockedEntity.getIdentifier()) {

                case TemporaryBlockedEntity.BLOCK_IP:
                    PerformIpBlock performIpBlock = new PerformIpBlock(blockedEntity);
                    performIpBlock.unBlock();
                    entidadDesbloqueada += "IP " + blockedEntity.getBlockedIP();
                    break;
                case TemporaryBlockedEntity.BLOCK_IP_AND_PORT:
                    PerformIPPortBlock performIPPortBlock = new PerformIPPortBlock(blockedEntity);
                    performIPPortBlock.unBlock();
                    entidadDesbloqueada += "IP " + blockedEntity.getBlockedIP() + " PORT:" + blockedEntity.getBlockedPort() + " PROTOCOL:"+blockedEntity.getProtocol();
                    break;
                case TemporaryBlockedEntity.BLOCK_PORT:
                    PerformPortBlock performPortBlock = new PerformPortBlock(blockedEntity);
                    entidadDesbloqueada += "PORT:" + blockedEntity.getBlockedPort() + " PROTOCOL:"+blockedEntity.getProtocol();
                    performPortBlock.unBlock();
                    break;
                case TemporaryBlockedEntity.BLOCK_HTTP_DOMAIN_TO_IP:
                    PerformHttpBlock performHttpBlock = new PerformHttpBlock(blockedEntity);
                    entidadDesbloqueada += "IP " + blockedEntity.getBlockedIP() + " DOMAIN:" + blockedEntity.getBlockedDomain();
                    performHttpBlock.unBlock();
                    break;
            }
            System.out.println("Desbloqueando entidad "+entidadDesbloqueada);
        }
    }
}
