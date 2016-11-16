package models;

/**
 * Created by Luis Pena on 9/23/2016.
 */
public enum BandwidthScale {
    Mbps(1000000),Kbps(1000), bps(1);

    private double actualValue;
    BandwidthScale(double actualValue){
        this.actualValue = actualValue;
    }

    public double getActualValue(){
        return actualValue;
    }
}
