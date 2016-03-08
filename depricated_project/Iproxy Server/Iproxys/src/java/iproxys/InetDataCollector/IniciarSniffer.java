/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iproxys.InetDataCollector;

import iproxy.externalDependencies.EjecutarIPtable;
import iproxys.performblock.timers.TimerInitializer;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 *
 * @author root
 */
@Singleton
@Startup
public class IniciarSniffer {

    @PostConstruct
    private void Iniciar() {
        Sniffer instance = Sniffer.getInstance();
        instance.select();
        EjecutarIPtable.iptableEjecutar();
        TimerInitializer.initialize();
    }
}
