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
    public List<Result> vysledky;


    @As("yyyy-MM-dd")
    public Date datumVysetreni;

    @OneToOne
    public User parafaVysetreni;

    @MaxSize(100)
    public String vedouciLekar;

    @MaxSize(500)
    public String zavZprava;

    @As("yyyy-MM-dd")
    public Date datumPCR1;

    @As("yyyy-MM-dd")
    public Date datumPCR2;

    @As("yyyy-MM-dd")
    public Date datumElForezy;

    @As("yyyy-MM-dd")
    public Date datumRevHybrid;

    @As("yyyy-MM-dd")
    public Date datumFragmentAn;

    @As("yyyy-MM-dd")
    public Date datumRTAn;

    @As("yyyy-MM-dd")
    public Date datumSekv;

    public boolean pozitivni;



}
