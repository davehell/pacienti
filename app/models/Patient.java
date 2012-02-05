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


    @Required
    @ManyToOne
    public InsuranceCompany pojistovna;

    @ManyToOne
    public Doctor lekar;
    
    public boolean infSouhlas;

    public String diagnoza;

    public String koncDna;

    @MaxSize(300)
    public String pozn;

    @MaxSize(300)
    public String verejnaPozn;

    @OneToMany(mappedBy="pacient", cascade=CascadeType.ALL)
    public List<BioMaterial> bioMaterialy;

    @OneToMany(mappedBy="pacient", cascade=CascadeType.ALL)
    public List<Report> zpravy;

    public Patient(String evCislo, String evRok, String rodneCislo, String jmeno, String infSouhlas, String diagnoza, String koncDna, String pozn, String verejnaPozn) {
        this.evCislo = new Integer(evCislo);
        this.evRok = new Integer(evRok);
        this.rodneCislo = rodneCislo;
        this.jmeno = jmeno;
        this.infSouhlas = (infSouhlas == "PRAVDA") ? true : false;
        this.koncDna = koncDna;
        this.pozn = pozn;
        this.verejnaPozn = verejnaPozn;
    }


    public String getKod() {
        if(evCislo == null || evRok == null) return "";
        return modul.kod + ' ' + String.format("%3s", evCislo.toString()).replace(' ', '0')   + '/' + (evRok.toString().length() > 2 ? evRok.toString().substring(2) : evRok.toString());
    }

    public String toString() {
        return jmeno;
    }
}
