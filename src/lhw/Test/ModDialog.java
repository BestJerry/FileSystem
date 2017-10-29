package lhw.Test;

import com.sun.javafx.robot.impl.FXRobotHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ModDialog {// ????
	final Stage stage = new Stage();
	ModDialog modDialog;
	TextField name = new TextField();
	public ModDialog() {
		modDialog = this;
	}

	public void messageDialog(String str) {
		Button btn = new Button();
		Label lb = new Label(str);

		stage.initModality(Modality.APPLICATION_MODAL);// ??
		stage.setTitle("error");
		Group root = new Group();
		Scene scene = new Scene(root, 250, 80);

		btn.setOnAction((ActionEvent e)-> {
			stage.close();
		});
		lb.setLayoutX(80);
		lb.setLayoutY(8);
		btn.setLayoutX(100);
		btn.setLayoutY(35);
		btn.setText("get");

		root.getChildren().add(btn);
		root.getChildren().add(lb);
		stage.setScene(scene);
		stage.show();
	}

	/*public void  newFolderOrFile(Folder folder,boolean is_folder,FlowPane pane) {
		Button btn = new Button();
		HBox rt = new HBox();

		stage.initModality(Modality.APPLICATION_MODAL);// ??
		stage.setTitle("input");
		Group root = new Group();
		Scene scene = new Scene(root, 300, 80);

		btn.setOnAction((ActionEvent e)->{
			stage.hide();

			Attribute attribute = folder.add(name.getText(),is_folder, attribute);
			if(attribute!= null) {
				pane.getChildren().add(new FileNode(attribute, pane, folder));
				try {
					updateUI();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			/*for(Attribute x:folder.listFolder()){
				Folder.print(x);
			}
		});

		rt.getChildren().addAll(new Label("?????"),name);
		rt.setSpacing(10);
		rt.setLayoutX(30);
		rt.setLayoutY(15);
		btn.setLayoutX(150);
		btn.setLayoutY(60);
		btn.setText("???");
		root.getChildren().add(rt);
		root.getChildren().add(btn);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();

	}*/
	public  void updateUI() throws IOException {
		HBox topmenu = FXMLLoader.load(getClass().getResource("/resources/TopMenu.fxml"));
		VBox leftView = FXMLLoader.load(getClass().getResource("/resources/LeftView.fxml"));
		VBox rightView = FXMLLoader.load(getClass().getResource("/resources/RightView.fxml"));
		Stage stage = FXRobotHelper.getStages().get(0);
		BorderPane root = (BorderPane) stage.getScene().getRoot();
		root.setTop(topmenu);
		root.setLeft(leftView);
		root.setRight(rightView);
	}
}
