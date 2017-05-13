package com.trimteam.blackjack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BetActivity extends AppCompatActivity {

    private Button firstBetButton, secondBetButton, thirdBetButton, fourthBetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bet);
        firstBetButton = (Button) findViewById(R.id.firstBet);
        secondBetButton = (Button) findViewById(R.id.secondBet);
        thirdBetButton = (Button) findViewById(R.id.thirdBet);
        fourthBetButton = (Button) findViewById(R.id.fourthBet);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BetActivity.this, GameScreenActivity.class);
                i.putExtra("bet",((Button) view).getText());
                i.putExtra("points",BetActivity.this.getIntent().getExtras().getString("points"));
                startActivity(i);
            }
        };

        firstBetButton.setOnClickListener(clickListener);
        secondBetButton.setOnClickListener(clickListener);
        thirdBetButton.setOnClickListener(clickListener);
        fourthBetButton.setOnClickListener(clickListener);

    }
}
