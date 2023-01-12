/**
 * The Flush class is a subclass of the Hand class and is used to model a Flush hand of cards. 
 * <br>
 * This class overrides the getTopCard(), isValid() and getType() methods in parent class Hand
 * @author Alicia Sutikno
 */
public class Flush extends Hand {
	/**
	 * A constructor for the Flush type hand. The constructor of parent class Hand will be called.
	 * @param player A CardGamePlayer object of the current player who plays this Hand
	 * @param cards A CardList object of the cards to be contained in the Hand
	 */
	public Flush(CardGamePlayer player, CardList cards) {
		super(player, cards);
	}
	
	/**
	 * A getter method for retrieving the player of this Flush hand.
	 * @return A CardGamePlayer object which is the player of this hand
	 */
	public Card getTopCard() {
		int [] flushRank = {this.getCard(0).getRank(), 
				this.getCard(1).getRank(), 
				this.getCard(2).getRank(),
				this.getCard(3).getRank(),
				this.getCard(4).getRank(),
		};
		
		int topCardRank = flushRank[0];
		int index = 0;

		for (int i = 0; i < flushRank.length; i++) {
			// if the card is a '2'
			if (flushRank[i] == 1) {flushRank[i] = 14;}
			// if the card is a 'A'
			else if (flushRank[i] == 0) {flushRank[i] = 13;} 
		}
		
		// finding the highest rank
		for (int i = 1; i < this.size(); i++) {
			if (topCardRank < flushRank[i]) {
				topCardRank = flushRank[i];
				index = i;
			}
		}
		
		return this.getCard(index);
	}

	/**
	 * A method for checking if this is a valid Flush hand
	 * @return A Boolean value which indicates whether or not Flush Hand is valid
	 */
	public boolean isValid() {
		if (this.size() == 5) {
			int [] flushSuit = {this.getCard(0).getSuit(), 
					this.getCard(1).getSuit(), 
					this.getCard(2).getSuit(),
					this.getCard(3).getSuit(),
					this.getCard(4).getSuit(),
			};
			
			// verifying straight suits
			for (int i = 0; i < flushSuit.length-1; i++) {
				if (flushSuit[i] != flushSuit[i+1]) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * An abstract method for returning a string specifying the type of this Flush hand.
	 * @return A String value which indicates the type of the Hand
	 */
	public String getType() {
		return "Flush";
	}
}
