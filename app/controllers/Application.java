package controllers;

import play.*;
import play.mvc.*;

import play.db.jpa.*;
import javax.persistence.*;
import java.util.*;
import play.data.binding.*;

import models.*;

@With(Secure.class)
public class Application extends CRUD {

  static User connected = null;


  @Before
  static void globals() {
      connected = User.getByUsername(Security.connected());
      renderArgs.put("connected", connected);
  }

  public static void index() {

  } //index


}