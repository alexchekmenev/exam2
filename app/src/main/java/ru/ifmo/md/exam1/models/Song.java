package ru.ifmo.md.exam1.models;

/**
 * Created by creed on 23.01.15.
 */
public class Song {
    private String name;
    private String url;
    private String duration;
    private Long popularity;
    //private ArrayList<String> genres;
    private Long year;

    Song() {}

    //Getters

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getDuration() {
        return duration;
    }

    public Long getPopularity() {
        return popularity;
    }

    /*public ArrayList<String> getGenres() {
        return genres;
    }*/

    public Long getYear() {
        return year;
    }

    //Setters

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setPopularity(Long popularity) {
        this.popularity = popularity;
    }

    /*public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }*/

    public void setYear(Long year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Song{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", duration='" + duration + '\'' +
                ", popularity=" + popularity +
                //", genres=" + genres +
                ", year=" + year +
                '}';
    }
}
