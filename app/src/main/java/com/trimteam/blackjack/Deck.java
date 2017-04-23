package com.trimteam.blackjack;

import java.util.ArrayList;

/**
 * Created by pedroneto on 23/04/17.
 */
public class Deck {

    /**
     * All cards of the deck. Played cards are removed
     */
    private ArrayList cards = new ArrayList();

    /**
     * Empty Constructor.
     */
    public Deck(){
        fillDeck();
    }

    /**
     * Method to fill the deck with all the 52 cards. 4(suits) * 13(types).
     */
    private void fillDeck() {
        int typeSelection = 0, suitSelection = 0;
        for(int i = 0; i < 52 ;i++){
            if(i%13 == 0 ) {
                typeSelection = 0;
                suitSelection++;
            }

            cards.add(new Card(Card.CardSuit.values()[suitSelection], Card.CardType.values()[typeSelection]));
            typeSelection++;
        }
    }

    /**
     * Remove a played card from the deck.
     * @param card Card to be removed
     * @return
     */
    public boolean removeFromDeck(Card card){
        return cards.remove(card);
    }

    /**
     * Add a new card to the deck.
     * @param card Card to be added.
     * @return
     */
    public boolean addToDeck(Card card){
        if(!cards.contains(card)){
            cards.add(card);
        }
        return false;
    }





}
