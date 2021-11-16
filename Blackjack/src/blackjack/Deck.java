package blackjack;

import java.util.*;

public class Deck {
    
 private final ArrayList<Card> deck;
 private final Random random;

 public Deck() {
 deck = new ArrayList<>(52);

  for (Card.Face face : Card.Face.values()) {
    for (Card.Suit suit : Card.Suit.values()) {
        deck.add(new Card(face, suit));
        }
   }
     random = new Random();
    }

    public Card getRandomCard() {            
        int randomIndex = random.nextInt(deck.size());
        return deck.remove(randomIndex);
    }

}
