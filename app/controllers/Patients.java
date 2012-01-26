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

  public static void detail(Long id) {
    Patient pacient = Patient.findById(id);
    notFoundIfNull(pacient);

    render(pacient);
  }

  public static void form(Long id) {
    List<InsuranceCompany> pojistovny = InsuranceCompany.find("byModul", connected.modul).fetch();
    List<Doctor> lekari = Doctor.find("byModul", connected.modul).fetch();

    if(id != null) {
      Patient pacient = Patient.findById(id);
      notFoundIfNull(pacient);

      if(pacient.modul.id != connected.modul.id) {
        notFound();
      }

      render(pacient, pojistovny, lekari);
    }

    render(pojistovny, lekari);
  }


  public static void save(Long id, Patient pacient) {
    List<InsuranceCompany> pojistovny = InsuranceCompany.find("byModul", connected.modul).fetch();
    List<Doctor> lekari = Doctor.find("byModul", connected.modul).fetch();

    validation.valid(pacient);
    if(validation.hasErrors()) {
      render("@form", pacient, lekari);
    }

    if(id == null) {
        pacient.modul = connected.modul;
        pacient.create();
        id = pacient.id;
    } else {
      Patient _pacient = Patient.findById(id);
      _pacient.evCislo = pacient.evCislo;
      _pacient.evRok = pacient.evRok;
      _pacient.rodneCislo = pacient.rodneCislo;
      _pacient.jmeno = pacient.jmeno;
      _pacient.pojistovna = pacient.pojistovna;
      _pacient.lekar = pacient.lekar;
      _pacient.infSouhlas = pacient.infSouhlas;
      _pacient.diagnoza = pacient.diagnoza;
      _pacient.koncDna = pacient.koncDna;
      _pacient.pozn = pacient.pozn;
      _pacient.verejnaPozn = pacient.verejnaPozn;

      _pacient.save();
    }

    flash.success("Pacient %s uložen.", pacient.jmeno);
    detail(id);
  }


  public static void delete(Long id) {
      Patient pacient = Patient.findById(id);
      if(pacient.modul.id != connected.modul.id) {
        notFound();
      }
      pacient.delete();
      flash.success("pacient %s smazán.", pacient.jmeno);
      index();
  }


}
