package app;

import InetDataCollector.Sniffer;
import api.ConfigController;
import api.LiveMonitorController;
import api.UserController;
import api.common.OAuthController;
import models.Config;
import persistence.dao.ConfigDao;
import services.UserService;
import services.common.AuthorizationService;
import spark.Spark;

import static spark.Spark.webSocket;

/**
 * Created by lupena on 2/5/2016.
 */
public class App {

    public static void main(String[] args) throws Exception {

        Spark.port(4000);

        webSocket("/liveMonitor", LiveMonitorController.class);
//        Spark.staticFileLocation("/public");

//        User u = new User();
//        u.setUsername("chan");
//        u.setRole(UserRoles.SellingPoint);
//        u.setPassword("123456");
//        u.setEmail("chan@chan.com");
//        u.setFirstName("LP");
//        u.setLastName("PL");
//        u.setCreatedBy(new ObjectId("57bd01dbef008d5ff4873a70"));
//        Mongo.getDataStore().save(u);

        /**
         Config conf = new Config();
         Bandwidth bw = new Bandwidth();
         bw.setBandwidth(50.01);
         bw.setBandwidthScale(BandwidthScale.KiloBit);
         conf.setBandwidth(bw);

         conf.save();
         **/


        Config c = ConfigDao.get();
        System.out.println(c.getBandwidth());
        Cors.enableCORS();

        new OAuthController(AuthorizationService.getInstance());
        new UserController(UserService.getInstance());
        new ConfigController();

        AuthorizationFilters.setFilters();
        Sniffer.getInstance().select();
    }


}

