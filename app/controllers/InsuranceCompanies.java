package controllers;
 
import models.*;
import play.*;
import play.mvc.*;
 
@With(Secure.class) 
@CRUD.For(InsuranceCompany.class)
public class InsuranceCompanies extends Application {
   
}