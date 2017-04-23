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
        setInitialHands();
    }

    private void setInitialHands(){

        while(userHand.size() != 2 || IAHand.size() != 2){
            if(userHand.size() != 2) {
                int typeSelection = (int) Math.random() * 13,
                        suitSelection = (int) Math.random() * 4;
                Card card = new Card(Card.CardSuit.values()[suitSelection], Card.CardType.values()[typeSelection]);
                if (mDeck.removeFromDeck(card)) userHand.add(card);
            }
            if(IAHand.size()!=2){
                int typeSelection = (int) Math.random() * 13,
                        suitSelection = (int) Math.random() * 4;
                Card card = new Card(Card.CardSuit.values()[suitSelection], Card.CardType.values()[typeSelection]);
                if (mDeck.removeFromDeck(card)) IAHand.add(card);
            }
        }
    }

    public boolean userMove(){
        int initialSize = userHand.size();
        while(initialSize == userHand.size()) {
            int typeSelection = (int) Math.random() * 13,
                    suitSelection = (int) Math.random() * 4;
            Card card = new Card(Card.CardSuit.values()[suitSelection], Card.CardType.values()[typeSelection]);
            if (mDeck.removeFromDeck(card)) userHand.add(card);
        }
        return calculateUserPoints()>21;
    }


    public boolean IAMove(){
        int initialSize = IAHand.size();
        while(initialSize == IAHand.size()) {
            int typeSelection = (int) Math.random() * 13,
                    suitSelection = (int) Math.random() * 4;
            Card card = new Card(Card.CardSuit.values()[suitSelection], Card.CardType.values()[typeSelection]);
            if (mDeck.removeFromDeck(card)) IAHand.add(card);
        }
        return calculateIAPoints()>21;
    }


    public int checkGameOver(){
       int points = calculateUserPoints();
        if(points > 21) return 1;

        points = calculateIAPoints();
        if(points > 21) return -1;
        return 0 ;
    }

    private int calculateIAPoints() {
        boolean hasAS = false;
        int points = 0;
        for(Card card: IAHand){
            if(card.cardType() == Card.CardType.AS){
                hasAS = true;
            }
            points += card.value();
        }
        if(hasAS && points>21){
            points -=10;
        }
        return points;
    }


    public int calculateUserPoints(){
        boolean hasAS = false;
        int points = 0;
        for(Card card: userHand){
            if(card.cardType() == Card.CardType.AS){
                hasAS = true;
            }
            points += card.value();
        }
        if(hasAS && points>21){
            points -=10;
        }
        return points;
    }



}
