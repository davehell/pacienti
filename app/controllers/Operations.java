package controllers;

import play.*;
import play.mvc.*;
import models.*;

@Check("doctor")
@With(Secure.class)
public class Operations extends Application {

}