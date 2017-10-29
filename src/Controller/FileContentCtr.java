package Controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lhw.left.TextFile;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FileContentCtr implements Initializable{

    @FXML
    private TextArea content_text;

    private TextFile textFile;

    public static Stage stage;



    public void setTextFile(TextFile textFile) {
        this.textFile = textFile;
    }

    public void setContent_text(){
        content_text.setText(textFile.getContent());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                //调用event.consume()来阻止事件进一步传递
                event.consume();
                if (textFile.isOnly_read()){
                    return;
                }
                else {
                    URL location = getClass().getResource("/resources/CheckSaveView.fxml");
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    Parent root = null;
                    try {
                        root = fxmlLoader.load(location.openStream());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    CheckSaveCtr checkSaveCtr = fxmlLoader.getController();
                    checkSaveCtr.setTextFile(textFile);
                    checkSaveCtr.setContent(content_text.getText());
                    checkSaveCtr.setPrimary_stage(stage);
                    Stage stage = new Stage();

                    checkSaveCtr.setStage(stage);
                    stage.setTitle("提示");
                    stage.setResizable(false);
                    Scene scene = new Scene(root, 480, 180);
                    stage.setScene(scene);
                    stage.setAlwaysOnTop(true);
                    //设置模态窗口
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.show();
                }

            }
        });
    }


}
