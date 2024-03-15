public class Card {
    private String suit;
    private int value;

    Card(String theSuit, int theValue) {
        this.suit = theSuit;
        this.value = theValue;
    }

    int getValue() {
        return value;
    }
    String getSuit() {
        return suit;
    }

    @Override
    public String toString() {
        return getValue() + "-" + getSuit();
    }
}
