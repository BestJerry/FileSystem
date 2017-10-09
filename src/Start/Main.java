package Start;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        BorderPane root = FXMLLoader.load(getClass().getResource("../View/MainView.fxml"));
        primaryStage.setTitle("FileSystem");
        primaryStage.setScene(new Scene(root, 1024, 640));
        primaryStage.setResizable(false);

        HBox topmenu = FXMLLoader.load(getClass().getResource("/View/TopMenu.fxml"));
        VBox leftView = FXMLLoader.load(getClass().getResource("/View/LeftView.fxml"));
        ScrollPane centerView = FXMLLoader.load(getClass().getResource("/View/CenterView.fxml"));
        VBox rightView = FXMLLoader.load(getClass().getResource("/View/RightView.fxml"));
        root.setTop(topmenu);
        root.setLeft(leftView);
        root.setCenter(centerView);
        root.setRight(rightView);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
