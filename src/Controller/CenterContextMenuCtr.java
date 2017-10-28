package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CenterContextMenuCtr implements Initializable{

    @FXML
    private MenuItem create_folder_item;

    @FXML
    private MenuItem create_file_item;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void createFolder(ActionEvent actionEvent) throws IOException {

        URL location = getClass().getResource("/View/CheckCreateView.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());

        Parent root = fxmlLoader.load(location.openStream());
        CheckCreateCtr checkCreateCtr = fxmlLoader.getController();
        checkCreateCtr.setText(1);



        Stage stage = new Stage();
        stage.setTitle("新建文件夹");
        stage.setResizable(false);
        Scene scene = new Scene(root, 480, 180);
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.show();
    }

    public void createFile(ActionEvent actionEvent) throws IOException {

        URL location = getClass().getResource("/View/CheckCreateView.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());

        Parent root = fxmlLoader.load(location.openStream());
        CheckCreateCtr checkCreateCtr = fxmlLoader.getController();
        checkCreateCtr.setText(2);

        Stage stage = new Stage();
        stage.setTitle("新建文件");
        stage.setResizable(false);
        Scene scene = new Scene(root, 480, 180);
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.show();
    }
}
