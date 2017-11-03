package Controller;

import Interface.addCallback;
import Utility.updateUI;
import com.sun.javafx.robot.impl.FXRobotHelper;
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
import Model.Attribute;
import Model.Folder;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CheckCreateCtr implements Initializable {

    @FXML
    private TextField new_name_text;

    @FXML
    private Label tips_label;

    @FXML
    private Button commit_button;

    @FXML
    private Label title_label;

    //新建的文件夹名或文件名
    private String newFileName;

    //在哪个文件夹中操作
    private Folder folder;

    //判断新建文件还是新建文件夹
    private boolean isfolder;

    private FlowPane flowPane;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }


    public void setFlowPane(FlowPane flowPane) {
        this.flowPane = flowPane;
    }

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }


    public void setIsfolder(boolean isfolder) {
        this.isfolder = isfolder;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tips_label.setVisible(false);

    }

    /**
     * 确定新建
     * @param mouseEvent
     * @throws IOException
     */
    public void commitCreate(MouseEvent mouseEvent) throws IOException {
        newFileName = new_name_text.getText();
        folder.add(newFileName, isfolder, new addCallback() {
            @Override
            public void getResult(String message, Attribute attribute) throws IOException {
                if (message.equals("创建成功！") && attribute != null) {

                    URL location = getClass().getResource("/resources/SubFileOrFolderView.fxml");
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    Node node = fxmlLoader.load(location.openStream());
                    SubFileOrFolderCtr subFileOrFolderCtr = fxmlLoader.getController();

                    if (attribute instanceof Folder) {
                        subFileOrFolderCtr.setImageview_picture(1);
                    } else {
                        subFileOrFolderCtr.setImageview_picture(2);
                    }

                    subFileOrFolderCtr.setLabel_name(attribute.getName());
                    try {
                        flowPane.getChildren().add(node);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }

                    updateUI();

                } else {
                    tips_label.setText(message);
                    tips_label.setVisible(true);
                }
            }
        });

    }

    public void setText(int code) {
        if (code == 1) {
            title_label.setText("请输入您要新建文件夹的名字");
        } else if (code == 2) {
            title_label.setText("请输入您要新建文件的名字");
        }
    }

    /**
     * 更新主界面
     * @throws IOException
     */
    public void updateUI() throws IOException {
        Stage stage = FXRobotHelper.getStages().get(0);
        BorderPane root = (BorderPane) stage.getScene().getRoot();

        updateUI.updateTopMenu(root,folder,getClass());

        updateUI.updateLeftView(root,folder,getClass());

        updateUI.updateRightView(root,getClass());

        updateUI.updateCenterView(root,folder,getClass());

        this.stage.close();

    }


}
