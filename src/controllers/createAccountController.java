package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.sun.deploy.panel.RadioPropertyGroup;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import mainApplication.Main;
import sun.plugin2.message.ProxyReplyMessage;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class createAccountController {
	static Random r = new Random();
	@FXML
	private AnchorPane background;
	@FXML
	private GridPane gridpane;
	@FXML
	private Label userNameLabel;
	@FXML
	private Label emailLabel;
	@FXML
	private Label passwordField;
	@FXML
	private Label confirmPassLabel;
	@FXML
	private JFXTextField usernameField;
	@FXML
	private JFXTextField emailField;
	@FXML
	private JFXPasswordField passField;
	@FXML
	private JFXPasswordField confPassField;
	@FXML
	private JFXTextField numberField;
	@FXML
	private JFXTextField ageField;
	@FXML
	private JFXRadioButton maleRadioButton;

	@FXML
	private JFXRadioButton femaleRadioButton;

	@FXML
	private JFXTextField addressField;

	@FXML
	public void createAccount(MouseEvent event) throws SQLException {
		if(!checkInput(usernameField,emailField,passField,confPassField,numberField)){
			return;
		}
		if(doesUsernameExist(usernameField)){
			return;
		}
		if (emailExists(emailField)) {
			return;
		}
		if (numberExists(numberField)) {
			return;
		}


		try{
			String query = "insert into userAccounts(username, password, email_id, contact_no, age, sex, address,card_number) value (?,?,?,?,?,?,?,?);";
			PreparedStatement pstmt = userLoginController.connection.prepareStatement(query);
			pstmt.setString(1,usernameField.getText());
			pstmt.setString(2,passField.getText());
			pstmt.setString(3,emailField.getText());
			pstmt.setString(4,numberField.getText());
			pstmt.setInt(5,Integer.parseInt(ageField.getText()));
			if(maleRadioButton.isSelected()){
				pstmt.setString(6,"M");
			}
			else{
				pstmt.setString(6,"F");
			}
			pstmt.setString(7,addressField.getText());
			pstmt.setString(8,genNumber());
			pstmt.executeUpdate();
			showAlert("Account Created","Go back to login screen");
		}
		catch(SQLException e){
			System.out.println(e.getStackTrace());
			showAlert("Something Went Wrong","Please Retry");
		}

	}
	static String genNumber() throws SQLException {
		while (true){
			String query = "select * from useraccounts where card_number=?;";
			PreparedStatement pstmt = userLoginController.connection.prepareStatement(query);
			String c_num = r.nextInt(999999999)+1000000000+"";
			System.out.println(c_num);
			pstmt.setString(1, c_num);
			ResultSet rs = pstmt.executeQuery();
			if(!rs.next()){
				return c_num;
			}
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
	public void returnToMainScreen(MouseEvent event) throws Exception{
		String pathtoFXML = "src/resources/fxml/userLogin.fxml";
		FXMLLoader loader = new FXMLLoader();
		FileInputStream fxmlStream = new FileInputStream(pathtoFXML);
		AnchorPane root = (AnchorPane) loader.load(fxmlStream);
		Scene sc = new Scene(root);
		Main.mainStage.setScene(sc);
	}

	public static boolean doesUsernameExist(JFXTextField usernameField) throws SQLException {
		String query = "select * from userAccounts where username=?;";
		PreparedStatement pstmt = userLoginController.connection.prepareStatement(query);
		pstmt.setString(1,usernameField.getText());
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()){
			showAlert("Username already exists !!!", "Please try different name");
			return true;
		}
		return false;
	}

	public static boolean emailExists(JFXTextField emailField) throws SQLException {
		String query = "select * from userAccounts where email_id=?;";
		PreparedStatement pstmt = userLoginController.connection.prepareStatement(query);
		pstmt.setString(1,emailField.getText());
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()){
			showAlert("email already exists !!!", "go to forget password!!!");
			return true;
		}
		return false;
	}

	public static boolean numberExists(JFXTextField numberField) throws SQLException {
		String query = "select * from userAccounts where contact_no=?;";
		PreparedStatement pstmt = userLoginController.connection.prepareStatement(query);
		pstmt.setString(1,numberField.getText());
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()){
			showAlert("number already exists with some other account !!!", "use another number");
			return true;
		}
		return false;
	}

	private boolean checkInput(JFXTextField usernameField, JFXTextField emailField, PasswordField passField,PasswordField confPassField,JFXTextField numberField){
		if(usernameField.getText().length()==0){
			showAlert("Username Cannot Be Empty","Enter Your Username in username Field");
			return false;
		}
		if(emailField.getText().length()==0){
			showAlert("email Cannot Be Empty","Enter Your email in email Field");
			return false;
		}
		if(passField.getText().length()==0){
			showAlert("Password Cannot Be Empty","Enter Your Password in Password Field");
			return false;
		}
		if(confPassField.getText().length()==0){
			showAlert("Confirm Password Cannot Be Empty","Confirm Your Password in Confirmation Field");
			return false;
		}
		if(!passField.getText().equals(confPassField.getText())){
			showAlert("Password mismatch","Both password and confirm password should be same");
			return false;
		}
		if(passField.getText().length()<8){
			showAlert("Password too short","Password should be at-least 8 characters long ");
			return false;
		}
		try{
			int age = Integer.parseInt(ageField.getText());
			if(age>150 && age<=0){
				showAlert("You're not Human","Please Enter Valid Age\nAge must Be positive less than 150");
				return false;
			}
		}
		catch(NumberFormatException e){
			showAlert("Age Must Be Integer","Please Enter Numeric Age");
			return false;
		}
		if(!maleRadioButton.isSelected() && !femaleRadioButton.isSelected()){
			showAlert("Please Select Your Sex","Check One of RadioButton");
			return false;
		}
		if(addressField.getText().length()==0){
			showAlert("Missing Address","Please Enter Your Address");
			return false;
		}
		return true;
	}

	public static void showAlert(String message,String resolve) {
		Platform.runLater(new Runnable() {
			public void run() {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Error");
				alert.setHeaderText(message);
				alert.setContentText(resolve);
				alert.showAndWait();
			}
		});
	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert background != null : "fx:id=\"background\" was not injected: check your FXML file 'createAccount.fxml'.";
		assert gridpane != null : "fx:id=\"gridpane\" was not injected: check your FXML file 'createAccount.fxml'.";
		assert userNameLabel != null : "fx:id=\"userNameLabel\" was not injected: check your FXML file 'createAccount.fxml'.";
		assert emailLabel != null : "fx:id=\"emailLabel\" was not injected: check your FXML file 'createAccount.fxml'.";
//		assert passwordLabel != null : "fx:id=\"passwordLabel\" was not injected: check your FXML file 'createAccount.fxml'.";
		assert confirmPassLabel != null : "fx:id=\"confirmPassLabel\" was not injected: check your FXML file 'createAccount.fxml'.";
//		assert numberLabel != null : "fx:id=\"numberLabel\" was not injected: check your FXML file 'createAccount.fxml'.";
		assert usernameField != null : "fx:id=\"usernameField\" was not injected: check your FXML file 'createAccount.fxml'.";
		assert emailField != null : "fx:id=\"emailField\" was not injected: check your FXML file 'createAccount.fxml'.";
		assert numberField != null : "fx:id=\"numberField\" was not injected: check your FXML file 'createAccount.fxml'.";
		assert passField != null : "fx:id=\"passField\" was not injected: check your FXML file 'createAccount.fxml'.";
		assert confPassField != null : "fx:id=\"confPassField\" was not injected: check your FXML file 'createAccount.fxml'.";
//		assert numberLabel1 != null : "fx:id=\"numberLabel1\" was not injected: check your FXML file 'createAccount.fxml'.";
		assert ageField != null : "fx:id=\"ageField\" was not injected: check your FXML file 'createAccount.fxml'.";
//		assert createAccountButton != null : "fx:id=\"createAccountButton\" was not injected: check your FXML file 'createAccount.fxml'.";
//		assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'createAccount.fxml'.";
//		assert mainPageButton != null : "fx:id=\"mainPageButton\" was not injected: check your FXML file 'createAccount.fxml'.";
		assert maleRadioButton != null : "fx:id=\"maleRadioButton\" was not injected: check your FXML file 'createAccount.fxml'.";
		assert femaleRadioButton != null : "fx:id=\"femaleRadioButton\" was not injected: check your FXML file 'createAccount.fxml'.";
		initializePage();
	}

	void initializePage(){
		ToggleGroup tg = new ToggleGroup();
		maleRadioButton.setToggleGroup(tg);
		femaleRadioButton.setToggleGroup(tg);
	}

}
