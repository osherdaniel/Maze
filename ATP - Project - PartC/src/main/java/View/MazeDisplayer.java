
package View;

import javafx.beans.property.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;


public class MazeDisplayer extends Canvas {

    private int[][] maze;

    private int characterPositionRow;
    private int characterPositionColumn;

    private int goalPositionRow;
    private int goalPositionColumn;

    private Image characterImage;

    StringProperty imageFileNameWall = new SimpleStringProperty();
    StringProperty imageFileNamePlayer = new SimpleStringProperty();

    public void setMaze(int[][] maze) {
        this.maze = maze;
        draw();
    }

    public void setSolution(int[][] maze, ArrayList<Integer> rowsSolution, ArrayList<Integer> columnsSolution){
        this.maze = maze;
        drawSolution(rowsSolution, columnsSolution);
    }

    public void setCharacterPosition(int row, int column) {
        characterPositionRow = row;
        characterPositionColumn = column;
        draw();
    }

    public void setGoalPoint (int row, int column) {
        goalPositionRow = row;
        goalPositionColumn = column;
    }

    public int getCharacterPositionRow() {
        return characterPositionRow;
    }

    public int getCharacterPositionColumn() {
        return characterPositionColumn;
    }

    public void draw() {
        if (maze != null) {
            double canvasHeight = getWidth();
            double canvasWidth = getHeight();
            double cellHeight = canvasHeight / maze[0].length;
            double cellWidth = canvasWidth / maze.length;

            try {
                Image wallImage = new Image(new FileInputStream(imageFileNameWall.get()));

                if(characterImage == null)
                    characterImage = new Image(new FileInputStream(imageFileNamePlayer.get()));

                GraphicsContext gc = getGraphicsContext2D();
                gc.clearRect(0, 0, getWidth(), getHeight());

                //Draw Maze
                for (int i = 0; i < maze.length; i++) {
                    for (int j = 0; j < maze[i].length; j++) {
                        if (maze[i][j] == 1)
                            gc.drawImage(wallImage,  j * cellHeight, i * cellWidth, cellHeight, cellWidth);
                    }
                }

                Image goalPointImage;

                try{
                    if(characterPositionColumn == goalPositionColumn && characterPositionRow == goalPositionRow)
                        goalPointImage = new Image(new FileInputStream("./resources/Images/GoalChange.jpg"));
                    else {
                        goalPointImage = new Image(new FileInputStream("./resources/Images/Goal.jpg"));
                        gc.drawImage(characterImage, characterPositionColumn * cellHeight, characterPositionRow * cellWidth, cellHeight, cellWidth);
                    }

                    gc.drawImage(goalPointImage, goalPositionColumn * cellHeight, goalPositionRow * cellWidth, cellHeight, cellWidth);
                }
                catch (FileNotFoundException e) {
                }
            }
            catch (FileNotFoundException e) {
            }
        }
    }

    public void drawSolution(ArrayList<Integer> rowsSolution, ArrayList<Integer> columnsSolution)  {
        if (maze != null) {
            double canvasHeight = getWidth();
            double canvasWidth = getHeight();
            double cellHeight = canvasHeight / maze[0].length;
            double cellWidth = canvasWidth / maze.length;

            draw();

            try {
                Image roadImage = new Image(new FileInputStream("./resources/Images/goldbar.jpg"));

                GraphicsContext gc = getGraphicsContext2D();

                for(int i = 1; i < rowsSolution.size(); i++){
                    int rowValue = rowsSolution.get(i);
                    int columnValue = columnsSolution.get(i);

                    if(rowValue == characterPositionRow && columnValue == characterPositionColumn){
                        rowsSolution.remove(i);
                        columnsSolution.remove(i);
                    }
                }

                for (int i = 1; i < rowsSolution.size() - 1; i++)
                    gc.drawImage(roadImage, columnsSolution.get(i)* cellHeight, rowsSolution.get(i) * cellWidth, cellHeight, cellWidth);

                gc.drawImage(characterImage, characterPositionColumn * cellHeight, characterPositionRow * cellWidth, cellHeight, cellWidth);

            }
            catch (FileNotFoundException e) {
            }
        }
    }

    public void drawHint(int rowSolution, int columnSolution){
        if (maze != null) {
            double canvasHeight = getWidth();
            double canvasWidth = getHeight();
            double cellHeight = canvasHeight / maze[0].length;
            double cellWidth = canvasWidth / maze.length;

            draw();

            try {
                Image roadImage = new Image(new FileInputStream("./resources/Images/goldbar.jpg"));

                GraphicsContext gc = getGraphicsContext2D();

                gc.drawImage(roadImage, columnSolution * cellHeight, rowSolution * cellWidth, cellHeight, cellWidth);
            }
            catch (FileNotFoundException e) {
            }
        }
    }

    public void SetCharacter(String CharacterName) throws FileNotFoundException {
        if(CharacterName.equals("Tokyo"))
            characterImage = new Image(new FileInputStream("./resources/Images/tokio.jpg"));
        else if(CharacterName.equals("Denver"))
            characterImage = new Image(new FileInputStream("./resources/Images/denver.jpg"));
        else if(CharacterName.equals("Nayrobi"))
            characterImage = new Image(new FileInputStream("./resources/Images/nayrobi.jpg"));
        else if(CharacterName.equals("Ryo"))
            characterImage = new Image(new FileInputStream("./resources/Images/rio.jpg"));
        else if(CharacterName.equals("Professor"))
            characterImage = new Image(new FileInputStream("./resources/Images/Professor.jpg"));
        else
            characterImage = new Image(new FileInputStream(imageFileNamePlayer.get()));
    }

    public String getImageFileNameWall() {
        return imageFileNameWall.get();
    }
    public String getImageFileNamePlayer() {
        return imageFileNamePlayer.get();
    }
    public void setImageFileNameWall(String imageFileNameWall) {
        this.imageFileNameWall.set(imageFileNameWall);
    }
    public void setImageFileNamePlayer(String imageFileNamePlayer) {
        this.imageFileNamePlayer.set(imageFileNamePlayer);
    }
}
