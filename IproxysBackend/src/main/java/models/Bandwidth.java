package models;

import validator.ValiatableObject;

import java.io.Serializable;

/**
 * Created by Luis Pena on 9/23/2016.
 */
public class Bandwidth extends ValiatableObject implements Serializable{
    private double bandwidth;
    private BandwidthScale bandwidthScale;

    public BandwidthScale getBandwidthScale() {
        return bandwidthScale;
    }

    public void setBandwidthScale(BandwidthScale bandwidthScale) {
        this.bandwidthScale = bandwidthScale;
    }

    public double getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(double bandwidth) {
        this.bandwidth = bandwidth;
    }
}
