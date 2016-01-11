
package com.flixeek.contentapi.tmdb.model.movieinfo;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Videos {

    @SerializedName("results")
    @Expose
    private List<TrailerResult> trailerResults = new ArrayList<TrailerResult>();

    /**
     * 
     * @return
     *     The trailerResults
     */
    public List<TrailerResult> getTrailerResults() {
        return trailerResults;
    }

    /**
     * 
     * @param trailerResults
     *     The trailerResults
     */
    public void setTrailerResults(List<TrailerResult> trailerResults) {
        this.trailerResults = trailerResults;
    }

    @Override
    public String toString() {
        return "Videos{" +
                "trailerResults=" + trailerResults +
                '}';
    }
}
