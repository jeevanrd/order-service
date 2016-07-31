package models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Dimensions {
    @JsonProperty
    private int height;

    @JsonProperty
    private int width;

    @JsonProperty
    private int breadth;

    public Dimensions() {}

    public Dimensions(int height, int width, int breadth) {
        this.height = height;
        this.width = width;
        this.breadth = breadth;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getBreadth() {
        return breadth;
    }

    public void setBreadth(int breadth) {
        this.breadth = breadth;
    }
}
