package Start;

import Controller.CenterViewCtr;
import Controller.LeftViewCtr;
import Controller.TopMenuCtr;
import Utility.ReadAndWrite;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lhw.Test.ModDialog;
import lhw.left.FileIO;
import lhw.left.Folder;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;

public class Main extends Application {

    private Folder folder = ReadAndWrite.root;

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root = FXMLLoader.load(getClass().getResource("../View/MainView.fxml"));

        primaryStage.setTitle("FileSystem");
        primaryStage.setScene(new Scene(root, 1024, 640));
        primaryStage.setResizable(false);

        URL location = getClass().getResource("/View/TopMenu.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        HBox topMenu = fxmlLoader.load(location.openStream());
        TopMenuCtr topMenuCtr = fxmlLoader.getController();
        topMenuCtr.setText(folder.getPath());

        URL location_two = getClass().getResource("/View/LeftView.fxml");
        FXMLLoader fxmlLoader_two = new FXMLLoader();
        fxmlLoader_two.setLocation(location_two);
        fxmlLoader_two.setBuilderFactory(new JavaFXBuilderFactory());
        VBox leftView = fxmlLoader_two.load(location_two.openStream());
        LeftViewCtr leftViewCtr = fxmlLoader_two.getController();
        leftViewCtr.setFolder(folder);
        leftViewCtr.init();

        URL location_three = getClass().getResource("/View/CenterView.fxml");
        FXMLLoader fxmlLoader_three = new FXMLLoader();
        fxmlLoader_three.setLocation(location_three);
        fxmlLoader_three.setBuilderFactory(new JavaFXBuilderFactory());
        ScrollPane centerView = fxmlLoader_three.load(location_three.openStream());
        CenterViewCtr centerViewCtr = fxmlLoader_three.getController();
        centerViewCtr.setFolder(folder);
        centerViewCtr.init();


        VBox rightView = FXMLLoader.load(getClass().getResource("/View/RightView.fxml"));
        root.setTop(topMenu);
        root.setLeft(leftView);
        root.setCenter(centerView);
        root.setRight(rightView);

        primaryStage.show();
        /**
         * ±£´æÐÞ¸Ä
         */
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                ReadAndWrite.save();
            }
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
