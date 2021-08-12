/**
 * Sample Skeleton for 'adminPanel.fxml' Controller Class
 */

package controllers;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import mainApplication.weekWiseData;

public class adminPanelController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="weekWiseButton"
    private JFXButton weekWiseButton; // Value injected by FXMLLoader

    @FXML // fx:id="logout"
    private JFXButton logout; // Value injected by FXMLLoader

    @FXML // fx:id="weekStatsTable"
    private TableView<weekWiseData> weekStatsTable; // Value injected by FXMLLoader

    @FXML // fx:id="userWelcomeLabel"
    private Label userWelcomeLabel; // Value injected by FXMLLoader

    @FXML // fx:id="user_icon"
    private FontAwesomeIconView user_icon; // Value injected by FXMLLoader

    @FXML
    void logoutPressed(MouseEvent event) throws IOException {
        userLoginController.changeScene("Main");
    }


    @FXML
    void weekWiseButtonPressed(MouseEvent event) throws IOException{
        userLoginController.changeScene("adminPanel");
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws SQLException {
        assert weekWiseButton != null : "fx:id=\"weekWiseButton\" was not injected: check your FXML file 'adminPanel.fxml'.";
        assert logout != null : "fx:id=\"logout\" was not injected: check your FXML file 'adminPanel.fxml'.";
        assert weekStatsTable != null : "fx:id=\"weekStatsTable\" was not injected: check your FXML file 'adminPanel.fxml'.";
        assert userWelcomeLabel != null : "fx:id=\"userWelcomeLabel\" was not injected: check your FXML file 'adminPanel.fxml'.";
        assert user_icon != null : "fx:id=\"user_icon\" was not injected: check your FXML file 'adminPanel.fxml'.";
        userWelcomeLabel.setText("Welcome, Admin");
        initializeTable();

    }

    void initializeTable() throws SQLException {
        TableColumn<weekWiseData, Timestamp> c1 = new TableColumn<>();
        c1.setCellValueFactory(new PropertyValueFactory<weekWiseData,Timestamp>("time"));
        c1.setText("Week");

        TableColumn<weekWiseData, Integer> c2 = new TableColumn<>();
        c2.setCellValueFactory(new PropertyValueFactory<weekWiseData,Integer>("cash"));
        c2.setText("Total Cash In");

        TableColumn<weekWiseData, Integer> c3 = new TableColumn<>();
        c3.setCellValueFactory(new PropertyValueFactory<weekWiseData,Integer>("quantity"));
        c3.setText("Total Books Sold");

        weekStatsTable.getColumns().setAll(c1,c2,c3);
        weekStatsTable.setItems(weekWiseData.getWeekWiseData());
        weekStatsTable.getSortOrder().add(c1);
    }

    public void user_iconPressed(MouseEvent mouseEvent) {
    }

    public void stockPercentageButtonPressed(MouseEvent mouseEvent) throws IOException {
        userLoginController.changeScene("stockPercentage");
    }

}
