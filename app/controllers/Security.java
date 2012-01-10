package controllers;
 
import models.*;
 
public class Security extends Secure.Security {

  static void onDisconnected() {
      Application.index();
  }
  
  static void onAuthenticated() {
      Patients.index();
  }
	
  static boolean authenticate(String username, String password) {
      //return true;
      return User.connect(username, password) != null;
  }

  static boolean check(String profile) {
      if("admin".equals(profile)) {
          return User.isUserAdmin(connected());
      }
      return false;
  }


  static User getConnUser() {
      return User.getByUsername(connected());
  }

    
}