package models;

import play.db.jpa.*;
import play.data.validation.*;
import javax.persistence.*;
import java.util.*;

@Entity
public class InsuranceCompany extends Model {

    //@Required
    @ManyToOne
    public AppModul modul;

    @Required
    public Integer cislo;
     
    @Required
    @MaxSize(100)
    public String nazev;

    public boolean aktual;

    public String toString() {
        return cislo + " - " + nazev;
    }    

    public static List<InsuranceCompany> getAktual(AppModul modul) {
      return InsuranceCompany.find("modul = ? and aktual = ? order by cislo asc", modul, true).fetch();
    }

}
