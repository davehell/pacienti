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


  public static void save(Long id, int evCislo, int evRok, String rodneCislo, String jmeno, Long pojistovnaId, Long lekarId, boolean infSouhlas, String diagnoza, BigDecimal koncDna, String pozn, String verejnaPozn) {
    Patient pacient = null;
    Doctor lekar = null;
    InsuranceCompany pojistovna = null;

    if(lekarId != null) lekar = Doctor.findById(lekarId);
    if(pojistovnaId != null) pojistovna = InsuranceCompany.findById(pojistovnaId);

    if(id == null) {
        AppModul modul = connected.modul;
        pacient = new Patient(modul, evCislo, evRok, rodneCislo, jmeno, pojistovna, lekar, infSouhlas, diagnoza, koncDna, pozn, verejnaPozn);
    } else {
        pacient = Patient.findById(id);
        pacient.evCislo = evCislo;
        pacient.evRok = evRok;
        pacient.rodneCislo = rodneCislo;
        pacient.jmeno = jmeno;
        pacient.pojistovna = pojistovna;
        pacient.lekar = lekar;
        pacient.infSouhlas = infSouhlas;
        pacient.diagnoza = diagnoza;
        pacient.koncDna = koncDna;
        pacient.pozn = pozn;
        pacient.verejnaPozn = verejnaPozn;
    }
    

    validation.valid(pacient);
    if(validation.hasErrors()) {
        params.flash(); // add http parameters to the flash scope
        validation.keep(); // keep the errors for the next request
        render("@form", pacient);
    }
    
    
    pacient.save();
    flash.success("pacient %s uložen.", pacient.jmeno);
    index();

  }


  public static void delete(Long id) {
      Patient pacient = Patient.findById(id);
      pacient.delete();
      flash.success("pacient %s smazán.", pacient.jmeno);
      index();
  }

}
