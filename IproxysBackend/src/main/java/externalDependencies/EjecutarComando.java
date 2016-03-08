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
        ProcessBuilder pb;
        try {
            pb = new ProcessBuilder(command);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            BufferedReader inStreamReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            String result = "",readLine;

            while ((readLine = inStreamReader.readLine()) != null) {
                result = result + readLine + "\n";
            }

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
