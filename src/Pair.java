/**
 * The Pair class is a subclass of the Hand class and is used to model a Pair hand of cards. 
 * <br>
 * This class overrides the getTopCard(), isValid() and getType() methods in parent class Hand
 * @author Alicia Sutikno
 */
public class Pair extends Hand{
	/**
	 * A constructor for the Pair type hand. The constructor of parent class Hand will be called.
	 * @param player A CardGamePlayer object of the current player who plays this Hand
	 * @param cards A CardList object of the cards to be contained in the Hand
	 */
	public Pair(CardGamePlayer player, CardList cards) {
		super(player, cards);
	}
	
	/**
	 * A getter method for retrieving the player of this Pair hand.
	 * @return A CardGamePlayer object which is the player of this hand
	 */
	public Card getTopCard() {
		if (this.getCard(0).compareTo(this.getCard(1)) == 1){
			return this.getCard(0);
		}
		return this.getCard(1);
	}

	/**
	 * A method for checking if this is a valid Pair hand
	 * @return A Boolean value which indicates whether or not Pair Hand is valid
	 */
	public boolean isValid() {
		if (this.size() == 2 && (this.getCard(0).getRank() == this.getCard(1).getRank())){
			return true;
		}
		return false;
	}

	/**
	 * An abstract method for returning a string specifying the type of this Pair hand.
	 * @return A String value which indicates the type of the Hand
	 */
	public String getType() {
		return "Pair";
	}

}
