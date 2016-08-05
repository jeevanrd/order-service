package models;


import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class OrderTest {
    @Test
    public void validateModelSetting(){
        List<InputItem> items = new ArrayList<>();
        Dimensions cd = new Dimensions(15,15,15);
        items.add(new InputItem(5,5,5));
        Slot slot = new Slot(2, 2);
        Order order = new Order(slot, 2, items);

        assertThat(order.getCartonCount(), is(2));
        assertThat(order.getSlot().getDuration(), is(2));
        assertThat(order.getSlot().getStartTime(), is(2));
        assertThat(order.getItems().get(0).getBreadth(), is(5));
        assertThat(order.getItems().get(0).getHeight(), is(5));
        assertThat(order.getItems().get(0).getWidth(), is(5));
    }

    @Test
    public void shouldGiveMiniumCartonToFillAllItemsAsthree(){
        List<InputItem> items = new ArrayList<>();
        Dimensions cd = new Dimensions(15,15,15);
        for(int i=0;i<27; i++)
            items.add(new InputItem(5,5,5));
        Order order = new Order(items);
        assertThat(order.getMinimumCartonsToFillAllItems(cd), is(3));
    }

    @Test
    public void shouldGiveMiniumCartonToFillAllItemsAsTwo(){
        List<InputItem> items = new ArrayList<>();
        Dimensions cd = new Dimensions(15,15,15);
        items.add(new InputItem(10,10,10));
        items.add(new InputItem(10,10,10));
        items.add(new InputItem(5,5,10));
        items.add(new InputItem(5,5,10));
        Order order = new Order(items);
        assertThat(order.getMinimumCartonsToFillAllItems(cd), is(2));
    }

//    @Test
//    public void shouldGiveMiniumCartonToFillAllItemsAsThree(){
//        List<InputItem> items = new ArrayList<>();
//        Dimensions cd = new Dimensions(15,15,15);
//        items.add(new InputItem(10,10,10));
//        items.add(new InputItem(5,5,10));
//        items.add(new InputItem(10,10,10));
//        items.add(new InputItem(5,5,10));
//
//        Order order = new Order(items);
//        assertThat(order.getMinimumCartonsToFillAllItems(cd), is(2));
//    }

    @Test
    public void shouldSortListByVolume(){
        List<InputItem> items = new ArrayList<>();
        Dimensions cd = new Dimensions(15,15,15);
        items.add(new InputItem(5,6,7));
        items.add(new InputItem(5,5,5));
        items.add(new InputItem(7,7,7));

        Order order = new Order(items);
        List<InputItem> sortedList = order.sortListByVolDescOrder(items);

        assertThat(sortedList.get(1).getHeight(), is(5));
        assertThat(sortedList.get(1).getWidth(), is(6));
        assertThat(sortedList.get(1).getBreadth(), is(7));

        assertThat(sortedList.get(0).getHeight(), is(7));
        assertThat(sortedList.get(0).getWidth(), is(7));
        assertThat(sortedList.get(0).getBreadth(), is(7));

        assertThat(sortedList.get(2).getHeight(), is(5));
        assertThat(sortedList.get(2).getWidth(), is(5));
        assertThat(sortedList.get(2).getBreadth(), is(5));
    }
}
