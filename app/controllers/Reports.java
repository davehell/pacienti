package controllers;

import models.*;

import play.*;
import play.mvc.*;
import play.data.binding.*;
import java.util.*;
import static play.modules.pdf.PDF.*;

import org.allcolor.yahp.converter.IHtmlToPdfTransformer;


@With(Secure.class)
public class Reports extends Application {

  public static void report(Long id) {
    Report zprava = Report.findById(id);
    notFoundIfNull(zprava);
    //render(zprava);
    LinkedHashMap<String,String> vyslMap = zprava.getVysl();

    Options options = new Options();
    //options.FOOTER = "lkjdklsfkjhdkjhgkjhk";
    //options.filename = "pok.pdf";


    IHtmlToPdfTransformer.PageSize pok2 = new IHtmlToPdfTransformer.PageSize(21.0, 29.7, 1.9, 1.9, 1.5, 1.5);
    options.pageSize = pok2;

    renderPDF(zprava, vyslMap, options);
  }

  public static void form(Long id, Long pacientId) {
    Patient pacient = Patient.getByModulAndId(connected.modul, pacientId);
    notFoundIfNull(pacient);
    List<BioMaterial> bioMaterialy = BioMaterial.find("byPacient", pacient).fetch();
    List<Examination> vysetreni = Examination.getActual();
    List<User> users = User.find("modul = ? AND isAdmin = ?", connected.modul, false).fetch();

    String[] vedouciLekari = {};
    String[] uvolnujiAnalyzu = {};
    String[] provadiAnalyzu = {};
    LinkedHashMap<String,String> vyslMap = new LinkedHashMap<String,String>();

    if(connected.modul.vedouciLekari != null) {
      vedouciLekari = connected.modul.vedouciLekari.split(";");
    }
    if(connected.modul.uvolnujiAnalyzu != null) {
      uvolnujiAnalyzu = connected.modul.uvolnujiAnalyzu.split(";");
    }
    if(connected.modul.provadiAnalyzu != null) {
      provadiAnalyzu = connected.modul.provadiAnalyzu.split(";");
    }
    

    if(id != null) {
      Report zprava = Report.findById(id);
      notFoundIfNull(zprava);
      vyslMap = zprava.getVysl();
      render(zprava, vyslMap, pacient, bioMaterialy, vysetreni, users, vedouciLekari, uvolnujiAnalyzu, provadiAnalyzu);
    }

    render(pacient, bioMaterialy, vysetreni, users, vedouciLekari, uvolnujiAnalyzu);
  }


  public static void mySave(Long zpravaId, Report zprava, Long pacientId, String[] vysledky, String[] markery) {
    Patient pacient = Patient.getByModulAndId(connected.modul, pacientId);
    notFoundIfNull(pacient);
    List<BioMaterial> bioMaterialy = BioMaterial.findAll();
    List<Examination> vysetreni = Examination.getActual();
    List<User> users = User.find("modul = ? AND isAdmin = ?", connected.modul, false).fetch();
    String vysledek = "";
    Genotype genotyp = null;
    LinkedHashMap<String,String> vyslMap = new LinkedHashMap<String,String>();


    if(vysledky != null) {
      int j = 1; int len = vysledky.length;
      for (int i = 0; i < vysledky.length; i++) {
        if(vysledky[i] == null) {len--; continue;}
        vysledek += (markery[i] + "$" + vysledky[i].replace("$", "&#36;"));
        if(j++ < len) vysledek += "$";
    	}
    }

    zprava.pacient = pacient;
    zprava.vysledek = vysledek;
    vyslMap = zprava.getVysl();

    validation.valid(zprava);
    if(validation.hasErrors()) {
        render("@form", zprava, vyslMap, pacient, bioMaterialy, vysetreni, users);
    }

    if(zpravaId == null) {
        List<Genotype> genotypes = Genotype.find("byVysetreni", zprava.vysetreni).fetch();
        int k = 1;
        for(Iterator<Genotype> i = genotypes.iterator(); i.hasNext(); ) {
          genotyp = i.next();
          vysledek += (genotyp.nazev + "$" + genotyp.vychozi);
          if(k++ < genotypes.size()) vysledek += "$";
        }
        zprava.vysledek = vysledek;

        try {
          zprava.create();
          appLog.add("vyšetření", "create", zprava.id);
        }
        catch (Exception e) {
          flash.error("Vyšetření se nepodařilo vytvořit.");
          Patients.detail(pacientId);
        }
    } else {
      Report newZprava = Report.findById(zpravaId);

      newZprava.vysledek = vysledek;
      newZprava.datumVysetreni = zprava.datumVysetreni;
      newZprava.parafaVysetreni = zprava.parafaVysetreni;
      newZprava.analyzuUvolnil = zprava.analyzuUvolnil;
      newZprava.analyzuProvedl = zprava.analyzuProvedl;
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

      try {
        newZprava.save();
        appLog.add("vyšetření", "update", zpravaId);
      }
      catch (Exception e) {
        Logger.error(e.getMessage());
        flash.error("Uložení se neprovedlo.");
        Patients.detail(pacientId);
      }
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
        appLog.add("vyšetření", "delete", id);
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
    
      AppModul modul = connected.modul;
      List<Report> vysetreni = Report.getNeprovedena(datumOd, datumDo, modul);

      render(vysetreni, datumOd, datumDo, modul);
  }
}