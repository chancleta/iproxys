package iproxy.externalDependencies;

import iproxys.PersistenceData.TemporaryBlockedEntity;
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
    private static final String filename = "/home/luis/iproxys/Iproxy Server/Iproxys/squid_float.conf";
    private static RandomAccessFile raf;
    private static EjecutarComando ejecutar = EjecutarComando.getInstance();

    public SquidController() {
    }

    private static void reiniciarSquid() {

        ejecutar.Ejecutar_Comando("service squid3 stop");
        ejecutar.Ejecutar_Comando("service squid3 start");

    }

    private static boolean openSquidConfFile() {

        try {
            raf = new RandomAccessFile(filename, "rw");
            return true;
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return false;
        }

    }

    public static boolean addDomain(TemporaryBlockedEntity temporaryBlockedEntity) {
        openSquidConfFile();
        try {
            String domain = temporaryBlockedEntity.getBlockedDomain();
            String ipClient = temporaryBlockedEntity.getBlockedIP();
            String aclIPName = temporaryBlockedEntity.getBlockedOnTimeDate().toString().replaceAll(" |:", "");
            String aclDomainName = aclIPName + "_domain";

            raf.seek(raf.length());
            raf.writeBytes("\n\nacl " + aclIPName + " src " + ipClient);
            raf.writeBytes("\nacl " + aclDomainName + " dstdomain " + domain);
            raf.writeBytes("\ndelay_access 1 allow " + aclIPName + " " + aclDomainName);
            reiniciarSquid();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        return true;

    }

    public static boolean deleteDomain(TemporaryBlockedEntity temporaryBlockedEntity) {
        openSquidConfFile();
        try {

            String aclIPName = temporaryBlockedEntity.getBlockedOnTimeDate().toString().replaceAll(" |:", "");
            raf.seek(0);
            String read = raf.readLine();
            String newFileContent = "";
            while (read != null) {

                if (!read.contains(aclIPName)) {
                    newFileContent += read + "\n";
                }
                read = raf.readLine();
            }
            raf.seek(0);
            raf.write(newFileContent.getBytes());
            raf.setLength(newFileContent.getBytes().length);
            reiniciarSquid();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        return true;
    }
}
