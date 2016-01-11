package com.flixeek.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.flixeek.R;
import com.flixeek.common.FlixeekConstants;

/**
 * An activity representing movie details screen.
 *
 * @version 1.0
 * @author Ketan Damle
 */
public class MovieDetailActivity extends AppCompatActivity {

    private static final String MOVIE_DETAIL_ACTIVITY = MovieDetailActivity.class.getName();
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);

            //Bypass the title being set by the AppBar Layout
            actionBar.setTitle("");
        }

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putParcelable(FlixeekConstants.BUNDLE_KEY_MOVIE_DETAILS,
                    getIntent().getParcelableExtra(FlixeekConstants.BUNDLE_KEY_MOVIE_DETAILS));

            MovieDetailFragment fragment = new MovieDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.movie_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpTo(this, new Intent(this, MovieListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Sets the action bar title, and can be used by child fragments of this activity for which it is inaccessible.
     *
     * @param title Title to be set for the action bar.
     */
    public void setActionBarTitle(String title){
        actionBar.setTitle(title);
    }

    /**
     * Returns the action bar title, and can be used by child fragments of this activity for which it is inaccessible.
     *
     */
    public void getActionBarTitle(){
        actionBar.getTitle();
    }
}
