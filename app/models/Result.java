package models;

import play.db.jpa.*;
import play.data.validation.*;
import javax.persistence.*;
import play.libs.*;
import java.util.*;

@Entity
public class Result extends Model {

    //@Required
    @ManyToOne
    public Report zavZprava;

    @Required
    @OneToOne
    public Genotype genotyp;
    
    public String vysledek;


    public Result(Report zprava, Genotype genotyp) {
        this.genotyp = genotyp;
        this.vysledek = genotyp.vychozi;
        this.zavZprava = zprava;
    }


    public String toString() {
        return vysledek;
    }

    public static boolean setVysl(String pacKod, String marker, String vysl) {
      Patient pacient = Patient.getByKod(pacKod);
      if(pacient == null) return false;

      Iterator<Report> iterator = pacient.zpravy.iterator();
      Report rep = null;
      Genotype genotyp = null;
      while (iterator.hasNext()) {
        rep = iterator.next();
        //System.out.println(rep.vysetreni.nazev);
        genotyp = Genotype.find("vysetreni = ? and nazev = ?", rep.vysetreni, marker).first();
        if(genotyp != null) break;
      }

      //Genotype genotyp = Genotype.find("nazev = ?", marker).first();
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
    }
}
