
package com.flixeek.contentapi.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Class containing the trailer details.
 *
 * @version 1.0
 * @author Ketan Damle
 */
public class TrailerDetails implements Parcelable{

    private String id;

    private String key;

    private String name;

    private String site;

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The key
     */
    public String getKey() {
        return key;
    }

    /**
     * 
     * @param key
     *     The key
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The site
     */
    public String getSite() {
        return site;
    }

    /**
     * 
     * @param site
     *     The site
     */
    public void setSite(String site) {
        this.site = site;
    }

    @Override
    public String toString() {
        return "TrailerDetails{" +
                "id='" + id + '\'' +
                ", key='" + key + '\'' +
                ", name='" + name + '\'' +
                ", site='" + site + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(key);
        dest.writeString(name);
        dest.writeString(site);
    }

    public static final Parcelable.Creator<TrailerDetails> CREATOR
            = new Parcelable.Creator<TrailerDetails>() {
        public TrailerDetails createFromParcel(Parcel in) {
            return new TrailerDetails(in);
        }

        public TrailerDetails[] newArray(int size) {
            return new TrailerDetails[size];
        }
    };

    private TrailerDetails(Parcel in) {
        id = in.readString();
        key = in.readString();
        name = in.readString();
        site = in.readString();
    }
}
