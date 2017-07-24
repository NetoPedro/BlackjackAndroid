package com.trimteam.blackjack;


import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MenuActivity extends AppCompatActivity {

    //private Rater rater;
    private int points = 0;
    private ImageView shareIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO rater = new Rater(this.getBaseContext(),this);
        //android.app.AlertDialog ad = rater.show();
        //if(ad!=null ) ad.show();
       /*TODO MobileAds.initialize(getApplicationContext(), "ca-app-pub-3410114126236036~1623476703");
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/


        //Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/gomarice_super_g_type_2.ttf");
       // title.setTypeface(typeface);

    }


    @Override
    protected void onStart() {
        super.onStart();
        ImageView play = (ImageView) findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuActivity.this, BetActivity.class);
                i.putExtra("points", points + "");
                    startActivity(i);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        shareIcon = (ImageView) findViewById(R.id.share);
        shareIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String urlToShare = "http://play.google.com/store/apps/details?id=com.trimteam.tictactoe";
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
// intent.putExtra(Intent.EXTRA_SUBJECT, "Foo bar"); // NB: has no effect!
                intent.putExtra(Intent.EXTRA_TEXT, urlToShare);

// See if official Facebook app is found
                boolean facebookAppFound = false;
                List<ResolveInfo> matches = getPackageManager().queryIntentActivities(intent, 0);
                for (ResolveInfo info : matches) {
                    if (info.activityInfo.packageName.toLowerCase().startsWith("com.facebook.katana")) {
                        intent.setPackage(info.activityInfo.packageName);
                        facebookAppFound = true;
                        break;
                    }
                }

// As fallback, launch sharer.php in a browser
                if (!facebookAppFound) {
                    String sharerUrl = "https://www.facebook.com/sharer/sharer.php?u=" + urlToShare;
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
                }

                startActivity(intent);
            }
        });
    }
}





