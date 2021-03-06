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

    public void removeNullCard() {
        IAHand.remove(new Card(Card.CardSuit.BACK, Card.CardType.NULL));
    }

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
    protected Board(){
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

        while(userHand.size() != 2 || IAHand.size() != 1){
            if(userHand.size() != 2) {
                perfomMove(userHand);
            }
            if(IAHand.size()!=1){
                perfomMove(IAHand);
            }
        }
        Card card = new Card(Card.CardSuit.BACK, Card.CardType.NULL);
        IAHand.add(card);
    }

    /**
     * Add a random card to user's hand
     * @return returns true if the user lose
     */
    public boolean userMove(){
            int initialSize = userHand.size();
            while (initialSize == userHand.size()) {
                perfomMove(userHand);

        }
        return calculateUserPoints()>21;
    }

    /**
     * Add a random card to IA's hand.
     * @return returns true if the IA lose
     */
    public boolean IAMove(){

           int initialSize = IAHand.size();
           while (initialSize == IAHand.size()) {
               perfomMove(IAHand);



       }
        return calculateIAPoints()>21;
    }

    /**
     * Generates a random card to add to a list
     * @param moveList
     */
    private void perfomMove(ArrayList<Card> moveList){
        long typeSelection =  Math.round(Math.random() * 12),
                suitSelection = Math.round( Math.random() * 3);
        Card card = new Card(Card.CardSuit.values()[(int)suitSelection], Card.CardType.values()[(int)typeSelection]);
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
    public int calculateIAPoints() {
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
            if(card.cardType() == Card.CardType.ACE){
                hasAS = true;
            }
            points += card.value();
        }
        if(hasAS && points>21){
            points -=10;
        }
        return points;
    }

    /**
     * Returns a new Board Object!
     * @return
     */
    public static Board startBoard(){
        return new Board();
    }

    /**
     * Returns the user set of cards
     * @return
     */
    public ArrayList<Card> userHand(){
        return  userHand;
    }

    /**
     * Returns the IA set of cards
     * @return
     */
    public ArrayList<Card> IAHand(){
        return IAHand;
    }

}
