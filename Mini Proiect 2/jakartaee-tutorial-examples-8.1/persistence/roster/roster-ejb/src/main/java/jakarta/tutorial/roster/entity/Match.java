/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jakarta.tutorial.roster.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author alexj
 */
@Entity
@Table(name = "PERSISTENCE_ROSTER_MATCH")
public class Match implements Serializable {

    private static final long serialVersionUID = 13123L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
       
    @Column(name = "host_points")
    private int hostPoints;
    
    @Column(name = "guest_points")
    private int guestPoints;
    
    
    @ManyToOne
    @JoinColumn(name = "host_team_id")
    private Team hostTeam;
    
    @ManyToOne
    @JoinColumn(name = "guest_team_id")
    private Team guestTeam;

    public Match(int id, int hostPoints, int guestPoints, Team hostTeam, Team guestTeam) {
        this.id = id;
        this.hostPoints = hostPoints;
        this.guestPoints = guestPoints;
        this.hostTeam = hostTeam;
        this.guestTeam = guestTeam;
    }

    public Match(int id, int hostPoints, int guestPoints) {
        this.id = id;
        this.hostPoints = hostPoints;
        this.guestPoints = guestPoints;
    }

    public Match() {
    }

    public int getHostPoints() {
        return hostPoints;
    }

    public void setHostPoints(int hostPoints) {
        this.hostPoints = hostPoints;
    }

    public int getGuestPoints() {
        return guestPoints;
    }

    public void setGuestPoints(int guestPoints) {
        this.guestPoints = guestPoints;
    }

    public Team getHostTeam() {
        return hostTeam;
    }

    public void setHostTeam(Team hostTeam) {
        this.hostTeam = hostTeam;
    }

    public Team getGuestTeam() {
        return guestTeam;
    }

    public void setGuestTeam(Team guestTeam) {
        this.guestTeam = guestTeam;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.id;
        hash = 29 * hash + this.hostPoints;
        hash = 29 * hash + this.guestPoints;
        hash = 29 * hash + Objects.hashCode(this.hostTeam);
        hash = 29 * hash + Objects.hashCode(this.guestTeam);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Match other = (Match) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.hostPoints != other.hostPoints) {
            return false;
        }
        if (this.guestPoints != other.guestPoints) {
            return false;
        }
        if (!Objects.equals(this.hostTeam, other.hostTeam)) {
            return false;
        }
        if (!Objects.equals(this.guestTeam, other.guestTeam)) {
            return false;
        }
        return true;
    }
    
    
   
    @Override
    public String toString() {
        return "Match{" + "id=" + id + ", hostPoints=" + hostPoints + ", guestPoints=" + guestPoints + ", hostTeam=" + hostTeam + ", guestTeam=" + guestTeam + '}';
    }

    
    
}
