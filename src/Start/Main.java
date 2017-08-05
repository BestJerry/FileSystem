package Start;

import com.sun.javafx.font.freetype.HBGlyphLayout;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.*;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        BorderPane root = FXMLLoader.load(getClass().getResource("../View/Main.fxml"));
        primaryStage.setTitle("模拟磁盘文件系统");
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
