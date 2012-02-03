package models;

import play.db.jpa.*;
import play.data.validation.*;
import javax.persistence.*;

@Entity
public class InsuranceCompany extends Model {
    
    @Required
    public Integer cislo;
     
    @Required
    @MaxSize(100)
    public String nazev;

    //@Required
    @ManyToOne
    public AppModul modul;

    public String toString() {
        return cislo + " - " + nazev;
    }    
}
