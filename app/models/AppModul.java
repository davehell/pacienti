package models;

import play.db.jpa.*;
import play.data.validation.*;
import javax.persistence.*;

@Entity
public class AppModul extends Model {

    @Required
    public String nazev;


    public String toString() {
        return nazev;
    }      
}
