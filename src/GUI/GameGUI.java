package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import Logic.GameManager;
import Logic.Player;
import Logic.CellContexts.CellContext;
import Logic.GameManager.MoveDirection;
import Logic.Setups.SetupSettings;

public class GameGUI {
	GameManager gameManager;
	JFrame frame;
	JLabel helperLabel;

	JPanel cells_panel;
	JButton cell_buttons[][];

	JPanel score_panel;
	JLabel player1_score_label, player2_score_label;

	public int buttonSizeHeight = 30;
	public int buttonSizeWIdth = 60;

	public GameGUI(SetupSettings setupSettings, Player player1, Player player2) {

		System.out.println("Starting GameGUI with settings :\n" + setupSettings.toString());
		gameManager = new GameManager(setupSettings, player1, player2);

		setupGUI();

		UpdateCellButtons(true);
		UpdateHelperLabel();
		UpdateScoreLabels();
	}

	private void setupGUI() {
		frame = new JFrame("Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel content = new JPanel();
		frame.add(content);
		content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

		// helper text
		JPanel helperPanel = new JPanel();
		helperLabel = new JLabel("Helper label!");
		helperPanel.add(helperLabel);

		// button cells
		cell_buttons = new JButton[gameManager.GetWidth()][gameManager.GetHeight()];
		for (int i = 0; i < cell_buttons.length; i++)
			for (int j = 0; j < cell_buttons[i].length; j++)
				cell_buttons[i][j] = new JButton();

		cells_panel = new JPanel();
		cells_panel.setLayout(new GridLayout(cell_buttons.length, cell_buttons[0].length));
		for (JButton[] btn : cell_buttons)
			for (JButton _b : btn) {
				cells_panel.add(_b);
				_b.setPreferredSize(new Dimension(buttonSizeWIdth, buttonSizeHeight));
			}

		// score text
		score_panel = new JPanel();
		score_panel.setLayout(new GridLayout(1, 2));
		player1_score_label = new JLabel("Score : 2");
		player2_score_label = new JLabel("Score : 1");
		player1_score_label.setHorizontalAlignment( /* center */ 0);
		player2_score_label.setHorizontalAlignment( /* center */ 0);
		score_panel.add(player1_score_label);
		score_panel.add(player2_score_label);

		content.add(helperPanel);
		content.add(cells_panel);
		content.add(score_panel);

		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null); // at the center of screen
	}

	private void UpdateCellButtons(boolean enableButtons) {

		if (gameManager.IsGameFinished())
			return;

		System.out.println("PLayer is at (" + gameManager.GetPlayerWithTurn().cell_index_i + ", "
				+ gameManager.GetPlayerWithTurn().cell_index_j + ")");

		for (int i = 0; i < cell_buttons.length; i++) {
			for (int j = 0; j < cell_buttons[i].length; j++) {
				JButton btn = cell_buttons[i][j];
				btn.setText(gameManager.GetCell(i, j).context.getCellText());

				// enable/disable
				boolean enabled = true;

				CellContext context = gameManager.GetCell(i, j).context;
				if (context.isPlayerCell() || context.isBlockCell())
					enabled = false;

				int player_i = gameManager.GetPlayerWithTurn().cell_index_i;
				int player_j = gameManager.GetPlayerWithTurn().cell_index_j;
				if (Math.abs(i - player_i) + Math.abs(j - player_j) != 1)
					enabled = false;

				btn.setEnabled(enableButtons && enabled);

				if (btn.isEnabled()) {
					// btn.setText(i + ":" + j);
					btn.addActionListener(new GameCellKeyListsner(i, j));
				}

			}
		}
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.repaint();
	}

	class GameCellKeyListsner extends AbstractAction {
		int i, j;

		public GameCellKeyListsner(int i, int j) {
			super();
			System.out.println("init button ( " + i + ", " + j + ")");
			this.i = i;
			this.j = j;
		}

		@Override
		public void actionPerformed(ActionEvent actionEvent) {

			GameManager.MoveDirection moveDirection = MoveDirection.Up;

			int move_i = i - gameManager.GetPlayerWithTurn().cell_index_i;
			int move_j = j - gameManager.GetPlayerWithTurn().cell_index_j;

			if (move_i == 1)
				moveDirection = MoveDirection.Up;
			else if (move_i == -1)
				moveDirection = MoveDirection.Down;
			else if (move_j == 1)
				moveDirection = MoveDirection.Right;
			else if (move_j == -1)
				moveDirection = MoveDirection.Left;

			System.out.println("moving " + gameManager.GetPlayerWithTurn().name + " to " + moveDirection.toString());

			try {
				gameManager.Move(
					moveDirection,
					() -> { // callback on after each move sequence
						System.out.println("On each sequence callback!!");
						UpdateCellButtons(false); // updates buttons but don't let users put fingers on them
					},
					() -> { // on finish callback
						System.out.println("On finish callback!!");
						UpdateHelperLabel();
						UpdateScoreLabels();
						UpdateCellButtons(true); // updates buttons AND let users put fingers on them
						});
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	private void UpdateHelperLabel() {

		if (gameManager.IsGameFinished()) {
			helperLabel.setText("Game is finished! " + gameManager.GetPlayerWithMostScore().name + " Is The Winner!");

		} else {
			helperLabel.setText("It's turn for " + gameManager.GetPlayerWithTurn().name + " to move");
		}
		frame.pack();
		frame.setLocationRelativeTo(null);
	}

	private void UpdateScoreLabels() {
		player1_score_label.setText("Player 1 : " + gameManager.GetPlayer(0).getPoints());
		player2_score_label.setText("Player 2 : " + gameManager.GetPlayer(1).getPoints());

		boolean is_player_1_higher = gameManager.GetPlayerWithMostScore() == gameManager.GetPlayer(0);
		boolean is_player_2_higher = gameManager.GetPlayerWithMostScore() == gameManager.GetPlayer(1);

		player1_score_label.setForeground(is_player_1_higher ? Color.ORANGE : Color.black);
		player2_score_label.setForeground(is_player_2_higher ? Color.ORANGE : Color.black);

		frame.pack();
		frame.setLocationRelativeTo(null);
	}
}
