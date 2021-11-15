/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jakarta.tutorial.roster.entity;

import java.io.Serializable;
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
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Match(Long id, int hostPoints, int guestPoints, Team hostTeam, Team guestTeam) {
        this.id = id;
        this.hostPoints = hostPoints;
        this.guestPoints = guestPoints;
        this.hostTeam = hostTeam;
        this.guestTeam = guestTeam;
    }

    public Match(Long id, int hostPoints, int guestPoints) {
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
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Match)) {
            return false;
        }
        Match other = (Match) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Match{" + "id=" + id + ", hostPoints=" + hostPoints + ", guestPoints=" + guestPoints + ", hostTeam=" + hostTeam + ", guestTeam=" + guestTeam + '}';
    }

    
    
}
