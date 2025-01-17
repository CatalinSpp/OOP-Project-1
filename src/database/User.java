package database;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;

public final class User {
    private Credentials credentials;
    private int tokensCount;
    private int numFreePremiumMovies;
    private ArrayList<Movie> purchasedMovies;
    private ArrayList<Movie> watchedMovies;
    private ArrayList<Movie> likedMovies;
    private ArrayList<Movie> ratedMovies;
    private ArrayList<Movie> moviesAvailableInHisCountry;
    public User() {
        tokensCount = 0;
        numFreePremiumMovies = 15;
        purchasedMovies = new ArrayList<>();
        watchedMovies = new ArrayList<>();
        likedMovies = new ArrayList<>();
        ratedMovies = new ArrayList<>();
        moviesAvailableInHisCountry = new ArrayList<>();
    }

    public User(final User duplicate) {
        credentials = new Credentials(duplicate.getCredentials());
        tokensCount = duplicate.getTokensCount();
        numFreePremiumMovies = duplicate.getNumFreePremiumMovies();
        purchasedMovies = new ArrayList<>(duplicate.getPurchasedMovies());
        watchedMovies = new ArrayList<>(duplicate.getWatchedMovies());
        likedMovies = new ArrayList<>(duplicate.getLikedMovies());
        ratedMovies = new ArrayList<>(duplicate.getRatedMovies());
        moviesAvailableInHisCountry = new ArrayList<>();
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(final Credentials credentials) {
        this.credentials = credentials;
    }

    public int getTokensCount() {
        return tokensCount;
    }

    public void setTokensCount(final int tokensCount) {
        this.tokensCount = tokensCount;
    }

    public int getNumFreePremiumMovies() {
        return numFreePremiumMovies;
    }

    public void setNumFreePremiumMovies(final int numFreePremiumMovies) {
        this.numFreePremiumMovies = numFreePremiumMovies;
    }

    public ArrayList<Movie> getPurchasedMovies() {
        return purchasedMovies;
    }

    public void setPurchasedMovies(final ArrayList<Movie> purchasedMovies) {
        this.purchasedMovies = purchasedMovies;
    }

    public ArrayList<Movie> getWatchedMovies() {
        return watchedMovies;
    }

    public void setWatchedMovies(final ArrayList<Movie> watchedMovies) {
        this.watchedMovies = watchedMovies;
    }

    public ArrayList<Movie> getLikedMovies() {
        return likedMovies;
    }

    public void setLikedMovies(final ArrayList<Movie> likedMovies) {
        this.likedMovies = likedMovies;
    }

    public ArrayList<Movie> getRatedMovies() {
        return ratedMovies;
    }

    public void setRatedMovies(final ArrayList<Movie> ratedMovies) {
        this.ratedMovies = ratedMovies;
    }

    @JsonIgnore
    public ArrayList<Movie> getMoviesAvailableInHisCountry() {
        return moviesAvailableInHisCountry;
    }

    @JsonIgnore
    public void setMoviesAvailableInHisCountry(final ArrayList<Movie> moviesAvailableInHisCountry) {
        this.moviesAvailableInHisCountry = moviesAvailableInHisCountry;
    }

}
