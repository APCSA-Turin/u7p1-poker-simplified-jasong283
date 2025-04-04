package com.example.project;
import java.util.ArrayList;
import java.util.Collections;


public class Game {

    public static String determineWinner(Player p1, Player p2, String p1Hand, String p2Hand, ArrayList<Card> communityCards) {
        int p1Rank = Utility.getRankValue(p1Hand);
        int p2Rank = Utility.getRankValue(p2Hand);

        if (p1Rank > p2Rank) {
            return "Player 1 wins!";
        } else if (p2Rank > p1Rank) {
            return "Player 2 wins!";
        } else {
            
            //tiebreaker
            ArrayList<Card> p1Cards = new ArrayList<>(p1.getHand());
            ArrayList<Card> p2Cards = new ArrayList<>(p2.getHand());

            p1Cards.addAll(communityCards);
            p2Cards.addAll(communityCards);

            p1.sortAllCards();
            Collections.reverse(p1.getAllCards());
            p2.sortAllCards();
            Collections.reverse(p2.getAllCards());

            int min = p1Cards.size();
            if (p2Cards.size() < min) {
                min = p2Cards.size();
            } //finds the smallest list
            
            for (int i = 0; i < min; i++) {
                int val1 = Utility.getRankValue(p1Cards.get(i).getRank());
                int val2 = Utility.getRankValue(p2Cards.get(i).getRank());
            
                if (val1 > val2) return "Player 1 wins!";
                if (val2 > val1) return "Player 2 wins!";
            } //compares ranking value of the cards
            
            return "Tie!"; 
            //if the compared cards are equal, return tie
        }
    }

    public static void play() {
    }
}