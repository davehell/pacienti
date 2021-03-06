package controllers;

import models.*;

import play.*;
import play.mvc.*;
import play.db.jpa.*;
import play.data.validation.*;
import javax.persistence.*;

import play.data.binding.*;
import java.util.*;
 
@Check("doctor")
@With(Secure.class) 
public class Doctors extends Application {

  public static void index() {
    //Query query = JPA.em().createQuery("select d from Doctor d order by icz desc");
    //List<Doctor> lekari = query.getResultList();

    List<Doctor> lekari = Doctor.find("modul = ? order by icz desc", connected.modul).fetch();
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
        try {
          lekar.create();
        }catch(Exception e) {
          flash.error("Lékař se stejným IČP a pracovištěm už existuje.");
          index();
        }
      } else {
        Doctor newLekar = Doctor.findById(id);
        newLekar.icz = lekar.icz;
        newLekar.jmeno = lekar.jmeno;
        newLekar.pracoviste = lekar.pracoviste;
        newLekar.odbornost = lekar.odbornost;
        try {
          newLekar.save();
        }catch(Exception e) {
          flash.error("Lékař se stejným IČP a pracovištěm už existuje.");
          form(id);
        }
      }

      flash.success("Lékař %s uložen.", lekar.toString());
      index();
  }


  public static void myDelete(Long id) {
      Doctor lekar = Doctor.findById(id);
      notFoundIfNull(lekar);
      try {
        lekar.delete();
        flash.success("Lékař %s odebrán.", lekar.toString());
      }
      catch (Exception e) {
          flash.error("Lékaře %s se nepodařilo odebrat.",lekar.toString());
          form(id);
      }
      index();
  }
}