package models;

import play.db.jpa.*;
import play.data.validation.*;
import javax.persistence.*;
import java.util.*;
import play.data.binding.*;


@Entity
public class BioMaterial extends Model {

    @ManyToOne
    public Patient pacient;

    @Required
    public String typ;

    @As("dd.MM.yyyy HH:mm")
    public Date datumOdberu;

    @As("dd.MM.yyyy HH:mm")
    public Date datumPrijeti;

    @OneToOne
    public User parafaPrijeti;

    @As("dd.MM.yyyy")
    public Date datumIzolace;

    @OneToOne
    public User parafaIzolace;

    public BioMaterial(String typ) {
        this.typ = typ;
    }

    public String toString() {
        return typ;
    }
}
