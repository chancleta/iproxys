/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iproxys.connectivity;

import com.iproxys.interfaces.ArrivalDataBeanRemote;
import com.iproxys.interfaces.LoginServiceBeanRemote;
import com.iproxys.interfaces.UnblockableManageBeanRemote;
import com.iproxys.interfaces.UserManageBeanRemote;
import java.util.Properties;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author LuisjPena
 */
public class ServerComunication {

    private static Properties props;
    private static ServerComunication sc = null;
    
    private static final String loginservice = "com.iproxys.interfaces.LoginServiceBeanRemote#com.iproxys.interfaces.LoginServiceBeanRemote";
    private static final String arrivaldata = "com.iproxys.interfaces.ArrivalDataBeanRemote#com.iproxys.interfaces.ArrivalDataBeanRemote";
    private static final String unblockableip = "com.iproxys.interfaces.UnblockableManageBeanRemote#com.iproxys.interfaces.UnblockableManageBeanRemote";
    private static final String usermanagebean = "com.iproxys.interfaces.UserManageBeanRemote#com.iproxys.interfaces.UserManageBeanRemote";

    private static ArrivalDataBeanRemote arrivalData = null;
    private static LoginServiceBeanRemote loginService = null;
    private static UnblockableManageBeanRemote unblockableIP = null;
    private static UserManageBeanRemote userManageBean = null;
    private static InitialContext ic = null;
    public static ServerComunication getInstance(){
        if(sc == null)
        {
            if(doConfigure()){
               sc = new ServerComunication();
                System.out.println("im here");
               return sc; 
            }
        }
        return sc;
    }
    
    
    private ServerComunication() {}
    
    private static boolean doConfigure() {

        props = new Properties();
        props.setProperty("java.naming.factory.initial","com.sun.enterprise.naming.SerialInitContextFactory");
        props.setProperty("java.naming.factory.url.pkgs","com.sun.enterprise.naming");
        props.setProperty("java.naming.factory.state","com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
        props.setProperty("org.omg.CORBA.ORBInitialHost", "127.0.0.1");
        
        try {
             ic = new InitialContext(props);
            return true;
        } catch (NamingException ex) {
            return false;
        }
    }
    
    public LoginServiceBeanRemote getLoginServiceBean(){
        
        if(loginService == null)
        {
            try {
                loginService = (LoginServiceBeanRemote)ic.lookup(loginservice);
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
            finally{
                return loginService;
            }
        }
        return loginService;
        
    }
    public ArrivalDataBeanRemote getArrivalDataBean(){
        
        if(arrivalData == null)
        {
            try {
                arrivalData = (ArrivalDataBeanRemote)ic.lookup(arrivaldata);
            } catch (Exception ex) {
              //  System.err.println(ex.getMessage());
            }
            finally{
                return arrivalData;
            }
        }
        return arrivalData;
        
    }
    public UnblockableManageBeanRemote UnblockableManageBean(){
        
        if(unblockableIP == null)
        {
            try {
                unblockableIP = (UnblockableManageBeanRemote)ic.lookup(unblockableip);
            } catch (Exception ex) {
              //  System.err.println(ex.getMessage());
            }
            finally{
                return unblockableIP;
            }
        }
        return unblockableIP;
        
    }
    public UserManageBeanRemote UserManageBean(){
        
        if(userManageBean == null)
        {
            try {
                userManageBean = (UserManageBeanRemote)ic.lookup(usermanagebean);
            } catch (Exception ex) {
              //  System.err.println(ex.getMessage());
            }
            finally{
                return userManageBean;
            }
        }
        return userManageBean;
        
    }
}
