package Logic.CellContexts;

public class BombCell implements CellContext {
    public int points;

    /**
     * points between 0 and 4
     */
    public BombCell(int points) throws Exception {
        if(points < 0 || points > 4) throw new Exception("Points between 0 and 4, inclusively");
        points *= 5;
        if(points == 0) points = 1;
        this.points = points;
    }

    @Override
    public String getCellText() {
        return "B "+points;
    }

    @Override
    public boolean isPassable() {
        return true;
    }
}
