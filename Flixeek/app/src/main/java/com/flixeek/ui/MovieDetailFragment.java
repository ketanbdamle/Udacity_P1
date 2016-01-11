package com.flixeek.ui;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.flixeek.R;
import com.flixeek.common.FlixeekConstants;
import com.flixeek.contentapi.model.MovieDetails;
import com.flixeek.utils.FlixeekUtils;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

/**
 * A fragment representing a single Movie detail screen.
 *  *
 * @version 1.0
 * @author Ketan Damle
 */
public class MovieDetailFragment extends Fragment {

    private static final String MOVIE_DETAIL_FRAGMENT = MovieDetailFragment.class.getName();

    private ImageView backdrop;

    private TextView movieTitle;

    private TextView movieDescription;

    private ImageView movieThumb;

    private TextView moviePopularity;

    private RatingBar movieRatingBar;

    private TextView movieVotes;

    private TextView movieReleaseDate;


    private MovieDetails movieDetails;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MovieDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState==null || !savedInstanceState.containsKey(FlixeekConstants.BUNDLE_KEY_MOVIE_DETAILS)){
            movieDetails = getArguments().getParcelable(FlixeekConstants.BUNDLE_KEY_MOVIE_DETAILS);
        }
        else{
            movieDetails = savedInstanceState.getParcelable(FlixeekConstants.BUNDLE_KEY_MOVIE_DETAILS);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(FlixeekConstants.BUNDLE_KEY_MOVIE_DETAILS, movieDetails);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.movie_detail, container, false);


        final MovieDetailActivity activity = (MovieDetailActivity) this.getActivity();
        backdrop = (ImageView) activity.findViewById(R.id.backdrop);
        movieTitle = (TextView) activity.findViewById(R.id.movieTitle);

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
        AppBarLayout appBarLayout = (AppBarLayout) activity.findViewById(R.id.app_bar);

        /* The overriding is essential so that in the expanded mode the Title is placed at the bottom of the App Bar Layout
           and in the collapsed mode the Title is placed in the Action bar.
           Credits: Stackoverflow - unfortunately the link was not bookmarked.
         */
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    activity.setActionBarTitle(movieDetails.getTitle());
                    isShow = true;
                } else if(isShow) {
                    activity.setActionBarTitle("");
                    isShow = false;
                }
            }
        });

        if (collapsingToolbarLayout != null) {
            collapsingToolbarLayout.setTitleEnabled(false);
            if(movieDetails!=null) {
                Picasso.with(getContext()).load(movieDetails.getFanartUrl()).fit().into(backdrop);
                movieTitle.setText(movieDetails.getTitle());
            }
        }

        if(movieDetails!=null) {
            fillMovieDetailsTemplate(movieDetails, rootView);
        }

        return rootView;
    }

    /**
     * Populates the movie details.
     *
     * @param movieDetails Object containing all the movie details to be displayed.
     * @param rootView View representing the movie details apart from the backdrop and the title.
     */
    private void fillMovieDetailsTemplate(MovieDetails movieDetails, View rootView) {

        Log.i(MOVIE_DETAIL_FRAGMENT, "Inside movie details fragment ... ");

        movieDescription = (TextView) rootView.findViewById(R.id.movieDescription);
        movieDescription.setText(movieDetails.getOverview());

        movieThumb = (ImageView) rootView.findViewById(R.id.movieThumb);
        Picasso.with(getContext())
                .load(movieDetails.getListingThumbnailUrl())
                .fit()
                .into(movieThumb);

        moviePopularity = (TextView) rootView.findViewById(R.id.moviePopularity);
        moviePopularity.setText(String.format("%.2f", movieDetails.getPopularity()));

        movieRatingBar = (RatingBar) rootView.findViewById(R.id.movieRatingBar);
        if (movieDetails.getRating() > 0) {
            movieRatingBar.setRating((float) ((movieDetails.getRating()) / 2));
        }

        movieVotes = (TextView) rootView.findViewById(R.id.movieVotes);
        movieVotes.setText(movieDetails.getVotes());

        movieReleaseDate = (TextView) rootView.findViewById(R.id.movieReleaseDate);
        String releaseDate = FlixeekUtils.getFormattedRelease(movieDetails.getReleaseDate());
        if (StringUtils.isNotBlank(releaseDate)) {
            movieReleaseDate.setText(releaseDate);
        }

    }
}
