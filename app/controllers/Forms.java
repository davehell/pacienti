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
}