package controllers;
/**
 * Sample Skeleton for 'authorLogin.fxml' Controller Class
 */



import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import mainApplication.Author;
import mainApplication.Main;

public class AuthorLoginController {



    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="authorField"
    private JFXTextField authorField; // Value injected by FXMLLoader

    @FXML // fx:id="passwordField"
    private JFXPasswordField passwordField; // Value injected by FXMLLoader

    @FXML // fx:id="author_icon"
    private FontAwesomeIconView author_icon; // Value injected by FXMLLoader

    @FXML // fx:id="lock_icon"
    private FontAwesomeIconView lock_icon; // Value injected by FXMLLoader

    @FXML // fx:id="forgotPasswordButton"
    private JFXButton forgotPasswordButton; // Value injected by FXMLLoader

    @FXML // fx:id="logo"
    private FontAwesomeIconView logo; // Value injected by FXMLLoader

    @FXML // fx:id="loginButton"
    private JFXButton loginButton; // Value injected by FXMLLoader

    @FXML // fx:id="createAccountButton"
    private JFXButton createAccountButton; // Value injected by FXMLLoader

    @FXML
    void authorFieldPressed(MouseEvent event) {

    }

    @FXML
    void author_iconPressed(MouseEvent event) {

    }


    @FXML
    void lock_iconPressed(MouseEvent event) {

    }

    @FXML
    void logoPressed(MouseEvent event) {

    }

    @FXML
    void onLoginClicked(MouseEvent event) throws SQLException, IOException {
        if(!authenticate(authorField.getText(), passwordField.getText())) return;
        Main.changeScene("AuthorInfo");
    }

    private boolean authenticate(String username, String password) throws SQLException {
        String query = "Select * from authors where auth_id = " + username + " and password = '" + password + "';";
        System.out.println("authenticating");
        ResultSet rs = userLoginController.executer.executeQuery(query);
        if (!rs.next()) {
            createAccountController.showAlert("Authentication Failed", "Invalid Entry");
            return false;
        }
        Author.auth_name = rs.getString("auth_name");
        Author.auth_id = rs.getInt("auth_id");
        return true;
    }

    @FXML
    void passwordFieldPressed(MouseEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert authorField != null : "fx:id=\"authorField\" was not injected: check your FXML file 'authorLogin.fxml'.";
        assert passwordField != null : "fx:id=\"passwordField\" was not injected: check your FXML file 'authorLogin.fxml'.";
        assert author_icon != null : "fx:id=\"author_icon\" was not injected: check your FXML file 'authorLogin.fxml'.";
        assert lock_icon != null : "fx:id=\"lock_icon\" was not injected: check your FXML file 'authorLogin.fxml'.";
        assert forgotPasswordButton != null : "fx:id=\"forgotPasswordButton\" was not injected: check your FXML file 'authorLogin.fxml'.";
        assert logo != null : "fx:id=\"logo\" was not injected: check your FXML file 'authorLogin.fxml'.";
        assert loginButton != null : "fx:id=\"loginButton\" was not injected: check your FXML file 'authorLogin.fxml'.";
        assert createAccountButton != null : "fx:id=\"createAccountButton\" was not injected: check your FXML file 'authorLogin.fxml'.";

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
