package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import lhw.left.Attribute;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FileAttributeCtr implements Initializable{
    @FXML
    private Label filename_label;

    @FXML
    private Label size_label;

    @FXML
    private Label position_label;

    @FXML
    private Label attribute_label;

    private Attribute attribute;

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }
    private void setFilename_label(){
        filename_label.setText(attribute.getName());
    }
    private void setSize_label(){
        size_label.setText(String.valueOf(attribute.getSize2()));
    }
    private void setPosition_label(){
        position_label.setText(attribute.getPath());
    }
    private void setAttribute_label(){
        attribute_label.setText(String.valueOf(attribute.get_type()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void init(){
      setAttribute_label();
      setFilename_label();
      setPosition_label();
      setSize_label();
    }
}
