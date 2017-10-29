package Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by jerry on 17-8-4.
 */
public class TopMenuCtr implements Initializable{

    @FXML
    private TextField text;

    public void setText(String path){
        text.setText(path);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
