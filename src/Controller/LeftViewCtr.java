
package Controller;

import Utility.ReadAndWrite;
import com.sun.javafx.robot.impl.FXRobotHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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

        /*file_structure.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<Attribute>>() {
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
        });*/
        file_structure.addEventHandler(MouseEvent.MOUSE_CLICKED,(MouseEvent e)->{
            handleMouseClicked(e);//单击就更新，即使选定的无变化，上面的监听器不行
        });
    }

    private void handleMouseClicked(MouseEvent event) {
        Node node = event.getPickResult().getIntersectedNode();
        // Accept clicks only on node cells, and not on empty spaces of the TreeView
        if (node instanceof Text || (node instanceof TreeCell && ((TreeCell) node).getText() != null)) {
            Attribute click = (Attribute)((TreeItem)file_structure.getSelectionModel().getSelectedItem()).getValue();
            if(click instanceof  Folder)
                try {
                    updateCenterView((Folder)click);
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

    }

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
        if(folder == CenterViewCtr.folder) return ;
        CenterViewCtr.folder  = folder;
        ScrollPane centerView = FXMLLoader.load(getClass().getResource("/View/CenterView.fxml"));
        Stage stage = FXRobotHelper.getStages().get(0);
        BorderPane root = (BorderPane) stage.getScene().getRoot();
        root.setCenter(centerView);
    }

}
