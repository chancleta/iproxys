/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package performblock.timers;

import PersistenceData.PermanentBlockedEntity;
import PersistenceData.TemporaryBlockedEntity;
import performblock.PerformHttpBlock;
import performblock.PerformIPPortBlock;
import performblock.PerformIpBlock;
import performblock.PerformPortBlock;
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
        performUnblock();
    }

    private void performBlock() {

        TemporaryBlockedEntity entitiesToPermaBlock = new TemporaryBlockedEntity();
        ArrayList<TemporaryBlockedEntity> blockCandidates = entitiesToPermaBlock.findEntitiesToPermaBlock();
        for (TemporaryBlockedEntity blockedEntity : blockCandidates) {
            String entidadDesbloqueada = "";

            switch (blockedEntity.getIdentifier()) {
                case TemporaryBlockedEntity.BLOCK_IP:
                    PerformIpBlock performIpBlock = new PerformIpBlock(blockedEntity);
                    performIpBlock.block();
                    entidadDesbloqueada += "IP " + blockedEntity.getBlockedIP();
                    break;
                case TemporaryBlockedEntity.BLOCK_IP_AND_PORT:
                    PerformIPPortBlock performIPPortBlock = new PerformIPPortBlock(blockedEntity);
                    performIPPortBlock.block();
                    entidadDesbloqueada += "IP " + blockedEntity.getBlockedIP() + " PORT:" + blockedEntity.getBlockedPort() + " PROTOCOL:" + blockedEntity.getProtocol();
                    break;
                case TemporaryBlockedEntity.BLOCK_PORT:
                    PerformPortBlock performPortBlock = new PerformPortBlock(blockedEntity);
                    performPortBlock.block();
                    entidadDesbloqueada += "PORT:" + blockedEntity.getBlockedPort() + " PROTOCOL:" + blockedEntity.getProtocol();
                    break;
                case TemporaryBlockedEntity.BLOCK_HTTP_DOMAIN_TO_IP:
                    PerformHttpBlock performHttpBlock = new PerformHttpBlock(blockedEntity);
                    performHttpBlock.block();
                    entidadDesbloqueada += "IP " + blockedEntity.getBlockedIP() + " DOMAIN:" + blockedEntity.getBlockedDomain();
                    break;
            }
            PermanentBlockedEntity.getPermanentBlockedEntityFromTemporaryBlockedEntity(blockedEntity).save();
            System.out.println("Bloqueando entidad permanentemente" + entidadDesbloqueada);

        }
    }

    private void performUnblock() {
        PermanentBlockedEntity alreadyBlockedProvider = new PermanentBlockedEntity();
        ArrayList<PermanentBlockedEntity> blockedEntities = alreadyBlockedProvider.findAllByAlreadyUnblocked(false);


        for (PermanentBlockedEntity blockedEntity : blockedEntities) {
            /*
             chequear si ha pasado una semana para asi poder desbloquear
             */
            boolean hasBeenBlockedForAWeek = (System.currentTimeMillis() - blockedEntity.getBlockedOnTimeDate().getTime()) >= 7 * TemporaryBlockedEntity.DAY_IN_MS;
            if (hasBeenBlockedForAWeek) {
                String entidadDesbloqueada = "";

                switch (blockedEntity.getIdentifier()) {
                    case TemporaryBlockedEntity.BLOCK_IP:
                        PerformIpBlock performIpBlock = new PerformIpBlock(PermanentBlockedEntity.getTemporaryEntityFromPermanentEntity(blockedEntity));
                        entidadDesbloqueada += "IP " + blockedEntity.getBlockedIP();

                        performIpBlock.unBlock();
                        break;
                    case TemporaryBlockedEntity.BLOCK_IP_AND_PORT:
                        PerformIPPortBlock performIPPortBlock = new PerformIPPortBlock(PermanentBlockedEntity.getTemporaryEntityFromPermanentEntity(blockedEntity));
                        performIPPortBlock.unBlock();
                        entidadDesbloqueada += "IP " + blockedEntity.getBlockedIP() + " PORT:" + blockedEntity.getBlockedPort() + " PROTOCOL:" + blockedEntity.getProtocol();

                        break;
                    case TemporaryBlockedEntity.BLOCK_PORT:
                        PerformPortBlock performPortBlock = new PerformPortBlock(PermanentBlockedEntity.getTemporaryEntityFromPermanentEntity(blockedEntity));
                        entidadDesbloqueada += "PORT:" + blockedEntity.getBlockedPort() + " PROTOCOL:" + blockedEntity.getProtocol();

                        performPortBlock.unBlock();

                        break;
                    case TemporaryBlockedEntity.BLOCK_HTTP_DOMAIN_TO_IP:
                        PerformHttpBlock performHttpBlock = new PerformHttpBlock(PermanentBlockedEntity.getTemporaryEntityFromPermanentEntity(blockedEntity));
                        entidadDesbloqueada += "IP " + blockedEntity.getBlockedIP() + " DOMAIN:" + blockedEntity.getBlockedDomain();

                        performHttpBlock.unBlock();
                        break;
                }
                blockedEntity.setAlreadyUnblocked(true);
                blockedEntity.update();
                System.out.println("Desbloqueando entidad que fue bloqueada permanentemente" + entidadDesbloqueada);

            }
        }
    }
}
