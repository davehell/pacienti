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

  public static void index(String typ, @As("dd.MM.yyyy") Date datumOd, @As("dd.MM.yyyy") Date datumDo) {
    notFoundIfNull(typ);
    if(datumOd == null && datumDo == null) {
      datumOd = new Date();
      datumDo = new Date();
      render(typ, datumOd,datumDo);
    }
    else {
      if(datumOd == null) datumOd = new Date();
      if(datumDo == null) datumDo = new Date();
    }

    if(typ.equals("neprovedena-vysetreni")) {
      neprovedena(datumOd, datumDo);
    }
    else if(typ.equals("pocty-vzorku")) {
      poctyVzorku(datumOd, datumDo);
    }
    else if(typ.equals("neizolovana-dna")) {
      neizolovana();
    }
  }


  public static void log() {
    DateFormat df = new SimpleDateFormat("yyyy-MM");
    String filename = df.format(new Date());
    List<String> lines = appLog.get(filename);
    render(lines);
  }


  public static void neprovedena(@As("dd.MM.yyyy") Date datumOd, @As("dd.MM.yyyy") Date datumDo) {
      if(datumOd == null) datumOd = new Date();
      if(datumDo == null) datumDo = new Date();

      AppModul modul = connected.modul;
      List<Report> vysetreni = Report.getNeprovedena(datumOd, datumDo, modul);

      Options options = new Options();
      options.FOOTER = modul.formNeprovVys;
      IHtmlToPdfTransformer.PageSize ps = new IHtmlToPdfTransformer.PageSize(21.0, 29.7, 1.9, 1.9, 1.5, 1.5);
      options.pageSize = ps;

      //render(vysetreni, datumOd, datumDo, modul);
      renderPDF(vysetreni, datumOd, datumDo, modul, options);
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

  public static void neizolovana() {
      List<BioMaterial> bioMaterialy = BioMaterial.getNeizolovana(connected.modul);

      render(bioMaterialy);
  }

  public static void stitky(String evCisla) {
    List<Patient> pacienti = Patient.getLastPatients(connected.modul, 100);
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


  public static void vyslImport(File file) {
    if(file != null) {
      List str = new ArrayList();
      List<String> lines = IO.readLines(file);
      String line = "";
      String[] columns = null;
      String pacKod = "";
      String marker = "";
      String vysl = "";
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
        vysl   = columns[7];

        if(vysl.equals("Both")) vysl = "wt/mut";
        else if(vysl.equals("Undetermined")) vysl = "";
        else vysl = vysl.substring(4);

        pacient = Patient.getByKod(pacKod);

        upraveno = Report.setVysl(pacKod, marker, vysl);
        if(upraveno) str.add(pacKod + "," + marker + "," + vysl);
        //System.out.println(pacKod + " - " + marker + " - " + vysl);
      }

      String fileName = file.getName();
      render(fileName, str);
    }
    else {
      render();
    }

    
  }  
  
  
}