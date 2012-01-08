package controllers;
 
import models.*;
import play.*;
import play.mvc.*;
import play.db.jpa.*;
import play.data.validation.*;
import play.data.binding.*;
import java.util.*;

@With(Secure.class)  
public class AppModuls extends Application {

  public static void index() {
    List<AppModul> moduly = AppModul.findAll();
    render(moduly);
  }

  public static void form(Long id) {
    if(id != null) {
      AppModul modul = AppModul.findById(id);
      notFoundIfNull(modul);
      render(modul);
    }

    render();
  }


  public static void save(Long id, String nazev) {
    AppModul modul;

    if(id == null) {
        modul = new AppModul(nazev);
    } else {
        modul = AppModul.findById(id);
        modul.nazev = nazev;
    }

    validation.valid(modul);
    if(validation.hasErrors()) {
        params.flash();
        validation.keep();
        render("@form", modul);
    }

    modul.save();
    flash.success("modul %s uložen.", modul.nazev);
    index();
  }

  public static void saveModul(Long id, AppModul modul) {
      validation.valid(modul);
      if(validation.hasErrors()) {
          render("@form", modul);
      }
      if(id == null) {
          Logger.info("ID null -> create");
          modul.create();
      } else {
        Logger.info("ID je: " + id.toString());
        AppModul modulOld = AppModul.findById(id);
        modulOld.nazev = modul.nazev;
        modulOld.save();
      }
      
      flash.success("modul %s uložen.", modul.nazev);
      index();
  }


  public static void delete(Long id) {
      AppModul modul = AppModul.findById(id);
      modul.delete();
      flash.success("modul %s smazán.", modul.nazev);
      index();
  }

}