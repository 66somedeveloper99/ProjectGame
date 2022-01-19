package Logic.CellContexts;

public interface CellContext {
    public String getCellText();

    public boolean isPassable();

    public default boolean isEmptyCell() {
        return this.getClass() == EmptyCell.class;
    }
    public default boolean isBombCell() {
        return this.getClass() == BombCell.class;
    }
    public default boolean isStarCell() {
        return this.getClass() == StarCell.class;
    }
    public default boolean isPlayerCell() {
        return this.getClass() == PlayerCell.class;
    }
    public default boolean isBlockCell() {
        return this.getClass() == BlockCell.class;
    }
}

