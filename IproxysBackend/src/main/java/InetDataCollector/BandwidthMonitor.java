package InetDataCollector;

import api.LiveMonitorController;

import java.io.IOException;
import java.util.TimerTask;

/**
 * Created by Luis Pena on 9/15/2016.
 */
public class BandwidthMonitor extends TimerTask {


    @Override
    public void run() {
        double bandwidthMonitorLastSecond = Sniffer.bandwidthMonitor / 1024;

        LiveMonitorController.sessions.stream().forEach(session -> {
            try {
                session.getRemote().sendString(String.valueOf(bandwidthMonitorLastSecond));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        Sniffer.bandwidthMonitor = 0;
    }
}
