package models;

import play.db.jpa.*;
import play.data.validation.*;
import javax.persistence.*;
import java.util.*;
import play.data.binding.*;
import java.sql.Clob;
import utils.*;
import java.text.*;

@Entity
public class Report extends Model {

    @Required
    @ManyToOne
    public Patient pacient;
        
    @Required
    @ManyToOne
    public BioMaterial bioMaterial;
     
    @Required
    @ManyToOne
    public Examination vysetreni;

    //@OneToMany(mappedBy="zavZprava", cascade=CascadeType.ALL)
    //public List<Result> vysledky = new ArrayList<Result>();


    @Lob
    public String vysledek;

    @As("dd.MM.yyyy")
    public Date datumVysetreni;

    @As("dd.MM.yyyy HH:mm")
    public Date datumZpravy; //datum vytvoření závěrečné zprávy

    @OneToOne
    public User parafaVysetreni;

    @MaxSize(100)
    public String analyzuUvolnil;

    @MaxSize(200)
    public String analyzuProvedl;

    @MaxSize(100)
    public String vedouciLekar;

    @Lob
    public String zavZprava;

    public boolean pozitivni;

    public boolean neniCertif; //toto konkrétní vyšetření není certifikované

    @As("dd.MM.yyyy")
    public Date datumPCR1;

    @As("dd.MM.yyyy")
    public Date datumPCR2;

    @As("dd.MM.yyyy")
    public Date datumElForezy;

    @As("dd.MM.yyyy")
    public Date datumRevHybrid;

    @As("dd.MM.yyyy")
    public Date datumFragmentAn;

    @As("dd.MM.yyyy")
    public Date datumRTAn;

    @As("dd.MM.yyyy")
    public Date datumSekv;


    public Report(Patient pacient, BioMaterial bioMaterial, Examination vysetreni) {
        this.pacient = pacient;
        this.bioMaterial = bioMaterial;
        this.vysetreni = vysetreni;
    }

    public String kontrolaTAT() {
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        df.setTimeZone(TimeZone.getTimeZone("Europe/Prague"));
        Date limitTAT = null;
        Date dnes = new Date();
        int i = 0;

        if(this.bioMaterial.datumPrijeti == null) return null;

        limitTAT = this.bioMaterial.datumPrijeti;
        while(i++ < this.vysetreni.tat) {
            limitTAT = this.pacient.nextWorkingDay(limitTAT);
        }

        if(this.jeHotovo() && datumVysetreni != null && datumVysetreni.after(limitTAT)) {
            return "0;" + df.format(limitTAT) + ";-"; //limit byl dne d.m.Y, nebyl tedy splněn
        }
        else if(!this.jeHotovo() && dnes.after(limitTAT)) {
            return "0;" + df.format(limitTAT) + ";5"; //limit byl dne d.m.Y, což je před x dny
        }
        else if(!this.jeHotovo() && dnes.before(limitTAT)) {
            return "1;" + df.format(limitTAT) + ";-3"; //limit bude dne d.m.Y, což je za x dny
        }
        else {
            return "1;" + df.format(limitTAT) + ";-"; //limit byl dne d.m.Y, byl tedy splněn
        }
    }

    public LinkedHashMap<String,String> getVysl() {
        if(this.vysledek == null) return null;
        String[] splitVysl = this.vysledek.split("\\$", -1);

        LinkedHashMap<String,String> map = new LinkedHashMap<String,String>();
        for ( int i=0; i< splitVysl.length - 1 ; i+=2 ){
            map.put( splitVysl[i], splitVysl[i+1] );
        }
        return map;
    }

    public boolean jeHotovo() {
      return (this.analyzuUvolnil == null || this.analyzuUvolnil == "") ? false : true;
    }

