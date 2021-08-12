/**
 * Sample Skeleton for 'stockManagerHome.fxml' Controller Class
 */

package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import mainApplication.HomeTable;

public class stockManagerHomeController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="stockHome"
    private JFXButton stockHome; // Value injected by FXMLLoader

    @FXML // fx:id="logout"
    private JFXButton logout; // Value injected by FXMLLoader

    @FXML // fx:id="stockTable"
    private TableView<HomeTable> stockTable; // Value injected by FXMLLoader

    @FXML // fx:id="userWelcomeLabel"
    private Label userWelcomeLabel; // Value injected by FXMLLoader

    @FXML // fx:id="user_icon"
    private FontAwesomeIconView user_icon; // Value injected by FXMLLoader

    @FXML // fx:id="ThresholdLabel"
    private Label ThresholdLabel; // Value injected by FXMLLoader

    @FXML // fx:id="thresholdField"
    private JFXTextField thresholdField; // Value injected by FXMLLoader

    @FXML // fx:id="getBelowThresholdButton"
    private JFXButton getBelowThresholdButton; // Value injected by FXMLLoader

    @FXML
    void getBelowThreshold(MouseEvent event) {
        int threshold;
        try{
            threshold = Integer.parseInt(thresholdField.getText());
            String query = "select * from books Natural join written_by natural join authors natural join book_genre natural join genre where num_copies<"+threshold+" group by b_id;";
            ResultSet rs = userLoginController.executer.executeQuery(query);
            initializeTable(rs);
        }
        catch(NumberFormatException | SQLException e){
            createAccountController.showAlert("Please Enter Valid Threshold","Threshold must be a valid Integer");
        }
    }

    @FXML
    void logoutPressed(MouseEvent event) throws IOException {
        userLoginController.changeScene("Main");
    }

    @FXML
    void stockHomePressed(MouseEvent event) {

    }

    @FXML
    void user_iconPressed(MouseEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws SQLException {
        assert stockHome != null : "fx:id=\"stockHome\" was not injected: check your FXML file 'stockManagerHome.fxml'.";
        assert logout != null : "fx:id=\"logout\" was not injected: check your FXML file 'stockManagerHome.fxml'.";
        assert stockTable != null : "fx:id=\"stockTable\" was not injected: check your FXML file 'stockManagerHome.fxml'.";
        assert userWelcomeLabel != null : "fx:id=\"userWelcomeLabel\" was not injected: check your FXML file 'stockManagerHome.fxml'.";
        assert user_icon != null : "fx:id=\"user_icon\" was not injected: check your FXML file 'stockManagerHome.fxml'.";
        assert ThresholdLabel != null : "fx:id=\"ThresholdLabel\" was not injected: check your FXML file 'stockManagerHome.fxml'.";
        assert thresholdField != null : "fx:id=\"thresholdField\" was not injected: check your FXML file 'stockManagerHome.fxml'.";
        assert getBelowThresholdButton != null : "fx:id=\"getBelowThresholdButton\" was not injected: check your FXML file 'stockManagerHome.fxml'.";
        ResultSet rs = userLoginController.executer.executeQuery("select * from books Natural join written_by natural join authors natural join book_genre natural join genre group by b_id;");
        userWelcomeLabel.setText("Welcome, Manager");
        initializeTable(rs);
    }

    void initializeTable(ResultSet rs) throws SQLException {
        ObservableList<HomeTable> data = HomeTable.getConditionedObservableListForHomeTable(rs);
        TableColumn<HomeTable, Integer> c1 = new TableColumn<HomeTable, Integer>();
        c1.setCellValueFactory(new PropertyValueFactory<HomeTable, Integer>("b_id"));
        c1.setText("#");

        TableColumn<HomeTable, String> c2 = new TableColumn<HomeTable, String>();
        c2.setCellValueFactory(new PropertyValueFactory<HomeTable, String>("b_name"));
        c2.setText("Name");

        TableColumn<HomeTable, String> c3 = new TableColumn<HomeTable, String>();
        c3.setCellValueFactory(new PropertyValueFactory<HomeTable, String>("auth_name"));
        c3.setText("Author");

        TableColumn<HomeTable, Integer> c4 = new TableColumn<HomeTable, Integer>();
        c4.setCellValueFactory(new PropertyValueFactory<HomeTable, Integer>("pub_year"));
        c4.setText("Year");

        TableColumn<HomeTable, String> c5 = new TableColumn<HomeTable, String>();
        c5.setCellValueFactory(new PropertyValueFactory<HomeTable, String>("genre"));
        c5.setText("Genre");

        TableColumn<HomeTable, Integer> c6 = new TableColumn<HomeTable, Integer>();
        c6.setCellValueFactory(new PropertyValueFactory<HomeTable, Integer>("num_pages"));
        c6.setText("Number of Pages");

        TableColumn<HomeTable, Integer> c7 = new TableColumn<HomeTable, Integer>();
        c7.setCellValueFactory(new PropertyValueFactory<HomeTable, Integer>("num_copies"));
        c7.setText("Number Of Copies");

        TableColumn<HomeTable, Integer> c8 = new TableColumn<HomeTable, Integer>();
        c8.setCellValueFactory(new PropertyValueFactory<HomeTable, Integer>("price"));
        c8.setText("Price");

        TableColumn<HomeTable, String> c9 = new TableColumn<HomeTable, String>();
        c9.setCellValueFactory(new PropertyValueFactory<HomeTable, String>("nationality"));
        c9.setText("nationality");

        TableColumn<HomeTable, String> c10 = new TableColumn<HomeTable, String>();
        c10.setCellValueFactory(new PropertyValueFactory<HomeTable, String>("language"));
        c10.setText("language");

        TableColumn<HomeTable, String> c11 = new TableColumn<HomeTable, String>();
        c11.setCellValueFactory(new PropertyValueFactory<HomeTable, String>("description"));
        c11.setText("description");

        stockTable.getColumns().addAll(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11);
        stockTable.setItems(data);
    }

}
