package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import Model.Folder;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CenterContextMenuCtr implements Initializable {

    @FXML
    private MenuItem create_folder_item;

    @FXML
    private MenuItem create_file_item;

    private FlowPane flowPane;

    private Folder folder;

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }


    public void setFlowPane(FlowPane flowPane) {
        this.flowPane = flowPane;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    /**
     * 新建文件夹
     *
     * @param actionEvent
     * @throws IOException
     */
    public void createFolder(ActionEvent actionEvent) throws IOException {

        URL location = getClass().getResource("/resources/CheckCreateView.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());

        Parent root = fxmlLoader.load(location.openStream());
        CheckCreateCtr checkCreateCtr = fxmlLoader.getController();
        checkCreateCtr.setIsfolder(true);
        checkCreateCtr.setText(1);
        checkCreateCtr.setFolder(folder);
        checkCreateCtr.setFlowPane(flowPane);

        Stage stage = new Stage();
        checkCreateCtr.setStage(stage);
        stage.setTitle("新建文件夹");
        stage.setResizable(false);
        Scene scene = new Scene(root, 480, 180);
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        //设置模态窗口
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    /**
     * 新建文件
     *
     * @param actionEvent
     * @throws IOException
     */
    public void createFile(ActionEvent actionEvent) throws IOException {

        URL location = getClass().getResource("/resources/CheckCreateView.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());

        Parent root = fxmlLoader.load(location.openStream());
        CheckCreateCtr checkCreateCtr = fxmlLoader.getController();
        checkCreateCtr.setIsfolder(false);
        checkCreateCtr.setFlowPane(flowPane);
        checkCreateCtr.setText(2);
        checkCreateCtr.setFolder(folder);
        Stage stage = new Stage();
        checkCreateCtr.setStage(stage);
        stage.setTitle("新建文件");
        stage.setResizable(false);
        Scene scene = new Scene(root, 480, 180);
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        //设置模态窗口
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
}
