package com.example.snehaanand.multisearch.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by snehaanandyeluguri on 7/17/16.
 */
public class MovieTVClass implements Parcelable {

    private String release_date;

    private String first_air_date;

    private Integer id;

    private String media_type;

    private String original_title;

    private String poster_path;

    private String overview;

    //bitmap to be displayed
    private String display_image;

    private Float vote_average;

    protected MovieTVClass(Parcel in) {
        release_date = in.readString();
        first_air_date = in.readString();
        media_type = in.readString();
        original_title = in.readString();
        poster_path = in.readString();
        overview = in.readString();
        display_image = in.readString();
    }

    public static final Creator<MovieTVClass> CREATOR = new Creator<MovieTVClass>() {
        @Override
        public MovieTVClass createFromParcel(Parcel in) {
            return new MovieTVClass(in);
        }

        @Override
        public MovieTVClass[] newArray(int size) {
            return new MovieTVClass[size];
        }
    };

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getDisplay_image() {
        return display_image;
    }

    public void setDisplay_image(String display_image) {
        this.display_image = display_image;
    }

    public Float getVote_average() {
        return vote_average;
    }

    public void setVote_average(Float vote_average) {
        this.vote_average = vote_average;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(release_date);
        dest.writeString(first_air_date);
        dest.writeString(media_type);
        dest.writeString(original_title);
        dest.writeString(poster_path);
        dest.writeString(overview);
        dest.writeString(display_image);
    }
}
