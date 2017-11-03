package Controller;

import Model.Main;
import Utility.updateUI;
import com.sun.javafx.robot.impl.FXRobotHelper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by jerry on 17-8-4.
 */
public class TopMenuCtr implements Initializable{

    @FXML
    private TextField text;

    @FXML
    CheckBox hide_box;

    public void setPath(String path){
        text.setText(path);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hide_box.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue){
                    Main.setShow_hide(true);
                    /*Stage stage = FXRobotHelper.getStages().get(0);
                    BorderPane root = (BorderPane) stage.getScene().getRoot();

                    updateUI.updateTopMenu(root,folder,getClass());

                    updateUI.updateLeftView(root,folder,getClass());


                    updateUI.updateCenterView(root,folder,getClass());*/
                }
                else if(oldValue){
                    Main.setShow_hide(false);
                }
            }
        });
    }
}
