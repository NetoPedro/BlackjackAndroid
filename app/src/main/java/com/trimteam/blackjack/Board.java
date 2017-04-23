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

    public int checkGameOver(){
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
        if(points > 21) return 1;
        hasAS = false;
        points = 0;
        for(Card card: IAHand){
            if(card.cardType() == Card.CardType.AS){
                hasAS = true;
            }
            points += card.value();
        }
        if(hasAS && points>21){
            points -=10;
        }
        if(points > 21) return -1;
        return 0 ;
    }




}
