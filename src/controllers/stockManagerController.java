/**
 * Sample Skeleton for 'stockManagerLogin.fxml' Controller Class
 */

package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import mainApplication.Main;

public class stockManagerController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="rootAnchorPane"
    private AnchorPane rootAnchorPane; // Value injected by FXMLLoader

    @FXML // fx:id="stockManagerField"
    private JFXTextField stockManagerField; // Value injected by FXMLLoader

    @FXML // fx:id="passwordField"
    private JFXPasswordField passwordField; // Value injected by FXMLLoader

    @FXML // fx:id="loginButton"
    private JFXButton loginButton; // Value injected by FXMLLoader

    boolean checkInput(){
        int userId;
        try{
            userId = Integer.parseInt(stockManagerField.getText());
        }
        catch (Exception e){
            createAccountController.showAlert("Invalid ID","Stock Manager ID must Be Integer");
            return false;
        }
        String pass = passwordField.getText();
        if(pass.length()==0){
            createAccountController.showAlert("Too Short Password","Password should not be empty");
            return false;
        }
        return true;
    }

    @FXML
    void onLoginClicked(MouseEvent event) throws IOException, SQLException {
        if(!checkInput()){
            return;
        }
        String query = "Select * from stock_manager where stock_id=? and password=sha(?);";
        PreparedStatement pstmt = userLoginController.connection.prepareStatement(query);
        pstmt.setInt(1,Integer.parseInt(stockManagerField.getText()));
        pstmt.setString(2,passwordField.getText());
        ResultSet rs = pstmt.executeQuery();
        if(rs.next()){
            userLoginController.changeScene("stockManagerHome");
        }
        else{
            createAccountController.showAlert("Invalid Credentials","Please Try Again !!!");
        }
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert rootAnchorPane != null : "fx:id=\"rootAnchorPane\" was not injected: check your FXML file 'stockManagerLogin.fxml'.";
        assert stockManagerField != null : "fx:id=\"stockManagerField\" was not injected: check your FXML file 'stockManagerLogin.fxml'.";
        assert passwordField != null : "fx:id=\"passwordField\" was not injected: check your FXML file 'stockManagerLogin.fxml'.";
        assert loginButton != null : "fx:id=\"loginButton\" was not injected: check your FXML file 'stockManagerLogin.fxml'.";

    }

    public void onMainPageClicked(MouseEvent mouseEvent) throws IOException {
        Main.changeScene("Main");
    }
}
