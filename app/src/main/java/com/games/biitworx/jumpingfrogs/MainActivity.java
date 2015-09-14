package com.games.biitworx.jumpingfrogs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.facebook.FacebookSdk;
import com.games.biitworx.jumpingfrogs.helper.util.IabHelper;
import com.games.biitworx.jumpingfrogs.helper.util.IabResult;
import com.games.biitworx.jumpingfrogs.helper.util.Inventory;
import com.games.biitworx.jumpingfrogs.helper.util.Purchase;
import com.games.biitworx.jumpingfrogs.helper.util.SkuDetails;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.ArrayList;
import java.util.UUID;

public class MainActivity extends Activity {

    public static String KEY="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnT9RBmKLdMWNN3+NOZDuvsS6aGTLXDS9nG9lOhh8NgTwQJnPJVPITCpA0qou6xvdDAiAhfSMWkptVpZrAN8uO75ARW8A/jbj8k+1ZkUk6wdfltAUWKkSkOqTabH12ZQU3iwGHNCia5HZO4AS78iaIErMIYxnCv7V/+JJ/U3vODyBHGcYR5rfRU0tnFlkFuE5Vl0fqQ376YJe65owzQXErP2Ltr1qADATf7bGnK4OhWO9G7iwcbnF9nCJuE1kRer60hkz/xF5a1Egf/EyVk7FERZIV8WYWvILy7/6QZGVG4/VJde3DGis2zv8CaJ8OU7OgLnDL+bQ8mtBZipMpyLT0QIDAQAB";
    public static String KEY2="UA-65112560-4";
    public static Intent GA;
    public static GoogleAnalytics analytics;
    public static Tracker tracker;
    public static SharedPreferences Preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        analytics = GoogleAnalytics.getInstance(this);
        analytics.setLocalDispatchPeriod(1080);

        tracker = analytics.newTracker(KEY2);
        tracker.enableAdvertisingIdCollection(true);
        tracker.enableExceptionReporting(true);
        tracker.enableAdvertisingIdCollection(true);
        tracker.enableAutoActivityTracking(true);
        setContentView(R.layout.activity_main);
        init();
        FacebookSdk.sdkInitialize(getApplicationContext());
        Preferences=getSharedPreferences(TXT.KEY_GLOBAL, Context.MODE_PRIVATE);




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

    public void init()
    {
        mHelper = new IabHelper(this, MainActivity.KEY);


        mHelper.startSetup(new
                                   IabHelper.OnIabSetupFinishedListener() {
                                       public void onIabSetupFinished(IabResult result) {
                                           if (result.isSuccess()) {
                                               CanBuy=true;
                                               ArrayList<String> l = new ArrayList<String>();

                                               l.add(SKU_BUY7);
                                               MainActivity.sendTracking("Shop", "shop", "UX", "open shop");

                                           }
                                           else

                                           {
                                               MainActivity.sendTracking("Error Shop", "shop", "UX", "open shop " + result.getMessage());
                                           }
                                       }
                                   });
    }


    public  String Price7=null;



    public  String SKU_BUY7="noads";


    public  int SKU_CODE_BUY7=51;


    public  int SKU_CODE2_BUY7=51;


    public String getPrice(int code)
    {

        if(code==7)return Price7;
        return "0.99";
    }



    public final String TAG ="com.games.billing";

    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener
            = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result,
                                          Purchase purchase)
        {
            if (result.isFailure()) {
                // Handle error
                MainActivity.sendTracking("Shop", "buy", "ERROR", result.getMessage());
                return;
            }
            else {
                MainActivity.sendTracking("Shop", "buy", "Consume",result.getMessage());
                mHelper.consumeAsync(purchase, mConsumeFinishedListener);
            }

        }
    };

    public IabHelper mHelper;
    public static boolean CanBuy=false;
    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mHelper != null) {
            mHelper.dispose();
            mHelper = null;
        }
    }



    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener =
            new IabHelper.OnConsumeFinishedListener() {
                public void onConsumeFinished(Purchase purchase,
                                              IabResult result) {

                    if (result.isSuccess()) {
                        MainActivity.sendTracking("Shop", "buy", "SUCCESS CONSUME",result.getMessage());
                        if (purchase.getSku().equals(SKU_BUY7)) {

                            MainActivity.saveBuy(SKU_CODE_BUY7,SKU_CODE2_BUY7);
                        }

                    }


                    else {
                        // handle error
                        MainActivity.sendTracking("Shop", "buy", "ERROR CONSUME",result.getMessage());
                    }
                }
            };


    public void buyNoAds()
    {
        try{
            MainActivity.sendTracking("Shop", "buy", "UX", "no ads");

            mHelper.launchPurchaseFlow(this,SKU_BUY7,SKU_CODE_BUY7,mPurchaseFinishedListener);
        }
        catch(Exception e){
            MainActivity.sendTracking("Shop", "buy", "ERROR CALL7",e.getMessage());
            mHelper.dispose();
            init();
            try
            {
                mHelper.launchPurchaseFlow(this,SKU_BUY7,SKU_CODE_BUY7,mPurchaseFinishedListener);

            }catch (Exception e2){}
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (mHelper == null) return;

        // Pass on the activity result to the helper for handling
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            // not handled, so handle it ourselves (here's where you'd
            // perform any handling of activity results not related to in-app
            // billing...
            super.onActivityResult(requestCode, resultCode, data);
        }
        else {
            Log.d(TAG, "onActivityResult handled by IABUtil.");
        }
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
        buyNoAds();
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
