package com.games.biitworx.jumpingfrogs;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.games.biitworx.jumpingfrogs.helper.BitmapHelper;
import com.games.biitworx.jumpingfrogs.helper.RectHandler;

import java.util.ArrayList;

/**
 * Created by WEIS on 07.09.2015.
 */
public class GameOver extends View {
    public GameOver(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onDraw(Canvas canvas)
    {
        Rect main = new Rect(0,0,getWidth(),getHeight());

        Paint back=new Paint();
        back.setColor(Color.argb(200,20,30,20));
        back.setStyle(Paint.Style.FILL);

        Paint text = new Paint();
        text.setTextSize(getHeight() / 8);
        text.setShadowLayer(10, 10, 10, Color.DKGRAY);
        text.setStyle(Paint.Style.FILL_AND_STROKE);
        text.setColor(Color.WHITE);
        ArrayList<Rect> all = RectHandler.getGrid(5,1,main);

        Rect textOver = RectHandler.getGrid(3,1,all.get(2)).get(1);
        canvas.drawText("GAME OVER", textOver.exactCenterX() - (int) (text.getTextSize() * 2.5f), textOver.exactCenterY(), text);


        Rect retry = RectHandler.getGrid(1,9,all.get(3)).get(3);

        Rect retryButton=new Rect(retry.left,retry.top,retry.left+GameView.RetryButton.getWidth(),retry.top+GameView.RetryButton.getHeight());
        Rect quit = RectHandler.getGrid(1,8,all.get(3)).get(4);

        Rect quitButton=new Rect(quit.left,quit.top,quit.left+GameView.QuitButton.getWidth(),quit.top+GameView.QuitButton.getHeight());
        Rect share = RectHandler.getGrid(1,8,all.get(3)).get(5);

        Rect shareButton=new Rect(share.left,share.top,share.left+GameView.ShareButton.getWidth(),share.top+GameView.ShareButton.getHeight());

        BitmapHelper.drawIn(canvas,retryButton,GameView.RetryButton);
        BitmapHelper.drawIn(canvas,shareButton,GameView.ShareButton);
        BitmapHelper.drawIn(canvas,quitButton,GameView.QuitButton);

        canvas.drawRect(main,back);
    }





}
