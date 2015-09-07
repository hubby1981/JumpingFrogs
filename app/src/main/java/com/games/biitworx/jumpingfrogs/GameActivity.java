package com.games.biitworx.jumpingfrogs;

import android.app.Activity;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends Activity {

    Timer RosesAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.game_over).setVisibility(View.INVISIBLE);

            }
        });
RosesAnim=new Timer();
        RosesAnim.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.game).invalidate();
                    }
                });
            }
        },0,100);
    }

    public int dropOnRose()
    {
        Frog fr = (Frog)findViewById(R.id.frog);

        GameView ga = (GameView)findViewById(R.id.game);

        if(ga.dropOnRose(fr.Position))
            return 0;
        return 1;

    }


    public void update(int move)
    {
        try{
            findViewById(R.id.frog).invalidate();

            GameView ga = (GameView)findViewById(R.id.game);
            ga.move(move);
            ga.invalidate();
        }catch(Exception e){}


    }

    public void openGameOver()
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.game_over).setVisibility(View.VISIBLE);

            }
        });
    }

    public void updateEx(final int move)
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                update(move);
            }
        });
    }





}
