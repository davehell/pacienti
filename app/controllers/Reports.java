package controllers;

import models.*;

import play.*;
import play.mvc.*;
import play.data.binding.*;
import java.util.*;


@With(Secure.class)
public class Reports extends Application {

  public static void form(Long id, Long pacientId) {
    Patient pacient = Patient.findById(pacientId);
    notFoundIfNull(pacient);
    List<BioMaterial> bioMaterialy = BioMaterial.findAll();
    List<Examination> vysetreni = Examination.find("byAktual", true).fetch();
    List<User> users = User.find("byModul", connected.modul).fetch();
    //List<String> vedLekari = "aa";


    if(id != null) {
      Report zprava = Report.findById(id);
      notFoundIfNull(zprava);
      render(zprava, pacient, bioMaterialy, vysetreni, users);
    }

    render(pacient, bioMaterialy, vysetreni, users);
  }


  public static void save(Long zpravaId, Report zprava, Long pacientId) {
    Patient pacient = Patient.findById(pacientId);
    notFoundIfNull(pacient);
    List<BioMaterial> bioMaterialy = BioMaterial.findAll();
    List<Examination> vysetreni = Examination.find("byAktual", true).fetch();
    List<User> users = User.find("byModul", connected.modul).fetch();
    //List<String> vedLekari = "aa";


    zprava.pacient = pacient;

    validation.valid(zprava);
    if(validation.hasErrors()) {
        render("@form", zprava, pacient, bioMaterialy, vysetreni, users);
    }

    if(zpravaId == null) {
        zprava.create();
    } else {
      Report newZprava = Report.findById(zpravaId);
      newZprava.datumVysetreni = zprava.datumVysetreni;
      newZprava.parafaVysetreni = zprava.parafaVysetreni;
      newZprava.vedouciLekar = zprava.vedouciLekar;
      newZprava.zavZprava = zprava.zavZprava;
      newZprava.pozitivni = zprava.pozitivni;
      newZprava.datumPCR1 = zprava.datumPCR1;
      newZprava.datumPCR2 = zprava.datumPCR2;
      newZprava.datumElForezy = zprava.datumElForezy;
      newZprava.datumRevHybrid = zprava.datumRevHybrid;
      newZprava.datumFragmentAn = zprava.datumFragmentAn;
      newZprava.datumRTAn = zprava.datumRTAn;
      newZprava.datumSekv = zprava.datumSekv;
      newZprava.save();

    }

    flash.success("Vyšetření %s uloženo.", zprava.vysetreni.nazev);

    Patients.detail(pacientId);

  }

  public static void delete(Long id) {
      Report report = Report.findById(id);
      notFoundIfNull(report);
      Patient pacient = report.pacient;
      report.delete();
      flash.success("Vyšetření %s smazáno.", report.vysetreni);
      Patients.detail(pacient.id);
  }
}