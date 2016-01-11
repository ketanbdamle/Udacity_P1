package com.flixeek.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.flixeek.R;
import com.flixeek.common.FlixeekConstants;
import com.flixeek.common.SortPrefEnum;
import com.flixeek.contentapi.ContentApi;
import com.flixeek.contentapi.ContentApiHandlerFactory;
import com.flixeek.contentapi.model.MovieDetails;
import com.flixeek.utils.DisplayUtils;
import com.flixeek.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * An activity representing a list of Movies.
 *
 * @version 1.0
 * @author Ketan Damle
 */
public class MovieListActivity extends AppCompatActivity {

    private static final String MOVIE_LISTING = MovieListActivity.class.getName();

    private MovieListActivity movieListActivity;

    private View recyclerViewParent;
    private RecyclerView recyclerView;
    private MovieItemRecyclerViewAdapter movieItemRecyclerViewAdapter;

    private View progressBarFrame;
    private ProgressBar progressBar;

    private TextView sortTitle;

    private AlertDialog alertDialog;

    private String currSortPreference;
    private String sortTitleText;
    private List<MovieDetails> movieDetailsList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState == null || !savedInstanceState.containsKey(FlixeekConstants.BUNDLE_KEY_MOVIE_LIST_DATA)) {
            movieDetailsList = new ArrayList<>();
        }
        else {
            movieDetailsList= savedInstanceState.getParcelableArrayList(FlixeekConstants.BUNDLE_KEY_MOVIE_LIST_DATA);
            currSortPreference = savedInstanceState.getString(FlixeekConstants.BUNDLE_KEY_CURR_SORT_PREF);
            sortTitleText = savedInstanceState.getString(FlixeekConstants.BUNDLE_KEY_SORT_TITLE);
        }

        setContentView(R.layout.activity_movie_list);

        movieListActivity = this;

        recyclerViewParent = findViewById(R.id.recyclerViewParent);
        recyclerView = (RecyclerView) findViewById(R.id.movie_list);

        progressBarFrame = findViewById(R.id.progressBarFrame);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        sortTitle = (TextView) findViewById(R.id.sortTitle);
        if(sortTitleText!=null)
            sortTitle.setText(sortTitleText);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(movieListActivity);
        String sortSharedPreference =  prefs.getString(getString(R.string.sort_pref_key), getString(R.string.sort_pref_default));
        if(currSortPreference==null)
            currSortPreference = sortSharedPreference;

        assert recyclerView != null;
        setupRecyclerView(recyclerView);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(MOVIE_LISTING, "Inside onSaveInstanceState ... ");
        outState.putParcelableArrayList(FlixeekConstants.BUNDLE_KEY_MOVIE_LIST_DATA, (ArrayList<? extends Parcelable>) movieDetailsList);
        outState.putString(FlixeekConstants.BUNDLE_KEY_CURR_SORT_PREF, currSortPreference);
        outState.putString(FlixeekConstants.BUNDLE_KEY_SORT_TITLE, sortTitleText);
        super.onSaveInstanceState(outState);
    }

    /**
     * Configures the recycler view and sets the padding on recycler view parent to center the recycler view.
     *
     * @param recyclerView Recycler View that serves as the container for the movies sourced from Content Api
     */
    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {

        recyclerView.setPadding(12, 8, 0, 0);

        int screenWidth = DisplayUtils.getScreenWidth(getWindowManager());
        int defaultImgPixelWidth = DisplayUtils.getPixelValue(this, FlixeekConstants.DEFAULT_GRID_IMG_WIDTH);
        Map<String, Integer> gridParams = DisplayUtils.computeGridColCountAndMargin(screenWidth, defaultImgPixelWidth, DisplayUtils.isLandscape(this));
        int gridColCount = gridParams.get(FlixeekConstants.GRID_COL_COUNT);
        int gridMargin = gridParams.get(FlixeekConstants.GRID_MARGIN);

        Log.d(MOVIE_LISTING, "screenWidth: " + screenWidth);
        Log.d(MOVIE_LISTING, "defaultImgPixelWidth: "+defaultImgPixelWidth);
        Log.d(MOVIE_LISTING, "gridColCount: " + gridColCount);
        Log.d(MOVIE_LISTING, "gridMargin: " + gridMargin);

        recyclerViewParent.setPadding(gridMargin, 0, gridMargin, 0);

        movieItemRecyclerViewAdapter = new MovieItemRecyclerViewAdapter(this, movieDetailsList, currSortPreference);
        
        recyclerView.setAdapter(movieItemRecyclerViewAdapter);

        recyclerView.setLayoutManager(new GridLayoutManager(this, gridColCount));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_listing_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort_by_popularity:
                currSortPreference = SortPrefEnum.popularity.name();
                getMovies(currSortPreference);
                return true;
            case R.id.action_sort_by_voter_average:
                currSortPreference = SortPrefEnum.vote_average.name();
                getMovies(currSortPreference);
                return true;
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Fetches the movie listing everytime after the activity is created or is restarted.
     */
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(MOVIE_LISTING, "Inside onStart ... ");
        if (movieDetailsList == null || movieDetailsList.isEmpty()) {
            Log.d(MOVIE_LISTING, "Invoking getMovies ..");
            getMovies(currSortPreference);
        }
    }


    /**
     * Fetches the movie listing based on given sort preference and network availability via an Async Task {@link com.flixeek.ui.MovieListActivity.MovieDetailsRetriever} depending on the Content Api chosen.
     *
     * @param sortPreference Sort Preference for the movies.
     */
    private void getMovies(String sortPreference) {
        Log.d(MOVIE_LISTING, "Inside getMovies");
        if (NetworkUtils.isNetworkAvailable(this)) {
            Log.d(MOVIE_LISTING, "Inside isNetworkAvailable");
            new MovieDetailsRetriever().execute(sortPreference);
        } else {
            createAlertDialog(getString(R.string.alertdlg_title_no_network), getString(R.string.alertdlg_content_no_network), true);
            Toast.makeText(this, getString(R.string.alertdlg_title_no_network), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Called when the Content Api call returns a response, hides the progress bar and alert dialog, if any, and shows the movie listing using the Recycler View
     *
     * @param movieDetailsList List of {@link MovieDetails}
     */
    public void updateDisplay(List<MovieDetails> movieDetailsList) {
        Log.d(MOVIE_LISTING, "Inside updateDisplay ..... ");
        if (!movieDetailsList.isEmpty()) {
            progressBar.setVisibility(View.GONE);
            progressBarFrame.setVisibility(View.GONE);
            sortTitle.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            if (alertDialog != null) {
                Log.d(MOVIE_LISTING, "Dismissing alert dialog ...");
                alertDialog.dismiss();
            }
            if(currSortPreference.equals(SortPrefEnum.popularity.name())){
                sortTitleText = getString(R.string.sort_by_popularity_title);
            }
            else{
                sortTitleText = getString(R.string.sort_by_voter_average_title);
            }
            sortTitle.setText(sortTitleText);
            movieItemRecyclerViewAdapter.setCurrSortPreference(currSortPreference);
            movieItemRecyclerViewAdapter.setMovieDetailsList(movieDetailsList);
            movieItemRecyclerViewAdapter.notifyDataSetChanged();
        }
        else {
            createAlertDialog(getString(R.string.flixeek_contentapi_failure_title), getString(R.string.flixeek_contentapi_failure_msg), false);
        }
    }

    /**
     * Alert Dialog shown to the user in case of Content API failure or network issues.
     *
     * @param title Title of Alert Dialog
     * @param alertContent Content of the Alert Dialog
     * @param isNetworkFailure Has there been a Network Failure
     */
    private void createAlertDialog(String title, String alertContent, boolean isNetworkFailure) {
        Log.d(MOVIE_LISTING, "Inside createDialog ...");

        AlertDialog.Builder builder = new AlertDialog.Builder(getSupportActionBar().getThemedContext());

        builder.setTitle(title).setMessage(alertContent);


        builder.setPositiveButton(getString(R.string.nw_fail_retry), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                getMovies(currSortPreference);
            }
        });
        if (isNetworkFailure) {
            builder.setNeutralButton(getString(R.string.nw_fail_netsettings), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent networkSettings = new Intent(Settings.ACTION_SETTINGS);
                    startActivity(networkSettings);
                }
            });
        }
        builder.setNegativeButton(getString(R.string.nw_fail_cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                alertDialog.dismiss();
                finish();
            }
        });

        alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Async Task which makes a call to the chosen Content Api and updates the display with the movie listing.
     *
     */
    class MovieDetailsRetriever extends AsyncTask<String, Void, List<MovieDetails>> {

        @Override
        protected void onPreExecute() {
            progressBarFrame.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            sortTitle.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
        }

        @Override
        protected List<MovieDetails> doInBackground(String... params) {
            String sortPreference = params[0];
            return ContentApiHandlerFactory.handler(ContentApi.valueOf(getString(R.string.default_api))).getMovieDetails(movieListActivity, sortPreference);
        }

        @Override
        protected void onPostExecute(List<MovieDetails> movieDetails) {
            movieDetailsList = movieDetails;
            updateDisplay(movieDetails);
        }

    }
}
