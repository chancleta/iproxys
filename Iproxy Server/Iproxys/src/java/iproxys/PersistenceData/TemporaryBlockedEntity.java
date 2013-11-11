/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iproxys.PersistenceData;

import iproxys.performblock.PerformBlock;
import iproxys.performblock.PerformHttpBlock;
import iproxys.performblock.PerformIPPortBlock;
import iproxys.performblock.PerformIpBlock;
import iproxys.performblock.PerformPortBlock;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Query;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author root
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "TemporaryBlockedEntity.findAll", query = "SELECT u FROM TemporaryBlockedEntity u"),
    @NamedQuery(name = "TemporaryBlockedEntity.findEntityToUnblock", query = "SELECT u FROM TemporaryBlockedEntity u where u.blockedOnTimeDate BETWEEN :allowTimeEnd AND :allowTimeStart")
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
    public static final int BLOCK_IP = 1;
    public static final int BLOCK_IP_AND_PORT = 2;
    public static final int BLOCK_PORT = 3;
    public static final int BLOCK_HTTP_DOMAIN_TO_IP = 4;
    public static long HOUR_IN_MS = 1000 * 60 * 60;
    public static long MIN_IN_MS = 1000 * 60;

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

    public ArrayList<TemporaryBlockedEntity> findEntityToUnblock() {

        Query findEntityToUnblock = PersistenceProvider.entityManger.createNamedQuery("TemporaryBlockedEntity.findEntityToUnblock");
        findEntityToUnblock.setParameter("allowTimeStart", new Date(System.currentTimeMillis() - (1 * HOUR_IN_MS)), TemporalType.TIMESTAMP);
        findEntityToUnblock.setParameter("allowTimeEnd", new Date(System.currentTimeMillis() - (1 * HOUR_IN_MS) - (10*MIN_IN_MS)), TemporalType.TIMESTAMP);
        List<Object> resultList = findEntityToUnblock.getResultList();
        return converFromListObjectTo(resultList);

    }
    
    public ArrayList<TemporaryBlockedEntity> findEntitiesToPermaBlock(){
        
        List<TemporaryBlockedEntity> allEntities = converFromListObjectTo(findAll());
        
        for(TemporaryBlockedEntity temporaryBlockedEntity: allEntities){
            
            Date parentDate = temporaryBlockedEntity.getBlockedOnTimeDate();
            for(TemporaryBlockedEntity temporaryBlockedEntitySecond: allEntities){
                
                if(temporaryBlockedEntity.getId() !=  temporaryBlockedEntitySecond.getId()){
                    
                   if(areTheseEntitiesEquals(temporaryBlockedEntity,temporaryBlockedEntitySecond)){
                   
                        // VERIFICAR LA MIERDA DEL DIA ANTERIOR O DOS DIAS ANTERIORES 
                   
                   }
                }
           
            }
        }
    
        return new ArrayList<>();
    
    
    }
    
    private ArrayList<TemporaryBlockedEntity> converFromListObjectTo(List<Object> inCommingList ){
        ArrayList<TemporaryBlockedEntity> castedList = new ArrayList<>();
        for (Object o : inCommingList) {
            Object trans = o;
            TemporaryBlockedEntity temporaryBlockedEntity = (TemporaryBlockedEntity) trans;
            castedList.add(temporaryBlockedEntity);
        }
        return castedList;
    }
    
    
    private boolean areTheseEntitiesEquals(TemporaryBlockedEntity givenEntityOne, TemporaryBlockedEntity givenEntityTwo){
        boolean areTheyEquals = false;
        if(givenEntityOne.getIdentifier() == givenEntityTwo.getIdentifier()){
            
            switch (givenEntityOne.getIdentifier()) {
                case TemporaryBlockedEntity.BLOCK_IP:
                    areTheyEquals = givenEntityOne.getBlockedIP().equals(givenEntityTwo.getBlockedIP()); 
                    break;
                case TemporaryBlockedEntity.BLOCK_IP_AND_PORT:
                    areTheyEquals = givenEntityOne.getBlockedIP().equals(givenEntityTwo.getBlockedIP()) && givenEntityOne.getBlockedPort() == givenEntityTwo.getBlockedPort() &&  givenEntityOne.getProtocol()== givenEntityTwo.getProtocol();
                    break;
                case TemporaryBlockedEntity.BLOCK_PORT:
                    areTheyEquals = givenEntityOne.getBlockedPort() == givenEntityTwo.getBlockedPort() &&  givenEntityOne.getProtocol()== givenEntityTwo.getProtocol();
                    break;
                case TemporaryBlockedEntity.BLOCK_HTTP_DOMAIN_TO_IP:
                    areTheyEquals = givenEntityOne.getBlockedIP().equals(givenEntityTwo.getBlockedIP()) && givenEntityOne.getBlockedDomain().equals(givenEntityTwo.getBlockedDomain());
                    break;
            }
        
        }
        return areTheyEquals;
    }
    
    
}
