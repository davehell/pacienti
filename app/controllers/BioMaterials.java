package controllers;
 
import models.*;

import play.*;
import play.mvc.*;
import play.data.binding.*;
import java.util.*;
import java.text.*;

@Check("doctor")
@With(Secure.class) 
public class BioMaterials extends Application {

  public static void form(Long id, Long pacientId) {
    List<User> users = User.getDoctors(connected.modul);
    Patient pacient = Patient.findById(pacientId);
    notFoundIfNull(pacient);
    String[] typyMaterialu = {};

    if(connected.modul.typyMaterialu != null) {
      typyMaterialu = connected.modul.typyMaterialu.split(";");
    }


    if(id != null) {
      BioMaterial bioMaterial = BioMaterial.findById(id);
      notFoundIfNull(bioMaterial);

      render(bioMaterial, pacient, users, typyMaterialu );
    }

    render(pacient, users, typyMaterialu);
  }


  public static void mySave(Long bioMatId, BioMaterial bioMaterial, Long pacientId, String casOdberu, String casPrijeti, String koncDna, boolean nevyhovujiciVzorek) {
    List<User> users = User.find("modul = ? AND isAdmin = ?", connected.modul, false).fetch();
    Patient pacient = Patient.findById(pacientId);
    notFoundIfNull(pacient);
    String[] typyMaterialu = {};
    if(connected.modul.typyMaterialu != null) {
      typyMaterialu = connected.modul.typyMaterialu.split(",");
    }

    DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
    df.setTimeZone(TimeZone.getTimeZone("Europe/Prague"));
    DateFormat dtf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    dtf.setTimeZone(TimeZone.getTimeZone("Europe/Prague"));

    if(bioMaterial.datumOdberu != null) {
      try {
        bioMaterial.datumOdberu = dtf.parse(df.format(bioMaterial.datumOdberu) + upravCas(casOdberu));
      } catch (ParseException e) {
        e.printStackTrace();
      }
    }

    if(bioMaterial.datumPrijeti != null && casPrijeti != null) {
      try {
        bioMaterial.datumPrijeti = dtf.parse(df.format(bioMaterial.datumPrijeti) + upravCas(casPrijeti));
      } catch (ParseException e) {
        e.printStackTrace();
      }
    }


    validation.valid(bioMaterial);
    if(validation.hasErrors()) {
        render("@form", bioMaterial, pacient, users, typyMaterialu);
    }

    pacient.koncDna = koncDna;

    if(bioMatId == null) {
        bioMaterial.pacient = pacient;
        try {
          pacient.save();
          bioMaterial.create();
          appLog.add("biologický materiál", "create", bioMaterial.id);
        }
        catch (Exception e) {
          flash.error("Biologický materiál se nepodařilo vytvořit.");
          Patients.detail(pacientId);
        }
        
    } else {
      BioMaterial newBioMat = BioMaterial.findById(bioMatId);
      newBioMat.typ = bioMaterial.typ;
      newBioMat.datumOdberu = bioMaterial.datumOdberu;
      newBioMat.datumPrijeti = bioMaterial.datumPrijeti;
      newBioMat.parafaPrijeti = bioMaterial.parafaPrijeti;
      newBioMat.datumIzolace = bioMaterial.datumIzolace;
      newBioMat.parafaIzolace = bioMaterial.parafaIzolace;
      newBioMat.nevyhovujiciVzorek = bioMaterial.nevyhovujiciVzorek;

      try {
        pacient.save();
        newBioMat.save();
        appLog.add("biologický materiál", "update", bioMatId);
      }
      catch (Exception e) {
        Logger.error(e.getMessage());
        flash.error("Uložení se neprovedlo.");
        Patients.detail(pacientId);
      }
    }

    flash.success("biologický materiál %s uložen.", bioMaterial.typ);

    Patients.detail(pacientId);
  }

  public static void myDelete(Long id) {
      BioMaterial bioMaterial = BioMaterial.findById(id);
      notFoundIfNull(bioMaterial);
      Patient pacient = bioMaterial.pacient;
      try {
        bioMaterial.delete();
        appLog.add("biologický materiál", "delete", id);
        flash.success("Biologický materiál %s smazán.", bioMaterial.typ);
      }
      catch (Exception e) {
          flash.error("Biologický materiál %s se nepodařilo smazat.", bioMaterial.typ);
          form(bioMaterial.id, bioMaterial.pacient.id);
      }
      Patients.detail(pacient.id);
  }

  /*
   * Pokud je čas zadán pouze čísly (bez dvojtečky) doplní ho na formát hh:mm
   * U hodin doplní úvodní nulu.
   * Na začátku je mezera - čas se totiž přidává za datumu. Vznikne tak formát dd.MM.yyyy HH:mm
   */
  private static String upravCas(String cas) {
    if(cas.isEmpty()) return " 00:00";

    //735 -> 0735
    if(cas.length() == 3 && !cas.contains(":")) cas = "0" + cas;
    //7:35 -> 07:35
    if(cas.length() == 4 && cas.contains(":")) cas = "0" + cas;
    //0735 -> 07:35
    if(cas.length() == 4 && !cas.contains(":")) cas = cas.substring(0, 2) + ":" + cas.substring(2);

    //pokud neni hh:mm, tak chyba
    if(cas.length() != 5 || !cas.contains(":")) {
      flash.error("Poozor - chybný formát času: " + cas);
      return " 00:00";
    }

    return " " + cas;
  }

}