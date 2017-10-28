package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import lhw.left.Attribute;
import lhw.left.Folder;

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
            imageview_picture.setImage(new Image(getClass().getResourceAsStream("/Picture/documents.png")));
        } else if (code == 2) {
            imageview_picture.setImage(new Image(getClass().getResourceAsStream("/Picture/textfile.png")));

        }
    }

    public void setLabel_name(String name){
        label_name.setText(name);

    }
    public void showMenu(MouseEvent mouseEvent) throws IOException {

            if (contextMenu != null && contextMenu.isShowing()) {
                contextMenu.hide();

            }

        else if (mouseEvent.getButton() == MouseButton.SECONDARY) {

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
}
