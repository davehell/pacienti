package controllers;
 
import models.*;

import play.*;
import play.mvc.*;
import play.data.binding.*;
import java.util.*;

@With(Secure.class) 
public class BioMaterials extends Application {

  public static void form(Long id, Long pacientId) {
    List<User> users = User.find("byModul", connected.modul).fetch();
    Patient pacient = Patient.findById(pacientId);
    notFoundIfNull(pacient);
    String[] typyMaterialu = connected.modul.typyMaterialu.split(",");

    if(id != null) {
      BioMaterial bioMaterial = BioMaterial.findById(id);
      notFoundIfNull(bioMaterial);

      render(bioMaterial, pacient, users, typyMaterialu );
    }

    render(pacient, users, typyMaterialu);
  }


  public static void mySave(Long bioMatId, BioMaterial bioMaterial, Long pacientId) {
    List<User> users = User.find("byModul", connected.modul).fetch();
    Patient pacient = Patient.findById(pacientId);
    notFoundIfNull(pacient);
    String[] typyMaterialu = connected.modul.typyMaterialu.split(",");


    validation.valid(bioMaterial);
    if(validation.hasErrors()) {
        render("@form", bioMaterial, pacient, users, typyMaterialu);
    }

    if(bioMatId == null) {
        bioMaterial.pacient = pacient;
        bioMaterial.create();
    } else {
      BioMaterial newBioMat = BioMaterial.findById(bioMatId);
      newBioMat.typ = bioMaterial.typ;
      newBioMat.datumOdberu = bioMaterial.datumOdberu;
      newBioMat.datumPrijeti = bioMaterial.datumPrijeti;
      newBioMat.parafaPrijeti = bioMaterial.parafaPrijeti;
      newBioMat.datumIzolace = bioMaterial.datumIzolace;
      newBioMat.parafaIzolace = bioMaterial.parafaIzolace;

      newBioMat.save();
    }

    flash.success("Biologický materiál %s uložen.", bioMaterial.typ);

    Patients.detail(pacientId);
    
  }

  public static void myDelete(Long id) {
      BioMaterial bioMaterial = BioMaterial.findById(id);
      notFoundIfNull(bioMaterial);
      Patient pacient = bioMaterial.pacient;
      try {
        bioMaterial.delete();
        flash.success("Biologický materiál %s smazán.", bioMaterial.typ);
      }
      catch (Exception e) {
          flash.error("Biologický materiál %s se nepodařilo smazat.", bioMaterial.typ);
          form(bioMaterial.id, bioMaterial.pacient.id);
      }
      Patients.detail(pacient.id);
  }


}