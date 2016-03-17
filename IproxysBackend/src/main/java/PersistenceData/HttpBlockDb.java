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
@Table(name="HttpBlockDb")
@NamedQueries({
    @NamedQuery(name = HttpBlockDb.findAllHttpBlock, query = "select u from HttpBlockDb u"),
    @NamedQuery(name = HttpBlockDb.findbyDomain, query = "select u from HttpBlockDb u where u.domain = :domain")
})
public class HttpBlockDb implements Serializable {
    
    public static final String findAllHttpBlock = "findAllHttpBlock";
    public static final String findbyDomain = "findbyDomain";
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name="domain",nullable=false)
    private String domain;
    @Column(name="ip",nullable=false)
    private String ip;
    @Column(name="timeref",nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeref;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int var = 1;
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HttpBlockDb)) {
            return false;
        }
        HttpBlockDb other = (HttpBlockDb) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "iproxys.PersistenceData.HttpBlockDb[ id=" + id + " ]";
    }

    /**
     * @return the domain
     */
    public String getDomain() {
        return domain;
    }

    /**
     * @param domain the domain to set
     */
    public void setDomain(String domain) {
        this.domain = domain;
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
