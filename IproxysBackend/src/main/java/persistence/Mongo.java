package persistence;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luis Pena on 8/18/2016.
 */
public final class Mongo {

    private static Morphia morphia = new Morphia();
    private static Datastore datastore;

    private Mongo(){}

    public static Datastore getDataStore(){

        if(datastore == null)
            initMongoDB();
        return datastore;
    }
    private static void initMongoDB() {

        morphia.mapPackage("org.mongodb.morphia.example");

        // TODO: Username, Password, URL, DBName and Port to a config file and load that data based on environment
        ServerAddress mongoDBServer = new ServerAddress("127.0.0.1",27017);
        List<MongoCredential> credentialsList = new ArrayList<>();
        credentialsList.add(MongoCredential.createCredential("activacionesRD","activacionesRD","activacionesRD".toCharArray()));

        datastore = morphia.createDatastore(new MongoClient(mongoDBServer, credentialsList),"activacionesRD");
        datastore.ensureIndexes();
    }
}
