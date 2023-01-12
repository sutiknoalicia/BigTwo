import java.util.ArrayList;

/**
 * The BigTwo class implements the CardGame interface and is used to model a Big
 * Two card game.
 * 
 * <br>
 * It has private instance variables for storing the number of players, a deck
 * of cards, a list of players, a list of hands played on the table, an index of
 * the current player, and a user interface.
 * 
 * @author Alicia Sutikno
 */
public class BigTwo implements CardGame{

	// instance variables
	private int numOfPlayers;
	private Deck deck;
	private ArrayList<CardGamePlayer> playerList;
	private ArrayList<Hand> handsOnTable;
	private int currentPlayerIdx;
	private BigTwoGUI ui;

	// constructor
	/**
	 * A constructor for creating a Big Two card game. Creates 4 players and adds
	 * them to the player list; and creates a BigTwoUI object for providing the user
	 * interface.
	 */
	public BigTwo() {
		playerList = new ArrayList<CardGamePlayer>();
		this.numOfPlayers = 4;
		for (int i = 0; i < this.numOfPlayers; i++) {
			CardGamePlayer player = new CardGamePlayer();
			playerList.add(player);
		}
		handsOnTable = new ArrayList<Hand>();
		ui = new BigTwoGUI(this);
	}

	// public methods
	/**
	 * A getter method for getting the number of players.
	 * 
	 * @return the number of players in integers
	 */
	public int getNumOfPlayers() {
		return this.numOfPlayers;
	}

	/**
	 * A getter method for retrieving the deck of cards being used.
	 * 
	 * @return the deck of cards being used (a Deck object)
	 */
	public Deck getDeck() {
		return this.deck;
	}

	/**
	 * A getter method for retrieving the list of players.
	 * 
	 * @return an ArrayList of CardGamePlayer players
	 */
	public ArrayList<CardGamePlayer> getPlayerList() {
		return this.playerList;
	}

	/**
	 * A method for retrieving the list of hands played on the table.
	 * 
	 * @return an ArrayList of Hand objects
	 */
	public ArrayList<Hand> getHandsOnTable() {
		return this.handsOnTable;
	}

	/**
	 * A method for retrieving the index of the current player.
	 * 
	 * @return the index of the current player in integer
	 */
	public int getCurrentPlayerIdx() {
		return this.currentPlayerIdx;
	}

	/**
	 * A method for starting/restarting the game with a given shuffled deck of
	 * cards.
	 * 
	 * @param deck The Deck object supplied which is a shuffled deck of cards
	 */
	public void start(Deck deck) {
		// (i) remove all the cards from the players and table
		for (int i = 0; i < 4; i++) {
			playerList.get(i).removeAllCards();
		}

		if (!handsOnTable.isEmpty()) {
			handsOnTable.clear();
		}

		currentPlayerIdx = 0;
		// (ii) distribute cards to the player
		// distribute 52 cards to each of the 4 players
		int playerNum = 0;
		for (int i = 0; i < 52; i++) {
			playerList.get(playerNum).addCard(deck.getCard(i));
			if (playerNum == 3) {
				playerNum = 0;
			} else {
				playerNum += 1;
			}
		}

		// sort the cards of the player
		for (int i = 0; i < 4; i++) {
			playerList.get(i).sortCardsInHand();
		}

		Card threeDiamonds = new Card(0, 2);
		for (int i = 0; i < 4; i++) {
			// (iii) identify player who has 3 Diamonds
			if (playerList.get(i).getCardsInHand().contains(threeDiamonds)) {
				// (iv) set both the currentPlayerIdx of the BigTwo object and the
				// activePlayer of the BigTwoUI object to the index of the
				// player who holds the Three of Diamonds
				currentPlayerIdx = i;
				ui.setActivePlayer(i);
				break;
			}
		}

		// (v) call the repaint() method of the BigTwoUI object to show cards on table
		ui.repaint();
		// (vi) call the promptActivePlayer() method of the BigTwoUI object to prompt
		// user to select cards and make his/her move
		ui.promptActivePlayer();
	}

	/**
	 * A method for making a move by a player with the specified index using the
	 * cards specified by the list of indices.
	 * 
	 * @param playerIdx An integer which specifies the index of which player will go
	 *                  their turn
	 * @param cardIdx   An array of integers which specifies the indexes of the
	 *                  cards selected
	 */
	public void makeMove(int playerIdx, int[] cardIdx) {
		checkMove(playerIdx, cardIdx);
	}

