package Controller;

import Utility.updateUI;
import com.sun.javafx.robot.impl.FXRobotHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import Model.Attribute;
import Model.Folder;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RenameViewCtr implements Initializable {

    @FXML
    private TextField new_name_text;

    @FXML
    private Label tips_label;

    @FXML
    private Button commit_button;

    @FXML
    private Label title_label;

    //重命名的文件夹名或文件名
    private String newFileName;

    //在哪个文件夹中操作
    private Folder folder;

    //判断重命名的是文件还是文件夹
    private boolean isfolder;

    private FlowPane flowPane;

    private Stage stage;

    private boolean flag = true;

    private Attribute attribute;

    private Label rename_label;

    public void setRename_label(Label rename_label) {
        this.rename_label = rename_label;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public void setNewFileName(String newFileName) {
        this.newFileName = newFileName;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    public void setIsfolder(boolean isfolder) {
        this.isfolder = isfolder;
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

    public void commitRename(MouseEvent mouseEvent) throws IOException {

        newFileName = new_name_text.getText();

        if (!Attribute.is_correctName(newFileName)) {
            tips_label.setVisible(true);
            tips_label.setText("错误的文件名！");
            return;
        }
        for (Attribute attribute : folder.listFolder()) {
            if ((attribute.getName().equals(newFileName) == true) && (attribute instanceof Folder == isfolder)) {
                //new ModDialog().messageDialog("名字与其他文件重复");
                flag = false;
                break;
            }
        }
        if (!flag) {
            tips_label.setVisible(true);
            tips_label.setText("名字与其他文件重复！");
            return;
        } else {
            attribute.setName(newFileName);
            //rename_label.setPath(newFileName);
            stage.close();
            tips_label.setVisible(false);
            updateUI();
        }
    }

    public void updateUI() throws IOException {

        Stage stage = FXRobotHelper.getStages().get(0);
        BorderPane root = (BorderPane) stage.getScene().getRoot();

        updateUI.updateTopMenu(root,folder,getClass());

        updateUI.updateLeftView(root,folder,getClass());

        updateUI.updateRightView(root,getClass());

        updateUI.updateCenterView(root,folder,getClass());
    }
}
