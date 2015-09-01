package com.games.biitworx.jumpingfrogs.helper;

import android.graphics.Rect;
import android.graphics.RectF;

import java.util.ArrayList;

/**
 * Created by WEIS on 27.07.2015.
 */
public class RectHandler {


    public static ArrayList<Rect> getGrid(int x,int y,Rect r)
    {

        ArrayList<Rect> result = new ArrayList<>();

        int left=r.left;
        int top = r.top;
        int width = r.width()/y;
        int height = r.height()/x;

        for(int w=0;w<x;w++)
        {
            for(int h=0;h<y;h++)
            {
                result.add(new Rect(left,top,left+width,top+height));
                left+=width;

            }
            left=r.left;
            top+=height;
        }

        return result;
    }

    public static RectF getToRectF(Rect r)
    {
        return new RectF(r.left,r.top,r.right,r.bottom);
    }

    public static Rect combineRects(Rect r1,Rect r2)
    {
        return new Rect(r1.left,r1.top,r2.right,r2.bottom);
    }

    public static Rect combineRects(Rect r1,Rect r2,int padding)
    {
        return new Rect(r1.left+padding,r1.top+padding,r2.right-padding,r2.bottom-padding);
    }

    public static Rect combineRects(Rect r1,Rect r2,int padding,int maxH)
    {
        return new Rect(r1.left+padding,r1.top+padding,r2.right-padding,(r2.bottom-padding)+((r1.height())-padding*maxH));
    }
}