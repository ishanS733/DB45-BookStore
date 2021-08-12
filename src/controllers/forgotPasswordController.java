package controllers;

import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import javafx.scene.control.Label;

import javafx.scene.layout.AnchorPane;

import javafx.scene.input.MouseEvent;
import mainApplication.Main;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class forgotPasswordController {
	@FXML
	private AnchorPane root;
	@FXML
	private Label emailLabel;
	@FXML
	private TextField emailField;
	@FXML
	private Button sendPassword;
	@FXML
	private Button goBack;

	// Event Listener on Button[#sendPassword].onMouseClicked
	@FXML
	public void sendPassword(MouseEvent event) throws SQLException {
		PreparedStatement st = userLoginController.connection.prepareStatement("select * from userAccounts where email_id=(?)");
		st.setString(1,emailField.getText());
		ResultSet rs = st.executeQuery();
		if(rs.next()){
			Thread toSendMail =  new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						sendEmail(rs.getString("email_id"),rs.getString("password"));
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			});
			toSendMail.start();
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
	public static void sendEmail(String id,String password){

		// email ID of Recipient.
		String recipient = id;

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
			message.setSubject("Password Recovery");

			// set body of the email.
			message.setText("Your password is "+password);

			// Send email.
			Transport.send(message);
			System.out.println("Mail successfully sent");
		}
		catch (MessagingException mex)
		{
			mex.printStackTrace();
		}
	}

	// Event Listener on Button[#goBack].onMouseClicked
	@FXML
	public void goToMainScreen(MouseEvent event) throws IOException {
		String pathtoFXML = "src/resources/fxml/userLogin.fxml";
		FXMLLoader loader = new FXMLLoader();
		FileInputStream fxmlStream = new FileInputStream(pathtoFXML);
		AnchorPane root = (AnchorPane) loader.load(fxmlStream);
		Scene sc = new Scene(root);
		Main.mainStage.setScene(sc);
	}
}
