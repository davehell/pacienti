package controllers;
 
import models.*;

import play.*;
import play.mvc.*;
import play.db.jpa.*;
import play.data.validation.*;
import javax.persistence.*;
import java.math.*;

import play.data.binding.*;
import java.util.*;
 
@With(Secure.class) 
public class Patients extends Application  {

  public static void index() {

    List<Patient> pacienti = Patient.find("byModul", connected.modul).fetch();
    render(pacienti);
  }

  public static void form(Long id) {
    if(id != null) {
      Patient pacient = Patient.findById(id);
      notFoundIfNull(pacient);
      render(pacient);
    }

    render();
  }


  public static void save(int evCislo, String jmeno) {
    AppModul modul = connected.modul;
    Patient pacient = new Patient(modul, evCislo, jmeno);

    validation.valid(pacient);
    if(validation.hasErrors()) {
        render("@form", pacient);
    }
    
    pacient.save();
    index();

  }

    public static void delete(Long id) {
        Patient pacient = Patient.findById(id);
        pacient.delete();
        flash.success("pacient %s smazán.", pacient.jmeno);
        index();
    }

}