package controllers;
 
import models.*;

import play.*;
import play.mvc.*;
import play.db.jpa.*;
import play.data.validation.*;
import javax.persistence.*;
import play.data.binding.*;
import java.util.*;
import java.io.*;
import java.text.*;
import play.libs.*;

@With(Secure.class) 
public class Patients extends Application  {

  public static void index() {
    List<Patient> pacienti = Patient.getLastPatients(connected.modul, 100);
    render(pacienti);
  }

  public static void detail(Long id) {
    Patient pacient = Patient.findById(id);
    notFoundIfNull(pacient);
    List<InsuranceCompany> pojistovny = InsuranceCompany.find("modul = ? order by cislo asc", connected.modul).fetch();
    List<Doctor> lekari = Doctor.find("modul = ? order by icz asc", connected.modul).fetch();
    List<Patient> stejnaRC = Patient.find("id <> ? AND modul = ? AND rcZac = ? AND rcKon = ?", id, connected.modul, pacient.rcZac, pacient.rcKon).fetch();
    render(pacient, pojistovny, lekari, stejnaRC);
  }

  public static void form(Long id) {
    List<InsuranceCompany> pojistovny = InsuranceCompany.getAktual(connected.modul);
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
          flash.error("Pacient se stejným evidenčním číslem již existuje.");
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


    try
    { //ulozeni do logu
      DateFormat dtf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
      String time = dtf.format(new Date());
      DateFormat df = new SimpleDateFormat("yyyy-MM");
      String filename = df.format(new Date());

      FileWriter out = new FileWriter("logs/" + filename + ".txt", true);

      BufferedWriter writer = new BufferedWriter(out);
      writer.write(time + ";" + connected.parafa + ";" + "Patient.mySave;" + pacient.getKod() +  "\r\n");
      writer.close();
    }
    catch(Exception e) {
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
