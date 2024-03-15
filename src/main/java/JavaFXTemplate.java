import javafx.animation.PauseTransition;
import javafx.application.Application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.InputStream;
import java.util.List;

public class JavaFXTemplate extends Application {
	private BlackjackGame blackjackGame;
	private Stage primaryStage;
	private double totalMoney = 0;
	private double betAmount = 0;
	private Label currentBet;
	private Label playerCardSumLabel;
	private Label bankerCardSumLabel;
	private int playerCardCount = 0;
	private int bankerCardCount = 0;

	public static void main(String[] args) { launch(args); }

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		welcomePage();
	}
	private void welcomePage(){
		// Resetting total money for new game
		this.totalMoney = 0;
		primaryStage.setTitle("Blackjack Game");

		// First line of text
		Label welcomeLabel = new Label("Welcome to Blackjack!");
		welcomeLabel.setStyle("-fx-font-family: 'Lucida Calligraphy'; -fx-font-size: 40; -fx-font-weight: bold; -fx-text-fill: white;");

		// Second line of text
		Label promptLabel = new Label("Enter The Money You'd Like to Spend:");
		promptLabel.setStyle("-fx-font-family: Stencil; -fx-font-size: 20; -fx-text-fill: white;");

		// Text field to input starting money
		TextField betAmountText = new TextField();
		betAmountText.setPromptText("More Risk, More Reward!!");
		betAmountText.setStyle("-fx-text-fill: black; -fx-font-size: 16; -fx-background-radius: 10;");
		betAmountText.setPrefWidth(50);
		betAmountText.setAlignment(Pos.CENTER);

		// Event handler for start button
		Button startButton = new Button("Let's Go!");
		startButton.setStyle("-fx-font-size: 16px; -fx-padding: 10px 20px; -fx-background-radius: 20px;");

		// Event handler for start button
		startButton.setOnAction(e -> {
			String inputVal = betAmountText.getText();
			try {
				double betAmount = Double.parseDouble(inputVal);
				if (betAmount <= 0) {
					throw new Exception();
				} else {
					this.totalMoney = betAmount;
					gamePlay();
				}
			} catch (Exception ex) {
				betAmountText.setText("Invalid input, please enter a positive number.");
			}
		});

		// VBox for all elements on screen
		VBox welcomeBox = new VBox(20, welcomeLabel, promptLabel, betAmountText, startButton);
		welcomeBox.setAlignment(Pos.CENTER);

		// Pane for VBox of all elements on screen
		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(70));
		pane.setCenter(welcomeBox);
		pane.setStyle("-fx-background-color: green;");

		// Setting scene
		Scene display = new Scene(pane, 700, 700);
		primaryStage.setScene(display);
		primaryStage.show();
	}

	private void gamePlay(){
		// Resetting all variables for new round
		this.betAmount = 0;
		this.bankerCardCount = 0;
		this.playerCardCount = 0;

		// Label for the total money
		Label totalMoney = new Label("Total Money\n$" + this.totalMoney);
		totalMoney.setStyle("-fx-font-family: 'serif'; -fx-font-size: 18; -fx-background-color: white; -fx-alignment: center;");

		GridPane totalMoneyPane = getTotalMoneyPane(totalMoney);

		// Bet buttons
		Button dol1 = styleBetButton(new Button("$1"));
		dol1.setStyle(dol1.getStyle() + "-fx-background-color: white ");
		Button dol5 = styleBetButton(new Button("$5"));
		dol5.setStyle(dol5.getStyle() + "-fx-background-color: red");
		Button dol25 = styleBetButton(new Button("$25"));
		dol25.setStyle(dol25.getStyle() + "-fx-background-color: #77ff77");
		Button dol100 = styleBetButton(new Button("$100"));
		dol100.setStyle(dol100.getStyle() + "-fx-background-color: black");
		Button dol250 = styleBetButton(new Button("$250"));
		dol250.setStyle(dol250.getStyle() + "-fx-background-color: purple");

		// Event handlers for bet buttons
		dol1.setOnAction(e->updateBet(1));
		dol5.setOnAction(e->updateBet(5));
		dol25.setOnAction(e->updateBet(25));
		dol100.setOnAction(e->updateBet(100));
		dol250.setOnAction(e->updateBet(250));

		// Text Field for custom bet
		TextField customBet = new TextField();
		customBet.setPromptText("$0");
		customBet.setPrefWidth(100);
		customBet.setStyle("-fx-background-radius: 15; -fx-border-radius: 15; -fx-text-fill: black; -fx-font-size: 16;");

		// Label for custom bet
		Label customBetLabel = new Label("Custom: $");
		customBetLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: white; -fx-font-size: 18;");

		// Event handler for custom bet
		customBet.setOnAction(e->{
			try{
				String customBetText = customBet.getText();
				if (customBetText.isEmpty()) {
					currentBet.setText("");
					throw new Exception();
				}

				double inputBet = Double.parseDouble(customBet.getText());
				if (inputBet <= 0) {
					currentBet.setText("");
					throw new Exception();
				}
				else if (inputBet > this.totalMoney) {
					currentBet.setText("You are a Brokie! ");
					throw new Exception();
				}
				else {
					this.betAmount = inputBet;
					currentBet.setText("Bet: $" + this.betAmount);
				}
			}
			catch(Exception ex){
				currentBet.setText(currentBet.getText() + "Enter a valid number.");
			}
		});

		// HBox for custom bet text field & label
		HBox customBetBox = new HBox(10, customBetLabel, customBet);
		customBetBox.setAlignment(Pos.CENTER);

		// HBox for custom bet HBox and default bet buttons
		HBox betOptionsBox  = new HBox(10, dol1, dol5, dol25, dol100, dol250, customBetBox);
		betOptionsBox.setAlignment(Pos.CENTER);

		// Confirm button
		Button confirmBet = new Button("Confirm");
		confirmBet.setStyle("-fx-background-color: #ffffff;");

		// Label for the current bet
		currentBet = new Label("Bet: $" + this.betAmount);
		currentBet.setStyle("-fx-font-family: 'serif'; -fx-font-size: 18; -fx-text-fill: black; -fx-alignment: center;");

		// VBox for the current bet
		VBox currentBetBox = new VBox(currentBet);
		currentBetBox.setStyle("-fx-padding: 20; -fx-background-color: white; -fx-border-color: black; -fx-border-width: 2; -fx-background-radius: 10; -fx-border-radius: 10;");

		// Pane for the current bet VBox
		GridPane betPane = new GridPane();
		betPane.setAlignment(Pos.CENTER);
		betPane.setHgap(10);
		betPane.add(currentBetBox, 0, 0);

		// VBox for confirm button & current bet pane
		VBox betConfirmationBox = new VBox(10, confirmBet, betPane);
		betConfirmationBox.setAlignment(Pos.CENTER);

		// Outside layer of all bet options HBox
		StackPane betButtonsContainer = new StackPane(betOptionsBox);
		betButtonsContainer.setStyle("-fx-background-radius: 15; -fx-background-color: #363636; -fx-padding: 10;");
		betOptionsBox.setPadding(new Insets(10));

		// Setting up main pane
		BorderPane gamePane = new BorderPane();
		gamePane.setPadding(new Insets(20));
		gamePane.setStyle("-fx-background-color: green;");
		gamePane.setTop(totalMoneyPane);
		gamePane.setCenter(betConfirmationBox);
		gamePane.setBottom(betButtonsContainer);

		// Event handler for confirming bet
		confirmBet.setOnAction(e->{
			try{
				if(betAmount <= this.totalMoney && betAmount != 0){
					this.totalMoney -= betAmount;
					gameZone();
				}
				else if(betAmount == 0){
					throw new Exception("Invalid Input!");
				}
			}
			catch(Exception ex){
				currentBet.setText("Bet Must Be Greater Than Zero");
			}
		});

		// Setting scene
		Scene gameScene = new Scene(gamePane, 700, 700);
		primaryStage.setScene(gameScene);
		primaryStage.show();
	}

	/**
	 * Constructs a GridPane containing the Label of the total money.
	 *
	 * @param totalMoney The Label displaying the total money.
	 * @return The constructed GridPane.
	 */
	private static GridPane getTotalMoneyPane(Label totalMoney) {
		// VBox for the total money label
		VBox totalMoneyBox = new VBox(totalMoney);
		totalMoneyBox.setStyle("-fx-padding: 20; -fx-background-color: white; -fx-border-color: black; -fx-border-width: 2; -fx-background-radius: 10; -fx-border-radius: 10;");

		// GridPane for the VBox of total money
		GridPane totalMoneyPane = new GridPane();
		totalMoneyPane.setAlignment(Pos.TOP_LEFT);
		totalMoneyPane.setHgap(10);
		totalMoneyPane.add(totalMoneyBox, 0, 0);

		return totalMoneyPane;
	}

	private void gameZone(){
		// Label for the total money
		Label totalMoney = new Label("Total Money\n$" + this.totalMoney);
		totalMoney.setStyle("-fx-font-family: 'serif'; -fx-font-size: 18; -fx-background-color: white; -fx-alignment: center;");

		// VBox for the total money label
		GridPane totalMoneyPane = getTotalMoneyPane(totalMoney);

		// Label for the current bet
		currentBet = new Label("Bet: $" + this.betAmount);
		currentBet.setStyle("-fx-font-family: 'serif'; -fx-font-size: 18;");

		// VBox for the current bet label
		VBox currentBetBox = new VBox(currentBet);
		currentBetBox.setStyle("-fx-padding: 20; -fx-background-color: white; -fx-border-color: black; -fx-border-width: 2; -fx-background-radius: 10; -fx-border-radius: 10;");

		// Pane for the current bet VBox
		GridPane betPane = new GridPane();
		betPane.setAlignment(Pos.CENTER);
		betPane.setHgap(10);
		betPane.add(currentBetBox, 0, 0);

		// Circle & label for player card sum
		Circle playerCardSumCircle = new Circle(15);
		playerCardSumCircle.setFill(Color.WHITE);
		playerCardSumLabel = new Label("");

		// Pane for player card sum label & circle
		StackPane playerCardSumPane = new StackPane();
		playerCardSumPane.getChildren().addAll(playerCardSumCircle, playerCardSumLabel);
		playerCardSumPane.setAlignment(Pos.CENTER);

		// Circle & label for banker card sum
		Circle bankerCardSumCircle = new Circle(15);
		bankerCardSumCircle.setFill(Color.WHITE);
		bankerCardSumLabel = new Label("");

		// Pane for banker card sum & circle
		StackPane bankerCardSumPane = new StackPane();
		bankerCardSumPane.getChildren().addAll(bankerCardSumCircle, bankerCardSumLabel);
		bankerCardSumPane.setAlignment(Pos.CENTER);

		// HBox for player & banker hand
		HBox playerHand = new HBox(5);
		playerHand.setAlignment(Pos.CENTER);
		HBox bankerHand = new HBox(5);
		bankerHand.setAlignment(Pos.CENTER);

		// VBox for player & banker hand and card sum pane
		VBox playerHandBox = new VBox(10, playerCardSumPane, playerHand);
		playerHandBox.setAlignment(Pos.CENTER);
		playerHandBox.setPadding(new Insets(45, 0, 0, 0));
		VBox bankerHandBox = new VBox(10, bankerHand, bankerCardSumPane);
		bankerHandBox.setAlignment(Pos.CENTER);
		bankerHandBox.setPadding(new Insets(0, 0, 45, 0));

		// VBox for both hand box and current bet pane
		VBox centerPane = new VBox(10, bankerHandBox, betPane, playerHandBox);
		centerPane.setAlignment(Pos.CENTER);

		// Hit and stay buttons
		Button hitButton = new Button("Hit");
		Button stayButton = new Button("Stay");
		hitButton.setStyle("-fx-font-size: 16px; -fx-padding: 20px 140px; -fx-background-radius: 20px;");
		stayButton.setStyle("-fx-font-size: 16px; -fx-padding: 20px 140px; -fx-background-radius: 20px;");

		// HBox for the control buttons
		HBox controlButtons = new HBox(5, hitButton, stayButton);
		controlButtons.setAlignment(Pos.CENTER);
		controlButtons.setPadding(new Insets(15, 12, 15, 12));

		// Pane for the control buttons HBox
		StackPane controlButtonsContainer = new StackPane(controlButtons);
		controlButtonsContainer.setStyle("-fx-background-radius: 15; -fx-background-color: #363636; -fx-padding: 10;");
		controlButtons.setPadding(new Insets(10));

		// Setting up instance of game
		blackjackGame = new BlackjackGame();
		blackjackGame.setTotalWinnings(this.totalMoney);
		blackjackGame.newRound(this.betAmount);
		hitButton.setDisable(true);
		stayButton.setDisable(true);
		updateCards(playerHand, bankerHand, true, hitButton, stayButton);
		String checkBlackjack = blackjackGame.isBlackjack();

		// Check Blackjack Condition
		if (!checkBlackjack.equals("none")) {
			hitButton.setDisable(true);
			stayButton.setDisable(true);

			// Pausing for cards image to load
			PauseTransition pause = new PauseTransition(Duration.seconds(4.5));
			pause.setOnFinished(event -> {
				updateBlackjackCards(bankerHand);
				// Win Condition
				if (checkBlackjack.equals("player")) {
					this.totalMoney += blackjackGame.evaluateWinnings() * 2.5;
					currentBet.setText("BLACKJACK");
					currentBetBox.setStyle(currentBetBox.getStyle() + "-fx-background-color: #ffae00");
					totalMoney.setText("Total Money\n$" + this.totalMoney);
					makeNewRoundButton(controlButtonsContainer);
				}
				// Lose & Draw Condition
				else {
					handleRoundEnd(currentBetBox, totalMoney, controlButtonsContainer);
				}
			});
			pause.play();
		}

		// Event handler for hit button
		hitButton.setOnAction(e->{
			blackjackGame.playerHits();
			hitButton.setDisable(true);
			stayButton.setDisable(true);
			// Player Busted -> Lose Condition
			if (blackjackGame.isBusted(blackjackGame.getPlayerHand())) {
				handleGamePlay(totalMoney, currentBetBox, playerHand, bankerHand, hitButton, stayButton, controlButtonsContainer);
			}
			// Player reaches 21 -> Win or Draw Condition
			else if (blackjackGame.getCardSum(blackjackGame.getPlayerHand()) == 21) {
				blackjackGame.playerStays();
				handleGamePlay(totalMoney, currentBetBox, playerHand, bankerHand, hitButton, stayButton, controlButtonsContainer);
				// Player still valid to keep playing
			} else {
				updateCards(playerHand, bankerHand, true, hitButton, stayButton);
			}
		});

		// Event handler for stay button
		stayButton.setOnAction(e->{
			blackjackGame.playerStays();
			hitButton.setDisable(true);
			stayButton.setDisable(true);
			handleGamePlay(totalMoney, currentBetBox, playerHand, bankerHand, hitButton, stayButton, controlButtonsContainer);
		});

		// Setting up main pane
		BorderPane gamePane = new BorderPane();
		gamePane.setPadding(new Insets(20));
		gamePane.setStyle("-fx-background-color: green;");
		gamePane.setTop(totalMoneyPane);
		gamePane.setCenter(centerPane);
		gamePane.setBottom(controlButtonsContainer);

		// Setting up scene
		Scene gameScene = new Scene(gamePane, 700, 700);
		primaryStage.setScene(gameScene);
		primaryStage.show();
	}

	private void gameOver() {
		primaryStage.setTitle("Blackjack Game");

		// Creation and event handler of reset button
		Button resetButton = new Button("Try Again");
		resetButton.setStyle("-fx-font: 15 'serif'; -fx-background-color: white; -fx-padding: 10px 20px; -fx-background-radius: 20px;");
		resetButton.setOnAction(e -> welcomePage());

		// Game Over text
		Label gameOver = new Label("Game Over!");
		gameOver.setStyle("-fx-font: 40 'serif'; -fx-text-fill: white; -fx-font-weight: bold;-fx-font-family: 'Lucida Calligraphy'");

		// VBox for reset button and game over text
		VBox gameOverBox = new VBox(10, gameOver, resetButton);
		gameOverBox.setPadding(new Insets(10));
		gameOverBox.setSpacing(30);
		gameOverBox.setStyle("-fx-alignment: center;");

		// Rectangle to store all elements
		Rectangle gameOverRec = new Rectangle(400, 200);
		gameOverRec.setFill(Color.DARKRED);
		gameOverRec.setStroke(Color.BLACK);
		gameOverRec.setStrokeWidth(2);
		gameOverRec.setArcWidth(30);
		gameOverRec.setArcHeight(30);

		// Setting up main pane with rectangle
		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(70));
		pane.setStyle("-fx-background-color: green;");
		pane.setCenter(gameOverRec);

		// Pane for rectangle pane and game over VBox
		StackPane stackPane = new StackPane();
		stackPane.getChildren().addAll(pane,gameOverBox);
		StackPane.setAlignment(gameOverBox, javafx.geometry.Pos.BOTTOM_CENTER);
		StackPane.setMargin(gameOverBox, new Insets(0, 0, 20, 0));

		// Setting up scene
		Scene display = new Scene(stackPane, 700, 700);
		primaryStage.setScene(display);
		primaryStage.show();
	}

	/**
	 * Updating the cards, waiting for card images to load, and handling the end of the round.
	 *
	 * @param totalMoney             The Label displaying the total money.
	 * @param currentBetBox          The VBox container for the current bet display.
	 * @param playerHand             The HBox container of the player's hand.
	 * @param bankerHand             The HBox container of the banker's hand.
	 * @param hitButton              The Button for hitting.
	 * @param stayButton             The Button for staying.
	 * @param controlButtonsContainer  The StackPane container for control buttons.
	 */
	private void handleGamePlay(Label totalMoney, VBox currentBetBox, HBox playerHand, HBox bankerHand, Button hitButton, Button stayButton, StackPane controlButtonsContainer) {
		// Update the cards for player and banker hands
		updateCards(playerHand, bankerHand, false, hitButton, stayButton);

		// Pause to allow card images to load
		PauseTransition pause = new PauseTransition(Duration.seconds(2.5));
		pause.setOnFinished(event -> {
			// Handle end of round after card images have loaded
			handleRoundEnd(currentBetBox, totalMoney, controlButtonsContainer);

			// Check if total money has become zero or less, indicating game over
			if (this.totalMoney <= 0) {
				gameOver();
			}
		});
		pause.play();
	}

	/**
	 * Creates New Round button labeled, adds it to the provided StackPane container, replacing the other buttons.
	 *
	 * @param controlButtonsContainer The StackPane container where the button is added.
	 */
	private void makeNewRoundButton(StackPane controlButtonsContainer) {
		// Clear buttons from controlButtonsContainer
		controlButtonsContainer.getChildren().clear();

		// Create NewRoundButton, apply style, and add to controlButtonsContainer
		Button newRoundButton = new Button("New Round");
		newRoundButton.setStyle("-fx-font-size: 16px; -fx-padding: 30px 200px; -fx-background-radius: 20px;");
		controlButtonsContainer.getChildren().add(newRoundButton);

		// Set event handler for button to switch to gamePlay pane when clicked
		newRoundButton.setOnAction(e->{
			gamePlay();
		});
	}

	/**
	 * Handles end of round by updating UI elements and displaying outcome.
	 *
	 * @param currentBetBox            The VBox containing the current bet display.
	 * @param totalMoneyLabel          The Label displaying the total money.
	 * @param controlButtonsContainer  The StackPane container for control buttons.
	 */
	private void handleRoundEnd(VBox currentBetBox, Label totalMoneyLabel, StackPane controlButtonsContainer) {
		// Increase total money by amount won or lost in the round
		this.totalMoney += blackjackGame.evaluateWinnings();

		// Get the sums of cards in the banker's and player's hands
		int bankerSum = blackjackGame.getCardSum(blackjackGame.getBankerHand());
		int playerSum = blackjackGame.getCardSum(blackjackGame.getPlayerHand());

		// Check if banker is busted or has lower sum than player, and player is not busted
		if ((blackjackGame.isBusted(blackjackGame.getBankerHand()) || bankerSum < playerSum) && !blackjackGame.isBusted(blackjackGame.getPlayerHand())) {
			// Player wins
			currentBet.setText("You Win");
			currentBetBox.setStyle(currentBetBox.getStyle() + "-fx-background-color: #249400;");
		} else if (bankerSum > playerSum || blackjackGame.isBusted(blackjackGame.getPlayerHand())) {
			// Banker wins
			currentBet.setText("You Lose");
			currentBet.setStyle(currentBet.getStyle() + "-fx-text-fill: white;");
			currentBetBox.setStyle(currentBetBox.getStyle() + "-fx-background-color: #700101;");
		} else {
			// Draw
			currentBet.setText("Draw");
			currentBetBox.setStyle(currentBetBox.getStyle() + "-fx-background-color: #ffe900;");
		}
		// Update total money label with new total money amount
		totalMoneyLabel.setText("Total Money\n$" + this.totalMoney);

		// Add new round button to the control buttons container
		makeNewRoundButton(controlButtonsContainer);
	}

	/**
	 * Updates UI elements of the banker's hand in the event of a Blackjack.
	 *
	 * @param bankerHand The HBox container of banker's hand.
	 */
	private void updateBlackjackCards(HBox bankerHand) {
		// Get the image for the first card in banker's hand
		List<Card> bankerCards = blackjackGame.getBankerHand();
		ImageView cardView = getImageView("/cards/" + bankerCards.get(0).toString() + ".png");

		// Remove the back card image, then add the first card image to the HBox container
		bankerHand.getChildren().remove(0);
		bankerHand.getChildren().add(0, cardView);

		// Update label displaying sum of banker's cards
		bankerCardSumLabel.setText(String.valueOf(blackjackGame.getCardSum(blackjackGame.getBankerHand())));
	}

	/**
	 * Updates cards displayed in the player's and banker's hands
	 *
	 * @param playerHand    The HBox container of the player's hand.
	 * @param bankerHand    The HBox container of the banker's hand.
	 * @param hideFirstCard Boolean indicating whether to hide the first card in the banker's hand.
	 * @param hitButton     The Button for hitting.
	 * @param stayButton    The Button for staying.
	 */
	private void updateCards(HBox playerHand, HBox bankerHand, boolean hideFirstCard, Button hitButton, Button stayButton) {
		List<Card> playerCards = blackjackGame.getPlayerHand();
		List<Card> bankerCards = blackjackGame.getBankerHand();
		int pauseTimer = 0;

		// Display player's cards
		for (int i = playerCardCount; i < playerCards.size(); i++) {
			int currIndex = i;

			Card card = playerCards.get(i);
			PauseTransition pause = new PauseTransition(Duration.seconds(pauseTimer + 0.25));
			pause.setOnFinished(event -> {
				ImageView cardView = getImageView("/cards/" + card.toString() + ".png");
				playerHand.getChildren().add(cardView);
				if (currIndex == playerCards.size() - 1) {
					playerCardSumLabel.setText(String.valueOf(blackjackGame.getCardSum(blackjackGame.getPlayerHand())));
				}
				if (hideFirstCard) {
					// Enable hit and stay buttons after displaying player's cards
					hitButton.setDisable(false);
					stayButton.setDisable(false);
				}
			});
			pause.play();
			pauseTimer++;
			playerCardCount = i + 1;
		}

		// Display banker's first card if the player can't hit anymore
		if (!hideFirstCard) {
			PauseTransition pause = new PauseTransition(Duration.seconds(pauseTimer + 0.1));
			pause.setOnFinished(event -> {
				ImageView cardView = getImageView("/cards/" + bankerCards.get(0).toString() + ".png");
				bankerHand.getChildren().remove(0);
				bankerHand.getChildren().add(0, cardView);

				if (bankerCardCount == bankerCards.size()) {
					bankerCardSumLabel.setText(String.valueOf(blackjackGame.getCardSum(blackjackGame.getBankerHand())));
				}
			});
			pause.play();
		}

		for (int i = bankerCardCount; i < bankerCards.size(); i++) {
			int currIndex = i;
			PauseTransition pause = new PauseTransition(Duration.seconds(pauseTimer + 0.55));
			pause.setOnFinished(event -> {
				String imagePath;
				if (currIndex == 0 && hideFirstCard) {
					// If first banker card is hidden, display a back image
					imagePath = "/cards/B.png";
				} else {
					imagePath = "/cards/" + bankerCards.get(currIndex).toString() + ".png";
				}
				ImageView cardView = getImageView(imagePath);
				bankerHand.getChildren().add(cardView);
				if (currIndex == bankerCards.size() - 1 && !hideFirstCard) {
					bankerCardSumLabel.setText(String.valueOf(blackjackGame.getCardSum(blackjackGame.getBankerHand())));
				}
			});
			pause.play();
			pauseTimer++;
			bankerCardCount = i + 1;
		}
	}

	/**
	 * Constructs ImageView object of image located at the specified imagePath.
	 *
	 * @param imagePath The path to the image resource.
	 * @return ImageView object of the image.
	 */
	private ImageView getImageView(String imagePath) {
		// Create an InputStream for the image located at given imagePath
		InputStream is = getClass().getResourceAsStream(imagePath);

		// Create new ImageView object of the image, then modify the height and width
		ImageView cardView = new ImageView(new Image(is));
		cardView.setFitHeight(100);
		cardView.setFitWidth(70);

		return cardView;
	}

	/**
	 * Applies styling to the Button to create a circular button.
	 *
	 * @param button The Button.
	 * @return The finished circular Button.
	 */
	private Button styleBetButton(Button button) {
		final int buttonSize = 60;

		// Apply CSS styling to the button and modify preferred width and height
		button.setStyle("-fx-font-weight: bold; -fx-text-fx-background-radius: 30; -fx-font-size: 14; -fx-background-insets: 0; -fx-padding: 0; -fx-text-fill: #ffae00;");
		button.setPrefHeight(buttonSize);
		button.setPrefWidth(buttonSize);

		// Create a circular shape then set the button shape to be circular with extra padding
		Circle circleShape = new Circle(buttonSize / 2);
		button.setShape(circleShape);
		button.setPadding(new Insets(buttonSize / 4));

		return button;
	}

	/**
	 * Calculates new bet amount with the given amount, and updates current bet amount
	 * if the sum does not exceed the total money.
	 *
	 * @param sum The amount to add to the current bet.
	 */
	private void updateBet(int sum){
		// Check if adding the specified sum to the current bet exceeds the total money
		if(this.betAmount + sum <= this.totalMoney){
			// Update new bet amount & the display to show the new bet amount
			this.betAmount += sum;
			currentBet.setText("Bet: $"+this.betAmount);
		}
		else{
			// Set text to current bet to indicate that bet amount exceeds the total money
			currentBet.setText("You are a Brokie! Enter a Valid Bet.");
		}
	}
}