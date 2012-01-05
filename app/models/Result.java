package models;

import play.db.jpa.*;
import play.data.validation.*;
import javax.persistence.*;
import play.libs.*;

@Entity
public class Result extends Model {

    @Required
    @ManyToOne
    public Report zavZprava;

    @Required
    @OneToOne
    public Genotype genotyp;
    
    public String vysledek;

    public String vychozi;

    
}
