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

   /*private double FPWIDTH = Constant.FPWIDTH = 500, FPHEIGHT = Constant.FPHEIGHT = 560;
   private static double row = 6, col = 6;
   private double WIDTHUNIT = Constant.WIDTHUNIT = FPWIDTH / col, HEIGHTUNIT = Constant.HEIGHTUNIT = FPHEIGHT / row;

   private double paneWidth = FPWIDTH, paneHeight = FPHEIGHT;
   private LinkedList<Attribute> allFile = new LinkedList<>();//folder.listFolder,emmmm

   public static TextField textField;*/


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*if (folder == null) return;
        allFile = folder.listFolder();
        init();
        addNode();
        scrollpane.setContent(flowpane);*/

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

    /*private void init() {

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
                    iNode.file.setLabel_name(name);
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

    private void addNode() {
        for (Attribute node : allFile) {
            flowpane.getChildren().add(new FileNode(node, flowpane, folder));
        }
    }*/


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


