package com.games.biitworx.jumpingfrogs;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.games.biitworx.jumpingfrogs.helper.BitmapHelper;
import com.games.biitworx.jumpingfrogs.helper.RectHandler;
import com.google.android.gms.games.Game;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by WEIS on 01.09.2015.
 */
public class Scene {

    public int X=0;
    public Rect Bounds;
    Rect InfoArea;
    Rect MainArea;
    Rect RoseArea;
    ArrayList<Rose> Roses=new ArrayList<>();
    Level GameLevel;
    public Scene(Rect bounds,Level level)
    {
        X=bounds.left;
        Bounds=bounds;
        GameLevel = level;
        initGame(GameLevel);
    }

    public void onDraw(Canvas canvas)
    {
        BitmapHelper.drawIn(canvas,new Rect(X,Bounds.top,X+Bounds.width(), Bounds.bottom),GameView.BackImage);

        for(Rose r : Roses)
            r.onDraw(canvas);
    }

    public void initGame(Level level)
    {
        GameLevel = level;


        InfoArea= RectHandler.getGrid(2, 1, Bounds).get(0);
        MainArea=RectHandler.getGrid(2, 1, Bounds).get(1);

        RoseArea=RectHandler.getGrid(5,1,MainArea).get(3);


        ArrayList<Rect> roseRects = RectHandler.getGrid(1,9,RoseArea);
        for(Integer i :level.Roses)
            Roses.add(new Rose(roseRects.get(i)));

    }

    public Rose getFirstRose()
    {
        return Roses.get(0);
    }

    public boolean isDropped()
    {
        return X+Bounds.width()<0;
    }

    public boolean  move(int move)
    {
        X-=move;
        for(Rose r : Roses)
        {
            r.Display.left-=move;
            r.Display.right-=move;
        }
        return isDropped();
    }
}
