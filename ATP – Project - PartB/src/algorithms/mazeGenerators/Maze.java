package algorithms.mazeGenerators;

import java.util.Arrays;
import java.util.Random;
import java.io.Serializable;
import java.util.stream.Collectors;

public class Maze implements Serializable {
    private int Rows;
    private int Columns;
    private  int[][] myMaze;
    private Position StartPosition;
    private Position GoalPosition;

    public Maze(int Rows, int Columns) {
        if(Rows < 0 || Columns < 0)
            return;

        this.Rows = Rows;
        this.Columns = Columns;
        myMaze = new int[Rows][Columns];
    }

    public Position getStartPosition() {
        return StartPosition;
    }

    /**
     * Set the start position of the maze
     */
    public void setStartPosition() {
        Random rand = new Random();
        int startRow = rand.nextInt(Rows);
        int startColumn = rand.nextInt(Columns);
        StartPosition = new Position(startRow, startColumn);
        myMaze[startRow][startColumn] = 0;
    }

    /**
     * Set the goal position of the maze
     */
    public void setGoalPosition() {
        Random rand = new Random();
        int goalRow = rand.nextInt(Rows);
        int goalColumn = rand.nextInt(Columns);

        boolean foundZero = false;
        if(Rows < 4 && Columns < 4){
            while(goalRow == StartPosition.getRowIndex() && goalColumn == StartPosition.getColumnIndex()){
                goalRow = rand.nextInt(Rows);
                goalColumn = rand.nextInt(Columns);
            }
            for(int row = 0; row < Rows; row++)
                for(int column = 0; column < Columns; column++)
                    if(myMaze[row][column] == 0 && row != StartPosition.getRowIndex() && column != StartPosition.getColumnIndex()) {
                        foundZero = true;
                        break;
                    }

            if(!foundZero) {
                GoalPosition = new Position(goalRow, goalColumn);
                myMaze[GoalPosition.getRowIndex()][GoalPosition.getColumnIndex()] = 0;
                CreatePath();
            }
        }
        if(foundZero || (Rows > 3 || Columns > 3)){
            int value = myMaze[goalRow][goalColumn];

            while (value != 0 || (goalRow == StartPosition.getRowIndex() && goalColumn == StartPosition.getColumnIndex())) {
                goalRow = rand.nextInt(Rows);
                goalColumn = rand.nextInt(Columns);
                value = myMaze[goalRow][goalColumn];
            }
            GoalPosition = new Position(goalRow, goalColumn);
        }
    }

    private void CreatePath(){
        int rowDif = GoalPosition.getRowIndex() - StartPosition.getRowIndex();
        int columnDif = GoalPosition.getColumnIndex() - StartPosition.getColumnIndex();

        //Down
        if(rowDif >= 0) {
            for (int j = 1; j < rowDif + 1; j++) {
                if (StartPosition.getRowIndex() + j < Rows)
                    myMaze[StartPosition.getRowIndex() + j][StartPosition.getColumnIndex()] = 0;
            }
        }
        // Up
        else {
            rowDif = -rowDif;
            for (int j = 1; j < rowDif + 1; j++) {
                if (StartPosition.getRowIndex() - j >= 0)
                    myMaze[StartPosition.getRowIndex() - j][StartPosition.getColumnIndex()] = 0;
            }
        }
        // Right
        if(columnDif >= 0) {
            for (int j = 1; j < columnDif + 1; j++) {
                if(StartPosition.getRowIndex() + j < Rows && StartPosition.getColumnIndex() + j < Columns)
                    myMaze[StartPosition.getRowIndex() + rowDif][StartPosition.getColumnIndex() + j] = 0;
            }
        }
        //Left
        else {
            columnDif = -columnDif;
            for (int j = 1; j < columnDif + 1; j++) {
                if(StartPosition.getRowIndex() + j < Rows && StartPosition.getColumnIndex()  - j >= 0)
                    myMaze[StartPosition.getRowIndex() + rowDif][StartPosition.getColumnIndex() - j] = 0;
            }
        }
    }

    /**
     * Return the goal position of the maze
     * @return The goal postion
     */
    public Position getGoalPosition() {
        return GoalPosition;
    }

