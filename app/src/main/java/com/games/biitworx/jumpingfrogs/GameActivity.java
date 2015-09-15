package com.games.biitworx.jumpingfrogs;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.apptracker.android.track.AppTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.ShareApi;
import com.facebook.share.model.SharePhotoContent;
import com.games.biitworx.jumpingfrogs.helper.BitmapHelper;
import com.games.biitworx.jumpingfrogs.helper.FontHelper;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends Activity {

    Timer RosesAnim;
public static Bitmap LastBitmap1;
    public static Bitmap LastBitmap2;
    public static Bitmap LastBitmap3;
    public static Bitmap LastBitmap4;

    public  CallbackManager callbackManager;
    public  LoginManager manager;

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
        }, 0, 100);



    }
    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent data)
    {
        super.onActivityResult(requestCode, responseCode, data);
        callbackManager.onActivityResult(requestCode, responseCode, data);
    }

    public  void publish(final SharePhotoContent content)
    {
        callbackManager = CallbackManager.Factory.create();

        manager = LoginManager.getInstance();

        List<String> permissionNeeds = Arrays.asList("publish_actions");

        manager.logInWithPublishPermissions(this, permissionNeeds);

        manager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                ShareApi.share(content, null);
                closeMe();
            }

            @Override
            public void onCancel() {
                System.out.println("onCancel");
            }

            @Override
            public void onError(FacebookException exception) {
                System.out.println("onError");
            }
        });

    }


    public int dropOnRose()
    {
        Frog fr = (Frog)findViewById(R.id.frog);

        GameView ga = (GameView)findViewById(R.id.game);

        if(ga.dropOnRose(fr.Position))
            return 0;
        return 1;

    }

    public void closeMe()
    {
        startActivity(new Intent(this, MainActivity.class));

        finish();

    }

    public void closeMeEx()
    {
        startActivity(new Intent(this, GameActivity.class));

        finish();


    }

    public void takeScreen(int state)
    {
        if(state==5)
            LastBitmap2=getScreen();
        else if(state==6)
            LastBitmap3=getScreen();
        else if(state==7)
            LastBitmap4=getScreen();
        else if(state==3)
            LastBitmap1=getScreen();
        else
            LastBitmap4=getScreen();
    }

    private Bitmap getScreen()
    {
        Bitmap a = screenShot(findViewById(R.id.game));
        Bitmap b = screenShot(findViewById(R.id.frog));

        Frog f = (Frog)findViewById(R.id.frog);


        int x=(int)(f.getWidth()/4.5f);
        int y=(int)(f.getHeight()/2.5f);

        int w = (int)x/20;

        Bitmap d = Bitmap.createBitmap(x, y, Bitmap.Config.ARGB_4444);
        Bitmap d2 = Bitmap.createBitmap(x, y, Bitmap.Config.ARGB_4444);
        Rect d1=new Rect(0+x/w,0+y/w,x-(x/w),y-y/5);

        Rect d11=new Rect(0+x/w,(0+y)-y/5,x-(x/w),y);


        Canvas bits = new Canvas(d);


        int xn = 0 - (f.Position.left-x/4);
        int yn = (int)(0-(f.Position.top-y/1.6f));

        bits.translate(xn,yn);
        bits.drawBitmap(a,0,0,null);
        bits.drawBitmap(b, 0, 0, null);





        Canvas bits2 = new Canvas(d2);
        Paint p = new Paint();
        p.setColor(Color.WHITE);

        Paint p3 = new Paint();
        p3.setColor(Color.DKGRAY);
        p3.setStyle(Paint.Style.STROKE);
        p3.setStrokeWidth(2);

        bits2.drawRect(new Rect(0, 0, x, y), p);
        bits2.drawRect(new Rect(0, 0, x, y), p3);

        BitmapHelper.drawIn(bits2, d1, d);
        bits2.drawRect(d1, p3);

        String text = (String) android.text.format.DateFormat.format("yyyy-MM-dd hh:mm",new Date())+" : "+String.valueOf(f.Score);
        Paint p1 = new Paint();
        p1.setColor(Color.argb(255, 50,50,50));
        p1.setFakeBoldText(true);
        p1.setTextSize(d11.height()/2);
        bits2.drawText(text, FontHelper.drawTextX(text, p1, d11.centerX()),d11.exactCenterY(),p1);

        return d2;
    }

    public void showAd()
    {
        if(MainActivity.readBuy(51)==0) {
            findViewById(R.id.adView).setVisibility(View.VISIBLE);

            AdRequest.Builder adRequestBuilder = new AdRequest.Builder();

            adRequestBuilder.addTestDevice("2A399D156F5F2E0FEE7B0056DD3D0D56");
            AdView view = (AdView) findViewById(R.id.adView);
            AdRequest r = adRequestBuilder.build();
            view.loadAd(r);

            AppTracker.loadModule(getApplicationContext(),"inapp");
        }
    }

    private Bitmap screenShot(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(), Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
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
                showAd();
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
