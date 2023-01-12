/**
 * The Hand class is a subclass of the CardList class and is used to model a hand of cards. 
 * <br>
 * It has a private instance variable for storing the player who plays this hand. It also has methods for
 * getting the player of this hand, checking if it is a valid hand, getting the type of this hand,
 * getting the top card of this hand, and checking if it beats a specified hand.
 * @author Alicia Sutikno
 *
 */
abstract class Hand extends CardList{
	
	// instance variables
	private CardGamePlayer player;
	
	// public constructor
	/**
	 * A constructor for building a hand with the specified player and list of cards.
	 * @param player A CardGamePlayer object of the current player who plays this Hand
	 * @param cards A CardList object of the cards to be contained in the Hand
	 */
	public Hand(CardGamePlayer player, CardList cards) {
		this.player = player;
		for (int i = 0; i < cards.size(); i++) {
			this.addCard(cards.getCard(i)); // add card to the player's hand
		}
	}
	
	// methods
	/**
	 * A getter method for retrieving the player of this hand.
	 * @return A CardGamePlayer object which is the player of this hand
	 */
	public CardGamePlayer getPlayer() {
		return this.player;
	}
	
	/**
	 * A getter method for retrieving the top card of this hand.
	 * @return A Card object which is the top card of this hand
	 */
	public Card getTopCard() {
		return null; // will be overridden by subclasses of Hand
	}
	
	/**
	 * A method for checking if this hand beats a specified hand.
	 * @param hand A Hand object which is the specified hand
	 * @return A Boolean value which indicates whether or not this hand beats a specified hand
	 */
	public boolean beats(Hand hand) {
		// checking for single hand
		if (this.size() == 1 && hand.size() == 1 && this.isValid() && hand.isValid() ) {
			if (this.getTopCard().compareTo(hand.getTopCard()) == 1) {
				return true;
			}
			else {
				return false;
			}
		}
		
		// checking for double hand
		else if (this.size() == 2 && hand.size() == 2 && this.isValid() && hand.isValid() ) {
			if (this.getTopCard().compareTo(hand.getTopCard()) == 1) {
				return true;
			}
			else {
				return false;
			}
		}
		
		// checking for triple
		else if (this.size() == 3 && hand.size() == 3 && this.isValid() && hand.isValid() ) {
			if (this.getTopCard().compareTo(hand.getTopCard()) == 1) {
				return true;
			}
			else {
				return false;
			}
		}
		
		// checking for combinations involving 5 cards
		else if (this.size() == 5 && hand.size() == 5 && this.isValid() && hand.isValid() ) {
			// if both hands are the same type (e.g. flush & flush)
			if (this.getType() == hand.getType()) {
				if (this.getTopCard().compareTo(hand.getTopCard()) == 1) {
					return true;
				}
				else {
					return false;
				}
			}
			
			// if both hands are different types (e.g. straight flush & quad)
			else {
				if (this.getType() == "StraightFlush") {
					return true;
				}
				else if (this.getType() == "Quad" && hand.getType() != "StraightFlush") {
					return true;
				}
				else if (this.getType() == "FullHouse" && 
						(hand.getType() != "StraightFlush" && hand.getType() != "Quad")) {
					return true;
				}
				else if (this.getType() == "Flush" &&
						(hand.getType() != "StraightFlush" && hand.getType() != "Quad" && hand.getType() != "FullHouse")) {
					return true;
				}
				else { 
					// if this hand is straight, but other hand is a different combo, this hand will not beat
					return false;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * An abstract method for checking if this is a valid hand
	 * @return A Boolean value which indicates whether or not Hand is valid
	 */
	public abstract boolean isValid();
	
	/**
	 * An abstract method for returning a string specifying the type of this hand.
	 * @return A String value which indicates the type of the Hand
	 */
	public abstract String getType(); 
}
