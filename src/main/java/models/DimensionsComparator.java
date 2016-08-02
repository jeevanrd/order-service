package models;


import java.util.Comparator;

public class DimensionsComparator implements Comparator<Dimensions> {
    @Override
    public int compare(Dimensions d1, Dimensions d2) {
        int v1 = d1.getVolume();
        int v2 = d2.getVolume();
        return v1 - v2;
    }
}
