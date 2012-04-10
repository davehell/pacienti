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

    @As("dd.MM.yyyy")
    public Date datumPrijeti;

    @OneToOne
    public User parafaPrijeti;

    @As("dd.MM.yyyy")
    public Date datumIzolace;

    @OneToOne
    public User parafaIzolace;

    public String toString() {
        return typ;
    }

    public static List<BioMaterial> getNeizolovana(AppModul modul) {
        List<BioMaterial> result = BioMaterial.find("pacient.modul = ? AND typ <> ? and datumIzolace is null and pacient.evRok >= ?", modul, "izolovaná DNA", 2010).fetch();
        return result;
    }
}
