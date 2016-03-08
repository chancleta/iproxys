/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iproxy.client;

import iproxy.client.gui.Login;
import iproxy.client.gui.mainFrame;

/**
 *
 * @author LuisjPena
 */
public class IproxyClient {

    /**
     * @param args the command line arguments
     */
    private static mainFrame main = null;
    private static Login login = null;

    public static void main(String[] args) {

       showLogin();
    }

    public static void showMainFrame() {
        if (main == null) {
            main = new mainFrame();
        }
        main.setVisible(true);
    }

    public static void hideMainFrame() {
        if (main != null) {
            main.setVisible(false);
        }
        
    }

    public static void showLogin() {
        if (login == null) {
            login = new Login();
        }
        login.setVisible(true);
    }

    public static void hideLogin() {
        if (login == null) {
            login = new Login();
        }
        login.setVisible(false);
    }

    public static void destroyLogin() {
        if (login != null) {
            login.dispose();
            login = null;
        }
    }
    public static void destroymainFrame() {
        if (main != null) {
            main.dispose();
            main = null;
        }
    }
}
