package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateFileFolderCtr implements Initializable {

    @FXML
    private TextField new_name_text;

    @FXML
    private Label tips_label;

    @FXML
    private Button check_button;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Stage stage = new Stage();

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/View/CreateFileOrFolderView.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.setTitle("新建文件或文件夹");
        stage.setResizable(false);
        Scene scene = new Scene(root, 480, 180);
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.show();


    }
}
