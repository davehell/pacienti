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
    public Date datumIzolace; //datum izolace DNA

    @As("dd.MM.yyyy")
    public Date datumIzolaceRNA;

    @OneToOne
    public User parafaIzolace;

    public boolean nevyhovujiciVzorek; //statistika - počet nevyhovujících vzorků

    public String toString() {
        return typ;
    }

    public static List<BioMaterial> getNeizolovana(AppModul modul) {
        List<BioMaterial> result = BioMaterial.find("pacient.modul = ? AND typ <> ? and datumIzolace is null and datumIzolaceRNA is null and pacient.evRok >= ?", modul, "izolovaná DNA", 2010).fetch();
        return result;
    }

    public static Long getPocet(Integer rok, AppModul modul) {
      Query q = JPA.em().createQuery ("SELECT COUNT(b.id) FROM BioMaterial b, Patient p WHERE p.id = b.pacient.id AND p.modul = :modul AND p.evRok = :rok");
      q.setParameter ("rok", rok);
      q.setParameter ("modul", modul);
      return (Long) q.getSingleResult();
    }

    public static Long getPocetNevyhovujicich(Integer rok, AppModul modul) {
      Query q = JPA.em().createQuery ("SELECT COUNT(b.id) FROM BioMaterial b, Patient p WHERE p.id = b.pacient.id AND p.modul = :modul AND p.evRok = :rok AND b.nevyhovujiciVzorek = true");
      q.setParameter ("rok", rok);
      q.setParameter ("modul", modul);
      return (Long) q.getSingleResult();
    }
}
