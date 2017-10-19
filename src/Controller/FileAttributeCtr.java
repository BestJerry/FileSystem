package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FileAttributeCtr implements Initializable{
    @FXML
    private Label filename;

    @FXML
    private Label size;

    @FXML
    private Label position;

    @FXML
    private Label attribute;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Stage stage = new Stage();

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/View/FileAttribute.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.setTitle("文件名");
        stage.setResizable(false);
        Scene scene = new Scene(root, 320, 480);
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.show();

        filename.setText("filename");
        size.setText("size");
        position.setText("position");
        attribute.setText("attribute");
    }
}
