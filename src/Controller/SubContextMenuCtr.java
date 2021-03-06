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
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import Model.Attribute;
import Model.Folder;
import Model.TextFile;

import java.io.IOException;
import java.net.URL;
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

    private Label rename_label;

    public void setName_label(Label name_label) {
        this.rename_label = name_label;
    }

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

    /**
     * 打开文件或文件夹
     * @param actionEvent
     * @throws IOException
     */
    public void open(ActionEvent actionEvent) throws IOException {
        if (attribute instanceof Folder) {

            Stage stage = FXRobotHelper.getStages().get(0);
            BorderPane root = (BorderPane) stage.getScene().getRoot();

            updateUI.updateTopMenu(root, (Folder) attribute,getClass());

            updateUI.updateCenterView(root, (Folder) attribute,getClass());
        } else {
            if(!((TextFile)attribute).is_open()){
                showFileContent();
            }

        }


    }

    public void showAttribute(ActionEvent actionEvent) throws IOException {

        if (attribute.isOpen_property()){
            return;
        }
        attribute.setOpen_property(true);
        Stage stage = new Stage();

        URL location = getClass().getResource("/resources/FileAttributeView.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = fxmlLoader.load(location.openStream());
        FileAttributeCtr fileAttributeCtr = fxmlLoader.getController();
        fileAttributeCtr.setAttribute(attribute);
        fileAttributeCtr.init();
        fileAttributeCtr.setStage(stage);
        stage.setTitle("属性");
        stage.setResizable(false);
        Scene scene = new Scene(root, 420, 420);
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.show();
    }

    public void delete(ActionEvent actionEvent) throws IOException {

        URL location = getClass().getResource("/resources/CheckDeleteView.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());

        Parent root = fxmlLoader.load(location.openStream());
        CheckDeleteCtr checkDeleteCtr  = fxmlLoader.getController();
        checkDeleteCtr.setFolder(folder);
        checkDeleteCtr.setFlowPane(flowPane);
        checkDeleteCtr.setAttribute(attribute);
        checkDeleteCtr.setNode(node);
        Stage stage = new Stage();
        checkDeleteCtr.setStage(stage);
        stage.setTitle("提示");
        stage.setResizable(false);
        Scene scene = new Scene(root, 480, 180);
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        //设置模态窗口
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();

    }

    public void rename(ActionEvent actionEvent) throws IOException {
        URL location = getClass().getResource("/resources/RenameView.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());

        Parent root = fxmlLoader.load(location.openStream());
        RenameViewCtr renameViewCtr = fxmlLoader.getController();
        if (attribute instanceof Folder){
            renameViewCtr.setIsfolder(true);
        }else{
            renameViewCtr.setIsfolder(false);
        }
        renameViewCtr.setFolder(folder);
        renameViewCtr.setFlowPane(flowPane);
        renameViewCtr.setAttribute(attribute);
        renameViewCtr.setRename_label(rename_label);
        Stage stage = new Stage();
        renameViewCtr.setStage(stage);
        stage.setTitle("重命名"+attribute.getName());
        stage.setResizable(false);
        Scene scene = new Scene(root, 480, 180);
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        //设置模态窗口
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();

    }



    private void showFileContent() throws IOException {
        ((TextFile) attribute).setIs_open(true);
        Stage stage = new Stage();

        URL location = getClass().getResource("/resources/FileContentView.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = fxmlLoader.load(location.openStream());
        FileContentCtr fileContentCtr  = fxmlLoader.getController();
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
