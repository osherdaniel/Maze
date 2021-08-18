package algorithms.search;

import algorithms.mazeGenerators.Maze;

import javax.swing.plaf.nimbus.State;
import java.util.ArrayList;

public class SearchableMaze implements ISearchable {
    private Maze maze;
    private AState StartState;
    private AState GoalState;

    /**
     * Constructor of the maze
     * @param maze
     */
    public SearchableMaze(Maze maze) {
        if(maze == null)
            this.maze = null;
        else
            this.maze = maze;
        StartState = new MazeState(maze.getStartPosition().getRowIndex(),maze.getStartPosition().getColumnIndex());
        GoalState = new MazeState(maze.getGoalPosition().getRowIndex(),maze.getGoalPosition().getColumnIndex());
    }

    /**
     *
     * @return - The start state of the maze
     */
    @Override
    public AState getStartState() {
        return StartState;
    }

    /**
     *
     * @return - The goal state of the maze
     */
    @Override
    public AState getGoalState() {
        return GoalState;
    }

    /**
     *
     * @param s - The current state
     * @return - A list of the possibleState
     */
    @Override
    public ArrayList<AState> getAllPossibleStates(AState s) {
        if(s == null)
            return null;

        MazeState M = (MazeState) s;
        ArrayList<AState> possibleState = new ArrayList<AState>();

        int rowIndex = M.getPositionRow();
        int columnIndex = M.getPositionColumn();

        int Rows = maze.getRows();
        int Columns = maze.getColumns();

        // Up
        if(rowIndex - 1 > -1 && maze.getMaze(rowIndex - 1, columnIndex) == 0)
            possibleState.add(new MazeState(rowIndex - 1, columnIndex));

        // Right
        if(columnIndex + 1 < Columns && maze.getMaze(rowIndex, columnIndex + 1) == 0)
            possibleState.add(new MazeState(rowIndex, columnIndex + 1));

        // Down
        if(rowIndex + 1 < Rows && maze.getMaze(rowIndex + 1, columnIndex) == 0)
            possibleState.add(new MazeState(rowIndex + 1, columnIndex));

        // Left
        if(columnIndex -  1 > -1 && maze.getMaze(rowIndex, columnIndex - 1) == 0)
            possibleState.add(new MazeState(rowIndex, columnIndex - 1));

        ArrayList<AState> DiagonalList = SetDiagonalCost(rowIndex, columnIndex);
        for(int i = 0; i < DiagonalList.size();i ++)
            possibleState.add(DiagonalList.get(i));

        return possibleState;
    }


    /**
     * Get a list of all the diagonal moves.
     * @param row
     * @param column
     * @return - A List of all Diagonal moves.
     */
    private ArrayList<AState> SetDiagonalCost(int row, int column){
        ArrayList<AState> tmpList = new ArrayList<>();

        int maxRox = maze.getRows();
        int maxColumn = maze.getColumns();

        // Up Right
        if(row - 1 > -1 && column + 1< maxColumn) // It is Ok
            if(maze.getMaze(row - 1, column) == 0 || maze.getMaze(row, column + 1) == 0)
                if(maze.getMaze(row - 1, column + 1) == 0){
                    AState newState = new MazeState(row - 1, column + 1);
                    newState.setCost(15);
                    tmpList.add(newState);
                }

        // Up Left
        if(row - 1 > -1 && column - 1 > -1) // It is Ok
            if(maze.getMaze(row - 1, column) == 0 || maze.getMaze(row, column - 1) == 0)
                if(maze.getMaze(row - 1, column - 1) == 0){
                    AState newState = new MazeState(row - 1, column - 1);
                    newState.setCost(15);
                    tmpList.add(newState);
                }

        // Down Right
        if(row + 1 < maxRox &&  column + 1 < maxColumn) // It is Ok
            if(maze.getMaze(row + 1, column) == 0 || maze.getMaze(row, column + 1) == 0)
                if(maze.getMaze(row + 1, column + 1) == 0){
                    AState newState = new MazeState(row + 1, column + 1);
                    newState.setCost(15);
                    tmpList.add(newState);
                }

        // Down Left
        if(row + 1 < maxRox && column  - 1 > -1) // It is Ok
            if(maze.getMaze(row + 1, column) == 0 || maze.getMaze(row, column - 1) == 0)
                if(maze.getMaze(row + 1, column - 1) == 0){
                    AState newState = new MazeState(row + 1, column - 1);
                    newState.setCost(15);
                    tmpList.add(newState);
                }

        return tmpList;
    }
}
