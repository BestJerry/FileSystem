/*

package lhw.Test;

import java.util.LinkedList;

import com.sun.javafx.scene.control.skin.LabeledText;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.TextAlignment;
import lhw.left.Attribute;
import lhw.left.Folder;
import lhw.left.TextFile;

public class UI extends FlowPane {
    private double paneWidth = Constant.FPWIDTH, paneHeight = Constant.FPHEIGHT;
    private LinkedList<Attribute> allFile = new LinkedList<>();//folder.listFolder,emmmm
    public static Folder folder;
    public static TextField textField;

    public UI(Folder folder) {
        allFile = folder.listFolder();
        this.folder = folder;
        init();
        loadAllNode();
    }

    public UI() {
        init();
    }

    private void init() {

        this.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        this.setHgap(10);
        this.setVgap(10);
        addMenuItem(this);
    }

    private void addMenuItem(Node node) {
        final ContextMenu cm = new ContextMenu();

        MenuItem newFolder = new MenuItem("�½��ļ���");
        MenuItem newFile = new MenuItem("�½��ļ�");
        newFolder.setOnAction(e -> {
            new ModDialog().newFolderOrFile(this.folder,true,this);
        });
        newFile.setOnAction(e -> {
            new ModDialog().newFolderOrFile(this.folder,false,this);
        });

        cm.getItems().add(newFile);
        cm.getItems().add(newFolder);


        node.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {

            Node clickNode = e.getPickResult().getIntersectedNode();// ����ľ�����

			if (textField != null) {
                if (!(clickNode instanceof TextField) || !((TextField) clickNode).equals(textField)) {// ����������λ�����������
					FileNode iNode = FileNode.selectedNode;
					String name = iNode.file.getName();
					if(iNode.rightName(textField.getText())) name = textField.getText();

					Label label = new Label(name);
					label.setWrapText(true);
					label.setTextAlignment(TextAlignment.CENTER);
					iNode.setBottom(label);
					iNode.setMargin(label, new Insets(0, Constant.WIDTHUNIT / 6.0, 0, Constant.WIDTHUNIT / 6.0));


					iNode.file.setName(name);


					UI.textField = null;
					//this.fileList.refresh(this);
					//����������ٸ���һ�ν�����¼�
					if (clickNode instanceof FileNode) {
						new TestEventHandler(clickNode).handle(e);
					} else if (clickNode.getParent() instanceof FileNode) {
						new TestEventHandler(clickNode.getParent()).handle(e);
					} else if (clickNode instanceof LabeledText) {
						new TestEventHandler(clickNode.getParent().getParent()).handle(e);
					}
				}
			}
            if (clickNode instanceof UI) {
                if (e.getButton() == MouseButton.SECONDARY) {
                    cm.show(node, e.getScreenX(), e.getScreenY());
                } else {
                    if (cm.isShowing())// �������ͼƬ��hide
                        cm.hide();
                }

                FileNode.clrSelected();
            } else {// ��������,����ͼƬ֮��
                if (cm.isShowing())
                    cm.hide();
            }
        });

    }

    private void loadAllNode() {
        System.out.println(allFile.size() + this.folder.getName());
        for (Attribute node : allFile) {
            this.getChildren().add(new FileNode(node, this, folder));
        }
    }


}

*/
