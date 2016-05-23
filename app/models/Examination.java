package models;

import play.db.jpa.*;
import play.data.validation.*;
import javax.persistence.*;
import play.data.binding.*;
import java.util.*;

@Entity
public class Examination extends Model {

    @Required
    @MaxSize(100)
    public String nazev;

    //@MaxSize(300)
    @Lob
    public String popis;

    @MaxSize(10)
    public String zkratka;

    public String vysvetlivka; // ((tučně))

    public int body;

    public boolean aktual;

    public boolean certif;

    public boolean genomac;

    public int sloupce;

    public int tat; //TAT = doba odezvy. Počet dní, do kterých se musí vyšetření provést

    public boolean volitGenotypy; //povolit při založení vyšetření výběr genotypů, které se budou testovat?

    @OneToMany(mappedBy="vysetreni", cascade=CascadeType.ALL)
    public List<Genotype> genotypy;

    @OneToMany(mappedBy="vysetreni", cascade=CascadeType.ALL)
    public List<Score> score;

    public String toString() {
        return (aktual ? "" : "X ") + nazev;
    }

    public static List<Examination> getActual() {
        return Examination.find("aktual = ? order by nazev asc", true).fetch();
    }

    //všechny genotypy těch vyšetření, u kterých je možné si z genotypů zvolit jen některé
    public static LinkedHashMap<String,String> getExaminationGenotypesMap() {
        List<Examination> vysetreni = Examination.find("aktual = ? and volitGenotypy = ? order by nazev asc", true, true).fetch();
        Examination vys = null;
        Genotype gen = null;
        String genotypy = "";
        LinkedHashMap<String,String> map = new LinkedHashMap<String,String>();

        //všechna vyšetření
        for(Iterator<Examination> i = vysetreni.iterator(); i.hasNext(); ) {
          vys = i.next();
          genotypy = "";
          //všechny genotypy daného vyšetření
          for(Iterator<Genotype> j = vys.genotypy.iterator(); j.hasNext(); ) {
            gen = j.next();
            genotypy += gen.nazev;
            if(j.hasNext()) genotypy += "$"; //za poslední položku se $ nepřidá
          }
          map.put( Long.toString(vys.id), genotypy );
        }
        return map;
    }


    public static Examination getByGenotyp(String genotyp) {
        if(genotyp.isEmpty()) return null;

        Genotype gtyp = Genotype.find("nazev = ?", genotyp).first();
        return (gtyp == null) ? null : gtyp.vysetreni;
    }

    public HashMap<String,String> getAutoCompletes() {
      HashMap<String,String> map = new HashMap<String,String>();
      Iterator<Genotype> iterator = this.genotypy.iterator();
      Genotype genotyp = null;
      while (iterator.hasNext()) {
        genotyp = iterator.next();
        map.put( genotyp.nazev, genotyp.autocompl );
      }
      return map;
    }
}
