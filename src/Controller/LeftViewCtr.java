package Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lhw.left.Attribute;
import lhw.left.FileIO;
import lhw.left.Folder;
import lhw.left.TextFile;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by jerry on 17-8-4.
 */
public class LeftViewCtr implements Initializable{
    @FXML
    private TreeView<String> file_structure;

    private final Image folderIcon =
            new Image(getClass().getResourceAsStream("/Picture/folderIcon.png"));
    private final Image fileIcon =
            new Image(getClass().getResourceAsStream("/Picture/fileIcon.png"));
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ArrayList<File> data_files = FileIO.get_DataFile();//获取所有数据文件
        File d_file1 = data_files.get(0);
        FileIO rw = new FileIO(d_file1);

        try {
            Folder root = rw.read_data();
            TreeItem<String> file_structure_rootItem = new TreeItem<>("Root",new ImageView(folderIcon));
            traverseFolder(file_structure_rootItem,root);
            file_structure.setRoot(file_structure_rootItem);
            file_structure_rootItem.setExpanded(true);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    public void traverseFolder(TreeItem rootItem, Folder root) {

        if(root.listFolder().size()==0){
            return;
        }
        else {
            for (Attribute son : root.listFolder()) {
                if (son instanceof Folder) {
                    TreeItem<String> folderItem = new TreeItem<>(son.getName(),new ImageView(folderIcon));
                    rootItem.getChildren().add(folderItem);
                    traverseFolder(folderItem, (Folder) son);
                } else {
                    TreeItem<String> fileItem = new TreeItem<>(son.getName(),new ImageView(fileIcon));
                    rootItem.getChildren().add(fileItem);
                }
            }
        }

    }
}
