package externalDependencies;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */

/**
 * @author luis
 */
public class EjecutarComando {

    private static EjecutarComando ejecutarComando = null;

    private EjecutarComando() {
    }

    public static EjecutarComando getInstance() {
        if (ejecutarComando == null)
            ejecutarComando = new EjecutarComando();
        return ejecutarComando;
    }

    public String Ejecutar_Comando(String command) {
        try {
            String[] cmd = {"/bin/bash","-c","echo Elchulo00| sudo "+command};
            Process pb = Runtime.getRuntime().exec(cmd);

            String line;
            String result = "";
            BufferedReader input = new BufferedReader(new InputStreamReader(pb.getInputStream()));
            while ((line = input.readLine()) != null) {
               result += line;
            }
            input.close();
            return result;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    @Override
    public String toString() {
        if (ejecutarComando == null)
            return "Instancia no Creada";
        else
            return "Instancia Creada";
    }
}
