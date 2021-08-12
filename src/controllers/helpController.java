package controllers;
/**
 * Sample Skeleton for 'help.fxml' Controller Class
 */


import com.jfoenix.controls.JFXButton;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextArea;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import mainApplication.queries;


public class helpController {


    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="home"
    private JFXButton home; // Value injected by FXMLLoader

    @FXML // fx:id="queryPanel"
    private JFXTextArea queryPanel; // Value injected by FXMLLoader

    @FXML // fx:id="submitButton"
    private JFXButton submitButton; // Value injected by FXMLLoader

    @FXML // fx:id="previousQueriesTable"
    private TableView<queries> previousQueriesTable; // Value injected by FXMLLoader

    @FXML
    void homePressed(MouseEvent event) throws IOException {
        userLoginController.changeScene("home");
    }

    @FXML
    void submitButtonPressed(MouseEvent event) throws SQLException {
        if(queryPanel.getText().length()<10){
            createAccountController.showAlert("Too Short Description","Please Be A bit more Descriptive\nDescription Length Must Be Greater than 10");
            return;
        }
        try{
            String query = "insert into userqueries (u_id, userQuery) value (?,?);";
            PreparedStatement pstmt = userLoginController.connection.prepareStatement(query);
            pstmt.setInt(1,userLoginController.currUser.getU_id());
            pstmt.setString(2,queryPanel.getText());
            pstmt.executeUpdate();
            initializetable();
            createAccountController.showAlert("Query Submitted SuccesFully","Please Wait For Response");
        }
        catch (SQLException e){
            createAccountController.showAlert("Something Went Wrong","May be You're Bit to Expressive");
        }
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws SQLException {
        assert home != null : "fx:id=\"home\" was not injected: check your FXML file 'help.fxml'.";
        assert submitButton != null : "fx:id=\"submitButton\" was not injected: check your FXML file 'help.fxml'.";
        initializetable();
    }

    void initializetable() throws SQLException {
        String query = "select * from userqueries where u_id=?;";
        PreparedStatement pstmt = userLoginController.connection.prepareStatement(query);
        pstmt.setInt(1, userLoginController.currUser.getU_id());
        ResultSet rs = pstmt.executeQuery();
        ObservableList<queries> prevQueries = FXCollections.observableArrayList();
        while(rs.next()){
            prevQueries.add(new queries(rs.getString("userQuery"),rs.getString("queryReply")));
        }
        TableColumn<queries, String> c1 = new TableColumn<queries, String>();
        c1.setCellValueFactory(new PropertyValueFactory<queries, String>("userQuery"));
        c1.setText("Query");

        TableColumn<queries, String> c2 = new TableColumn<queries, String>();
        c2.setCellValueFactory(new PropertyValueFactory<queries, String>("queryReply"));
        c2.setText("Reply");

        previousQueriesTable.getColumns().setAll(c1, c2);
        previousQueriesTable.setItems(prevQueries);
    }
}