    public static boolean setVysl(String pacKod, String marker, String vysl, boolean jenTest) {
      Patient pacient = Patient.getByKod(pacKod);
      if(pacient == null) return false;

      Iterator<Report> iterator = pacient.zpravy.iterator();
      Report rep = null;
      Genotype genotyp = null;
      while (iterator.hasNext()) {
        rep = iterator.next();
        genotyp = Genotype.find("vysetreni = ? and nazev = ?", rep.vysetreni, marker).first();
        if(genotyp != null) break;
      }

      if(genotyp == null) return false;

      Examination vysetreni = genotyp.vysetreni;
      if(vysetreni == null) return false;

      Report zprava = Report.find("pacient = ? and vysetreni = ?", pacient, vysetreni).first();
      if(zprava == null) return false;

//       Result vysledek = Result.find("zavZprava = ? and genotyp = ?", zprava, genotyp).first();
//       if(vysledek == null) return false;

      if(jenTest) return true;

      String vyslStr = "";
      String[] splitVysl = zprava.vysledek.split("\\$", -1);
      for ( int i=0; i< splitVysl.length - 1 ; i+=2 ){
        if(splitVysl[i].equals(marker)) {
            splitVysl[i+1] = vysl;
        }
        if(vyslStr.length() > 0) vyslStr += "$";
        vyslStr += (splitVysl[i] + "$" + splitVysl[i+1]);

      }


      try {
        zprava.vysledek = vyslStr;
        zprava.save();
      }
      catch (Exception e) {
        return false;
      }
      return true;
    }

    public static Long getPocet(Integer rok, String pohlavi, AppModul modul) {
      Calendar cal = new GregorianCalendar();
      cal.set(Calendar.YEAR, rok);
      cal.set(Calendar.DAY_OF_YEAR, 1);
      Date startDate = cal.getTime();

      cal.set(Calendar.YEAR, rok);
      cal.set(Calendar.MONTH, 11); // 11 = december
      cal.set(Calendar.DAY_OF_MONTH, 31); // new years eve
      Date endDate = cal.getTime();

      Query q = null;
      if(pohlavi.equals("M")) {
        q = JPA.em().createQuery ("SELECT COUNT(r.id) FROM Report r, Patient p WHERE r.pacient.id = p.id AND p.modul = :modul AND r.datumVysetreni >= :startDate AND r.datumVysetreni <= :endDate AND SUBSTR(p.rcZac, 3, 1)<>'5' AND SUBSTR(p.rcZac, 3, 1)<>'6'");
      }
      else if(pohlavi.equals("F")) {
        q = JPA.em().createQuery ("SELECT COUNT(r.id) FROM Report r, Patient p WHERE r.pacient.id = p.id AND p.modul = :modul AND r.datumVysetreni >= :startDate AND r.datumVysetreni <= :endDate AND (SUBSTR(p.rcZac, 3, 1)='5' OR SUBSTR(p.rcZac, 3, 1)='6')");
      }
      else {
        q = JPA.em().createQuery ("SELECT COUNT(r.id) FROM Report r, Patient p WHERE r.pacient.id = p.id AND p.modul = :modul AND r.datumVysetreni >= :startDate AND r.datumVysetreni <= :endDate");
      }

      q.setParameter ("startDate", startDate);
      q.setParameter ("endDate", endDate);
      q.setParameter ("modul", modul);
      return (Long) q.getSingleResult();
    }

    public static List<Report> getPocetDleTypu(Integer rok, String pohlavi, AppModul modul) {
      Calendar cal = new GregorianCalendar();
      cal.set(Calendar.YEAR, rok);
      cal.set(Calendar.DAY_OF_YEAR, 1);
      Date startDate = cal.getTime();

      cal.set(Calendar.YEAR, rok);
      cal.set(Calendar.MONTH, 11); // 11 = december
      cal.set(Calendar.DAY_OF_MONTH, 31); // new years eve
      Date endDate = cal.getTime();

      List<Report> result = null;

      if(pohlavi.equals("M")) {
        result = Report.find(
            "select new map(count(r.id) as pocet, r.vysetreni.nazev as nazev) from Report r, Patient p WHERE r.pacient.id = p.id AND p.modul = ? AND r.datumVysetreni >= ? AND r.datumVysetreni <= ? AND SUBSTR(p.rcZac, 3, 1)<>'5' AND SUBSTR(p.rcZac, 3, 1)<>'6' GROUP BY r.vysetreni.id ORDER BY nazev ASC", modul, startDate, endDate
        ).fetch();
      }
      else if(pohlavi.equals("F")) {
        result = Report.find(
            "select new map(count(r.id) as pocet, r.vysetreni.nazev as nazev) from Report r, Patient p WHERE r.pacient.id = p.id AND p.modul = ? AND r.datumVysetreni >= ? AND r.datumVysetreni <= ? AND (SUBSTR(p.rcZac, 3, 1)='5' OR SUBSTR(p.rcZac, 3, 1)='6') GROUP BY r.vysetreni.id ORDER BY nazev ASC", modul, startDate, endDate
        ).fetch();
      }
      else {
        result = Report.find(
            "select new map(count(r.id) as pocet, r.vysetreni.nazev as nazev) from Report r, Patient p WHERE r.pacient.id = p.id AND p.modul = ? AND r.datumVysetreni >= ? AND r.datumVysetreni <= ? GROUP BY r.vysetreni.id ORDER BY nazev ASC", modul, startDate, endDate
        ).fetch();
        
      }

      return result;
    }

