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

    @As("dd.MM.yyyy")
    public Date datumOdberu;
    @As("HH:mm")
    public Date casOdberu;

    @As("dd.MM.yyyy")
    public Date datumPrijeti;
    @As("HH:mm")
    public Date casPrijeti;

    @OneToOne
    public User parafaPrijeti;

    @As("dd.MM.yyyy")
    public Date datumIzolace;

    @OneToOne
    public User parafaIzolace;

    public String toString() {
        return typ;
    }

    public static List<BioMaterial> getNeizolovana() {
        List<BioMaterial> result = BioMaterial.find("typ <> ? and datumIzolace is null", "izolovaná DNA").fetch();
        return result;
    }
}
