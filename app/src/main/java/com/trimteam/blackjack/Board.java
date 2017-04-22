package com.trimteam.blackjack;

import java.util.ArrayList;

/**
 * Created by pedroneto on 23/04/17.
 */
public class Board {

    private Deck mDeck ;
    private ArrayList<Card> userHand ;
    private ArrayList<Card> IAHand;
    private enum GameStates{
        PLAYER_TURN,IA_TURN
    }
    private GameStates gameState ;
    public Board(){
        mDeck = new Deck();
        gameState = GameStates.PLAYER_TURN;
        userHand = new ArrayList<>();
        IAHand = new ArrayList<>();
    }




}
