package controllers;

import play.mvc.*;

import play.api.Environment;
import play.data.*;
import play.db.ebean.Transactional;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

import models.*;
import models.users.*;

import views.html.*;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    private FormFactory formFactory;
    private Environment env;

    @Inject
    public HomeController(FormFactory f, Environment e) {
        this.formFactory = f;
        this.env = e;
    }


    private User getCurrentUser() {
		User u = User.getLoggedIn(session().get("email"));
		return u;
	}

    public Result index() {
        return ok(index.render(getCurrentUser()));
    }

    public Result support() {
        return ok(support.render(getCurrentUser()));
    }

    public Result reviews() {
        return ok(reviews.render(getCurrentUser()));
    }

    public Result videos() {
        return ok(videos.render(getCurrentUser()));
    }

    public Result news() {
        return ok(news.render(getCurrentUser()));
    }


    public Result games(Long cat) {

        List<Game> gameList = new ArrayList<Game>();

        List <Category> categoryList = Category.find.query().where().orderBy("name asc").findList();
        if (cat == 0) {
            gameList = Game.find.all();
        }  
        else {
            gameList = Category.find.ref(cat).getGames();
        }

        return ok(games.render(gameList, categoryList, getCurrentUser()));

    }


    public Result addGame(){

        Form<Game> gameForm = formFactory.form(Game.class);
        return ok(addGame.render(gameForm,  getCurrentUser()));
    }


    public Result addGameSubmit(){
        Form<Game> newGameForm = formFactory.form(Game.class).bindFromRequest();
        Game newGame = new Game();

        if (newGameForm.hasErrors()){    
            return badRequest (addGame.render(newGameForm, getCurrentUser()));
        }
        else 
        {
            newGame = newGameForm.get();
            if (newGame.id == null ){ 
                newGame.save();
            }
            else{
                newGame.update();
            }
        }

        flash("Success", "game" + newGame.name + "added");
        return redirect (controllers.routes.HomeController.games(0));
    }

    @Security.Authenticated(Secured.class)
    @With(AuthAdmin.class)
    @Transactional
    public Result deleteGame(Long id) {
        Game.find.ref(id).delete();
        flash("success", "game has been deleted");
        return redirect(routes.HomeController.games(0));
    }


    @Security.Authenticated(Secured.class)
    @Transactional
    public Result updateGame(Long id) {

        Game g = new Game();

        try{
            g = Game.find.byId(id);
        } catch (Exception ex) {
            return badRequest("error");
        }

        Form<Game>  gameForm = formFactory.form(Game.class).fill(g);

        return ok(addGame.render(gameForm, getCurrentUser()));
 
    }

}
