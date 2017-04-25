package com.trimteam.blackjack;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

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
        updateGrids();
    }

    private void updateGrids(){
        ArrayList<Card> userHand = mBoard.userHand();
        ArrayList<Card> IAHand = mBoard.IAHand();

        userHandGrid.setColumnCount(userHand.size());
        IAHandGrid.setColumnCount(IAHand.size());


        ImageView cardImageView;
        for (int i = 0; i < userHand.size(); i++) {
            cardImageView = new ImageView(this.getApplicationContext());
            //cardImageView.setText(facilities.get(i));
            userHandGrid.addView(cardImageView, i);
            //cardImageView.setCompoundDrawablesWithIntrinsicBounds(rightIc, 0, 0, 0);
            GridLayout.LayoutParams param =new GridLayout.LayoutParams();
            param.height = GridLayout.LayoutParams.WRAP_CONTENT;
            param.width = GridLayout.LayoutParams.WRAP_CONTENT;
            param.rightMargin = 5;
            param.topMargin = 5;
            param.setGravity(Gravity.CENTER);
            param.columnSpec = GridLayout.spec(i);
            param.rowSpec = GridLayout.spec(1);
            cardImageView.setLayoutParams (param);

        }

        for (int i = 0; i < IAHand.size(); i++) {
            cardImageView = new ImageView(this.getApplicationContext());
            //cardImageView.setText(facilities.get(i));
            IAHandGrid.addView(cardImageView, i);
            //cardImageView.setCompoundDrawablesWithIntrinsicBounds(rightIc, 0, 0, 0);
            GridLayout.LayoutParams param =new GridLayout.LayoutParams();
            param.height = GridLayout.LayoutParams.WRAP_CONTENT;
            param.width = GridLayout.LayoutParams.WRAP_CONTENT;
            param.rightMargin = 5;
            param.topMargin = 5;
            param.setGravity(Gravity.CENTER);
            param.columnSpec = GridLayout.spec(i);
            param.rowSpec = GridLayout.spec(1);
            cardImageView.setLayoutParams (param);

        }
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
