/**
 * The Quad class is a subclass of the Hand class and is used to model a Quad hand of cards. 
 * <br>
 * This class overrides the getTopCard(), isValid() and getType() methods in parent class Hand
 * @author Alicia Sutikno
 */
public class Quad extends Hand {
	/**
	 * A constructor for the Quad type hand. The constructor of parent class Hand will be called.
	 * @param player A CardGamePlayer object of the current player who plays this Hand
	 * @param cards A CardList object of the cards to be contained in the Hand
	 */
	public Quad(CardGamePlayer player, CardList cards) {
		super(player, cards);
	}
	
	/**
	 * A getter method for retrieving the player of this Quad hand.
	 * @return A CardGamePlayer object which is the player of this hand
	 */
	public Card getTopCard() {
		this.sort();
		int rankOneCounter = 0;
		int rankTwoCounter = 0;
		int rankOne = this.getCard(0).getRank(); // first rank
		int rankTwo = 0; // second rank
		
		// finding the other rank
		for (int i = 0; i < this.size(); i++) {
			if (this.getCard(i).getRank() != rankOne) {
				rankTwo = this.getCard(i).getRank();
				break;
			}
		}
		
		// counting the number of each rank (how many times a rank is repeated)
		for (int i = 0; i < this.size(); i++) {
			if (this.getCard(i).getRank() == rankOne) {
				rankOneCounter += 1;
			}
			else {
				rankTwoCounter += 1;
			}
		}
		
		// check which rank occurs 4 times
		if (rankOneCounter == 4){
			for (int i = 0; i < 5; i++) { // iterate through CardList
				// if the card has a spade suit, and if its rank matches with the rank occurring 4x
				// then it is the topCard of the Quad
				if (this.getCard(i).getSuit() == 3 && this.getCard(i).getRank() == rankOne) {
					return this.getCard(i);
				}
			}
		}
		else if (rankTwoCounter == 4) {
			for (int i = 0; i < 5; i++) {
				// if the card has a spade suit, and if its rank matches with the rank occurring 4x
				// then it is the topCard of the Quad
				if (this.getCard(i).getSuit() == 3 && this.getCard(i).getRank() == rankTwo) {
					return this.getCard(i);
				}
			}
		}
		
		return null;
		
		
	}

	/**
	 * A method for checking if this is a valid Quad hand
	 * @return A Boolean value which indicates whether or not Quad Hand is valid
	 */
	public boolean isValid() {
		if (this.size() == 5){
			this.sort();
			if ((this.getCard(0).getRank() == this.getCard(1).getRank()) && 
				(this.getCard(1).getRank() == this.getCard(2).getRank()) &&
				(this.getCard(2).getRank() == this.getCard(3).getRank())) {
				return true; // if first 4 cards have the same rank
			}
			else if ((this.getCard(1).getRank() == this.getCard(2).getRank()) && 
					(this.getCard(2).getRank() == this.getCard(3).getRank()) &&
					(this.getCard(3).getRank() == this.getCard(4).getRank())) {
				return true; // if the last 4 cards have the same rank
			}
			else {
				return false;
			}
		}
		return false;
	}

	/**
	 * An abstract method for returning a string specifying the type of this Quad hand.
	 * @return A String value which indicates the type of the Hand
	 */
	public String getType() {
		return "Quad";
	}
}
