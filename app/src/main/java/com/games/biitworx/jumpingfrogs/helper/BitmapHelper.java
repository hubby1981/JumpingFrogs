package com.games.biitworx.jumpingfrogs.helper;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by WEIS on 31.08.2015.
 */
public class BitmapHelper {


    public static void drawOn(Canvas canvas,Rect display,Bitmap bitmap)
    {
        canvas.drawBitmap(bitmap,display.exactCenterX(),display.exactCenterY(),null);
    }



    public static void drawIn(Canvas canvas,Rect display,Bitmap bitmap)
    {
        canvas.drawBitmap(bitmap,new Rect(0,0,bitmap.getWidth(),bitmap.getHeight()),display,null);
    }


    public static Rect drawIn(Canvas canvas,Rect display,Bitmap bitmap,int padding)
    {
        display=new Rect(display.left,display.top-padding,display.right,display.bottom-padding);
        canvas.drawBitmap(bitmap,new Rect(0,0,bitmap.getWidth(),bitmap.getHeight()),display,null);
        return display;
    }

    public static Rect drawInPlus(Canvas canvas,Rect display,Bitmap bitmap,int plus)
    {
        display=new Rect(display.left-display.width()/plus,display.top,display.right,display.bottom);
        canvas.drawBitmap(bitmap,new Rect(0,0,bitmap.getWidth(),bitmap.getHeight()),display,null);

        return display;
    }
}
