package algorithms.mazeGenerators;
import com.sun.rowset.internal.Row;

import java.util.*;

public class MyMazeGenerator extends AMazeGenerator {

    private static boolean found = false;
    private static Stack<Position> stack;
    private static Maze myMaze;
    /**
     * Create Maze by DFS Algorithm
     * @param Rows
     * @param Columns
     * @return A new maze
     */

    @Override
    public Maze generate(int Rows, int Columns) {
        myMaze = new Maze(Rows,Columns);
        for (int i = 0; i < Rows; i++) {
            for (int j = 0; j < Columns; j++)
                myMaze.setMaze(1, i, j);
        }

        myMaze.setStartPosition();

        stack = new Stack<>();
        stack.push(myMaze.getStartPosition());

        while(!stack.isEmpty()){
            Integer[] Directions = generateRandomDirections();

            found = false;
            for (int i = 0; i < Directions.length; i++) {
                int peekRow = stack.peek().getRowIndex();
                int peekColumn = stack.peek().getColumnIndex();

                switch (Directions[i]) {
                    // Up
                    case 1:
                        if (stack.peek().getRowIndex() - 2 < 0)
                            continue;
                        casesFunction(peekRow, peekColumn, -2, -1, 0, 0);
                        break;

                    // Right
                    case 2:
                        if (peekColumn + 2 > Columns - 1)
                            continue;
                        casesFunction(peekRow, peekColumn, 0, 0,2, 1);
                        break;

                    // Down
                    case 3:
                        if (peekRow + 2 > Rows - 1)
                            continue;
                        casesFunction(peekRow, peekColumn, 2, 1, 0, 0);
                        break;

                    // Left
                    case 4:
                        if (peekColumn - 2 < 0)
                            continue;
                        casesFunction(peekRow, peekColumn, 0, 0, -2, -1);
                        break;
                }
            }
            if(found == false)
                stack.pop();
        }
        myMaze.setGoalPosition();
        return myMaze;
    }

    /**
     * Get all the possible direction
     * @return - array of integer
     */
    private Integer[] generateRandomDirections() {
        ArrayList<Integer> randoms = new ArrayList<Integer>();
        for (int i = 0; i < 4; i++)
            randoms.add(i + 1);
        Collections.shuffle(randoms);

        return randoms.toArray(new Integer[4]);
    }

    /**
     * Create the maze - The possibles directions
     * @param indexRow
     * @param indexColumn
     * @param firstRowAddition
     * @param secondRowAddition
     * @param firstColumnAddition
     * @param secondColumnAddition
     */
    private void casesFunction(int indexRow, int indexColumn, int firstRowAddition, int secondRowAddition, int firstColumnAddition, int secondColumnAddition){
        if (myMaze.getMaze(indexRow + firstRowAddition, indexColumn + firstColumnAddition) != 0) {
            found = true;

            myMaze.setMaze(0, indexRow + firstRowAddition, indexColumn + firstColumnAddition);
            myMaze.setMaze(0, indexRow + secondRowAddition, indexColumn + secondColumnAddition);

            stack.push(new Position(indexRow + firstRowAddition, indexColumn + firstColumnAddition));
        }
    }
}