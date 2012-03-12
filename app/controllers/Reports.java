package controllers;

import models.*;

import play.*;
import play.mvc.*;
import play.data.binding.*;
import java.util.*;
import static play.modules.pdf.PDF.*;

import org.allcolor.yahp.converter.IHtmlToPdfTransformer;
import org.allcolor.yahp.converter.IHtmlToPdfTransformer.PageSize;
import org.allcolor.yahp.*;

@With(Secure.class)
public class Reports extends Application {

  public static void report(Long id) {
    Report zprava = Report.findById(id);
    notFoundIfNull(zprava);
    //render(zprava);

    Options options = new Options();
    //options.FOOTER = "lkjdklsfkjhdkjhgkjhk";
    //options.filename = "pok.pdf";


    IHtmlToPdfTransformer.PageSize pok2 = new IHtmlToPdfTransformer.PageSize(21.0, 29.7, 1.9, 1.9, 1.5, 1.5);
    options.pageSize = pok2;

    renderPDF(zprava, options);
  }

  public static void form(Long id, Long pacientId) {
    Patient pacient = Patient.findById(pacientId);
    notFoundIfNull(pacient);
    List<BioMaterial> bioMaterialy = BioMaterial.find("byPacient", pacient).fetch();
    List<Examination> vysetreni = Examination.find("byAktual", true).fetch();
    List<User> users = User.find("modul = ? AND isAdmin = ?", connected.modul, false).fetch();
    String[] vedouciLekari = connected.modul.vedouciLekari.split(",");
    String[] uvolnujiAnalyzu = connected.modul.uvolnujiAnalyzu.split(",");

    if(id != null) {
      Report zprava = Report.findById(id);
      notFoundIfNull(zprava);
      render(zprava, pacient, bioMaterialy, vysetreni, users, vedouciLekari, uvolnujiAnalyzu);
    }

    render(pacient, bioMaterialy, vysetreni, users, vedouciLekari, uvolnujiAnalyzu);
  }


  public static void mySave(Long zpravaId, Report zprava, Long pacientId, List<Result> vysledky) {
    Patient pacient = Patient.findById(pacientId);
    notFoundIfNull(pacient);
    List<BioMaterial> bioMaterialy = BioMaterial.findAll();
    List<Examination> vysetreni = Examination.find("byAktual", true).fetch();
    List<User> users = User.find("modul = ? AND isAdmin = ?", connected.modul, false).fetch();
    Result vysledek = null;

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

      if(vysledky != null) {
      	for (int i = 0; i < vysledky.size(); i++) {
          if(vysledky.get(i) == null) continue;
          Result vysl = Result.findById(vysledky.get(i).id);
          vysl.vysledek = vysledky.get(i).vysledek;
          vysl.save();
      	}
      }

      newZprava.datumVysetreni = zprava.datumVysetreni;
      newZprava.parafaVysetreni = zprava.parafaVysetreni;
      newZprava.parafaUvolneni = zprava.parafaUvolneni;
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

  public static void myDelete(Long id) {
      Report report = Report.findById(id);
      notFoundIfNull(report);
      Patient pacient = report.pacient;

      try {
        report.delete();
        flash.success("Vyšetření %s odebráno.", report.vysetreni);
      }
      catch (Exception e) {
          flash.error("Vyšetření %s se nepodařilo odebrat.",report.vysetreni);
          form(id, pacient.id);
      }

      Patients.detail(pacient.id);
  }

  public static void neprovedena(@As("dd.MM.yyyy") Date datumOd, @As("dd.MM.yyyy") Date datumDo) {
      if(datumOd == null) datumOd = new Date();
      if(datumDo == null) datumDo = new Date();
    
      List<Report> vysetreni = Report.getNeprovedena(datumOd, datumDo);
      AppModul modul = connected.modul;

      render(vysetreni, datumOd, datumDo, modul);
  }
}