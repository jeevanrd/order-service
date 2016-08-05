package models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

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

    public int getVolume() {
        return this.breadth * this.width * this.breadth;
    }

    public List<Integer> getDimensionsArray() {
        List<Integer> dims = new ArrayList<>();
        dims.add(this.getBreadth());
        dims.add(this.getWidth());
        dims.add(this.getHeight());
        return dims;
    }

    public void decreaseDimensions(Dimensions itemDim) {
        this.breadth = this.breadth - itemDim.getBreadth();
        this.width = this.width - itemDim.getWidth();
        this.height = this.height - itemDim.getHeight();
    }

    public void decreaseBreadthDimension(int value) {
        this.breadth = this.breadth - value;
    }

    public void decreaseWidthDimension(int value) {
        this.width = this.width - value;
    }

    public void decreaseHeightDimension(int value) {
        this.height = this.height - value;
    }


    public Boolean checkDim(Dimensions itemDim) {
        return this.width == itemDim.getWidth()
                && this.breadth == itemDim.getBreadth()
                && this.height == itemDim.getHeight();
    }
}
