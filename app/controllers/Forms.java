package controllers;

import models.*;

import play.*;
import play.mvc.*;
import play.data.binding.*;
import java.util.*;
import java.io.File;
import play.libs.IO;

import static play.modules.pdf.PDF.*;

import org.allcolor.yahp.converter.IHtmlToPdfTransformer;
import org.allcolor.yahp.converter.IHtmlToPdfTransformer.PageSize;
import org.allcolor.yahp.*;

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


  public static void neprovedena(@As("dd.MM.yyyy") Date datumOd, @As("dd.MM.yyyy") Date datumDo) {
      if(datumOd == null) datumOd = new Date();
      if(datumDo == null) datumDo = new Date();

      List<Report> vysetreni = Report.getNeprovedena(datumOd, datumDo);
      AppModul modul = connected.modul;

      render(vysetreni, datumOd, datumDo, modul);
  }

  public static void poctyVzorku(@As("dd.MM.yyyy") Date datumOd, @As("dd.MM.yyyy") Date datumDo) {
      if(datumOd == null) datumOd = new Date();
      if(datumDo == null) datumDo = new Date();

      List<Doctor> lekari = Doctor.getPocetVzorku(datumOd, datumDo);

      render(lekari, datumOd, datumDo);
  }

  public static void neizolovana() {
      List<BioMaterial> bioMaterialy = BioMaterial.getNeizolovana();

      render(bioMaterialy);
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

        pacient = Patient.getByKod(pacKod);
        if(pacient != null) System.out.println(pacient.jmeno);

        upraveno = Result.setVysl(pacKod, marker, vysl);
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