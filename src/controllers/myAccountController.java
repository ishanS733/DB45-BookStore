/**
 * Sample Skeleton for 'myAccount.fxml' Controller Class
 */

package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import mainApplication.Main;
import mainApplication.User;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class myAccountController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="home"
    private JFXButton home; // Value injected by FXMLLoader

    @FXML // fx:id="my_account"
    private JFXButton my_account; // Value injected by FXMLLoader

    @FXML // fx:id="profile"
    private JFXButton profile; // Value injected by FXMLLoader

    @FXML // fx:id="wallet"
    private JFXButton wallet; // Value injected by FXMLLoader

    @FXML // fx:id="wishlist"
    private JFXButton wishlist; // Value injected by FXMLLoader

    @FXML // fx:id="logout"
    private JFXButton logout; // Value injected by FXMLLoader

    @FXML // fx:id="userWelcomeLabel"
    private Label userWelcomeLabel; // Value injected by FXMLLoader

    @FXML // fx:id="user_icon"
    private FontAwesomeIconView user_icon; // Value injected by FXMLLoader

    @FXML // fx:id="usernameField"
    private JFXTextField usernameField; // Value injected by FXMLLoader

    @FXML // fx:id="passwordField"
    private JFXPasswordField passwordField; // Value injected by FXMLLoader

    @FXML // fx:id="emailField"
    private JFXTextField emailField; // Value injected by FXMLLoader

    @FXML // fx:id="contactField"
    private JFXTextField contactField; // Value injected by FXMLLoader

    @FXML // fx:id="user_icon1"
    private FontAwesomeIconView user_icon1; // Value injected by FXMLLoader

    @FXML // fx:id="boyRadioButton"
    private JFXRadioButton boyRadioButton; // Value injected by FXMLLoader

    @FXML // fx:id="user_icon2"
    private FontAwesomeIconView user_icon2; // Value injected by FXMLLoader

    @FXML // fx:id="girlRadioButton"
    private JFXRadioButton girlRadioButton; // Value injected by FXMLLoader

    @FXML // fx:id="showPasswordCheckBox"
    private JFXCheckBox showPasswordCheckBox; // Value injected by FXMLLoader

    @FXML // fx:id="changeUserButton"
    private JFXButton changeUserButton; // Value injected by FXMLLoader

    @FXML // fx:id="changePasswordButton"
    private JFXButton changePasswordButton; // Value injected by FXMLLoader

    @FXML // fx:id="changeEmailAddressButton"
    private JFXButton changeEmailAddressButton; // Value injected by FXMLLoader

    @FXML // fx:id="changeContactButton"
    private JFXButton changeContactButton; // Value injected by FXMLLoader

    @FXML // fx:id="changeSexButton"
    private JFXButton changeSexButton; // Value injected by FXMLLoader

    @FXML
    private HBox horBox;

    @FXML
    private Label visiblePassword;

    @FXML
    void changeContactButtonPressed(MouseEvent event) throws SQLException {
        if(changeContactButton.getText().equals(userLoginController.currUser.getUsername())){
            return;
        }
        try{
            String query = "update useraccounts set contact_no=? where u_id=?";
            PreparedStatement pstmt = userLoginController.connection.prepareStatement(query);
            pstmt.setString(1,contactField.getText());
            pstmt.setInt(2, userLoginController.currUser.getU_id());
            pstmt.executeUpdate();
            userLoginController.currUser.setContact_no(contactField.getText());
            User temp = userLoginController.currUser.copy();
            temp.serialize();
            User.deserialize();
        } catch (IOException e) {
            e.printStackTrace();
        } catch(SQLException e){
            createAccountController.showAlert("Some Went Wrong","Please Fill Valid Contact Number");
        }
        finally {
            initializeFields();
        }
    }

    @FXML
    void changeEmailAddressButtonPressed(MouseEvent event) {
        if(changeEmailAddressButton.getText().equals(userLoginController.currUser.getEmail_id())){
            return;
        }
        try{
            String query = "update useraccounts set email_id=? where u_id=?";
            PreparedStatement pstmt = userLoginController.connection.prepareStatement(query);
            pstmt.setString(1,emailField.getText());
            pstmt.setInt(2, userLoginController.currUser.getU_id());
            pstmt.executeUpdate();
            userLoginController.currUser.setEmail_id(emailField.getText());
            User temp = userLoginController.currUser.copy();
            temp.serialize();
            User.deserialize();
        } catch (IOException e) {
            e.printStackTrace();
        } catch(SQLException e){
            createAccountController.showAlert("Some Went Wrong","Please Fill Valid Email Address");
        }
        finally {
            initializeFields();
        }
    }

    @FXML
    void changePasswordButtonPressed(MouseEvent event) {
        if(changePasswordButton.getText().equals(userLoginController.currUser.getPassword())){
            return;
        }
        try{
            String query = "update useraccounts set password=? where u_id=?";
            PreparedStatement pstmt = userLoginController.connection.prepareStatement(query);
            pstmt.setString(1,passwordField.getText());
            pstmt.setInt(2, userLoginController.currUser.getU_id());
            pstmt.executeUpdate();
            userLoginController.currUser.setPassword(passwordField.getText());
            User temp = userLoginController.currUser.copy();
            temp.serialize();
            User.deserialize();
            Thread t = new Thread(()->{
                sendEmail("Password Changed","Your Password Has Been Changed");
            });
            t.start();
        } catch (IOException e) {
            e.printStackTrace();
        } catch(SQLException e){
            createAccountController.showAlert("Some Went Wrong","Please Fill Valid Password");
            Thread t = new Thread(()->{
                sendEmail("Attempt To Change Password","Someone Tried to Change Password\nAttempt Status: Failed");
            });
            t.start();
        }
        finally {
            initializeFields();
        }
    }

    @FXML
    void changeSexButtonPressed(MouseEvent event) {
        if(!boyRadioButton.isSelected() && !girlRadioButton.isSelected()){
            createAccountController.showAlert("Please Select Something First", "Click On Either of the buttons");
            return;
        }
        if(boyRadioButton.isSelected() && userLoginController.currUser.getSex().equals("M")){
            return;
        }
        else if(girlRadioButton.isSelected() && userLoginController.currUser.getSex().equals("F")){
            return;
        }
        try{
            String query = "update useraccounts set sex=? where u_id=?";
            PreparedStatement pstmt = userLoginController.connection.prepareStatement(query);
            if(boyRadioButton.isSelected()){
                pstmt.setString(1,"M");
            }
            else{
                pstmt.setString(1,"F");
            }
            pstmt.setInt(2, userLoginController.currUser.getU_id());
            pstmt.executeUpdate();
            if(boyRadioButton.isSelected()){
                userLoginController.currUser.setSex("M");
            }
            if(girlRadioButton.isSelected()){
                userLoginController.currUser.setSex("F");
            }
            User temp = userLoginController.currUser.copy();
            temp.serialize();
            User.deserialize();
        } catch (IOException e) {
            e.printStackTrace();
        } catch(SQLException e){
            createAccountController.showAlert("Some Went Wrong","Please Fill Valid Sex");
        }
        finally {
            initializeFields();
        }
    }

    @FXML
    void changeUserButtonPressed(MouseEvent event) {
        if(usernameField.getText().equals(userLoginController.currUser.getUsername())){
            return;
        }
        try{
            String query = "update useraccounts set username=? where u_id=?";
            PreparedStatement pstmt = userLoginController.connection.prepareStatement(query);
            pstmt.setString(1,usernameField.getText());
            pstmt.setInt(2, userLoginController.currUser.getU_id());
            pstmt.executeUpdate();
            userLoginController.currUser.setUsername(usernameField.getText());
            User temp = userLoginController.currUser.copy();
            temp.serialize();
            User.deserialize();
        } catch (IOException e) {
            e.printStackTrace();
        } catch(SQLException e){
            createAccountController.showAlert("Some Went Wrong","Please Fill Valid Email Address");
        }
        finally {
            initializeFields();
        }
    }

    @FXML
    void homePressed(MouseEvent event) throws IOException {
        userLoginController.changeScene("home");
    }


    @FXML
    void showPasswordPressed(MouseEvent event) {
        if(showPasswordCheckBox.isSelected()){
            visiblePassword.setVisible(true);
        }
        else{
            visiblePassword.setVisible(false);
        }
    }

    @FXML
    void logoutPressed(MouseEvent event) throws IOException {
        System.out.println("logoutPressed");
        userLoginController.changeScene("Main");
        File fs = new File("src/mainApplication/currUser");
        if (fs.exists()) {
            fs.delete();
        }

    }

    @FXML
    void my_accountPressed(MouseEvent event) {
        System.out.println("my_accountPressed");
    }

    @FXML
    void profilePressed(MouseEvent event) {
        System.out.println("profilePressed");
    }

    @FXML
    void user_iconPressed(MouseEvent event) {

    }

    @FXML
    void walletPressed(MouseEvent event) throws IOException {
        System.out.println("walletPressed");
        Main.changeScene("Wallet");
    }

    @FXML
    void wishlistPressed(MouseEvent event) {
        System.out.println("wishlistPressed");
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert home != null : "fx:id=\"home\" was not injected: check your FXML file 'myAccount.fxml'.";
        assert my_account != null : "fx:id=\"my_account\" was not injected: check your FXML file 'myAccount.fxml'.";
        assert profile != null : "fx:id=\"profile\" was not injected: check your FXML file 'myAccount.fxml'.";
        assert wallet != null : "fx:id=\"wallet\" was not injected: check your FXML file 'myAccount.fxml'.";
        assert wishlist != null : "fx:id=\"wishlist\" was not injected: check your FXML file 'myAccount.fxml'.";
        assert logout != null : "fx:id=\"logout\" was not injected: check your FXML file 'myAccount.fxml'.";
        assert userWelcomeLabel != null : "fx:id=\"userWelcomeLabel\" was not injected: check your FXML file 'myAccount.fxml'.";
        assert user_icon != null : "fx:id=\"user_icon\" was not injected: check your FXML file 'myAccount.fxml'.";
        assert usernameField != null : "fx:id=\"usernameField\" was not injected: check your FXML file 'myAccount.fxml'.";
        assert passwordField != null : "fx:id=\"passwordField\" was not injected: check your FXML file 'myAccount.fxml'.";
        assert emailField != null : "fx:id=\"emailField\" was not injected: check your FXML file 'myAccount.fxml'.";
        assert contactField != null : "fx:id=\"contactField\" was not injected: check your FXML file 'myAccount.fxml'.";
        assert user_icon1 != null : "fx:id=\"user_icon1\" was not injected: check your FXML file 'myAccount.fxml'.";
        assert boyRadioButton != null : "fx:id=\"boyRadioButton\" was not injected: check your FXML file 'myAccount.fxml'.";
        assert user_icon2 != null : "fx:id=\"user_icon2\" was not injected: check your FXML file 'myAccount.fxml'.";
        assert girlRadioButton != null : "fx:id=\"girlRadioButton\" was not injected: check your FXML file 'myAccount.fxml'.";
        assert showPasswordCheckBox != null : "fx:id=\"showPasswordCheckBox\" was not injected: check your FXML file 'myAccount.fxml'.";
        assert changeUserButton != null : "fx:id=\"changeUserButton\" was not injected: check your FXML file 'myAccount.fxml'.";
        assert changePasswordButton != null : "fx:id=\"changePasswordButton\" was not injected: check your FXML file 'myAccount.fxml'.";
        assert changeEmailAddressButton != null : "fx:id=\"changeEmailAddressButton\" was not injected: check your FXML file 'myAccount.fxml'.";
        assert changeContactButton != null : "fx:id=\"changeContactButton\" was not injected: check your FXML file 'myAccount.fxml'.";
        assert changeSexButton != null : "fx:id=\"changeSexButton\" was not injected: check your FXML file 'myAccount.fxml'.";
        initializeFields();
    }

    void initializeFields(){
        usernameField.setText(userLoginController.currUser.getUsername());
        passwordField.setText(userLoginController.currUser.getPassword());
        contactField.setText(userLoginController.currUser.getContact_no());
        emailField.setText(userLoginController.currUser.getEmail_id());
        if(userLoginController.currUser.getSex().equals("M")){
            boyRadioButton.setSelected(true);
        }
        else if(userLoginController.currUser.getSex().equals("F")){
            girlRadioButton.setSelected(true);
        }
        ToggleGroup tg = new ToggleGroup();
        boyRadioButton.setToggleGroup(tg);
        girlRadioButton.setToggleGroup(tg);
        horBox.setSpacing(30);
        userWelcomeLabel.setText("Hi, "+userLoginController.currUser.getUsername());
        visiblePassword.setText(passwordField.getText());
        passwordField.setOnKeyReleased(e->{
            visiblePassword.setText(passwordField.getText());
        });
    }

    public static void sendEmail(String subject, String messageToSend){

        // email ID of Recipient.
        String recipient = userLoginController.currUser.getEmail_id();

        // using host as localhost
        String host = "localhost";

        // Getting system properties
        Properties properties = System.getProperties();
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","587");

        String username = "books.paradise45@gmail.com";
        String userPassword = "dbms@045";

        // creating session object to get properties
        Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username,userPassword);
            }
        });

        try
        {
            // MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From Field: adding senders email to from field.
            message.setFrom(new InternetAddress(username));

            // Set To Field: adding recipient's email to from field.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

            // Set Subject: subject of the email
            message.setSubject(subject);

            // set body of the email.
            message.setText(messageToSend);

            // Send email.
            Transport.send(message);
            System.out.println("Mail successfully sent");
        }
        catch (MessagingException mex)
        {
            mex.printStackTrace();
        }
    }

}
