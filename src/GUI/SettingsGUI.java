package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Logic.Player;
import Logic.CellContexts.BlockCell;
import Logic.CellContexts.BombCell;
import Logic.CellContexts.PlayerCell;
import Logic.CellContexts.StarCell;
import Logic.Setups.SetupSettings;
import Logic.Setups.SetupSettings.GameContextsSettings;

public class SettingsGUI {

    // output of the settings
    public SetupSettings setupSettings = new SetupSettings();

    public SettingsGUI() {
        GetGameDimentions();
    }

    public SetupSettings.GameStatsSettings dimentionSettings() {
        return setupSettings.statSettings;
    }

    public SetupSettings.GameContextsSettings contextsSettings() {
        return setupSettings.gameContextsSettings;
    }

    public void GetGameDimentions() {

        // making settings ready
        setupSettings.statSettings = new SetupSettings.GameStatsSettings();
        SetupSettings.GameStatsSettings dimentionSettings = setupSettings.statSettings;

        JFrame frame = new JFrame("Settings");
        frame.setSize(500, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridLayout(6, 1));
        frame.setLocationRelativeTo(null); // center screen

        // first row
        JPanel cell_hor_panel = new JPanel();
        // text
        JLabel horLabel = new JLabel("Cells horizontal count : ");
        horLabel.setForeground(Color.BLACK);
        cell_hor_panel.add(horLabel);
        // horizontal input
        JTextField cell_hor_txt = new JTextField("4");
        cell_hor_txt.setPreferredSize(new Dimension(50, 30));
        cell_hor_panel.add(cell_hor_txt);

        frame.add(cell_hor_panel);

        // second row
        JPanel cell_vert_panel = new JPanel();
        // text
        JLabel vertLabel = new JLabel("Cells vertical count : ");
        vertLabel.setForeground(Color.BLACK);
        cell_vert_panel.add(vertLabel);
        // vertical input
        JTextField cell_vert_txt = new JTextField("4");
        cell_vert_txt.setPreferredSize(new Dimension(50, 30));
        cell_vert_panel.add(cell_vert_txt);
        frame.add(cell_vert_panel);

        // third row , block count
        JPanel block_panel = new JPanel();
        frame.add(block_panel);
        JLabel blockLabel = new JLabel("Blocks Count : ");
        blockLabel.setForeground(Color.BLACK);
        block_panel.add(blockLabel);
        // input
        JTextField block_txt = new JTextField("1");
        block_txt.setPreferredSize(new Dimension(50, 30));
        block_panel.add(block_txt);

        // forth row, bomb count
        JPanel bomb_panel = new JPanel();
        frame.add(bomb_panel);
        JLabel bombLabel = new JLabel("Bomb count : ");
        bombLabel.setForeground(Color.BLACK);
        bomb_panel.add(bombLabel);
        // input
        JTextField bomb_txt = new JTextField("1");
        bomb_txt.setPreferredSize(new Dimension(50, 30));
        bomb_panel.add(bomb_txt);

        // fifth row, stars count
        JPanel star_panel = new JPanel();
        frame.add(star_panel);
        JLabel starLabel = new JLabel("Star Count : ");
        starLabel.setForeground(Color.BLACK);
        star_panel.add(starLabel);
        // input
        JTextField star_txt = new JTextField("1");
        star_txt.setPreferredSize(new Dimension(50, 30));
        star_panel.add(star_txt);

        // next button ( 3rd row )
        JPanel next_panel = new JPanel();
        frame.add(next_panel);
        JButton next_btn = new JButton("Next");
        JButton auto_setup = new JButton("Auto Setup");
        next_panel.add(next_btn);
        next_panel.add(auto_setup);

        // actions and listerers
        cell_hor_txt.addKeyListener(new TextCorrectionCheckListener() {
            @Override
            public boolean isCorrect(String text) {
                return text.chars().allMatch(Character::isDigit) && text.length() > 0;
            }

            @Override
            public void OnSuccess(String text) {
                horLabel.setForeground(Color.BLACK);
            }

            @Override
            public void OnFail(String text) {
                horLabel.setForeground(Color.RED);
            }

            @Override
            public JTextField GetTextField() {
                return cell_hor_txt;
            }
        });
        cell_vert_txt.addKeyListener(new TextCorrectionCheckListener() {
            @Override
            public boolean isCorrect(String text) {
                return text.chars().allMatch(Character::isDigit) && text.length() > 0;
            }

            @Override
            public void OnSuccess(String text) {
                vertLabel.setForeground(Color.BLACK);
            }

            @Override
            public void OnFail(String text) {
                vertLabel.setForeground(Color.RED);
            }

            @Override
            public JTextField GetTextField() {
                return cell_vert_txt;
            }
        });
        block_txt.addKeyListener(new TextCorrectionCheckListener() {
            @Override
            public boolean isCorrect(String text) {
                return text.chars().allMatch(Character::isDigit) && text.length() > 0;
            }

            @Override
            public void OnSuccess(String text) {
                blockLabel.setForeground(Color.BLACK);
            }

            @Override
            public void OnFail(String text) {
                blockLabel.setForeground(Color.RED);
            }

            @Override
            public JTextField GetTextField() {
                return block_txt;
            }
        });
        bomb_txt.addKeyListener(new TextCorrectionCheckListener() {
            @Override
            public boolean isCorrect(String text) {
                return text.chars().allMatch(Character::isDigit) && text.length() > 0;
            }

            @Override
            public void OnSuccess(String text) {
                bombLabel.setForeground(Color.BLACK);
            }

            @Override
            public void OnFail(String text) {
                bombLabel.setForeground(Color.RED);
            }

            @Override
            public JTextField GetTextField() {
                return bomb_txt;
            }
        });
        star_txt.addKeyListener(new TextCorrectionCheckListener() {
            @Override
            public boolean isCorrect(String text) {
                return text.chars().allMatch(Character::isDigit) && text.length() > 0;
            }

            @Override
            public void OnSuccess(String text) {
                starLabel.setForeground(Color.BLACK);
            }

            @Override
            public void OnFail(String text) {
                starLabel.setForeground(Color.RED);
            }

            @Override
            public JTextField GetTextField() {
                return star_txt;
            }
        });
        next_btn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // check for validity
                boolean all_ok = horLabel.getForeground() == Color.BLACK &&
                        vertLabel.getForeground() == Color.BLACK &&
                        blockLabel.getForeground() == Color.BLACK &&
                        bombLabel.getForeground() == Color.BLACK &&
                        starLabel.getForeground() == Color.BLACK;

                if (all_ok) {
                    // go to next part
                    dimentionSettings.horizontalCount = Integer.parseInt(cell_hor_txt.getText());
                    dimentionSettings.verticalCount = Integer.parseInt(cell_vert_txt.getText());
                    dimentionSettings.blocksCount = Integer.parseInt(block_txt.getText());
                    dimentionSettings.bombCount = Integer.parseInt(bomb_txt.getText());
                    dimentionSettings.starsCount = Integer.parseInt(star_txt.getText());

                    frame.setVisible(false);
                    frame.dispose();
                    GetGamePositionings();

                } else {

                    // give error
                    JOptionPane.showMessageDialog(frame, "Some of the inputs are incorrect. check and make sure none " +
                            "is colored red!");
                }
            }
        });
        auto_setup.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // setting up values
                Random rand = new Random();
                dimentionSettings.horizontalCount = rand.nextInt(13) + 8; // from 8 to 21
                dimentionSettings.verticalCount = dimentionSettings.horizontalCount;
                dimentionSettings.blocksCount = (rand.nextInt(2) + 1) * 2; // ( from 1 to 2 ) * 2
                dimentionSettings.bombCount = (rand.nextInt(2) + 1) * 2; // ( from 1 to 2 ) * 2
                dimentionSettings.starsCount = (rand.nextInt(2) + 1) * 2; // ( from 1 to 2 ) * 2

                // setting up cell contexts
                setupSettings.gameContextsSettings = new GameContextsSettings(dimentionSettings.horizontalCount,
                        dimentionSettings.verticalCount);
                GameContextsSettings context = setupSettings.gameContextsSettings;

                // putting blocks
                for (int i = 0; i < dimentionSettings.blocksCount; i++) {
                    while (true) {
                        int _i = rand.nextInt(context.contexts.length);
                        int _j = rand.nextInt(context.contexts[0].length);

                        // go for next try. this turned out wrong
                        if (!context.contexts[_i][_j].isEmptyCell())
                            continue;

                        context.contexts[_i][_j] = new BlockCell();
                        System.out.println("put a block");
                        break;
                    }
                }
                for (int i = 0; i < dimentionSettings.bombCount; i++) {
                    while (true) {
                        int _i = rand.nextInt(context.contexts.length);
                        int _j = rand.nextInt(context.contexts[0].length);
                        // go for next try. this turned out wrong
                        if (!context.contexts[_i][_j].isEmptyCell())
                            continue;

                        try {
                            context.contexts[_i][_j] = new BombCell(rand.nextInt(4));
                            System.out.println("put a bomb");
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                        break;
                    }
                }
                for (int i = 0; i < dimentionSettings.starsCount; i++) {
                    while (true) {
                        int _i = rand.nextInt(context.contexts.length);
                        int _j = rand.nextInt(context.contexts[0].length);
                        // go for next try. this turned out wrong
                        if (!context.contexts[_i][_j].isEmptyCell())
                            continue;

                        context.contexts[_i][_j] = new StarCell(rand.nextInt(19) + 1); // from 1 to 20
                        System.out.println("put a star");
                        break;
                    }
                }

                // and place players
                Player player1 = new Player("P 1");
                Player player2 = new Player("P 2");
                context.contexts[context.contexts.length - 1][0] = new PlayerCell(player1);
                context.contexts[context.contexts.length - 1][context.contexts[0].length - 1] = new PlayerCell(player2);

                // removing the visuals and going on to the game
                frame.setVisible(false);
                frame.dispose();
                new GameGUI(setupSettings, player1, player2);
            }
        });
        // set the cell_hor_txt focused
        cell_hor_txt.grabFocus();
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    public void GetGamePositionings() {

        // making settings ready
        // initializing the gameContextSettings
        setupSettings.gameContextsSettings = new SetupSettings.GameContextsSettings(
                dimentionSettings().horizontalCount,
                dimentionSettings().verticalCount);
        // makign shortcut for the long word
        SetupSettings.GameContextsSettings contextsSettings = setupSettings.gameContextsSettings;

        JFrame frame = new JFrame("Setting up game positionings");
        JPanel content = new JPanel();
        frame.add(content);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        // first row, desciption
        JPanel helperLabel = new JPanel();
        JLabel helper_txt = new JLabel("Set ");
        helper_txt.setPreferredSize(new Dimension(300, 100));
        helperLabel.add(helper_txt);

        // the selection for when each button is selected.
        // it's a helper to be accessed for just buttons listeners and the for loop
        class HelperFunc {
            public int selected_i = -1, selected_j = -1;
            public int i, j;
        }
        final HelperFunc helper = new HelperFunc();

        // the next button
        JPanel nextPanel = new JPanel();
        JButton next_btn = new JButton("Next");
        next_btn.setPreferredSize(new Dimension(200, 100));
        nextPanel.add(next_btn);

        // amount input
        JPanel amountPanel = new JPanel();
        final JTextField amountTxt = new JTextField();
        amountTxt.setText("Type the amount here ( erase this text )");
        amountTxt.setPreferredSize(new Dimension(45, 40));
        amountPanel.add(amountTxt);

        // setup cells
        JButton[][] cellButtons = new JButton[dimentionSettings().horizontalCount][dimentionSettings().verticalCount];
        JPanel cellsLayout = new JPanel();
        cellsLayout.setLayout(new GridLayout(cellButtons.length, cellButtons[0].length));
        content.add(cellsLayout);

        // draw grid
        for (helper.i = 0; helper.i < dimentionSettings().horizontalCount; helper.i++) {
            for (helper.j = 0; helper.j < dimentionSettings().verticalCount; helper.j++) {

                int i = helper.i;
                int j = helper.j;

                cellButtons[i][j] = new SquareButton(String.format("(%d,%d)", i, j));

                cellButtons[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // set the selected
                        helper.selected_i = i;
                        helper.selected_j = j;
                        // make next_button be affected
                        next_btn.setText(String.format("Select cell %d, %d", i, j));
                    }
                });
                cellsLayout.add(cellButtons[i][j]);
            }
        }

        // selection process
        cellButtons[cellButtons.length - 1][0].setEnabled(false); // player cell disabled
        cellButtons[cellButtons.length - 1][cellButtons[0].length - 1].setEnabled(false); // player cell disabled

        class SelectionProcess {
            public int block_count = 0;
            public int bomb_count = 0;
            public int star_count = 0;
            public boolean turn0 = true; // selection turn for player 0
        }
        final SelectionProcess selectionProcess = new SelectionProcess();

        helper_txt.setText("Player 0 should choose a block cell");

        // making the next button actions ready
        next_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // setting context settings

                if (selectionProcess.block_count < dimentionSettings().blocksCount * 2) {
                    contextsSettings().contexts[helper.selected_i][helper.selected_j] = new BlockCell();

                    // update selection process
                    selectionProcess.block_count++;
                    selectionProcess.turn0 = !selectionProcess.turn0;

                    // set helper text
                    if (selectionProcess.block_count < dimentionSettings().blocksCount * 2) {
                        helper_txt.setText(String.format(
                                "Player %s should choose a block cell",
                                selectionProcess.turn0 ? "0" : "1"));
                    } else {
                        helper_txt.setText(
                                "Player 0 should choose a bomb cell ( you should use the input field for the amount )");
                    }
                } else if (selectionProcess.bomb_count < dimentionSettings().bombCount * 2) {
                    BombCell bombCell;
                    try {
                        bombCell = new BombCell(Integer.valueOf(amountTxt.getText()));
                    } catch (Exception e1) {
                        amountTxt.setText("Should Between 0 and 4");
                        return;
                    }
                    contextsSettings().contexts[helper.selected_i][helper.selected_j] = bombCell;

                    // update selection process
                    selectionProcess.bomb_count++;
                    selectionProcess.turn0 = !selectionProcess.turn0;

                    // set helper text
                    if (selectionProcess.block_count < dimentionSettings().blocksCount * 2) {
                        helper_txt.setText(String.format(
                                "Player %s should choose a bomb cell",
                                selectionProcess.turn0 ? "0" : "1"));
                    } else {
                        helper_txt.setText(
                                "Player 0 should choose a star cell ( you should use the input field for the amount )");
                    }
                } else if (selectionProcess.star_count < dimentionSettings().starsCount * 2) {

                    contextsSettings().contexts[helper.selected_i][helper.selected_j] = new StarCell(
                            selectionProcess.star_count % dimentionSettings().starsCount + 1);

                    // update selection process
                    selectionProcess.star_count++;
                    selectionProcess.turn0 = !selectionProcess.turn0;

                    // set helper text
                    if (selectionProcess.star_count < dimentionSettings().starsCount * 2) {
                        helper_txt.setText(String.format(
                                "Player %s should choose a star cell",
                                selectionProcess.turn0 ? "0" : "1"));
                    } else {
                        // !!!!!! DONE !!!!!!!! \\
                        // !!!!!! DONE !!!!!!!! \\
                        // !!!!!! DONE !!!!!!!! \\
                        frame.setVisible(false);
                        frame.dispose();

                        // creating two players
                        Player player1 = new Player("P 1");
                        Player player2 = new Player("P 2");
                        contextsSettings.contexts[contextsSettings.contexts.length - 1][0] = new PlayerCell(player1);
                        contextsSettings.contexts[contextsSettings.contexts.length
                                - 1][contextsSettings.contexts[0].length - 1] = new PlayerCell(player2);

                        new GameGUI(setupSettings, player1, player2);
                    }
                }

                // disabling the cell
                JButton selected_btn = cellButtons[helper.selected_i][helper.selected_j];
                selected_btn.setText(
                        String.format("%d %d | %s",
                                helper.selected_i,
                                helper.selected_j,
                                contextsSettings.contexts[helper.selected_i][helper.selected_j].getCellText()));
                selected_btn.setEnabled(false);
            }
        });

        content.add(helper_txt);
        content.add(cellsLayout);
        content.add(amountTxt);
        content.add(nextPanel);

        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

}
