package Controller;

import Model.TextFile;
import com.sun.javafx.beans.IDProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import Model.Attribute;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

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
    private CheckBox only_read_box;

    @FXML
    private CheckBox read_write_box;

    @FXML
    private CheckBox hide_box;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                attribute.setOpen_property(false);
            }
        });
    }

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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        only_read_box.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue){
                    read_write_box.setSelected(false);
                    ((TextFile)attribute).setOnly_read(true);
                }else if(oldValue){
                    read_write_box.setSelected(true);
                    ((TextFile)attribute).setOnly_read(false);
                }
            }
        });

        read_write_box.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue){
                    only_read_box.setSelected(false);
                    if (attribute instanceof TextFile){
                        ((TextFile)attribute).setOnly_read(false);
                    }

                }else if(oldValue){
                    only_read_box.setSelected(true);
                    if (attribute instanceof TextFile){
                        ((TextFile)attribute).setOnly_read(false);
                    }
                }
            }
        });

        hide_box.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue){
                   attribute.setHide(true);
                }else if(oldValue){
                   attribute.setHide(false);
                }
            }
        });

    }

    public void init(){
      setFilename_label();
      setPosition_label();
      setSize_label();
      if (attribute.getHide()){
          hide_box.setSelected(true);
      }else{
          hide_box.setSelected(false);
      }

      if (attribute instanceof TextFile){
          if (((TextFile) attribute).isOnly_read()){
              only_read_box.setSelected(true);
              read_write_box.setSelected(false);
          }else{
              only_read_box.setSelected(false);
              read_write_box.setSelected(true);
          }
      }else{
          only_read_box.setDisable(true);
          read_write_box.setDisable(true);
          only_read_box.setSelected(false);
          read_write_box.setSelected(true);
      }
    }
}
