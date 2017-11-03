package Controller;

import Model.Attribute;
import Model.Folder;
import Utility.updateUI;
import com.sun.javafx.robot.impl.FXRobotHelper;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CheckDeleteCtr implements Initializable{

    @FXML
    private Button commit_button;

    @FXML Button cancel_button;

    @FXML
    Label tips_label;

    //新建的文件夹名或文件名
    private String newFileName;

    //在哪个文件夹中操作
    private Folder folder;

    private Attribute attribute;

    private Node node;

    public void setNode(Node node) {
        this.node = node;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    private FlowPane flowPane;

    private Stage stage;

    public void setFolder(Folder folder) {
        this.folder = folder;
    }



    public void setFlowPane(FlowPane flowPane) {
        this.flowPane = flowPane;
    }

    public void setStage(Stage stage) {
        this.stage = stage;

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void commitDelete(MouseEvent mouseEvent) {
        try {
            if(attribute.getFaNode().remove(attribute)){
                flowPane.getChildren().remove(node);
                tips_label.setVisible(false);
                stage.close();
                updateUI();
            }else{
                tips_label.setVisible(true);
            }

        }
        catch (NullPointerException | IOException e){
            e.printStackTrace();
        }
    }

    public void cancelDelete(MouseEvent mouseEvent) {
        tips_label.setVisible(false);
        stage.close();
    }

    private void updateUI() throws IOException {

        Stage stage = FXRobotHelper.getStages().get(0);
        BorderPane root = (BorderPane) stage.getScene().getRoot();

        updateUI.updateTopMenu(root,folder,getClass());

        updateUI.updateLeftView(root,folder,getClass());

        updateUI.updateRightView(root,getClass());

        updateUI.updateCenterView(root,folder,getClass());
    }
}
