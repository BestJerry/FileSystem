
package Controller;

import Model.Main;
import Utility.ReadAndWrite;
import Utility.updateUI;
import com.sun.javafx.robot.impl.FXRobotHelper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import Model.Attribute;
import Model.Folder;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * Created by jerry on 17-8-4.
 */

public class LeftViewCtr implements Initializable{
    @FXML
    private TreeView<Attribute> file_structure;

    private Folder folder;

    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    private final Image folderIcon =
            new Image(getClass().getResourceAsStream("/resources/folderIcon.png"));
    private final Image fileIcon =
            new Image(getClass().getResourceAsStream("/resources/fileIcon.png"));



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void init(){
        try {

            Folder root = ReadAndWrite.root;
            System.out.println(root);
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
                        updateUI((Folder) selectItem.getValue());
                    }
                } catch (Exception ex) {

                }
            }
        });
    }

    /**
     * ????????§µ????treeview???
     * @param rootItem
     * @param root
     */
    public void traverseFolder(TreeItem rootItem, Folder root) {
            for (Attribute son : root.listFolder()) {
                if (son instanceof Folder && (Main.isShow_hide()||!son.getHide())) {
                    TreeItem<Attribute> folderItem = new TreeItem<>(son,new ImageView(folderIcon));
                    folderItem.setExpanded(true);
                    rootItem.getChildren().add(folderItem);
                    traverseFolder(folderItem, (Folder) son);
                } else if ((Main.isShow_hide()||!son.getHide())){
                    TreeItem<Attribute> fileItem = new TreeItem<>(son,new ImageView(fileIcon));
                    rootItem.getChildren().add(fileItem);
                }
            }
    }

    private void updateUI(Folder folder) throws IOException {

        Stage stage = FXRobotHelper.getStages().get(0);
        BorderPane root = (BorderPane) stage.getScene().getRoot();

        updateUI.updateTopMenu(root,folder,getClass());

        updateUI.updateCenterView(root,folder,getClass());
    }

}
