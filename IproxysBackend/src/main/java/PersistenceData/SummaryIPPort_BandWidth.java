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
@Table(name = "SummaryIPPort_BandWidth" )
public class SummaryIPPort_BandWidth implements Serializable{
    
    @Id @Column (name = "id") @GeneratedValue
    private long id;
    
    @Column (name = "IP_Dst")
    private String ip_Dst;
    
    @Column (name = "IP_Src")
    private String ip_Src;
    
    @Column (name = "Protocol")
    private int protocol;
    
    @Column (name = "Port")
    private int port;
    
    @Column (name = "bdusage")
    private double bdusage;
    
    @Column (name = "timeref")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeref;

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
     * @return the ip_Dst
     */
    public String getIp_Dst() {
        return ip_Dst;
    }

    /**
     * @param ip_Dst the ip_Dst to set
     */
    public void setIp_Dst(String ip_Dst) {
        this.ip_Dst = ip_Dst;
    }

    /**
     * @return the ip_Src
     */
    public String getIp_Src() {
        return ip_Src;
    }

    /**
     * @param ip_Src the ip_Src to set
     */
    public void setIp_Src(String ip_Src) {
        this.ip_Src = ip_Src;
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

    

    
}
