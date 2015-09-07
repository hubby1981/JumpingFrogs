package com.games.biitworx.jumpingfrogs;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.games.biitworx.jumpingfrogs.helper.BitmapHelper;
import com.games.biitworx.jumpingfrogs.helper.RandomRange;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by WEIS on 01.09.2015.
 */
public class Rose {

    public Rect Display;
    Timer Anim;
    int anim=0;
    int reverse=0;
    int move=RandomRange.getRandom(1,1);
    int max= RandomRange.getRandom(2,4);
    int animM = RandomRange.getRandom(10,20);
    public Rose(Rect display)
    {
        Display = display;
        Anim = new Timer();
        Anim.schedule(new TimerTask() {
            @Override
            public void run() {
                animate();
            }
        },0,200);
    }

    public void onDraw(Canvas canvas)
    {
        BitmapHelper.drawIn(canvas, Display, GameView.RoseImage);
    }

    public void animate()
    {


        if(reverse==0)

            {if(move==1) {
                Display.top += max;
                Display.bottom += max;
            }
                if(move==2) {
                    Display.left += max;
                    Display.right+= max;
                }
            anim+=max;
            if(anim>=animM)
                reverse=1;
        }
        else
            {if(move==1) {
                Display.top -= max;
                Display.bottom -= max;
            }
                if(move==2) {
                    Display.left -= max;
                    Display.right -= max;
                }
            anim-=max;
            if(anim<=0) {
                reverse = 0;

            }
        }




    }

}
