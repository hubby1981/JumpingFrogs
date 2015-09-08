package com.games.biitworx.jumpingfrogs.helper;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by WEIS on 08.09.2015.
 */
public class FontHelper {

    public static int drawTextX(String text,Paint paint,int x)
    {

        float size = paint.measureText(text);

            return x-((int)size/2);
    }
}
