package View;

import ViewModel.MyViewModel;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Observable;
import java.util.Observer;

public class MyViewController<override> implements IView, Observer {
    public static int counter = 1;

    private MyViewModel viewModel;
    private boolean ctrlKey = false;


    private boolean newGame = false;

    @FXML
    private MazeDisplayer myMazeDisplayer;

    @FXML
    private ImageView logoImage;


    public javafx.scene.control.TextField rowsNum;
    public javafx.scene.control.TextField columnsNum;
    public javafx.scene.control.Button generateMazeButton;
    public javafx.scene.control.Button solveMazeButton;
    public javafx.scene.control.Button helpButton;
    public javafx.scene.control.Label selectCharacterLabel;
    public javafx.scene.control.ComboBox selectCharactersCB;

    public StringProperty characterPositionRow = new SimpleStringProperty("");
    public StringProperty characterPositionColumn = new SimpleStringProperty("");

    private MediaPlayer mediaPlayer;

    public void setViewModel(MyViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o == viewModel) {
            DisplayMaze(viewModel.GetMaze());
            generateMazeButton.setDisable(false);

            if (viewModel.isGameOver()) {
                playMusic(false);

                try {
                    Stage stage = new Stage();
                    stage.setTitle("Winning");
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    Parent root = fxmlLoader.load(getClass().getResource("Winning.fxml").openStream());
                    Scene scene = new Scene(root, 500, 500);
                    stage.setScene(scene);
                    stage.initModality(Modality.APPLICATION_MODAL);

                    WinningController view = fxmlLoader.getController();
                    view.setViewModel(viewModel);
                    view.setViewController(this);

                    stage.showAndWait();
                    if(newGame) {
                        generateNewMaze();
                        newGame = false;
                    }

                } catch (Exception e) {
                    viewModel.addToLog("Scene open failed");
                }
            }
        }
    }

    @Override
    public void DisplayMaze(int[][] maze) {
        int characterPositionRow = viewModel.GetCharacterPositionRow();
        int characterPositionColumn = viewModel.GetCharacterPositionColumn();
        myMazeDisplayer.setCharacterPosition(characterPositionRow, characterPositionColumn);

        this.characterPositionRow.set(characterPositionRow + "");
        this.characterPositionColumn.set(characterPositionColumn + "");

        if (viewModel.isSolvedMaze())
            myMazeDisplayer.drawSolution(viewModel.getRowsSolution(), viewModel.getColumnsSolution());

        else if (viewModel.isHint()) {
            myMazeDisplayer.drawHint(viewModel.getRowsSolution().get(1), viewModel.getColumnsSolution().get(1));
            viewModel.setHint(false);
        }
        else {
            myMazeDisplayer.setGoalPoint(viewModel.getGoalPoint()[0], viewModel.getGoalPoint()[1]);
            myMazeDisplayer.setMaze(maze);
        }
    }

    public void generateMaze(ActionEvent actionEvent) {
        generateNewMaze();
    }
    private void generateNewMaze() {
        try {
            int Height = Integer.valueOf(rowsNum.getText());
            int Width = Integer.valueOf(columnsNum.getText());

            if (Height > 1 && Width > 1) {
                viewModel.setSolvedMaze(false);

                viewModel.GenerateMaze(Width, Height);

                playMusic(true);

                solveMazeButton.setVisible(true);
                selectCharacterLabel.setVisible(true);
                selectCharactersCB.setVisible(true);
                helpButton.setVisible(true);

                generateMazeButton.setVisible(false);
            } else {
                Alert lessThanOne = new Alert(Alert.AlertType.CONFIRMATION, "Please enter numbers that bigger than One", ButtonType.OK);
                lessThanOne.showAndWait();
                viewModel.addToLog("The user enter a number that smaller than one");
            }
        } catch (Exception e) {
            Alert invalidparameters = new Alert(Alert.AlertType.CONFIRMATION, "The parameters entered are incorrect. Please enter numbers only that bigger than One", ButtonType.OK);
            invalidparameters.showAndWait();
            viewModel.addToLog("The user entered an invalid character");
        }
    }

    public void solveMaze(ActionEvent actionEvent) {
        viewModel.SolveMaze();
        helpButton.setVisible(false);
    }

    // Files Options
    public void newFileSelected(ActionEvent actionEvent) {
        generateNewMaze();
    }
    public void saveFileSelected(ActionEvent actionEvent) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save the current Maze");
        fileChooser.setInitialFileName("Maze number " + counter);

        counter = counter + 1;

        File file = new File("./Save Mazes/");
        if (!file.exists())
            file.mkdir();

        fileChooser.setInitialDirectory(file);

        File destination = fileChooser.showSaveDialog(myMazeDisplayer.getScene().getWindow());
        if (destination != null)
            viewModel.SaveFile(destination);

    }
    public void loadFileSelected(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load a Maze");

        File file = new File("./Save Mazes/");
        if (!file.exists())
            file = new File("./");

        fileChooser.setInitialDirectory(file);
        File destination = fileChooser.showOpenDialog(myMazeDisplayer.getScene().getWindow());
        if (destination != null)
            viewModel.LoadFile(destination);
    }

    // Menus Options
    public void helpSelected(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            stage.setTitle("Help");
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("Help.fxml").openStream());
            Scene scene = new Scene(root, 370, 300);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (Exception e) {

        }
    }
    public void aboutSelected(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            stage.setTitle("About");
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("About.fxml").openStream());
            Scene scene = new Scene(root, 450, 200);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

            OptionController view = fxmlLoader.getController();
            view.setViewModel(viewModel);
        } catch (Exception e) {

        }
    }
    public void exitSelected(ActionEvent actionEvent) {
        viewModel.stopServers();
        Platform.exit();
    }
    public void propertiesButtonSelected(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            stage.setTitle("Properties");
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("Options.fxml").openStream());
            Scene scene = new Scene(root, 600, 400);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

            OptionController view = fxmlLoader.getController();
            view.setViewModel(viewModel);

        } catch (Exception e) {

        }
    }

    // My Features
    public void initialize() {
        logoImage.setImage(new Image("/Images/Logo.jpg"));
    }
    public void helpMeButtonSelected(ActionEvent actionEvent) {
        viewModel.Hint();
    }
    public void changeCharacter(ActionEvent dragEvent) throws FileNotFoundException {
        myMazeDisplayer.SetCharacter(selectCharactersCB.getValue().toString());
        DisplayMaze(viewModel.GetMaze());
    }

    // Work Requirements
    public void KeyPressed(KeyEvent keyEvent) {
        if (keyEvent.isControlDown())
            ctrlKey = true;

        else if (keyEvent.getCode().equals(KeyCode.ENTER))
            generateNewMaze();

        else {
            viewModel.moveCharacter(keyEvent.getCode());
            keyEvent.consume();
        }

    }
    public void KeyReleasedEvent(KeyEvent keyEvent) {
        if (!keyEvent.isControlDown())
            ctrlKey = false;
    }
    public void mouseClicked(MouseEvent mouseEvent) {
        myMazeDisplayer.requestFocus();
    }
    public void playMusic(boolean flag) {
        if (mediaPlayer != null)
            mediaPlayer.stop();
        String Path = null;
        if (flag)
            Path = "resources\\Song.mp3";
        else {
            Path = "resources\\win.mp3";
            mediaPlayer.stop();
        }

        Media media = new Media(Paths.get(Path).toUri().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }
    public void setResizeEvent(Scene scene) {
        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                myMazeDisplayer.draw();
            }
        });
        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                myMazeDisplayer.draw();
            }
        });
    }
    public void onScrollEvent(ScrollEvent scrollEvent) {
        if (ctrlKey) {
            double zoomFactor = 1.05;
            double deltaY = scrollEvent.getDeltaY();
            if (deltaY < 0) {
                zoomFactor = 2.0 - zoomFactor;
            }

            double newX = myMazeDisplayer.getScaleX() * zoomFactor;
            double newY = myMazeDisplayer.getScaleY() * zoomFactor;

            myMazeDisplayer.setScaleX(newX);
            myMazeDisplayer.setScaleY(newY);
        }
    }


    public void setNewGame(boolean newGame) {
        this.newGame = newGame;
    }
}


