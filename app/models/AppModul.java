package models;

import play.db.jpa.*;
import play.data.validation.*;
import javax.persistence.*;

@Entity
public class AppModul extends Model {

    @Required
    public String nazev;

    @MaxSize(10)
    public String kod;

    public AppModul(String nazev) {
        this.nazev = nazev;
    }

    public String toString() {
        return nazev;
    }      
}
