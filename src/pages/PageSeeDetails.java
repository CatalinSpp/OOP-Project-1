package pages;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import database.Action;
import database.Database;
import database.Movie;
import database.User;

import java.util.ArrayList;

// see details page
public final class PageSeeDetails implements Page {
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
        userOnPage = new User(database.getLoggedUser());
        displayedMovieOnPage = new Movie(database.getDisplayedMovieList().get(0));
    }

    /** purchase function */
    public void purchase(final Database database, final ArrayNode out) {
        int alreadyPurchased = 0;
        int idx = 0;
        while (idx < userOnPage.getPurchasedMovies().size()) {
            if (userOnPage.getPurchasedMovies().get(idx).getName()
                    .equals(displayedMovieOnPage.getName())) {
                alreadyPurchased = 1;
                break;
            }
            idx++;
        }
        if (alreadyPurchased == 1) {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode error = objectMapper.createObjectNode();
            error.put("error", "Error");
            error.putPOJO("currentMoviesList", new ArrayList<>());
            error.putPOJO("currentUser", null);
            out.add(error);
        } else {
            switch (userOnPage.getCredentials().getAccountType()) {
                case "standard" -> standardAccountPurchase(out);
                case "premium" -> {
                    if (userOnPage.getNumFreePremiumMovies() != 0) {
                        userOnPage.getPurchasedMovies().add(new Movie(displayedMovieOnPage));
                        userOnPage.setNumFreePremiumMovies(userOnPage
                                .getNumFreePremiumMovies() - 1);
                        seeDetailsValid(out);
                    } else {
                        standardAccountPurchase(out);
                    }
                }
                default -> System.out.println("wrong acc type");
            }
        }
        database.getLoggedUser().setPurchasedMovies(userOnPage.getPurchasedMovies());
        database.getLoggedUser().setNumFreePremiumMovies(userOnPage.getNumFreePremiumMovies());
        database.getLoggedUser().setTokensCount(userOnPage.getTokensCount());
    }

    /** function that puts out a successful see details action */
    private void seeDetailsValid(final ArrayNode out) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode valid = objectMapper.createObjectNode();
        ArrayList<Movie> movieList = new ArrayList<>();
        movieList.add(new Movie(displayedMovieOnPage));
        valid.put("error", (String) null);
        valid.putPOJO("currentMoviesList", movieList);
        valid.putPOJO("currentUser", new User(userOnPage));
        out.add(valid);
    }

    /** function that does the standard acc purchase*/
    private void standardAccountPurchase(final ArrayNode out) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode valid = objectMapper.createObjectNode();
        ObjectNode error = objectMapper.createObjectNode();
        if (userOnPage.getTokensCount() > 1) {
            userOnPage.getPurchasedMovies().add(new Movie(displayedMovieOnPage));
            userOnPage.setTokensCount(userOnPage.getTokensCount() - 2);
            valid.putPOJO("error", null);
            seeDetailsValid(out);
        } else {
            error.put("error", "Error");
            error.putPOJO("currentMoviesList", new ArrayList<>());
            error.putPOJO("currentUser", null);
            out.add(error);
        }
    }

    /** watch function */
    public void watch(final Database database, final ArrayNode out) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode error = objectMapper.createObjectNode();
        int alreadyWatched = 0;
        int purchased = 0;
        int idx = 0;
        while (idx < userOnPage.getPurchasedMovies().size()) {
            if (userOnPage.getPurchasedMovies().get(idx).getName()
                    .equals(displayedMovieOnPage.getName())) {
                purchased = 1;
                break;
            }
            idx++;
        }
        idx = 0;
        while (idx < userOnPage.getWatchedMovies().size()) {
            if (userOnPage.getWatchedMovies().get(idx).getName()
                    .equals(displayedMovieOnPage.getName())) {
                alreadyWatched = 1;
                break;
            }
            idx++;
        }
        if (purchased == 0 || alreadyWatched == 1) {
            error.put("error", "Error");
            error.putPOJO("currentMoviesList", new ArrayList<>());
            error.putPOJO("currentUser", null);
            out.add(error);
        } else {
            userOnPage.getWatchedMovies().add(new Movie(displayedMovieOnPage));
            seeDetailsValid(out);
        }
        database.getLoggedUser().setWatchedMovies(userOnPage.getWatchedMovies());
    }

    /** like function */
    public void like(final Database database, final ArrayNode out) {
        int alreadyLiked = 0;
        int watched = 0;
        int idx = 0;
        while (idx < userOnPage.getWatchedMovies().size()) {
            if (userOnPage.getWatchedMovies().get(idx).getName()
                    .equals(displayedMovieOnPage.getName())) {
                watched = 1;
                break;
            }
            idx++;
        }
        idx = 0;
        while (idx < userOnPage.getLikedMovies().size()) {
            if (userOnPage.getLikedMovies().get(idx).getName()
                    .equals(displayedMovieOnPage.getName())) {
                alreadyLiked = 1;
                break;
            }
            idx++;
        }
        if (watched == 0 || alreadyLiked == 1) {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode error = objectMapper.createObjectNode();
            error.put("error", "Error");
            error.putPOJO("currentMoviesList", new ArrayList<>());
            error.putPOJO("currentUser", null);
            out.add(error);
        } else {
            Movie movie = new Movie(displayedMovieOnPage);
            movie.setNumLikes(movie.getNumLikes() + 1);

            userOnPage.getLikedMovies().add(movie);
            refreshMovieFromList(displayedMovieOnPage.getName(),
                    userOnPage.getPurchasedMovies(), movie);
            refreshMovieFromList(displayedMovieOnPage.getName(),
                    userOnPage.getWatchedMovies(), movie);
            refreshMovieFromList(displayedMovieOnPage.getName(),
                    userOnPage.getRatedMovies(), movie);

            database.getLoggedUser().setLikedMovies(userOnPage.getLikedMovies());
            refreshMovieFromList(displayedMovieOnPage.getName(),
                    database.getLoggedUser().getMoviesAvailableInHisCountry(), movie);
            refreshMovieFromList(displayedMovieOnPage.getName(),
                    database.getDisplayedMovieList(), movie);
            refreshMovieFromList(displayedMovieOnPage.getName(), database.getMovies(), movie);

            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode valid = objectMapper.createObjectNode();
            ArrayList<Movie> movieList = new ArrayList<>();
            movieList.add(new Movie(displayedMovieOnPage));
            refreshMovieFromList(displayedMovieOnPage.getName(), movieList, movie);
            valid.put("error", (String) null);
            valid.putPOJO("currentMoviesList", movieList);
            valid.putPOJO("currentUser", new User(userOnPage));
            out.add(valid);
        }
    }

    /** rate function */
    public void rate(final Database database, final Action action, final ArrayNode out) {
        int alreadyRated = 0;
        int watched = 0;
        int idx = 0;
        while (idx < userOnPage.getWatchedMovies().size()) {
            if (userOnPage.getWatchedMovies().get(idx).getName()
                    .equals(displayedMovieOnPage.getName())) {
                watched = 1;
                break;
            }
            idx++;
        }
        idx = 0;
        while (idx < userOnPage.getRatedMovies().size()) {
            if (userOnPage.getRatedMovies().get(idx).getName()
                    .equals(displayedMovieOnPage.getName())) {
                alreadyRated = 1;
                break;
            }
            idx++;
        }
        if (watched == 0 || alreadyRated == 1 || action.getRate() > 5 || action.getRate() < 1) {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode error = objectMapper.createObjectNode();
            error.put("error", "Error");
            error.putPOJO("currentMoviesList", new ArrayList<>());
            error.putPOJO("currentUser", null);
            out.add(error);
        } else {
            Movie movie = new Movie(displayedMovieOnPage);
            movie.setNumRatings(movie.getNumRatings() + 1);
            movie.setSumRatings(movie.getSumRatings() + action.getRate());
            double rating = movie.getSumRatings() / movie.getNumRatings();
            movie.setRating(rating);

            userOnPage.getRatedMovies().add(movie);
            refreshMovieFromList(displayedMovieOnPage.getName(),
                    userOnPage.getPurchasedMovies(), movie);
            refreshMovieFromList(displayedMovieOnPage.getName(),
                    userOnPage.getWatchedMovies(), movie);
            refreshMovieFromList(displayedMovieOnPage.getName(),
                    userOnPage.getLikedMovies(), movie);

            database.getLoggedUser().setRatedMovies(userOnPage.getRatedMovies());
            refreshMovieFromList(displayedMovieOnPage.getName(),
                    database.getLoggedUser().getMoviesAvailableInHisCountry(), movie);
            refreshMovieFromList(displayedMovieOnPage.getName(),
                    database.getDisplayedMovieList(), movie);
            refreshMovieFromList(displayedMovieOnPage.getName(), database.getMovies(), movie);

            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode valid = objectMapper.createObjectNode();
            ArrayList<Movie> movieList = new ArrayList<>();
            movieList.add(new Movie(displayedMovieOnPage));
            refreshMovieFromList(displayedMovieOnPage.getName(), movieList, movie);
            valid.put("error", (String) null);
            valid.putPOJO("currentMoviesList", movieList);
            valid.putPOJO("currentUser", new User(userOnPage));
            out.add(valid);
        }
    }

    /** function that updates a list for a movie with a given name */
    public void refreshMovieFromList(final String movieName,
                                     final ArrayList<Movie> movieList,
                                     final Movie switchMovie) {
        int idx = 0;
        while (idx < movieList.size()) {
            if (movieList.get(idx).getName().equals(movieName)) {
                movieList.set(idx, switchMovie);
                break;
            }
            idx++;
        }
    }
}
