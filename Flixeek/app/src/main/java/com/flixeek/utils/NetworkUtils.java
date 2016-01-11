package com.flixeek.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Network Utility for network related computations.
 *
 * @version 1.0
 * @author Ketan Damle
 */
public final class NetworkUtils {

    private NetworkUtils(){

    }

    /**
     * General Utility function to determine the network availability.
     *
     * @return {@linkplain true} if network is available, {@linkplain false} otherwise
     */
    public static boolean isNetworkAvailable(Activity activity) {
        ConnectivityManager manager = (ConnectivityManager)
                activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }

        return isAvailable;
    }
}
