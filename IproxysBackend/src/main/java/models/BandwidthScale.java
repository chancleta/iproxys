package models;

/**
 * Created by Luis Pena on 9/23/2016.
 */
public enum BandwidthScale {
    MegaBit(1000000),KiloBit(1000), Bit(1);

    private double actualValue;
    BandwidthScale(double actualValue){
        this.actualValue = actualValue;
    }

    public double getActualValue(){
        return actualValue;
    }
}
