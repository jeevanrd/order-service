package models;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CartonFitter {
    private Dimensions cartonDimensions;

    private Dimensions breadthWiseDimensions;

    private Dimensions widthWiseDimensions;

    private Dimensions heightWiseDimensions;

    public CartonFitter() {}

    public CartonFitter(Dimensions cDims) {
        this.cartonDimensions = new Dimensions(cDims.getBreadth(), cDims.getWidth(), cDims.getHeight());
        this.breadthWiseDimensions = new Dimensions(cDims.getBreadth(), cDims.getWidth(), cDims.getHeight());
        this.widthWiseDimensions = new Dimensions(cDims.getBreadth(), cDims.getWidth(), cDims.getHeight());
        this.heightWiseDimensions = new Dimensions(cDims.getBreadth(), cDims.getWidth(), cDims.getHeight());
    }

    public Dimensions getBreadthWiseDimensions() {
        return breadthWiseDimensions;
    }

    public void setBreadthWiseDimensions(Dimensions breadthWiseDimensions) {
        this.breadthWiseDimensions = breadthWiseDimensions;
    }

    public Dimensions getWidthWiseDimensions() {
        return widthWiseDimensions;
    }

    public void setWidthWiseDimensions(Dimensions widthWiseDimensions) {
        this.widthWiseDimensions = widthWiseDimensions;
    }

    public Dimensions getHeightWiseDimensions() {
        return heightWiseDimensions;
    }

    public void setHeightWiseDimensions(Dimensions heightWiseDimensions) {
        this.heightWiseDimensions = heightWiseDimensions;
    }

    public Dimensions getCartonDimensions() {
        return cartonDimensions;
    }

    public void setCartonDimensions(Dimensions cartonDimensions) {
        this.cartonDimensions = cartonDimensions;
    }

    public Boolean checkItemFitInAlongDimensionWise(List<Integer> itemDimArray, Dimensions dDimensions) {

        List<Integer> cartonDimArray = dDimensions.getDimensionsArray();

//        if(!(Collections.max(itemDimArray) <= Collections.max(cartonDimArray)))
//            return false;

        return itemDimArray.get(0) <= cartonDimArray.get(0)
                && itemDimArray.get(1) <= cartonDimArray.get(1)
                && itemDimArray.get(2) <= cartonDimArray.get(2);
    }

    public boolean checkItemFitInAlongLengthDim(Dimensions itemDimensions) {
        return checkItemFitInAlongDimensionWise(itemDimensions.getDimensionsArray(), this.getBreadthWiseDimensions());
    }

    public boolean checkItemFitInAlongWidthDim(Dimensions itemDimensions) {
        return checkItemFitInAlongDimensionWise(itemDimensions.getDimensionsArray(), this.getWidthWiseDimensions());
    }

    public boolean checkItemFitInAlongHeightDim(Dimensions itemDimensions) {
        return checkItemFitInAlongDimensionWise(itemDimensions.getDimensionsArray(), this.getHeightWiseDimensions());
    }

    public Boolean arrangeItemAlongDimensionWise(Dimensions itemDimensions) {
        Boolean fitted = false;
        if(checkItemFitInAlongLengthDim(itemDimensions)) {
            this.getBreadthWiseDimensions().decreaseDimensions(itemDimensions);
            this.getWidthWiseDimensions().decreaseWidthDimension(itemDimensions.getWidth());
            this.getHeightWiseDimensions().decreaseHeightDimension(itemDimensions.getHeight());

            fitted = true;
        } else if(checkItemFitInAlongWidthDim(itemDimensions)) {
            this.getBreadthWiseDimensions().decreaseBreadthDimension(itemDimensions.getBreadth());
            this.getWidthWiseDimensions().decreaseDimensions(itemDimensions);
            this.getHeightWiseDimensions().decreaseHeightDimension(itemDimensions.getHeight());

            fitted = true;
        } else if(checkItemFitInAlongHeightDim(itemDimensions)) {
            this.getBreadthWiseDimensions().decreaseBreadthDimension(itemDimensions.getBreadth());
            this.getWidthWiseDimensions().decreaseWidthDimension(itemDimensions.getWidth());
            this.getHeightWiseDimensions().decreaseDimensions(itemDimensions);

            fitted = true;
        }
        return fitted;
    }
}
