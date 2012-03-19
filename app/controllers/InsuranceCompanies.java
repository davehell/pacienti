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
@CRUD.For(InsuranceCompany.class)
public class InsuranceCompanies extends Application  {

  public static void index() {
    List<InsuranceCompany> pojistovny = InsuranceCompany.find("modul = ? order by nazev desc", connected.modul).fetch();
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
        newPojistovna.aktual = pojistovna.aktual;
        newPojistovna.save();
      }

      flash.success("Pojišťovna %s uložena.", pojistovna.toString());
      index();
  }


  public static void myDelete(Long id) {
      InsuranceCompany pojistovna = InsuranceCompany.findById(id);
      notFoundIfNull(pojistovna);
      try {
          pojistovna.delete();
          flash.success("Pojišťovna %s odebrána.", pojistovna.toString());
      }
      catch (Exception e) {
          flash.error("Pojišťovnu %s se nepodařilo odebrat.", pojistovna.toString());
          form(id);
      }

      index();
  }
}