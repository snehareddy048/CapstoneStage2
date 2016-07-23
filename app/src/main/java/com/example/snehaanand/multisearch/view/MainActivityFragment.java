package com.example.snehaanand.multisearch.view;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.snehaanand.multisearch.R;
import com.example.snehaanand.multisearch.model.MovieTVClass;
import com.example.snehaanand.multisearch.network.DownloadWebPageTask;
import com.example.snehaanand.multisearch.utils.Utils;
import com.example.snehaanand.multisearch.view.adapter.ImageAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by snehaanandyeluguri on 10/31/15.
 */
public class MainActivityFragment extends Fragment {
    ArrayList<MovieTVClass> movieDetails = new ArrayList<>();
    ArrayList<Integer> movieIds = new ArrayList<>();
    ArrayList<String> movieType = new ArrayList<>();
    GridView gridview;
    public final String FAVORITE_MOVIES = "favorite_movies";
    public final String MOVIE_KEY = "movie_list_key";
    Uri movies;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    private class GetImageTask extends AsyncTask<JsonArray, Void, List> {
        @Override
        protected List<MovieTVClass> doInBackground(JsonArray... jsonArray) {
            return parseResult(jsonArray[0]);
        }

        private List<MovieTVClass> parseResult(JsonArray jsonArray) {
            for (int i = 0; i < jsonArray.size(); i++) {
                MovieTVClass data = new Gson().fromJson(jsonArray.get(i), MovieTVClass.class);
                    data.setDisplay_image("http://image.tmdb.org/t/p/w185/" + data.getPoster_path());
                    movieDetails.add(data);
            }
            return movieDetails;
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ImageAdapter imageAdapter = new ImageAdapter(getActivity(), movieDetails);
        String URL = "content://" + Utils.CONTENT_BASE_URL + "/" + Utils.MOVIES_TEXT;
        movies = Uri.parse(URL);

        gridview = (GridView) getActivity().findViewById(R.id.gridView);
        SharedPreferences sharedPrefs =
                PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
        String sortType = sharedPrefs.getString(getString(R.string.pref_sort_key), "popularity");

        gridview.setAdapter(imageAdapter);
        if (savedInstanceState != null) {
            movieIds = savedInstanceState.getIntegerArrayList(FAVORITE_MOVIES);
            movieDetails = (ArrayList<MovieTVClass>) savedInstanceState.get(MOVIE_KEY);
            imageAdapter.setGridData(movieDetails);
        } else if (!isNetworkAvailable()) {
            Toast.makeText(getActivity().getBaseContext(), R.string.no_internet, Toast.LENGTH_LONG).show();
       }
// else if (sortType.equalsIgnoreCase("favorite")) {
//            Cursor c = getActivity().getContentResolver().query(movies, null, null, null, MoviesProvider._ID);
//            if (c != null && c.moveToFirst()) {
//                do {
//                    Integer movieId = c.getInt(c.getColumnIndex(MoviesProvider._ID));
//                    movieIds.add(movieId);
//                    movieType.add(c.getString(c.getColumnIndex(MoviesProvider.SEARCH_RESULT_TYPE)));
//                } while (c.moveToNext());
//            }
//
//            for (Integer movieId : movieIds) {
//                Uri builtUri = Uri.parse(Utils.MOVIEDB_BASE_URL).buildUpon().
//                        appendPath(movieType.get(movieIds.indexOf(movieId))).appendPath(movieId.toString())
//                        .appendQueryParameter(Utils.QUERY_PARAMETER_API, Utils.API_KEY).build();
//                String MOVIE_DB_URL = builtUri.toString();
//                JsonArray movieJsonArray = new JsonArray();
//
//                try {
//                    JsonObject jsonObject = new DownloadWebPageTask().execute(MOVIE_DB_URL).get();
//                    movieJsonArray.add(jsonObject);
//                    movieDetails = (ArrayList<MovieClass>) new GetImageTask().execute(movieJsonArray).get();
//                    if (movieDetails != null) {
//                        imageAdapter.setGridData(movieDetails);
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
        else {
            MainActivity activity = (MainActivity) getActivity();
            String searchString= activity.getIntent().getExtras().getString(Utils.SEARCH_STRING);
            Uri builtUri = Uri.parse(Utils.MOVIEDB_BASE_URL).buildUpon().appendPath(Utils.PATH_SEARCH).
                    appendPath(Utils.PATH_MULTI).appendQueryParameter(Utils.PATH_QUERY, searchString)
                    .appendQueryParameter(Utils.QUERY_PARAMETER_API, Utils.API_KEY).build();
            String MOVIE_DB_URL = builtUri.toString();

            try {
                JsonObject jsonObject = new DownloadWebPageTask().execute(MOVIE_DB_URL).get();
                JsonArray jsonArray = jsonObject.getAsJsonArray(Utils.RESULTS);
                movieDetails = (ArrayList<MovieTVClass>) new GetImageTask().execute(jsonArray).get();
                if (movieDetails != null) {
                    imageAdapter.setGridData(movieDetails);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                MovieTVClass movieClass = movieDetails.get(position);
                Cursor cursor = getActivity().getContentResolver().query(movies, null, MoviesProvider._ID+"=?", new String[]{movieClass.getId().toString()}, MoviesProvider._ID);
                boolean favoriteSetting=false;
                cursor.moveToFirst();
                if(cursor!=null&cursor.getCount()>0){
                    favoriteSetting=true;
                }
                PaneSelection paneSelection = (MainActivityFragment.PaneSelection) getActivity();
                paneSelection.onItemSelection(movieClass, favoriteSetting);
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public interface PaneSelection {
        void onItemSelection(MovieTVClass movieClass, Boolean favoriteSetting);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntegerArrayList(FAVORITE_MOVIES, movieIds);
        outState.putSerializable(MOVIE_KEY, movieDetails);
    }
}
