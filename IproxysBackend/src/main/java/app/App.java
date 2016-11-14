package app;

import PersistenceData.User;
import api.*;
import api.common.OAuthController;
import models.Bandwidth;
import models.BandwidthScale;
import models.Config;
import models.UserRoles;
import persistence.dao.ConfigDao;
import persistence.dao.UserDao;
import services.UserService;
import services.common.AuthorizationService;
import spark.Spark;

import static spark.Spark.webSocket;

/**
 * Created by lupena on 2/5/2016.   wsx
 */
public class App {

    public static void main(String[] args) throws Exception {

        Spark.port(4000);
        Spark.staticFileLocation("/public");

        webSocket("/liveMonitor", LiveMonitorController.class);
        webSocket("/live-actions-socket", LiveActionsWebSocketController.class);


//        User u = new User();
//        u.setUsername("chan");
//        u.setRole(UserRoles.SellingPoint);
//        u.setPassword("123456");
//        u.setEmail("chan@chan.com");
//        u.setFirstName("LP");
//        u.setLastName("PL");
//        u.setCreatedBy(new ObjectId("57bd01dbef008d5ff4873a70"));
//        Mongo.getDataStore().save(u);


        if (ConfigDao.get() == null) {
            Config conf = new Config();
            Bandwidth bw = new Bandwidth();
            bw.setBandwidth(5);
            bw.setBandwidthScale(BandwidthScale.MegaBit);
            conf.setBandwidth(bw);

            Bandwidth bw1 = new Bandwidth();

            bw1.setBandwidth(500);
            bw1.setBandwidthScale(BandwidthScale.KiloBit);
            conf.setMaxBandwidthPerUser(bw1);

            conf.setTempTimeDuration(3);
            conf.save();
        }

        if(UserDao.findUserByCredentials("chancleta","123456") == null){
            User us = new User();
            us.setUsername("chancleta");
            us.setEmail("ljpenaurena@gmail.com");
            us.setPassword("123456");
            us.setLastName("Pena");
            us.setFirstName("Luis");
            us.setRole(UserRoles.Admin);
            us.save();
        }

//
//
//        Config c = ConfigDao.get();
//        System.out.println(c.getBandwidth());
        Cors.enableCORS();

        new OAuthController(AuthorizationService.getInstance());
        new UserController(UserService.getInstance());
        new ConfigController();
        new LiveActionsController();
        new ResourceAllowanceController();

        AuthorizationFilters.setFilters();
//        Sniffer.getInstance().select();

//        EjecutarIPtable.iptableEjecutar();
//        TimerInitializer.initialize();
    }


}

