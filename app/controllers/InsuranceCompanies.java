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
public class InsuranceCompanies extends Application {
  public static void index() {
    List<InsuranceCompany> pojistovny = InsuranceCompany.find("byModul", connected.modul).fetch();
    render(pojistovny);
  }

  public static void form(Long id) {
    if(id != null) {
      InsuranceCompany pojistovna = InsuranceCompany.findById(id);
      notFoundIfNull(pojistovna);
      render(pojistovna);
    }

    render();
  }


  public static void mySave(Long id, InsuranceCompany pojistovna) {
      validation.valid(pojistovna);
      if(validation.hasErrors()) {
          render("@form", pojistovna);
      }
      if(id == null) {
        pojistovna.modul = connected.modul;
        pojistovna.create();
      } else {
        InsuranceCompany newPojistovna = InsuranceCompany.findById(id);
        newPojistovna.cislo = pojistovna.cislo;
        newPojistovna.nazev = pojistovna.nazev;
        newPojistovna.save();
      }

      flash.success("Pojišťovna %s uložena.", pojistovna.toString());
      index();
  }


  public static void delete(Long id) {
      InsuranceCompany pojistovna = InsuranceCompany.findById(id);
      notFoundIfNull(pojistovna);
      pojistovna.delete();
      flash.success("Pojišťovna %s smazána.", pojistovna.toString());
      index();
  }
}