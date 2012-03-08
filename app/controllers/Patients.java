package controllers;
 
import models.*;

import play.*;
import play.mvc.*;
import play.db.jpa.*;
import play.data.validation.*;
import javax.persistence.*;

import play.data.binding.*;
import java.util.*;
 


import play.libs.*;

@With(Secure.class) 
public class Patients extends Application  {

  public static void index() {

    List<Patient> pacienti = Patient.find("byModul", connected.modul).fetch();
    render(pacienti);
  }

  public static void detail(Long id) {
    Patient pacient = Patient.findById(id);
    notFoundIfNull(pacient);
    List<InsuranceCompany> pojistovny = InsuranceCompany.find("modul = ? order by cislo asc", connected.modul).fetch();
    List<Doctor> lekari = Doctor.find("modul = ? order by icz asc", connected.modul).fetch();

    render(pacient, pojistovny, lekari);
  }

  public static void form(Long id) {
    List<InsuranceCompany> pojistovny = InsuranceCompany.find("modul = ? order by cislo asc", connected.modul).fetch();
    List<Doctor> lekari = Doctor.find("modul = ? order by icz asc", connected.modul).fetch();

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


  public static void mySave(Long id, Patient pacient) {
    List<InsuranceCompany> pojistovny = InsuranceCompany.find("byModul", connected.modul).fetch();
    List<Doctor> lekari = Doctor.find("byModul", connected.modul).fetch();

    validation.valid(pacient);
    if(validation.hasErrors()) {
      render("@form", pacient, lekari, pojistovny);
    }

    if(id == null) {
        pacient.modul = connected.modul;
        try {
          pacient.create();
        }
        catch (Exception e) {
          flash.error("Pacienta se stejným evidenčním číslem již existuje.");
          render("@form", pacient, lekari, pojistovny);
        }
        id = pacient.id;
    } else {
      Patient _pacient = Patient.findById(id);
      _pacient.evCislo = pacient.evCislo;
      _pacient.evRok = pacient.evRok;
      _pacient.rcZac = pacient.rcZac;
      _pacient.rcKon = pacient.rcKon;
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

    flash.success("Informace o pacientovi %s byly úspěšně uloženy.", pacient.getKod());
    detail(id);
  }


  public static void myDelete(Long id) {
      Patient pacient = Patient.findById(id);
      if(pacient.modul.id != connected.modul.id) {
        notFound();
      }
      try {
        pacient.delete();
        flash.success("Pacient %s odebrán.", pacient.getKod());
      }
      catch (Exception e) {
          Logger.error(e.getMessage());
          flash.error("Pacienta %s se nepodařilo odebrat.",pacient.getKod());
          detail(pacient.id);
      }
      index();
  }


}
