package resources;

import dao.OrderDao;
import models.Order;
import mongo.Connection;
import mongo.MongoController;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.mongodb.morphia.Datastore;

import java.io.IOException;

@Ignore
public class BaseResourceTest {
    public static final int TEST_MONGO_PORT = 27017;
    public static final String TEST_MONGO_HOST = "127.0.0.1";
    protected static Datastore dataStore;
    private static String dbName = "orderDb_test";
    protected static OrderDao orderDao;

    @BeforeClass
    public static void initDb() throws IOException {
        dataStore = new MongoController(new Connection(dbName, TEST_MONGO_HOST, TEST_MONGO_PORT), null).getDataStore();
        orderDao = new OrderDao(dataStore);
    }

    @AfterClass
    public static void destroyResource() throws IOException {
        dataStore.delete(dataStore.find(Order.class));
    }
}
