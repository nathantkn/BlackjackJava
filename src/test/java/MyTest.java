import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


import java.util.ArrayList;

class MyTest {

	@Test
	void testGenerateDeckandSize(){
		BlackjackDealer myDealer = new BlackjackDealer();
		myDealer.generateDeck();
		assertEquals(myDealer.deckSize(), 52);
	}

	@Test
	void testDrawOneSmall(){
		BlackjackDealer myDealer = new BlackjackDealer();
		Card myCard = myDealer.drawOne();
		assertEquals(myCard.getValue(), 2);
		assertEquals(myDealer.deckSize(), 51);
	}

	@Test
	void testDrawOneMultiple(){
		BlackjackDealer myDealer = new BlackjackDealer();
		Card myCard = myDealer.drawOne();
		myCard = myDealer.drawOne();
		myCard = myDealer.drawOne();
		myCard = myDealer.drawOne();
		myCard = myDealer.drawOne();
		myCard = myDealer.drawOne();
		assertEquals(myCard.getValue(), 7);
		assertEquals(myDealer.deckSize(), 46);
	}

	@Test
	void testDealHandEmpty(){
		BlackjackDealer myDealer = new BlackjackDealer();
		ArrayList<Card> test = new ArrayList<>();myDealer.dealHand();
		assertEquals(test.size(), 0);
	}

	@Test
	void testDealHand(){
		BlackjackDealer myDealer = new BlackjackDealer();
		myDealer.generateDeck();
		ArrayList<Card> test = myDealer.dealHand();
		assertEquals(test.size(),2);
		assertEquals(myDealer.deckSize(), 50);
	}


	@Test
	void testwhoWonPlayer1(){
		BlackjackGameLogic myGame = new BlackjackGameLogic();
		ArrayList<Card> player = new ArrayList<>();
		ArrayList<Card> dealer = new ArrayList<>();
		player.add(new Card("H", 4));
		player.add(new Card("C", 4));
		player.add(new Card("D", 4));

		dealer.add(new Card("H", 4));

		assertEquals(myGame.whoWon(player, dealer), "player");
	}

	@Test
	void testwhoWonPlayer2(){
		BlackjackGameLogic myGame = new BlackjackGameLogic();
		ArrayList<Card> player = new ArrayList<>();
		ArrayList<Card> dealer = new ArrayList<>();
		dealer.add(new Card("H", 5));
		dealer.add(new Card("C", 8));
		dealer.add(new Card("D", 9));

		player.add(new Card("D", 2));
		player.add(new Card("C", 4));

		assertEquals(myGame.whoWon(player, dealer), "player");
	}

	@Test
	void testwhoWonPlayer3(){
		BlackjackGameLogic myGame = new BlackjackGameLogic();
		ArrayList<Card> player = new ArrayList<>();
		ArrayList<Card> dealer = new ArrayList<>();

		player.add(new Card("H", 4));
		player.add(new Card("C", 8));
		player.add(new Card("D", 9));

		dealer.add(new Card("D", 2));
		dealer.add(new Card("H", 9));
		dealer.add(new Card("C", 8));
		assertEquals(myGame.whoWon(player, dealer), "player");
	}

	@Test
	void testwhoWonPush(){
		BlackjackGameLogic myGame = new BlackjackGameLogic();
		ArrayList<Card> player = new ArrayList<>();
		ArrayList<Card> dealer = new ArrayList<>();

		player.add(new Card("H", 2));
		player.add(new Card("C", 9));
		player.add(new Card("D", 7));

		dealer.add(new Card("D", 2));
		dealer.add(new Card("H", 9));
		dealer.add(new Card("C", 7));
		assertEquals(myGame.whoWon(player, dealer), "push");
	}

	@Test
	void testWhoWonDealer1(){
		BlackjackGameLogic myGame = new BlackjackGameLogic();
		ArrayList<Card> player = new ArrayList<>();
		ArrayList<Card> dealer = new ArrayList<>();
		player.add(new Card("H", 4));
		player.add(new Card("H",5));

		dealer.add(new Card("H", 4));
		dealer.add(new Card("C", 4));
		dealer.add(new Card("D", 4));
		assertEquals(myGame.whoWon(player, dealer), "dealer");
	}

