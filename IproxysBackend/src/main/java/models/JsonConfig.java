package models;

import validator.ValiatableObject;

import javax.validation.constraints.NotNull;

public class JsonConfig  extends ValiatableObject {

    @NotNull
    private Bandwidth bandwidth;

    @NotNull
    private Bandwidth maxBandwidthPerUser;

    @NotNull
    private long tempTimeDuration;

    public Bandwidth getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(Bandwidth bandwidth) {
        this.bandwidth = bandwidth;
    }


    public Bandwidth getMaxBandwidthPerUser() {
        return maxBandwidthPerUser;
    }

    public void setMaxBandwidthPerUser(Bandwidth maxBandwidthPerUser) {
        this.maxBandwidthPerUser = maxBandwidthPerUser;
    }

    public long getTempTimeDuration() {
        return tempTimeDuration;
    }

    public void setTempTimeDuration(long tempTimeDuration) {
        this.tempTimeDuration = tempTimeDuration;
    }
}
