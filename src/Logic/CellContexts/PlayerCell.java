package Logic.CellContexts;

import Logic.Player;

public class PlayerCell implements CellContext {
    public Player player;

    public PlayerCell(Player player) {
        this.player = player;
    }

    @Override
    public String getCellText() {
        return player.name;
    }
    
    @Override
    public boolean isPassable() {
        return false;
    }

}
