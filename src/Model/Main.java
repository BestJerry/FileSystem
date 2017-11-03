package Model;

import Controller.CenterViewCtr;
import Controller.LeftViewCtr;
import Controller.TopMenuCtr;
import Utility.ReadAndWrite;
import Utility.updateUI;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URL;

public class Main extends Application {

    private Folder folder = ReadAndWrite.root;

    private static boolean show_hide = false;

    public static boolean isShow_hide() {
        return show_hide;
    }

    public static void setShow_hide(boolean show_hide) {
        Main.show_hide = show_hide;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root = FXMLLoader.load(getClass().getResource("/resources/MainView.fxml"));

        primaryStage.setTitle("FileSystem");
        primaryStage.setScene(new Scene(root, 1024, 640));
        primaryStage.setResizable(false);

        updateUI.updateTopMenu(root,folder,getClass());
        updateUI.updateCenterView(root,folder,getClass());
        updateUI.updateLeftView(root,folder,getClass());
        updateUI.updateRightView(root,getClass());

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
