package models;

import play.db.jpa.*;
import play.data.validation.*;
import javax.persistence.*;
import play.data.binding.*;
import java.util.*;

@Entity
public class Examination extends Model {

    @Required
    @MaxSize(100)
    public String nazev;

    @MaxSize(300)
    public String popis;

    public int body;

    public boolean aktual;

    public boolean certif;

     @OneToMany(mappedBy="vysetreni", cascade=CascadeType.ALL)
     public List<Genotype> genotypy;



    public String toString() {
        return nazev;
    }      
}
