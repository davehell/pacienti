package controllers;

import models.*;

import play.*;
import play.mvc.*;
import play.db.jpa.*;
import play.data.validation.*;
import javax.persistence.*;

import play.data.binding.*;
import java.util.*;
 
@With(Secure.class) 
public class Doctors extends Application {

  public static void index() {
  /*
Query q = JPA.em().createQuery ("UPDATE Doctor d SET d.jmeno = :jmeno ");
q.setParameter ("jmeno", "pok");
Integer updated = q.executeUpdate ();
Logger.info(updated.toString());
*/

// Doctor lekar;
// lekar = new Doctor("y","y","y");
// JPA.em().persist(lekar);
// lekar = new Doctor("z","z","z");
// JPA.em().persist(lekar);

List<Doctor> docs  = new ArrayList<Doctor>();
docs.add(new Doctor("llllllllllll","y","y"));
//docs.add(new Doctor("pppppppppppp","df","df"));

    	for (int i = 0; i < docs.size(); i++) {
        if(docs.get(i) == null) continue;
        JPA.em().persist(docs.get(i));
    	}



    Query query = JPA.em().createQuery("select d from Doctor d");
    List<Doctor> lekari = query.getResultList();
    render(lekari);
  }




  public static void form(Long id) {
    if(id != null) {
      Doctor lekar = Doctor.findById(id);
      notFoundIfNull(lekar);
      render(lekar);
    }

    render();
  }


  public static void mySave(Long id, Doctor lekar) {
      validation.valid(lekar);
      if(validation.hasErrors()) {
          render("@form", lekar);
      }
      if(id == null) {
        lekar.modul = connected.modul;
        lekar.create();
      } else {
        Doctor newLekar = Doctor.findById(id);
        newLekar.icz = lekar.icz;
        newLekar.jmeno = lekar.jmeno;
        newLekar.pracoviste = lekar.pracoviste;
        newLekar.save();
      }

      flash.success("Lékař %s uložen.", lekar.toString());
      index();
  }


  public static void myDelete(Long id) {
      Doctor lekar = Doctor.findById(id);
      notFoundIfNull(lekar);
      lekar.delete();
      flash.success("Lékař %s smazán.", lekar.toString());
      index();
  }
}