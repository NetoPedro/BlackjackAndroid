package com.trimteam.blackjack;

import java.util.ArrayList;

/**
 * Created by pedroneto on 23/04/17.
 */
public class Deck {


   // private Card[] cards= new Card[52];
    private ArrayList cards = new ArrayList();
    public Deck(){
        fillDeck();
    }

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

    public boolean removeFromDeck(Card card){
        return cards.remove(card);
    }

    public boolean addToDeck(Card card){
        if(!cards.contains(card)){
            cards.add(card);
        }
        return false;
    }





}
