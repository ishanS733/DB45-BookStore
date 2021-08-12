/**
 * Sample Skeleton for 'yourReviews.fxml' Controller Class
 */

package controllers;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import mainApplication.yourReviews;

public class yourReviewsController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="home"
    private JFXButton home; // Value injected by FXMLLoader

    @FXML // fx:id="table"
    private TableView<yourReviews> table; // Value injected by FXMLLoader

    @FXML // fx:id="removeReviewButton"
    private JFXButton removeReviewButton; // Value injected by FXMLLoader

    @FXML // fx:id="userWelcomeLabel"
    private Label userWelcomeLabel; // Value injected by FXMLLoader

    @FXML // fx:id="user_icon"
    private FontAwesomeIconView user_icon; // Value injected by FXMLLoader

    @FXML
    public void RemoveButtonPressed(MouseEvent event) throws SQLException {
        yourReviews selectedReview = table.getSelectionModel().getSelectedItem();
        if(selectedReview==null){
            createAccountController.showAlert("No Item Selected","Please Select Some Review To Remove");
            return;
        }
        String query = "delete from comments where c_id=?;";
        PreparedStatement pstmt = userLoginController.connection.prepareStatement(query);
        pstmt.setInt(1, selectedReview.getC_id());
        pstmt.executeUpdate();
        createAccountController.showAlert("Removed Succesfully","Your Review Removed Succesfully");
        initializeTable();
    }

    @FXML
    void homePressed(MouseEvent event) throws IOException {
        userLoginController.changeScene("home");
    }

    @FXML
    void tablePressed(MouseEvent event) {

    }

    @FXML
    void user_iconPressed(MouseEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws SQLException {
        assert home != null : "fx:id=\"home\" was not injected: check your FXML file 'yourReviews.fxml'.";
        assert table != null : "fx:id=\"table\" was not injected: check your FXML file 'yourReviews.fxml'.";
        assert removeReviewButton != null : "fx:id=\"removeReviewButton\" was not injected: check your FXML file 'yourReviews.fxml'.";
        assert userWelcomeLabel != null : "fx:id=\"userWelcomeLabel\" was not injected: check your FXML file 'yourReviews.fxml'.";
        assert user_icon != null : "fx:id=\"user_icon\" was not injected: check your FXML file 'yourReviews.fxml'.";
        userWelcomeLabel.setText("Hi, "+userLoginController.currUser.getUsername());
        initializeTable();
    }

    void initializeTable() throws SQLException {
        TableColumn<yourReviews, Integer> c1 = new TableColumn<>();
        c1.setCellValueFactory(new PropertyValueFactory<yourReviews,Integer>("c_id"));
        c1.setText("#");

        TableColumn<yourReviews, Integer> c2 = new TableColumn<>();
        c2.setCellValueFactory(new PropertyValueFactory<yourReviews,Integer>("b_id"));
        c2.setText("book Id");

        TableColumn<yourReviews, String> c3 = new TableColumn<>();
        c3.setCellValueFactory(new PropertyValueFactory<yourReviews,String>("b_name"));
        c3.setText("Book Name");

        TableColumn<yourReviews, Integer> c4 = new TableColumn<>();
        c4.setCellValueFactory(new PropertyValueFactory<yourReviews,Integer>("Rating"));
        c4.setText("Rating");

        TableColumn<yourReviews, String> c5 = new TableColumn<>();
        c5.setCellValueFactory(new PropertyValueFactory<yourReviews,String>("comment"));
        c5.setText("Your Comment");

        table.getColumns().setAll(c1,c2,c3,c4,c5);
        table.setItems(yourReviews.getAllReviews());


    }
}
