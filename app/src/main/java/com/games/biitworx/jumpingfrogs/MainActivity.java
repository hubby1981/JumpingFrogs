package com.games.biitworx.jumpingfrogs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.games.biitworx.jumpingfrogs.helper.util.ShopHelperFlyer;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class MainActivity extends Activity {

    public static String KEY="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnT9RBmKLdMWNN3+NOZDuvsS6aGTLXDS9nG9lOhh8NgTwQJnPJVPITCpA0qou6xvdDAiAhfSMWkptVpZrAN8uO75ARW8A/jbj8k+1ZkUk6wdfltAUWKkSkOqTabH12ZQU3iwGHNCia5HZO4AS78iaIErMIYxnCv7V/+JJ/U3vODyBHGcYR5rfRU0tnFlkFuE5Vl0fqQ376YJe65owzQXErP2Ltr1qADATf7bGnK4OhWO9G7iwcbnF9nCJuE1kRer60hkz/xF5a1Egf/EyVk7FERZIV8WYWvILy7/6QZGVG4/VJde3DGis2zv8CaJ8OU7OgLnDL+bQ8mtBZipMpyLT0QIDAQAB";
    public static String KEY2="UA-65112560-4";
    public static Intent GA;
    public static GoogleAnalytics analytics;
    public static Tracker tracker;
    public static SharedPreferences Preferences;

    private ShopHelperFlyer Shop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        analytics = GoogleAnalytics.getInstance(this);
        analytics.setLocalDispatchPeriod(1080);

        tracker = analytics.newTracker(KEY2);
        tracker.enableExceptionReporting(true);
        tracker.enableAdvertisingIdCollection(true);
        tracker.enableAutoActivityTracking(true);
        setContentView(R.layout.activity_main);
        FacebookSdk.sdkInitialize(getApplicationContext());
        Preferences=getSharedPreferences(TXT.KEY_GLOBAL, Context.MODE_PRIVATE);
        Shop=new ShopHelperFlyer(this);



        sendTracking("Main", "info", "UX", "start app");

showAd();
/*
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.games.biitworx.jumpingfrogs", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }*/





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
        }
    }

public void buy()
{
    try
    {
        Shop.buyNoAds();
    }catch(Exception e){}
}

    public void play()
    {
        GA=new Intent(this,GameActivity.class);
        startActivity(GA);
    }

    public static void sendTracking(String screen, String label, String category, String action)
    {
        tracker.setScreenName(screen);
        tracker.send(new HitBuilders.EventBuilder()
                .setCategory(category)
                .setAction(action)
                .setLabel(label)
                .build());
    }


    protected static SharedPreferences getPref()
    {
        return Preferences;
    }
    public static int readBest()
    {
        SharedPreferences pref = getPref();
        int best =  pref.getInt(TXT.KEY_BEST, -1);
        return best==-1?0:best;
    }


    public static void saveBest(int best)
    {
        SharedPreferences pref = getPref();
        SharedPreferences.Editor edit = pref.edit();
        edit.putInt(TXT.KEY_BEST, best);
        edit.commit();
    }

    public static String readId()
    {
        SharedPreferences pref = getPref();
        String id =  pref.getString(TXT.KEY_ID, "-1");
        return id=="-1"? UUID.randomUUID().toString():id;
    }


    public static void saveId(String id)
    {
        SharedPreferences pref = getPref();
        SharedPreferences.Editor edit = pref.edit();
        edit.putString(TXT.KEY_ID, id);
        edit.commit();
    }

    public static int readAd()
    {
        SharedPreferences pref = getPref();
        int best =  pref.getInt(TXT.KEY_AD, -1);
        return best==-1?0:best;
    }


    public static void saveAd()
    {
        SharedPreferences pref = getPref();
        SharedPreferences.Editor edit = pref.edit();
        edit.putInt(TXT.KEY_AD, 1);
        edit.commit();
    }

    public static int readHigh()
    {
        SharedPreferences pref = getPref();
        int best =  pref.getInt(TXT.KEY_HIGH, -1);
        return best==-1?0:best;
    }

    public static void saveSpeed(int speed)
    {
        SharedPreferences pref = getPref();
        SharedPreferences.Editor edit = pref.edit();
        edit.putInt(TXT.KEY_SPEED, speed);
        edit.commit();
    }

    public static int readSpeed()
    {
        SharedPreferences pref = getPref();
        int best =  pref.getInt(TXT.KEY_SPEED, -1);
        return best==-1?20:best;
    }


    public static void saveHigh(int best)
    {
        SharedPreferences pref = getPref();
        SharedPreferences.Editor edit = pref.edit();
        edit.putInt(TXT.KEY_HIGH, best);
        edit.commit();
    }

    public static int readMusic()
    {
        SharedPreferences pref = getPref();
        int best =  pref.getInt(TXT.KEY_MUSIC, -1);
        return best==-1?0:best;
    }


    public static void saveMusic(int music)
    {
        SharedPreferences pref = getPref();
        SharedPreferences.Editor edit = pref.edit();
        edit.putInt(TXT.KEY_MUSIC,music);
        edit.commit();
    }

    public static int loadChar()
    {
        SharedPreferences pref = getPref();
        int best =  pref.getInt(TXT.KEY_CHAR, -1);
        return best==-1?1:best;
    }


    public static void saveChar(int best)
    {
        SharedPreferences pref = getPref();
        SharedPreferences.Editor edit = pref.edit();
        edit.putInt(TXT.KEY_CHAR, best);
        edit.commit();
    }


    public static int readBest(int code)
    {
        SharedPreferences pref = getPref();
        int best =  pref.getInt(TXT.KEY_BEST + String.valueOf(code), -1);
        return best==-1?0:best;
    }


    public static int readBuy(int code)
    {
        SharedPreferences pref = getPref();
        int buy =  pref.getInt(TXT.KEY_BUY + String.valueOf(code), -1);
        return buy==-1?0:buy;
    }
    public static void saveBuy(int buy,int code)
    {
        SharedPreferences pref = getPref();
        SharedPreferences.Editor edit = pref.edit();
        edit.putInt(TXT.KEY_BUY+String.valueOf(code), buy);
        edit.commit();
    }

}
