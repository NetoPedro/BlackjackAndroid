package com.trimteam.blackjack;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;

public class BetActivity extends AppCompatActivity {

    private SharedPreferences mPreferences;
    private int points;
    private int currentBet = 0;
    private static final int STARTING_COINS= 1000;
    private static final int BONUS_POINTS= 100;
    private Button firstBetButton, secondBetButton, thirdBetButton, fourthBetButton, startBt ;
    private TextView betText ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bet);
        mPreferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE);
        firstBetButton = (Button) findViewById(R.id.firstBet);
        secondBetButton = (Button) findViewById(R.id.secondBet);
        thirdBetButton = (Button) findViewById(R.id.thirdBet);
        fourthBetButton = (Button) findViewById(R.id.fourthBet);
        betText =(TextView) findViewById(R.id.currentBet);
        View.OnClickListener start = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BetActivity.this, GameScreenActivity.class);
                i.putExtra("bet",String.valueOf(currentBet));
                i.putExtra("points",points + "");
                startActivity(i);
            }
        } ;
         startBt = (Button) findViewById(R.id.start)
                ;
        startBt.setOnClickListener(start);
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button  viewBt = (Button) view ;
                String text = (String ) viewBt.getText();
                if (text != null) {


                int bet = Integer.parseInt(text);
                currentBet += bet;
                betText.setText("Current Bet: "+ currentBet);
                if (currentBet + Integer.parseInt(firstBetButton.getText().toString()) > points){
                    firstBetButton.setEnabled(false);
                }
                if (currentBet + Integer.parseInt(secondBetButton.getText().toString())> points){
                    secondBetButton.setEnabled(false);
                }
                if (currentBet + Integer.parseInt(thirdBetButton.getText().toString()) > points){
                    thirdBetButton.setEnabled(false);
                }
                if (currentBet +Integer.parseInt(fourthBetButton.getText().toString()) > points){
                    fourthBetButton.setEnabled(false);
                }
                }
            }
        };

        firstBetButton.setOnClickListener(clickListener);
        secondBetButton.setOnClickListener(clickListener);
        thirdBetButton.setOnClickListener(clickListener);
        fourthBetButton.setOnClickListener(clickListener);


    }


    private void checkButtonValue(Button button){
        if(Integer.parseInt(button.getText().toString()) > points )
            button.setEnabled(false);
        else
            button.setEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPoints();
        currentBet = 0;
        checkButtonValue(firstBetButton);
        checkButtonValue(secondBetButton);
        checkButtonValue(thirdBetButton);
        checkButtonValue(fourthBetButton);
    }

    private synchronized void getPoints(){
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
}
