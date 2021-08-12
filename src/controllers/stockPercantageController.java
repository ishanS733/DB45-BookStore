/**
 * Sample Skeleton for 'stockPercentage.fxml' Controller Class
 */

package controllers;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import mainApplication.pubStats;

public class stockPercantageController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="weekWiseButton"
    private JFXButton weekWiseButton; // Value injected by FXMLLoader

    @FXML // fx:id="stockPercentageButton"
    private JFXButton stockPercentageButton; // Value injected by FXMLLoader

    @FXML // fx:id="logout"
    private JFXButton logout; // Value injected by FXMLLoader

    @FXML // fx:id="userWelcomeLabel"
    private Label userWelcomeLabel; // Value injected by FXMLLoader

    @FXML // fx:id="user_icon"
    private FontAwesomeIconView user_icon; // Value injected by FXMLLoader

    @FXML // fx:id="summaryChart"
    private PieChart summaryChart; // Value injected by FXMLLoader

    @FXML
    void logoutPressed(MouseEvent event) throws IOException {
        userLoginController.changeScene("Main");
    }

    @FXML
    void stockPercentageButtonPressed(MouseEvent event) throws IOException {
        userLoginController.changeScene("stockPercentage");
    }

    @FXML
    void user_iconPressed(MouseEvent event) {

    }

    @FXML
    void weekWiseButtonPressed(MouseEvent event) throws IOException {
        userLoginController.changeScene("adminPanel");
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws SQLException {
        assert weekWiseButton != null : "fx:id=\"weekWiseButton\" was not injected: check your FXML file 'stockPercentage.fxml'.";
        assert stockPercentageButton != null : "fx:id=\"stockPercentageButton\" was not injected: check your FXML file 'stockPercentage.fxml'.";
        assert logout != null : "fx:id=\"logout\" was not injected: check your FXML file 'stockPercentage.fxml'.";
        assert userWelcomeLabel != null : "fx:id=\"userWelcomeLabel\" was not injected: check your FXML file 'stockPercentage.fxml'.";
        assert user_icon != null : "fx:id=\"user_icon\" was not injected: check your FXML file 'stockPercentage.fxml'.";
        assert summaryChart != null : "fx:id=\"summaryChart\" was not injected: check your FXML file 'stockPercentage.fxml'.";
        userWelcomeLabel.setText("Welcome, Admin");
        summaryChart.setTitle("Stock Percentages With Respect to Publications");
        summaryChart.setData(pubStats.getPubStatsBooks());
    }
}
