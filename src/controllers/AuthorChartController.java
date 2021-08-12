package controllers;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import mainApplication.Author;
import mainApplication.Main;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AuthorChartController {

    @FXML
    private JFXButton homeButton;

    @FXML
    private Label userWelcomeLabel;

    @FXML
    private FontAwesomeIconView user_icon;

    @FXML
    private PieChart summaryChart;

    @FXML
    void initialize() throws SQLException {
        userWelcomeLabel.setText(Author.auth_name + "'s Page");
        fiLLPie();
    }

    //Authors Percentage to total Sells
    void fiLLPie() throws SQLException {
        ArrayList<PieChart.Data> data = new ArrayList<>();
        String query = "Select auth_name, count(b_id) as bookSelled from all_order natural join written_by natural join authors group by auth_id;";
        PreparedStatement pstmt = userLoginController.connection.prepareStatement(query);
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()){
            data.add(new PieChart.Data(rs.getString("auth_name"), rs.getInt("bookSelled")));
        }
        summaryChart.setData(FXCollections.observableArrayList(data));
    }

    @FXML
    void homePressed(MouseEvent event) throws IOException {
        Main.changeScene("AuthorInfo");
    }

    @FXML
    void user_iconPressed(MouseEvent event) {

    }

}
