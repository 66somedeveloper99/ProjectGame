package Logic.CellContexts;

public class BlockCell implements CellContext {
    @Override
    public String getCellText() {
        return "BL";
    }

    @Override
    public boolean isPassable() {
        return false;
    }
}
