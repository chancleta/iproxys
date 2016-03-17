/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package externalDependencies;

/**
 *
 * @author root
 */
public class GeneralConfiguration {

    public static final double bit = 1;
    public static final double Kilobit = 1024*bit;
    public static final double Megabit = 1024*Kilobit;

//    private static String password = "elchulo";
    private static double availableBandwidth = 1.5*Megabit;


    public static double getAvailableBandwidth(){
        return availableBandwidth;
    }

    public static void setAvailableBandwidth(double availableBandwidth ){
        GeneralConfiguration.availableBandwidth = availableBandwidth;
    }
}
