package models;

import play.db.jpa.*;
import play.data.validation.*;
import javax.persistence.*;
import java.util.*;
import play.data.binding.*;

@Entity
public class Operation extends Model {

    @Required
    @Unique
    @MaxSize(10)
    public String kod;  //kod vykonu

    @Required
    @MaxSize(50)
    public String popis;  //slovni popis vykonu

    public boolean jednouNaVzorek;  //uctovat pouze 1x na vzorek

    public boolean jednouDenne; //uctovat pouze 1x denne

    public String toString()  {
        return kod + " - " + popis;
    }



}
