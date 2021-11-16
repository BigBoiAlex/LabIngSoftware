/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jakarta.tutorial.roster.util;

import java.io.Serializable;

/**
 *
 * @author alexj
 */
public class MatchDetails implements Serializable {

    public MatchDetails() {
    }
    
    private int id;
    private int hostPoints;
    private int guestPoints;

    public int getId() {
        return id;
    }

    public int getHostPoints() {
        return hostPoints;
    }

    public int getGuestPoints() {
        return guestPoints;
    }

    public MatchDetails(int id, int hostPoints, int guestPoints) {
        this.id = id;
        this.hostPoints = hostPoints;
        this.guestPoints = guestPoints;
    }

    @Override
    public String toString() {
        return "Match ID = " + id + " HostPoints = " + hostPoints + " GuestPoints = " + guestPoints ;
    }
    
    
    
    
    
    
}
