package lhw.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


import Controller.CenterViewCtr;
import com.sun.javafx.robot.impl.FXRobotHelper;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import lhw.left.Attribute;
import lhw.left.Folder;
import lhw.left.TextFile;


public class FileNode extends BorderPane {
    private double widthUnit = Constant.WIDTHUNIT, heightUnit = Constant.HEIGHTUNIT;
    FileNode fileNode = this;

    public BooleanProperty selected = new SimpleBooleanProperty();
    public static FileNode selectedNode = null;
    public Attribute file = null;
    public Folder faFile = null;
    public FlowPane ldImgFP = null;
    private ContextMenu cm = null;


    public FileNode(Attribute file, FlowPane lFp, Folder faFile) {
        ldImgFP = lFp;
        this.file = file;
        this.faFile = faFile;
        init();
        getNode(file);
    }

    void init() {
        this.setPrefWidth(Constant.WIDTHUNIT);
        this.setMinWidth(Constant.WIDTHUNIT);
        this.setMaxWidth(Constant.WIDTHUNIT);
        this.setPrefHeight(Constant.HEIGHTUNIT);
        addListener();
        //addMenuItem(this);
    }


    void getNode(Attribute file) {
        double w = widthUnit - 10, h = heightUnit - 10;
        ImageView thumImg;
        if (file instanceof Folder)
            thumImg = new ImageView(new Image("/Picture/folderIcon.png", w, h, false, false));
        else {
            thumImg = new ImageView(new Image("/Picture/fileIcon.png", w, h, false, false));
        }
        this.setCenter(thumImg);
        Label label = new Label(file.getName());
        label.setWrapText(true);
        this.setBottom(label);
        double right = 0, left = 0;
        right = left = (widthUnit - w) / 2.0;
        if (Math.abs(right - 0) <= 5)
            right = left = right + 5;
        this.setMargin(thumImg, new Insets(heightUnit - h > 30 ? 30 : heightUnit - h, right, 0, left));
        this.setMargin(label, new Insets(0, widthUnit / 6.0, 0, widthUnit / 6.0));
        label.setTextAlignment(TextAlignment.CENTER);
    }

    void addMenuItem(Node node) {
        cm = new ContextMenu();

        MenuItem property = new MenuItem(new String("����"));
        MenuItem delete = new MenuItem("ɾ��");
        MenuItem rename = new MenuItem("������");
        MenuItem open = new MenuItem("��");
        open.setOnAction((ActionEvent e) -> {
            if (this.file instanceof Folder) {
                CenterViewCtr.folder = (Folder) this.file;
                ScrollPane centerView = null;
                try {
                    centerView = FXMLLoader.load(getClass().getResource("/View/CenterView.fxml"));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                Stage stage = FXRobotHelper.getStages().get(0);
                BorderPane root = (BorderPane) stage.getScene().getRoot();
                root.setCenter(centerView);
            } else {
                ((TextFile)this.file).open();//���ı�֮ǰ�ı�Ҫ����

            }
        });

        delete.setOnAction((ActionEvent e) -> {
            ldImgFP.getChildren().remove(this);
            ((Folder) faFile).remove(file);
            try {
                updateUI();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        rename.setOnAction((ActionEvent e) -> {
            FileNode iNode = this;

            Label label = (Label) (iNode.getBottom());
            TextField tf = new TextField(label.getText());
            //CenterViewCtr.textField = tf;
            tf.setEditable(true);
            iNode.setBottom(tf);
            tf.setOnAction((ActionEvent event) -> {
                String name = label.getText();
                if (this.rightName(tf.getText())) name = tf.getText();
                label.setText(name);
                iNode.setBottom(label);
                boolean changeName = true;
                if (this.file.getName().equals(name)) changeName = false;
                this.file.setName(name);
                //CenterViewCtr.textField = null;
                if (changeName) {
                    try {
                        updateUI();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            });

        });
        property.setOnAction(e -> {
			/*
				��ʾ���ԵĴ���=_=
			 */
        });
        cm.getItems().add(open);
        cm.getItems().add(property);
        cm.getItems().add(delete);
        cm.getItems().add(rename);

        node.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            if (e.getButton() == MouseButton.SECONDARY)
                cm.show(node, e.getScreenX(), e.getScreenY());
            else {
                if (cm.isShowing())
                    cm.hide();
            }
        });
    }

    void addListener() {
        selected.addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                if (selected.get()) {
                    fileNode.setStyle("-fx-background-color:lightblue;");
                } else {
                    fileNode.setStyle("-fx-background-color:transparent;");
                }
            }
        });
        this.setOnMouseEntered((MouseEvent e) -> {
            if (!selected.get())
                this.setStyle("-fx-background-color:PaleTurquoise;");
        });
        this.setOnMouseExited((MouseEvent e) -> {
            if (!selected.get())
                this.setStyle("-fx-background-color:transparent;");
        });
        //this.addEventHandler(MouseEvent.MOUSE_CLICKED, new TestEventHandler(this));

    }

    public void setSelected(boolean value) {
        if (value == true) {
            clrSelected();
            selectedNode = this;
        }
        selected.set(value);
    }

    public static void clrSelected() {
        if (selectedNode != null)
            selectedNode.setSelected(false);
    }

    public boolean rightName(String name) {
        if (!Attribute.is_correctName(name)) {
            new ModDialog().messageDialog("���ֲ��Ϸ�");
            return false;
        }
        for (Attribute f : faFile.listFolder()) {
            if (f != this.file && f.getName().equals(name) == true
                    && f.getClass() == this.file.getClass()) {
                new ModDialog().messageDialog("�����������ļ��ظ�");
                return false;
            }
        }
        return true;
    }

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
}
