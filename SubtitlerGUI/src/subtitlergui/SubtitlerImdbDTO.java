/*
 * Copyright (c) 2012. Subtitler Renaissance V1.0
 * Renaissance is very simple application to allow search and download subtitles for chosen video and specified language.
 * Copyright (c) Spiral Research 2005-2012(abhra.dev@gmail.com)
 */

package subtitlergui;

import java.util.ArrayList;

public class SubtitlerImdbDTO {
    private String aka;
    private String genres;
    private String votes;
    private String kind;
    private String country;
    private String plot;
    private String id;
    private String cover;
    private String title;
    private ArrayList cast;
    private String year;
    private ArrayList writers;
    private String rating;
    private ArrayList directors;

    public String getAka() {
        return aka;
    }

    public void setAka(String aka) {
        this.aka = aka;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getVotes() {
        return votes;
    }

    public void setVotes(String votes) {
        this.votes = votes;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList getCast() {
        return cast;
    }

    public void setCast(ArrayList cast) {
        this.cast = cast;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public ArrayList getWriters() {
        return writers;
    }

    public void setWriters(ArrayList writers) {
        this.writers = writers;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public ArrayList getDirectors() {
        return directors;
    }

    public void setDirectors(ArrayList directors) {
        this.directors = directors;
    }

    @Override
    public String toString() {
        return "SubtitlerImdbDTO{" +
                "aka='" + aka + '\'' +
                ", genres='" + genres + '\'' +
                ", votes='" + votes + '\'' +
                ", kind='" + kind + '\'' +
                ", country='" + country + '\'' +
                ", plot='" + plot + '\'' +
                ", id='" + id + '\'' +
                ", cover='" + cover + '\'' +
                ", title='" + title + '\'' +
                ", cast=" + cast +
                ", year='" + year + '\'' +
                ", writers=" + writers +
                ", rating='" + rating + '\'' +
                ", directors=" + directors +
                '}';
    }
}

