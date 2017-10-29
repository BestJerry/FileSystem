package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FolderAttributeCtr implements Initializable{

    @FXML
    private Label foldername;

    @FXML
    private Label size;

    @FXML
    private Label position;

    @FXML
    private Label attribute;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
