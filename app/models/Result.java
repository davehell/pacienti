package models;

import play.db.jpa.*;
import play.data.validation.*;
import javax.persistence.*;
import play.libs.*;

@Entity
public class Result extends Model {

    //@Required
    @ManyToOne
    public Report zavZprava;

    @Required
    @OneToOne
    public Genotype genotyp;
    
    public String vysledek;

    public String vychozi;


    public Result(Report zprava, Genotype genotyp) {
        this.genotyp = genotyp;
        this.vysledek = vychozi;
        this.zavZprava = zprava;
    }


    public String toString() {
        return vysledek;
    }

    public static boolean setVysl(String pacKod, String marker, String vysl) {
      Patient pacient = Patient.getByKod(pacKod);
      if(pacient == null) return false;

      Genotype genotyp = Genotype.find("nazev = ?", marker).first();
      if(genotyp == null) return false;

      Examination vysetreni = genotyp.vysetreni;
      if(vysetreni == null) return false;

      Report zprava = Report.find("pacient = ? and vysetreni = ?", pacient, vysetreni).first();
      if(zprava == null) return false;

      Result vysledek = Result.find("zavZprava = ? and genotyp = ?", zprava, genotyp).first();
      if(vysledek == null) return false;


      try {
        vysledek.vysledek = vysl;
        vysledek.save();
      }
      catch (Exception e) {
        return false;
      }
      return true;

// Query q = JPA.em().createQuery ("UPDATE Doctor d SET d.jmeno = :jmeno ");
// q.setParameter ("jmeno", "pok");
// Integer updated = q.executeUpdate ();

    }
}
