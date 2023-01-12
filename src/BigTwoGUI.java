import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.DefaultCaret;

/**
 * The BigTwoGUI class implements the CardGameUI interface. It is used to build
 * a GUI for the Big Two card game and handle all user actions.
 * 
 * @author Alicia Sutikno
 *
 */
public class BigTwoGUI implements CardGameUI {

	// instance variables

	// a Big Two card game associated with this GUI.
	private BigTwo game;
	// a boolean array indicating which cards are being selected
	private boolean[] selected;

	// an integer specifying the index of the active player.
	private int activePlayer;

	// the main window of the application.
	private JFrame frame;

	// a panel for showing the cards of each player and the cards played on the
	// table.
	private JPanel bigTwoPanel;

	// a 'Play' button for the active player to play the selected cards.
	private JButton playButton;

	// a 'Pass' button for the active player to pass his/her turn to the next
	// player.
	private JButton passButton;

	// a text area for showing the current game status as well as end of game
	// messages.
	private JTextArea msgArea;

	// a text area for showing chat messages sent by the players.
	private JTextArea chatArea;

	// a text field for players to input chat messages.
	private JTextField chatInput;

	private Image cardBack; // back of card image
	private Image[][] cardImage = new Image[4][13];
	private Image[] avatarImage = new Image[4];

	// constructor
	/**
	 * A constructor for creating a BigTwoGUI.
	 * 
	 * @param game Reference to a Big Two card game associated with this GUI
	 */
	public BigTwoGUI(BigTwo game) {
		this.game = game;
		selected = new boolean[13];
		setActivePlayer(game.getCurrentPlayerIdx());
		go();
	}

