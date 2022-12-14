package pages;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import database.Action;
import database.Database;
import database.Movie;
import database.User;

import java.util.ArrayList;
import static pages.Homepage.success;

// login page
public final class PageLogin implements Page {
    private static PageLogin instance = null;

    private PageLogin() {
    }

    /** getInstance() method for Singleton design pattern*/
    public static PageLogin getInstance() {
        if (instance == null) {
            instance = new PageLogin();
        }
        return instance;
    }

    @Override
    public void navigateToHere(final Database database) {
        database.setLoggedUser(null);
        database.getDisplayedMovieList().clear();
    }

    /** login function */
    public void login(final Database database, final Action action, final ArrayNode out) {
        infiniteLoop:
        while (true) {
            int userIdx = 0;
            while (userIdx < database.getUsers().size()) {
                if (action.getCredentials().getPassword()
                        .equals(database.getUsers().get(userIdx).getCredentials().getPassword())
                        && action.getCredentials().getName()
                        .equals(database.getUsers().get(userIdx).getCredentials().getName())) {
                    /**enterACC*/
                    database.setLoggedUser(new User(database.getUsers().get(userIdx)));
                    enterAcc(database);
                    /**enterACC*/
                    success(database, out);
                    break infiniteLoop;
                }
                userIdx++;
            }
            loginError(database, out);
            break;
        }
    }

    /** function that put out the error for login */
    static void loginError(final Database database, final ArrayNode out) {
        ObjectMapper objectMapper = new ObjectMapper();
        database.setLivePage(PageLogout.getInstance());
        ObjectNode error = objectMapper.createObjectNode();
        error.put("error", "Error");
        error.putPOJO("currentMoviesList", new ArrayList<>());
        error.putPOJO("currentUser", null);
        out.add(error);
    }

    /** function that logged user into account */
    static void enterAcc(final Database database) {
        for (int i = 0; i < database.getMovies().size(); i++) {
            if (!database.getMovies().get(i).getCountriesBanned()
                    .contains(database.getLoggedUser().getCredentials().getCountry())) {
                Movie aux = new Movie(database.getMovies().get(i));
                database.getLoggedUser().getMoviesAvailableInHisCountry().add(aux);
            }
        }
        database.setLivePage(Homepage.getInstance());
    }
}
