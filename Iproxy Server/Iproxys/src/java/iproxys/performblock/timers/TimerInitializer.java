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
    private static PerformUnblockOnTemporaryEntities unblockOnTemporaryEntities;
    private static Timer UnblockTemporalEntitiesTimer;

    public static void initialize() {
        if (!initialized) {
            TimerInitializer.initUnblockTemporalEntitiesTimer();
            initialized = true;
        }
    }

    private static void initUnblockTemporalEntitiesTimer() {
        unblockOnTemporaryEntities = new PerformUnblockOnTemporaryEntities();
        UnblockTemporalEntitiesTimer = new Timer();
        UnblockTemporalEntitiesTimer.scheduleAtFixedRate(unblockOnTemporaryEntities, 1000, UnblockTemporalEntitiesRecurrentTime);
    }
}
