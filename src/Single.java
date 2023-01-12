/**
 * The Single class is a subclass of the Hand class and is used to model a Single hand of cards. 
 * <br>
 * This class overrides the getTopCard(), isValid() and getType() methods in parent class Hand
 * @author Alicia Sutikno
 */
public class Single extends Hand {

	/**
	 * A constructor for the Single type hand. The constructor of parent class Hand will be called.
	 * @param player A CardGamePlayer object of the current player who plays this Hand
	 * @param cards A CardList object of the cards to be contained in the Hand
	 */
	public Single(CardGamePlayer player, CardList cards) {
		super(player, cards);
	}
	
	/**
	 * A getter method for retrieving the player of this Single hand.
	 * @return A CardGamePlayer object which is the player of this hand
	 */
	public Card getTopCard() {
		return this.getCard(0); // first card will be the top card
	}
	
	/**
	 * A method for checking if this is a valid Single hand
	 * @return A Boolean value which indicates whether or not Single Hand is valid
	 */
	public boolean isValid() {
		if (this.size() == 1){
			return true;
		}
		return false;
	}

	/**
	 * An abstract method for returning a string specifying the type of this Single hand.
	 * @return A String value which indicates the type of the Hand
	 */
	public String getType() {
		return "Single";
	}

}
