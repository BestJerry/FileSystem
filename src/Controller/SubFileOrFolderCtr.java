package Controller;

import Utility.updateUI;
import com.sun.javafx.robot.impl.FXRobotHelper;
import javafx.event.ActionEvent;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import Model.Attribute;
import Model.Folder;
import Model.TextFile;

import java.io.IOException;
import java.net.URL;
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
            imageview_picture.setImage(new Image(getClass().getResourceAsStream("/resources/documents.png")));
        } else if (code == 2) {
            imageview_picture.setImage(new Image(getClass().getResourceAsStream("/resources/textfile.png")));

        }
    }

    public void setLabel_name(String name) {
        label_name.setText(name);

    }

    public void showMenuOrOpenFile(MouseEvent mouseEvent) throws IOException {

        if (contextMenu != null && contextMenu.isShowing()) {
            contextMenu.hide();

        } else if (mouseEvent.getClickCount() >= 2) {
            if (attribute instanceof Folder) {
                Stage stage = FXRobotHelper.getStages().get(0);
                BorderPane root = (BorderPane) stage.getScene().getRoot();

                updateUI.updateTopMenu(root, (Folder) attribute,getClass());

                updateUI.updateCenterView(root, (Folder) attribute,getClass());
            } else {
                if(!((TextFile)attribute).is_open()){
                    showFileContent();
                }else{
                    return;
                }

            }

        } else if (mouseEvent.getButton() == MouseButton.SECONDARY) {

            URL location = getClass().getResource("/resources/SubContextMenuView.fxml");
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



    public void rename(ActionEvent actionEvent) {


    }

    private void showFileContent() throws IOException {
        ((TextFile) attribute).setIs_open(true);
        Stage stage = new Stage();
        URL location = getClass().getResource("/resources/FileContentView.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = fxmlLoader.load(location.openStream());
        FileContentCtr fileContentCtr = fxmlLoader.getController();
        if (((TextFile) attribute).isOnly_read()) {
            fileContentCtr.setUnEditable();
        }
        fileContentCtr.setTextFile((TextFile) attribute);
        fileContentCtr.setStage(stage);
        fileContentCtr.setAttribute(attribute);
        fileContentCtr.setContent_text();
        stage.setTitle(attribute.getName());
        stage.setResizable(false);
        Scene scene = new Scene(root, 480, 480);
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.show();

    }

}
