package models;

import validator.ValiatableObject;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by root on 21/10/16.
 */
public class ResourceAllowance extends ValiatableObject {

    private int identifier;

    private String blockedIP;

    private int blockedPort;

    private int protocol;

    private String blockedDomain;

    private long id;

    public int getIdentifier() {
        return identifier;
    }

    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    public String getBlockedIP() {
        return blockedIP;
    }

    public void setBlockedIP(String blockedIP) {
        this.blockedIP = blockedIP;
    }

    public int getBlockedPort() {
        return blockedPort;
    }

    public void setBlockedPort(int blockedPort) {
        this.blockedPort = blockedPort;
    }

    public int getProtocol() {
        return protocol;
    }

    public void setProtocol(int protocol) {
        this.protocol = protocol;
    }

    public String getBlockedDomain() {
        return blockedDomain;
    }

    public void setBlockedDomain(String blockedDomain) {
        this.blockedDomain = blockedDomain;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
