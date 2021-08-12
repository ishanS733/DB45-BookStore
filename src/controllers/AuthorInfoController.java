package controllers;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import mainApplication.Author;
import mainApplication.Main;

import javax.management.Query;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorInfoController {

    @FXML
    private Label userWelcomeLabel;

    @FXML
    private FontAwesomeIconView user_icon;

    @FXML
    private Label bestsellerLabel;

    @FXML
    private JFXButton authorChart;

    @FXML
    private Label bestGenreLabel;


    @FXML
    private Label avgRatingLabel;


    @FXML
    private Label numBooksRegLabel;


    @FXML
    private Label numBooksSoldLabel;


    @FXML
    private JFXButton backButton;

    @FXML
    void initialize() throws SQLException {
        System.out.println("Initting author " + Author.auth_id);
        bestsellerLabel.setText(getBestseller());
        bestGenreLabel.setText(getBestSellingGenre());
        avgRatingLabel.setText(Double.toString(getAvgRating()));
        numBooksRegLabel.setText(Integer.toString(getRegdBooks()));
        numBooksSoldLabel.setText(Integer.toString(getBooksSold()));
        userWelcomeLabel.setText(Author.auth_name + "'s Home");
    }

    @FXML
    void authorChartClicked(MouseEvent event) throws IOException {
        Main.changeScene("authorChart");
    }

    @FXML
    void backButtonPressed(MouseEvent event) throws IOException {
        Main.changeScene("authorLogin");
    }

    @FXML
    void user_iconPressed(MouseEvent event) {
        System.out.println("Icon pressed");
    }

    String getBestseller() throws SQLException { // optimize with "WITH" clause
        String query = "select b_id, totalbooks " +
                "  from (" +
                "       select b_id                               " +
                "            , count(b_id) as totalbooks" +
                "         from all_order natural join written_by where auth_id = " + Author.auth_id
                +
                "       group " +
                "           by b_id" +
                "       ) as t2 having totalbooks = (" +
                "       select max(mycount) from (" +
                "              select b_id                               " +
                "            , count(b_id) as mycount" +
                "         from all_order natural join written_by where auth_id = " + Author.auth_id +
                "       group " +
                "           by b_id" +
                "       ) as t3" +
                "       );";
        ResultSet rs = userLoginController.executer.executeQuery(query);
        if(!rs.next()) return "";
        int b_id = rs.getInt("b_id");
        query = "select * from books where b_id = " + b_id + ";";
        rs = userLoginController.executer.executeQuery(query);
        rs.next();
        return rs.getString("b_name");
    }

    String getBestSellingGenre() throws SQLException {
        String query = "select g_id, totalbooks   from (select g_id, count(g_id) as totalbooks from all_order natural join written_by natural join book_genre where auth_id = "
                + Author.auth_id +
                " group by g_id) as t2 having totalbooks = ( select max(mycount) from ( select g_id, count(g_id) as mycount from all_order natural join written_by natural join book_genre where auth_id = "
                + Author.auth_id + " group by g_id) as t3);";
        ResultSet rs = userLoginController.executer.executeQuery(query);
        if(!rs.next()) return "";
        int g_id = rs.getInt("g_id");
        query = "select * from genre where g_id = " + g_id + ";";
        rs = userLoginController.executer.executeQuery(query);
        rs.next();
        return rs.getString("genre");
    }

    Double getAvgRating() throws SQLException {
        String query = "select avg(rating) 'avg' from comments natural join written_by where auth_id = "
                + Author.auth_id + ";";
        ResultSet rs = userLoginController.executer.executeQuery(query);
        if(!rs.next()) return 0.0;
        return rs.getDouble("avg");
    }

    int getRegdBooks() throws SQLException {
        String query = "select * from books natural join written_by where auth_id = " + Author.auth_id
                + ";";
        int size = 0;
        ResultSet rs = userLoginController.executer.executeQuery(query);
        if(!rs.next()) return 0;
        rs.last();
        size = rs.getRow();
        return size;
    }

    int getBooksSold() throws SQLException {
        String query = "select * from all_order natural join written_by where auth_id = " + Author.auth_id
                + ";";
        int size = 0;
        ResultSet rs = userLoginController.executer.executeQuery(query);
        if(!rs.next()) return 0;
        rs.last();
        size = rs.getRow();
        return size;
    }

    public void mySellingSharePressed(MouseEvent mouseEvent) throws IOException {
        userLoginController.changeScene("mySellingPercentage");
    }
}
