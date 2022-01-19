package Logic;

import Logic.CellContexts.PlayerCell;

public class Player {

    public String name;
    public int cell_index_i, cell_index_j;

    public Player(String name) {
        this.name = name;
    }

    int points;

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    // updates the cell indecies based on the contexts on the cells 2D array
    public void UpdateCellIndecies(Cell[][] cells) throws Exception {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (cells[i][j].context.isPlayerCell() && ((PlayerCell) cells[i][j].context).player == this) {
                    cell_index_i = i;
                    cell_index_j = j;
                    return;
                }
            }
        }
        throw new Exception("Player cell not found!");
    }

}
