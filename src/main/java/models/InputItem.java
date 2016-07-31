package models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InputItem extends Dimensions {
    public InputItem(){}

    public InputItem(int height, int width, int breadth) {
        super(height, width, breadth);
    }
}
