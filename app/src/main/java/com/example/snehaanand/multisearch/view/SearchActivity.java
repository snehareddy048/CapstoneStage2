package com.example.snehaanand.multisearch.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.example.snehaanand.multisearch.R;
import com.example.snehaanand.multisearch.utils.Utils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;


public class SearchActivity extends AppCompatActivity {
    EditText searchBar;
    private AdView mAdView;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-7029429216366427~7943491992");
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        String sortType;
        if (getIntent() != null) {
            sortType = Utils.SEARCH_STRING;
        } else {
            sortType = getIntent().getExtras().getString(Utils.SEARCH_STRING);
        }

        if (sortType.equalsIgnoreCase(Utils.FAVORITE)) {
            Intent intent = new Intent(SearchActivity.this, ListActivity.class);
            intent.putExtra(Utils.SORT_STRING, Utils.FAVORITE);
            startActivity(intent);
        } else {
            searchBar = (EditText) findViewById(R.id.searchTitle);
            searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        String userSearch = searchBar.getText().toString();
                        // log events in firebase
                        Bundle bundle = new Bundle();
                        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, userSearch);
                        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);


                        Intent intent = new Intent(SearchActivity.this, ListActivity.class);
                        intent.putExtra(Utils.SORT_STRING, Utils.SEARCH);
                        intent.putExtra(Utils.SEARCH_STRING, userSearch);
                        startActivity(intent);

                        return true;
                    }
                    return false;
                }
            });
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            startActivity(new Intent(this, SettingsActivity.class));
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

}
