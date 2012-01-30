package models;

import play.db.jpa.*;
import play.data.validation.*;
import javax.persistence.*;
import play.data.binding.*;
import java.util.*;

@Entity
public class Examination extends Model {

    @Required
    public String nazev;

    public String popis;

    public int body;

    public boolean aktual;

     @OneToMany(mappedBy="vysetreni", cascade=CascadeType.ALL)
     public List<Genotype> genotypy;



    public String toString() {
        return nazev;
    }      
}
