
package com.flixeek.contentapi.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Class containing the review details.
 *
 * @version 1.0
 * @author Ketan Damle
 */
public class ReviewDetails implements Parcelable {

    private String author;

    private String content;

    private String url;

    /**
     * 
     * @return
     *     The author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * 
     * @param author
     *     The author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * 
     * @return
     *     The content
     */
    public String getContent() {
        return content;
    }

    /**
     * 
     * @param content
     *     The content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 
     * @return
     *     The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 
     * @param url
     *     The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ReviewDetails{" +
                "author='" + author + '\'' +
                ", content='" + content + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(author);
        dest.writeString(content);
        dest.writeString(url);
    }

    public static final Parcelable.Creator<ReviewDetails> CREATOR
            = new Parcelable.Creator<ReviewDetails>() {
        public ReviewDetails createFromParcel(Parcel in) {
            return new ReviewDetails(in);
        }

        public ReviewDetails[] newArray(int size) {
            return new ReviewDetails[size];
        }
    };

    private ReviewDetails(Parcel in) {
        author = in.readString();
        content = in.readString();
        url = in.readString();
    }
}
