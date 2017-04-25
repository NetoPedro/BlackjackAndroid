package com.trimteam.blackjack;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.GridView;

public class GameScreenActivity extends AppCompatActivity {

    private Board mBoard;
    GridLayout IAHandGrid, userHandGrid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);
        startComponents();
        showCards();
        IAHandGrid = (GridLayout) findViewById(R.id.IAHand);
        userHandGrid = (GridLayout) findViewById(R.id.userHand);

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
    }
}
