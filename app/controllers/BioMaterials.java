package controllers;
 
import models.*;

import play.*;
import play.mvc.*;
import play.data.binding.*;
import java.util.*;
import java.text.*;

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


  public static void mySave(Long bioMatId, BioMaterial bioMaterial, Long pacientId, String casOdberu, String casPrijeti) {
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

    if(bioMaterial.datumOdberu != null && casOdberu != null) {
      try {
        bioMaterial.datumOdberu = dtf.parse(df.format(bioMaterial.datumOdberu) + " " + casOdberu);
      } catch (ParseException e) {
        e.printStackTrace();
      }
    }

    if(bioMaterial.datumPrijeti != null && casPrijeti != null) {
      try {
        bioMaterial.datumPrijeti = dtf.parse(df.format(bioMaterial.datumPrijeti) + " " + casPrijeti);
      } catch (ParseException e) {
        e.printStackTrace();
      }
    }


    validation.valid(bioMaterial);
    if(validation.hasErrors()) {
        render("@form", bioMaterial, pacient, users, typyMaterialu);
    }

    if(bioMatId == null) {
        bioMaterial.pacient = pacient;
        try {
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

      try {
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


}