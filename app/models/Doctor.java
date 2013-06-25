package models;

import play.db.jpa.*;
import play.data.validation.*;
import javax.persistence.*;
import java.util.*;
import play.data.binding.*;
import utils.*;

@Table(
    uniqueConstraints=@UniqueConstraint(columnNames={"modul_id", "icz", "jmeno", "pracoviste"})
)

@Entity
public class Doctor extends Model {

    //@Required
    @ManyToOne
    public AppModul modul;

    @Required
    @MaxSize(20)
    public String icz;

    @MaxSize(20)
    public String odbornost;
        
    @Required
    @MaxSize(100)
    public String jmeno;

    @MaxSize(200)
    public String pracoviste;

    public String toString() {
        return jmeno + " (" + icz + ")";
    }

    public Doctor(AppModul modul, String jmeno, String icz, String odbornost, String pracoviste, String oldId) {
        this.modul = modul;
        this.icz = icz;
        this.odbornost = odbornost;
        this.jmeno = jmeno;
        this.pracoviste = pracoviste;
        this.save();
    }

    public static List<Doctor> getPocetVzorku(Date datumOd, Date datumDo, AppModul modul) {
      datumDo = Utils.getEndOfDay(datumDo);
      List<Doctor> result = Doctor.find(
          "SELECT new map(count(m.id) as pocet, m.pacient.lekar as lekar) FROM BioMaterial m WHERE m.pacient.lekar.modul = ? AND m.datumPrijeti >= ? and m.datumPrijeti <= ? GROUP BY m.pacient.lekar.id", modul, datumOd, datumDo
      ).fetch();

      return result;
    }

    public static List<Doctor> getPocetVzorku(Date datumOd, Date datumDo, Doctor lekar) {
      datumDo = Utils.getEndOfDay(datumDo);
      List<Doctor> result = Doctor.find(
          "SELECT new map(count(r.vysetreni.id) as pocet,  m.pacient.lekar as lekar, r.vysetreni.nazev as vysetreni) FROM BioMaterial m, Report r WHERE m.pacient.lekar = ? AND r.bioMaterial.id = m.id AND m.datumPrijeti >= ? and m.datumPrijeti <= ? GROUP BY r.vysetreni.id ORDER BY r.vysetreni.nazev", lekar, datumOd, datumDo
      ).fetch();

      return result;
    }
}
