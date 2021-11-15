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
    
    private Long id;
    private int hostPoints;
    private int guestPoints;

    public Long getId() {
        return id;
    }

    public int getHostPoints() {
        return hostPoints;
    }

    public int getGuestPoints() {
        return guestPoints;
    }

    public MatchDetails(Long id, int hostPoints, int guestPoints) {
        this.id = id;
        this.hostPoints = hostPoints;
        this.guestPoints = guestPoints;
    }

    @Override
    public String toString() {
        return "MatchDetails{" + "id=" + id + ", hostPoints=" + hostPoints + ", guestPoints=" + guestPoints + '}';
    }
    
    
    
    
    
    
}
