package models;

import PersistenceData.PersistenceProvider;
import validator.ValiatableObject;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "Config")
@NamedQueries({
        @NamedQuery(name = Config.getAll, query = "select u from Config u"),
})
public class Config extends PersistenceProvider implements Serializable {
    public static final String getAll = "Configuration.getAll";
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column
    private long id;

    @Column(nullable = false)
    @NotNull
    private Bandwidth bandwidth;

    @Column(nullable = false)
    @NotNull
    private Bandwidth maxBandwidthPerUser;

    @Column(nullable = false)
    @NotNull
    private long tempTimeDuration;

    public Bandwidth getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(Bandwidth bandwidth) {
        this.bandwidth = bandwidth;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
