package controllers;

import play.*;
import play.mvc.*;
import models.*;

@Check("admin")
@With(Secure.class)
public class Users extends Application {
   
}