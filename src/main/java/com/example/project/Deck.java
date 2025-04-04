package com.example.project;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck{
    private ArrayList<Card> cards;

    public Deck(){
        cards = new ArrayList<>();
        initializeDeck();
        shuffleDeck();
    }

    public ArrayList<Card> getCards(){
        return cards;
    }

    public  void initializeDeck(){ //hint.. use the utility class
        cards.clear();
        ArrayList<Card> deck = new ArrayList<>();
        String[] suits = Utility.getSuits();
        String[] ranks = Utility.getRanks();
        for(int i = 0; i < suits.length; i++) {
            for(int x = 0; x < ranks.length; x++) {
                deck.add(new Card(suits[i], ranks[x]));
            }
        } 
        cards = deck;
        //creates a deck of cards by using a nested for loop to place each suit with every single rank
    }

    public  void shuffleDeck(){ //You can use the Collections library or another method. You do not have to create your own shuffle algorithm
        Collections.shuffle(cards);
    }

    public  Card drawCard(){
        if(cards.size() > 0) {
            Card i = cards.get(0);
            cards.remove(0);
            return i;
        }
       return new Card("","");
    }

    public  boolean isEmpty(){
        return cards.isEmpty();
    }

   


}