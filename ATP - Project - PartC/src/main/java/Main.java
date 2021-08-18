import Model.IModel;
import Model.MyModel;
import View.MyViewController;
import ViewModel.MyViewModel;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Optional;

//import MyViewModel;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        IModel model = new MyModel();
        model.startServers();
        MyViewModel viewModel = new MyViewModel(model);
        //--------------
        primaryStage.setTitle("La Casa De Papel Maze!");
        FXMLLoader fxmlLoader = new FXMLLoader();

        //Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("View/MyView.fxml"));
        Parent root = fxmlLoader.load(getClass().getResource("/View/MyView.fxml").openStream());
        Scene scene = new Scene(root, 850, 600);
        scene.getStylesheets().add(getClass().getResource("/View/MainStyle.css").toExternalForm());

        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(850);

        //--------------
        MyViewController view = fxmlLoader.getController();
        view.setResizeEvent(scene);
        view.setViewModel(viewModel);

        viewModel.addObserver(view);
        primaryStage.setScene(scene);


        //--------------
        SetStageCloseEvent(primaryStage, model);
        primaryStage.show();

    }
    private void SetStageCloseEvent(Stage primaryStage, IModel model) {
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent windowEvent) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to exit?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    model.stopServers();
                    primaryStage.close();
                    Platform.exit();
                } else {
                    windowEvent.consume();

                }
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
