/**
 * Sample Skeleton for 'mySellingPercentage.fxml' Controller Class
 */

package controllers;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import mainApplication.Author;
import mainApplication.Main;

public class MySellingPercentageController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="homeButton"
    private JFXButton homeButton; // Value injected by FXMLLoader

    @FXML // fx:id="userWelcomeLabel"
    private Label userWelcomeLabel; // Value injected by FXMLLoader

    @FXML // fx:id="user_icon"
    private FontAwesomeIconView user_icon; // Value injected by FXMLLoader

    @FXML // fx:id="summaryChart"
    private PieChart summaryChart; // Value injected by FXMLLoader

    @FXML
    void homePressed(MouseEvent event) throws IOException {
        Main.changeScene("AuthorInfo");
    }

    @FXML
    void user_iconPressed(MouseEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws SQLException {
        assert homeButton != null : "fx:id=\"homeButton\" was not injected: check your FXML file 'mySellingPercentage.fxml'.";
        assert userWelcomeLabel != null : "fx:id=\"userWelcomeLabel\" was not injected: check your FXML file 'mySellingPercentage.fxml'.";
        assert user_icon != null : "fx:id=\"user_icon\" was not injected: check your FXML file 'mySellingPercentage.fxml'.";
        assert summaryChart != null : "fx:id=\"summaryChart\" was not injected: check your FXML file 'mySellingPercentage.fxml'.";
        fillMySellingPercentage();
        userWelcomeLabel.setText(Author.auth_name + "'s Share");
    }

    void fillMySellingPercentage() throws SQLException {
        String query = "select b_name, count(b_name) as bookSelled from authors natural join written_by natural join books natural join all_order where auth_id=? group by b_id;";
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        PreparedStatement pstmt = userLoginController.connection.prepareStatement(query);
        pstmt.setInt(1,Author.auth_id);
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()){
            data.add(new PieChart.Data(rs.getString("b_name"),rs.getInt("bookSelled")));
        }
        summaryChart.setData(data);
    }
}
