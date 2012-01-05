package controllers;
 
import play.*;
import play.mvc.*;
 
import java.util.*;
 
import models.*;
 
@With(Secure.class)
public class Admin extends Application {
    /*
    @Before
    static void setConnectedUser() {
         if(Security.isConnected()) {
             User user = User.find("byUsername", Security.connected()).first();
             renderArgs.put("user", user.username);
         }
    }
 */
    public static void index() {
        render();
    }
    
}