package algorithms.mazeGenerators;

public class EmptyMazeGenerator extends  AMazeGenerator{
    public EmptyMazeGenerator() {
    }

    /**
     * Create EMPTHY Maze
     * @param rows
     * @param columns
     * @return A new maze
     */
    @Override
    public Maze generate(int rows, int columns) {
        Maze myMaze = new Maze(rows,columns);
        myMaze.setStartPosition();
        myMaze.setGoalPosition();
        return myMaze;
    }
}
