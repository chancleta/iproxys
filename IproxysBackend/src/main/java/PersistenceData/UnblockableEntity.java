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
 * @author Luis
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "UnblockableEntity.findAll", query = "SELECT u FROM UnblockableEntity u"),})
public class UnblockableEntity extends PersistenceProvider implements Serializable {

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
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date insertedOnDate;
    @Column
    private int protocol;

    public UnblockableEntity() {
        super();
    }

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
     * @return the blockedOnTimeDate
     */
    public Date getInsertedOnDate() {
        return insertedOnDate;
    }

    /**
     * @param blockedOnTimeDate the blockedOnTimeDate to set
     */
    public void setInsertedOnDatee(Date insertedOnDate) {
        this.insertedOnDate = insertedOnDate;
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

//    public List<UnblockableBean> getAllUnblockableEntities() {
//        List<UnblockableBean> unblockableBeans = new ArrayList<>();
//        for (Object o : findAll()) {
//            UnblockableEntity unblockableEntity = (UnblockableEntity) o;
//            unblockableBeans.add(unblockableEntity.getUnblockableBean());
//        }
//        return unblockableBeans;
//    }
//
//    public UnblockableBean getUnblockableBean() {
//        UnblockableBean unBean = new UnblockableBean();
//        unBean.setBlockedIP(blockedIP);
//        unBean.setBlockedPort(blockedPort);
//        unBean.setIdentifier(identifier);
//        unBean.setId(id);
//        unBean.setProtocol(protocol);
//        return unBean;
//    }
}
