package pages;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import database.Action;
import database.Database;
import database.Movie;
import database.User;

import java.util.ArrayList;
import java.util.Comparator;

import static pages.Homepage.success;

public class PageMovies implements Page {
    private User userOnPage;
    private ArrayList<Movie> displayedMovieListOnPage;
    private static PageMovies instance = null;

    private PageMovies() {
    }

    /**
     * getInstance() method for Singleton design pattern
     */
    public static PageMovies getInstance() {
        if (instance == null) {
            instance = new PageMovies();
        }
        return instance;
    }

    @Override
    public void navigateToHere(final Database database) {
        userOnPage = database.getLoggedUser();
        database.setDisplayedMovieList(new ArrayList<>(userOnPage.getMoviesAvailableInHisCountry()));
        displayedMovieListOnPage = database.getDisplayedMovieList();
    }

    public void search(final Action action, final ArrayNode out) {
        displayedMovieListOnPage.clear();
        int idx = 0;
        while (idx < userOnPage.getMoviesAvailableInHisCountry().size()) {
            if (userOnPage.getMoviesAvailableInHisCountry().get(idx).getName()
                    .startsWith(action.getStartsWith())) {
                displayedMovieListOnPage
                        .add(new Movie(userOnPage.getMoviesAvailableInHisCountry().get(idx)));
            }
            idx++;
        }
        succesMoviePageAction(out);
    }

    private void succesMoviePageAction(ArrayNode out) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode valid = objectMapper.createObjectNode();
        valid.putPOJO("error", null);
        valid.putPOJO("currentMoviesList", new ArrayList<>(displayedMovieListOnPage));
        valid.putPOJO("currentUser", new User(userOnPage));
        out.add(valid);
    }

    public void filter(final Action action, final ArrayNode out) {
        displayedMovieListOnPage = new ArrayList<>(userOnPage.getMoviesAvailableInHisCountry());
        if (action.getFilters().getContains() != null) {
            if (action.getFilters().getContains().getGenre() != null) {
                int idx = 0;
                while (idx < action.getFilters().getContains().getGenre().size()) {
                    int finalIdx = idx;
                    displayedMovieListOnPage.removeIf(movie -> !movie.getGenres()
                            .contains(action.getFilters().getContains().getGenre().get(finalIdx)));
                    idx++;
                }
            }
            if (action.getFilters().getContains().getActors() != null) {
                int idx = 0;
                while (idx < action.getFilters().getContains().getActors().size()) {
                    int finalIdx = idx;
                    displayedMovieListOnPage.removeIf(movie -> !movie.getActors()
                            .contains(action.getFilters().getContains().getActors().get(finalIdx)));
                    idx++;
                }
            }
        }
        if (action.getFilters().getSort() != null) {
            if (action.getFilters().getSort().getRating() != null) {
                switch (action.getFilters().getSort().getRating()) {
                    case "increasing" -> displayedMovieListOnPage.sort(Comparator.comparing(Movie::getRating));
                    case "decreasing" -> displayedMovieListOnPage.sort(Comparator.comparing(Movie::getRating).reversed());
                    default -> System.out.println("Unknown sort!");
                }
                if (action.getFilters().getSort().getDuration() != null) {
                    switch (action.getFilters().getSort().getDuration()) {
                        case "increasing" -> displayedMovieListOnPage.sort(Comparator.comparing(Movie::getDuration));
                        case "decreasing" -> displayedMovieListOnPage.sort(Comparator.comparing(Movie::getDuration).reversed());
                        default -> System.out.println("Unknown sort!");
                    }
                }
            }
        }
        succesMoviePageAction(out);
    }
}
