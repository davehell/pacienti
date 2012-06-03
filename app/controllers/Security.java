package controllers;
 
import models.*;
 
public class Security extends Secure.Security {

  static void onDisconnected() {
      Application.index();
  }
  
  static void onAuthenticated() {
      Application.start();
  }
	
  static boolean authenticate(String username, String password) {
      //return true;
      return User.connect(username, password) != null;
  }

  static boolean check(String profile) {
      if(User.isUserAdmin(connected())) return true;

      if("doctor".equals(profile)) {
          return User.isUserDoctor(connected());
      }
      return false;
  }



    
}