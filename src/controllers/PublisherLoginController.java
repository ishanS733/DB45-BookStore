package controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import mainApplication.Main;
import mainApplication.Publication;
import mainApplication.User;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import static controllers.userLoginController.changeScene;
import static controllers.userLoginController.createLocalSession;

public class PublisherLoginController {
    public String curr_pub_name;
    public JFXTextField publisherField;
    public JFXPasswordField passwordField;

    @FXML
    void initialize() throws SQLException {
        userLoginController.connectToDatabase();
    }

    public void passwordFieldPressed(MouseEvent mouseEvent) {
    }
    public void onLoginClicked(MouseEvent mouseEvent) throws SQLException, IOException {
        String pub_id = publisherField.getText();
        String password = passwordField.getText();
//        System.out.println(pub_id + " " + password + "l");
        boolean isValidUser = false;
        try {
            isValidUser = authenticate(pub_id, password);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            createAccountController.showAlert("Login Failed", "Enter valid publication entries");
            return;
        }
        catch (Error e){
            System.out.println(e.getMessage());
            createAccountController.showAlert("Login Failed", "Enter valid publication entries");
            return;
        }
        if(isValidUser) {
            Publication.pub_id = Integer.parseInt(pub_id);
            Publication.pub_name = curr_pub_name;
            Main.changeScene("publisher_home");
        }
        else{
            createAccountController.showAlert("Login Failed", "Enter valid publication entries");
            return;
        }
    }

    private boolean authenticate(String username, String password) throws SQLException {
        String query = "Select * from publications where pub_id = " + username + " and password = '" + password + "';";
        System.out.println("authenticating");
        ResultSet rs = userLoginController.executer.executeQuery(query);
//        System.out.println("auth doing");

        if (!rs.next()) {
            return false;
        }
        curr_pub_name = rs.getString("pub_name");
        return true;
    }

    public void onMainPageClicked(MouseEvent mouseEvent) throws IOException {
        String pathtoFXML = "src/resources/fxml/Main.fxml";
        FXMLLoader loader = new FXMLLoader();
        FileInputStream fxmlStream = new FileInputStream(pathtoFXML);
        AnchorPane root = (AnchorPane) loader.load(fxmlStream);
        Scene sc = new Scene(root);
        Main.mainStage.setScene(sc);
    }
}
