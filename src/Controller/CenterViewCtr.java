package Controller;

import java.io.IOException;

import com.sun.javafx.robot.impl.FXRobotHelper;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lhw.left.Folder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by jerry on 17-8-4.
 */
public class CenterViewCtr implements Initializable {
    public static Folder folder;
    @FXML
    private ScrollPane scrollPane;

    @FXML
    private FlowPane flowpane;

    private ContextMenu contextMenu;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*if (folder == null) return;
        allFile = folder.listFolder();
        init();
        loadAllNode();
        scrollPane.setContent(flowpane);*/
    }

    /*private double FPWIDTH = Constant.FPWIDTH = 500, FPHEIGHT = Constant.FPHEIGHT = 560;
    private static double row = 6, col = 6;
    private double WIDTHUNIT = Constant.WIDTHUNIT = FPWIDTH / col, HEIGHTUNIT = Constant.HEIGHTUNIT = FPHEIGHT / row;

    private double paneWidth = FPWIDTH, paneHeight = FPHEIGHT;
    private LinkedList<Attribute> allFile = new LinkedList<>();//folder.listFolder,emmmm

    public static TextField textField;


    private void init() {

        flowpane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        flowpane.setHgap(10);
        flowpane.setVgap(10);
        addMenuItem(flowpane);
    }

    private void addMenuItem(Node node) {
        final ContextMenu cm = new ContextMenu();

        MenuItem newFolder = new MenuItem("新建文件夹");
        MenuItem newFile = new MenuItem("新建文件");
        newFolder.setOnAction(e -> {
            new ModDialog().newFolderOrFile(CenterViewCtr.folder, true, flowpane);
        });
        newFile.setOnAction(e -> {
            new ModDialog().newFolderOrFile(CenterViewCtr.folder, false, flowpane);
        });

        node.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {

            Node clickNode = e.getPickResult().getIntersectedNode();// 点击的具体结点

            if (textField != null) {
                if (!(clickNode instanceof TextField) || !((TextField) clickNode).equals(textField)) {// 点击面板任意位置完成重命名
                    FileNode iNode = FileNode.selectedNode;
                    String name = iNode.file.getName();
                    if (iNode.rightName(textField.getText())) name = textField.getText();

                    Label label = new Label(name);
                    label.setWrapText(true);
                    label.setTextAlignment(TextAlignment.CENTER);
                    iNode.setBottom(label);
                    iNode.setMargin(label, new Insets(0, Constant.WIDTHUNIT / 6.0, 0, Constant.WIDTHUNIT / 6.0));
                    boolean changeName = true;
                    if (iNode.file.equals(name)) changeName = false;
                    iNode.file.setName(name);
                    if (changeName) {
                        try {
                            updateUI();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    textField = null;
                    //this.fileList.refresh(this);
                    //重命名完成再更新一次结点点击事件
                    if (clickNode instanceof FileNode) {
                        new TestEventHandler(clickNode).handle(e);
                    } else if (clickNode.getParent() instanceof FileNode) {
                        new TestEventHandler(clickNode.getParent()).handle(e);
                    } else if (clickNode instanceof LabeledText) {
                        new TestEventHandler(clickNode.getParent().getParent()).handle(e);
                    }
                }
            }
            if (clickNode instanceof FlowPane) {
                if (e.getButton() == MouseButton.SECONDARY) {
                    cm.show(node, e.getScreenX(), e.getScreenY());
                } else {
                    if (cm.isShowing())// 点击面板非图片，hide
                        cm.hide();
                }

                FileNode.clrSelected();
            } else {// 点击非面板,例如图片之类
                if (cm.isShowing())
                    cm.hide();
            }
        });
        cm.getItems().add(newFile);
        cm.getItems().add(newFolder);


    }

    private void loadAllNode() {
        for (Attribute node : allFile) {
            flowpane.getChildren().add(new FileNode(node, flowpane, folder));
        }
    }*/

    public void updateUI() throws IOException {
        HBox topmenu = FXMLLoader.load(getClass().getResource("/View/TopMenu.fxml"));
        VBox leftView = FXMLLoader.load(getClass().getResource("/View/LeftView.fxml"));
        VBox rightView = FXMLLoader.load(getClass().getResource("/View/RightView.fxml"));
        Stage stage = FXRobotHelper.getStages().get(0);
        BorderPane root = (BorderPane) stage.getScene().getRoot();
        root.setTop(topmenu);
        root.setLeft(leftView);
        root.setRight(rightView);
    }

    public void showMenu(MouseEvent mouseEvent) throws IOException {

        if (mouseEvent.getButton() == MouseButton.PRIMARY){
            if (contextMenu!=null&&contextMenu.isShowing()){
                contextMenu.hide();
            }
            return;
        }

        if(mouseEvent.getButton()== MouseButton.SECONDARY){
            if(contextMenu!=null&&contextMenu.isShowing()) {
                contextMenu.hide();

            }
            else{
                URL location = getClass().getResource("/View/ContextMenuView.fxml");
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(location);
                fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                contextMenu = fxmlLoader.load(location.openStream());
                CenterContextMenuCtr centerContextMenuCtr = fxmlLoader.getController();
                contextMenu.show(flowpane, mouseEvent.getScreenX(), mouseEvent.getScreenY());
            }
            return;
        }
    }
}


