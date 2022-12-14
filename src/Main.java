import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import database.Action;
import database.Database;
import database.Movie;
import database.User;
import pages.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    /** main function */
    public static void main(final String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Database database = objectMapper.readValue(new File(args[0]), Database.class);
        // here we read the input

        ArrayNode out = objectMapper.createArrayNode();
        // output array

        ObjectNode error = objectMapper.createObjectNode();
        error.put("error", "Error");
        error.putPOJO("currentMoviesList", new ArrayList<>());
        error.putPOJO("currentUser", null);
        // standard error

        for (Action action: database.getActions()) {
            if (action.getType().equals("change page")) {
                if (action.getPage().equals("login")
                        && database.getLivePage().equals(PageLogout.getInstance())) {
                    database.setLivePage(PageLogin.getInstance());
                    database.getLivePage().navigateToHere(database);
                } else if (action.getPage().equals("register")
                        && database.getLivePage().equals(PageLogout.getInstance())) {
                    database.setLivePage(PageRegister.getInstance());
                    database.getLivePage().navigateToHere(database);
                } else if (action.getPage().equals("logout")
                        && !database.getLivePage().equals(PageLogin.getInstance())
                        && !database.getLivePage().equals(PageRegister.getInstance())
                        && !database.getLivePage().equals(PageLogout.getInstance())) {
                    database.setLivePage(PageLogout.getInstance());
                    database.getLivePage().navigateToHere(database);
                } else if (action.getPage().equals("movies")
                        && (database.getLivePage().equals(Homepage.getInstance())
                        || database.getLivePage().equals(PageMovies.getInstance())
                        || database.getLivePage().equals(PageSeeDetails.getInstance())
                        || database.getLivePage().equals(PageUpgrades.getInstance()))) {
                    database.setDisplayedMovieList(new ArrayList<>(database.getLoggedUser()
                            .getMoviesAvailableInHisCountry()));
                    database.setLivePage(PageMovies.getInstance());
                    displayValidChangePage(database, out);
                } else if (action.getPage().equals("see details")
                        && (database.getLivePage().equals(PageMovies.getInstance())
                        || database.getLivePage().equals(PageSeeDetails.getInstance()))) {
                    int movieFound = 0;
                    for (Movie movie : database.getDisplayedMovieList()) {
                        if (movie.getName().equals(action.getMovie())) {
                            Movie movieAux = new Movie(movie);
                            database.getDisplayedMovieList().clear();
                            database.getDisplayedMovieList().add(movieAux);
                            database.setLivePage(PageSeeDetails.getInstance());
                            displayValidChangePage(database, out);
                            movieFound = 1;
                            break;
                        }
                    }
                    if (movieFound == 0) {
                        out.add(error);
                    }
                } else if (action.getPage().equals("upgrades")
                        && (database.getLivePage().equals(Homepage.getInstance())
                        || database.getLivePage().equals(PageMovies.getInstance())
                        || database.getLivePage().equals(PageSeeDetails.getInstance())
                        || database.getLivePage().equals(PageUpgrades.getInstance()))) {
                    database.setLivePage(PageUpgrades.getInstance());
                    database.getLivePage().navigateToHere(database);
                } else {
                    out.add(error);
                }
            } else {
                if (action.getFeature().equals("login")
                        && database.getLivePage().equals(PageLogin.getInstance())) {
                    PageLogin.getInstance().login(database, action, out);
                } else if (action.getFeature().equals("register")
                        && database.getLivePage().equals(PageRegister.getInstance())) {
                    PageRegister.getInstance().register(database, action, out);
                } else if (action.getFeature().equals("search")
                        && database.getLivePage().equals(PageMovies.getInstance())) {
                    PageMovies.getInstance().search(action, out);
                } else if (action.getFeature().equals("filter")
                        && database.getLivePage().equals(PageMovies.getInstance())) {
                    PageMovies.getInstance().filter(action, out);
                } else if (action.getFeature().equals("purchase")
                        && database.getLivePage().equals(PageSeeDetails.getInstance())) {
                    PageSeeDetails.getInstance().purchase(database, out);
                } else if (action.getFeature().equals("watch")
                        && database.getLivePage().equals(PageSeeDetails.getInstance())) {
                    PageSeeDetails.getInstance().watch(database, out);
                } else if (action.getFeature().equals("like")
                        && database.getLivePage().equals(PageSeeDetails.getInstance())) {
                    PageSeeDetails.getInstance().like(database, out);
                } else if (action.getFeature().equals("rate")
                        && database.getLivePage().equals(PageSeeDetails.getInstance())) {
                    PageSeeDetails.getInstance().rate(database, action, out);
                } else if (action.getFeature().equals("buy tokens")
                        && database.getLivePage().equals(PageUpgrades.getInstance())) {
                    PageUpgrades.getInstance().buyTokens(action, out);
                } else if (action.getFeature().equals("buy premium account")
                        && database.getLivePage().equals(PageUpgrades.getInstance())) {
                    PageUpgrades.getInstance().buyPremiumAccount(out);
                } else {
                    out.add(error);
                }
            }
        }
        //here we take each actions, execute it and put the right output in the out array,
        //it will navigate through pages and do the commands from the current page if asked

        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(args[1]), out);
        // here we write the output
    }

    // this function displays the valid action for change page
    private static void displayValidChangePage(final Database database, final ArrayNode out) {
        ObjectMapper objectMapper = new ObjectMapper();
        database.getLivePage().navigateToHere(database);
        ObjectNode valid = objectMapper.createObjectNode();
        valid.putPOJO("error", null);
        valid.putPOJO("currentMoviesList",
                new ArrayList<>(database.getDisplayedMovieList()));
        valid.putPOJO("currentUser", new User(database.getLoggedUser()));
        out.add(valid);
    }
}
