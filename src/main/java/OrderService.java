import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import config.ServicesConfiguration;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import mongo.Connection;
import mongo.MongoController;
import mongo.MongoHealthCheck;
import mongo.MongoManaged;
import org.mongodb.morphia.Datastore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import resources.OrderResource;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class OrderService extends Application<ServicesConfiguration> {
    private static final Logger LOG = LoggerFactory.getLogger(OrderService.class);
    public static Datastore dataStore = null;

    public static void main(String[] args) throws Exception {
        new OrderService().run(new String[]{"server"});
    }

    @Override
    public void initialize(Bootstrap<ServicesConfiguration> bootstrap) {
    }

    @Override
    public void run(ServicesConfiguration configuration, Environment environment) throws Exception {
        MongoController mongoController = setUpMongoDbConnection(environment, configuration);
        dataStore = mongoController.getDataStore();
        environment.jersey().register(new OrderResource(dataStore));
    }

    private MongoController setUpMongoDbConnection(Environment environment, ServicesConfiguration serviceConfiguration) throws UnknownHostException {
        Connection mongoConnection = new Connection(serviceConfiguration);
        LOG.info("Using mongodb at " + mongoConnection.getHost() + ", port: " + mongoConnection.getPort() + ", db: " + mongoConnection.getDbName());
        MongoController dbController = new MongoController(mongoConnection);
        MongoManaged mongoManaged = new MongoManaged(dbController.getMongo());
        environment.lifecycle().manage(mongoManaged);
        environment.healthChecks().register("database", new MongoHealthCheck(dbController.getMongo()));
        return dbController;
    }

}
