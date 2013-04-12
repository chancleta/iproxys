/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iproxys.InetDataCollector;

import iproxys.PersistenceData.SummaryIPPort_BandWidth;
import iproxys.PersistenceData.SummaryIP_BandWidth;
import iproxys.PersistenceData.SummaryPort_BandWidth;
import java.util.Date;
import java.util.List;
import jpcap.PacketReceiver;
import jpcap.packet.Packet;

/**
 *
 * @author root
 */
public class RecievePackets implements PacketReceiver {

    private jpcap.packet.IPPacket RecievedPacket;

    @Override
    public void receivePacket(Packet packet) {

        
        if (packet instanceof jpcap.packet.IPPacket) {
            
            Sniffer.networkMonitor += packet.caplen;
            //System.err.println("123");
            this.RecievedPacket = (jpcap.packet.IPPacket) packet;

            if (!this.RecievedPacket.src_ip.getHostAddress().equals(Sniffer.InterfaceIP)) {
                
                updateIP();
                updatePort();
                updateIPPort();
                
            }
        }
    }

    private void updateIPPort() {
        
        recorrerIPPort(Sniffer.IPPortPDUs);
        recorrerIPPort(Sniffer.TempIPPortPDUs);
    }

    private void recorrerIPPort(List<SummaryIPPort_BandWidth> relativo) {
        
        boolean control = true;
                
        for (SummaryIPPort_BandWidth sumipport : relativo) {

            if (sumipport.getIp_Dst().equals(RecievedPacket.dst_ip.getHostAddress()) && sumipport.getIp_Src().equals(RecievedPacket.src_ip.getHostAddress()) && sumipport.getProtocol() == RecievedPacket.protocol) {

                if (this.RecievedPacket instanceof jpcap.packet.TCPPacket) {
                    
                    if (sumipport.getPort() == ((jpcap.packet.TCPPacket) this.RecievedPacket).src_port) {
                        
                        sumipport.setBdusage(sumipport.getBdusage() + RecievedPacket.caplen);
                        control = false;
                    }

                } else if (this.RecievedPacket instanceof jpcap.packet.UDPPacket) {
                                      
                    if (sumipport.getPort() == ((jpcap.packet.UDPPacket) this.RecievedPacket).src_port) {
                        
                        sumipport.setBdusage(sumipport.getBdusage() + RecievedPacket.caplen);
                        control = false;
                    }
                }
            }
        }
        if (control) {
            
            SummaryIPPort_BandWidth newsumipport = new SummaryIPPort_BandWidth();
            boolean controlInterno = false;
            
            if (RecievedPacket instanceof jpcap.packet.TCPPacket) {
                
                newsumipport.setPort(((jpcap.packet.TCPPacket) RecievedPacket).src_port);
                controlInterno = true;

            } else if (RecievedPacket instanceof jpcap.packet.UDPPacket) {

                newsumipport.setPort(((jpcap.packet.UDPPacket) RecievedPacket).src_port);
                controlInterno = true;

            }
            if (controlInterno) {
                
                newsumipport.setBdusage(RecievedPacket.caplen);
                newsumipport.setProtocol(RecievedPacket.protocol);
                newsumipport.setTimeref(Sniffer.TimeTempRef);
                newsumipport.setIp_Dst(RecievedPacket.dst_ip.getHostAddress());
                newsumipport.setIp_Src(RecievedPacket.src_ip.getHostAddress());
                relativo.add(newsumipport);
            }
        }
    }

    private void updatePort() {
        
        recorrerPort(Sniffer.PortPDUs);
        recorrerPort(Sniffer.TempPortPDUs);
    }

    private void recorrerPort(List<SummaryPort_BandWidth> relativo) {

        boolean control = true;
        
        for (SummaryPort_BandWidth sumport : relativo) {
           
            if (sumport.getProtocol() == RecievedPacket.protocol) {
                
                if (this.RecievedPacket instanceof jpcap.packet.TCPPacket) {
                    
                    jpcap.packet.TCPPacket tcp = (jpcap.packet.TCPPacket) this.RecievedPacket;
                    
                    if (sumport.getPort() == tcp.src_port) {
                        sumport.setBdusage(sumport.getBdusage() + RecievedPacket.caplen);
                        control = false;
                    }

                } else if (sumport.getProtocol() == RecievedPacket.protocol && this.RecievedPacket instanceof jpcap.packet.UDPPacket) {
                    
                    jpcap.packet.UDPPacket udp = (jpcap.packet.UDPPacket) this.RecievedPacket;
                    
                    if (sumport.getPort() == udp.src_port) {
                        sumport.setBdusage(sumport.getBdusage() + RecievedPacket.caplen);
                        control = false;
                    }
                }
            }
        }
        if (control) {
            
            SummaryPort_BandWidth newsumport;
            newsumport = new SummaryPort_BandWidth();
            boolean controlInterno = false;
            
            if (RecievedPacket instanceof jpcap.packet.TCPPacket) {
                
                newsumport.setPort(((jpcap.packet.TCPPacket) RecievedPacket).src_port);
                controlInterno = true;

            } else if (RecievedPacket instanceof jpcap.packet.UDPPacket) {

                newsumport.setPort(((jpcap.packet.UDPPacket) RecievedPacket).src_port);
                controlInterno = true;

            }
            if (controlInterno) {
                
                newsumport.setBdusage(RecievedPacket.caplen);
                newsumport.setProtocol(RecievedPacket.protocol);
                newsumport.setTimeref(Sniffer.TimeTempRef);
                relativo.add(newsumport);
            }
        }
    }

    private void updateIP() {
        
        recorrerIP(Sniffer.IPPDUs);
        recorrerIP(Sniffer.TempIPPDUs);
    }

    private void recorrerIP(List<SummaryIP_BandWidth> relativo) {

        boolean control = true;

       
        for (SummaryIP_BandWidth sumip : relativo) {

            if (sumip.getIp_Dst().equals(RecievedPacket.dst_ip.getHostAddress())) {
                sumip.setBdusage(sumip.getBdusage() + RecievedPacket.caplen);
                control = false;
            }

        }
        if (control) {
            
            SummaryIP_BandWidth newsumip;
            newsumip = new SummaryIP_BandWidth();
            newsumip.setBdusage(RecievedPacket.caplen);
            newsumip.setIp_Dst(RecievedPacket.dst_ip.getHostAddress());
            newsumip.setTimeref(Sniffer.TimeTempRef);
            relativo.add(newsumip);
            
        }
    }
}
