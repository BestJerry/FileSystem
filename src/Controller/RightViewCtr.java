package Controller;

import Start.FATForShow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import lhw.left.FAT;

import javax.swing.text.TableView;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by jerry on 17-8-4.
 */
public class RightViewCtr implements Initializable {
    @FXML
    private javafx.scene.control.TableView fat_table;

    @FXML
    private TableColumn disk;

    @FXML
    private TableColumn value;


    ObservableList<FATForShow> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        for (int i = 0; i < 128; i++) {
            FATForShow fatForShow = new FATForShow();
            fatForShow.setDisk(String.valueOf(i));
            fatForShow.setValue(String.valueOf(FAT.get(i)));
            data.add(fatForShow);
        }

        disk.setCellValueFactory(new PropertyValueFactory<>("disk"));
        value.setCellValueFactory(new PropertyValueFactory<>("value"));

        fat_table.setItems(data);

    }
}
