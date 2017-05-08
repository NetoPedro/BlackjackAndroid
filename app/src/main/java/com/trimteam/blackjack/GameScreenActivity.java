package com.trimteam.blackjack;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class GameScreenActivity extends AppCompatActivity {

    private Board mBoard;
    GridLayout IAHandGrid, userHandGrid;
    LinearLayout parentLayout;
    Button hitButton, standButton;
    TextView userPointsText, iaPointsText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);


        userPointsText = (TextView) findViewById(R.id.userPointsInfoTextView);
        iaPointsText = (TextView) findViewById(R.id.iaPointsInfoTextView);
        IAHandGrid = (GridLayout) findViewById(R.id.IAHand);
        userHandGrid = (GridLayout) findViewById(R.id.userHand);
        standButton = (Button) findViewById(R.id.standButton);
        standButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                while (mBoard.calculateIAPoints()<17){
                    mBoard.IAMove();
                    updateGrids();
                }

                if(mBoard.calculateIAPoints()>mBoard.calculateUserPoints() && mBoard.calculateIAPoints()<=21){
                    alertGameOver("Fez " + mBoard.calculateUserPoints() + " pontos. Contra " + mBoard.calculateIAPoints() + " da IA. Deseja continuar?");
                }
                else if(mBoard.calculateIAPoints()<mBoard.calculateUserPoints() || mBoard.calculateIAPoints()>21){
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
                int result = mBoard.checkGameOver();
                if(result==1){
                    alertGameOver("Fez " + mBoard.calculateUserPoints() + " pontos. Contra " + mBoard.calculateIAPoints() + " da IA. Deseja continuar?");
                }
                else if(result == -1){
                    alertGameOver("Venceu com  " + mBoard.calculateUserPoints() + " pontos. Contra " + mBoard.calculateIAPoints() + " da IA. Deseja continuar?");
                }
            }
        });

        startComponents();
        showCards();
        updateGrids();
        hitButton.setEnabled(true);
        System.out.println();
    }


    private void alertGameOver(String text){
       SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Game Over")
                .showCancelButton(true)
                .setCancelText("Don't play")
                .setContentText(text)
                .setConfirmText("Play Again")
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                        startComponents();
                        showCards();
                        updateGrids();
                        hitButton.setEnabled(true);
                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        startComponents();
                        showCards();
                        updateGrids();
                        hitButton.setEnabled(true);
                    }
                }
                );
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.setCanceledOnTouchOutside(false);
        sweetAlertDialog.show();

    }
/*
    private void alertGameOver(String text){
        hitButton.setEnabled(false);

        AlertDialog alert =  new AlertDialog.Builder(this)
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
        alert.setCancelable(false) ;
        alert.setCanceledOnTouchOutside(false);
    }*/




    private void updateGrids(){
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
            //cardImageView.setText(facilities.get(i));
            String resource = userHand.get(i).resource();
            int id = getResources().getIdentifier(resource, "drawable", getPackageName());
            cardImageView.setImageResource(id);

            userHandGrid.addView(cardImageView, i);
            cardImageView.setScaleType(ImageView.ScaleType.CENTER);
            cardImageView.setAdjustViewBounds(true);

            //cardImageView.setCompoundDrawablesWithIntrinsicBounds(rightIc, 0, 0, 0);
            GridLayout.LayoutParams param =new GridLayout.LayoutParams();
            param.height = GridLayout.LayoutParams.MATCH_PARENT ;
            int width = parentLayout.getWidth();
            if(width == 0) width = this.getWindow().getWindowManager().getDefaultDisplay().getWidth();
             param.width = width/maxSize -60;
            param.rightMargin = 30;
            param.leftMargin = 30;
            param.bottomMargin=30;
            param.topMargin = 30;

            param.setGravity(Gravity.RIGHT);
            param.columnSpec = GridLayout.spec(i);
            param.rowSpec = GridLayout.spec(0);
            cardImageView.setLayoutParams (param);
            userPointsText.setText("User Points: "+mBoard.calculateUserPoints());
            iaPointsText.setText("IA Points:"+mBoard.calculateIAPoints());

        }

        for (int i = 0; i < IAHand.size(); i++) {
            cardImageView = new ImageView(this.getApplicationContext());
            //cardImageView.setText(facilities.get(i));
            int id = getResources().getIdentifier(IAHand.get(i).resource(), "drawable", getPackageName());
            cardImageView.setImageResource(id);
            IAHandGrid.addView(cardImageView, i);
            cardImageView.setAdjustViewBounds(true);
            //cardImageView.setCompoundDrawablesWithIntrinsicBounds(rightIc, 0, 0, 0);
            GridLayout.LayoutParams param =new GridLayout.LayoutParams();
            param.height = GridLayout.LayoutParams.MATCH_PARENT  ;
            int width = parentLayout.getWidth();
            if(width == 0) width = this.getWindow().getWindowManager().getDefaultDisplay().getWidth();
            param.width = width/maxSize- 60;
            param.rightMargin =30;
            param.leftMargin = 30;
            param.topMargin = 30;
            param.bottomMargin=30;

            param.setGravity(Gravity.CENTER);
            param.columnSpec = GridLayout.spec(i);
            param.rowSpec = GridLayout.spec(0);
            cardImageView.setLayoutParams (param);

        }
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

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
