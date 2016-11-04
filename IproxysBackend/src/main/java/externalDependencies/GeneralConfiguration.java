/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package externalDependencies;

import persistence.dao.ConfigDao;

/**
 *
 * @author root
 */
public class GeneralConfiguration {

    public static final double bit = 1;
    public static final double Kilobit = 1000*bit;
    public static final double Megabit = 1000*Kilobit;

//    private static String password = "elchulo";
    private static double availableBandwidth = 1.5*Megabit;
    private static double maxBandWidthPerUser = 500*Kilobit;


    public static double getAvailableBandwidth(){
        return ConfigDao.get().getBandwidth().getBandwidth() *  ConfigDao.get().getBandwidth().getBandwidthScale().getActualValue();
    }

    public static double getMaxBandWidthPerUser() {
        return ConfigDao.get().getMaxBandwidthPerUser().getBandwidth() *  ConfigDao.get().getMaxBandwidthPerUser().getBandwidthScale().getActualValue();
    }


    public static double getTempTimeDuration() {
        return ConfigDao.get().getTempTimeDuration();
    }

}
