package Controller;

import com.sun.javafx.robot.impl.FXRobotHelper;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lhw.left.Attribute;
import lhw.left.Folder;
import lhw.left.TextFile;

import java.io.IOException;
import java.net.URL;
import java.util.EventListener;
import java.util.ResourceBundle;

public class SubFileOrFolderCtr implements Initializable {

    @FXML
    private ImageView imageview_picture;

    @FXML
    private Label label_name;

    @FXML
    private VBox vbox;



    private ContextMenu contextMenu;

    private Attribute attribute;

    private Folder folder;

    private FlowPane flowPane;

    private Node node;

    public void setNode(Node node) {
        this.node = node;
    }

    public void setFlowPane(FlowPane flowPane) {
        this.flowPane = flowPane;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }

    public void setImageview_picture(int code) {
        if (code == 1) {
            imageview_picture.setImage(new Image(getClass().getResourceAsStream("/Picture/documents.png")));
        } else if (code == 2) {
            imageview_picture.setImage(new Image(getClass().getResourceAsStream("/Picture/textfile.png")));

        }
    }

    public void setLabel_name(String name) {
        label_name.setText(name);

    }

    public void showMenu(MouseEvent mouseEvent) throws IOException {

        if (contextMenu != null && contextMenu.isShowing()) {
            contextMenu.hide();

        } else if (mouseEvent.getClickCount() >= 2) {
            if (attribute instanceof Folder) {
                URL location = getClass().getResource("/View/CenterView.fxml");
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(location);
                fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                ScrollPane scrollPane = fxmlLoader.load(location.openStream());
                CenterViewCtr centerViewCtr = fxmlLoader.getController();
                centerViewCtr.setFolder((Folder) attribute);
                centerViewCtr.init();
                Stage stage = FXRobotHelper.getStages().get(0);
                BorderPane root = (BorderPane) stage.getScene().getRoot();
                root.setCenter(scrollPane);
            } else {

                showFileContent();
            }

        } else if (mouseEvent.getButton() == MouseButton.SECONDARY) {

            URL location = getClass().getResource("/View/SubContextMenuView.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(location);
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
            contextMenu = fxmlLoader.load(location.openStream());
            SubContextMenuCtr subContextMenuCtr = fxmlLoader.getController();
            subContextMenuCtr.setAttribute(attribute);
            subContextMenuCtr.setFlowPane(flowPane);
            subContextMenuCtr.setFolder(folder);
            subContextMenuCtr.setNode(node);
            subContextMenuCtr.setName_label(label_name);
            contextMenu.show(vbox, mouseEvent.getScreenX(), mouseEvent.getScreenY());

            return;
        }
    }

    public void selectModel(MouseEvent mouseEvent) {
        vbox.setStyle("-fx-background-color:lightblue;");
    }

    public void unSelectModel(MouseEvent mouseEvent) {
        vbox.setStyle("-fx-background-color:transparent;");
    }


   /*public void updateUI() throws IOException {
        URL location_one = getClass().getResource("/View/TopMenu.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location_one);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        HBox topMenu = fxmlLoader.load(location_one.openStream());
        TopMenuCtr topMenuCtr = fxmlLoader.getController();
        topMenuCtr.setText(folder.getPath());
        VBox leftView = FXMLLoader.load(getClass().getResource("/View/LeftView.fxml"));
        VBox rightView = FXMLLoader.load(getClass().getResource("/View/RightView.fxml"));
        Stage stage = FXRobotHelper.getStages().get(0);
        BorderPane root = (BorderPane) stage.getScene().getRoot();
        root.setTop(topMenu);
        root.setLeft(leftView);
        root.setRight(rightView);

        URL location_two = getClass().getResource("/View/CenterView.fxml");
        FXMLLoader fxmlLoader_two = new FXMLLoader();
        fxmlLoader_two.setLocation(location_two);
        fxmlLoader_two.setBuilderFactory(new JavaFXBuilderFactory());
        ScrollPane scrollPane  = fxmlLoader_two.load(location_two.openStream());
        CenterViewCtr centerViewCtr = fxmlLoader_two.getController();
        centerViewCtr.setFolder(folder);
        centerViewCtr.init();
        root.setCenter(scrollPane);

    }*/

    public void rename(ActionEvent actionEvent) {


    }

    private void showFileContent() throws IOException {
        ((TextFile) attribute).open();
        Stage stage = new Stage();
        FileContentCtr.stage = stage;
        URL location = getClass().getResource("/View/FileContentView.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = fxmlLoader.load(location.openStream());
        FileContentCtr fileContentCtr = fxmlLoader.getController();
        fileContentCtr.setTextFile((TextFile) attribute);
        fileContentCtr.setContent_text();
        stage.setTitle(attribute.getName());
        stage.setResizable(false);
        Scene scene = new Scene(root, 480, 480);
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.show();

    }
}
