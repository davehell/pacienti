package models;

import play.db.jpa.*;
import play.data.validation.*;
import javax.persistence.*;
import java.math.*;

import play.data.binding.*;
import java.util.*;
import java.text.*;


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
    
    public String kod;

    @Required
    @MaxSize(10)
    public String rcZac;

    @Required
    @MaxSize(5)
    public String rcKon;

    @MaxSize(15)
    public String rodneCislo;

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

    @ManyToOne()
    public Doctor lekar;
    
    public boolean infSouhlas; //informovaný souhlas s uložením vzorku pro další analýzu

    public boolean infSouhlasVyuziti; //informovaný souhlas s anonymním využitím DNA k lékařskému výzkumu

    public String diagnoza; //základní diagnóza

    public String ostatniDiagnozy; //ostatní diagnózy

    public String koncDna;  //koncentrace DNA

    public String koncRna;  //koncentrace RNA

    @MaxSize(300)
    public String pozn; //interní poznámka

    @MaxSize(300)
    public String verejnaPozn;  //veřejná poznámka

    public boolean neuplnaZadanka; //statistika - počet neúplných žádanek

    @OneToMany(mappedBy="pacient", cascade=CascadeType.ALL)
    public List<BioMaterial> bioMaterialy;

    @OneToMany(mappedBy="pacient", cascade=CascadeType.ALL)
    public List<Report> zpravy;

    public Patient(AppModul modul, String evCislo, String evRok, String rcZac, String rcKon, String jmeno, String prijmeni, String pojistovna, String lekar, String infSouhlas, String diagnoza, String koncDna, String koncRna, String pozn, String verejnaPozn, String oldId) {
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
        this.koncRna = koncRna;
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

    public static List<Patient> getLastPatients(AppModul modul, int count, int minCislo, int maxCislo) {
        if(count > 0)
          return Patient.find("modul = ? and evCislo >= ? and evCislo <= ? order by evRok desc, evCislo desc", modul, minCislo, maxCislo).fetch(count);
        else
          return Patient.find("modul = ? and evCislo >= ? and evCislo <= ? and evRok >= 2013 order by evRok desc, evCislo desc", modul, minCislo, maxCislo).fetch();
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
        if(koncDna == null || koncDna == "") return "";
        return koncDna + " ng/&micro;l";
    }

    public String getRodneCislo() {
        return rcZac + "/" + rcKon;
    }

    public String toString() {
        return  prijmeni + " " + jmeno + (titul.length() > 0 ? ", " + titul : "");
    }

    public String getSkupina() {
        if(evCislo >= 3000) return modul.kod + "3000";
        else return modul.kod + "000";
    }

    //kod = KO 111/12 nebo KO 3000/12 nebo K 111/12 nebo k111/12
    public static Patient getByKod(String kod) {
        if(kod.isEmpty() || kod.length() < "K111/12".length() || kod.length() > "KO 3111/12".length()) return null;
        int mezera = kod.indexOf(" ");
        if(mezera == -1) mezera = kod.indexOf("-");
        if(mezera == -1 && kod.length() == "K111/12".length()) {
          kod = "K " + kod.substring(1);
          mezera = kod.indexOf(" ");
        }
        int lomeno = kod.indexOf("/");

        String modulKod = kod.substring(0, mezera).toUpperCase();
        Integer pacCislo = Integer.parseInt(kod.substring(mezera+1, lomeno));
        Integer pacRok = 2000 + Integer.parseInt(kod.substring(lomeno+1));

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

    public static Long getPocet(Integer rok, String pohlavi, AppModul modul) {
      Query q = null;
      if(pohlavi.equals("M")) {
        q = JPA.em().createQuery ("SELECT COUNT(id) FROM Patient p WHERE p.modul = :modul AND p.evRok = :rok AND SUBSTR(p.rcZac, 3, 1)<>'5' AND SUBSTR(p.rcZac, 3, 1)<>'6'");
      }
      else if(pohlavi.equals("F")) {
        q = JPA.em().createQuery ("SELECT COUNT(id) FROM Patient p WHERE p.modul = :modul AND p.evRok = :rok AND (SUBSTR(p.rcZac, 3, 1)='5' OR SUBSTR(p.rcZac, 3, 1)='6')");
      }
      else {
        q = JPA.em().createQuery ("SELECT COUNT(id) FROM Patient p WHERE p.modul = :modul AND p.evRok = :rok");
      }

      q.setParameter ("rok", rok);
      q.setParameter ("modul", modul);
      return (Long) q.getSingleResult();
    }

    public static Long getPocetNeuplnychZadanek(Integer rok, AppModul modul) {
      Query q = JPA.em().createQuery ("SELECT COUNT(id) FROM Patient p WHERE p.modul = :modul AND p.evRok = :rok AND p.neuplnaZadanka = true");
      q.setParameter ("rok", rok);
      q.setParameter ("modul", modul);
      return (Long) q.getSingleResult();
    }

    public BioMaterial getFirstBioMaterial() {
        return BioMaterial.find("pacient.id = ? order by id asc", this.id).first();
    }

    public Report getFirstReport() {
        return Report.find("pacient.id = ? order by id asc", this.id).first();
    }

    public String getZkratkyVysetreni() {
        List<Report> zpravy = Report.find("pacient.id = ? order by id asc", this.id).fetch();
        String zkratky = "";
        String zkratka = "";
        String sep = "";
        for(Report zprava : zpravy) {
          zkratka = zprava.vysetreni.zkratka;
          if(zkratka == null || zkratka == "") continue;
          zkratky += (sep + zkratka);
          sep = ", ";
        }
        return zkratky;
    }

    public Boolean vysetreniHotova() {
        List<Report> zpravy = Report.find("pacient.id = ? order by id asc", this.id).fetch();
        Boolean hotovo = null;
        for(Report zprava : zpravy) {
          if(zprava.jeHotovo()) hotovo = true;
          else return false;
        }
        return hotovo;
    }


    public String limityTat() {
        List<Report> zpravy = Report.find("pacient.id = ? order by id asc", this.id).fetch();
        String TATy = "";
        String vysl = "";
        String[] array = null;
        String sep = "";

        try {
          for(Report zprava : zpravy) {
            array = zprava.kontrolaTAT().split(";",-1);
            TATy += (sep + (array[0].equals("1") && !array[2].equals("-") && !array[2].equals("0") ? "-" : "") + array[2]);
            sep = ", ";
          }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }


        return TATy;
    }


    public Date nextWorkingDay(Date date) {
        Date nextDay = date;
        int blbec = 0;
        String[] holidays = {"01.01","01.05","08.05","05.07","06.07","28.09","28.10","17.11","24.12","25.12","26.12"}; //TODO: chybi velikonocni pondeli
        Calendar c = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        df.setTimeZone(TimeZone.getTimeZone("Europe/Prague"));

        while(blbec < 100) {
            blbec++;

            c.setTime(nextDay);
            c.add(Calendar.DATE, 1);
            nextDay = c.getTime();

            if(c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) continue;
            if(c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) continue;
            if(Arrays.asList(holidays).contains( df.format(nextDay).substring(0,5))) continue;

            break;
        }

        return nextDay;
    }

    public static List<Patient> getSouhlasySUlozenim(AppModul modul, int rok) {
        List<Patient> pacienti = Patient.find("modul = ? AND evRok = ? AND infSouhlas = true ORDER BY evCislo", modul, rok).fetch();
        return pacienti;
    }

    public static boolean setKonc(String pacKod, String konc, boolean jenTest) {
      Patient pacient = Patient.getByKod(pacKod);
      if(pacient == null) return false;

      if(jenTest) return true;

      try {
        pacient.koncDna = konc;
        pacient.save();
      }
      catch (Exception e) {
        return false;
      }
      return true;
    }
}
