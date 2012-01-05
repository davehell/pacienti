package models;

import play.db.jpa.*;
import play.data.validation.*;
import javax.persistence.*;
import java.util.*;
import play.data.binding.*;


@Entity
public class BioMaterial extends Model {

    @ManyToOne
    @Required
    public Patient pacient;

    @Required
    public String typ;

    @As("yyyy-MM-dd")
    public Date datumOdberu;


    public Date datumPrijeti;

    @OneToOne
    public User parafaPrijeti;


    public Date datumIzolace;

    @OneToOne
    public User parafaIzolace;

    public BioMaterial(Patient pacient, String typ) {
        this.pacient = pacient;
        this.typ = typ;
    }

    public String toString() {
        return typ;
    }
}
