package models;

import play.db.jpa.*;
import play.data.validation.*;
import javax.persistence.*;
import java.util.*;
import play.data.binding.*;

@Entity
public class Doctor extends Model {

    @Required
    @MaxSize(20)
    public String icz;
        
    @Required
    @MaxSize(100)
    public String jmeno;

    @MaxSize(200)
    public String pracoviste;

    //@Required
    @ManyToOne
    public AppModul modul;

    public String toString() {
        return jmeno + " (" + icz + ")";
    }

    public Doctor(String icz, String jmeno, String pracoviste) {
        this.icz = icz;
        this.jmeno = jmeno;
        this.pracoviste = pracoviste;
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
