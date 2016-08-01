package resources;

import com.fasterxml.jackson.core.type.TypeReference;
import config.AppConstants;
import dao.OrderDao;
import models.*;
import org.mongodb.morphia.Datastore;
import utils.OperationStatus;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static utils.Utils.asJson;
import static utils.Utils.fromJson;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
public class OrderResource {
    public OrderDao orderDao;
    public SlotVehicle vehicle;
    public List<Slot> slots;
    public AppConstants appConstants;


    public OrderResource(Datastore datastore, AppConstants constantsobj) {
        appConstants = constantsobj;
        Dimensions cartonDimensions = new Dimensions(constantsobj.CARTON_HEIGHT, constantsobj.CARTON_WIDTH, constantsobj.CARTON_BREADTH);
        this.vehicle = new SlotVehicle(constantsobj.VEHICLE_COUNT, constantsobj.CARTON_COUNT, cartonDimensions);
        this.slots = new SlotSchedule(constantsobj.SLOT_START_TIME, constantsobj.SLOT_END_TIME, constantsobj.SLOT_DURATION, constantsobj.SLOT_NON_AVAILABILITY).getAvailableSlots();
        this.orderDao = new OrderDao(datastore);
    }

    @POST
    public Response createOrder(String payload) throws IOException {
        HashMap map = fromJson(payload, HashMap.class);
        List<InputItem> items = fromJson(asJson(map.get("items")), new TypeReference<List<InputItem>>() {});
        if(items == null || items.size() == 0) {
            throwResponseWithStatus(Response.Status.BAD_REQUEST, "Please add some items to place order");
        }

        if(this.slots.size() == 0)
            throwResponseWithStatus(Response.Status.PRECONDITION_FAILED, "No slot available");

        int maxCartonCount = vehicle.getMaxCartonCount();
        Boolean created = false;
        Dimensions cd = vehicle.getCartonDimensions();
        Order newOrder = null;

        //Check the carton dimensions && item dimensions
        for(InputItem item : items) {
            if(!(item.getBreadth() <= cd.getBreadth() && item.getHeight() <= cd.getHeight() && item.getWidth() <= cd.getWidth())) {
                throwResponseWithStatus(Response.Status.BAD_REQUEST, "we can't process your order based on input items");
            }
        }

        for(Slot slot : this.slots) {
            int usedCartons = 0;
            List<Order> orders = this.orderDao.createQuery().field("slot.startTime").equal(slot.getStartTime()).asList();
            for(Order order : orders)
                usedCartons += order.getCartonCount();

            int remaining = maxCartonCount - usedCartons;

            if(remaining > 0 && remaining >= items.size()) {
                newOrder = new Order(slot, items.size(), items);
                this.orderDao.save(newOrder);
                created = true;
                break;
            }
        }

        if(!created)
            throwResponseWithStatus(Response.Status.PRECONDITION_FAILED, "No slot available");

        HashMap<String,Object> output = new HashMap<>();
        output.put("orderId", newOrder.getId());
        output.put("items", newOrder.getItems());
        Slot orderSlot = newOrder.getSlot();
        output.put("slot", orderSlot.getStartTime()  + "-" + (orderSlot.getDuration() + orderSlot.getStartTime()) + " schedule");

        return Response.ok().entity(output).build();
    }


    @GET
    public Response getOrders() throws IOException {
        List<Order> orders = this.orderDao.createQuery().asList();
        List<Map<String, Object>> outputs = new ArrayList<>();
        for(Order order : orders) {
            HashMap<String,Object> output = new HashMap<>();
            output.put("orderId", order.getId().toString());
            output.put("items", order.getItems());
            Slot orderSlot = order.getSlot();
            output.put("slot", orderSlot.getStartTime()  + "-" + (orderSlot.getDuration() + orderSlot.getStartTime()) + " schedule");
            outputs.add(output);
        }
        return Response.ok().entity(outputs).build();
    }

    public static void throwResponseWithStatus(Response.Status status, String message) {
        OperationStatus opStatus = new OperationStatus(status.toString(), message);
        throw new WebApplicationException(Response.status(status).entity(opStatus).build()) {
            @Override
            public String toString() {
                OperationStatus operationStatus = (OperationStatus) getResponse().getEntity();
                return String.format("WebApplicationException(status=%s, message=%s)", operationStatus.getStatusCode(), operationStatus.getStatusMessage());
            }
        };
    }

}
