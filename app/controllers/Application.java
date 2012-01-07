package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

@With(Secure.class)
public class Application extends CRUD {

    static User connected = Security.getConnUser();

    @Before
    static void globals() {
        renderArgs.put("connected", connected);
    }

    public static void index() {
    Logger.info("I am an info message");
        List users = User.findAll();
        //User user = User.find("byUsername", "davehell").first();
        List zpravy = Report.findAll();

        render(users, zpravy);
        Logger.info("Action executed ...");
    }


    static User connectedUser() {
        return Security.isConnected() ? (User) User.find("byUsername", Security.connected()).first() : null;
    }
}