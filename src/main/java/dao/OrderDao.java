package dao;

import models.Order;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

public class OrderDao extends BasicDAO<Order, ObjectId> {
    public OrderDao(Datastore ds) {
        super(ds);
    }
}
