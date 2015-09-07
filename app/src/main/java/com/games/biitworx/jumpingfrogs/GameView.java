package com.games.biitworx.jumpingfrogs;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.games.biitworx.jumpingfrogs.helper.BitmapHelper;
import com.games.biitworx.jumpingfrogs.helper.RandomRange;
import com.games.biitworx.jumpingfrogs.helper.RectHandler;
import com.games.biitworx.jumpingfrogs.scenes.Scene0;
import com.games.biitworx.jumpingfrogs.scenes.Scene1;
import com.games.biitworx.jumpingfrogs.scenes.Scene2;
import com.games.biitworx.jumpingfrogs.scenes.Scene3;

import java.util.ArrayList;

/**
 * Created by WEIS on 31.08.2015.
 */
public class GameView extends View {





    public static Bitmap BackImage;

    public static Bitmap RoseImage;

    Rect Bounds;


    int X=0;

    ArrayList<Scene> Scenes;


    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);




        RoseImage = BitmapFactory.decodeResource(getResources(),R.drawable.rose);
        BackImage = BitmapFactory.decodeResource(getResources(),R.drawable.back_solo);


    }


    public void move(int move)
    {
        X-=move;

        for(Scene s : Scenes)
            s.move(move);

        ArrayList<Scene> rem=new ArrayList<>();

        for(Scene s: Scenes)
            if(s.isDropped())
                rem.add(s);


        for(Scene s : rem)
        {Scenes.remove(s);
        addScene(new Scene(recalculateBounds(),getLevel()));}



    }

    public boolean dropOnRose(Rect frog)
    {
        boolean drop=false;
        int x= frog.centerX();
        int y=frog.centerY();
        for(Scene s : Scenes)
            for(Rose r:s.Roses)
                if(!drop)
                {
                    int x1=r.Display.left+r.Display.width()/10;
                    int y1=r.Display.right-r.Display.width()/10;
                    if(x1<x && y1> x)
                        drop=true;
                }
        return drop;
    }

    private Level getLevel()
    {
        int level = RandomRange.getRandom(1,4);

        return level==4?new Scene3():level==3?new Scene2():level==2?new Scene1():new Scene0();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onDraw(Canvas canvas) {

      if(Scenes==null)initGame();
        for(Scene s:Scenes)
            s.onDraw(canvas);

    }

    public void initGame()
    {
        Bounds=new Rect(0,0,getWidth(),getHeight());
        Scenes=new ArrayList<>();

        addScene(new Scene(recalculateBounds(),getLevel()));
        addScene(new Scene(recalculateBounds(),getLevel()));

        GameStat.RoseStart=Scenes.get(0).getFirstRose().Display;

    }

    public Rect recalculateBounds()
    {
        if(Scenes.size()>0)
            return new Rect(Scenes.get(Scenes.size()-1).X+Bounds.width(),Bounds.top,Scenes.get(Scenes.size()-1).X+Bounds.width()+Bounds.width(),Bounds.bottom);
        return Bounds;
    }

    public void addScene(Scene scene)
    {
        Scenes.add(scene);
    }


}