    /**
     * The number of rows in the maze
     * @return The rows number
     */
    public int getRows() {
        return Rows;
    }

    /**
     * The number of columns in the maze
     * @return The columns number
     */
    public int getColumns() {
        return Columns;
    }

    /**
     * Return the value of the maze in the index we got
     * @param row
     * @param column
     * @return value of the maze
     */
    public int getMaze(int row, int column) {
        if(row > -1 && row <  getRows() && column > -1 && column <  getColumns())
            return myMaze[row][column];
        else
            return -1;
    }

    /**
     * Change the value of the maze in the index we got
     * @param value
     * @param row
     * @param column
     */
    public void setMaze(int value, int row, int column) {
        if(row > -1 && row <  getRows() && column > -1 && column <  getColumns())
            myMaze[row][column] = value;
    }

    /**
     * Print the maze
     */
    public void print(){
        for(int i = 0; i < Rows; i++) {
            for (int j = 0; j < Columns; j++) {
                if(i == StartPosition.getRowIndex() && j == StartPosition.getColumnIndex())
                    System.out.print("S");
                else if(i == GoalPosition.getRowIndex() && j == GoalPosition.getColumnIndex())
                    System.out.print("E");
                else
                    System.out.print(myMaze[i][j]);
            }
            System.out.println("");
        }
    }

    /* Part B */
    public Maze(byte[] byteMaze) {
        Rows = Byte.toUnsignedInt(byteMaze[0]) * 256 + Byte.toUnsignedInt(byteMaze[1]);
        Columns = Byte.toUnsignedInt(byteMaze[2]) * 256 + Byte.toUnsignedInt(byteMaze[3]);
        StartPosition = new Position(Byte.toUnsignedInt(byteMaze[4]) * 256 + Byte.toUnsignedInt(byteMaze[5]), Byte.toUnsignedInt(byteMaze[6]) * 256 + Byte.toUnsignedInt(byteMaze[7]));
        GoalPosition = new Position(Byte.toUnsignedInt(byteMaze[8]) * 256 + Byte.toUnsignedInt(byteMaze[9]), Byte.toUnsignedInt(byteMaze[10]) * 256 + Byte.toUnsignedInt(byteMaze[11]));

        myMaze = new int[Rows][Columns];
        int index = 12;
        for(int row = 0; row < Rows; row++){
            for(int column = 0; column < Columns; column++){
                myMaze[row][column] = (int) byteMaze[index];
                index++;
            }
        }
    }

    public byte[] toByteArray(){
        byte[] bytesArray = new byte[Rows * Columns + 12];

        // Rows
        bytesArray[0] = (byte) (Rows / 256);
        bytesArray[1] = (byte) (Rows % 256);

        // Columns
        bytesArray[2] = (byte) (Columns / 256);
        bytesArray[3] = (byte) (Columns % 256);

        //StartPosition - Rows
        bytesArray[4] = (byte) (getStartPosition().getRowIndex() / 256);
        bytesArray[5] = (byte) (getStartPosition().getRowIndex() % 256);

        //StartPosition - Column
        bytesArray[6] = (byte) (getStartPosition().getColumnIndex() / 256);
        bytesArray[7] = (byte) (getStartPosition().getColumnIndex() % 256);

        //GoalPosition - Rows
        bytesArray[8] = (byte) (getGoalPosition().getRowIndex() / 256);
        bytesArray[9] = (byte) (getGoalPosition().getRowIndex() % 256);

        //GoalPosition - Columns
        bytesArray[10] = (byte) (getGoalPosition().getColumnIndex() / 256);
        bytesArray[11] = (byte) (getGoalPosition().getColumnIndex() % 256);

        int index = 12;
        for(int row = 0; row < Rows; row++){
            for(int column = 0; column < Columns; column++){
                bytesArray[index] = (byte) myMaze[row][column];
                index++;
            }
        }
        return bytesArray;
    }

    public String toString() {
        int [][] tmp = myMaze;
        String S = Arrays.stream(tmp).flatMapToInt(Arrays::stream).mapToObj(String::valueOf).collect(Collectors.joining(" "));
        S = S + StartPosition.toString() + " " + GoalPosition.toString();
        return S;
    }

}
