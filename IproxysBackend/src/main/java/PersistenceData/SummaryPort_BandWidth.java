/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PersistenceData;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author root
 */
@Entity
@Table(name = "SummaryPort_BandWidth")

public class SummaryPort_BandWidth implements Serializable
{
//    public static final String findAll = "findAll";
    @Id @Column (name = "id") @GeneratedValue
    private long id;
    
    @Column (name = "Port")
    private int port;
    
    @Column (name = "Protocol")
    private int protocol;
    
    @Column (name = "bdusage")
    private double bdusage;
    
    @Column (name = "timeref")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeref;

    public int userAmount;
    public double availableBandwidth;
    public double  maxBandwidthPerUser;

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * @return the protocol
     */
    public int getProtocol() {
        return protocol;
    }

    /**
     * @param protocol the protocol to set
     */
    public void setProtocol(int protocol) {
        this.protocol = protocol;
    }

    /**
     * @return the bdusage
     */
    public double getBdusage() {
        return bdusage;
    }

    /**
     * @param bdusage the bdusage to set
     */
    public void setBdusage(double bdusage) {
        this.bdusage = bdusage;
    }

    /**
     * @return the timeref
     */
    public Date getTimeref() {
        return timeref;
    }

    /**
     * @param timeref the timeref to set
     */
    public void setTimeref(Date timeref) {
        this.timeref = timeref;
    }

    public void calculateMaxBWAllowance(){
        if(userAmount > 0) {
            maxBWAllowance = availableBandwidth / maxBandwidthPerUser <= userAmount ? maxBandwidthPerUser / availableBandwidth : 1.0 / userAmount;
            maxBWAllowance = maxBWAllowance * 100;
        }else{
            maxBWAllowance = 100;
        }
    }

    private double maxBWAllowance;

    public double getMaxBWAllowance() {
        return maxBWAllowance;
    }

    public void setMaxBWAllowance(double maxBWAllowance) {
        this.maxBWAllowance = maxBWAllowance;
    }
}
