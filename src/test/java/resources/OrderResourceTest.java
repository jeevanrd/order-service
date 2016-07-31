package resources;

import com.google.common.collect.ImmutableMap;
import config.AppConstants;
import io.dropwizard.configuration.ConfigurationException;
import models.InputItem;
import models.Order;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static resources.WebApplicationExceptionMatchers.*;
import static utils.Utils.asJson;

public class OrderResourceTest extends BaseResourceTest {
    private OrderResource orderResource;
    private AppConstants constantsObj;

    @Rule
    public ExpectedException should = ExpectedException.none();

    @Before
    public void setUp() throws IOException, ConfigurationException {
        AppConstants constantsObj = new AppConstants();
        int currentHour = DateTime.now().getHourOfDay();
        constantsObj.CARTON_COUNT = 2;
        constantsObj.VEHICLE_COUNT = 1;
        constantsObj.SLOT_START_TIME = currentHour;
        constantsObj.SLOT_END_TIME = currentHour + 2;
        constantsObj.SLOT_NON_AVAILABILITY = Arrays.asList();
        this.constantsObj = constantsObj;
        orderResource = new OrderResource(dataStore, constantsObj);
    }

    @After
    public void tearDown(){
        dataStore.delete(dataStore.find(Order.class));
    }

    @Test
    public void shouldGetBadRequestForOrderWithEmptyItems() throws IOException {
        should.expect(webApplicationExceptionWith(status(400),message("Please add some items to place order")));
        orderResource.createOrder(asJson(ImmutableMap.of()));
    }

    @Test
    public void shouldGetBadRequestForOrderWithInputItemsDimensionsHavingMoreThanCartonDimensions() throws IOException {
        Order order = new Order();
        order.setItems(Arrays.asList(new InputItem(40, 40, 40)));
        should.expect(webApplicationExceptionWith(status(400),message("we can't process your order based on input items")));
        orderResource.createOrder(asJson(order));
    }

    @Test
    public void shouldCreateOrder() throws IOException {
        Order order = new Order();
        order.setItems(Arrays.asList(new InputItem(5, 5, 5)));
        Response response = orderResource.createOrder(asJson(order));
        assertThat(response.getStatus(), is(200));
        HashMap map = (HashMap<String,Object>) response.getEntity();
        List<InputItem> items = (List<InputItem>) map.get("items");
        assertThat(items.get(0).getBreadth(), is(5));
        assertThat(items.get(0).getBreadth(), is(5));
    }

    @Test
    public void shouldGetNoSlotAvailableWhileCreatingOrder() throws IOException {
        Order order = new Order();
        for(int i= 0; i< constantsObj.CARTON_COUNT; i++ ) {
            order.setItems(Arrays.asList(new InputItem(5, 5, 5)));
            Response response = orderResource.createOrder(asJson(order));
            assertThat(response.getStatus(), is(200));
        }

        order.setItems(Arrays.asList(new InputItem(5, 5, 5)));
        should.expect(webApplicationExceptionWith(status(412),message("No slot available")));
        orderResource.createOrder(asJson(order));
    }


    @Test
    public void shouldGetEmptyOrders() throws IOException {
        Response response = orderResource.getOrders();
        List<Map<String,Object>> orders = (List<Map<String, Object>>) response.getEntity();
        assertThat(orders.size(), is(0));
    }

    @Test
    public void shouldGetNonEmptyOrders() throws IOException {
        Order order = new Order();
        order.setItems(Arrays.asList(new InputItem(5, 5, 5)));
        Response response = orderResource.createOrder(asJson(order));
        response = orderResource.getOrders();
        List<Map<String,Object>> orders = (List<Map<String, Object>>) response.getEntity();
        assertThat(orders.size(), is(1));
    }
}