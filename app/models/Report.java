package models;

import play.db.jpa.*;
import play.data.validation.*;
import javax.persistence.*;
import java.util.*;
import play.data.binding.*;
import java.sql.Clob;

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


    public LinkedHashMap<String,String> getVysl() {
        if(this.vysledek == null) return null;
        String[] splitVysl = this.vysledek.split("\\$", -1);

        LinkedHashMap<String,String> map = new LinkedHashMap<String,String>();
        for ( int i=0; i< splitVysl.length - 1 ; i+=2 ){
            map.put( splitVysl[i], splitVysl[i+1] );
        }
        return map;
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

    public static Long getPocetRok(Integer rok) {
      Calendar cal = new GregorianCalendar();
      cal.set(Calendar.YEAR, rok);
      cal.set(Calendar.DAY_OF_YEAR, 1);
      Date startDate = cal.getTime();
      cal.set(Calendar.DAY_OF_YEAR, 366); // for leap years
      Date endDate = cal.getTime();

      Query q = JPA.em().createQuery ("SELECT COUNT(id) FROM Report r WHERE r.datumVysetreni >= :startDate AND r.datumVysetreni <= :endDate");
      q.setParameter ("startDate", startDate);
      q.setParameter ("endDate", endDate);
      return (Long) q.getSingleResult();
    }

    public static List<Report> getNeprovedena(Date datumOd, Date datumDo, AppModul modul) {
        //List<Report> result = Report.findAll();
        List<Report> result = Report.find("pacient.modul = ? and datumVysetreni is null and bioMaterial.datumPrijeti >= ? and bioMaterial.datumPrijeti <= ?order by vysetreni.id asc, pacient.evCislo asc", modul, datumOd, datumDo).fetch();
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

        return result;
    }
}
