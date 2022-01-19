package Logic.Setups;

import Logic.CellContexts.CellContext;
import Logic.CellContexts.EmptyCell;

public class SetupSettings {

    static public class GameStatsSettings {
        public int horizontalCount, verticalCount, blocksCount, starsCount, bombCount;
    }
    public GameStatsSettings statSettings;

    static public class GameContextsSettings {
        public CellContext[][] contexts;

        public GameContextsSettings(int horizontal, int vertical) {
            this.contexts = new CellContext[horizontal][vertical];
            // filling it with empty cells
            for (int i=0; i<contexts.length; i++) for(int j=0; j<contexts[i].length; j++)
                contexts[i][j] = new EmptyCell();
        }
    }
    public GameContextsSettings gameContextsSettings;
    
    @Override
    public String toString() {
        return  "Setup Settings:{"+
                "\n     hor count: "+statSettings.horizontalCount+
                "\n     vert count: "+statSettings.verticalCount+
                "\n     block count: "+statSettings.blocksCount+
                "\n     stars count: "+statSettings.starsCount+
                "\n     bomb count: "+statSettings.blocksCount+
                "\n}\n";
    }
}
