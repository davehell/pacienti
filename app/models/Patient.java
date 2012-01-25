package models;

import play.db.jpa.*;
import play.data.validation.*;
import javax.persistence.*;
import java.math.*;

import play.data.binding.*;
import java.util.*;

@Entity
public class Patient extends Model {

    //@Required
    @ManyToOne
    public AppModul modul;

    @Required
    public Integer evCislo;
    
    @Required
    public Integer evRok;
    
    @Required
    @MaxSize(30)
    public String rodneCislo;

    @Required
    @MaxSize(100)
    public String jmeno;

    //@Required
    //@MaxSize(30)
    //public String prijmeni;

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

    public Patient(String jmeno) {
        this.jmeno = jmeno;
    }
/*
    public Patient(
        AppModul modul,
        int evCislo,
        int evRok,
        String rodneCislo,
        String jmeno,
        InsuranceCompany pojistovna,
        Doctor lekar,
        boolean infSouhlas,
        String diagnoza,
        BigDecimal koncDna,
        String pozn,
        String verejnaPozn)
    {
        this.modul = modul;
        this.evCislo = evCislo;
        this.evRok = evRok;
        this.rodneCislo = rodneCislo;
        this.jmeno = jmeno;
        this.pojistovna = pojistovna;
        this.lekar = lekar;
        this.infSouhlas = infSouhlas;
        this.diagnoza = diagnoza;
        this.koncDna = koncDna;
        this.pozn = pozn;
        this.verejnaPozn = verejnaPozn;
    }
*/

    public Patient addBioMaterial(String typ) {
        BioMaterial novyBioMaterial = new BioMaterial(typ);
        this.bioMaterialy.add(novyBioMaterial);
        this.save();
        return this;
    }

    public String getKod() {
        return "KO " + evCislo.toString() + '/' + evRok.toString();
    }

    public String toString() {
        return jmeno;
    }
}
