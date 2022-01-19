package Logic.CellContexts;

public class EmptyCell implements CellContext {

    @Override
    public String getCellText() {
        return ".";
    }
    
    @Override
    public boolean isPassable() {
        return true;
    }

}
