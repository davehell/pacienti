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


    @As("dd.MM.yyyy")
    public Date datumVysetreni;

    @OneToOne
    public User parafaVysetreni;

    @MaxSize(100)
    public String vedouciLekar;

    @MaxSize(500)
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

    



}
