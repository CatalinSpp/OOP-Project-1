package pages;

import database.Database;
import database.Movie;
import database.User;

import java.util.ArrayList;

public class PageUpgrades implements Page {
    private User userOnPage;
    private ArrayList<Movie> displayedMovieListOnPage;
    private static PageUpgrades instance = null;

    private PageUpgrades() {
    }

    /** getInstance() method for Singleton design pattern*/
    public static PageUpgrades getInstance() {
        if (instance == null) {
            instance = new PageUpgrades();
        }
        return instance;
    }

    @Override
    public void navigateToHere(final Database database) {
        userOnPage = database.getLoggedUser();
        displayedMovieListOnPage = database.getDisplayedMovieList();
    }
}
