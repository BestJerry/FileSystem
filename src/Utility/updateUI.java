package Utility;

import Controller.CenterViewCtr;
import Controller.LeftViewCtr;
import Controller.TopMenuCtr;
import Model.Folder;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;

public class updateUI {

    /**
     * 更新界面上部
     * @param root
     * @param folder
     * @param c
     * @throws IOException
     */
    public static void updateTopMenu(BorderPane root,Folder folder,Class c) throws IOException {
        URL location = c.getResource("/resources/TopMenu.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        HBox topMenu = fxmlLoader.load(location.openStream());
        TopMenuCtr topMenuCtr = fxmlLoader.getController();
        topMenuCtr.setPath(folder.getPath());
        root.setTop(topMenu);
    }

    public static void updateLeftView(BorderPane root,Folder folder,Class c) throws IOException {
        URL location = c.getResource("/resources/LeftView.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        TreeView leftView = fxmlLoader.load(location.openStream());
        LeftViewCtr leftViewCtr = fxmlLoader.getController();
        leftViewCtr.setFolder(folder);
        leftViewCtr.init();
        root.setLeft(leftView);

    }

    public static void updateRightView(BorderPane root,Class c) throws IOException {
        TableView rightView = FXMLLoader.load(c.getResource("/resources/RightView.fxml"));
        root.setRight(rightView);
    }

    public static void updateCenterView(BorderPane root,Folder folder,Class c) throws IOException {
        URL location = c.getResource("/resources/CenterView.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        ScrollPane centerView = fxmlLoader.load(location.openStream());
        CenterViewCtr centerViewCtr = fxmlLoader.getController();
        centerViewCtr.setFolder(folder);
        centerViewCtr.init();
        root.setCenter(centerView);
    }

}
