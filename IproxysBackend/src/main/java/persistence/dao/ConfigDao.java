package persistence.dao;

import PersistenceData.EntityManagerProvider;
import models.Config;
import models.JsonConfig;

import javax.persistence.Query;

/**
 * Created by Luis Pena on 8/18/2016.
 */
public class ConfigDao {


    public static Config get() {
        Query query = EntityManagerProvider.getInstance().getEntityManager().createNamedQuery(Config.getAll, Config.class);

        try {
            return (Config) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Config update(JsonConfig jsonConfig) {
        Query query = EntityManagerProvider.getInstance().getEntityManager().createNamedQuery(Config.getAll, Config.class);

        try {
            Config dataconf = (Config) query.getSingleResult();
            dataconf.setBandwidth(jsonConfig.getBandwidth());
            dataconf.setMaxBandwidthPerUser(jsonConfig.getMaxBandwidthPerUser());
            dataconf.setTempTimeDuration(jsonConfig.getTempTimeDuration());
            dataconf.save();
            return dataconf;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
