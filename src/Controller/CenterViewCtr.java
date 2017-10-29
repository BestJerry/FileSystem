package Controller;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import lhw.left.Attribute;
import lhw.left.Folder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

/**
 * Created by jerry on 17-8-4.
 */
public class CenterViewCtr implements Initializable {


    @FXML
    private ScrollPane scrollpane;

    @FXML
    private FlowPane flowpane;

    private ContextMenu contextMenu;

    private LinkedList<Attribute> files = new LinkedList<>();

    //当前所在文件夹folder
    private  Folder folder;


    public void setFolder(Folder folder) {
        this.folder = folder;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }

    public void init(){
        try {
            addNode();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void addNode() throws IOException {
        if (folder == null) {
            return;
        }
        files = folder.listFolder();

        for (Attribute son : files) {
            if (son instanceof Folder) {
                URL location = getClass().getResource("/resources/SubFileOrFolderView.fxml");
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(location);
                fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                Node node = fxmlLoader.load(location.openStream());
                SubFileOrFolderCtr subFileOrFolderCtr = fxmlLoader.getController();
                subFileOrFolderCtr.setImageview_picture(1);
                subFileOrFolderCtr.setLabel_name(son.getName());
                subFileOrFolderCtr.setAttribute(son);
                subFileOrFolderCtr.setFolder(folder);
                subFileOrFolderCtr.setFlowPane(flowpane);
                subFileOrFolderCtr.setNode(node);
                flowpane.getChildren().add(node);
            } else {
                URL location = getClass().getResource("/resources/SubFileOrFolderView.fxml");
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(location);
                fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                Node node = fxmlLoader.load(location.openStream());
                SubFileOrFolderCtr subFileOrFolderCtr = fxmlLoader.getController();
                subFileOrFolderCtr.setImageview_picture(2);
                subFileOrFolderCtr.setLabel_name(son.getName());
                subFileOrFolderCtr.setAttribute(son);
                subFileOrFolderCtr.setFolder(folder);
                subFileOrFolderCtr.setFlowPane(flowpane);
                subFileOrFolderCtr.setNode(node);
                flowpane.getChildren().add(node);
            }

        }

    }




    /**
     * 打开右键菜单
     * @param mouseEvent
     * @throws IOException
     */
    public void showMenu(MouseEvent mouseEvent) throws IOException {

        if (mouseEvent.getPickResult().getIntersectedNode() != flowpane) {
            if (contextMenu != null && contextMenu.isShowing()){
                contextMenu.hide();
            }
            return;
        }
        if (contextMenu != null && contextMenu.isShowing()) {
            contextMenu.hide();

        } else if (mouseEvent.getButton() == MouseButton.SECONDARY) {

                URL location = getClass().getResource("/resources/CenterContextMenuView.fxml");
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(location);
                fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                contextMenu = fxmlLoader.load(location.openStream());
                CenterContextMenuCtr centerContextMenuCtr = fxmlLoader.getController();
                centerContextMenuCtr.setFlowPane(flowpane);
                centerContextMenuCtr.setFolder(folder);
                contextMenu.show(flowpane, mouseEvent.getScreenX(), mouseEvent.getScreenY());

            return;
        }
    }

}


