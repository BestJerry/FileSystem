package Controller;

import com.sun.javafx.robot.impl.FXRobotHelper;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import Model.TextFile;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CheckSaveCtr implements Initializable {

    private TextFile textFile;

    private String content;

    private Stage stage;

    private Stage  primary_stage;

    public void setPrimary_stage(Stage primary_stage) {
        this.primary_stage = primary_stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTextFile(TextFile textFile) {
        this.textFile = textFile;
    }

    public void commitSave(MouseEvent mouseEvent) throws Exception {
        try {
            textFile.setIs_open(true);
            textFile.close(true,content);
            stage.close();
            primary_stage.close();
            updateUI();
        }catch (Exception e){
            System.out.println(textFile.is_open());
        }

    }

    public void cancelSave(MouseEvent mouseEvent) throws Exception {
        textFile.setIs_open(true);
        textFile.close(false,content);
        stage.close();
        primary_stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void updateUI() throws IOException {
        Node  rightView = FXMLLoader.load(getClass().getResource("/resources/RightView.fxml"));
        Stage stage = FXRobotHelper.getStages().get(0);
        BorderPane root = (BorderPane) stage.getScene().getRoot();

        root.setRight(rightView);
    }
}
