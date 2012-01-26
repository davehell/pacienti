package models;

import play.db.jpa.*;
import play.data.validation.*;
import javax.persistence.*;
import java.util.*;
import play.data.binding.*;

@Entity
public class AppModul extends Model {

    @Required
    public String nazev;

    @MaxSize(10)
    public String kod;

    public String vedouciLekari;

    public AppModul(String nazev) {
        this.nazev = nazev;
    }

    public String toString() {
        return nazev;
    }      
}
