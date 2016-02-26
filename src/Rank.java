/**
Represents the possible ranks of a playing card.
*/

public enum Rank {
    ACE(11), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10), JACK(10), QUEEN(10), KING(10);

    private int value;

    Rank(int value) { this.value = value; }

    public int getValue() { return value; }
    private void changeValue(int newValue) { value = newValue; }

    /**
    If the passed in rank is an ace, it will toggle its value from 1 to 11 and vice versa.
     It will do nothing if the value isn't an ace.
    */
    public void changeValue(Rank rank) {
        if(rank == ACE)
            if(rank.getValue() == 1)
                rank.changeValue(11);
            else
                rank.changeValue(1);
    }
}
