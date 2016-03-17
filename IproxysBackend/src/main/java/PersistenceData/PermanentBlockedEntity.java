/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PersistenceData;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Luis
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "PermanentBlockedEntity.findAll", query = "SELECT u FROM PermanentBlockedEntity u"),
    @NamedQuery(name = "PermanentBlockedEntity.findAllByAlreadyUnblocked", query = "SELECT u FROM PermanentBlockedEntity u where u.alreadyUnblocked = :alreadyUnblocked")
})
public class PermanentBlockedEntity extends PersistenceProvider implements Serializable {

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
    @Column
    private boolean alreadyUnblocked = false;
    private static int BLOCK_IP = 1;
    private static int BLOCK_IP_AND_PORT = 2;
    private static int BLOCK_PORT = 3;
    private static int BLOCK_HTTP_DOMAIN_TO_IP = 4;
    public PermanentBlockedEntity(){
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

    /**
     * @return the alreadyUnblocked
     */
    public boolean isAlreadyUnblocked() {
        return alreadyUnblocked;
    }

    /**
     * @param alreadyUnblocked the alreadyUnblocked to set
     */
    public void setAlreadyUnblocked(boolean alreadyUnblocked) {
        this.alreadyUnblocked = alreadyUnblocked;
    }

    public ArrayList<PermanentBlockedEntity> findAllByAlreadyUnblocked(boolean alreadyUnblocked) {
        Query findEntityToUnblock = entityManager.createNamedQuery("PermanentBlockedEntity.findAllByAlreadyUnblocked",PermanentBlockedEntity.class);
        findEntityToUnblock.setParameter("alreadyUnblocked", alreadyUnblocked);
        return converFromListObjectTo(findEntityToUnblock.getResultList());

    }

    public static PermanentBlockedEntity getPermanentBlockedEntityFromTemporaryBlockedEntity(TemporaryBlockedEntity temEntity) {

        PermanentBlockedEntity permanent = new PermanentBlockedEntity();

        switch (temEntity.getIdentifier()) {

            case TemporaryBlockedEntity.BLOCK_IP:
                permanent.setBlockedIP(temEntity.getBlockedIP());
                break;
            case TemporaryBlockedEntity.BLOCK_IP_AND_PORT:
                permanent.setBlockedIP(temEntity.getBlockedIP());
                permanent.setProtocol(temEntity.getProtocol());
                permanent.setBlockedPort(temEntity.getBlockedPort());
                break;
            case TemporaryBlockedEntity.BLOCK_PORT:
                permanent.setProtocol(temEntity.getProtocol());
                permanent.setBlockedPort(temEntity.getBlockedPort());
                break;
            case TemporaryBlockedEntity.BLOCK_HTTP_DOMAIN_TO_IP:
                permanent.setBlockedIP(temEntity.getBlockedIP());
                permanent.setBlockedDomain(temEntity.getBlockedDomain());
                break;
        }
        permanent.setBlockedOnTimeDate(new Date());
        permanent.setIdentifier(temEntity.getIdentifier());
        return permanent;
    }

    public static TemporaryBlockedEntity getTemporaryEntityFromPermanentEntity(PermanentBlockedEntity permanentBlockedEntity) {

        TemporaryBlockedEntity temporalBlocked = new TemporaryBlockedEntity();

        switch (permanentBlockedEntity.getIdentifier()) {

            case TemporaryBlockedEntity.BLOCK_IP:
                temporalBlocked.setBlockedIP(permanentBlockedEntity.getBlockedIP());
                break;
            case TemporaryBlockedEntity.BLOCK_IP_AND_PORT:
                temporalBlocked.setBlockedIP(permanentBlockedEntity.getBlockedIP());
                temporalBlocked.setProtocol(permanentBlockedEntity.getProtocol());
                temporalBlocked.setBlockedPort(permanentBlockedEntity.getBlockedPort());
                break;
            case TemporaryBlockedEntity.BLOCK_PORT:
                temporalBlocked.setProtocol(permanentBlockedEntity.getProtocol());
                temporalBlocked.setBlockedPort(permanentBlockedEntity.getBlockedPort());
                break;
            case TemporaryBlockedEntity.BLOCK_HTTP_DOMAIN_TO_IP:
                temporalBlocked.setBlockedIP(permanentBlockedEntity.getBlockedIP());
                temporalBlocked.setBlockedDomain(permanentBlockedEntity.getBlockedDomain());
                break;
        }
        temporalBlocked.setBlockedOnTimeDate(new Date());
        temporalBlocked.setIdentifier(permanentBlockedEntity.getIdentifier());
        return temporalBlocked;

    }

    public ArrayList<PermanentBlockedEntity> converFromListObjectTo(List<Object> inCommingList) {
        ArrayList<PermanentBlockedEntity> castedList = new ArrayList<>();
        for (Object o : inCommingList) {
            Object trans = o;
            PermanentBlockedEntity temporaryBlockedEntity = (PermanentBlockedEntity) trans;
            castedList.add(temporaryBlockedEntity);
        }
        return castedList;
    }
}
