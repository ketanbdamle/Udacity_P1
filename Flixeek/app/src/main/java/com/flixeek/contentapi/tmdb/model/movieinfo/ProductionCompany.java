
package com.flixeek.contentapi.tmdb.model.movieinfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductionCompany {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("id")
    @Expose
    private int id;

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
     *     The id
     */
    public int getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ProductionCompany{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
