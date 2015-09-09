package com.games.biitworx.jumpingfrogs;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.games.biitworx.jumpingfrogs.helper.BitmapHelper;
import com.games.biitworx.jumpingfrogs.helper.FontHelper;
import com.games.biitworx.jumpingfrogs.helper.RectHandler;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by WEIS on 31.08.2015.
 */
public class MainMenuView extends View {


    private Rect ClickPlay;
    private Rect ClickRate;
    private Rect ClickAds;
    private Rect ClickLeader;
    private Rect ClickClose;

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

        Paint text = new Paint();
        text.setTextSize(getHeight() / 6);
        text.setShadowLayer(3, 3, 3, Color.WHITE);
        text.setStyle(Paint.Style.FILL);
        text.setColor(Color.argb(255, 50, 100, 50));

        Rect rcTitle =  RectHandler.getGrid(1, 3, RectHandler.getGrid(3, 1, top).get(1)).get(1);
        BitmapHelper.drawInPlus(canvas, rcTitle, title, 2);

        String score = "Best: "+String.valueOf(MainActivity.readHigh());
        canvas.drawText(score,FontHelper.drawTextX(score,text,rcTitle.centerX()),(int)(rcTitle.bottom+text.getTextSize()),text);

        rcMain =  RectHandler.getGrid(3,1,main);


        Rect line0=rcMain.get(0);
        Rect line1=rcMain.get(1);
        Rect line2=rcMain.get(2);



        ArrayList<Rect> secondButtons = RectHandler.getGrid(1, 5, line2);


        ClickPlay = secondButtons.get(2);
        ClickLeader = secondButtons.get(3);
        ClickAds = secondButtons.get(0);
        ClickClose=secondButtons.get(4);
        ClickRate=secondButtons.get(1);


        Bitmap play = BitmapFactory.decodeResource(getResources(), R.drawable.play);

        ClickPlay = BitmapHelper.drawIn(canvas,ClickPlay,play,getHeight()/10);

        Bitmap leader = BitmapFactory.decodeResource(getResources(),R.drawable.leader);

        BitmapHelper.drawIn(canvas,ClickLeader,leader,getHeight()/20);

        Bitmap ads = BitmapFactory.decodeResource(getResources(),R.drawable.ads);

        BitmapHelper.drawIn(canvas,ClickAds,ads);

        Bitmap rate = BitmapFactory.decodeResource(getResources(),R.drawable.rate);

        BitmapHelper.drawIn(canvas,ClickRate,rate,getHeight()/20);

        Bitmap close = BitmapFactory.decodeResource(getResources(),R.drawable.close);

        BitmapHelper.drawIn(canvas,ClickClose,close);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {



            if (ClickPlay.contains((int) event.getX(), (int) event.getY())) {
                    MainActivity m = (MainActivity)getContext();
                    m.play();
            }
        if (ClickClose.contains((int) event.getX(), (int) event.getY())) {

            System.exit(0);
        }

        if (ClickAds.contains((int) event.getX(), (int) event.getY())) {
            MainActivity m = (MainActivity)getContext();
            m.buy();
        }

        if(ClickRate.contains((int)event.getX(),(int)event.getY()))
        {
            MainActivity.sendTracking("Options", "Rate", "UX", "Rate the app "+ Locale.getDefault().getCountry());

            final String appPackageName = getContext().getPackageName();
            try {
                getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }

        }


        return true;
    }

}
