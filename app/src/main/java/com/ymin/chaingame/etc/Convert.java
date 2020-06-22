package com.ymin.chaingame.etc;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class Convert {

    public static int dpToPx(Context context, float dp){
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp , dm);
    }
}
