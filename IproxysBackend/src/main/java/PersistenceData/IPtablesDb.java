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
@Table(name="IPtablesDb")
@NamedQueries({
    @NamedQuery(name = IPtablesDb.findAllIPtables, query = "select u from IPtablesDb u"),
    @NamedQuery(name = IPtablesDb.findbyIPtables, query = "select u from IPtablesDb u where u.comando = :comando")
})
public class IPtablesDb implements Serializable {

    public static final String findAllIPtables = "findAllIPtables";
    public static final String findbyIPtables = "findbyIPtables";
    @GeneratedValue
    @Id
    private long id;
    @Column(name = "comando", nullable = false)
    private String comando;
    @Column(name = "ip")
    private String ip;
    @Column(name = "port")
    private int port;
    @Column(name = "protocol")
    private int protocol;
    
    @Column(name = "tipo")
    private int tipo;
    
    @Column(name = "timeref")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeref;
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the comando
     */
    public String getComando() {
        return comando;
    }

    /**
     * @param comando the comando to set
     */
    public void setComando(String comando) {
        this.comando = comando;
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
     * @return the tipo
     */
    public int getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(int tipo) {
        this.tipo = tipo;
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