    //počet patologických vyšetření za rok (pozitivni == true)
    //věk - započítat pouze pacienty mladších než tento věk
    //pro rok 2012 a věk 19: pacienti s datem narození > 21.12.1993 (tzn. 1.1.1994 a dál)
    public static Long pocetPatolog(Integer rok, Integer vek, AppModul modul) {
      Calendar cal = new GregorianCalendar();
      cal.set(Calendar.YEAR, rok);
      cal.set(Calendar.DAY_OF_YEAR, 1);
      Date startDate = cal.getTime();

      cal.set(Calendar.YEAR, rok);
      cal.set(Calendar.MONTH, 11); // 11 = december
      cal.set(Calendar.DAY_OF_MONTH, 31); // new years eve
      Date endDate = cal.getTime();
      
      Query q = null;
      if(vek > 0) {
        String limitRok = new Integer(rok - vek).toString().substring(2,4);
        q = JPA.em().createQuery ("SELECT COUNT(r.id) FROM Report r, Patient p WHERE SUBSTR(p.rcZac, 0, 2) > :limit AND r.pozitivni=true AND r.datumVysetreni >= :startDate AND r.datumVysetreni <= :endDate AND r.pacient.id = p.id AND p.modul = :modul ");
        q.setParameter ("limit", limitRok);
      }
      else {
        q = JPA.em().createQuery ("SELECT COUNT(r.id) FROM Report r, Patient p WHERE r.pozitivni=true AND r.datumVysetreni >= :startDate AND r.datumVysetreni <= :endDate AND r.pacient.id = p.id AND p.modul = :modul ");
      }

      q.setParameter ("startDate", startDate);
      q.setParameter ("endDate", endDate);
      q.setParameter ("modul", modul);
      
      return (Long) q.getSingleResult();
    }

    public static List<Report> getNeprovedena(Date datumOd, Date datumDo, AppModul modul, Long vysetreniId) {
        datumDo = Utils.getEndOfDay(datumDo);
        List<Report> result = null;
        if(vysetreniId == null || vysetreniId == 0) result = Report.find("pacient.modul = ? and datumVysetreni is null and bioMaterial.datumPrijeti >= ? and bioMaterial.datumPrijeti <= ?order by vysetreni.id asc, pacient.evCislo asc", modul, datumOd, datumDo).fetch();
        else result = Report.find("pacient.modul = ? and vysetreni.id = ? and datumVysetreni is null and bioMaterial.datumPrijeti >= ? and bioMaterial.datumPrijeti <= ?order by vysetreni.id asc, pacient.evCislo asc", modul, vysetreniId, datumOd, datumDo).fetch();
        return result;
    }


/*
Query q = JPA.em().createQuery ("UPDATE Doctor d SET d.jmeno = :jmeno ");
q.setParameter ("jmeno", "pok");
Integer updated = q.executeUpdate ();
Logger.info(updated.toString());
*/
//Query q = JPA.em().createQuery ("select r, sum(r.vysetreni.body) from Report r group by r.id order by r.vysetreni.nazev ");
//List<Report> results = (List<Report>) q.getResultList ();
/*
    List<Report> result = Report.find(
        "select new map(sum(r.vysetreni.body) as body, r.vysetreni.nazev as vyset) from Report r group by r.vysetreni.id having (select r2.pacient from Report r2)"
    ).fetch();
*/
}
