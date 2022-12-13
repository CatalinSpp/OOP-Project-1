package database;

import java.util.ArrayList;

public class Movie {
    private String name;
    private int year;
    private int duration;
    private ArrayList<String> genres;
    private ArrayList<String> actors;
    private ArrayList<String> countriesBanned;
    private int numLikes;
    private double rating;
    private int numRatings;

    public Movie() {
        numLikes = 0;
        rating = 0;
        numRatings = 0;
    }

    public Movie(Movie duplicate) {
        name = duplicate.getName();
        year = duplicate.getYear();
        duration = duplicate.getDuration();
        genres = duplicate.getGenres();
        actors = duplicate.getActors();
        countriesBanned = duplicate.getCountriesBanned();
        numLikes = duplicate.getNumLikes();
        rating = duplicate.getRating();
        numRatings = duplicate.getNumRatings();
    }

    public final String getName() {
        return name;
    }

    public final void setName(final String name) {
        this.name = name;
    }

    public final int getYear() {
        return year;
    }

    public final void setYear(final int year) {
        this.year = year;
    }

    public final int getDuration() {
        return duration;
    }

    public final void setDuration(final int duration) {
        this.duration = duration;
    }

    public final ArrayList<String> getGenres() {
        return genres;
    }

    public final void setGenres(final ArrayList<String> genres) {
        this.genres = genres;
    }

    public final ArrayList<String> getActors() {
        return actors;
    }

    public final void setActors(final ArrayList<String> actors) {
        this.actors = actors;
    }

    public final ArrayList<String> getCountriesBanned() {
        return countriesBanned;
    }

    public final void setCountriesBanned(final ArrayList<String> countriesBanned) {
        this.countriesBanned = countriesBanned;
    }

    public int getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(int numLikes) {
        this.numLikes = numLikes;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getNumRatings() {
        return numRatings;
    }

    public void setNumRatings(int numRatings) {
        this.numRatings = numRatings;
    }
}
