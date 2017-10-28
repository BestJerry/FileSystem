package Controller;

import com.oracle.webservices.internal.api.message.PropertySet;
import com.sun.javafx.robot.impl.FXRobotHelper;
import com.sun.media.sound.ModelPatch;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import lhw.left.Attribute;
import lhw.left.Folder;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.ResourceBundle;

public class SubContextMenuCtr implements Initializable{

    @FXML
    private MenuItem openItem;

    @FXML
    private MenuItem showAttributeItem;

    @FXML
    private MenuItem renameItem;

    @FXML
    private MenuItem deleteItem;

    //当前操作的attribute
    private Attribute attribute;

    //当前所在的文件夹
    private Folder folder;

    private FlowPane flowPane;

    private Node node;

    public void setNode(Node node) {
        this.node = node;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    public void setFlowPane(FlowPane flowPane) {
        this.flowPane = flowPane;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void open(ActionEvent actionEvent) {

    }

    public void showAttribute(ActionEvent actionEvent) throws IOException {

        Stage stage = new Stage();

        URL location = getClass().getResource("/View/FileAttributeView.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = fxmlLoader.load(location.openStream());
        FileAttributeCtr fileAttributeCtr = fxmlLoader.getController();
        fileAttributeCtr.setAttribute(attribute);
        fileAttributeCtr.init();

        stage.setTitle("属性");
        stage.setResizable(false);
        Scene scene = new Scene(root, 420, 420);
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.show();
    }

    public void delete(ActionEvent actionEvent) throws IOException {


        try {
            flowPane.getChildren().remove(node);
            attribute.getFaNode().remove(attribute);
        }
        catch (NullPointerException e){
            if(folder==null){
                System.out.print("folder为空");
            }
            if (attribute==null){
                System.out.print("attribute为空");
            }
            if (flowPane == null){
                System.out.print("flowPane为空");

            }
            if (node==null){
                System.out.print("node为空");
            }
        }

        updateUI();
    }

    public void rename(ActionEvent actionEvent) {


    }

    public void updateUI() throws IOException {
        HBox topmenu = FXMLLoader.load(getClass().getResource("/View/TopMenu.fxml"));
        VBox leftView = FXMLLoader.load(getClass().getResource("/View/LeftView.fxml"));
        VBox rightView = FXMLLoader.load(getClass().getResource("/View/RightView.fxml"));
        Stage stage = FXRobotHelper.getStages().get(0);
        BorderPane root = (BorderPane) stage.getScene().getRoot();
        root.setTop(topmenu);
        root.setLeft(leftView);
        root.setRight(rightView);
    }

}
