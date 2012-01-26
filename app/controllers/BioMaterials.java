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

    if(id != null) {
      BioMaterial bioMaterial = BioMaterial.findById(id);
      notFoundIfNull(bioMaterial);

      render(bioMaterial, pacient, users);
    }

    render(pacient, users);
  }


  public static void save(Long bioMatId, BioMaterial bioMaterial, Long pacientId) {
    List<User> users = User.find("byModul", connected.modul).fetch();
    Patient pacient = Patient.findById(pacientId);
    notFoundIfNull(pacient);


    validation.valid(bioMaterial);
    if(validation.hasErrors()) {
        render("@form", bioMaterial, pacient, users);
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

    flash.success("Materiál %s uložen.", bioMaterial.typ);

    Patients.detail(pacientId);
    
  }

  public static void delete(Long id) {
      BioMaterial bioMaterial = BioMaterial.findById(id);
      notFoundIfNull(bioMaterial);
      Patient pacient = bioMaterial.pacient;
      bioMaterial.delete();
      flash.success("Materiál %s smazán.", bioMaterial.typ);
      Patients.detail(pacient.id);
  }
}