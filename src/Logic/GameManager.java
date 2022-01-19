package Logic;

import Logic.CellContexts.*;
import Logic.Setups.SetupSettings;

public class GameManager {

    private static final int DELAY_MOVE = 200;
    Cell[][] cells;
    Player[] players = new Player[2];
    boolean isgameFinished = false;

    public boolean IsGameFinished() {
        return isgameFinished;
    }

    public GameManager(SetupSettings settings, Player player1, Player player2) {

        // setting up cells
        cells = new Cell[settings.statSettings.horizontalCount][settings.statSettings.verticalCount];

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j] = new Cell(settings.gameContextsSettings.contexts[i][j]);
            }
        }

        // setting up players
        SetPlayer(0, player1);
        SetPlayer(1, player2);
        try {
            player1.UpdateCellIndecies(cells);
            player2.UpdateCellIndecies(cells);
        } catch (Exception e) {
            e.printStackTrace();
        }

        turn = 0;
    }

    public void SetPlayer(int index, Player player) {
        this.players[index] = player;
    }

    public Player GetPlayerWithTurn() {
        return players[turn];
    }

    public Player GetPlayer(int index) {
        return players[index];
    }

    public Player GetPlayer(String name) throws Exception {
        for (Player player : players)
            if (player.name.equals(name))
                return player;
            else
                throw new Exception("Player with the given name was not found! given name : " + name);
        return null;
    }

    public Player GetPlayerWithMostScore() {
        if (players[0].getPoints() > players[1].getPoints())
            return players[0];
        else if (players[1].getPoints() > players[0].getPoints())
            return players[1];
        return null;
    }

    public Cell GetCell(int i, int j) {
        return cells[i][j];
    }

    int turn = 0;

    public int GetTurn() {
        return turn;
    }

    public enum MoveDirection {
        Up, Down, Right, Left
    }

    public interface VoidCallback {
        public void operation();
    }

    public void Move(MoveDirection moveDirection, VoidCallback onEachMove, VoidCallback onFinish) throws Exception {
        final Player current_player = GetPlayerWithTurn();

        Thread thread = new Thread() {
            @Override
            public void run(){
                while (CanMove(current_player, moveDirection)) {

                    try { MoveOneElement(moveDirection); } 
                    catch (Exception e) { e.printStackTrace(); }

                    if (isgameFinished) {
                        onFinish.operation();
                        return;
                    }
                    onEachMove.operation();

                    // wait for 0.5 seconds
                    try { Thread.sleep(DELAY_MOVE); } 
                    catch (InterruptedException e) { e.printStackTrace(); } 
                }
                System.out.println("next turn");
                nextTurn();
                onFinish.operation();
            }
        };
        thread.start();
    }

    private boolean CanMove(Player current_player, MoveDirection moveDirection) {
        int commingIndex1 = current_player.cell_index_i;
        int commingIndex2 = current_player.cell_index_j;

        switch (moveDirection) {
            case Up:
                commingIndex1++;
                break;
            case Down:
                commingIndex1--;
                break;
            case Right:
                commingIndex2++;
                break;
            case Left:
                commingIndex2--;
                break;
        }
        System.out.println("checking " + commingIndex1 + " : " + commingIndex2);
        return CellIsMovable(commingIndex1, commingIndex2);
    }

    private void nextTurn() {
        turn = (turn == 0) ? 1 : 0;
    }

    private void MoveOneElement(MoveDirection moveDirection) throws Exception {

        if (isgameFinished)
            throw new Exception("Game is finished. can't move while game is finished!");
        Player currentPlayer = GetPlayerWithTurn();

        // setting up the comming cell index
        int commingIndex1 = currentPlayer.cell_index_i;
        int commingIndex2 = currentPlayer.cell_index_j;

        switch (moveDirection) {
            case Up:
                commingIndex1++;
                break;
            case Down:
                commingIndex1--;
                break;
            case Right:
                commingIndex2++;
                break;
            case Left:
                commingIndex2--;
                break;
        }

        // check for boundries
        if (!CellIsMovable(commingIndex1, commingIndex2)) {
            throw new Exception("Cell is not movable!");
        }

        // act on the new cell
        Cell newCell = cells[commingIndex1][commingIndex2];
        Cell oldCell = cells[currentPlayer.cell_index_i][currentPlayer.cell_index_j];

        if (newCell.context.getClass().equals(StarCell.class)) {
            // on a star cell
            currentPlayer.points += ((StarCell) newCell.context).points;
        } else if (newCell.context.getClass().equals(BombCell.class)) {
            // on a bomb cell
            currentPlayer.points -= ((BombCell) newCell.context).points;
        }

        // make old cell into an empty cell
        oldCell.context = new EmptyCell();

        // set the new cell's context to player
        newCell.context = new PlayerCell(currentPlayer);

        // update player position indecies
        try {
            for (Player player : players)
                player.UpdateCellIndecies(cells);
        } catch (Exception e) {
            e.getStackTrace();
        }

        // check if game is finished
        UpdateIsGameFinishedStatus();
    }

    private void UpdateIsGameFinishedStatus() {

        // if no stars is left, game is over
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (cells[i][j].context.isStarCell()) {
                    isgameFinished = false;
                    return;
                }
            }
        }
        isgameFinished = true;
    }

    public boolean CellIsMovable(int commingIndex1, int commingIndex2) {
        // check being in boundries
        if (commingIndex1 < 0 || commingIndex1 >= cells.length ||
                commingIndex2 < 0 || commingIndex2 >= cells[0].length) {
            System.out.println("was out of board");
            return false;
        }
        // check cell context
        CellContext context = cells[commingIndex1][commingIndex2].context;
        return context.isPassable();
    }

    // #region Helper functions
    public int GetWidth() {
        return cells.length;
    }

    public int GetHeight() {
        return cells[0].length;
    }
    // #endregion
}
