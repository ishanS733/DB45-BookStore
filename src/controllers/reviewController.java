/**
 * Sample Skeleton for 'reviewScreen.fxml' Controller Class
 */

package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class reviewController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="home"
    private JFXButton home; // Value injected by FXMLLoader

    @FXML // fx:id="userWelcomeLabel"
    private Label userWelcomeLabel; // Value injected by FXMLLoader

    @FXML // fx:id="user_icon"
    private FontAwesomeIconView user_icon; // Value injected by FXMLLoader

    @FXML // fx:id="search_bar"
    private JFXTextField bookID; // Value injected by FXMLLoader

    @FXML // fx:id="queryPanel"
    private JFXTextArea userReview; // Value injected by FXMLLoader

    @FXML // fx:id="Ratingchoice"
    private ChoiceBox<Integer> Ratingchoice; // Value injected by FXMLLoader

    @FXML // fx:id="submitButton"
    private JFXButton submitButton; // Value injected by FXMLLoader

    @FXML
    void homePressed(MouseEvent event) throws IOException {
        userLoginController.changeScene("home");
    }

    @FXML
    void search_barPressed(MouseEvent event) {

    }

    @FXML
    void submitButtonPressed(MouseEvent event) throws SQLException {
        if(!hasPurchasedBook()){
            createAccountController.showAlert("You haven't Purchased This Book","You Should not give False Review\nWhen you haven't read book");
            return;
        }
        if(userReview.getText().length()==0){
            createAccountController.showAlert("Please Be More Descriptive","Don't Leave Empty Comment");
            return;
        }
        String query = "INSERT INTO COMMENTS (u_id,b_id,commentByUser, rating) value (?,?,?,?);";
        PreparedStatement pstmt = userLoginController.connection.prepareStatement(query);
        pstmt.setInt(1,userLoginController.currUser.getU_id());
        pstmt.setInt(2,Integer.parseInt(bookID.getText()));
        pstmt.setString(3, userReview.getText());
        pstmt.setInt(4,Ratingchoice.getValue());
        pstmt.executeUpdate();
        userReview.setText("");
        bookID.setText("");
        createAccountController.showAlert("Review Submitted","Thankyou For Review");
    }

    boolean hasPurchasedBook() throws SQLException {
        String query = "select * from all_order where u_id=? and b_id=?;";
        PreparedStatement pstmt = userLoginController.connection.prepareStatement(query);
        pstmt.setInt(1,userLoginController.currUser.getU_id());
        try{
            pstmt.setInt(2,Integer.parseInt(bookID.getText()));
        }
        catch(NumberFormatException e){
            createAccountController.showAlert("Please Enter Valid Integer","Book ID must Be Integer");
            return false;
        }
        ResultSet rs = pstmt.executeQuery();
        return rs.next();
    }

    @FXML
    void user_iconPressed(MouseEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert home != null : "fx:id=\"home\" was not injected: check your FXML file 'reviewScreen.fxml'.";
        assert userWelcomeLabel != null : "fx:id=\"userWelcomeLabel\" was not injected: check your FXML file 'reviewScreen.fxml'.";
        assert user_icon != null : "fx:id=\"user_icon\" was not injected: check your FXML file 'reviewScreen.fxml'.";
//        assert search_bar != null : "fx:id=\"search_bar\" was not injected: check your FXML file 'reviewScreen.fxml'.";
        assert userReview != null : "fx:id=\"queryPanel\" was not injected: check your FXML file 'reviewScreen.fxml'.";
        assert Ratingchoice != null : "fx:id=\"Ratingchoice\" was not injected: check your FXML file 'reviewScreen.fxml'.";
        assert submitButton != null : "fx:id=\"submitButton\" was not injected: check your FXML file 'reviewScreen.fxml'.";
        userWelcomeLabel.setText("Hi, "+userLoginController.currUser.getUsername());
        Ratingchoice.getItems().addAll(1,2,3,4,5);
        Ratingchoice.setValue(3);
    }
}
