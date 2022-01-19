package Logic.CellContexts;

public class StarCell implements CellContext {
    public int points;

    public StarCell(int points) {
        this.points = points;
    }

    @Override
    public String getCellText() {
        return "S " + points;
    }
    
    @Override
    public boolean isPassable() {
        return true;
    }

}
