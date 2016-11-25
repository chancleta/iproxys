/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PersistenceData;

import InetDataCollector.Sniffer;
import externalDependencies.GeneralConfiguration;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author root
 */
@Entity

@NamedQueries({
    @NamedQuery(name = "TemporaryBlockedEntity.findAll", query = "SELECT u FROM TemporaryBlockedEntity u"),
    @NamedQuery(name = "TemporaryBlockedEntity.findLiveActions", query = "SELECT u FROM TemporaryBlockedEntity u where u.tempUnBlocked = false"),
    @NamedQuery(name = "TemporaryBlockedEntity.findEntityToUnblock", query = "SELECT u FROM TemporaryBlockedEntity u where u.tempUnBlocked = false and u.blockedOnTimeDate <= :allowTimeStart"),
    @NamedQuery(name = "TemporaryBlockedEntity.findAllByPermaBlocked", query = "SELECT u FROM TemporaryBlockedEntity u where u.permaBlocked = :permaBlockedValue"),
})
public class TemporaryBlockedEntity extends PersistenceProvider implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Temporal(TemporalType.TIMESTAMP)
    private Date blockedOnTimeDate;
    @Column
    private int protocol;
    @Column(nullable = false)
    private boolean permaBlocked = false;

    @Column(nullable = false)
    private boolean tempUnBlocked = false;

    @Column
    private String blockedIPDest;

    public static final int BLOCK_IP = 1;
    public static final int BLOCK_IP_AND_PORT = 2;
    public static final int BLOCK_PORT = 3;
    public static final int BLOCK_HTTP_DOMAIN_TO_IP = 4;
    public static final long HOUR_IN_MS = 60 * TemporaryBlockedEntity.MIN_IN_MS;
    public static final long MIN_IN_MS = 1000 * 60;
    public static final long DAY_IN_MS = 24 * TemporaryBlockedEntity.HOUR_IN_MS;

    public TemporaryBlockedEntity() {
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
     * @return the permaBlocked
     */
    public boolean isPermaBlocked() {
        return permaBlocked;
    }

    /**
     * @param permaBlocked the permaBlocked to set
     */
    public void setPermaBlocked(boolean permaBlocked) {
        this.permaBlocked = permaBlocked;
    }

    public boolean isTempUnBlocked() {
        return tempUnBlocked;
    }

    public void setTempUnBlocked(boolean tempUnBlocked) {
        this.tempUnBlocked = tempUnBlocked;
    }

    public ArrayList<TemporaryBlockedEntity> findEntityToUnblock(){

        Query findEntityToUnblock = entityManager.createNamedQuery("TemporaryBlockedEntity.findEntityToUnblock",TemporaryBlockedEntity.class);
        findEntityToUnblock.setParameter("allowTimeStart", new Date(System.currentTimeMillis() - ((int)GeneralConfiguration.getTempTimeDuration() * MIN_IN_MS)), TemporalType.TIMESTAMP);
        List<Object> resultList = findEntityToUnblock.getResultList();
        return converFromListObjectTo(resultList);

    }

    public ArrayList<TemporaryBlockedEntity> findEntitiesToPermaBlock() {
        ArrayList<TemporaryBlockedEntity> entitiesToPermaBlock = new ArrayList<>();
        TemporaryBlockedEntity entityToPermaBlock;
        do {
             entityToPermaBlock = findNextEntityToPermaBlock();

            if (entityToPermaBlock != null) {
                entitiesToPermaBlock.add(entityToPermaBlock);
            }

        }while(entityToPermaBlock != null );
        return entitiesToPermaBlock;
    }
    
    /*
     Returns true if finds an entity to perma block
     */

    private TemporaryBlockedEntity findNextEntityToPermaBlock() {
        boolean firstDayHaveBeenFound = false;
        boolean foundEntityToBlock = false;
        List<TemporaryBlockedEntity> allEntities = findAllByPermaBlocked(false);
        TemporaryBlockedEntity entityToPermaBlock = null;
        for (TemporaryBlockedEntity temporaryBlockedEntity : allEntities) {
            Date parentDate = temporaryBlockedEntity.getBlockedOnTimeDate();
            ArrayList<TemporaryBlockedEntity> concurrentBlockedEntity = new ArrayList<>();
            for (int index = 0; index < allEntities.size(); index++) {
                TemporaryBlockedEntity temporaryBlockedEntitySecond = allEntities.get(index);

                if (temporaryBlockedEntity.getId() != temporaryBlockedEntitySecond.getId()) {

                    if (areTheseEntitiesEquals(temporaryBlockedEntity, temporaryBlockedEntitySecond)) {
                        System.out.println("found a match");
                        System.out.println(isThereADayBetweenTheseDates(parentDate, temporaryBlockedEntitySecond.getBlockedOnTimeDate()));

                        if (!firstDayHaveBeenFound && isThereADayBetweenTheseDates(parentDate, temporaryBlockedEntitySecond.getBlockedOnTimeDate())) {
                            firstDayHaveBeenFound = true;
                            index = 0;
                            concurrentBlockedEntity.add(temporaryBlockedEntitySecond);
                        } else if (firstDayHaveBeenFound && isThereTwoDaysBetweenTheseDates(parentDate, temporaryBlockedEntitySecond.getBlockedOnTimeDate())) {
                            concurrentBlockedEntity.add(temporaryBlockedEntitySecond);
                            foundEntityToBlock = true;
                            break;
                        }
                    }
                }

            }
            if (foundEntityToBlock) {
                for (TemporaryBlockedEntity temporaryBlockedEntityConcurrency : concurrentBlockedEntity) {
                    temporaryBlockedEntityConcurrency.setPermaBlocked(true);
                    temporaryBlockedEntityConcurrency.update();
                }
                temporaryBlockedEntity.setPermaBlocked(true);
                temporaryBlockedEntity.update();
                entityToPermaBlock = temporaryBlockedEntity;
                break;
            }
            firstDayHaveBeenFound = false;
            foundEntityToBlock = false;
            concurrentBlockedEntity.clear();
        }
        return entityToPermaBlock;
    }

    private ArrayList<TemporaryBlockedEntity> converFromListObjectTo(List<Object> inCommingList) {
        ArrayList<TemporaryBlockedEntity> castedList = new ArrayList<>();
        for (Object o : inCommingList) {
            Object trans = o;
            TemporaryBlockedEntity temporaryBlockedEntity = (TemporaryBlockedEntity) trans;
            castedList.add(temporaryBlockedEntity);
        }
        return castedList;
    }

    private boolean areTheseEntitiesEquals(TemporaryBlockedEntity givenEntityOne, TemporaryBlockedEntity givenEntityTwo) {
        boolean areTheyEquals = false;
        if (givenEntityOne.getIdentifier() == givenEntityTwo.getIdentifier()) {

            switch (givenEntityOne.getIdentifier()) {
                case TemporaryBlockedEntity.BLOCK_IP:
                    areTheyEquals = givenEntityOne.getBlockedIP().equals(givenEntityTwo.getBlockedIP());
                    break;
                case TemporaryBlockedEntity.BLOCK_IP_AND_PORT:
                    areTheyEquals = givenEntityOne.getBlockedIP().equals(givenEntityTwo.getBlockedIP()) && givenEntityOne.getBlockedPort() == givenEntityTwo.getBlockedPort() && givenEntityOne.getProtocol() == givenEntityTwo.getProtocol();
                    break;
                case TemporaryBlockedEntity.BLOCK_PORT:
                    areTheyEquals = givenEntityOne.getBlockedPort() == givenEntityTwo.getBlockedPort() && givenEntityOne.getProtocol() == givenEntityTwo.getProtocol();
                    break;
                case TemporaryBlockedEntity.BLOCK_HTTP_DOMAIN_TO_IP:
                    areTheyEquals = givenEntityOne.getBlockedIP().equals(givenEntityTwo.getBlockedIP()) && givenEntityOne.getBlockedDomain().equals(givenEntityTwo.getBlockedDomain());
                    break;
            }

        }
        return areTheyEquals;
    }

    private boolean isThereADayBetweenTheseDates(Date dateOne, Date dateTwo) {
        long dateSubstraction = dateOne.getTime() - dateTwo.getTime();
        dateSubstraction = (dateSubstraction < 0) ? dateSubstraction * -1 : dateSubstraction;
        return ((TemporaryBlockedEntity.DAY_IN_MS - (30 * TemporaryBlockedEntity.MIN_IN_MS)) <= dateSubstraction && dateSubstraction <= ((TemporaryBlockedEntity.DAY_IN_MS + 30 * TemporaryBlockedEntity.MIN_IN_MS)));
    }

    private boolean isThereTwoDaysBetweenTheseDates(Date dateOne, Date dateTwo) {
        long dateSubstraction = dateOne.getTime() - dateTwo.getTime();
        dateSubstraction = (dateSubstraction < 0) ? dateSubstraction * -1 : dateSubstraction;
        return (((2 * TemporaryBlockedEntity.DAY_IN_MS) - (30 * TemporaryBlockedEntity.MIN_IN_MS)) <= dateSubstraction && dateSubstraction <= (((2 * TemporaryBlockedEntity.DAY_IN_MS) + 30 * TemporaryBlockedEntity.MIN_IN_MS)));
    }

    private ArrayList<TemporaryBlockedEntity> findAllByPermaBlocked(boolean permaBlockedValue) {
        Query findAllByPermaBlocked = entityManager.createNamedQuery("TemporaryBlockedEntity.findAllByPermaBlocked",TemporaryBlockedEntity.class);
        findAllByPermaBlocked.setParameter("permaBlockedValue", permaBlockedValue);
        List<Object> resultList = findAllByPermaBlocked.getResultList();
        return converFromListObjectTo(resultList);
    }

    public String getBlockedIPDest() {
        return blockedIPDest;
    }

    public void setBlockedIPDest(String blockedIPDest) {
        this.blockedIPDest = blockedIPDest;
    }
}
