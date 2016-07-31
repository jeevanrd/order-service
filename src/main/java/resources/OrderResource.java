package resources;

import com.fasterxml.jackson.core.type.TypeReference;
import dao.OrderDao;
import models.*;
import org.mongodb.morphia.Datastore;
import utils.OperationStatus;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static utils.Utils.asJson;
import static utils.Utils.fromJson;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
public class OrderResource {
    public OrderDao orderDao;
    public int VEHICLE_COUNT = 4;
    public int CARTON_COUNT = 20;
    public int CARTON_HEIGHT = 15;
    public int CARTON_WIDTH = 30;
    public int CARTON_BREADTH = 15;
    public int SLOT_START_TIME = 9;
    public int SLOT_END_TIME = 22;
    public int SLOT_DURATION = 2;
    public List<Integer> SLOT_NON_AVAILABILITY = Arrays.asList(13);
    public SlotVehicle vehicle;
    public List<Slot> slots;


    public OrderResource(Datastore datastore) {
        Dimensions cartonDimensions = new Dimensions(CARTON_HEIGHT, CARTON_WIDTH, CARTON_BREADTH);
        this.vehicle = new SlotVehicle(VEHICLE_COUNT, CARTON_COUNT, cartonDimensions);
        this.slots = new SlotSchedule(SLOT_START_TIME, SLOT_END_TIME, SLOT_DURATION, SLOT_NON_AVAILABILITY).getSlots();
        this.orderDao = new OrderDao(datastore);
    }

    @POST
    public Response createOrder(String payload) throws IOException {
        HashMap map = fromJson(payload, HashMap.class);
        List<InputItem> items = fromJson(asJson(map.get("items")), new TypeReference<List<InputItem>>() {});
        if(items.size() == 0) {
            throwResponseWithStatus(Response.Status.BAD_REQUEST, "Please add some items to place order");
        }

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

        return Response.ok().build();
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
