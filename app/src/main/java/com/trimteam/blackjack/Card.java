package com.trimteam.blackjack;

import java.util.Random;

/**
 * Created by pedroneto on 22/04/17.
 */
public class Card {
    /**
     * Types of cards. This type determines the value of the card
     */
    public enum CardType {
        ACE,TWO,THREE,FOUR,FIVE,SIX,SEVEN,EIGHT,NINE,TEN,KING,QUEEN,JACK
    }

    /**
     * Suits of cards. There are 13 types for each suit. One type for suit!
     */
    public enum CardSuit{
        HEARTS,DIAMONDS,CLUBS, SPADES
    }


    /**
     * Value of the card. Predetermined by card's type.
     */
    private int  mValue ;
    /**
     * Suit of the card
     */
    private CardSuit mSuit;
    /**
     * Type of the card
     */
    private CardType mType;

    /**
     * Constructor. Suit and Type needed.
     * @param suit
     * @param type
     */
    public Card(CardSuit suit, CardType type){
        mType = type;
        mSuit = suit;
        defineCardValue();
    }

    /**
     * Determines the card value, using is type as reference.
     */
    private void defineCardValue(){
        switch (mType){
            case ACE : mValue = 11;
                break;
            case TWO: mValue = 2;
                break;
            case THREE: mValue = 3;
                break;
            case FOUR: mValue = 4;
                break;
            case FIVE: mValue = 5;
                break;
            case SIX: mValue = 6;
                break;
            case SEVEN: mValue = 7;
                break;
            case EIGHT: mValue = 8;
                break;
            case NINE: mValue = 9;
                break;
            case TEN: ;
            case KING:;
            case QUEEN:;
            case JACK: mValue = 10;
                break;
        }
    }

    /**
     * Returns the card type
     * @return
     */
    public CardType cardType(){
        return mType;
    }

    /**
     * Returns the card value
     * @return
     */
    public int value(){
        return mValue;
    }


    public String resource(){
        String resource = "";
        if(mType.equals(CardType.ACE)||mType.equals(CardType.KING)||mType.equals(CardType.QUEEN)||mType.equals(CardType.JACK)){
            resource +=mType.name().toLowerCase();
        }
        else{
            resource+="c"+mValue;
        }
        resource+="_of_" + mSuit.name().toLowerCase();
        //resource+=".png";
        return resource;
    }

    /**
     * Returns card Suit.
     * @return
     */
    public CardSuit cardSuit(){
        return mSuit;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null ) return false;
        if(this == obj) return true;
        if(!(obj instanceof Card))  return false;
        Card that = (Card) obj;
        return that.mSuit.equals(mSuit) && that.mType.equals(mType);
    }

}
