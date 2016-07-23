package com.example.snehaanand.multisearch.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.snehaanand.multisearch.R;
import com.example.snehaanand.multisearch.model.MovieTVClass;
import com.example.snehaanand.multisearch.utils.Utils;

public class MainActivity extends AppCompatActivity implements MainActivityFragment.PaneSelection {
Boolean mTwoPane;
    String DETAILS_TAG="details";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (findViewById(R.id.fragment_popularMovies_detail) != null) {
            // The detail container view will be present only in the large-screen layouts
            // (res/layout-sw600dp). If this view is present, then the activity should be
            // in two-pane mode.
            mTwoPane = true;
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
           if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_popularMovies_detail, new DetailsActivityFragment(), DETAILS_TAG)
                        .commit();
            }
        } else {
            mTwoPane = false;
        }
    }


    @Override
    public void onItemSelection(MovieTVClass movieClass,Boolean favoriteSetting) {
        if(mTwoPane){
            Bundle arguments = new Bundle();
            arguments.putParcelable(Utils.MOVIE_DETAILS, movieClass);
            if (favoriteSetting) {
                arguments.putBoolean(Utils.FAVORITE_MOVIE_ID, true);
            }
            DetailsActivityFragment fragment = new DetailsActivityFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_popularMovies_detail, fragment, DETAILS_TAG)
                    .commit();
        }
        else{
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putParcelable(Utils.MOVIE_DETAILS_ACTIVITY, movieClass);
            if (favoriteSetting) {
                bundle.putBoolean(Utils.FAVORITE_SETTING, true);
            }
            intent.putExtras(bundle);
            intent.setClass(MainActivity.this, DetailsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

}
