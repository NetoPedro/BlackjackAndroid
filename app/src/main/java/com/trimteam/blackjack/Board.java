package com.trimteam.blackjack;

import java.util.ArrayList;

/**
 * Created by pedroneto on 23/04/17.
 */
public class Board {

    /**
     * Deck of the game
     */
    private Deck mDeck ;
    /**
     * User cards.
     */
    private ArrayList<Card> userHand ;
    /**
     * Computer's cards.
     */
    private ArrayList<Card> IAHand;

    /**
     * Game states enum.
     */
    private enum GameStates{
        PLAYER_TURN,IA_TURN
    }

    /**
     * Board Game State
     */
    private GameStates gameState ;

    /**
     * Empty constructor.
     */
    public Board(){
        mDeck = new Deck();
        gameState = GameStates.PLAYER_TURN;
        userHand = new ArrayList<>();
        IAHand = new ArrayList<>();
        setInitialHands();
    }

    /**
     * Defines random set of 2 cards to user and computer
     */
    private void setInitialHands(){

        while(userHand.size() != 2 || IAHand.size() != 2){
            if(userHand.size() != 2) {
                perfomMove(userHand);
            }
            if(IAHand.size()!=2){
                perfomMove(IAHand);
            }
        }
    }

    /**
     * Add a random card to user's hand
     * @return returns true if the user lose
     */
    public boolean userMove(){
        if(GameStates.PLAYER_TURN.equals(gameState)) {
            int initialSize = userHand.size();
            while (initialSize == userHand.size()) {
                perfomMove(userHand);
            }
            gameState = GameStates.IA_TURN;
        }
        return calculateUserPoints()>21;
    }

    /**
     * Add a random card to IA's hand.
     * @return returns true if the IA lose
     */
    public boolean IAMove(){

       if(gameState.equals(GameStates.IA_TURN)) {
           int initialSize = IAHand.size();
           while (initialSize == IAHand.size()) {
               perfomMove(IAHand);
           }
           gameState = GameStates.PLAYER_TURN;
       }
        return calculateIAPoints()>21;
    }

    /**
     * Generates a random card to add to a list
     * @param moveList
     */
    private void perfomMove(ArrayList<Card> moveList){
        int typeSelection = (int) Math.random() * 13,
                suitSelection = (int) Math.random() * 4;
        Card card = new Card(Card.CardSuit.values()[suitSelection], Card.CardType.values()[typeSelection]);
        if (mDeck.removeFromDeck(card)) moveList.add(card);

    }

    /**
     * Check if user or IA lose.
     * @return
     */
    public int checkGameOver(){
       int points = calculateUserPoints();
        if(points > 21) return 1;

        points = calculateIAPoints();
        if(points > 21) return -1;
        return 0 ;
    }

    /**
     * Calculates IA points
     * @return
     */
    private int calculateIAPoints() {
        return calculatePoints(IAHand);
    }

    /**
     * Calculates user points
     * @return
     */
    public int calculateUserPoints(){
       return calculatePoints(userHand);
    }

    /**
     * Calculates points of a list of cards.
     * @param pointsList
     * @return
     */
    private int calculatePoints(ArrayList<Card> pointsList){
        boolean hasAS = false;
        int points = 0;
        for(Card card: pointsList){
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
