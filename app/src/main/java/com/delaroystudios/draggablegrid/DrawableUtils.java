package com.delaroystudios.draggablegrid;

import android.graphics.drawable.Drawable;

/**
 * Created by delaroy on 4/23/17.
 */
public class DrawableUtils {
    private static final int[] EMPTY_STATE = new int[] {};

    public static void clearState(Drawable drawable){
        if (drawable != null){
            drawable.setState(EMPTY_STATE);
        }
    }
}
