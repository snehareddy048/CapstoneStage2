package com.example.snehaanand.multisearch.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuItem;


import com.example.snehaanand.multisearch.R;
import com.example.snehaanand.multisearch.utils.Utils;


public class SearchActivity extends AppCompatActivity {
EditText searchBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchBar=(EditText)findViewById(R.id.searchTitle);
        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // your action here
                    Toast.makeText(SearchActivity.this, searchBar.getText(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                    intent.putExtra(Utils.SEARCH_STRING,  searchBar.getText().toString());
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
//        searchBar.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                // If the event is a key-down event on the "enter" button
//                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
//                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
//                    // Perform action on key press
//                    Toast.makeText(SearchActivity.this, searchBar.getText(), Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(SearchActivity.this, MainActivity.class);
//                    intent.putExtra(Utils.SEARCH_STRING,  searchBar.getText().toString());
//                    startActivity(intent);
//                    return true;
//                }
//                return false;
//            }
//        });

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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
