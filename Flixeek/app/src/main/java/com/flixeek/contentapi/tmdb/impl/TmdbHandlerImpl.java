package com.flixeek.contentapi.tmdb.impl;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.flixeek.BuildConfig;
import com.flixeek.common.SortPrefEnum;
import com.flixeek.contentapi.ContentApiConstants;
import com.flixeek.contentapi.ContentApiHandler;
import com.flixeek.contentapi.model.MovieDetails;
import com.flixeek.contentapi.tmdb.model.movielist.Movies;
import com.flixeek.contentapi.tmdb.model.movielist.Result;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Handler implementation class for the TMDB API, makes a call to the API and returns the list of most popular or highest rated movies.
 *
 * @version 1.0
 * @author Ketan Damle
 */
public class TmdbHandlerImpl extends ContentApiHandler{

    private static final String TMDB_HANDLER = "TmdbHandlerImpl";

    private Movies movies;
    private List<MovieDetails> movieDetailsList = new ArrayList<>();

    @Override
    public List<MovieDetails> getMovieDetails(Context context, String... params) {

        OkHttpClient client = new OkHttpClient();
        Log.i(TMDB_HANDLER, "API KEY from prop file: " + BuildConfig.TMDB_API_KEY);

        final String SORT_PARAM = "sort_by";
        final String SORT_ORDER = ".desc";
        final String VOTE_COUNT_GTE_PARAM ="vote_count.gte";
        final String API_KEY_PARAM = "api_key";

        Uri.Builder builder = Uri.parse(ContentApiConstants.TMDB_DISCOVER_BASE_URL).buildUpon();
        builder.appendQueryParameter(API_KEY_PARAM, BuildConfig.TMDB_API_KEY);
        if(params!=null){
            String sortPreference = params[0];
            if(sortPreference.equalsIgnoreCase(SortPrefEnum.popularity.name())){
                builder.appendQueryParameter(SORT_PARAM, sortPreference+SORT_ORDER);
            }
            else{
                builder.appendQueryParameter(SORT_PARAM, sortPreference+SORT_ORDER);
                builder.appendQueryParameter(VOTE_COUNT_GTE_PARAM, "50");
            }
        }
        Uri builtUri = builder.build();
        Log.i(TMDB_HANDLER, "API Url: "+builtUri.toString());
        Request request = new Request.Builder().url(builtUri.toString()).build();

        Call call = client.newCall(request);

        try {
            Response response = call.execute();
            String responseData = response.isSuccessful() ? response.body().string() : null;
            if (responseData == null) {
                Log.e(TMDB_HANDLER, "TMDB Api Response is not successful, Response Code: " + response.code());
            }
            Log.d(TMDB_HANDLER, "Response Data: " + responseData);
            movies = new GsonBuilder().create().fromJson(responseData, Movies.class);
            System.out.println(movies);
            Log.d(TMDB_HANDLER, "MovieDetails: " + movies);
        } catch (IOException e) {
            Log.e(TMDB_HANDLER, "TMDB Api Response Failed - "+e.getMessage());
        }

        if(movies!=null) {
            List<Result> results = movies.getResults();
            for (Result result: results) {
                movieDetailsList.add(toGenMovieDetailFormat(result));
            }
        }

        return movieDetailsList;
    }

    /**
     * Transforms the movie info received from the TMDB API into a generalized application level wrapped format.
     *
     * @param result Movie from TMDB API
     * @return {@link MovieDetails} object representation based on {@link Result}
     */
    private MovieDetails toGenMovieDetailFormat(Result result){

        MovieDetails movieDetails = new MovieDetails();
        movieDetails.setMovieId(String.valueOf(result.getId()));
        movieDetails.setTitle(result.getTitle());
        movieDetails.setOverview(result.getOverview());
        movieDetails.setReleaseDate(result.getReleaseDate());
        movieDetails.setListingThumbnailUrl(ContentApiConstants.TMDB_POSTER_IMG_BASE_URL + result.getPosterPath());
        movieDetails.setFanartUrl(ContentApiConstants.TMDB_BACKDROP_IMG_BASE_URL + result.getBackdropPath());
        movieDetails.setPopularity(result.getPopularity());
        movieDetails.setRating(result.getVoteAverage());
        movieDetails.setVotes(String.valueOf(result.getVoteCount()));

        return movieDetails;
    }

}

