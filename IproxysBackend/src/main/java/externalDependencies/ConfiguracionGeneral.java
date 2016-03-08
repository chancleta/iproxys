/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package externalDependencies;

/**
 *
 * @author root
 */
public class ConfiguracionGeneral implements ConfiguracionGeneralInteface {

    
    private static String password = "elchulo";
    private static double anchoBanda = 1.5*Megabit;
    private static ConfiguracionGeneral congral = null;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    /**
     * @return the AnchoBanda
     */
    private ConfiguracionGeneral(){}
    
    public static ConfiguracionGeneral getInstance(){
        if(congral == null)
            congral = new ConfiguracionGeneral();
        return congral;
    
    }
    
    @Override
    public double getAnchoBanda() {
        return anchoBanda;
    }

    /**
     * @param aAnchoBanda the AnchoBanda to set
     */
    @Override
    public void setAnchoBanda(double aAnchoBanda) {
        anchoBanda = ((aAnchoBanda/1024)/8);
        
    }

    /**
     * @return the password
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * @param aPassword the password to set
     */
    @Override
    public void setPassword(String aPassword) {
        password = aPassword;
    }
    
    
}
