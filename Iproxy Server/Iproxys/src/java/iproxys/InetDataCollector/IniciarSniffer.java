/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iproxys.InetDataCollector;

import iproxy.externalDependencies.EjecutarIPtable;
import iproxys.PersistenceData.UserTable;
import iproxys.performblock.timers.TimerInitializer;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;

/**
 *
 * @author root
 */
@Singleton
@Startup
public class IniciarSniffer {

    public static EntityManager createEntityManager = null;
//    private static EntityManager entityManager = entityFactory.createEntityManager();

    @PostConstruct
    private void Iniciar() {
        Sniffer instance = Sniffer.getInstance();
        instance.select();
        EjecutarIPtable.iptableEjecutar();
        TimerInitializer.initialize();
    }

    public EntityManager getEntityManager() {
        return IniciarSniffer.createEntityManager;
    }

    private void databaseConectivityTest() {
        UserTable u = new UserTable();
        u.setApellido("Pena");
        u.setCorreo("ljpenaurena@gmail.com");
        u.setNombre("Luis");
        u.setPassword("elchulo");
        u.setUsername("chancleta");
        u.save();
        UserTable findbyUsername = u.findbyUsername("chancleta");
        if (findbyUsername.getUsername().equals("chancleta")) {
            System.out.println("Prueba de Conectividad con la base de datos fue exitosa");
        } else {
            System.out.println("Hubo un error durante la conexion a la base de datos");

        }
    }
}
