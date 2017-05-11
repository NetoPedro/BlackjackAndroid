package com.trimteam.blackjack;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

public class MenuActivity extends AppCompatActivity {
    private boolean mIsBound = false;
    private static final int STARTING_COINS= 1000;
    private static final int BONUS_POINTS= 100;
    private SharedPreferences mPreferences ;
    //private Rater rater;
    private int points = 0;
    static boolean focus = false;
    private static boolean resumed = false;
    private ImageView shareIcon;
    private static boolean outraAtividade = false;
    private static Intent music;
    private static AudioManager am;
    private static Runnable mDelayedStopRunnable = new Runnable() {
        @Override
        public void run() {
        }
    };
    public static AudioManager.OnAudioFocusChangeListener afChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        // Permanent loss of audio focus
                        // Pause playback immediately
                        // Wait 30 seconds before stopping playback
                        mHandler.postDelayed(mDelayedStopRunnable, 30 * 1000);
                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        if (musicaOn) {
                            if (resumed && focus) {
                            }
                        }
                    }
                }


            };
    public static Handler mHandler = new Handler();
    public static boolean musicaOn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO rater = new Rater(this.getBaseContext(),this);
        //android.app.AlertDialog ad = rater.show();
        //if(ad!=null ) ad.show();
       /*TODO MobileAds.initialize(getApplicationContext(), "ca-app-pub-3410114126236036~1623476703");
        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/
        //SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        //musicaOn = (sharedPreferences.getBoolean("som_ligado",true));


        TextView title = (TextView) findViewById(R.id.text_main_title);
        //Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/gomarice_super_g_type_2.ttf");
       // title.setTypeface(typeface);

    }


    @Override
    protected void onStart() {
        super.onStart();
        ImageView play = (ImageView) findViewById(R.id.play);

        //mServ = new MusicService();

        focus = true;
        resumed = true;

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuActivity.this, GameScreenActivity.class);
                i.putExtra("points", points + "");
                    startActivity(i);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                outraAtividade = true;
            }
        });
        //ImageView opcoes = (ImageView) findViewById(R.id.settings);
        //opcoes.setOnClickListener(new View.OnClickListener() {
        //  @Override
        //   public void onClick(View view) {
        //       Intent i = new Intent(MainActivity.this, Opcoes.class);
        //        startActivity(i);
        //      outraAtividade = true;
        //     }
        //});

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


    @Override
    protected void onPause() {
        super.onPause();
        resumed = false;

    }


    @Override
    protected void onResume() {
        super.onResume();
        mPreferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE);
        Date date = new Date();
        date.setTime(System.currentTimeMillis());
        int  day = 0, month = 0, year = 0;
        String pointsString = new String();
        pointsString = mPreferences.getString("points", pointsString);
        if(!pointsString.isEmpty()) {
            points = Integer.parseInt(pointsString);
        }
        day = mPreferences.getInt("day", day);
        month = mPreferences.getInt("month", month);
        year = mPreferences.getInt("year", year);

        if(day!=date.getDay()){
            points  += BONUS_POINTS;
        }
        SharedPreferences.Editor editor = mPreferences.edit();
        if (mPreferences.getBoolean("firstrun", true)) {
            // Do first run stuff here then set 'firstrun' as false
            // using the following line to edit/commit prefs
            editor.putBoolean("firstrun", false).apply();
            points = STARTING_COINS;
            editor.putString("points",points + "");
        }
       // mPreferences.edit().putInt("points", points);
        editor.putInt("day", date.getDay()).apply();
        editor.putInt("month", date.getMonth()).apply();
        editor.putInt("year", date.getYear()).apply();
        //editor.commit();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        focus = hasFocus;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        resumed = false;
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        resumed = false;
        if (!outraAtividade) {
            //doUnbindService();
            //stopService(music);
          //  am.abandonAudioFocus(afChangeListener);
        }

    }

    @Override
    public void finish() {
        super.finish();
        resumed = false;


//        am.abandonAudioFocus(afChangeListener);

    }
}





