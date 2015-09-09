package com.games.biitworx.jumpingfrogs.helper.util;

import android.app.Activity;


import com.games.biitworx.jumpingfrogs.MainActivity;

import java.util.ArrayList;

/**
 * Created by WEIS on 24.08.2015.
 */
public class ShopHelperFlyer {


    private Activity MyActivity = null;
    public ShopHelperFlyer(Activity a)
    {
        MyActivity = a;

        init();
    }

    public void init()
    {
        mHelper = new IabHelper(MyActivity, MainActivity.KEY);
        MainActivity.sendTracking("Shop", "shop", "UX", "open shop");

        mHelper.startSetup(new
                                   IabHelper.OnIabSetupFinishedListener() {
                                       public void onIabSetupFinished(IabResult result) {
                                           if (result.isSuccess()) {
                                               CanBuy=true;
                                               ArrayList<String> l = new ArrayList<String>();

                                               l.add(SKU_BUY7);


                                               mHelper.queryInventoryAsync(true, l, mGotInventoryListener);
                                           }
                                       }
                                   });
    }


    public  String Price7=null;



    public  String SKU_BUY7="noads";

    public  int SKU_CODE_BUY1=61;
    public  int SKU_CODE_BUY2=62;
    public  int SKU_CODE_BUY3=63;
    public  int SKU_CODE_BUY4=64;
    public  int SKU_CODE_BUY5=65;
    public  int SKU_CODE_BUY6=66;
    public  int SKU_CODE_BUY7=51;

    public  int SKU_CODE2_BUY1=2;
    public  int SKU_CODE2_BUY2=3;
    public  int SKU_CODE2_BUY3=4;
    public  int SKU_CODE2_BUY4=5;
    public  int SKU_CODE2_BUY5=6;
    public  int SKU_CODE2_BUY6=97;
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


    IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        @Override
        public void onQueryInventoryFinished(final IabResult result,
                                             final Inventory inventory) {

            if(result.isSuccess()) {



                SkuDetails buychar7 = inventory.getSkuDetails(SKU_BUY7);
                if(buychar7!=null)
                {
                    Purchase  pOld = inventory.getPurchase(SKU_BUY7);
                    if(pOld !=null)
                    {
                        if(pOld.getToken()!=null)
                        {
                            MainActivity.saveBuy(SKU_CODE_BUY7,SKU_CODE2_BUY7);
                        }
                    }
                    Price7=buychar7.getPrice();
                }



            }
        }
    };


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

            mHelper.launchPurchaseFlow(MyActivity,SKU_BUY7,SKU_CODE_BUY7,mPurchaseFinishedListener);
        }
        catch(Exception e){
            MainActivity.sendTracking("Shop", "buy", "ERROR CALL7",e.getMessage());
            mHelper.dispose();
            init();
            try
            {
                mHelper.launchPurchaseFlow(MyActivity,SKU_BUY7,SKU_CODE_BUY7,mPurchaseFinishedListener);

            }catch (Exception e2){}
        }
    }


}
