/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iproxys.PersistenceData;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;

/**
 *
 * @author root
 */
@Entity
@NamedQueries({
    @NamedQuery(name="TemporaryBlockedEntity.findAll",query="SELECT u FROM TemporaryBlockedEntity u")
})
public class TemporaryBlockedEntity extends PersistenceProvider implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    @Column
    private int identifier;
    
    @Column
    private String blockedIP;
    
    @Column
    private int blockedPort;
    
    @Column
    private String blockedDomain;
    
    @Column
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date blockedOnTimeDate;

    @Column
    private int protocol;
    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @return the identifier
     */
    public int getIdentifier() {
        return identifier;
    }

    /**
     * @param identifier the identifier to set
     */
    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    /**
     * @return the blockedIP
     */
    public String getBlockedIP() {
        return blockedIP;
    }

    /**
     * @param blockedIP the blockedIP to set
     */
    public void setBlockedIP(String blockedIP) {
        this.blockedIP = blockedIP;
    }

    /**
     * @return the blockedPort
     */
    public int getBlockedPort() {
        return blockedPort;
    }

    /**
     * @param blockedPort the blockedPort to set
     */
    public void setBlockedPort(int blockedPort) {
        this.blockedPort = blockedPort;
    }

    /**
     * @return the blockedDomain
     */
    public String getBlockedDomain() {
        return blockedDomain;
    }

    /**
     * @param blockedDomain the blockedDomain to set
     */
    public void setBlockedDomain(String blockedDomain) {
        this.blockedDomain = blockedDomain;
    }

    /**
     * @return the blockedOnTimeDate
     */
    public Date getBlockedOnTimeDate() {
        return blockedOnTimeDate;
    }

    /**
     * @param blockedOnTimeDate the blockedOnTimeDate to set
     */
    public void setBlockedOnTimeDate(Date blockedOnTimeDate) {
        this.blockedOnTimeDate = blockedOnTimeDate;
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
    
    
}
