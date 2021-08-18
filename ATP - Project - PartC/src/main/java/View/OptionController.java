package View;

import ViewModel.MyViewModel;
import javafx.event.ActionEvent;
import javafx.scene.SnapshotResult;
import javafx.scene.control.Button;
import javafx.stage.Stage;


import java.io.*;

public class OptionController {
    private MyViewModel viewModel;

    public javafx.scene.control.ChoiceBox typeMazeCB;
    public javafx.scene.control.ChoiceBox solvingMethodCB;
    public javafx.scene.control.ChoiceBox numberThreadCB;
    public javafx.scene.control.Button saveButton;

    public void setViewModel(MyViewModel viewModel){
        this.viewModel = viewModel;
    }

    public void savePropreties(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        String typeMaze = typeMazeCB.getValue().toString();
        String solvingMethod = solvingMethodCB.getValue().toString();
        String numberOfThreads = numberThreadCB.getValue().toString();

        typeMaze = typeMaze.replaceAll("\\s", "");
        solvingMethod = solvingMethod.replaceAll("\\s", "");

        viewModel.SaveProperties(typeMaze, solvingMethod, numberOfThreads);

        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }
}
