/*package lhw.Test;

import Controller.CenterViewCtr;
import com.sun.javafx.robot.impl.FXRobotHelper;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lhw.left.Attribute;
import lhw.left.Folder;

import java.io.IOException;

public class TestEventHandler implements EventHandler<MouseEvent> {
    Node node;

    public TestEventHandler(Node node) {
        this.node = node;
    }

    @Override
    public void handle(MouseEvent event) {
        // TODO Auto-generated method stub
        if (node instanceof FileNode) {
            if (CenterViewCtr.textField != null)
                return;// 重命名时,ldimgfp那里已经处理过了
            if (event.isControlDown() == false) {
                ((FileNode) node).setSelected(true);
                if (event.getClickCount() >= 2 && event.getButton() == MouseButton.PRIMARY) {
                    Attribute file = ((FileNode) node).file;
                    if(file instanceof Folder){
                        CenterViewCtr.folder  = (Folder) file;
                        ScrollPane centerView = null;
                        try {
                            centerView = FXMLLoader.load(getClass().getResource("/View/CenterView.fxml"));
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        Stage stage = FXRobotHelper.getStages().get(0);
                        BorderPane root = (BorderPane) stage.getScene().getRoot();
                        root.setCenter(centerView);
                    }else {

                    }
                }
            }
        }
    }

}*/