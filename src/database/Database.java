package database;

import pages.Page;
import pages.PageLogout;

import java.util.ArrayList;


public final class Database {
    private ArrayList<User> users;
    private ArrayList<Movie> movies;
    private ArrayList<Action> actions;
    private User loggedUser;
    private ArrayList<Movie> displayedMovieList;
    private Page livePage;

    public Database() {
        movies = new ArrayList<>();
        users = new ArrayList<>();
        actions = new ArrayList<>();
        loggedUser = new User();
        displayedMovieList = new ArrayList<>();
        livePage = PageLogout.getInstance();
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(final ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void setMovies(final ArrayList<Movie> movies) {
        this.movies = movies;
    }

    public ArrayList<Action> getActions() {
        return actions;
    }

    public void setActions(final ArrayList<Action> actions) {
        this.actions = actions;
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    public ArrayList<Movie> getDisplayedMovieList() {
        return displayedMovieList;
    }

    public void setDisplayedMovieList(ArrayList<Movie> displayedMovieList) {
        this.displayedMovieList = displayedMovieList;
    }

    public Page getLivePage() {
        return livePage;
    }

    public void setLivePage(Page livePage) {
        this.livePage = livePage;
    }


}
