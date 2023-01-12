/**
 * The Straight class is a subclass of the Hand class and is used to model a Straight hand of cards. 
 * <br>
 * This class overrides the getTopCard(), isValid() and getType() methods in parent class Hand
 * @author Alicia Sutikno
 */
public class Straight extends Hand {
	/**
	 * A constructor for the Straight type hand. The constructor of parent class Hand will be called.
	 * @param player A CardGamePlayer object of the current player who plays this Hand
	 * @param cards A CardList object of the cards to be contained in the Hand
	 */
	public Straight(CardGamePlayer player, CardList cards) {
		super(player, cards);
	}
	
	/**
	 * A getter method for retrieving the player of this Straight hand.
	 * @return A CardGamePlayer object which is the player of this hand
	 */
	public Card getTopCard() {

		int [] straightRank = {this.getCard(0).getRank(), 
				this.getCard(1).getRank(), 
				this.getCard(2).getRank(),
				this.getCard(3).getRank(),
				this.getCard(4).getRank(),
		};
		
		int topCardRank = straightRank[0];
		int index = 0;

		for (int i = 0; i < straightRank.length; i++) {
			// if the card is a '2'
			if (straightRank[i] == 1) {straightRank[i] = 14;}
			// if the card is a 'A'
			else if (straightRank[i] == 0) {straightRank[i] = 13;} 
		}
		
		// finding the highest rank
		for (int i = 1; i < 5; i++) {
			if (topCardRank < straightRank[i]) {
				topCardRank = straightRank[i];
				index = i;
			}
		}
		
		return this.getCard(index);
	}

	/**
	 * A method for checking if this is a valid Straight hand
	 * @return A Boolean value which indicates whether or not Straight Hand is valid
	 */
	public boolean isValid() {
		
		if (this.size() == 5) {
			int [] straightRank = {this.getCard(0).getRank(), 
					this.getCard(1).getRank(), 
					this.getCard(2).getRank(),
					this.getCard(3).getRank(),
					this.getCard(4).getRank(),
			};
			int [] straightSuit = {this.getCard(0).getSuit(), 
					this.getCard(1).getSuit(), 
					this.getCard(2).getSuit(),
					this.getCard(3).getSuit(),
					this.getCard(4).getSuit(),
			};
			boolean validStraightSuit = false;
			
			// verifying straight suits
			for (int i = 0; i < straightSuit.length-1; i++) {
				if (straightSuit[i] != straightSuit[i+1]) {
					validStraightSuit = true;
				}
			}
			
			if (validStraightSuit == false) {return false;}
			
			// modifying the ranks of '2' and 'A' cards
			for (int i = 0; i < straightRank.length; i++) {
				// if the card is a '2'
				if (straightRank[i] == 0) {straightRank[i] = 14;}
				// if the card is a 'A'
				else if (straightRank[i] == 1) {straightRank[i] = 13;} 
			}

			// sorting straightRank array
			for (int i = 0; i < straightRank.length; i++){  
				for (int j = i + 1; j < straightRank.length; j++){  
					int temp = 0;  
					if (straightRank[i] > straightRank[j]){  
						temp = straightRank[i];  
						straightRank[i] = straightRank[j];  
						straightRank[j] = temp;}  
				}  
			}
			
			// checking if Straight is valid
			for (int i = 0; i < straightRank.length-1; i++){
				if (straightRank[i] + 1 == straightRank[i+1]) {;}
				else { return false; }
			}
			return true;
		}
		return false;
	}

	/**
	 * An abstract method for returning a string specifying the type of this Straight hand.
	 * @return A String value which indicates the type of the Hand
	 */
	public String getType() {
		return "Straight";
	}
}