	// methods
	/**
	 * A method for building the GUI of the BigTwo game.
	 */
	public void go() {
		// Image handling
		cardBack = new ImageIcon("src/cards/b.gif").getImage();
		
		for (int i = 0; i < 4; i++) {
			avatarImage[i] = new ImageIcon("src/avatar/player" + i +".png").getImage(); 
		}
		
		char[] suit = { 'd', 'c', 'h', 's' };
		char[] rank = { 'a', '2', '3', '4', '5', '6', '7', '8', '9', 't', 'j', 'q', 'k' };
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 13; j++) {
				cardImage[i][j] = new ImageIcon("src/cards/" + rank[j] + suit[i] + ".gif").getImage();
			}
		}

		// Frame
		frame = new JFrame();
		frame.setTitle("Big Two");
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(900, 700);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu gameMenu = new JMenu("Game");
		JMenu msgMenu = new JMenu("Message");
		menuBar.add(gameMenu);
		menuBar.add(msgMenu); // TODO add menuitem

		JMenuItem restart = new JMenuItem("Restart");
		restart.addActionListener(new RestartMenuItemListener());
		gameMenu.add(restart);
		JMenuItem quit = new JMenuItem("Quit");
		quit.addActionListener(new QuitMenuItemListener());
		gameMenu.add(quit);

		JMenuItem deleteMsg = new JMenuItem("Clear game messages");
		deleteMsg.addActionListener(new DeleteMessageListener());
		msgMenu.add(deleteMsg);
		JMenuItem deleteChat = new JMenuItem("Clear chat messages");
		deleteChat.addActionListener(new DeleteChatListener());
		msgMenu.add(deleteChat);

		bigTwoPanel = new BigTwoPanel();

		// Play and Pass Buttons
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1, 2));
		playButton = new JButton("Play");
		passButton = new JButton("Pass");
		playButton.addActionListener(new PlayButtonListener());
		passButton.addActionListener(new PassButtonListener());

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		buttonPanel.add(playButton);
		buttonPanel.add(passButton);

		// Chat Label and Chat Input
		JPanel chatPanel = new JPanel();
		JLabel chatLabel = new JLabel("Message: ");
		chatInput = new JTextField();
		chatInput.addActionListener(new ChatInputListener());
		chatPanel.setLayout(new BorderLayout());
		chatPanel.add(chatLabel, BorderLayout.LINE_START);
		chatPanel.add(chatInput, BorderLayout.CENTER);

		// Message Area and Chat Area
		msgArea = new JTextArea();
		msgArea.setEditable(false);
		JScrollPane msgScroll = new JScrollPane(msgArea);
		msgScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		msgScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		DefaultCaret caret = (DefaultCaret) msgArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		chatArea = new JTextArea();
		JSplitPane msgChatSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, msgScroll, chatArea);
		msgChatSplitPane.setResizeWeight(0.5d);
		msgChatSplitPane.setEnabled(false);

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, bigTwoPanel, msgChatSplitPane);
		splitPane.setResizeWeight(0.62d);
		splitPane.setEnabled(false);

		JPanel playPanel = new JPanel(new GridLayout(1, 2));
		playPanel.add(bigTwoPanel);
		playPanel.add(msgChatSplitPane);
		// Adding to panel, and frame
		bottomPanel.add(buttonPanel);
		bottomPanel.add(chatPanel);
		frame.add(bottomPanel, BorderLayout.PAGE_END);
		frame.add(playPanel, BorderLayout.CENTER);

		frame.setVisible(true);
	}

	@Override
	/**
	 * A setter method for setting the index of the active player (i.e., the player
	 * having control of the GUI).
	 * 
	 * @param activePlayer The index of the active player
	 */
	public void setActivePlayer(int activePlayer) {
		this.activePlayer = activePlayer;
	}

	@Override
	/**
	 * A method for repainting the GUI.
	 */
	public void repaint() {
		frame.repaint();
	}

	@Override
	/**
	 * A method for printing the specified string to the message area of the GUI.
	 * 
	 * @param msg The string to be printed to the UI
	 */
	public void printMsg(String msg) {
		msgArea.append(msg);
	}

	@Override
	/**
	 * A method for clearing the message area of the GUI.
	 */
	public void clearMsgArea() {
		msgArea.setText(null);
	}

	@Override
	/**
	 * A method for resetting the GUI.
	 */
	public void reset() {
		// (i) reset the list of selected cards
		resetSelected();
		// (ii) clear the message area
		clearMsgArea();
		chatArea.setText(null);
		chatInput.setText(null);
		// (iii) enable user interactions
		enable();

	}

	@Override
	/**
	 * A method for enabling user interactions with the GUI.
	 */
	public void enable() {
		// (i) enable the 'Play' button and 'Pass' button (i.e., making them clickable)
		playButton.setEnabled(true);
		passButton.setEnabled(true);
		// (ii) enable the chat input
		chatInput.setEnabled(true);
		// (iii) enable the BigTwoPanel for selection of cards through mouse clicks
		bigTwoPanel.setEnabled(true);

	}

	@Override
	/**
	 * A method for disabling user interactions with the GUI.
	 */
	public void disable() {
		// (i) disable the 'Play' button and 'Pass' button (i.e., making them not
		// clickable)
		playButton.setEnabled(false);
		passButton.setEnabled(false);
		// (ii) disable the chat input
		chatInput.setEnabled(false);
		// (iii) disable the BigTwoPanel for selection of cards through mouse clicks
		bigTwoPanel.setEnabled(false);

	}

	@Override
	/**
	 * A method for prompting the active player to select cards and make his/her
	 * move.
	 */
	public void promptActivePlayer() {
		printMsg(game.getPlayerList().get(activePlayer).getName() + "'s turn: \n");
		getSelected();
		resetSelected();

	}

	/**
	 * Resets the list of selected cards to an empty list.
	 */
	private void resetSelected() {
		for (int j = 0; j < selected.length; j++) {
			selected[j] = false;
		}
	}

	/**
	 * Returns an array of indices of the cards selected through the UI.
	 * 
	 * @return an array of indices of the cards selected, or null if no valid cards
	 *         have been selected
	 */
	private int[] getSelected() {
		int[] cardIdx = null;
		int count = 0;
		for (int j = 0; j < selected.length; j++) {
			if (selected[j]) {
				count+=1;
			}
		}

		if (count != 0) {
			cardIdx = new int[count];
			count = 0;
			for (int j = 0; j < selected.length; j++) {
				if (selected[j]) {
					cardIdx[count] = j;
					count+=1;
				}
			}
		}
		return cardIdx;
	}

	// inner classes

	/**
	 * An inner class that extends the JPanel class and implements the MouseListener
	 * interface. Overrides the paintComponent() method inherited from the JPanel
	 * class to draw the card game table. Implements the mouseReleased() method from
	 * the MouseListener interface to handle mouse click events.
	 * 
	 * @author Alicia Sutikno
	 *
	 */
	public class BigTwoPanel extends JPanel implements MouseListener {

		// constructor
		/**
		 * A constructor for creating a BigTwoPanel. Registers listener with the panel.
		 */
		public BigTwoPanel() {
			this.addMouseListener(this);
		}

		/**
		 * Paints the GUI by setting the background colour, player avatars, card images
		 * and their dimensions.
		 * 
		 * @param g An object of Graphics to create a new object Graphics within the
		 *          method which will be manipulated
		 */
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			this.setBackground(new Color(255, 184, 201));
			int playerCounter = 0;
			int width = (this.getWidth() / 15) * 8;
			int x = (this.getWidth() - width) / 3; // dimensions
			int y = (this.getHeight() / 7); // dimensions
			// drawImage(photoName, xLocation, yLocation, xDimensions, yDimensions, this);

			// writing all the player names
			setFont(new Font("Arial", Font.BOLD, 12));
			for (int i = 0; i < 4; i++) {
				if (playerCounter != activePlayer) {
					g.drawString("Player " + playerCounter, 10, 15 + this.getHeight() / 5 * i);
				} else {
					g.setColor(new Color(255, 44, 94));
					g.drawString("You", 10, 15 + this.getHeight() / 5 * i);
				}
				g.drawImage(avatarImage[i], 10, this.getHeight() / 23 + this.getHeight() / 5 * i, x, y, this);
				g.setColor(Color.BLACK);
				playerCounter += 1;
			}

			playerCounter = 0;

			// drawing all the lines
			for (int i = 1; i < 5; i++) {
				// drawLine(x1, y1, x2, y2);
				g2.drawLine(0, this.getHeight() / 5 * i, this.getWidth(), this.getHeight() / 5 * i);
			}

			// the cards
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < game.getPlayerList().get(i).getNumOfCards(); j++) {
					if (activePlayer == i) {
						int suit = game.getPlayerList().get(i).getCardsInHand().getCard(j).getSuit();
						int rank = game.getPlayerList().get(i).getCardsInHand().getCard(j).getRank();
						if (selected[j]) {
							g.drawImage(cardImage[suit][rank], (this.getWidth() / 5 + 5) + (this.getWidth() / 20) * j,
									this.getHeight() / 23 + this.getHeight() / 5 * i - 15, this.getWidth() / 7, y,
									this);
						} else {
							g.drawImage(cardImage[suit][rank], (this.getWidth() / 5 + 5) + (this.getWidth() / 20) * j,
									this.getHeight() / 23 + this.getHeight() / 5 * i, this.getWidth() / 7, y, this);
						}
					} else {
						g.drawImage(cardBack, (this.getWidth() / 5 + 5) + (this.getWidth() / 20) * j,
								this.getHeight() / 23 + this.getHeight() / 5 * i, this.getWidth() / 7, y, this);
					}
				}
			}

			// played by...
			if (!game.getHandsOnTable().isEmpty()) {
				g.drawString("Played by ", 10, 15 + this.getHeight() / 5 * 4);
				g.drawString(game.getHandsOnTable().get(game.getHandsOnTable().size() - 1).getPlayer().getName(), 72,
						15 + this.getHeight() / 5 * 4);
				for (int k = 0; k < game.getHandsOnTable().get(game.getHandsOnTable().size() - 1).size(); k++) {

					int suit = game.getHandsOnTable().get(game.getHandsOnTable().size() - 1).getCard(k).getSuit();
					int rank = game.getHandsOnTable().get(game.getHandsOnTable().size() - 1).getCard(k).getRank();

					g.drawImage(cardImage[suit][rank], (this.getWidth() / 5 + 5) + (this.getWidth() / 20) * k,
							this.getHeight() / 23 + this.getHeight() / 5 * 4, this.getWidth() / 7, y, this);
				}

				if (game.getHandsOnTable().size() > 1) {
					g.drawImage(cardBack, 10, this.getHeight() / 23 + this.getHeight() / 5 * 4, this.getWidth() / 7, y,
							this);
				}

			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		/**
		 * A method which gets invoked when a mouse button has been released on the
		 * specified component. Overrides the mouseReleased() method from MouseListener.
		 * 
		 * @param e A MouseEvent object which indicates that a mouse action occurred in
		 *          a component
		 */
		public void mouseReleased(MouseEvent e) {
			// checking the cards from right to left
			boolean pressed = false;
			int numberOfCards = game.getPlayerList().get(activePlayer).getNumOfCards();
			int currentCardIdx = numberOfCards - 1; // the right-most card
			int cardWidth = this.getWidth() / 7;
			int cardHeight = this.getHeight() / 7;

			// checking all the cards from the right-most card
			while (currentCardIdx >= 0 && pressed == false) {
				// checking the right-most card | range of X and Y coordinates is the entire
				// card
				if (currentCardIdx == numberOfCards - 1) {
					if (e.getX() >= ((this.getWidth() / 5 + 5) + (this.getWidth() / 20) * currentCardIdx)
							&& e.getX() <= ((this.getWidth() / 5 + 5) + (this.getWidth() / 20) * currentCardIdx
									+ cardWidth)) {
						if (!selected[currentCardIdx]) {
							if (e.getY() >= (this.getHeight() / 23 + this.getHeight() / 5 * activePlayer)
									&& e.getY() <= (this.getHeight() / 23 + this.getHeight() / 5 * activePlayer
											+ cardHeight)) {
								selected[currentCardIdx] = true;
								pressed = true;
							}
						} else { // card is selected
							if (e.getY() >= (this.getHeight() / 23 + this.getHeight() / 5 * activePlayer - 15)
									&& e.getY() <= (this.getHeight() / 23 + this.getHeight() / 5 * activePlayer - 15
											+ cardHeight)) {
								selected[currentCardIdx] = false;
								pressed = true;
							}
						}
					}
				}

				// check coordinates from currentCardIdx to the next card (does not account for
				// the
				// tiny part when card next to currentCardIdx is raised)
				else {
					if (e.getX() >= ((this.getWidth() / 5 + 5) + (this.getWidth() / 20) * currentCardIdx) && e
							.getX() <= ((this.getWidth() / 5 + 5) + (this.getWidth() / 20) * (currentCardIdx + 1))) {
						if (!selected[currentCardIdx]) {
							if (e.getY() >= (this.getHeight() / 23 + this.getHeight() / 5 * activePlayer)
									&& e.getY() <= (this.getHeight() / 23 + this.getHeight() / 5 * activePlayer
											+ cardHeight)) {
								selected[currentCardIdx] = true;
								pressed = true;
							}
						} else { // card is selected
							if (e.getY() >= (this.getHeight() / 23 + this.getHeight() / 5 * activePlayer - 15)
									&& e.getY() <= (this.getHeight() / 23 + this.getHeight() / 5 * activePlayer - 15
											+ cardHeight)) {
								selected[currentCardIdx] = false;
								pressed = true;
							}
						}
					}
					// when next card is raised
					if (selected[currentCardIdx + 1]
							&& e.getX() >= ((this.getWidth() / 5 + 5) + (this.getWidth() / 20) * (currentCardIdx + 1))
							&& e.getX() <= ((this.getWidth() / 5 + 5) + (this.getWidth() / 20) * currentCardIdx
									+ cardWidth)) {
						if (!selected[currentCardIdx]) {
							if (e.getY() >= (this.getHeight() / 23 + this.getHeight() / 5 * activePlayer)
									&& e.getY() <= (this.getHeight() / 23 + this.getHeight() / 5 * activePlayer
											+ cardHeight)) {
								selected[currentCardIdx] = true;
								pressed = true;
							}
						}
					}
					// when next card is not raised but currentCardIdx is raised, there is a small
					// section at the top where the player can click
					if (!selected[currentCardIdx + 1]
							&& e.getX() >= ((this.getWidth() / 5 + 5) + (this.getWidth() / 20) * (currentCardIdx + 1))
							&& e.getX() <= ((this.getWidth() / 5 + 5) + (this.getWidth() / 20) * currentCardIdx
									+ cardWidth)) {
						if (selected[currentCardIdx]) {
							if (e.getY() >= (this.getHeight() / 23 + this.getHeight() / 5 * activePlayer - 15)
									&& e.getY() <= (this.getHeight() / 23 + this.getHeight() / 5 * activePlayer - 15
											+ cardHeight)) {
								selected[currentCardIdx] = false;
								pressed = true;
							}
						}
					}
				}

				currentCardIdx -= 1;
			}

			this.repaint();

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

	/**
	 * An inner class that implements the ActionListener interface. Implements the
	 * actionPerformed() method from the ActionListener interface to handle
	 * button-click events for the 'Play' button.
	 * 
	 * @author Alicia Sutikno
	 *
	 */
	public class PlayButtonListener implements ActionListener {

		@Override
		/**
		 * Overrides the actionPerformed() method from the ActionListener interface.
		 * Invoked when an action occurs.
		 * 
		 * @param e An ActionEvent object which detects user action
		 */
		public void actionPerformed(ActionEvent e) {
			// call the makeMove() method of your BigTwo object to make a move
			if (getSelected() == null) {
				printMsg("You should select a hand to play.\n");
				repaint();
			} else {
				game.makeMove(activePlayer, getSelected());
				repaint();
			}
		}

	}

	/**
	 * An inner class that implements the ActionListener interface. Implements the
	 * actionPerformed() method from the ActionListener interface to handle
	 * button-click events for the 'Pass' button.
	 * 
	 * @author Alicia Sutikno
	 *
	 */
	public class PassButtonListener implements ActionListener {

		@Override
		/**
		 * Overrides the actionPerformed() method from the ActionListener interface.
		 * Invoked when an action occurs.
		 * 
		 * @param e An ActionEvent object which detects user action
		 */
		public void actionPerformed(ActionEvent e) {
			// call the makeMove() method of your BigTwo object to make a move
			game.makeMove(activePlayer, null);
			repaint();
		}

	}

	/**
	 * An inner class that implements the ActionListener interface. Implements the
	 * actionPerformed() method from the ActionListener interface to handle
	 * menu-item-click events for the 'Restart' menu item.
	 * 
	 * @author Alicia Sutikno
	 *
	 */
	public class RestartMenuItemListener implements ActionListener {

		@Override
		/**
		 * Overrides the actionPerformed() method from the ActionListener interface.
		 * Invoked when an action occurs.
		 * 
		 * @param e An ActionEvent object which detects user action
		 */
		public void actionPerformed(ActionEvent e) {
			// When the 'Restart' menu item is selected, you should
			// (i) create a new BigTwoDeck object and call its shuffle() method; and
			BigTwoDeck bigTwoGameDeck = new BigTwoDeck();
			bigTwoGameDeck.shuffle();
			// (ii) call the start() method of your BigTwo object with the BigTwoDeck object
			// as an argument.
			game.start(bigTwoGameDeck);
			reset();
			promptActivePlayer();
		}

	}

	/**
	 * An inner class that implements the ActionListener interface. Implements the
	 * actionPerformed() method from the ActionListener interface to handle
	 * menu-item-click events for the 'Quit' menu item.
	 * 
	 * @author Alicia Sutikno
	 *
	 */
	public class QuitMenuItemListener implements ActionListener {

		@Override
		/**
		 * Overrides the actionPerformed() method from the ActionListener interface.
		 * Invoked when an action occurs.
		 * 
		 * @param e An ActionEvent object which detects user action
		 */
		public void actionPerformed(ActionEvent e) {
			// When the 'Quit' menu item is selected, you should terminate your application.
			System.exit(0);
		}

	}

	/**
	 * An inner class that implements the ActionListener interface. Implements the
	 * actionPerformed() method from the ActionListener interface to handle text
	 * input events for the "Chat Input" text field.
	 * 
	 * @author Alicia Sutikno
	 *
	 */
	public class ChatInputListener implements ActionListener {

		@Override
		/**
		 * Overrides the actionPerformed() method from the ActionListener interface.
		 * Invoked when an action occurs.
		 * 
		 * @param e An ActionEvent object which detects user action
		 */
		public void actionPerformed(ActionEvent e) {
			chatArea.append("Player " + activePlayer + ": " + chatInput.getText() + "\n");
			chatInput.setText(null);
		}

	}

	/**
	 * An inner class that implements the ActionListener interface. Implements the
	 * actionPerformed() method from the ActionListener interface to handle
	 * menu-item-click events for the 'Clear chat messages' menu item.
	 * 
	 * @author Alicia Sutikno
	 *
	 */
	public class DeleteChatListener implements ActionListener {

		@Override
		/**
		 * Overrides the actionPerformed() method from the ActionListener interface.
		 * Invoked when an action occurs.
		 * 
		 * @param e An ActionEvent object which detects user action
		 */
		public void actionPerformed(ActionEvent e) {
			chatArea.setText(null);
			chatInput.setText(null);

		}

	}

	/**
	 * An inner class that implements the ActionListener interface. Implements the
	 * actionPerformed() method from the ActionListener interface to handle
	 * menu-item-click events for the 'Clear game messages' menu item.
	 * 
	 * @author Alicia Sutikno
	 */
	public class DeleteMessageListener implements ActionListener {

		@Override
		/**
		 * Overrides the actionPerformed() method from the ActionListener interface.
		 * Invoked when an action occurs.
		 * 
		 * @param e An ActionEvent object which detects user action
		 */
		public void actionPerformed(ActionEvent e) {
			clearMsgArea();

		}
	}

}
