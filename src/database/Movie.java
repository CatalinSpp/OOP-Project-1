package database;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;

public final class Movie {
    private String name;
    private int year;
    private int duration;
    private ArrayList<String> genres;
    private ArrayList<String> actors;
    private ArrayList<String> countriesBanned;
    private int numLikes;
    private double rating;
    private int numRatings;
    private double sumRatings;

    public Movie() {
        numLikes = 0;
        rating = 0;
        numRatings = 0;
        sumRatings = 0.00;
    }

    public Movie(final Movie duplicate) {
        name = duplicate.getName();
        year = duplicate.getYear();
        duration = duplicate.getDuration();
        genres = duplicate.getGenres();
        actors = duplicate.getActors();
        countriesBanned = duplicate.getCountriesBanned();
        numLikes = duplicate.getNumLikes();
        rating = duplicate.getRating();
        numRatings = duplicate.getNumRatings();
        sumRatings = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(final int year) {
        this.year = year;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(final ArrayList<String> genres) {
        this.genres = genres;
    }

    public ArrayList<String> getActors() {
        return actors;
    }

    public void setActors(final ArrayList<String> actors) {
        this.actors = actors;
    }

    public ArrayList<String> getCountriesBanned() {
        return countriesBanned;
    }

    public void setCountriesBanned(final ArrayList<String> countriesBanned) {
        this.countriesBanned = countriesBanned;
    }

    public int getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(final int numLikes) {
        this.numLikes = numLikes;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(final double rating) {
        this.rating = rating;
    }

    public int getNumRatings() {
        return numRatings;
    }

    public void setNumRatings(final int numRatings) {
        this.numRatings = numRatings;
    }

    @JsonIgnore
    public double getSumRatings() {
        return sumRatings;
    }

    @JsonIgnore
    public void setSumRatings(final double sumRatings) {
        this.sumRatings = sumRatings;
    }
}
