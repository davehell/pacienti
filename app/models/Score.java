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

    @MaxSize(10)
    public String kod;  //kod vykonu

    public int pocet; //uctovany pocet

    @Required
    @MaxSize(50)
    public String popis;  //slovni popis vykonu

    public int body;  //body za vykon

    public boolean jednouNaVzorek;  //uctovat pouze 1x na vzorek

    public boolean jednouDenne; //uctovat pouze 1x denne

    public String toString()  {
        return popis + " - " + vysetreni.nazev;
    }



}
