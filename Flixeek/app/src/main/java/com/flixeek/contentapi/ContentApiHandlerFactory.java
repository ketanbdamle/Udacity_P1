package com.flixeek.contentapi;

import com.flixeek.contentapi.tmdb.impl.TmdbHandlerImpl;

/**
 * Content Api Handler Factory which provides the appropriate Api Handler implementation for the specified Content Api
 *
 * @version 1.0
 * @author Ketan Damle
 */
public class ContentApiHandlerFactory {

    public static ContentApiHandler handler(ContentApi contentApi){
        switch (contentApi){
            case TMDB:
                return new TmdbHandlerImpl();
            default:
                break;

        }
        throw new UnsupportedOperationException("Api currently not supported ...");
    }
}
