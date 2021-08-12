package controllers;

import java.io.File;
import java.io.FileInputStream;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import mainApplication.Main;
import mainApplication.User;

import java.io.IOException;
import java.sql.*;

public class userLoginController {

    @FXML
    private Label userNameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private JFXTextField userField;
    @FXML
    private JFXPasswordField passwordField;
    @FXML
    private JFXButton loginButton;
    @FXML
    private JFXButton createAccountButton;

    public static Statement executer;

    public static Connection connection;
    public static User currUser;


    public static void userSetup() throws Exception {
        userLoginController.connectToDatabase();
        if (!CheckUserExists()) {
            String pathtoFXML = "src/resources/fxml/userLogin.fxml";
            FXMLLoader loader = new FXMLLoader();
            FileInputStream fxmlStream = new FileInputStream(pathtoFXML);
            AnchorPane root = (AnchorPane) loader.load(fxmlStream);
            Scene sc = new Scene(root);
            Main.mainStage.setScene(sc);
        } else {
            System.out.println("Batman");
            changeScene("home");
        }
    }

    public static void connectToDatabase() throws SQLException {
        String host = "jdbc:mysql://localhost:3306/dbms";
        String user = "hardik";
        String Password = "hardik";
        Connection con = null;
        try {
            con = DriverManager.getConnection(host, user, Password);
            userLoginController.executer = con.createStatement();
            userLoginController.connection = con;
            System.out.println("Connected to database");
        } catch (SQLException e) {
            System.out.println("Database is not setup");
        }
    }



    public void onMainPageClicked(MouseEvent mouseEvent) throws IOException {
        String pathtoFXML = "src/resources/fxml/Main.fxml";
        FXMLLoader loader = new FXMLLoader();
        FileInputStream fxmlStream = new FileInputStream(pathtoFXML);
        AnchorPane root = (AnchorPane) loader.load(fxmlStream);
        Scene sc = new Scene(root);
        Main.mainStage.setScene(sc);
    }
    @FXML
    public void onLoginClicked(MouseEvent event) throws SQLException, IOException {
        String username = userField.getText();
        String password = passwordField.getText();
        boolean isValidUser = authenticate(username, password);
        if (isValidUser) {
            User local = createLocalSession(username);
            local.serialize();
            changeScene("home");
        } else {
            System.out.println("User not found");
        }
    }


    public static User createLocalSession(String username) throws SQLException {
        String query = "Select * from userAccounts where username=?;";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, username);
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        User u = new User(rs.getInt("u_id"), rs.getString("username"), rs.getString("password"),
                rs.getString("email_id"), rs.getString("contact_no"), rs.getInt("age"),
                rs.getString("sex"));
        userLoginController.currUser = u;
        return u;
    }

    @FXML
    public void forgotPasswordScreen(MouseEvent event) throws SQLException, IOException {
        changeScene("forgotPassword");
    }

    private boolean authenticate(String username, String password) throws SQLException {
        String query = "Select * from userAccounts where username='" + username + "' and password='" + password + "';";
        ResultSet rs = executer.executeQuery(query);
        if (!rs.next()) {
            return false;
        }
        return true;
    }

    // Event Listener on Button[#createAccountButton].onMouseClicked
    @FXML
    public void onSignUp(MouseEvent event) throws IOException {
        changeScene("createAccount");
    }


    static boolean CheckUserExists() throws IOException {
        File fs = null;
        fs = new File("src/mainApplication/currUser");
        if (!fs.exists()) {
            return false;
        }
        User.deserialize();
        System.out.println(userLoginController.currUser);
        return true;
    }


    public static void changeScene(String sceneName) throws IOException {
        String pathtoFXML = "src/resources/fxml/" + sceneName + ".fxml";
        FXMLLoader loader = new FXMLLoader();
        FileInputStream fxmlStream = new FileInputStream(pathtoFXML);
        AnchorPane root = (AnchorPane) loader.load(fxmlStream);
        Scene sc = new Scene(root);
        if(sc == null) System.out.println("Secne null");
        if(Main.mainStage == null) System.out.println("Main stage null");
        Main.mainStage.setScene(sc);
    }
    public void goToCartPressed(MouseEvent mouseEvent) {
        System.out.println("Go To Cart presssed");
    }
}
