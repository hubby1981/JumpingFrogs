package com.games.biitworx.jumpingfrogs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.games.biitworx.jumpingfrogs.helper.BitmapHelper;
import com.games.biitworx.jumpingfrogs.helper.FontHelper;
import com.games.biitworx.jumpingfrogs.helper.RectHandler;

import java.util.ArrayList;

/**
 * Created by WEIS on 07.09.2015.
 */
public class GameOver extends View {

    Rect ClickClose;
    Rect ClickRetry;
    Rect ClickShare;
    public GameOver(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private GameActivity getContextEx()
    {
        return (GameActivity)getContext();
    }
    @Override
    protected void onDraw(Canvas canvas)
    {

        if(MainActivity.readHigh()<Frog.Score)
        {
            MainActivity.saveHigh(Frog.Score);
        }
        Rect main = new Rect(0,0,getWidth(),getHeight());

        Paint back=new Paint();
        back.setColor(Color.argb(225,20,50,20));
        back.setStyle(Paint.Style.FILL);

        Paint text = new Paint();
        text.setTextSize(getHeight() / 12);
        text.setShadowLayer(3, 3, 3, Color.DKGRAY);
        text.setStyle(Paint.Style.FILL);
        text.setColor(Color.argb(255,250,255,250));
        ArrayList<Rect> all = RectHandler.getGrid(5,1,main);

        Rect textOver = RectHandler.getGrid(3,1,all.get(2)).get(1);
        canvas.drawRect(main, back);

        GameActivity ga = (GameActivity)getContext();


        if(ga.LastBitmap1!=null)
        {
            drawPolo(canvas, ga.LastBitmap1);
        }


        if(ga.LastBitmap3!=null)
        {
            drawPolo2(canvas, ga.LastBitmap3);
        }
        if(ga.LastBitmap2!=null)
        {
            canvas.drawBitmap(ga.LastBitmap2, (main.centerX() - ga.LastBitmap2.getWidth() / 2), main.top + ga.LastBitmap2.getHeight() / 8, null);
        }
        String text1="BEST: "+String.valueOf(Frog.Score);
        String text0="GAME OVER";
        String text2="SPLASHED!";
        //canvas.drawText(text2, FontHelper.drawTextX(text2,text,(int)textOver.exactCenterX()), textOver.exactCenterY() - text.getTextSize()*3, text);

        //canvas.drawText(text0, FontHelper.drawTextX(text0,text,(int)textOver.exactCenterX()), textOver.exactCenterY() - text.getTextSize(), text);
        canvas.drawText(text1, FontHelper.drawTextX(text1,text,(int)textOver.exactCenterX()), textOver.exactCenterY()-text.getTextSize()+text.getTextSize()*2, text);


        ClickRetry = RectHandler.getGrid(1,9,all.get(3)).get(2);

        Rect retryButton=new Rect(ClickRetry.left,ClickRetry.top,ClickRetry.left+GameView.RetryButton.getWidth(),ClickRetry.top+GameView.RetryButton.getHeight());
        ClickClose = RectHandler.getGrid(1,9,all.get(3)).get(6);

        Rect quitButton=new Rect(ClickClose.left,ClickClose.top,ClickClose.left+GameView.QuitButton.getWidth(),ClickClose.top+GameView.QuitButton.getHeight());
       ClickShare = RectHandler.getGrid(1,9,all.get(3)).get(4);

        Rect shareButton=new Rect(ClickShare.left,ClickShare.top,ClickShare.left+GameView.ShareButton.getWidth(),ClickShare.top+GameView.ShareButton.getHeight());

        BitmapHelper.drawIn(canvas,retryButton,GameView.RetryButton);
        BitmapHelper.drawIn(canvas,shareButton,GameView.ShareButton);
        BitmapHelper.drawIn(canvas,quitButton,GameView.QuitButton);



    }

    private void drawPolo(Canvas canvas,Bitmap bit) {
        Rect rc = new Rect(0,0,getWidth(),getHeight());

        Rect copy = new Rect(0,0,bit.getWidth()*2,bit.getHeight()*2);

        Bitmap ba = Bitmap.createBitmap(copy.width(),copy.height(), Bitmap.Config.ARGB_4444);
        Canvas bac = new Canvas(ba);
        bac.rotate(-45);
        bac.drawBitmap(bit,0-rc.width()/6,rc.centerY()-rc.centerY()/4,null);


        Paint rc1 = new Paint();
        rc1.setColor(Color.DKGRAY);
        rc1.setStyle(Paint.Style.FILL);

        canvas.drawBitmap(ba, 0+bit.getWidth()/2, 0-bit.getHeight()/6, null);
    }

    private void drawPolo2(Canvas canvas,Bitmap bit) {
        Rect rc = new Rect(0,0,getWidth(),getHeight());

        Rect copy = new Rect(0,0,bit.getWidth()*2,bit.getHeight()*2);

        Bitmap ba = Bitmap.createBitmap(copy.width(),copy.height(), Bitmap.Config.ARGB_4444);
        Canvas bac = new Canvas(ba);
        bac.rotate(45);
        bac.drawBitmap(bit,0+rc.width()/4,0-rc.height()/8,null);


        Paint rc1 = new Paint();
        rc1.setColor(Color.DKGRAY);
        rc1.setStyle(Paint.Style.FILL);

        canvas.drawBitmap(ba, (rc.right-rc.width()/2)-bit.getWidth()/8, 0-rc.height()/6, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

       GameActivity context = (GameActivity) getContext();

        if(context!=null){

        if (ClickClose.contains((int) event.getX(), (int) event.getY())) {
            context.closeMe();
        }
            if (ClickShare.contains((int) event.getX(), (int) event.getY())) {
                SharePhoto photo = new SharePhoto.Builder().setBitmap(getContextEx().LastBitmap3).setCaption("Playing a good game on Jumping Frogs").build();
                SharePhotoContent content = new SharePhotoContent.Builder().addPhoto(photo).build();
                MainActivity.sendTracking("Game Over", "info", "UX", "sharing photo on facebook");

                getContextEx().publish(content);

            }
        if (ClickRetry.contains((int) event.getX(), (int) event.getY())) {
            context.closeMeEx();
            MainActivity.sendTracking("Game Over", "info", "UX", "Game Retry");

        }}

        return true;
    }




}
