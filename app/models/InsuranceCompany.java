package models;

import play.db.jpa.*;
import play.data.validation.*;
import javax.persistence.*;

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

    public String toString() {
        return cislo + " - " + nazev;
    }    
}
