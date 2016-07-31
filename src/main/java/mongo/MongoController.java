package mongo;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;


public class MongoController {

    private MongoClient mongoClient;
    private DB db;
    private final Morphia morphia;

    public MongoController(Connection connection) {
        if (connection.getHost() == null) {
            mongoClient = new MongoClient();
        } else {
            MongoClientOptions options = MongoClientOptions.builder()
                    .connectionsPerHost(600)
                    .build();
            mongoClient = new MongoClient(connection.getHost(), options);
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