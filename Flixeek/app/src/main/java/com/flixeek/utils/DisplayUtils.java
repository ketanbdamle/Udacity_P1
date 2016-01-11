package com.flixeek.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.flixeek.common.FlixeekConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility for computations related to display and user interface optimizations.
 *
 * @version 1.0
 * @author Ketan Damle
 */

public class DisplayUtils {

    /**
     * Converts dp to px as per the display density.
     *
     * @param context Context for display density.
     * @param dp dp value to converted to pixels.
     * @return converted pixel value
     */
    public static int getPixelValue(Context context, int dp) {
        float density = context.getApplicationContext().getResources().getDisplayMetrics().density;
        return Math.round((float)dp * density);
    }

    /**
     * Determines the screen orientation.
     *
     * @return true if the orientation is Landscape.
     */
    public static boolean isLandscape(Context context){
        int orientation = context.getResources().getConfiguration().orientation;
        if(orientation == Configuration.ORIENTATION_PORTRAIT) {
            return false;
        }
        return true;
    }

    /**
     * Computes Screen Width in pixels
     *
     * @param windowManager WindowManager interface for computing display metrics.
     * @return screen width in pixels.
     */
    public static int getScreenWidth(WindowManager windowManager){
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    /**
     * Computes optimal grid column count and grid margin as per the screen resolution and display density.
     *
     * @param screenWidth Screen Width in pixels
     * @param defaultImgPixelWidth Default Image width of each grid column.
     * @param isLandScape Boolean indicating if it is Landscape orientation.
     * @return Map containing the grid column count and grid margin.
     */
    public static Map<String, Integer> computeGridColCountAndMargin(int screenWidth, int defaultImgPixelWidth, boolean isLandScape){
        Map<String, Integer> gridParamsMap = new HashMap<>();
        int viableGridMargin = (int) (screenWidth * FlixeekConstants.EMPTY_SPACE_TO_SCREEN_WIDTH_RATIO);
        int viableColCount = screenWidth/defaultImgPixelWidth;
        if(isLandScape){
            if(viableColCount < FlixeekConstants.MIN_GRID_COLS_LANDSCAPE)
                viableColCount = FlixeekConstants.MIN_GRID_COLS_LANDSCAPE;
            int availableSpace = screenWidth - (defaultImgPixelWidth * viableColCount);
            if(availableSpace > viableGridMargin)
                viableGridMargin = availableSpace;
        }
        else{
            if(viableColCount < FlixeekConstants.MIN_GRID_COLS_POTRAIT)
                viableColCount = FlixeekConstants.MIN_GRID_COLS_POTRAIT;
            viableGridMargin = 0;
        }
        gridParamsMap.put(FlixeekConstants.GRID_COL_COUNT, viableColCount);
        gridParamsMap.put(FlixeekConstants.GRID_MARGIN, viableGridMargin / 2);
        return gridParamsMap;
    }

}
