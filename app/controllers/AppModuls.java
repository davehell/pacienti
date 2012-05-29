package controllers;
 
import models.*;
import play.*;
import play.mvc.*;
import play.db.jpa.*;
import play.data.validation.*;
import play.data.binding.*;
import java.util.*;
import play.libs.*;

@With(Secure.class)  
public class AppModuls extends Application {

  public static void uziv() {
    User uziv = connected;
    String jmeno = uziv.jmeno;
    render(uziv);
  }

  public static void saveUziv(Long id, String jmeno, String parafa, String pozice, String heslo, String heslo2, String origHeslo) {
      User uziv = null;
      String msg = "";

      if(id != null) {
        uziv = User.findById(id);
      }
      notFoundIfNull(uziv);


      if(heslo.length() > 0) {
        if(User.connect(uziv.username, origHeslo) == null) {
          flash.error("Zadáno chybné heslo!");
          uziv();
        }

        if(!heslo.equals(heslo2)) {
          flash.error("Hesla se neshodují!");
          uziv();
        }
        uziv.passwordHash = Codec.hexSHA1(heslo);
        msg = "Heslo bylo změněno.";
      }

      uziv.jmeno = jmeno;
      uziv.parafa = parafa;
      uziv.pozice = pozice;

      try {
          uziv.save();
          flash.success("Profil uživatele %s uložen. %s", jmeno, msg);
      }
      catch (Exception e) {
        Logger.error(e.getMessage());
        flash.error("Uložení se neprovedlo.");
      }

      uziv();
  }
/*
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
*/
}