import java.util.ArrayList;
public class BlackjackGameLogic {

    public String whoWon(ArrayList<Card> playerHand1,ArrayList<Card> dealerHand) {
        int totalPlayer = handTotal(playerHand1);
        int totalDealer = handTotal(dealerHand);
        if (totalPlayer > 21) {
            return "dealer";
        }
        else if (totalDealer > 21) {
            return "player";
        }
        else if (totalDealer > totalPlayer) {
            return "dealer";
        }
        else if (totalPlayer > totalDealer) {
            return "player";
        }
        else {
            return "push";
        }
    }

    public int handTotal(ArrayList<Card> hand) {
        int total = 0;
        int aceCount = 0;

        for (Card card: hand) {
            if (card.getValue() == 1) {
                aceCount++;
                total += 11;
                continue;
            }
            if (card.getValue() > 10) {
                total += 10;
            } else {
                total += card.getValue();
            }
        }

        while (total > 21 && aceCount > 0) {
            total -= 10;
            aceCount--;
        }
        return total;
    }

    public boolean evaluateBankerDraw(ArrayList<Card> hand) {
        return handTotal(hand) <= 16;
    }
}
