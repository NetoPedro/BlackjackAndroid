package com.trimteam.blackjack;

import java.util.Random;

/**
 * Created by pedroneto on 22/04/17.
 */
public class Card {

    public enum CardType {
        AS,TWO,THREE,FOUR,FIVE,SIX,SEVEN,EIGHT,NINE,TEN,KING,QUEEN,VALET
    }

    public enum CardSuit{
        HEARTS,DIAMONDS,CLUBS, SPADES
    }

    private int  mValue ;
    private CardSuit mSuit;
    private CardType mType;

    public Card(CardSuit suit, CardType type){
        mType = type;
        mSuit = suit;
        defineCardValue();
    }

    private void defineCardValue(){
        switch (mType){
            case AS : mValue = 11;
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
            case VALET: mValue = 10;
                break;
        }
    }

    public CardType cardType(){
        return mType;
    }

    public int value(){
        return mValue;
    }

    public CardSuit cardSuit(){
        return mSuit;
    }
}
