package pages;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import database.Database;
import database.Movie;
import database.User;

import java.util.ArrayList;

// authenticated page
public final class Homepage implements Page {
    private User userOnPage;
    private ArrayList<Movie> displayedMovieListOnPage;
    private static Homepage instance = null;

    private Homepage() {
    }

    /** getInstance() method for Singleton design pattern*/
    public static Homepage getInstance() {
        if (instance == null) {
            instance = new Homepage();
        }
        return instance;
    }

    @Override
    public void navigateToHere(final Database database) {
        userOnPage = database.getLoggedUser();
        displayedMovieListOnPage = database.getDisplayedMovieList();
    }

    static void success(final Database database, final ArrayNode out) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode valid = objectMapper.createObjectNode();
        valid.putPOJO("error", null);
        valid.putPOJO("currentMoviesList", new ArrayList<>(database.getDisplayedMovieList()));
        valid.putPOJO("currentUser", new User(database.getLoggedUser()));
        out.add(valid);
    }
}