	/**
	 * A method for checking a move made by a player. This method is called from the
	 * makeMove() method.
	 * 
	 * @param playerIdx An integer which specifies the index of which player will go
	 *                  their turn
	 * @param cardIdx   An array of integers which specifies the indexes of the
	 *                  cards selected
	 */
	public void checkMove(int playerIdx, int[] cardIdx) {
		Card threeDiamonds = new Card(0, 2); // starting card
		CardList cardsSelected = new CardList(); // the cards selected by the player
		boolean isValid = false; // check if the composed Hand is valid to move on

		while (!endOfGame()) {

			// first move of the game
			// if first move of the game is a pass, prompt player again
			if (playerList.get(playerIdx).getCardsInHand().contains(threeDiamonds) && cardIdx == null) {
				ui.printMsg("Not a legal move!!!\n");
				ui.promptActivePlayer();
				return;
			}

			// if first move of the player is not a pass
			else if (playerList.get(playerIdx).getCardsInHand().contains(threeDiamonds) && cardIdx != null) {
				// unless the player plays a hand with 3 diamonds, they will be prompted again
				while (isValid == false) {
					for (int i = 0; i < cardIdx.length; i++) {
						cardsSelected.addCard(playerList.get(playerIdx).getCardsInHand().getCard(cardIdx[i]));
					}
					Hand currentHand = composeHand(playerList.get(playerIdx), cardsSelected);
					for (int i = 0; i < cardIdx.length; i++) {
						if (currentHand != null && (playerList.get(playerIdx).getCardsInHand().getCard(cardIdx[i])
								.getSuit() == 0
								&& playerList.get(playerIdx).getCardsInHand().getCard(cardIdx[i]).getRank() == 2)) {
							isValid = true;
						}
					}
					if (isValid == false) {
						ui.printMsg("Not a legal move!!!\n");
						ui.promptActivePlayer();
						return;
					}
				}

				// if the player plays a hand consisting of 3 diamonds
				if (isValid == true) {
					playerList.get(playerIdx).removeCards(composeHand(playerList.get(playerIdx), cardsSelected));
					handsOnTable.add(composeHand(playerList.get(playerIdx), cardsSelected));
					ui.printMsg("{" + composeHand(playerList.get(playerIdx), cardsSelected).getType() + "} ");
					ui.printMsg(composeHand(playerList.get(playerIdx), cardsSelected).toString());
					ui.printMsg("\n");

					if (playerIdx != 3) {
						ui.setActivePlayer(playerIdx + 1);
					} else {
						ui.setActivePlayer(0);
					}

					ui.repaint();
					ui.promptActivePlayer();
					return;
				}
			}

			// other moves of the game (pass & if it is a proper move)
			else {
				// pass: cardIdx should be null and the current player should not have empty
				// hand
				if (cardIdx == null && !playerList.get(playerIdx).getCardsInHand().isEmpty()) {
					// checking if the current player is NOT the player who last put a Hand on the
					// Table
					if (playerList.get(playerIdx) != handsOnTable.get(handsOnTable.size() - 1).getPlayer()) {
						ui.printMsg("{Pass}\n");

						if (playerIdx != 3) {
							ui.setActivePlayer(playerIdx + 1);
						} else {
							ui.setActivePlayer(0);
						}

						ui.repaint();
						ui.promptActivePlayer();
						return;
					}

					else { // if player attempts to pass, but is invalid (player is last player who put
							// Hand on Table)
						ui.printMsg("Not a legal move!!!\n");
						ui.promptActivePlayer();
						return;
					}
				}

				// play
				else if (cardIdx != null && !playerList.get(playerIdx).getCardsInHand().isEmpty()) {

					// after all players pass or handsOnTable is unbeatable
					if (!handsOnTable.isEmpty()) {
						// if the current player is player who last put a Hand on Table
						if (handsOnTable.get(handsOnTable.size() - 1).getPlayer() == playerList.get(playerIdx)) {
							for (int i = 0; i < cardIdx.length; i++) {
								cardsSelected.addCard(playerList.get(playerIdx).getCardsInHand().getCard(cardIdx[i]));
							}
							Hand currentHand = composeHand(playerList.get(playerIdx), cardsSelected);
							isValid = true;
						}
					}

					// composing a hand for normal turns (not after everyone passes)
					while (isValid == false) {
						for (int i = 0; i < cardIdx.length; i++) {

							cardsSelected.addCard(playerList.get(playerIdx).getCardsInHand().getCard(cardIdx[i]));
						}
						Hand currentHand = composeHand(playerList.get(playerIdx), cardsSelected);
						if (currentHand != null
								&& handsOnTable.get(handsOnTable.size() - 1).size() == currentHand.size()) {
							isValid = true;
						} else {
							ui.printMsg("Not a legal move!!!\n");
							ui.promptActivePlayer();
							return;
						}
					} // at this point hand should be valid

					// if player is not the player who last put Hand on Table
					if (handsOnTable.get(handsOnTable.size() - 1).getPlayer() != playerList.get(playerIdx)) {
						Hand currentHand = composeHand(playerList.get(playerIdx), cardsSelected);

						if (currentHand.beats(handsOnTable.get(handsOnTable.size() - 1))) {
							playerList.get(playerIdx)
									.removeCards(composeHand(playerList.get(playerIdx), cardsSelected));
							handsOnTable.add(composeHand(playerList.get(playerIdx), cardsSelected));
							ui.printMsg("{" + composeHand(playerList.get(playerIdx), cardsSelected).getType() + "} ");
							ui.printMsg(composeHand(playerList.get(playerIdx), cardsSelected).toString());
							ui.printMsg("\n");

							if (endOfGame()) {
								break;
							} else {
								if (playerIdx != 3) {
									ui.setActivePlayer(playerIdx + 1);
								} else {
									ui.setActivePlayer(0);
								}

								ui.repaint();
								ui.promptActivePlayer();
								return;
							}

						} else {
							ui.printMsg("Not a legal move!!!\n");
							ui.promptActivePlayer();
							return;
						}
					}

					// player is the player who last put Hand on Table
					else {
						isValid = false;
						if (!cardsSelected.isEmpty()) {
							cardsSelected.removeAllCards();
						}

						while (isValid == false) {
							for (int i = 0; i < cardIdx.length; i++) {
								cardsSelected.addCard(playerList.get(playerIdx).getCardsInHand().getCard(cardIdx[i]));
							}
							Hand currentHand = composeHand(playerList.get(playerIdx), cardsSelected);
							if (currentHand != null) {
								isValid = true;
							} else {
								ui.printMsg("Not a legal move!!!\n");
								ui.promptActivePlayer();
								return;
							}
						}
						playerList.get(playerIdx).removeCards(composeHand(playerList.get(playerIdx), cardsSelected));

						handsOnTable.add(composeHand(playerList.get(playerIdx), cardsSelected));
						ui.printMsg("{" + composeHand(playerList.get(playerIdx), cardsSelected).getType() + "} ");
						ui.printMsg(composeHand(playerList.get(playerIdx), cardsSelected).toString());
						ui.printMsg("\n");

						// if one of the players has an empty hand, end the game, if not, change
						// activePlayer
						if (endOfGame()) {
							break;
						} else {
							if (playerIdx != 3) {
								ui.setActivePlayer(playerIdx + 1);
							} else {
								ui.setActivePlayer(0);
							}

							ui.repaint();
							ui.promptActivePlayer();
							return;
						}

					}
				}
			}
		}

		// while loop has been broken, which means game has ended
		if (endOfGame()) {
			ui.repaint();
			ui.printMsg("\n");
			ui.printMsg("Game ends\n");
			for (int i = 0; i < playerList.size(); i++) {
				if (playerList.get(i).getNumOfCards() == 0) {
					ui.printMsg("Player " + i + " wins the game\n");
				} else {
					ui.printMsg("Player " + i + " has " + playerList.get(i).getNumOfCards() + " cards in hand\n");
				}
			}
			ui.disable();
			return;
		}
	}

