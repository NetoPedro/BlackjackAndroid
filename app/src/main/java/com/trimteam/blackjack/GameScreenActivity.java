package com.trimteam.blackjack;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class GameScreenActivity extends AppCompatActivity {

    private Board mBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);
        startComponents();
        showCards();
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
