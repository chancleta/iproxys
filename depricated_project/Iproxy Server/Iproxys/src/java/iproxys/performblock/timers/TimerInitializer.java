/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iproxys.performblock.timers;

import java.util.Timer;

/**
 *
 * @author root
 */
public class TimerInitializer {

    private static boolean initialized = false;
    private static final int MIN_IN_MS = 60*1000;
    private static final int UnblockTemporalEntitiesRecurrentTime = 10*MIN_IN_MS;
    private static final int blockPermanentEntitiesRecurrentTime = TimerInitializer.UnblockTemporalEntitiesRecurrentTime;
    private static PerformUnblockOnTemporaryEntities unblockOnTemporaryEntities;
    private static PerformPermanentEntitiesBlockUnblock performPermaBlock;
    private static Timer UnblockTemporalEntitiesTimer;
    private static Timer blockPermanentEntitiesTimer;
    public static void initialize() {
        if (!initialized) {
            TimerInitializer.initUnblockTemporalEntitiesTimer();
            TimerInitializer.initBlockPermanentEntitiesTimer();
            initialized = true;
        }
    }
    private static void initBlockPermanentEntitiesTimer(){
        performPermaBlock = new PerformPermanentEntitiesBlockUnblock();
        blockPermanentEntitiesTimer = new Timer();
        blockPermanentEntitiesTimer.schedule(performPermaBlock, 1000,blockPermanentEntitiesRecurrentTime );
    
    }
    private static void initUnblockTemporalEntitiesTimer() {
        unblockOnTemporaryEntities = new PerformUnblockOnTemporaryEntities();
        UnblockTemporalEntitiesTimer = new Timer();
        UnblockTemporalEntitiesTimer.scheduleAtFixedRate(unblockOnTemporaryEntities, 1000, UnblockTemporalEntitiesRecurrentTime);
    }
}
