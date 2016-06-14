package controllers;

import play.mvc.*;
import play.api.*;
import play.api.data.*;

import views.html.*;

/**
 * This controller contains an action to handle HTTP requests
 * to the application.
 */
public class Application extends Controller {



    public Result index() {
        return ok(index.render());
    }

    public Result home() {
        if (session("connected") == null) {
            return ok(login.render());
        }
        return ok(login.render());
    }



    public Result logout() {
        session().clear();
        flash("success", "You have logged out.");
        return redirect (routes.Application.home());
    }

}
