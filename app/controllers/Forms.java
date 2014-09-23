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

  public static void index(String typ, @As("dd.MM.yyyy") Date datumOd, @As("dd.MM.yyyy") Date datumDo, Long vysetreni, Long lekar) {
    notFoundIfNull(typ);
    List<Examination> seznamVysetreni = Examination.getActual();
    List<Doctor> seznamLekaru = Doctor.find("modul = ? order by icz desc", connected.modul).fetch();

    //datumy nezadány - zobrazí se formuláø s kolonkami datum od, do a typem vyšetøení
    if(datumOd == null && datumDo == null) { 
      datumOd = new Date();
      datumDo = new Date(); //zde se pouze vkládá datum do kalendáøe, proto se nemusí nastavovat èas na 23:59:59
      render(typ, datumOd,datumDo, seznamVysetreni, seznamLekaru);
      //konec funkce index
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
    else if(typ.equals("pocty-vysetreni-lekar")) {
      poctyVysLekar(datumOd, datumDo, lekar);
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

      List<Report> pocetDleTypu = Report.getPocetDleTypu(rok, "", connected.modul);
      List<Report> pocetDleTypuM = Report.getPocetDleTypu(rok, "M", connected.modul);
      List<Report> pocetDleTypuF = Report.getPocetDleTypu(rok, "F", connected.modul);

      Long pocetNeuplnychZadanek = Patient.getPocetNeuplnychZadanek(rok, connected.modul);
      Long pocetNevyhovVzorku = BioMaterial.getPocetNevyhovujicich(rok, connected.modul);
      Long pocetVzorkuCelkem = BioMaterial.getPocet(rok, connected.modul);
      Long pocetOpakovanychVysetreni = Report.getPocetOpakovanych(rok, connected.modul);

      render(aktRok, rok, pocetVysetreni, pocetVysetreniM, pocetVysetreniF, pocetPacientu, pocetPacientuM, pocetPacientuF, pocetPatolog, pocetPatologMladi, pocetDleTypu, pocetDleTypuM, pocetDleTypuF,
      pocetNeuplnychZadanek, pocetNevyhovVzorku, pocetVzorkuCelkem, pocetOpakovanychVysetreni);
  }

  public static void poctyUlozenych(Integer rok) {
      Integer aktRok = Calendar.getInstance().get(Calendar.YEAR);
      if(rok == null) rok = aktRok;
      List<Patient> souhlasy = Patient.getSouhlasySUlozenim(connected.modul, rok);
      render(aktRok, rok, souhlasy);
  }
  
  public static void poctyVzorku(@As("dd.MM.yyyy") Date datumOd, @As("dd.MM.yyyy") Date datumDo) {
      List<Doctor> lekari = Doctor.getPocetVzorku(datumOd, datumDo, connected.modul);

      Options options = new Options();
      //options.FOOTER = "";
      IHtmlToPdfTransformer.PageSize ps = new IHtmlToPdfTransformer.PageSize(21.0, 29.7, 1.9, 1.9, 1.5, 1.5);
      options.pageSize = ps;

      //render(lekari, datumOd, datumDo);
      renderPDF(lekari, datumOd, datumDo, options);
  }


  public static void poctyVysLekar(@As("dd.MM.yyyy") Date datumOd, @As("dd.MM.yyyy") Date datumDo, Long lekarId) {
      Doctor lekar = Doctor.find("modul = ? AND id = ?", connected.modul, lekarId).first();
      notFoundIfNull(lekar);
      List<Doctor> poctyVys = Doctor.getVysetreni(datumOd, datumDo, lekar);

      Options options = new Options();
      //options.FOOTER = "";
      IHtmlToPdfTransformer.PageSize ps = new IHtmlToPdfTransformer.PageSize(21.0, 29.7, 1.9, 1.9, 1.5, 1.5);
      options.pageSize = ps;

      //render(poctyVys, datumOd, datumDo, lekar);
      renderPDF(poctyVys, datumOd, datumDo, lekar, options);
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
      String origMarker = "";
      String vysl = "";
      String origVysl = "";
      Patient pacient = null;
      Examination vysetreni = null;
      boolean upraveno = false;
      boolean oblastVysledku = false;

      Iterator<String> iterator = lines.iterator();
      while (iterator.hasNext()) {
        line = iterator.next();

        if(connected.modul.kod.equals("KO")) {
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
              else vysl = origVysl.substring(4);
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
        } //ostrava
        else if(connected.modul.kod.equals("K")) {
          if(line.equals("[Results]")) oblastVysledku = true;
          if(line.equals("[Raw Data]")) break;
          if(!oblastVysledku) continue;

          columns = line.split("	");
          if(columns.length < 10) continue;

          if(columns[1].length() < 7) continue; // pozn: "k001/14".length() == 7
          if(!columns[1].substring(0,1).equalsIgnoreCase("k")) continue;

          pacKod = columns[1];
          origMarker = columns[2];
          origVysl = columns[8];
          if(origMarker.equals("Leiden")) marker = "Factor V Leiden (G1691A)*";
          else if(origMarker.equals("MTHFR A/C")) marker = "MTHFR (A1298C)*";
          else if(origMarker.equals("MTHFR C/T")) marker = "MTHFR (C677T)*";
          else if(origMarker.equals("Factor II")) marker = "Factor II (G20210A)*";
          else if(origMarker.equals("Factor XIII")) marker = "Factor XIII (V34L)*";
          else if(origMarker.equals("Factor V R2")) marker = "Factor V R2 (H1299R)*";

          else if(origMarker.equals("EPCR A/G")) marker = "EPCR (A4600G)*";
          else if(origMarker.equals("EPCR G/C")) marker = "EPCR (G4678C)*";
          
          else if(origMarker.equals("PAI-1")) marker = "PAI-1 (4G/5G)*";
          else marker = origMarker;

          try {
            vysl = origVysl;
            if(vysl.equals("Both")) vysl = "mut/wt";
            else if(vysl.equals("Undetermined")) vysl = "";

            if(origMarker.substring(0,3).equals("PAI")) {
              if(origVysl.equals("Homozygous 1/1")) vysl = "5G/5G";
              else if(origVysl.equals("Homozygous 2/2")) vysl = "4G/4G";
              else if(origVysl.equals("Heterozygous 1/2")) vysl = "4G/5G";
            }
            else if(origMarker.substring(0,6).equals("EPCR A")) {
              if(origVysl.equals("Homozygous 1/1")) vysl = "A/A";
              else if(origVysl.equals("Homozygous 2/2")) vysl = "G/G";
              else if(origVysl.equals("Heterozygous 1/2")) vysl = "G/A";
            }
            else if(origMarker.substring(0,6).equals("EPCR G")) {
              if(origVysl.equals("Homozygous 1/1")) vysl = "G/G";
              else if(origVysl.equals("Homozygous 2/2")) vysl = "C/C";
              else if(origVysl.equals("Heterozygous 1/2")) vysl = "C/G";
            }
            else {
              if(origVysl.equals("Homozygous 1/1")) vysl = "wt/wt";
              else if(origVysl.equals("Homozygous 2/2")) vysl = "mut/mut";
              else if(origVysl.equals("Heterozygous 1/2")) vysl = "mut/wt";
            }
          }
          catch (Exception e) {
              vysl = "";
          }
        } //brno

        vysl = vysl.trim();
        pacient = Patient.getByKod(pacKod);

        upraveno = Report.setVysl(pacKod, marker, vysl, test);
        if(upraveno) strOk.add(pacKod + "," + marker + "," + origVysl + "," + vysl);
        else         strErr.add(pacKod + "," + marker + "," + origVysl + "," + vysl);
        //System.out.println(pacKod + " - " + marker + " - " + vysl);
      } //while iterator

      String fileName = file.getName();
      render(fileName, test, strOk, strErr);
    }
    else {
      render(test);
    }
  }//vyslImport
  
  
}