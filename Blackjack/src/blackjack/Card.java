package blackjack;

public class Card { 
    
    public Suit suit;

    public Face face;
    
    enum Face {TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE,TEN, JACK, QUEEN, KING, ACE}

    enum Suit {CLUBS, HEARTS, SPADES, DIAMONDS};
    
    Card(Card card) {
        face = card.face;
        suit = card.suit;
    }

    Card(Face face, Suit suit) {
        this.face = face;
        this.suit = suit;
    }
    
    public Face getFace() {
        return face;
    }

    public Suit getSuit() {
        return suit;
    }
            

}
