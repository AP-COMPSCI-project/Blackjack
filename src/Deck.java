import java.util.*;

/**
Represents a standard deck of 52 cards (not jokers included). Includes methods to shuffle the
 deck, deal a card(s), and check the deck's size.
*/

public class Deck {
    private Deque<Card> deck;

    /**
    Creates a shuffled, 52 card deck.
    */
    public Deck() {
        reset();
        shuffle();
    }

    /**
    Refills the deck, but does not shuffle it.
    */
    public void reset() {
        deck = new ArrayDeque<>();
        addSuit(Suit.CLUBS);
        addSuit(Suit.DIAMONDS);
        addSuit(Suit.HEARTS);
        addSuit(Suit.SPADES);
    }

    private void addSuit(Suit suit) {
        for (Rank rank : Rank.values())
            deck.push(new Card(suit, rank));
    }

    public void shuffle() {
        List<Card> list = new ArrayList<>(deck);
        Collections.shuffle(list);
        deck.clear();
        for(Card card : list)
            deck.push(card);
    }

    /**
    Returns the top card of the deck, or null if the deck is empty.
    */
    public Card deal() {
        if(isEmpty()) return null;
        return deck.pop();
    }

    /**
    Returns an ArrayList containing count cards. If count is greater than the number of cards in the deck,
     it will return the entire deck. If count is zero or less, it will return an empty ArrayList.
    */
    public ArrayList<Card> deal(int count) {
        ArrayList<Card> output = new ArrayList<>();
        if(count > size()) count = size();
        for(int i = 0; i < count; i++)
            output.add(deck.pop());
        return output;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Card card : deck)
            sb.append(card + "\n");
        return sb.toString();
    }

    public boolean isEmpty() { return deck.isEmpty(); }
    public int size() { return deck.size(); }
}
