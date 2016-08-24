package com.example.android.starwars.ModelClass;

/**
 * Created by sanu on 23-Aug-16.
 */
public class MovieModel {

    private String title;
    private String director;
    private String urlOfMovie;


    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getUrlOfMovie() {
        return urlOfMovie;
    }

    public void setUrlOfMovie(String urlOfMovie) {
        this.urlOfMovie = urlOfMovie;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
