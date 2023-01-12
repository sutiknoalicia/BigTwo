/**
 * The BigTwoCard class is a subclass of the Card class and is used to model a card used in a Big Two card game.
 * <br>
 * It should override the compareTo() method it inherits from the Card class to reflect the ordering of 
 * cards used in a Big Two card game. 
 * @author Alicia Sutikno
 *
 */
public class BigTwoCard extends Card {
	
	// constructor
	/**
	 * A constructor for building a card with the specified <code>suit</code> and <code>rank</code>. 
	 * <code>suit</code> is an integer between 0 and 3, and <code>rank</code> is an integer between 0 and 12.
	 * @param suit An integer between 0 and 3 denoting the suit of the card
	 * @param rank An integer between 0 and 12 denoting the rank of the card
	 */
	public BigTwoCard(int suit, int rank) {
		super(suit, rank);
	}
	
	// method
	/**
	 * A method for comparing the order of this card with the specified card. 
	 * Returns a negative integer, zero, or a positive integer when this card is 
	 * less than, equal to, or greater than the specified card.
	 * @param card The specified card to be compared to
	 */
	public int compareTo(Card card) {
		int this_rank = this.rank;
		int card_rank = card.rank;
		// ensuring that 'A' and '2' cards are higher than the rest
		if (this_rank == 1) {
			this_rank = 14;
		}
		else if (this_rank == 0) {
			this_rank = 13;
		}
		
		if (card_rank == 1) {
			card_rank = 14;
		}
		else if (card_rank == 0) {
			card_rank = 13;
		}
		
		if (this_rank > card_rank) {
			return 1;
		} else if (this_rank < card_rank) {
			return -1;
		} else if (this_rank == card_rank) {
			if (this.suit > card.suit) {
				return 1;
			}
			else if (this.suit < card.suit) {
				return -1;
			}
		}
		return 0;
	}
}
