import java.util.ArrayList;
import java.util.Collections;

public class BlackjackDealer {
    private ArrayList<Card> deck;
    public BlackjackDealer() {
        generateDeck();
    }

    public void generateDeck() {
        String[] suits = {"H", "D", "C", "S"};
        int[] values = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 1};
        deck = new ArrayList<>();

        for (String suit : suits) {
            for (int value : values) {
                deck.add(new Card(suit, value));
            }
        }
    }

    public ArrayList<Card> dealHand() {
        ArrayList<Card> hand = new ArrayList<>();
        if (deck.size() >= 2) {
            hand.add(drawOne());
            hand.add(drawOne());
        }
        return hand;
    }

    public Card drawOne() {
        if (!deck.isEmpty()) {
            return deck.remove(0);
        }
        return null;
    }

    public void shuffleDeck() {
        Collections.shuffle(deck);
    }

    public int deckSize() {
        return deck.size();
    }
}
