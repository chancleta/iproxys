package iproxy.externalDependencies;

import java.io.IOException;
import java.io.RandomAccessFile;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author LuisjPena
 */
public class SquidController {

    private static SquidController squidcontroller = null;
    private static final String filename = "squid_float.conf";
    private static RandomAccessFile raf;
    private static EjecutarComando ejecutar = EjecutarComando.getInstance();
    private SquidController() {
    }

    public static SquidController getInstance() {
        if (squidcontroller == null && isAccessable()) {
            squidcontroller = new SquidController();
        }
        return squidcontroller;

    }
    private void reiniciarSquid(){
        
        ejecutar.Ejecutar_Comando("service squid3 stop");
        ejecutar.Ejecutar_Comando("service squid3 start");
    
    }
    private static boolean isAccessable() {

        try {
            raf = new RandomAccessFile(filename, "rw");
            return true;
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return false;
        }

    }

    public boolean addNuevoDomain(String ip, String domain) {
        try {
            String line = raf.readLine();
            String acldomain;
            String aclip;
            long pos = 0;
            while (line != null) {

                if (line.contains("#ACLDOMAIN")) {

                    acldomain = "acl " + domain + "_ACL dstdomain " + domain;

                    nuevaLinea(acldomain);
                } else if (line.contains("#ACLIP")) {


                    aclip = "acl " + domain + "_IP src " + ip;
                    nuevaLinea(aclip);

                } else if (line.contains("#HTTPACCESS")) {

                    nuevaLinea("http_access deny " + domain + "_ACL " + domain + "_IP");
                }
                line = raf.readLine();
            }
            reiniciarSquid();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        return true;

    }
    public boolean addtoExistingDomain(String ip, String domain) {
        try {
            String line = raf.readLine();
            String acldomain;
            String aclip;
            long pos = 0;
            while (line != null) {

                if (line.contains("#ACLIP")) {


                    aclip = "acl " + domain + "_IP src " + ip;
                    nuevaLinea(aclip);

                } 
                line = raf.readLine();
            }
            reiniciarSquid();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        return true;

    }

    public boolean removeDomain(String domain) {
        try {
            String read = raf.readLine();
            String newfile = "";
            while (read != null) {

                if (!read.contains(domain)) {
                    newfile += read + "\n";
                }
                read = raf.readLine();
            }
            raf.seek(0);
            raf.write(newfile.getBytes());
            raf.setLength(newfile.getBytes().length);
            reiniciarSquid();
        } catch (Exception ex) {
        }
        return true;

    }

    private boolean nuevaLinea(String lineascribir) {

        try {
            long posicion = raf.getFilePointer();
            String read = "";
            String linea = "";
            while (linea != null) {

                linea = raf.readLine();
                if (linea != null) {
                    read += linea + "\n";
                }
            }
            raf.seek(posicion);
            raf.write((lineascribir + "\n" + read).getBytes());
            raf.seek(posicion);
        } catch (Exception ex) {
            return false;
        }
        return true;


    }

}
