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
    @MaxSize(100)
    public String jmeno;

    @MaxSize(200)
    public String pracoviste;

    //@Required
    @ManyToOne
    public AppModul modul;

    public String toString() {
        return icz + " - " + jmeno;
    }

    public Doctor(String icz, String jmeno, String pracoviste) {
        this.icz = icz;
        this.jmeno = jmeno;
        this.pracoviste = pracoviste;
    }
}
