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

    @As("yyyy-MM-dd")
    public Date datumOdberu;

    @As("yyyy-MM-dd")
    public Date datumPrijeti;

    @OneToOne
    public User parafaPrijeti;

    @As("yyyy-MM-dd")
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
