/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iproxy.client.gui;

import java.awt.Component;
import javax.swing.JOptionPane;

/**
 *
 * @author LuisjPena
 */
public class ShowMessages {

    private static ShowMessages showMessage = null;

    private ShowMessages() {
    }

    
    public static void ShowErrorMessage(String error, String titulo, Component comp) {
        JOptionPane.showMessageDialog(comp,
                error,
                titulo,
                JOptionPane.ERROR_MESSAGE);

    }
    public static void ShowSucessMessage(String sucess, String titulo, Component comp) {
        JOptionPane.showMessageDialog(comp,
                sucess,
                titulo,
                JOptionPane.INFORMATION_MESSAGE);

    }
}
