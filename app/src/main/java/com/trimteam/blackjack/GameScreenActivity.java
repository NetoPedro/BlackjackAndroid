package com.trimteam.blackjack;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class GameScreenActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer ;
    private SharedPreferences mPreferences;
    private Board mBoard;
    GridLayout IAHandGrid, userHandGrid;
    LinearLayout parentLayout;
    Button hitButton, standButton;
    TextView userPointsText, iaPointsText, coinsText;
    private int bet = 1; //TODO change bet to no constant value
    private int points;
    @Override
    //TODO save on preferences the new score
    protected synchronized void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);
        mPreferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE);
        coinsText = (TextView) findViewById(R.id.pointsText);
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        String pointsString = bundle.getString("points");
        points = Integer.parseInt(pointsString);
        bet = Integer.parseInt(bundle.getString("bet"));
        mediaPlayer = MediaPlayer.create(this,R.raw.card_slide6);
        userPointsText = (TextView) findViewById(R.id.userPointsInfoTextView);
        iaPointsText = (TextView) findViewById(R.id.iaPointsInfoTextView);
        IAHandGrid = (GridLayout) findViewById(R.id.IAHand);
        userHandGrid = (GridLayout) findViewById(R.id.userHand);
        standButton = (Button) findViewById(R.id.standButton);
        standButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public synchronized void onClick(View view) {
               mBoard.removeNullCard();
                final Semaphore mutex = new Semaphore(0);
                while (mBoard.calculateIAPoints()<21 && mBoard.calculateIAPoints()<mBoard.calculateUserPoints()){
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Runnable runnable =new Runnable() {
                        @Override
                        public synchronized void run() {
                            mBoard.IAMove();
                            updateGrids();
                            mutex.release();
                        }
                    };
                    runOnUiThread(runnable);
                    try {
                        mutex.acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }

                if(mBoard.calculateIAPoints()>mBoard.calculateUserPoints() && mBoard.calculateIAPoints()<=21){
                    alertGameOver("Lost with " + mBoard.calculateUserPoints() + " points. Dealer won with " + mBoard.calculateIAPoints() + " points. Keep trying? ?", "Defeat");
                    coinsText.setText(points + "");

                }
                else if(mBoard.calculateIAPoints()<mBoard.calculateUserPoints() || mBoard.calculateIAPoints()>21){
                    points+=2*bet;
                    alertGameOver("Won with  " + mBoard.calculateUserPoints() + " points. Dealer lost with " + mBoard.calculateIAPoints() + " points. Try to won again ?", "Victory");
                    coinsText.setText(points + "");
                }
                else{
                    points+=bet;
                    alertGameOver("Draw with " + mBoard.calculateUserPoints()+ " points. Keep trying? ", "Draw");
                }
            }
        });
        hitButton = (Button) findViewById(R.id.hitButton);
        parentLayout = (LinearLayout) findViewById(R.id.linear_layout_game_board);
        hitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBoard.userMove();
                updateGrids();
                int result = mBoard.checkGameOver();
                if(result==1){
                    alertGameOver("Lost with " + mBoard.calculateUserPoints() + " points. Dealer won with " + mBoard.calculateIAPoints() + " points. Keep trying?", "Defeat");
                    coinsText.setText(points + "");
                }
            }
        });

        startComponents();
        showCards();
        updateGrids();
        hitButton.setEnabled(true);
        System.out.println();

    }


    private synchronized void alertGameOver(String text , String title){
        mPreferences.edit().putString("points", points + "").apply();
        boolean canKeepGoing = true;
        if(bet>points)  canKeepGoing = false;
        final boolean finalCanKeepGoing = canKeepGoing;
        SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(title)
                .showCancelButton(true)
                .setCancelText("Don't play")
                .setContentText(text)
                .setConfirmText("Play Again")
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                        finish();
                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        if(finalCanKeepGoing) {
                            sDialog.dismissWithAnimation();
                            startComponents();
                            showCards();
                            updateGrids();
                            hitButton.setEnabled(true);
                        }
                        else{
                            sDialog.dismissWithAnimation();
                            finish();
                        }
                    }
                }
                );
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.setCanceledOnTouchOutside(false);
        sweetAlertDialog.show();

    }

    private void cardSound(){
        mediaPlayer.start();
    }


    private synchronized boolean updateGrids(){

        cardSound();
        ArrayList<Card> userHand = mBoard.userHand();
        ArrayList<Card> IAHand = mBoard.IAHand();
        int maxSize ;
        if(userHand.size()>IAHand.size()){
            maxSize = userHand.size();
        }
        else {
            maxSize = IAHand.size();
        }
        userHandGrid.removeAllViews();
        userHandGrid.setColumnCount(userHand.size());
        IAHandGrid.removeAllViews();
        IAHandGrid.setColumnCount(IAHand.size());


        ImageView cardImageView;
        for (int i = 0; i < userHand.size(); i++) {
            cardImageView = new ImageView(this.getApplicationContext());
            String resource = userHand.get(i).resource();
            int id = getResources().getIdentifier(resource, "drawable", getPackageName());
            cardImageView.setImageResource(id);
            if(i==userHand.size()){
                cardImageView.setAnimation(AnimationUtils.loadAnimation(this,android.R.anim.fade_in));
                cardImageView.animate();
            }
            userHandGrid.addView(cardImageView, i);
            cardImageView.setScaleType(ImageView.ScaleType.CENTER);
            cardImageView.setAdjustViewBounds(true);

            GridLayout.LayoutParams param =new GridLayout.LayoutParams();
            param.height = GridLayout.LayoutParams.MATCH_PARENT ;
            int width = parentLayout.getWidth();
            if(width == 0) width = this.getWindow().getWindowManager().getDefaultDisplay().getWidth();
             param.width = width/maxSize -90;
            param.rightMargin = 45;
            param.leftMargin = 45;
            param.bottomMargin=30;
            param.topMargin = 30;

            param.setGravity(Gravity.RIGHT);
            param.columnSpec = GridLayout.spec(i);
            param.rowSpec = GridLayout.spec(0);
            cardImageView.setLayoutParams (param);
            userPointsText.setText("User Points: "+mBoard.calculateUserPoints());
            iaPointsText.setText("Dealer Points:"+mBoard.calculateIAPoints());

        }

        for (int i = 0; i < IAHand.size(); i++) {
            cardImageView = new ImageView(this.getApplicationContext());
            int id = getResources().getIdentifier(IAHand.get(i).resource(), "drawable", getPackageName());
            cardImageView.setImageResource(id);
            if(i == IAHand.size()){
                cardImageView.setAnimation(AnimationUtils.loadAnimation(this,android.R.anim.fade_in));
                cardImageView.animate();
            }
            IAHandGrid.addView(cardImageView, i);
            cardImageView.setAdjustViewBounds(true);
            GridLayout.LayoutParams param =new GridLayout.LayoutParams();
            param.height = GridLayout.LayoutParams.MATCH_PARENT  ;
            int width = parentLayout.getWidth();
            if(width == 0) width = this.getWindow().getWindowManager().getDefaultDisplay().getWidth();
            param.width = width/maxSize- 90;
            param.rightMargin =45;
            param.leftMargin = 45;
            param.topMargin = 30;
            param.bottomMargin=30;
            param.setGravity(Gravity.CENTER);
            param.columnSpec = GridLayout.spec(i);
            param.rowSpec = GridLayout.spec(0);
            cardImageView.setLayoutParams (param);

        }
        IAHandGrid.invalidate();
        userHandGrid.invalidate();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        IAHandGrid.invalidate();
        userHandGrid.invalidate();
        return true;
    }

    /**
     * Method to display user and IA cards.
     */
    private void showCards() {
    }

    /**
     * Method to start Board.
     */
    private void startComponents() {
        mBoard = Board.startBoard();
        points-=bet;
        mPreferences.edit().putString("points", points + "").apply();
        coinsText.setText(points + "");

    }
}
