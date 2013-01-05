package models;

import play.db.jpa.*;
import play.data.validation.*;
import javax.persistence.*;
import java.util.*;
import play.data.binding.*;

@Entity
public class AppModul extends Model {

    @Required
    public String nazev;

    @MaxSize(10)
    public String kod;

    @MaxSize(200)
    public String adresa;
    @MaxSize(200)
    public String vedouciLekari;
    @MaxSize(200)
    public String uvolnujiAnalyzu;
    @MaxSize(200)
    public String provadiAnalyzu;
    @MaxSize(200)
    public String typyMaterialu;
    @MaxSize(20)
    public String formNeprovVys;  //kod formulare pro neprovedena vysetreni (zobrazuje se v paticce)
    @MaxSize(5)
    public String cDokladu; //prvni cislice v Cisle dokladu na zaverecne zprave


    public AppModul(String nazev) {
        this.nazev = nazev;
    }

    public String toString() {
        return nazev;
    }
}
