package controllers;

import models.*;

import play.*;
import play.mvc.*;
import play.data.binding.*;
import java.util.*;
import java.io.File;
import play.libs.IO;
import java.text.*;

import static play.modules.pdf.PDF.*;
import org.allcolor.yahp.converter.IHtmlToPdfTransformer;

@With(Secure.class)
public class Forms extends Application {

  public static void index(String typ, @As("dd.MM.yyyy") Date datumOd, @As("dd.MM.yyyy") Date datumDo, Long vysetreni) {
    notFoundIfNull(typ);
    List<Examination> seznamVysetreni = Examination.getActual();
    if(datumOd == null && datumDo == null) {
      datumOd = new Date();
      datumDo = new Date();
      render(typ, datumOd,datumDo, seznamVysetreni);
    }
    else {
      if(datumOd == null) datumOd = new Date();
      if(datumDo == null) datumDo = new Date();
    }

    if(typ.equals("neprovedena-vysetreni")) {
      neprovedena(datumOd, datumDo, vysetreni);
    }
    else if(typ.equals("pocty-vzorku")) {
      poctyVzorku(datumOd, datumDo);
    }
    else if(typ.equals("neizolovana-dna")) {
      neizolovana();
    }
  }

  @Check("doctor")
  public static void log() {
    DateFormat df = new SimpleDateFormat("yyyy-MM");
    String filename = df.format(new Date());
    List<String> lines = appLog.get(filename);
    render(lines);
  }


  @Check("doctor")
  public static void neprovedena(@As("dd.MM.yyyy") Date datumOd, @As("dd.MM.yyyy") Date datumDo, Long vysetreniId) {
      if(datumOd == null) datumOd = new Date();
      if(datumDo == null) datumDo = new Date();
      List<Examination> seznamVysetreni = Examination.getActual();
      Examination vysetreni = Examination.find("byId", vysetreniId).first();

      AppModul modul = connected.modul;
      List<Report> neprovVysetreni = Report.getNeprovedena(datumOd, datumDo, modul, vysetreniId);

      Options options = new Options();
      options.FOOTER = modul.formNeprovVys;
      IHtmlToPdfTransformer.PageSize ps = new IHtmlToPdfTransformer.PageSize(21.0, 29.7, 1.9, 1.9, 1.5, 1.5);
      options.pageSize = ps;

      //render(neprovVysetreni, datumOd, datumDo, modul);
      renderPDF(neprovVysetreni, vysetreni, datumOd, datumDo, modul, options);
  }

  public static void poctyVysetreni(Integer rok) {
      Integer aktRok = Calendar.getInstance().get(Calendar.YEAR);
      if(rok == null) rok = aktRok;

      Long pocetVysetreni = Report.getPocet(rok, "", connected.modul);
      Long pocetVysetreniM = Report.getPocet(rok, "M", connected.modul);
      Long pocetVysetreniF = Report.getPocet(rok, "F", connected.modul);

      Long pocetPacientu = Patient.getPocet(rok, "", connected.modul);
      Long pocetPacientuM = Patient.getPocet(rok, "M", connected.modul);
      Long pocetPacientuF = Patient.getPocet(rok, "F", connected.modul);
      
      Long pocetPatolog = Report.pocetPatolog(rok, 0, connected.modul);
      Long pocetPatologMladi = Report.pocetPatolog(rok, 19, connected.modul);

      List<Report> pocetDleTypu = Report.getPocetDleTypu(rok, connected.modul);

      render(aktRok, rok, pocetVysetreni, pocetVysetreniM, pocetVysetreniF, pocetPacientu, pocetPacientuM, pocetPacientuF, pocetPatolog, pocetPatologMladi, pocetDleTypu);
  }
  
  public static void poctyVzorku(@As("dd.MM.yyyy") Date datumOd, @As("dd.MM.yyyy") Date datumDo) {
      if(datumOd == null) datumOd = new Date();
      if(datumDo == null) datumDo = new Date();

      List<Doctor> lekari = Doctor.getPocetVzorku(datumOd, datumDo, connected.modul);

      Options options = new Options();
      //options.FOOTER = "";
      IHtmlToPdfTransformer.PageSize ps = new IHtmlToPdfTransformer.PageSize(21.0, 29.7, 1.9, 1.9, 1.5, 1.5);
      options.pageSize = ps;

      //render(lekari, datumOd, datumDo);
      renderPDF(lekari, datumOd, datumDo, options);
  }

  @Check("doctor")
  public static void neizolovana() {
      List<BioMaterial> bioMaterialy = BioMaterial.getNeizolovana(connected.modul);

      render(bioMaterialy);
  }

  @Check("doctor")
  public static void stitky(String evCisla, String skupina) {
    int min = 0;
    int max = 999;
    if(skupina != null) {
      if(skupina.equals("KO 000")) {
        min = 0;
        max = 999;
      }
      else if(skupina.equals("KO 3000")) {
        min = 3000;
        max = 4000;
      }
    }
    List<Patient> pacienti = Patient.getLastPatients(connected.modul, 100, min, max);
    if(evCisla == null) {
      render(pacienti);
    }
    else {
      List<Patient> stitky = new ArrayList<Patient>();
      String[] kody = evCisla.split(";");
      Patient pacient = null;
      for (String kod : kody) {
        pacient = Patient.getByKod(kod);
        if(pacient != null) stitky.add(pacient);
      }
      if(stitky.size() == 0) render(pacienti);
      Options options = new Options();
      IHtmlToPdfTransformer.PageSize ps = new IHtmlToPdfTransformer.PageSize(1.5, 1.5, 0.0, 0.0, 0.0, 0.0);
      options.pageSize = ps;      
      renderPDF(stitky, options);
    }
    
  }

  @Check("doctor")
  public static void vyslImport(File file, boolean test) {
    if(file != null) {
      List strOk = new ArrayList();
      List strErr = new ArrayList();
      List<String> lines = IO.readLines(file);
      String line = "";
      String[] columns = null;
      String pacKod = "";
      String marker = "";
      String vysl = "";
      String origVysl = "";
      Patient pacient = null;
      Examination vysetreni = null;
      boolean upraveno = false;

      Iterator<String> iterator = lines.iterator();
      while (iterator.hasNext()) {
        line = iterator.next();
        columns = line.split(",");
        if(columns.length < 8) continue;
        if(!columns[1].substring(0,2).equals(connected.modul.kod)) continue;

        pacKod = columns[1];
        marker = columns[2];
        origVysl = columns[7];
        vysl = "";

        try {
          if(marker.substring(0,3).equals("PAI")) {
            if(origVysl.equals("4G")) vysl = "4G/4G";
            else if(origVysl.equals("5G")) vysl = "5G/5G";
            else if(origVysl.equals("Both")) vysl = "4G/5G";
          }
          else {
            if(origVysl.equals("Both")) vysl = "mut/wt";
            else if(origVysl.equals("Undetermined")) vysl = "";
            else vysl = origVysl.substring(4);
          }
        }
        catch (Exception e) {
            vysl = "";
        }

        vysl = vysl.trim();
        pacient = Patient.getByKod(pacKod);

        upraveno = Report.setVysl(pacKod, marker, vysl, test);
        if(upraveno) strOk.add(pacKod + "," + marker + "," + origVysl + "," + vysl);
        else         strErr.add(pacKod + "," + marker + "," + origVysl + "," + vysl);
        //System.out.println(pacKod + " - " + marker + " - " + vysl);
      }

      String fileName = file.getName();
      render(fileName, test, strOk, strErr);
    }
    else {
      render(test);
    }
  }//vyslImport
  
  
}