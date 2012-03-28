package models;

import play.db.jpa.*;
import play.data.validation.*;
import javax.persistence.*;
import java.math.*;

import play.data.binding.*;
import java.util.*;


@Table(
    uniqueConstraints=@UniqueConstraint(columnNames={"modul_id", "evCislo", "evRok"})
)


@Entity
public class Patient extends Model {

    //@Required
    @ManyToOne
    @JoinColumn(name = "modul_id")
    public AppModul modul;

    @Required
    public Integer evCislo;
    
    @Required
    public Integer evRok;
    
    @Required
    @MaxSize(10)
    public String rcZac;

    @Required
    @MaxSize(5)
    public String rcKon;

    @MaxSize(300)
    public String titul;

    @Required
    @MaxSize(50)
    public String jmeno;

    @Required
    @MaxSize(100)
    public String prijmeni;


    @Required
    @ManyToOne
    public InsuranceCompany pojistovna;

    @ManyToOne(cascade=CascadeType.REMOVE)
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

    public Patient(AppModul modul, String evCislo, String evRok, String rcZac, String rcKon, String jmeno, String prijmeni, String pojistovna, String lekar, String infSouhlas, String diagnoza, String koncDna, String pozn, String verejnaPozn, String oldId) {
        this.modul = modul;
        this.evCislo = Integer.parseInt(evCislo);
        this.evRok = Integer.parseInt(evRok);
        this.rcZac = rcZac;
        this.rcKon = rcKon;
        this.jmeno = jmeno;
        this.prijmeni = prijmeni;
        this.infSouhlas = (infSouhlas == "PRAVDA") ? true : false;
        this.diagnoza = diagnoza;
        this.koncDna = koncDna;
        this.pozn = pozn;
        this.verejnaPozn = verejnaPozn;
        List<InsuranceCompany> pojistovny = InsuranceCompany.find("modul = ? and cislo = ?", modul, Integer.parseInt(pojistovna)).fetch();
        if (pojistovny != null && !pojistovny.isEmpty()) {
          this.pojistovna = pojistovny.get(0);
        }

        List<Doctor> lekari = Doctor.find("oldId = ?", lekar).fetch();
        if (lekari != null && !lekari.isEmpty()) {
          this.lekar = lekari.get(0);
        }

        try {
          this.save();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        
    }

    public static List<Patient> getLastPatients(AppModul modul, int count) {
        return Patient.find("modul = ? order by evRok desc, evCislo desc", modul).fetch(count);
    }

    public String getCDokladu() {
        if(modul.cDokladu == null || evCislo == null || evRok == null) return "";
        return modul.cDokladu + (evRok.toString().length() > 2 ? evRok.toString().substring(2) : evRok.toString()) + String.format("%4s", evCislo.toString()).replace(' ', '0');
    }

    public String getKod() {
        if(evCislo == null || evRok == null) return "";
        return modul.kod + ' ' + String.format("%3s", evCislo.toString()).replace(' ', '0')   + '/' + (evRok.toString().length() > 2 ? evRok.toString().substring(2) : evRok.toString());
    }

    public String getKonc() {
        if(koncDna == null) return "";
        return koncDna + " ng/&micro;l";
    }

    public String getRodneCislo() {
        return rcZac + "/" + rcKon;
    }

    public String toString() {
        return  prijmeni + " " + jmeno + (titul.length() > 0 ? ", " + titul : "");

    }

    //kod = KO-111/12
    public static Patient getByKod(String kod) {
        if(kod.isEmpty() || kod.length() != "KO-111/12".length()) return null;
        String modulKod = kod.substring(0, 2);
        Integer pacCislo = Integer.parseInt(kod.substring(3, 6));
        Integer pacRok = 2000 + Integer.parseInt(kod.substring(7, 9));

        Patient pacient = Patient.find("modul.kod = ? and evCislo = ? and evRok = ?", modulKod, pacCislo, pacRok).first();
        return pacient;
    }

    public static Patient getByModulAndId(AppModul modul, Long id) {
        Patient pacient = Patient.find("modul = ? and id = ?", modul, id).first();
        return pacient;
    }

    public static List<Patient> getPatientsWithSameRC(AppModul modul, Long id, String rcZac, String rcKon) {
        List<Patient> pacienti = Patient.find("id <> ? AND modul = ? AND rcZac = ? AND rcKon = ?", id, modul, rcZac, rcKon).fetch();
        return pacienti;
    }

}
