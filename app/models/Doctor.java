package models;

import play.db.jpa.*;
import play.data.validation.*;
import javax.persistence.*;
import java.util.*;
import play.data.binding.*;

@Entity
public class Doctor extends Model {

    //@Required
    @ManyToOne
    public AppModul modul;

    @Required
    @MaxSize(20)
    public String icz;
        
    @Required
    @MaxSize(100)
    public String jmeno;

    @MaxSize(200)
    public String pracoviste;

    public String toString() {
        return jmeno + " (" + icz + ")";
    }

    public Doctor(AppModul modul, String jmeno, String icz, String pracoviste, String oldId) {
        this.modul = modul;
        this.icz = icz;
        this.jmeno = jmeno;
        this.pracoviste = pracoviste;
        this.save();
    }

    public static List<Doctor> getPocetVzorku(Date datumOd, Date datumDo, AppModul modul) {
      List<Doctor> result = Doctor.find(
          "SELECT new map(count(m.id) as pocet, m.pacient.lekar as lekar) FROM BioMaterial m WHERE m.pacient.lekar.modul = ? AND m.datumPrijeti >= ? and m.datumPrijeti <= ? GROUP BY m.pacient.lekar.id", modul, datumOd, datumDo
      ).fetch();

      return result;
    }

    public static List<Doctor> getPocetVzorku(Date datumOd, Date datumDo, AppModul modul, Long lekar) {
      List<Doctor> result = Doctor.find(
          "SELECT new map(count(m.id) as pocet, m.pacient.lekar as lekar) FROM BioMaterial m WHERE m.pacient.lekar.modul = ? AND m.pacient.lekar.id = ? and m.datumPrijeti >= ? and m.datumPrijeti <= ? GROUP BY m.pacient.lekar.id", modul, lekar, datumOd, datumDo
      ).fetch();

      return result;
    }
}
