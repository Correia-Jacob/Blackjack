package blackjack;

import java.util.ArrayList;
import java.util.*;
import static java.util.Map.entry;

public class Hand {
    int valueOfHand;
    private ArrayList<Card> hand;

    public Hand() {
    hand = new ArrayList<>();
    }
    
    public void addCard(Card card) {
        hand.add(card);
    }
        
    public void clear() {
        hand.clear();
    }

    public int countOfHand() {
        return hand.size();
    }
    
    // CARD FACE -> CARDS VALUE
    Map<String, Integer> cardsValue = Map.ofEntries( 
            entry("ACE", valueOfHand + 11 > 21 ? 1 : 11),
            entry("TWO", 2),
            entry("THREE", 3),
            entry("FOUR", 4),
            entry("FIVE", 5),
            entry("SIX", 6),
            entry("SEVEN", 7),
            entry("EIGHT", 8),
            entry("NINE", 9),
            entry("TEN", 10),
            entry("JACK", 10),
            entry("QUEEN", 10),
            entry("KING", 10)
    );

    public int valueOfHand() {
        for (int index = 0; index < countOfHand(); index++){
            valueOfHand += cardsValue.get(hand.get(index).getFace().toString());
        }
        return valueOfHand;
    }

}