	@Test
	void testwhoWonDealer2(){
		BlackjackGameLogic myGame = new BlackjackGameLogic();
		ArrayList<Card> player = new ArrayList<>();
		ArrayList<Card> dealer = new ArrayList<>();
		player.add(new Card("H", 5));
		player.add(new Card("C", 8));
		player.add(new Card("D", 9));

		dealer.add(new Card("D", 2));
		dealer.add(new Card("C", 4));

		assertEquals(myGame.whoWon(player, dealer), "dealer");
	}

	@Test
	void testwhoWonDealer3(){
		BlackjackGameLogic myGame = new BlackjackGameLogic();
		ArrayList<Card> player = new ArrayList<>();
		ArrayList<Card> dealer = new ArrayList<>();

		dealer.add(new Card("H", 4));
		dealer.add(new Card("C", 8));
		dealer.add(new Card("D", 9));

		player.add(new Card("D", 2));
		player.add(new Card("H", 9));
		player.add(new Card("C", 8));
		assertEquals(myGame.whoWon(player, dealer), "dealer");
	}

	@Test
	void testHandTotalEmpty(){
		BlackjackGameLogic myGame = new BlackjackGameLogic();
		ArrayList<Card> player = new ArrayList<>();
		assertEquals(myGame.handTotal(player), 0);
	}

	@Test
	void testHandTotalSmall(){
		BlackjackGameLogic myGame = new BlackjackGameLogic();
		ArrayList<Card> player = new ArrayList<>();
		player.add(new Card("D", 2));
		player.add(new Card("H", 3));
		player.add(new Card("C", 4));
		assertEquals(myGame.handTotal(player), 9);
	}

	@Test
	void testHandTotalLarge(){
		BlackjackGameLogic myGame = new BlackjackGameLogic();
		ArrayList<Card> player = new ArrayList<>();

		player.add(new Card("D", 2));
		player.add(new Card("H", 3));
		player.add(new Card("C", 4));
		player.add(new Card("D", 5));
		player.add(new Card("H", 6));
		player.add(new Card("C", 7));
		player.add(new Card("D", 8));
		player.add(new Card("H", 9));
		player.add(new Card("C", 10));
		player.add(new Card("C", 11));
		player.add(new Card("C", 12));
		player.add(new Card("C", 13));
		assertEquals(myGame.handTotal(player), 84);
	}

	@Test
	void testHandTotalAces(){
		BlackjackGameLogic myGame = new BlackjackGameLogic();
		ArrayList<Card> player = new ArrayList<>();

		player.add(new Card("D", 1));
		player.add(new Card("H", 1));
		player.add(new Card("C", 1));
		player.add(new Card("D", 1));
		player.add(new Card("H", 1));
		player.add(new Card("C", 1));
		assertEquals(myGame.handTotal(player), 16);
	}

	@Test
	void testHandTotalDrawTrue(){
		BlackjackGameLogic myGame = new BlackjackGameLogic();
		ArrayList<Card> player = new ArrayList<>();

		player.add(new Card("D", 4));
		player.add(new Card("H", 4));
		player.add(new Card("C", 4));
		player.add(new Card("D", 4));
		assertTrue(myGame.evaluateBankerDraw(player));
	}

	@Test
	void testHandTotalDrawFalse(){
		BlackjackGameLogic myGame = new BlackjackGameLogic();
		ArrayList<Card> player = new ArrayList<>();

		player.add(new Card("D", 2));
		player.add(new Card("H", 10));
		player.add(new Card("C", 11));
		player.add(new Card("D", 1));
		assertFalse(myGame.evaluateBankerDraw(player));
	}

	@Test
	void testShuffleDeck(){
		BlackjackDealer myDealer1 = new BlackjackDealer();
		BlackjackDealer myDealer2 = new BlackjackDealer();
		myDealer1.generateDeck();
		myDealer2.generateDeck();
		myDealer2.shuffleDeck();

		assertNotEquals(myDealer1.drawOne(), myDealer2.drawOne());
	}

}