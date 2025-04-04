package com.example.project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player {
    private ArrayList<Card> hand;
    private ArrayList<Card> allCards;
    String[] suits = Utility.getSuits();
    String[] ranks = Utility.getRanks();

    public Player() {
        hand = new ArrayList<>();
        allCards = new ArrayList<>();
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public ArrayList<Card> getAllCards() {
        return allCards;
    }

    public void addCard(Card c) {
        hand.add(c);
    }

    public String playHand(ArrayList<Card> communityCards) {
        allCards.clear();
        allCards.addAll(hand);
        allCards.addAll(communityCards);
        sortAllCards();

        if (isRoyalFlush()) return "Royal Flush";
        if (isStraightFlush()) return "Straight Flush";
        if (isFourOfAKind()) return "Four of a Kind";
        if (isFullHouse()) return "Full House";
        if (isFlush()) return "Flush";
        if (isStraight()) return "Straight";
        if (isThreeOfAKind()) return "Three of a Kind";
        if (isTwoPair()) return "Two Pair";
        if (isPair()) return "A Pair";
        if (hasHighCard()) return "High Card";
        return "Nothing";
    }

    private boolean isRoyalFlush() {
        if (!isStraightFlush()) return false;
        
        boolean hasTen = false;
        boolean hasJack = false;
        boolean hasQueen = false;
        boolean hasKing = false;
        boolean hasAce = false;
        
        for (Card card : allCards) {
            String rank = card.getRank();
            if (rank.equals("10")) hasTen = true;
            if (rank.equals("J")) hasJack = true;
            if (rank.equals("Q")) hasQueen = true;
            if (rank.equals("K")) hasKing = true;
            if (rank.equals("A")) hasAce = true;
        }
        
        return hasTen && hasJack && hasQueen && hasKing && hasAce;
    }

    private boolean isStraightFlush() {
        return isFlush() && isStraight();
    }

    private boolean isFourOfAKind() {
        String[] ranks = getAllRanks();
        for (int i = 0; i < ranks.length; i++) {
            int count = 1;
            for (int j = i + 1; j < ranks.length; j++) {
                if (ranks[i].equals(ranks[j])) count++;
            }
            if (count == 4) return true;
        }
        return false;
    }

    private boolean isFullHouse() {
        ArrayList<Integer> rankFrequency = findRankingFrequency();
        boolean hasThree = false;
        boolean hasTwo = false;
    
        for (int count : rankFrequency) {
            if (count == 3) {
                hasThree = true;
            } else if (count == 2) {
                hasTwo = true;
            }
        }
    
        return hasThree && hasTwo; // full house = three + pair
    }

    private boolean isFlush() {
        String[] suits = getAllSuits();
        for (int i = 0; i < suits.length; i++) {
            int count = 1;
            for (int j = i + 1; j < suits.length; j++) {
                if (suits[i].equals(suits[j])) count++;
            }
            if (count >= 5) return true;
        }
        return false;
    }

    private boolean isStraight() {
        ArrayList<Integer> rankValues = new ArrayList<>();
        for (Card card : allCards) {
            rankValues.add(Utility.getRankValue(card.getRank()));
        }

        int x = 1;
        for (int i = 1; i < rankValues.size(); i++) {
            if (rankValues.get(i) == rankValues.get(i-1) + 1) {
                x++;
                if (x == 5) return true;
            } else if (rankValues.get(i) != rankValues.get(i-1)) {
                x = 1;
            }
        } //checks for 5 consecutive ranks (x)

        boolean hasAce = false;
        boolean hasTwo = false;
        boolean hasThree = false;
        boolean hasFour = false;
        boolean hasFive = false;
        
        for (Card card : allCards) {
            String rank = card.getRank();
            if (rank.equals("A")) hasAce = true;
            if (rank.equals("2")) hasTwo = true;
            if (rank.equals("3")) hasThree = true;
            if (rank.equals("4")) hasFour = true;
            if (rank.equals("5")) hasFive = true;
        }
        
        return hasAce && hasTwo && hasThree && hasFour && hasFive;
    }

    private boolean isThreeOfAKind() {
        ArrayList<Integer> rankFrequency = findRankingFrequency();
        boolean hasThree = false;
    
        for (int count : rankFrequency) {
            if (count == 3) {
                if (hasThree) {
                    return false;
                }
                hasThree = true;
            }
        }
    
        return hasThree;
    }
    
    
    private boolean isTwoPair() {
        String[] ranks = getAllRanks();
        int pairCount = 0;
        for (int i = 0; i < ranks.length; i++) {
            int count = 1;
            for (int j = i + 1; j < ranks.length; j++) {
                if (ranks[i].equals(ranks[j])) count++;
            }
            if (count == 2) {
                pairCount++;
                i++; //skip the next element to avoid recounting
            }
        }
        return pairCount >= 2;
    }
    
    private boolean isPair() {
        String[] ranks = getAllRanks();
        for (int i = 0; i < ranks.length; i++) {
            int count = 1;
            for (int j = i + 1; j < ranks.length; j++) {
                if (ranks[i].equals(ranks[j])) count++;
            }
            if (count == 2) return true;
        }
        return false;
    }
    
    //method to extract all ranks from allCards
    private String[] getAllRanks() {
        String[] ranks = new String[allCards.size()];
        for (int i = 0; i < allCards.size(); i++) {
            ranks[i] = allCards.get(i).getRank();
        }
        return ranks;
    }

    private String[] getAllSuits() {
        String[] suits = new String[allCards.size()];
        for (int i = 0; i < allCards.size(); i++) {
            suits[i] = allCards.get(i).getSuit();
        }
        return suits;
    } 
    //similar to getAllRanks, but for suits

    private boolean hasHighCard() {
        return !allCards.isEmpty();
    }
    


    public void sortAllCards() {
        //finds the value of each card
        for (int i = 1; i < allCards.size(); i++) {
            Card currentCard = allCards.get(i);
            int value = Utility.getRankValue(currentCard.getRank());
            int j = i - 1;
   
            //move elements that are greater than value one spot ahead
            //repeats until the list is correctly sortly in ascending order
            while (j >= 0 && Utility.getRankValue(allCards.get(j).getRank()) > value) {
                allCards.set(j + 1, allCards.get(j));
                j--;
            }
            allCards.set(j + 1, currentCard);
        }
    }


    
    public ArrayList<Integer> findRankingFrequency(){
        ArrayList<Integer> list = new ArrayList<Integer>();
        for(int x = 0; x < ranks.length; x++) {
            list.add(0);
        }
        for (Card card : allCards) {
            String rank = card.getRank();
            for (int i = 0; i < ranks.length; i++) {
                if (ranks[i].equals(rank)) {
                    list.set(i, list.get(i) + 1);
                }
            }
        }
        //creates a new empty ArrayList with 13 0's, when iterating through the community and hand cards, the new ArrayList's 0's will update depending on what ranks are found on the cards
        return new ArrayList<>(list);
    }


    public ArrayList<Integer> findSuitFrequency(){
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int x = 0; x < suits.length; x++) {
            list.add(0);
        }
        for (Card card: allCards) {
            String suit = card.getSuit();
            for (int i = 0; i < suits.length; i++) {
                if(suits[i].equals(suit)) {
                    list.set(i, list.get(i) + 1);
                }
            }
        }
        //works the same as rankfrequency, just with suits
        return new ArrayList<>(list);
    }


    @Override
    public String toString() {
        return hand.toString();
    }
}

