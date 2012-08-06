package controllers;
 
import models.*;

import play.*;
import play.mvc.*;
import play.db.jpa.*;
import play.data.validation.*;
import javax.persistence.*;
import play.data.binding.*;
import java.util.*;
import java.text.*;

import static play.modules.pdf.PDF.*;
import org.allcolor.yahp.converter.IHtmlToPdfTransformer;


@Check("doctor")
@With(Secure.class)
public class Patients extends Application  {

  public static void index(String skupina) {
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
    render(pacienti);
  }

  public static void detail(Long id) {
    Patient pacient = Patient.getByModulAndId(connected.modul, id);
    notFoundIfNull(pacient);
    List<InsuranceCompany> pojistovny = InsuranceCompany.find("modul = ? order by cislo asc", connected.modul).fetch();
    List<Doctor> lekari = Doctor.find("modul = ? order by icz asc", connected.modul).fetch();
    List<Patient> stejnaRC = Patient.getPatientsWithSameRC(connected.modul, id,pacient.rcZac, pacient.rcKon);
    render(pacient, pojistovny, lekari, stejnaRC);
  }

  public static void score(Long id) {
    Patient pacient = Patient.getByModulAndId(connected.modul, id);
    notFoundIfNull(pacient);

    List str = new ArrayList();
    Map<Long, String> vzorky = new HashMap<Long, String>(); //kombinace "id bio materialu" a "kodu vykonu"
    DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
    df.setTimeZone(TimeZone.getTimeZone("Europe/Prague"));
    Calendar c = Calendar.getInstance();
    Date datum = null;
    String[] svatky = {"01.01","01.05","08.05","05.07","06.07","28.09","28.10","17.11","24.12","25.12","26.12"}; //TODO: chybi velikonocni pondeli
    int blbec = 0;
    int pocetDni = 0;


    for(Report zprava : pacient.zpravy) {
      if(zprava.datumVysetreni == null) continue;
      for(Score ohodnoceni : zprava.vysetreni.score) {
        pocetDni = (ohodnoceni.vykon.jednouDenne ? ohodnoceni.pocet : 1);   //do kolika dni se ma vykon rozepsat
        
        if(ohodnoceni.vykon.kod.equals("94119")) {
          datum = zprava.bioMaterial.datumIzolace;
          if(datum == null) datum = zprava.datumVysetreni;
        }
        else {
          datum = zprava.datumVysetreni;
        }
        
        for(int i = 0; i < pocetDni; i++) {
          if(ohodnoceni.vykon.jednouNaVzorek) {
            if(vzorky.get(zprava.bioMaterial.id) != null) continue; //pro tento biomat se uz dany kod vykonu provadel
            vzorky.put(zprava.bioMaterial.id, ohodnoceni.vykon.kod);
          }

          //retezec jdouci na vystup
          str.add(df.format(datum) + ";" + ohodnoceni.vykon.kod + ";" + (ohodnoceni.vykon.jednouDenne ? 1 : ohodnoceni.pocet) );

          if(ohodnoceni.vykon.jednouDenne) {
            blbec = 0;
            while(blbec < 100) {
              blbec++;

              c.setTime(datum);
              c.add(Calendar.DATE, 1);
              datum = c.getTime();

              if(c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) continue;
              if(c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) continue;
              if(Arrays.asList(svatky).contains( df.format(datum).substring(0,5))) continue;

              break;
            }
          }
        } //for pocetDni
    	} //for zprava.vysetreni.score

  	} //for pacient.zpravy


    Options options = new Options();
    IHtmlToPdfTransformer.PageSize ps = new IHtmlToPdfTransformer.PageSize(21.0, 29.7, 1.9, 1.9, 1.5, 1.5);
    options.pageSize = ps;

    render(pacient, str);
    //renderPDF(pacient, str, options);
    
  }

  public static void form(Long id) {
    List<InsuranceCompany> pojistovny = InsuranceCompany.getAktual(connected.modul);
    List<Doctor> lekari = Doctor.find("modul = ? order by icz asc", connected.modul).fetch();

    if(id != null) {
      Patient pacient = Patient.findById(id);
      notFoundIfNull(pacient);

      if(pacient.modul.id != connected.modul.id) {
        notFound();
      }

      render(pacient, pojistovny, lekari);
    }

    render(pojistovny, lekari);
  }


  public static void mySave(Long id, Patient pacient) {
    List<InsuranceCompany> pojistovny = InsuranceCompany.find("byModul", connected.modul).fetch();
    List<Doctor> lekari = Doctor.find("byModul", connected.modul).fetch();

    validation.valid(pacient);
    if(validation.hasErrors()) {
      render("@form", pacient, lekari, pojistovny);
    }

    if(id == null) {
        pacient.modul = connected.modul;
        pacient.rodneCislo = pacient.rcZac + "/" + pacient.rcKon;
        pacient.kod = pacient.getKod();
        try {
          pacient.create();
        }
        catch (Exception e) {
          flash.error("Pacient se stejným evidenčním číslem již existuje.");
          render("@form", pacient, lekari, pojistovny);
        }
        id = pacient.id;
        appLog.add("pacient " + pacient.getKod(), "create", id);
    } else {
      Patient _pacient = Patient.findById(id);
      _pacient.evCislo = pacient.evCislo;
      _pacient.evRok = pacient.evRok;
      _pacient.kod = pacient.getKod();
      _pacient.rcZac = pacient.rcZac;
      _pacient.rcKon = pacient.rcKon;
      _pacient.rodneCislo = pacient.rcZac + "/" + pacient.rcKon;
      _pacient.jmeno = pacient.jmeno;
      _pacient.pojistovna = pacient.pojistovna;
      _pacient.lekar = pacient.lekar;
      _pacient.infSouhlas = pacient.infSouhlas;
      _pacient.diagnoza = pacient.diagnoza;
      _pacient.koncDna = pacient.koncDna;
      _pacient.pozn = pacient.pozn;
      _pacient.verejnaPozn = pacient.verejnaPozn;

      try {
        _pacient.save();
        appLog.add("pacient " + pacient.getKod(), "update", id);
      }
      catch (Exception e) {
        Logger.error(e.getMessage());
        flash.error("Uložení se neprovedlo.");
        render("@form", pacient, lekari, pojistovny);
      }
    }

    flash.success("Informace o pacientovi %s byly úspěšně uloženy.", pacient.getKod());
    detail(id);
  }


  public static void myDelete(Long id) {
    Patient pacient = Patient.getByModulAndId(connected.modul, id);
    notFoundIfNull(pacient);
    
    //TODO: zahada - bez tohoto (i kdyz prazdneho cyklu) se pacient nesmaze,
    //protoze dojde k poruseni referencni integrity u Report
    for(Report zprava : pacient.zpravy) {
    }

    try {
      pacient.delete();
      appLog.add("pacient " + pacient.getKod(), "delete", id);
      flash.success("Pacient %s odebrán.", pacient.getKod());
    }
    catch (Exception e) {
        Logger.error(e.getMessage());
        flash.error("Pacienta %s se nepodařilo odebrat.",pacient.getKod());
        detail(pacient.id);
    }
    index();
  }


}
