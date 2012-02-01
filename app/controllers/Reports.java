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


  public static void mySave(Long zpravaId, Report zprava, Long pacientId, List<Result> vysledky) {
    Patient pacient = Patient.findById(pacientId);
    notFoundIfNull(pacient);
    List<BioMaterial> bioMaterialy = BioMaterial.findAll();
    List<Examination> vysetreni = Examination.find("byAktual", true).fetch();
    List<User> users = User.find("byModul", connected.modul).fetch();
    Result vysledek = null;
    //List<String> vedLekari = "aa";
// String sArray[] = new String[] { "Array 1", "Array 2", "Array 3" };
// // convert array to list
// List<String> lList = Arrays.asList(sArray);


    zprava.pacient = pacient;


    validation.valid(zprava);
    if(validation.hasErrors()) {
        render("@form", zprava, pacient, bioMaterialy, vysetreni, users);
    }

    if(zpravaId == null) {
// for(Post p : Post.<Post>findAll()) {
//     p.delete();
// }
        List<Genotype> genotypes = Genotype.find("byVysetreni", zprava.vysetreni).fetch();
        for(Iterator<Genotype> i = genotypes.iterator(); i.hasNext(); ) {
          vysledek = new Result(zprava, i.next());
          if(vysledek == null) continue;
          zprava.vysledky.add(vysledek);
        }
        zprava.create();
    } else {
      Report newZprava = Report.findById(zpravaId);

    	for (int i = 0; i < vysledky.size(); i++) {
        if(vysledky.get(i) == null) continue;
        System.out.println(vysledky.get(i).id + " ss " + vysledky.size());
        Result vysl = Result.findById(vysledky.get(i).id);
        vysl.vysledek = vysledky.get(i).vysledek;
        vysl.save();
    	}

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

  public static void neprovedena(@As("dd.MM.yyyy") Date datumOd, @As("dd.MM.yyyy") Date datumDo) {
      if(datumOd == null) datumOd = new Date();
      if(datumDo == null) datumDo = new Date();
    
      List<Report> vysetreni = Report.getNeprovedena(datumOd, datumDo);

      render(vysetreni, datumOd, datumDo);
  }
}