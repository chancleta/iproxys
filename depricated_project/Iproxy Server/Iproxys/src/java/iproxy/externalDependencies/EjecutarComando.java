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
    
    private EjecutarComando(){}
    
    public static EjecutarComando getInstance(){
        if(ejecutarComando == null)
            ejecutarComando = new EjecutarComando();
        return ejecutarComando;
    }
    public String Ejecutar_Comando(String command) {
        Process p = null;
        try {
            p = Runtime.getRuntime().exec(command);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String result = "",s;
            while ((s = stdInput.readLine()) != null) {
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
