/**
 * The StraightFlush class is a subclass of the Hand class and is used to model a StraightFlush hand of cards. 
 * <br>
 * This class overrides the getTopCard(), isValid() and getType() methods in parent class Hand
 * @author Alicia Sutikno
 */
public class StraightFlush extends Hand {
	/**
	 * A constructor for the StraightFlush type hand. The constructor of parent class Hand will be called.
	 * @param player A CardGamePlayer object of the current player who plays this Hand
	 * @param cards A CardList object of the cards to be contained in the Hand
	 */
	public StraightFlush(CardGamePlayer player, CardList cards) {
		super(player, cards);
	}
	
	/**
	 * A getter method for retrieving the player of this StraightFlush hand.
	 * @return A CardGamePlayer object which is the player of this hand
	 */
	public Card getTopCard() {
		int [] straightFlushRank = {this.getCard(0).getRank(), 
				this.getCard(1).getRank(), 
				this.getCard(2).getRank(),
				this.getCard(3).getRank(),
				this.getCard(4).getRank(),
		};
		
		int topCardRank = straightFlushRank[0];
		int index = 0;

		for (int i = 0; i < straightFlushRank.length; i++) {
			// if the card is a '2'
			if (straightFlushRank[i] == 1) {straightFlushRank[i] = 14;}
			// if the card is a 'A'
			else if (straightFlushRank[i] == 0) {straightFlushRank[i] = 13;} 
		}
		
		// finding the highest rank
		for (int i = 1; i < this.size(); i++) {
			if (topCardRank < straightFlushRank[i]) {
				topCardRank = straightFlushRank[i];
				index = i;
			}
		}
		
		return this.getCard(index);
	}

	/**
	 * A method for checking if this is a valid StraightFlush hand
	 * @return A Boolean value which indicates whether or not StraightFlush Hand is valid
	 */
	public boolean isValid() {
		if (this.size() == 5) {
			int [] straightFlushRank = {this.getCard(0).getRank(), 
					this.getCard(1).getRank(), 
					this.getCard(2).getRank(),
					this.getCard(3).getRank(),
					this.getCard(4).getRank(),
			};
			int [] straightFlushSuit = {this.getCard(0).getSuit(), 
					this.getCard(1).getSuit(), 
					this.getCard(2).getSuit(),
					this.getCard(3).getSuit(),
					this.getCard(4).getSuit(),
			};
			
			// verifying straight suits
			for (int i = 0; i < straightFlushSuit.length-1; i++) {
				// if there is a different suit, it is immediately not a straight flush
				if (straightFlushSuit[i] != straightFlushSuit[i+1]) {
					return false; 
				}
			}
			
			
			// modifying the ranks of '2' and 'A' cards
			for (int i = 0; i < straightFlushRank.length; i++) {
				// if the card is a '2'
				if (straightFlushRank[i] == 0) {straightFlushRank[i] = 14;}
				// if the card is a 'A'
				else if (straightFlushRank[i] == 1) {straightFlushRank[i] = 13;} 
			}

			// sorting straightFlushRank array
			for (int i = 0; i < straightFlushRank.length; i++){  
				for (int j = i + 1; j < straightFlushRank.length; j++){  
					int temp = 0;  
					if (straightFlushRank[i] > straightFlushRank[j]){  
						temp = straightFlushRank[i];  
						straightFlushRank[i] = straightFlushRank[j];  
						straightFlushRank[j] = temp;}  
				}  
			} // straightFlushRank array should be in ascending ranks
			
			// checking if StraightFlush is valid
			for (int i = 0; i < straightFlushRank.length-1; i++){
				if (straightFlushRank[i] + 1 == straightFlushRank[i+1]) {;}
				else { return false; } // if condition is not met, it is immediately not a straight flush
			}
			return true;
		}
		return false;
	}

	/**
	 * An abstract method for returning a string specifying the type of this StraightFlush hand.
	 * @return A String value which indicates the type of the Hand
	 */
	public String getType() {
		return "StraightFlush";
	}
}
