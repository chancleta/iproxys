package iproxy.externalDependencies;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author luis
 */
public class EjecutarComando {

    private static EjecutarComando ejecutarComando = null;
    private static String[] comando = new String[3];
    
    private EjecutarComando(){}
    
    public static EjecutarComando getInstance(){
        if(ejecutarComando == null)
            ejecutarComando = new EjecutarComando();
        return ejecutarComando;
    }
    public String Ejecutar_Comando(String argv) {
        Process p = null;
        try {
            comando[0] = "/bin/bash";
            comando[1] = "-c";
            comando[2] = "echo 'elchulo' | sudo -S " + argv;
            p = Runtime.getRuntime().exec(comando);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String s;
            int count = 0;

            String result = "";
            
            while ((s = stdInput.readLine()) != null) {
                count++;
                result = result + s + "\n";
            }
            
            stdInput.close();

            p.waitFor();
            return result;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        } finally {
            p.destroy();
        }
    }
    @Override
    public String toString(){
    
        if(ejecutarComando == null)
            return "Instancia no Creada";
        else
            return "Instancia Creada";
    }
}
