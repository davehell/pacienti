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

  public static void index() {
    //slozeni rcZac a rcKon do rodneCislo
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
  } //index

}