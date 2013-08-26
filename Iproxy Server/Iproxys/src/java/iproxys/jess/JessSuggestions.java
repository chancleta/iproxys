/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iproxys.jess;

import java.util.Date;

/**
 *
 * @author root
 */
public class JessSuggestions {

    public final static String block = "Block";
    public final static String allow = "Allow";
    private String action;
    private int protocol;
    private int port;
    
    private String ip_Dst;
    private String ip_Src;
    private double db;
    private Date timeref;
    //Identificador de COnstructor
    private int tipo;
    
    public JessSuggestions(String action, String ip, Date timeref) {
        //matchingBandwidthIP

        ip_Dst = ip;
        this.action = action;
        tipo = 1;
        this.timeref = timeref;
    }

    public JessSuggestions(String ip, String action, int puerto, Date timeref, int protocol) {
        //matchingBandwithIPPort
       tipo = 2;
         ip_Dst = ip;
        this.action = action;
        port = puerto;
        this.protocol = protocol;
        this.timeref = timeref;
    }

    public JessSuggestions(int puerto, String action, int protocol, Date timeref) {
        //matchingBandwidthPort no 80 tcp
        tipo = 3;
        this.action = action;
        port = puerto;
        this.protocol = protocol;
        this.timeref = timeref;
    }
    //?Port ?*Block* ?Protocol ?ip_Dest ?ip_Src ?timeref ?*bdusage*

    public JessSuggestions(int puerto, String action, int protocol, String ip_Dest, String ip_Src, Date timeref, double bd) {
        //matchingPort puerto 80
        
        tipo = 4;
         ip_Dst = ip_Dest;
        this.action = action;
        port = puerto;
        this.protocol = protocol;
        this.timeref = timeref;
        this.ip_Src = ip_Src;
        this.db = bd;
    }

    /**
     * @return the action
     */
    public String getAction() {
        return action;
    }

    /**
     * @param action the action to set
     */
    public void setAction(String action) {
        this.action = action;
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
     * @return the ip_Dst
     */
    public String getIp_Dst() {
        return ip_Dst;
    }

    /**
     * @param ip_Dst the ip_Dst to set
     */
    public void setIp_Dst(String ip_Dst) {
        this.ip_Dst = ip_Dst;
    }

    /**
     * @return the ip_Src
     */
    public String getIp_Src() {
        return ip_Src;
    }

    /**
     * @param ip_Src the ip_Src to set
     */
    public void setIp_Src(String ip_Src) {
        this.ip_Src = ip_Src;
    }

    /**
     * @return the db
     */
    public double getDb() {
        return db;
    }

    /**
     * @param db the db to set
     */
    public void setDb(double db) {
        this.db = db;
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
}

 
