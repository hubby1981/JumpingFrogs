package com.games.biitworx.jumpingfrogs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.games.biitworx.jumpingfrogs.helper.BitmapHelper;
import com.games.biitworx.jumpingfrogs.helper.FontHelper;
import com.games.biitworx.jumpingfrogs.helper.RandomRange;
import com.games.biitworx.jumpingfrogs.helper.RectHandler;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by WEIS on 31.08.2015.
 */
public class Frog extends View {

    Bitmap StateSit=BitmapFactory.decodeResource(getResources(),R.drawable.statesit);
    Bitmap StateJump=BitmapFactory.decodeResource(getResources(),R.drawable.statejump);
    Bitmap StateFly=BitmapFactory.decodeResource(getResources(),R.drawable.statefly);
    Bitmap StateLand=BitmapFactory.decodeResource(getResources(),R.drawable.stateland);

    Bitmap StateSplash1=BitmapFactory.decodeResource(getResources(),R.drawable.splash1);
    Bitmap StateSplash2=BitmapFactory.decodeResource(getResources(),R.drawable.splash2);
    Bitmap StateSplash3=BitmapFactory.decodeResource(getResources(),R.drawable.splash3);
    Bitmap StateSplash4=BitmapFactory.decodeResource(getResources(),R.drawable.statesplashend);

    public int State=0;

    public int Charge=0;
    public int ChargeState=0;
    public int MaxCharge=0;
    public int Move=0;
    public int moveMaxX=0;
    public Point MovePoint=new Point(0,0);
public static int Score =0;
    public int GameOver =0;

    public int MaxY=0;
    public int DoSplash=0;
    public int LastX=0;

    Timer Flyer;
    Timer Mover;

    Timer Splash;
Point MoveP;
    Paint Drop;
Paint Scorer;
Rect Position;

    int X=0;
    int Y=0;

    public Frog(Context context, AttributeSet attrs) {
        super(context, attrs);

        Drop = new Paint();
        Drop.setStyle(Paint.Style.STROKE);
        Drop.setColor(Color.argb(225,0,150,0));
        Drop.setAntiAlias(true);
        Drop.setStrokeCap(Paint.Cap.ROUND);


        Scorer=new Paint();
        Scorer.setColor(Color.argb(200,50,50,50));
        Scorer.setAntiAlias(true);
        Score=0;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        try {
            int height = getHeight() / 10;
            Bitmap state =State == 7 ? StateSplash4 : State == 6 ? StateSplash3 : State == 5 ? StateSplash2 : State == 4 ? StateSplash1 : State == 3 ? StateLand : State == 2 ? StateFly : State == 1 ? StateJump : StateSit;


            if (Position == null) {
                X = (int) GameStat.RoseStart.exactCenterX();
                Y = (int) GameStat.RoseStart.exactCenterY();


                Y -= height;
                X -= height / 2;

                Position = new Rect(X, Y, X + height, Y + height);

            }

            if (Charge > 0 && State == 0) {

                int si = Charge + 5;
                Drop.setStrokeWidth(Position.width() / 2);
                Drop.setPathEffect(new DashPathEffect(new float[]{Position.width() /8, Position.width()}, 0));

                Path p = new Path();
                p.moveTo(Position.exactCenterX(), Position.exactCenterY());
                p.lineTo(MovePoint.x, MovePoint.y);

                canvas.drawPath(p, Drop);


            }
            Scorer.setTextSize(getHeight() / 8);
            Scorer.setFakeBoldText(true);
           //Scorer.setShadowLayer(10, 10, 10, Color.DKGRAY);


                canvas.drawText(String.valueOf(Score), FontHelper.drawTextX(String.valueOf(Score),Scorer,getWidth()/2), 0 + getHeight() / 8, Scorer);


            BitmapHelper.drawIn(canvas, Position, state);
        }catch(Exception e){}


    }

