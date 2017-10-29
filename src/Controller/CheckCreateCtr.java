package Controller;

import Interface.addCallback;
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

    public void updateUI() throws IOException {
       // HBox topMenu = FXMLLoader.load(getClass().getResource("/resources.View/TopMenu.fxml"));
        URL location = getClass().getResource("/resources/TopMenu.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        HBox topMenu = fxmlLoader.load(location.openStream());
        TopMenuCtr topMenuCtr = fxmlLoader.getController();
        topMenuCtr.setText(folder.getPath());


        //VBox leftView = FXMLLoader.load(getClass().getResource("/resources.View/LeftView.fxml"));
        URL location_two = getClass().getResource("/resources/LeftView.fxml");
        FXMLLoader fxmlLoader_two = new FXMLLoader();
        fxmlLoader_two.setLocation(location_two);
        fxmlLoader_two.setBuilderFactory(new JavaFXBuilderFactory());
        TreeView  leftView  = fxmlLoader_two.load(location_two.openStream());
        LeftViewCtr leftViewCtr  = fxmlLoader_two.getController();
        leftViewCtr.setFolder(folder);
        leftViewCtr.init();


        TableView rightView = FXMLLoader.load(getClass().getResource("/resources/RightView.fxml"));
        Stage stage = FXRobotHelper.getStages().get(0);
        BorderPane root = (BorderPane) stage.getScene().getRoot();
        root.setTop(topMenu);
        root.setLeft(leftView);
        root.setRight(rightView);

        URL location_three = getClass().getResource("/resources/CenterView.fxml");
        FXMLLoader fxmlLoader_three = new FXMLLoader();
        fxmlLoader_three.setLocation(location_three);
        fxmlLoader_three.setBuilderFactory(new JavaFXBuilderFactory());
        ScrollPane scrollPane  = fxmlLoader_three.load(location_three.openStream());
        CenterViewCtr centerViewCtr = fxmlLoader_three.getController();
        centerViewCtr.setFolder(folder);
        centerViewCtr.init();

        root.setCenter(scrollPane);
        this.stage.close();

    }


}
