/**
Represents a single playing card. Has a suit, rank, and status (face up or face down).
*/

public class Card implements Comparable<Card> {
    private final Suit suit;
    private final Rank rank;
    private boolean faceUp;

    /**
    Basic constructor for the Card class. Sets up the card as face down by default.
    */
    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
        faceUp = false;
    }

    /**
    Allows you to chose whether or not the card starts face up or down.
    */
    public Card(Suit suit, Rank rank, boolean isFaceUp) {
        this.suit = suit;
        this.rank = rank;
        faceUp = isFaceUp;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Card)) return false;
        return compareTo((Card)obj) == 0;
    }

    @Override
    public int compareTo(Card card) { return rank.compareTo(card.rank); }

    @Override
    public String toString() { return rank + " of " + suit; }

    public Suit getSuit() { return suit; }
    public Rank getRank() { return rank; }
    public boolean isFaceUp() { return faceUp; }
    public void flip() { faceUp = !faceUp; }
    public boolean isRed() { return suit == Suit.HEARTS || suit == Suit.DIAMONDS; }
    public boolean isBalck() { return !isRed(); }
}
