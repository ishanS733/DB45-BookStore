package mainApplication;

import com.jfoenix.controls.JFXButton;
import controllers.userLoginController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;

public class Main extends Application {

    @FXML
    private AnchorPane rootAnchorPane;
    @FXML
    public static Stage mainStage;
    @FXML
    private JFXButton authorButton;
    @FXML
    private JFXButton userButton;
    @FXML
    private JFXButton publisherButton;

    public static void main(String[] args) throws SQLException {
        userLoginController.connectToDatabase();
        launch(args);
    }




    public static void changeScene(String sceneName) throws IOException {
        String pathtoFXML = "src/resources/fxml/" + sceneName + ".fxml";
        FXMLLoader loader = new FXMLLoader();
        FileInputStream fxmlStream = new FileInputStream(pathtoFXML);
        System.out.println(pathtoFXML);
        AnchorPane root = (AnchorPane) loader.load(fxmlStream);
        Scene sc = new Scene(root);
        mainStage.setScene(sc);

    }


    @Override
    public void start(Stage mainStage) throws Exception {
        Main.mainStage = mainStage;
        mainStage.setResizable(false);
        changeScene("Main");
        mainStage.show();
    }

    public void adminButtonPressed(MouseEvent mouseEvent) throws IOException{
        changeScene("adminLogin");
    }

    public void publisherbuttonPressed(MouseEvent mouseEvent) throws IOException {
        changeScene("publisherLogin");
    }

    public void authorButtonPressed(MouseEvent mouseEvent) throws IOException {
        changeScene("authorLogin");
    }

    public void userButtonPressed(MouseEvent mouseEvent) throws Exception {
        userLoginController.userSetup();
    }

    public void stockManagerPressed(MouseEvent mouseEvent) throws IOException {
        userLoginController.changeScene("stockManagerLogin");
    }
}
