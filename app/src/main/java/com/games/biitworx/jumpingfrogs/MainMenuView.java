package com.games.biitworx.jumpingfrogs;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.games.biitworx.jumpingfrogs.helper.BitmapHelper;
import com.games.biitworx.jumpingfrogs.helper.RectHandler;

import java.util.ArrayList;

/**
 * Created by WEIS on 31.08.2015.
 */
public class MainMenuView extends View {


    private Rect ClickPlay;

    public MainMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onDraw(Canvas canvas)
    {
        Rect bounds = new Rect(0,0,getWidth(),getHeight()-getHeight()/20);
        ArrayList<Rect> rcMain = RectHandler.getGrid(2, 1, bounds);
        Paint back = new Paint();
        back.setColor(Color.BLUE);

        Rect top = rcMain.get(0);

        Rect main = rcMain.get(1);

        Bitmap title= BitmapFactory.decodeResource(getResources(), R.drawable.title);

        BitmapHelper.drawInPlus(canvas, RectHandler.getGrid(1, 3, RectHandler.getGrid(3, 1, top).get(1)).get(1), title, 2);

        rcMain =  RectHandler.getGrid(3,1,main);


        Rect line0=rcMain.get(0);
        Rect line1=rcMain.get(1);
        Rect line2=rcMain.get(2);



        ArrayList<Rect> secondButtons = RectHandler.getGrid(1, 5, line2);


        ClickPlay = secondButtons.get(2);


        Bitmap play = BitmapFactory.decodeResource(getResources(), R.drawable.play);

        ClickPlay = BitmapHelper.drawIn(canvas,ClickPlay,play,getHeight()/10);

        Bitmap leader = BitmapFactory.decodeResource(getResources(),R.drawable.leader);

        BitmapHelper.drawIn(canvas,secondButtons.get(3),leader,getHeight()/20);

        Bitmap ads = BitmapFactory.decodeResource(getResources(),R.drawable.ads);

        BitmapHelper.drawIn(canvas,secondButtons.get(0),ads);

        Bitmap rate = BitmapFactory.decodeResource(getResources(),R.drawable.rate);

        BitmapHelper.drawIn(canvas,secondButtons.get(1),rate,getHeight()/20);

        Bitmap close = BitmapFactory.decodeResource(getResources(),R.drawable.close);

        BitmapHelper.drawIn(canvas,secondButtons.get(4),close);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {



            if (ClickPlay.contains((int) event.getX(), (int) event.getY())) {
                    MainActivity m = (MainActivity)getContext();
                    m.play();
            }


        return true;
    }

}
