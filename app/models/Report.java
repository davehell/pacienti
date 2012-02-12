package models;

import play.db.jpa.*;
import play.data.validation.*;
import javax.persistence.*;
import java.util.*;
import play.data.binding.*;

@Entity
public class Report extends Model {

    @Required
    @ManyToOne
    public Patient pacient;
        
    @Required
    @ManyToOne
    public BioMaterial bioMaterial;
     
    @Required
    @ManyToOne
    public Examination vysetreni;

    @OneToMany(mappedBy="zavZprava", cascade=CascadeType.ALL)
    public List<Result> vysledky = new ArrayList<Result>();


    @As("dd.MM.yyyy")
    public Date datumVysetreni;

    @OneToOne
    public User parafaVysetreni;

    @MaxSize(100)
    public String parafaUvolneni;

    @MaxSize(100)
    public String vedouciLekar;

    @Lob
    public String zavZprava;

    public boolean pozitivni;

    @As("dd.MM.yyyy")
    public Date datumPCR1;

    @As("dd.MM.yyyy")
    public Date datumPCR2;

    @As("dd.MM.yyyy")
    public Date datumElForezy;

    @As("dd.MM.yyyy")
    public Date datumRevHybrid;

    @As("dd.MM.yyyy")
    public Date datumFragmentAn;

    @As("dd.MM.yyyy")
    public Date datumRTAn;

    @As("dd.MM.yyyy")
    public Date datumSekv;


    public static List<Report> getNeprovedena(Date datumOd, Date datumDo) {
        //List<Report> result = Report.findAll();
        List<Report> result = Report.find("datumVysetreni is null and bioMaterial.datumPrijeti >= ? and bioMaterial.datumPrijeti <= ?order by vysetreni.id asc, pacient.evCislo asc", datumOd, datumDo).fetch();
/*
Query q = JPA.em().createQuery ("UPDATE Doctor d SET d.jmeno = :jmeno ");
q.setParameter ("jmeno", "pok");
Integer updated = q.executeUpdate ();
Logger.info(updated.toString());
*/
//Query q = JPA.em().createQuery ("select r, sum(r.vysetreni.body) from Report r group by r.id order by r.vysetreni.nazev ");
//List<Report> results = (List<Report>) q.getResultList ();
/*
    List<Report> result = Report.find(
        "select new map(sum(r.vysetreni.body) as body, r.vysetreni.nazev as vyset) from Report r group by r.vysetreni.id having (select r2.pacient from Report r2)"
    ).fetch();
*/
/*
SELECT tbPacienti.jmeno, tbTypyVysetreni.nazev, tbTypyVysetreni.body
FROM
WHERE (((tbBioMaterialy.datum_prijeti)>[datum_prijeti_od] And (tbBioMaterialy.datum_prijeti)<[datum_prijeti_do]))
GROUP BY tbPacienti.jmeno, tbTypyVysetreni.nazev, tbTypyVysetreni.body
HAVING (((tbPacienti.rok)>=2010) AND ((tbVysetreni.datum_vysetreni) Is Null))
ORDER BY tbPacienti.rok DESC , tbPacienti.evidencni_cislo DESC;
*/
        return result;
    }
}
