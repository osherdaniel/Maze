package View;

import ViewModel.MyViewModel;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class WinningController {
    private MyViewModel viewModel;
    private MyViewController viewController;

    public void setViewController(MyViewController viewController) {
        this.viewController = viewController;
    }

    public javafx.scene.control.Button newMazeButton;
    public javafx.scene.control.Button exitButton;

    @FXML
    private ImageView winningImage;

    public void setViewModel(MyViewModel viewModel){
        this.viewModel = viewModel;
    }

    public void newMazeSelected(ActionEvent actionEvent) {
        viewController.setNewGame(true);
        Stage stage = (Stage) newMazeButton.getScene().getWindow();
        stage.close();
    }

    public void exitButtonSelected(ActionEvent actionEvent) {
        viewModel.stopServers();
        Stage stage = (Stage) newMazeButton.getScene().getWindow();
        stage.close();
        Platform.exit();
    }

    public void initialize() {
        winningImage.setImage(new Image("/Images/Winning.jpg"));
    }
}
