package com.trimteam.blackjack;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class GameScreenActivity extends AppCompatActivity {

    private Board mBoard;
    GridLayout IAHandGrid, userHandGrid;
    LinearLayout parentLayout;
    Button hitButton, standButton;
    TextView pointsText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);
        startComponents();
        showCards();
        pointsText = (TextView) findViewById(R.id.pointsInfoTextView);
        IAHandGrid = (GridLayout) findViewById(R.id.IAHand);
        userHandGrid = (GridLayout) findViewById(R.id.userHand);
        standButton = (Button) findViewById(R.id.standButton);
        standButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mBoard.calculateIAPoints()>mBoard.calculateUserPoints()){
                    alertGameOver("Fez " + mBoard.calculateUserPoints() + " pontos. Contra " + mBoard.calculateIAPoints() + " da IA. Deseja continuar?");
                }
                else if(mBoard.calculateIAPoints()<mBoard.calculateUserPoints()){
                    alertGameOver("Venceu com  " + mBoard.calculateUserPoints() + " pontos. Contra " + mBoard.calculateIAPoints() + " da IA. Deseja continuar?");

                }
                else{
                    alertGameOver("Empate com " + mBoard.calculateUserPoints()+ " pontos. Deseja continuar? ");
                }
            }
        });
        hitButton = (Button) findViewById(R.id.hitButton);
        parentLayout = (LinearLayout) findViewById(R.id.linear_layout_game_board);
        hitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBoard.userMove();
                if(mBoard.calculateIAPoints()<17){
                    mBoard.IAMove();
                }
                updateGrids();
                pointsText.setText("User : "+mBoard.calculateUserPoints()+"\nIA: "+mBoard.calculateIAPoints());
                int result = mBoard.checkGameOver();
                if(result==1){
                    alertGameOver("Fez " + mBoard.calculateUserPoints() + " pontos. Contra " + mBoard.calculateIAPoints() + " da IA. Deseja continuar?");
                }
                else if(result == -1){
                    alertGameOver("Venceu com  " + mBoard.calculateUserPoints() + " pontos. Contra " + mBoard.calculateIAPoints() + " da IA. Deseja continuar?");
                }
            }
        });
        System.out.println();
    }


    private void alertGameOver(String text){
        new AlertDialog.Builder(this)
                .setTitle("Game Over")
                .setMessage(text)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startComponents();
                        showCards();
                        updateGrids();
                        hitButton.setEnabled(true);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        //TODO change to previous screen
                        startComponents();
                        showCards();
                        updateGrids();
                        hitButton.setEnabled(true);
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        updateGrids();
        pointsText.setText("User : "+mBoard.calculateUserPoints()+"\nIA: "+mBoard.calculateIAPoints());

    }

    private void updateGrids(){
        ArrayList<Card> userHand = mBoard.userHand();
        ArrayList<Card> IAHand = mBoard.IAHand();
        userHandGrid.removeAllViews();
        userHandGrid.setColumnCount(userHand.size());
        IAHandGrid.removeAllViews();
        IAHandGrid.setColumnCount(IAHand.size());


        ImageView cardImageView;
        for (int i = 0; i < userHand.size(); i++) {
            cardImageView = new ImageView(this.getApplicationContext());
            //cardImageView.setText(facilities.get(i));
            String resource = userHand.get(i).resource();
            int id = getResources().getIdentifier(resource, "drawable", getPackageName());
            cardImageView.setImageResource(id);

            userHandGrid.addView(cardImageView, i);
            cardImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            cardImageView.setAdjustViewBounds(true);

            //cardImageView.setCompoundDrawablesWithIntrinsicBounds(rightIc, 0, 0, 0);
            GridLayout.LayoutParams param =new GridLayout.LayoutParams();
            param.height = GridLayout.LayoutParams.MATCH_PARENT;
            int width = parentLayout.getWidth();
            if(width == 0) width = this.getWindow().getWindowManager().getDefaultDisplay().getWidth();
             param.width = width/11;
            param.rightMargin = 10;
            param.topMargin = 0;

            param.setGravity(Gravity.RIGHT);
            param.columnSpec = GridLayout.spec(i);
            param.rowSpec = GridLayout.spec(0);
            cardImageView.setLayoutParams (param);

        }

        for (int i = 0; i < IAHand.size(); i++) {
            cardImageView = new ImageView(this.getApplicationContext());
            //cardImageView.setText(facilities.get(i));
            int id = getResources().getIdentifier(IAHand.get(i).resource(), "drawable", getPackageName());
            cardImageView.setImageResource(id);
            IAHandGrid.addView(cardImageView, i);
            cardImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            cardImageView.setAdjustViewBounds(true);
            //cardImageView.setCompoundDrawablesWithIntrinsicBounds(rightIc, 0, 0, 0);
            GridLayout.LayoutParams param =new GridLayout.LayoutParams();
            param.height = GridLayout.LayoutParams.WRAP_CONTENT;
            int width = parentLayout.getWidth();
            if(width == 0) width = this.getWindow().getWindowManager().getDefaultDisplay().getWidth();
            param.width = width/11;
            param.rightMargin = 5;
            param.topMargin = 5;
            param.setGravity(Gravity.CENTER);
            param.columnSpec = GridLayout.spec(i);
            param.rowSpec = GridLayout.spec(0);
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
