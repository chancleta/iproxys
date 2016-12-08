package models;

/**
 * Created by Luis Pena on 12/8/2016.
 */
public class DetailedStatus {

    private String ip;
    private int port;
    private int protocol;
    private double bdusage;
    private int indetifier;
    private String ipDest;
    public double getBdusage() {
        return bdusage;
    }

    public void setBdusage(double bdusage) {
        this.bdusage = bdusage;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getProtocol() {
        return protocol;
    }

    public void setProtocol(int protocol) {
        this.protocol = protocol;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getIndetifier() {
        return indetifier;
    }

    public void setIndetifier(int indetifier) {
        this.indetifier = indetifier;
    }

    public String getIpDest() {
        return ipDest;
    }

    public void setIpDest(String ipDest) {
        this.ipDest = ipDest;
    }
}
