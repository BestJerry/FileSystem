package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FileContentCtr implements Initializable{

    @FXML
    private TextArea content;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Stage stage = new Stage();

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/View/FileContentView.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.setTitle("文件名");
        stage.setResizable(false);
        Scene scene = new Scene(root, 480, 480);
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.show();


        content.setText("text");
    }
}
