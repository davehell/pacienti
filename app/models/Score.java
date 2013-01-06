package models;

import play.db.jpa.*;
import play.data.validation.*;
import javax.persistence.*;
import java.util.*;
import play.data.binding.*;

@Entity
public class Score extends Model {

    @Required
    @ManyToOne
    public Examination vysetreni;

    @Required
    @ManyToOne
    public Operation vykon;

    @Required
    public int pocet;  //uctovany pocet


    public String toString()  {
        return vysetreni.nazev + " - " + vykon.kod + " - " + pocet;
    }



}
