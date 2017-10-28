
package Controller;

import Utility.ReadAndWrite;
import com.sun.javafx.robot.impl.FXRobotHelper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lhw.left.Attribute;
import lhw.left.FileIO;
import lhw.left.Folder;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


/**
 * Created by jerry on 17-8-4.
 */

public class LeftViewCtr implements Initializable{
    @FXML
    private TreeView<Attribute> file_structure;

    private final Image folderIcon =
            new Image(getClass().getResourceAsStream("/Picture/folderIcon.png"));
    private final Image fileIcon =
            new Image(getClass().getResourceAsStream("/Picture/fileIcon.png"));



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {

            Folder root = ReadAndWrite.root;
            TreeItem<Attribute> file_structure_rootItem = new TreeItem<>(root, new ImageView(folderIcon));
            traverseFolder(file_structure_rootItem, root);
            file_structure.setRoot(file_structure_rootItem);
            file_structure_rootItem.setExpanded(true);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        file_structure.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<Attribute>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<Attribute>> observable, TreeItem<Attribute> oldValue, TreeItem<Attribute> newValue) {
                TreeItem<Attribute> selectItem = newValue;

                try {
                    if (selectItem.getValue() instanceof Folder) {
                        updateCenterView((Folder) selectItem.getValue());
                    }
                } catch (Exception ex) {

                }
            }
        });
    }

    /**
     * 遍历文件夹，获取treeview结点
     * @param rootItem
     * @param root
     */
    public void traverseFolder(TreeItem rootItem, Folder root) {
            for (Attribute son : root.listFolder()) {
                if (son instanceof Folder) {
                    TreeItem<Attribute> folderItem = new TreeItem<>(son,new ImageView(folderIcon));
                    folderItem.setExpanded(true);
                    rootItem.getChildren().add(folderItem);
                    traverseFolder(folderItem, (Folder) son);
                } else {
                    TreeItem<Attribute> fileItem = new TreeItem<>(son,new ImageView(fileIcon));
                    rootItem.getChildren().add(fileItem);
                }
            }
    }

    private void updateCenterView(Folder folder) throws IOException {

        URL location = getClass().getResource("/View/CenterView.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        ScrollPane scrollPane  = fxmlLoader.load(location.openStream());
        CenterViewCtr centerViewCtr = fxmlLoader.getController();
        centerViewCtr.setFolder(folder);
        //centerViewCtr.setFolder(folder);
        centerViewCtr.init();
        Stage stage = FXRobotHelper.getStages().get(0);
        BorderPane root = (BorderPane) stage.getScene().getRoot();
        root.setCenter(scrollPane);
    }

}
