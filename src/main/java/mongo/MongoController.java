package mongo;

import com.mongodb.*;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.util.Arrays;


public class MongoController {

    private MongoClient mongoClient;
    private DB db;
    private final Morphia morphia;

    public MongoController(Connection connection, String dbUri) {
        if (connection.getHost() == null) {
            mongoClient = new MongoClient();
        } else {
            if(dbUri != null) {
                MongoClientURI uri = new MongoClientURI("mongodb://root:root@ds139425.mlab.com:39425/orderdb",
                        MongoClientOptions.builder()
                        .connectionsPerHost(600));
                mongoClient = new MongoClient(uri);
            } else {
                MongoClientOptions options = MongoClientOptions.builder()
                        .connectionsPerHost(600)
                        .build();
                mongoClient = new MongoClient(connection.getHost(), options);
            }
        }
        db = mongoClient.getDB(connection.getDbName());
        morphia = new Morphia();
    }

    public Datastore getDataStore() {
        return morphia.createDatastore(mongoClient, db.getName());
    }

    public Mongo getMongo() {
        return mongoClient;
    }

    public void mapClass(Class entityClass) {
        morphia.map(entityClass);
    }
}