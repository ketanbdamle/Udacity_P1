package com.flixeek.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.flixeek.R;
import com.flixeek.common.FlixeekConstants;
import com.flixeek.common.SortPrefEnum;
import com.flixeek.contentapi.model.MovieDetails;
import com.flixeek.utils.DisplayUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Adapter for the recycler view holding the movie items.
 *
 * @version 1.0
 * @author Ketan Damle
 */
public class MovieItemRecyclerViewAdapter
        extends RecyclerView.Adapter<MovieItemRecyclerViewAdapter.ViewHolder> {

    private static final String RECYCLERVIEW_ADAPTER = MovieItemRecyclerViewAdapter.class.getName();

    private final MovieListActivity movieListActivity;
    private List<MovieDetails> movieDetailsList;
    private String currSortPreference;
    private int thumbnailWidth;
    private int thumbnailHeight;

    public MovieItemRecyclerViewAdapter(MovieListActivity movieListActivity, List<MovieDetails> movieDetailsList, String currSortPreference) {
        this.movieListActivity = movieListActivity;
        this.movieDetailsList = movieDetailsList;
        this.currSortPreference = currSortPreference;
        thumbnailWidth = DisplayUtils.getPixelValue(movieListActivity, FlixeekConstants.DEFAULT_GRID_IMG_WIDTH);
        thumbnailHeight =DisplayUtils.getPixelValue(movieListActivity, FlixeekConstants.DEFAULT_GRID_IMG_HEIGHT);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final MovieDetails movieDetails = movieDetailsList.get(position);

        Picasso.with(movieListActivity).load(movieDetails.getListingThumbnailUrl()).resize(thumbnailWidth, thumbnailHeight).into(holder.thumbnail);

        if(currSortPreference.equals(SortPrefEnum.popularity.name())){
            Picasso.with(movieListActivity).load(R.drawable.ic_social_whatshot).into(holder.movieTrendImg);
            holder.movieTrendText.setText(String.format("%.2f", movieDetails.getPopularity()));
        }
        else{
            Picasso.with(movieListActivity).load(R.drawable.ic_toggle_star).into(holder.movieTrendImg);
            holder.movieTrendText.setText(String.format("%.2f", movieDetails.getRating()));
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, MovieDetailActivity.class);
                intent.putExtra(FlixeekConstants.BUNDLE_KEY_MOVIE_DETAILS, movieDetails);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieDetailsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView thumbnail;
        public final ImageView movieTrendImg;
        public final TextView movieTrendText;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            movieTrendImg = (ImageView) view.findViewById(R.id.movieTrendImg);
            movieTrendText = (TextView) view.findViewById(R.id.movieTrendText);
        }
    }

    public void setMovieDetailsList(List<MovieDetails> movieDetailsList) {
        this.movieDetailsList = movieDetailsList;
    }

    public void setCurrSortPreference(String currSortPreference) {
        this.currSortPreference = currSortPreference;
    }
}