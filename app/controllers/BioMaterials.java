package controllers;
 
import models.*;

import play.*;
import play.mvc.*;
import play.data.binding.*;
import java.util.*;

@With(Secure.class) 
public class BioMaterials extends Application {

  public static void index(Long pacient) {
    Patient pac = Patient.findById(pacient);
    notFoundIfNull(pac);

    List<BioMaterial> bioMaterialy = BioMaterial.find("byPacient", pac).fetch();
    render(bioMaterialy);
  }

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


  public static void save(Long id, Long pacientId, BioMaterial bioMaterial) {
//post.addComment(author, content);
    List<User> users = User.find("byModul", connected.modul).fetch();

    validation.valid(bioMaterial);
    if(validation.hasErrors()) {
        render("@form", bioMaterial, users);
    }

    if(id == null) {
        bioMaterial.pacient = Patient.findById(pacientId);
        bioMaterial.create();
    } else {
      BioMaterial _bioMaterial = BioMaterial.findById(id);
      _bioMaterial.typ = bioMaterial.typ;
      _bioMaterial.datumOdberu = bioMaterial.datumOdberu;
      _bioMaterial.datumPrijeti = bioMaterial.datumPrijeti;
      _bioMaterial.parafaPrijeti = bioMaterial.parafaPrijeti;
      _bioMaterial.datumIzolace = bioMaterial.datumIzolace;
      _bioMaterial.parafaIzolace = bioMaterial.parafaIzolace;

      _bioMaterial.save();

    }

    flash.success("Materiál %s uložen.", bioMaterial.typ);

    Patients.detail(pacientId);
  }
}