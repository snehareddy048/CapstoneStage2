package com.example.snehaanand.multisearch.view;

import android.support.v4.app.Fragment;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.snehaanand.multisearch.R;
import com.example.snehaanand.multisearch.model.MovieTVPersonClass;
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
public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    ArrayList<MovieTVPersonClass> movieDetails = new ArrayList<>();
    ArrayList<Integer> movieIds = new ArrayList<>();
    ArrayList<String> movieType = new ArrayList<>();
    GridView gridview;
    public final String FAVORITE_MOVIES = "favorite_movies";
    public final String MOVIE_KEY = "movie_list_key";
    Uri movies;
    private static final int SEARCH_FAVORITES_LOADER_ID =1;
    ImageAdapter imageAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Loader<Cursor> loader=null;
        switch (id) {
            case SEARCH_FAVORITES_LOADER_ID: {
                loader = new CursorLoader(getActivity(),
                        MoviesProvider.CONTENT_URI,
                        new String[]{
                                MoviesProvider._ID,
                                MoviesProvider.SEARCH_RESULT_TYPE,
                                       }, null, null, null);
                break;
            }
            default:
                throw new UnsupportedOperationException();
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(data!=null&&data.getCount()!=0){
            while (data.moveToNext()) {
                Integer movieId = data.getInt(data.getColumnIndex(MoviesProvider._ID));
                movieIds.add(movieId);
                movieType.add(data.getString(data.getColumnIndex(MoviesProvider.SEARCH_RESULT_TYPE)));
            }
            JsonArray multiJsonArray = new JsonArray();

            for (Integer movieId : movieIds) {
                Uri builtUri = Uri.parse(Utils.MOVIEDB_BASE_URL).buildUpon().
                        appendPath(movieType.get(movieIds.indexOf(movieId))).appendPath(movieId.toString())
                        .appendQueryParameter(Utils.QUERY_PARAMETER_API, Utils.API_KEY).build();
                String MOVIE_DB_URL = builtUri.toString();
                JsonObject jsonObject = null;
                try {
                    jsonObject = new DownloadWebPageTask().execute(MOVIE_DB_URL).get();
                    multiJsonArray.add(jsonObject);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
            try {
                movieDetails = (ArrayList<MovieTVPersonClass>) new GetImageTask().execute(multiJsonArray).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            if (movieDetails != null) {
                imageAdapter.setGridData(movieDetails);
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


    private class GetImageTask extends AsyncTask<JsonArray, Void, List> {
        @Override
        protected List<MovieTVPersonClass> doInBackground(JsonArray... jsonArray) {
            return parseResult(jsonArray[0]);
        }

        private List<MovieTVPersonClass> parseResult(JsonArray jsonArray) {
            for (int i = 0; i < jsonArray.size(); i++) {
                MovieTVPersonClass data = new Gson().fromJson(jsonArray.get(i), MovieTVPersonClass.class);
                if(data.getMedia_type()==null)
                {
                    String s = movieType.get(i);
                    getImage(s,data);
                    data.setMedia_type(s);

                }
                else {
                    getImage(data.getMedia_type(),data);
                }

                    movieDetails.add(data);
            }
            return movieDetails;
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        imageAdapter = new ImageAdapter(getActivity(), movieDetails);
        String URL = "content://" + Utils.CONTENT_BASE_URL + "/" + Utils.MOVIES_TEXT;
        String sortType= getActivity().getIntent().getExtras().getString(Utils.SORT_STRING);
        movies = Uri.parse(URL);

        gridview = (GridView) getActivity().findViewById(R.id.gridView);

        gridview.setAdapter(imageAdapter);
        if (savedInstanceState != null) {
            movieIds = savedInstanceState.getIntegerArrayList(FAVORITE_MOVIES);
            movieDetails = (ArrayList<MovieTVPersonClass>) savedInstanceState.get(MOVIE_KEY);
            imageAdapter.setGridData(movieDetails);
        } else if (!isNetworkAvailable()) {
            Toast.makeText(getActivity().getBaseContext(), R.string.no_internet, Toast.LENGTH_LONG).show();
       }
        else if (sortType.equalsIgnoreCase(Utils.FAVORITE)) {

               getLoaderManager().restartLoader(SEARCH_FAVORITES_LOADER_ID,null,MainActivityFragment.this);
        }
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
                movieDetails = (ArrayList<MovieTVPersonClass>) new GetImageTask().execute(jsonArray).get();
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
                MovieTVPersonClass movieClass = movieDetails.get(position);
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

    private void getImage(String searchType, MovieTVPersonClass data){
        if(searchType.equalsIgnoreCase(Utils.PERSON)){
            data.setDisplay_image("http://image.tmdb.org/t/p/w185/" + data.getProfile_path());
        }
        else {
            data.setDisplay_image("http://image.tmdb.org/t/p/w185/" + data.getPoster_path());
        }
    }

    public interface PaneSelection {
        void onItemSelection(MovieTVPersonClass movieClass, Boolean favoriteSetting);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntegerArrayList(FAVORITE_MOVIES, movieIds);
        outState.putSerializable(MOVIE_KEY, movieDetails);
    }
}
