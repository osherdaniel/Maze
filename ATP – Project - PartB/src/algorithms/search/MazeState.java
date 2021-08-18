package algorithms.search;

import algorithms.mazeGenerators.Position;
import java.io.Serializable;

public class MazeState extends AState implements Serializable{
    private Position position;

    public MazeState(int Row, int Column) {
        if(Row < -1 || Column < -1)
            return;
        position = new Position(Row, Column);
    }
    @Override
    public String toString() {
        return position.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj == null || getClass() != obj.getClass())
            return  false;

        MazeState secondMaze = (MazeState) obj;
        return secondMaze.getPositionRow() == this.getPositionRow() && secondMaze.getPositionColumn() == this.getPositionColumn();
    }
    /**
     * Get the index of the row
     * @return the row index
     */
    public int getPositionRow() {
        return position.getRowIndex();
    }

    /**
     * Get the index of the column
     * @return the column index
     */
    public int getPositionColumn() {
        return position.getColumnIndex();
    }
}
