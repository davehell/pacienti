package controllers;

import play.*;
import play.mvc.*;
import models.*;

@Check("admin")
@With(Secure.class)
public class Results extends Application {
   
}