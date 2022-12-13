package pages;

import database.Database;
import database.Movie;
import database.User;

import java.util.ArrayList;

public class PageSeeDetails implements Page {
    private User userOnPage;
    private Movie displayedMovieOnPage;
    private static PageSeeDetails instance = null;

    private PageSeeDetails() {
    }

    /** getInstance() method for Singleton design pattern*/
    public static PageSeeDetails getInstance() {
        if (instance == null) {
            instance = new PageSeeDetails();
        }
        return instance;
    }

    @Override
    public void navigateToHere(final Database database) {
        userOnPage = database.getLoggedUser();
        database.setDisplayedMovieList(new ArrayList<>(userOnPage.getMoviesAvailableInHisCountry()));
        displayedMovieOnPage = database.getDisplayedMovieList().get(0);
    }

    public void purchase(Database database) {

    }

    public void watch(Database database) {

    }

    public void like(Database database) {

    }
}
