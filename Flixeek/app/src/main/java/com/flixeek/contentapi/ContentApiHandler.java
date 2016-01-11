package com.flixeek.contentapi;

import android.content.Context;

import com.flixeek.contentapi.model.MovieDetails;
import com.flixeek.contentapi.model.TrailerDetails;

import java.util.List;

/**
 * Abstract class to be inherited by different Content APIs.
 *
 * @version 1.0
 * @author Ketan Damle
 */
public abstract class ContentApiHandler {

    /**
     * @return List of the Movies and their details represented by {@link MovieDetails}
     */
    public abstract List<MovieDetails> getMovieDetails(Context context, String... params);
}
