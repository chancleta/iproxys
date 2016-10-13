package models;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by root on 30/09/16.
 */
public class LiveAction {


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

    public String getBlockedDomain() {
        return blockedDomain;
    }

    public void setBlockedDomain(String blockedDomain) {
        this.blockedDomain = blockedDomain;
    }

    public Date getBlockedOnTimeDate() {
        return blockedOnTimeDate;
    }

    public void setBlockedOnTimeDate(Date blockedOnTimeDate) {
        this.blockedOnTimeDate = blockedOnTimeDate;
    }

    public int getProtocol() {
        return protocol;
    }

    public void setProtocol(int protocol) {
        this.protocol = protocol;
    }

    private int identifier;
    private String blockedIP;
    private int blockedPort;
    private String blockedDomain;
    private Date blockedOnTimeDate;
    private int protocol;

}
