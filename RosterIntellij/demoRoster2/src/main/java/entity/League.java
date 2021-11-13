package entity;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "PERSISTENCE_ROSTER_LEAGUE")
@Entity
public class League implements Serializable {
    private static final long serialVersionUID = 5060910864394673463L;

    @Id
    @Column(name = "id", nullable = false)
    protected String id;

    @Column(name = "name")
    protected String name;

    @Column(name = "sport")
    protected String sport;

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



}