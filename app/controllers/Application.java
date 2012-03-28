package controllers;

import play.*;
import play.mvc.*;

import play.db.jpa.*;
import javax.persistence.*;
import java.util.*;
import play.data.binding.*;

import models.*;
import applogger.*;

@With(Secure.class)
public class Application extends CRUD {

  static User connected = null;
  static AppLogger appLog = null;


  @Before
  static void globals() {
      connected = User.getByUsername(Security.connected());
      renderArgs.put("connected", connected);

      appLog = new AppLogger(connected);
  }

  public static void index() {

  } //index

}