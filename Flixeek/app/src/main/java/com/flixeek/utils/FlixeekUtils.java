package com.flixeek.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * General Utility class for Flixeek
 *
 * @version 1.0
 * @author Ketan Damle
 */
public class FlixeekUtils {

    /**
     * Transforms the movie release date to Flixeek display format(E.g. May 10, 2015)
     *
     * @param releaseDateStr Movie Release Date
     * @return Formatted Movie Release Date
     */
    public static String getFormattedRelease(String releaseDateStr){
        String release = "";
        if(releaseDateStr!=null) {
            SimpleDateFormat appFormat = new SimpleDateFormat("MMM d, yyyy");
            SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date releaseDate = originalFormat.parse(releaseDateStr);
                release = appFormat.format(releaseDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return release;
    }
}
