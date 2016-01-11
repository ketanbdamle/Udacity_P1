package com.flixeek.contentapi.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * General class with only the movie details that are used by application.<br/><br/>
 * Wrapper class at application level for movie details fetched via multiple Content APIs.
 *
 * @version 1.0
 * @author Ketan Damle
 */
public class MovieDetails implements Parcelable{

    private String movieId;

    private String title;

    private String tagline;

    private String overview;

    private String runtime;

    private String yearOfRelease;

    private String releaseDate;

    private String[] genres = new String[]{};

    private String listingThumbnailUrl;

    private String fanartUrl;

    private String certificate;

    private double popularity;

    private double rating;

    private String votes;

    private String homepage;

    private String watchers;

    private List<TrailerDetails> trailerDetails = new ArrayList<>();

    private List<ReviewDetails> reviewDetails = new ArrayList<>();

    public MovieDetails(){

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(movieId);
        dest.writeString(title);
        dest.writeString(tagline);
        dest.writeString(overview);
        dest.writeString(runtime);
        dest.writeString(yearOfRelease);
        dest.writeString(releaseDate);
        dest.writeStringArray(genres);
        dest.writeString(listingThumbnailUrl);
        dest.writeString(fanartUrl);
        dest.writeString(certificate);
        dest.writeDouble(popularity);
        dest.writeDouble(rating);
        dest.writeString(votes);
        dest.writeString(homepage);
        dest.writeString(watchers);
        dest.writeTypedList(trailerDetails);
        dest.writeTypedList(reviewDetails);
    }

    public static final Parcelable.Creator<MovieDetails> CREATOR
            = new Parcelable.Creator<MovieDetails>() {
        public MovieDetails createFromParcel(Parcel in) {
            return new MovieDetails(in);
        }

        public MovieDetails[] newArray(int size) {
            return new MovieDetails[size];
        }
    };

    private MovieDetails(Parcel in) {
        movieId = in.readString();
        title = in.readString();
        tagline = in.readString();
        overview = in.readString();
        runtime = in.readString();
        yearOfRelease = in.readString();
        releaseDate = in.readString();
        in.readStringArray(genres);
        listingThumbnailUrl = in.readString();
        fanartUrl = in.readString();
        certificate = in.readString();
        popularity = in.readDouble();
        rating = in.readDouble();
        votes = in.readString();
        homepage = in.readString();
        watchers = in.readString();
        in.readTypedList(trailerDetails, TrailerDetails.CREATOR);
        in.readTypedList(reviewDetails, ReviewDetails.CREATOR);
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getFanartUrl() {
        return fanartUrl;
    }

    public void setFanartUrl(String fanartUrl) {
        this.fanartUrl = fanartUrl;
    }

    public String[] getGenres() {
        return genres;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getListingThumbnailUrl() {
        return listingThumbnailUrl;
    }

    public void setListingThumbnailUrl(String listingThumbnailUrl) {
        this.listingThumbnailUrl = listingThumbnailUrl;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVotes() {
        return votes;
    }

    public void setVotes(String votes) {
        this.votes = votes;
    }

    public String getWatchers() {
        return watchers;
    }

    public void setWatchers(String watchers) {
        this.watchers = watchers;
    }

    public String getYearOfRelease() {
        return yearOfRelease;
    }

    public void setYearOfRelease(String yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }


    public List<TrailerDetails> getTrailerDetails() {
        return trailerDetails;
    }

    public void setTrailerDetails(List<TrailerDetails> trailerDetails) {
        this.trailerDetails = trailerDetails;
    }

    public List<ReviewDetails> getReviewDetails() {
        return reviewDetails;
    }

    public void setReviewDetails(List<ReviewDetails> reviewDetails) {
        this.reviewDetails = reviewDetails;
    }
}
