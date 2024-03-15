import java.util.ArrayList;

public class BlackjackGame {
    private ArrayList<Card> playerHand;
    private ArrayList<Card> bankerHand;
    private BlackjackDealer theDealer;
    private BlackjackGameLogic gameLogic;
    private double currentBet;
    private double totalWinnings;

    public BlackjackGame() {
        theDealer = new BlackjackDealer();
        gameLogic = new BlackjackGameLogic();
        playerHand = new ArrayList<>();
        bankerHand = new ArrayList<>();
        currentBet = 0;
        totalWinnings = 0;
        theDealer.shuffleDeck();
    }

    public void newRound(double bet) {
        currentBet = bet;
        playerHand.clear();
        bankerHand.clear();
        playerHand.addAll(theDealer.dealHand());
        bankerHand.addAll(theDealer.dealHand());
    }

    public void playerHits() {
        playerHand.add(theDealer.drawOne());
    }

    public void playerStays() {
        while (gameLogic.evaluateBankerDraw(bankerHand)) {
            bankerHand.add(theDealer.drawOne());
        }
    }

    public double evaluateWinnings() {
        String winner = gameLogic.whoWon(playerHand, bankerHand);
        if ("player".equals(winner)) {
            totalWinnings += currentBet * 2;
            return currentBet * 2;
        } else if ("dealer".equals(winner)) {
            totalWinnings -= currentBet;
            return 0;
        } else {
            return currentBet;
        }
    }
    public String isBlackjack() {
        if (gameLogic.handTotal(playerHand) == 21 && gameLogic.handTotal(bankerHand) == 21) {
            return "both";
        }
        else if (gameLogic.handTotal(playerHand) == 21) {
            return "player";
        }
        else if (gameLogic.handTotal(bankerHand) == 21){
            return "banker";
        }
        return "none";
    }

    public boolean isBusted(ArrayList<Card> hand) {
        return gameLogic.handTotal(hand) > 21;
    }

    public ArrayList<Card> getPlayerHand() {
        return playerHand;
    }

    public ArrayList<Card> getBankerHand() {
        return bankerHand;
    }

    public int getCardSum(ArrayList<Card> hand) {
        return gameLogic.handTotal(hand);
    }

    public void setTotalWinnings(double totalWinnings) {
        this.totalWinnings = totalWinnings;
    }
}