	/**
	 * A method for checking if the game ends.
	 * 
	 * @return a Boolean value indicating whether or not game has ended.
	 */
	public boolean endOfGame() {
		for (int i = 0; i < playerList.size(); i++) {
			if (playerList.get(i).getNumOfCards() == 0) {
				return true;
			}
		}

		return false;
	}

	// public static methods
	/**
	 * A method for starting a Big Two card game
	 * 
	 * @param args Not being used in this application.
	 */
	public static void main(String[] args) {
		BigTwo bigTwoGame = new BigTwo();
		BigTwoDeck bigTwoGameDeck = new BigTwoDeck();
		bigTwoGameDeck.shuffle();
		bigTwoGame.start(bigTwoGameDeck);

	}

	/**
	 * A method for returning a valid hand from the specified list of cards of the
	 * player.
	 * 
	 * @param player A CardGamePlayer object of the current player the Hand is to be
	 *               composed
	 * @param cards  A CardList object of the cards to be contained in the Hand
	 * @return A Hand object consisting of the cards specified
	 */
	public static Hand composeHand(CardGamePlayer player, CardList cards) {
		Single singleHand = new Single(player, cards);
		Pair pairHand = new Pair(player, cards);
		Triple tripleHand = new Triple(player, cards);
		Straight straightHand = new Straight(player, cards);
		Flush flushHand = new Flush(player, cards);
		FullHouse fullHouseHand = new FullHouse(player, cards);
		Quad quadHand = new Quad(player, cards);
		StraightFlush straightFlushHand = new StraightFlush(player, cards);

		if (straightFlushHand.isValid()) {
			return straightFlushHand;
		}
		if (quadHand.isValid()) {
			return quadHand;
		}
		if (fullHouseHand.isValid()) {
			return fullHouseHand;
		}
		if (flushHand.isValid()) {
			return flushHand;
		}
		if (straightHand.isValid()) {
			return straightHand;
		}
		if (tripleHand.isValid()) {
			return tripleHand;
		}
		if (pairHand.isValid()) {
			return pairHand;
		}
		if (singleHand.isValid()) {
			return singleHand;
		}
		return null;
	}
}
