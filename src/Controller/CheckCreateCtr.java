package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

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

    private Label label;

    @Override
    public void initialize(URL location, ResourceBundle resources) {




    }

    public void commitCreate(MouseEvent mouseEvent) {


    }

    public void setText(int code){
        if(code == 1){
            label.setText("请输入您要新建的文件夹名称");
        }
        else if(code == 2){
            label.setText("请输入您要新建的文件名称");
        }
    }
}
