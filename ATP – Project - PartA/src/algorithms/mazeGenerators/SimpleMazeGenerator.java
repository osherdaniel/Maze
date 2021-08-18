package algorithms.mazeGenerators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class SimpleMazeGenerator extends AMazeGenerator{
    private Maze myMaze;

    private static int indexRow;
    private static int indexColumn;

    private static int goalRow;
    private static int goalColumn;

    private static int rowDif;
    private static int columnDif ;

    public SimpleMazeGenerator() {
    }

    /**
     * Create Maze by RANDOM
     * @param rows
     * @param columns
     * @return A new maze
     */
    @Override
    public Maze generate(int rows, int columns) {
        myMaze = new Maze(rows, columns);

        myMaze.setStartPosition();
        myMaze.setGoalPosition();
        myMaze.setMaze(0, myMaze.getGoalPosition().getRowIndex(), myMaze.getGoalPosition().getColumnIndex());

        ArrayList<String> Path = CreatePath();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++)
                if(!Path.contains((new Position(i,j)).toString()))
                    myMaze.setMaze((int)Math.round(Math.random()), i , j);
        }
        return myMaze;
    }

    private ArrayList<String> CreatePath(){

        for (int i = 0; i < myMaze.getRows(); i++) {
            for (int j = 0; j < myMaze.getColumns(); j++)
                myMaze.setMaze(1, i, j);
        }

        indexRow = myMaze.getStartPosition().getRowIndex();
        indexColumn = myMaze.getStartPosition().getColumnIndex();

        goalRow = myMaze.getGoalPosition().getRowIndex();
        goalColumn = myMaze.getGoalPosition().getColumnIndex();

        rowDif = goalRow - indexRow ;
        columnDif = goalColumn - indexColumn;

        ArrayList<String> Path;
        if(rowDif >= 0) {
            if (columnDif >= 0)
                Path = DownRight();
            else
                Path = DownLeft();
        }
        else {
            if (columnDif >= 0)
                Path = UpRight();
            else
                Path = UpLeft();
        }
        return Path;
    }

    private ArrayList<String> DownLeft() {
        ArrayList<String> Path = new ArrayList<>();

        columnDif = -columnDif;
        int result;
        while (indexRow != goalRow || indexColumn != goalColumn) {
            Random r = new Random();

            // Down
            if (rowDif > 0) {
                if (rowDif == 1) {
                    result = 1;
                    Path.add((new Position(indexRow + result, indexColumn)).toString());
                    myMaze.setMaze(0, indexRow + result, indexColumn);
                }
                else {
                    result = r.nextInt(rowDif - 1) + 1;
                    if (indexRow + result < myMaze.getRows()) {
                        for (int j = 1; j < result + 1; j++) {
                            myMaze.setMaze(0, indexRow + j, indexColumn);
                            Path.add((new Position(indexRow + j, indexColumn)).toString());
                        }
                    }
                }
                rowDif = rowDif - result;
                indexRow = indexRow + result;
            }

            // Left
            if (columnDif > 0) {
                if (columnDif == 1) {
                    result = 1;
                    Path.add((new Position(indexRow, indexColumn - result)).toString());
                    myMaze.setMaze(0, indexRow, indexColumn - result);
                }
                else {
                    result = r.nextInt(columnDif - 1) + 1;
                    if (indexColumn + result < myMaze.getColumns()) {
                        for (int j = 1; j < result + 1; j++) {
                            myMaze.setMaze(0, indexRow, indexColumn - j);
                            Path.add((new Position(indexRow, indexColumn - j)).toString());
                        }
                    }
                }
                columnDif = columnDif - result;
                indexColumn = indexColumn - result;
            }
        }
        return Path;
    }

    private ArrayList<String> DownRight() {
        ArrayList<String> Path = new ArrayList<>();

        int result;
        while (indexRow != goalRow || indexColumn != goalColumn) {
            Random r = new Random();

            // Down
            if (rowDif > 0) {
                if (rowDif == 1) {
                    result = 1;
                    Path.add((new Position(indexRow + result, indexColumn)).toString());
                    myMaze.setMaze(0, indexRow + result, indexColumn);
                } else {
                    result = r.nextInt(rowDif - 1) + 1;
                    if (indexRow + result < myMaze.getRows()) {
                        for (int j = 1; j < result + 1; j++) {
                            myMaze.setMaze(0, indexRow + j, indexColumn);
                            Path.add((new Position(indexRow + j, indexColumn)).toString());
                        }
                    }
                }
                rowDif = rowDif - result;
                indexRow = indexRow + result;
            }

            // Right
            if (columnDif > 0) {
                if (columnDif == 1) {
                    result = 1;
                    Path.add((new Position(indexRow, indexColumn + result)).toString());
                    myMaze.setMaze(0, indexRow, indexColumn + result);
                } else {
                    result = r.nextInt(columnDif - 1) + 1;
                    if (indexColumn + result < myMaze.getColumns()) {
                        for (int j = 1; j < result + 1; j++) {
                            myMaze.setMaze(0, indexRow, indexColumn + j);
                            Path.add((new Position(indexRow, indexColumn + j)).toString());
                        }
                    }
                }
                columnDif = columnDif - result;
                indexColumn = indexColumn + result;
            }
        }
        return Path;
    }

    private ArrayList<String> UpLeft() {
        ArrayList<String> Path = new ArrayList<>();

        columnDif = -columnDif;
        rowDif = -rowDif;

        int result;
        while (indexRow != goalRow || indexColumn != goalColumn) {
            Random r = new Random();

            // Up
            if (rowDif > 0) {
                if (rowDif == 1) {
                    result = 1;
                    Path.add((new Position(indexRow - result, indexColumn)).toString());
                    myMaze.setMaze(0, indexRow - result, indexColumn);
                }
                else {
                    result = r.nextInt(rowDif - 1) + 1;
                    if (indexRow - result > 0) {
                        for (int j = 1; j < result + 1; j++) {
                            myMaze.setMaze(0, indexRow - j, indexColumn);
                            Path.add((new Position(indexRow - j, indexColumn)).toString());
                        }
                    }
                }
                rowDif = rowDif - result;
                indexRow = indexRow - result;
            }

            // Left
            if (columnDif > 0) {
                if (columnDif == 1) {
                    result = 1;
                    Path.add((new Position(indexRow, indexColumn - result)).toString());
                    myMaze.setMaze(0, indexRow, indexColumn - result);
                } else {
                    result = r.nextInt(columnDif - 1) + 1;
                    if (indexColumn + result < myMaze.getColumns()) {
                        for (int j = 1; j < result + 1; j++) {
                            myMaze.setMaze(0, indexRow, indexColumn - j);
                            Path.add((new Position(indexRow, indexColumn - j)).toString());
                        }
                    }
                }
                columnDif = columnDif - result;
                indexColumn = indexColumn - result;
            }
        }
        return Path;
    }

    private ArrayList<String> UpRight() {
        ArrayList<String> Path = new ArrayList<>();
        rowDif = -rowDif;

        int result;
        while (indexRow != goalRow || indexColumn != goalColumn) {
            Random r = new Random();

            // Up
            if (rowDif > 0) {
                if (rowDif == 1) {
                    result = 1;
                    Path.add((new Position(indexRow - result, indexColumn)).toString());
                    myMaze.setMaze(0, indexRow - result, indexColumn);
                }
                else {
                    result = r.nextInt(rowDif - 1) + 1;
                    if (indexRow - result > 0) {
                        for (int j = 1; j < result + 1; j++) {
                            myMaze.setMaze(0, indexRow - j, indexColumn);
                            Path.add((new Position(indexRow - j, indexColumn)).toString());
                        }
                    }
                }
                rowDif = rowDif - result;
                indexRow = indexRow - result;
            }

            // Right
            if (columnDif > 0) {
                if (columnDif == 1) {
                    result = 1;
                    Path.add((new Position(indexRow, indexColumn + result)).toString());
                    myMaze.setMaze(0, indexRow, indexColumn + result);
                } else {
                    result = r.nextInt(columnDif - 1) + 1;
                    if (indexColumn + result < myMaze.getColumns()) {
                        for (int j = 1; j < result + 1; j++) {
                            myMaze.setMaze(0, indexRow, indexColumn + j);
                            Path.add((new Position(indexRow, indexColumn + j)).toString());
                        }
                    }
                }
                columnDif = columnDif - result;
                indexColumn = indexColumn + result;
            }
        }
        return Path;
    }
}

