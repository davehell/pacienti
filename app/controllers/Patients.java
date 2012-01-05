package controllers;
 
import models.*;

import play.*;
import play.mvc.*;
import play.db.jpa.*;
import play.data.validation.*;
import javax.persistence.*;
import java.math.*;

import play.data.binding.*;
import java.util.*;
 
@With(Secure.class) 
public class Patients extends Application  {

  public static void index() {

    List<Patient> pacienti = Patient.find("byModul", connected.modul).fetch();
    render(pacienti);
  }


}