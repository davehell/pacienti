package models;

import play.db.jpa.*;
import play.data.validation.*;
import javax.persistence.*;

@Entity
public class Doctor extends Model {

    @Required
    @MaxSize(20)
    public String icz;
        
    @Required
    @MaxSize(10)
    public String titul;
     
    @Required
    @MaxSize(30)
    public String jmeno;

    @Required
    @MaxSize(30)
    public String prijmeni;

    @Required
    @MaxSize(100)
    public String pracoviste;

    @Required
    @ManyToOne
    public AppModul modul;

    public String toString() {
        return icz + " - " + titul + " " + jmeno + " " + prijmeni;
    }      
}
