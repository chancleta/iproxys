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
@Table(name="UnBlockableIP")
@NamedQueries(
        {
           @NamedQuery(name=UnBlockableIP.findAllUnblockableIPs,query="select u from UnBlockableIP u" ),
           @NamedQuery(name=UnBlockableIP.findbyIP,query="select u from UnBlockableIP u where u.ip = :ip")
        
        }        
        
)
public class UnBlockableIP implements Serializable {
     public static final String findAllUnblockableIPs = "findAllUnblockableIPs";
    public static final String findbyIP = "findbyIP";
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name="ip",unique=true,nullable=false,length=15)
    private String ip;
    @Column(name="blockDate",nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date blockDate;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UnBlockableIP)) {
            return false;
        }
        UnBlockableIP other = (UnBlockableIP) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "iproxys.PersistenceData.UnBlockableIP[ id=" + id + " ]";
    }

    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return the blockDate
     */
    public Date getBlockDate() {
        return blockDate;
    }

    /**
     * @param blockDate the blockDate to set
     */
    public void setBlockDate(Date blockDate) {
        this.blockDate = blockDate;
    }
    
}
