package com.example.snehaanand.multisearch.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.renderscript.Float2;

/**
 * Created by snehaanandyeluguri on 7/17/16.
 */
public class MovieTVPersonClass implements Parcelable {

    //common for all
    private Integer id;

    //common for movie and TV
    private String overview;

    private String poster_path;

    //TV

    private String first_air_date;

    private String original_name;

    private Float vote_average;

    //movie
    private String release_date;

    private String original_title;

    private String media_type;

    //person
    private String name;

    private String profile_path;

    private Float popularity;

    //bitmap to be displayed
    private String display_image;


    protected MovieTVPersonClass(Parcel in) {
        id=in.readInt();
        overview = in.readString();
        poster_path = in.readString();
        first_air_date = in.readString();
        original_name = in.readString();
        release_date = in.readString();
        original_title = in.readString();
        media_type = in.readString();
        name = in.readString();
        profile_path = in.readString();
        popularity = in.readFloat();
        display_image = in.readString();
        vote_average=in.readFloat();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(overview);
        dest.writeString(poster_path);
        dest.writeString(first_air_date);
        dest.writeString(original_name);
        dest.writeString(release_date);
        dest.writeString(original_title);
        dest.writeString(media_type);
        dest.writeString(name);
        dest.writeString(profile_path);
        dest.writeFloat(popularity);
        dest.writeString(display_image);
        dest.writeFloat(vote_average);

        }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MovieTVPersonClass> CREATOR = new Creator<MovieTVPersonClass>() {
        @Override
        public MovieTVPersonClass createFromParcel(Parcel in) {
            return new MovieTVPersonClass(in);
        }

        @Override
        public MovieTVPersonClass[] newArray(int size) {
            return new MovieTVPersonClass[size];
        }
    };

    public String getDisplay_image() {
        return display_image;
    }

    public void setDisplay_image(String display_image) {
        this.display_image = display_image;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
    }

    public String getOriginal_name() {
        return original_name;
    }

    public void setOriginal_name(String original_name) {
        this.original_name = original_name;
    }

    public Float getVote_average() {
        return vote_average;
    }

    public void setVote_average(Float vote_average) {
        this.vote_average = vote_average;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    public Float getPopularity() {
        return popularity;
    }

    public void setPopularity(Float popularity) {
        this.popularity = popularity;
    }
}
