package controllers;

import play.api.Environment;
import play.mvc.*;
import play.data.*;
import play.db.ebean.Transactional;

import java.util.List;
import java.util.ArrayList;
import javax.inject.Inject;

import views.html.*;


// Import models
import models.users.*;
import models.*;

public class LoginController extends Controller {

    private FormFactory formFactory;

    private Environment env;

    @Inject
    public LoginController(Environment e, FormFactory f) {
        this.formFactory = f;
        this.env = e;
    }

    public Result login() {
        Form<Login> loginForm = formFactory.form(Login.class);
        return ok(login.render(loginForm, User.getUserById(session().get("email"))));
    }

    public Result loginSubmit() {
        Form<Login> loginSubmitform = formFactory.form(Login.class).bindFromRequest();
        if (loginSubmitform.hasErrors()) {
            flash("danger", "Invalid email or password");
            return badRequest(login.render(loginSubmitform, null));
        } else {
            flash("success", "You have successfully logged in");
            session().clear();
            session("email", loginSubmitform.get().getEmail());
        }
        return redirect(controllers.routes.HomeController.index());
    }

        private User getUserFromSession() {
            return User.getUserById(session().get("email"));
    }

        public Result logout() {
            
            session().clear();
            flash("success", "Successfully logged out");
            return redirect(routes.LoginController.login());
        }

        @Security.Authenticated(Secured.class)
        @Transactional
        public Result deleteMyAccount() {
    
            User u = getUserFromSession();
            logout();
            u.delete();
    
            flash("danger", "Your account has has been deleted");
    
            return redirect(routes.LoginController.login());
        }
    
        @Security.Authenticated(Secured.class)
        public Result cancelDeleteAccount(){
    
                flash("success", "Account deletion cancelled");
                return redirect(routes.HomeController.index());
        }
    
        @Security.Authenticated(Secured.class)
        public Result deleteAccountPage(){
            return redirect(routes.LoginController.login());
        }
    
}