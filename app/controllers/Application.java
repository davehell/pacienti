package controllers;

import play.*;
import play.mvc.*;

import play.db.jpa.*;
import javax.persistence.*;
import java.util.*;
import play.data.binding.*;

import models.*;
import applogger.*;

@With(Secure.class)
public class Application extends CRUD {

  static User connected = null;
  static AppLogger appLog = null;


  @Before
  static void globals() {
      connected = User.getByUsername(Security.connected());
      renderArgs.put("connected", connected);

      appLog = new AppLogger(connected);
  }

  //u kazdeho pacienta ulozi do "kod" jeho evidencni kod (kvuli fulltextovemu vyhledavani v archivu)
  public static void kod() {
    Patient pacient = null;
    Integer updated = -1;
    Query q = null;

    q = JPA.em().createQuery ("select p from Patient p order by id");
    //q.setMaxResults(10);
    List<Patient> pacienti =  q.getResultList ();


    for (int i = 0; i < pacienti.size(); i++) {
      pacient = pacienti.get(i);
      q = JPA.em().createQuery ("UPDATE Patient p SET p.kod = :kod WHERE p.id = :id ");
      q.setParameter ("id", pacient.id);
      q.setParameter ("kod", pacient.getKod());
      updated = q.executeUpdate ();
      //System.out.println(updated.toString());
  	}
  } //kod

/*
  //u kazde zav. zpravy posklada vsechny vysledky do jednoho retezce (report.vysledek)
  public static void vysl() {
    Report zprava = null;
    Result vysledek = null;
    List<Genotype> genotypy = null;
    Integer updated = 0;
    Query q = null;
    String vyslStr = "";
    Integer maxLen = 0;
    Map<String, String> vyslMap = null;

    q = JPA.em().createQuery ("select r from Report r order by id desc");
    q.setMaxResults(10);
    List<Report> zpravy =  q.getResultList();

    for (int i = 0; i < zpravy.size(); i++) {
      zprava = zpravy.get(i);
      //System.out.println(zprava.id.toString() + " - " + zprava.vysetreni.nazev);
      vyslMap = new LinkedHashMap<String, String>();
      vyslStr = "";

      if(zprava.vysledky.size() == 0) {
        //System.out.println("empty: " + zprava.id.toString());
        genotypy = zprava.vysetreni.genotypy;
        for (int k = 0; k < genotypy.size(); k++) {
          vyslMap.put(genotypy.get(k).nazev, genotypy.get(k).vychozi);
        }
      }
      else {
        for (int j = 0; j < zprava.vysledky.size(); j++) {
          vysledek = zprava.vysledky.get(j);
          vyslMap.put(vysledek.genotyp.nazev, vysledek.vysledek == null ? "" : vysledek.vysledek);
        }
      }

      if(vyslMap != null) {
        int j = 1;
        for (Map.Entry<String, String> entry : vyslMap.entrySet()) {
          vyslStr += (entry.getKey() + "$" + entry.getValue());
          if(j++ < vyslMap.size()) vyslStr += "$";
        }
        System.out.println(vyslStr);
      }

      if(vyslStr.length() > maxLen) maxLen = vyslStr.length();

      q = JPA.em().createQuery ("UPDATE Report r SET r.vysledek = :vysledek WHERE r.id = :id ");
      q.setParameter ("id", zprava.id);
      q.setParameter ("vysledek", vyslStr);
      updated = q.executeUpdate ();
  	}
  } //vysl
*/

  //slozeni rcZac a rcKon do rodneCislo
  public static void rc() {
    Patient pacient = null;
    Integer updated = -1;
    Query q = null;

    q = JPA.em().createQuery ("select p from Patient p order by id");
    //q.setMaxResults(10);
    List<Patient> pacienti =  q.getResultList ();


    for (int i = 0; i < pacienti.size(); i++) {
      pacient = pacienti.get(i);
      //System.out.println(pacient.id.toString() + " - " + pacient.rcZac + "/" + pacient.rcKon);
      q = JPA.em().createQuery ("UPDATE Patient p SET p.rodneCislo = :rodneCislo WHERE p.id = :id ");
      q.setParameter ("id", pacient.id);
      q.setParameter ("rodneCislo", pacient.rcZac + "/" + pacient.rcKon);
      updated = q.executeUpdate ();
      //System.out.println(updated.toString());
  	}
  } //rc
}