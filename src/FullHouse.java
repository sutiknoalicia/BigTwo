import java.util.ArrayList;

/**
 * The FullHouse class is a subclass of the Hand class and is used to model a
 * FullHouse hand of cards. <br>
 * This class overrides the getTopCard(), isValid() and getType() methods in
 * parent class Hand
 * 
 * @author Alicia Sutikno
 */
public class FullHouse extends Hand {

	/**
	 * A constructor for the FullHouse type hand. The constructor of parent class
	 * Hand will be called.
	 * 
	 * @param player A CardGamePlayer object of the current player who plays this
	 *               Hand
	 * @param cards  A CardList object of the cards to be contained in the Hand
	 */
	public FullHouse(CardGamePlayer player, CardList cards) {
		super(player, cards);
	}

	/**
	 * A getter method for retrieving the player of this FullHouse hand.
	 * 
	 * @return A CardGamePlayer object which is the player of this hand
	 */
	public Card getTopCard() {

		int rankOne = this.getCard(0).getRank(); // first rank
		int rankTwo = 0; // second rank
		ArrayList<Integer> rankOneIndex = new ArrayList<Integer>(); // stores the index of rankOne
		ArrayList<Integer> rankTwoIndex = new ArrayList<Integer>(); // stores the index of rankTwo

		// finding the other rank
		for (int i = 0; i < this.size(); i++) {
			if (this.getCard(i).getRank() != rankOne) {
				rankTwo = this.getCard(i).getRank();
				break;
			}
		}

		// index of ranks
		for (int i = 0; i < this.size(); i++) {
			if (this.getCard(i).getRank() == rankOne) {
				rankOneIndex.add(i);
			} else if (this.getCard(i).getRank() == rankTwo) {
				rankTwoIndex.add(i);
			}
		}

		// if rankOne is the rank occurring 3x
		if (rankOneIndex.size() == 3) {
			if (this.getCard(rankOneIndex.get(0)).compareTo(this.getCard(rankOneIndex.get(1))) == 1
					&& this.getCard(rankOneIndex.get(0)).compareTo(this.getCard(rankOneIndex.get(2))) == 1) {
				return this.getCard(rankOneIndex.get(0));
			} else if (this.getCard(rankOneIndex.get(1)).compareTo(this.getCard(rankOneIndex.get(0))) == 1
					&& this.getCard(rankOneIndex.get(1)).compareTo(this.getCard(rankOneIndex.get(2))) == 1) {
				return this.getCard(rankOneIndex.get(1));
			}
			return this.getCard(rankOneIndex.get(2));
		}

		// if rankTwo is the rank occurring 3x
		else if (rankTwoIndex.size() == 3) {
			if (this.getCard(rankTwoIndex.get(0)).compareTo(this.getCard(rankTwoIndex.get(1))) == 1
					&& this.getCard(rankTwoIndex.get(0)).compareTo(this.getCard(rankTwoIndex.get(2))) == 1) {
				return this.getCard(rankTwoIndex.get(0));
			} else if (this.getCard(rankTwoIndex.get(1)).compareTo(this.getCard(rankTwoIndex.get(0))) == 1
					&& this.getCard(rankTwoIndex.get(1)).compareTo(this.getCard(rankTwoIndex.get(2))) == 1) {
				return this.getCard(rankTwoIndex.get(1));
			}
			return this.getCard(rankTwoIndex.get(2));
		}

		return null;
	}

	/**
	 * A method for checking if this is a valid FullHouse hand
	 * 
	 * @return A Boolean value which indicates whether or not FullHouse Hand is
	 *         valid
	 */
	public boolean isValid() {
		if (this.size() == 5) {
			int rankOne = this.getCard(0).getRank(); // first rank
			int rankTwo = 0; // second rank
			int countRankOne = 0; // counter for first rank
			int countRankTwo = 0; // counter for second rank

			// finding the other ranks
			for (int i = 0; i < this.size(); i++) {
				if (this.getCard(i).getRank() != rankOne) {
					rankTwo = this.getCard(i).getRank();
					break;
				}
			}

			// number of ranks
			for (int i = 0; i < this.size(); i++) {
				if (this.getCard(i).getRank() == rankOne) {
					countRankOne += 1;
				} else if (this.getCard(i).getRank() == rankTwo) {
					countRankTwo += 1;
				} else {
					return false;
				}
			}

			if (countRankOne + countRankTwo == 5) {
				return true;
			} else {
				return false;
			}

		}
		return false;
	}

	/**
	 * An abstract method for returning a string specifying the type of this
	 * FullHouse hand.
	 * 
	 * @return A String value which indicates the type of the Hand
	 */
	public String getType() {
		return "FullHouse";
	}

}