    private void callInvalidate(int move)
    {try {
        GameActivity a = (GameActivity) getContext();
        a.updateEx(move);
    }catch(Exception e){}
    }
    private int callDropOnRose(int move)
    {try {
        GameActivity a = (GameActivity) getContext();
        return a.dropOnRose();
    }catch(Exception e){}
        return 1;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(State==0)
        {
            if(event.getAction()==MotionEvent.ACTION_DOWN) {
                if (Position.contains((int) event.getX(), (int) event.getY()) && Charge == 0) {
                    Move=1;
                    MaxY=Position.top;
                    LastX=Position.left;
                }
            }
            else if(event.getAction()==MotionEvent.ACTION_MOVE && Move==1)
            {
                MovePoint = new Point((int)event.getX(),(int)event.getY());
                Charge=5;
                callInvalidate(0);
            }
            else if(event.getAction()==MotionEvent.ACTION_UP && Charge>0)
            {


                Charge=MovePoint.x-Position.centerX();
                if(Charge>=900)
                    Charge = RandomRange.getRandom(750,850);
                Charge/=20;
                MaxCharge=Charge/2;
Move=0;
                MoveP = getXFly(Position.centerX(),Position.centerY(),MovePoint.x,MovePoint.y);
                Flyer=new Timer();
                Flyer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Charge--;
                        float ch= 3.4f;
                        if(Charge>MaxCharge)
                        {
                            if(Charge-MaxCharge/10>MaxCharge) {
                                Y -= (Position.height() / ch);
                                X += (Position.height() / ch);
                                State = 1;
                            }else
                            {
                                X += (Position.height() / ch);
                                State = 2;
                            }

                        }
                        else {

                            Y += (Position.height() / ch);
                            X += (Position.height() / ch);
                            State = 3;
                            getContextEx().takeScreen(State);
                            if(Y>MaxY)
                            {
                                Charge=0;
                                Y=MaxY;}
                        }

                        int h=Position.height();
                        int w = Position.width();

                        Position=new Rect(X,Y,X+w,Y+h);
                        if(Charge==0)
                        {
                            Flyer.cancel();
                            State=0;
                            DoSplash = callDropOnRose(0);
if(DoSplash==1) {
    Splash = new Timer();
    GameOver=1;


    Splash.schedule(new TimerTask() {
        @Override
        public void run() {

            if (State == 6) {
                State = 7;
                Splash.cancel();
                callMove();


            }
            if (State == 5)
                State = 6;
            if (State == 4)
                State = 5;

            if (State == 0)
                State = 4;

            getContextEx().takeScreen(State);
            callInvalidate(0);


        }
    }, 0, 125);
}else
{
    Score+=1;
    GameOver=0;
    callMove();
}


                        }else
                        {
                            callInvalidate(0);
                        }


                    }
                },0,50);
            }
        }


        return true;
    }


    private GameActivity getContextEx()
    {
        return (GameActivity)getContext();
    }
    private void callMove() {
        moveMaxX=(Position.left-LastX);
        moveMaxX-=(moveMaxX/16);
        Mover=new Timer();
        Mover.schedule(new TimerTask() {
            @Override
            public void run() {
                if(moveMaxX>0) {
                    moveMaxX -= 10;
                    Position.left-=10;
                    Position.right-=10;
                    X-=10;
                    callInvalidate(10);


                }
                else
                {
                    Mover.cancel();

                    if(GameOver==1)
                    {

                        getContextEx().openGameOver();
                    }
                    callInvalidate(0);


                }
            }
        },0,10);
    }


    private Point getXFly(int x,int y,int x1,int y1)
    {

        x=x1-x;
        y=y-y1;

        if(x>y)
        {
            if(x/2>y)
            {
                x=8;

            }else
            {
                x=4;
            }
            y=2;
        }

        if(y>x)
        {
            if(y/2>x)
            {
                y=8;

            }else
            {
                y=4;
            }
            x=2;
        }

        return new Point(x,y);


    }
}
