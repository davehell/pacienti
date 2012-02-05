package models;

import play.db.jpa.*;
import play.data.validation.*;
import javax.persistence.*;
import play.libs.*;

@Entity
public class Result extends Model {

    //@Required
    @ManyToOne
    public Report zavZprava;

    @Required
    @OneToOne
    public Genotype genotyp;
    
    public String vysledek;

    public String vychozi;


    public Result(Report zprava, Genotype genotyp) {
        this.genotyp = genotyp;
        this.vysledek = vychozi;
        this.zavZprava = zprava;
    }


    public String toString() {
        return vysledek;
    }
}
