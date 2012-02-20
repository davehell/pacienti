package models;

import play.db.jpa.*;
import play.data.validation.*;
import javax.persistence.*;
import java.util.*;
import play.data.binding.*;

@Entity
public class Genotype extends Model {

    @Required
    @ManyToOne
    public Examination vysetreni;

    @Required
    @MaxSize(50)
    public String nazev;
    


    public String toString()  {
        return nazev;
    }
    
  
    
}
