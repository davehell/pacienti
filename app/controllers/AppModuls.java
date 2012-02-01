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


  public static void mySave(Long id, AppModul modul) {
      validation.valid(modul);
      if(validation.hasErrors()) {
          render("@form", modul);
      }
      if(id == null) {
          modul.create();
      } else {
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