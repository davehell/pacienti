package models;

import play.db.jpa.*;
import play.data.validation.*;
import javax.persistence.*;
import java.math.*;

import play.data.binding.*;
import java.util.*;

@Entity
public class Patient extends Model {

    @Required
    @ManyToOne
    public AppModul modul;

    @Required
    public int evCislo;
    
    @Required
    public int evRok;
    
    @Required
    @MaxSize(30)
    public String rodneCislo;

    @Required
    @MaxSize(30)
    public String jmeno;

    @Required
    @MaxSize(30)
    public String prijmeni;

    @Required
    @ManyToOne
    public InsuranceCompany pojistovna;

    @ManyToOne
    public Doctor lekar;
    
    public boolean infSouhlas;

    public String diagnoza;

    public BigDecimal koncDna;

    @MaxSize(300)
    public String pozn;

    @MaxSize(300)
    public String verejnaPozn;

    @OneToMany(mappedBy="pacient", cascade=CascadeType.ALL)
    public List<BioMaterial> bioMaterialy;


    public Patient addBioMaterial(String typ) {
        BioMaterial novyBioMaterial = new BioMaterial(this, typ);
        this.bioMaterialy.add(novyBioMaterial);
        this.save();
        return this;
    }


    public String toString() {
        return jmeno + " " + prijmeni;
    }
}
