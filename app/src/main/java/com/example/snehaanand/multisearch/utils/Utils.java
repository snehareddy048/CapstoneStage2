package com.example.snehaanand.multisearch.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.snehaanand.multisearch.R;

/**
 * Created by snehaanandyeluguri on 10/18/15.
 */
public class Utils
{
    public static String MOVIE_DETAILS="movie_details";
    public static String MOVIE_DETAILS_ACTIVITY ="movie_details_activity";
    public static String FAVORITE_SETTING="favorite_setting";
    public static String MOVIEDB_BASE_URL ="http://api.themoviedb.org/3/";
    public static String YOUTUBE_BASE_URL="https://www.youtube.com/watch";
    public static String CONTENT_BASE_URL="com.example.provider.MovieDB";
    public static String PATH_MULTI="multi";
    public static String PATH_SEARCH="search";
    public static String PATH_QUERY="query";
    public static String PATH_REVIEWS="reviews";
    public static String PATH_VIDEOS="videos";
    public static String QUERY_PARAMETER_API="api_key";
    public static String FAVORITE_MOVIE_ID="favorite_id";
    public static String RESULTS="results";
    public static String API_KEY="YOUR_API_KEY";
    public static String MOVIES_TEXT="movies";
    public static String SEARCH_STRING="search_string";

    public static String SORT_STRING="sort_string";
    public static String FAVORITE="favorite";
    public static String SEARCH="search";
    //media_type
    public static  String PERSON="person";
    public static String TV="tv";
    public static String MOVIE="movie";

    public static void setDefaultSharedPrefs(Context context) {
        SharedPreferences sharedPrefs;
        sharedPrefs =
                PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(context.getString(R.string.pref_sort_key), Utils.SEARCH);
        editor.commit();
    }
}

