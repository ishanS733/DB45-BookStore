package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ResizeFeaturesBase;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import mainApplication.Main;
import mainApplication.Publication;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AddNewBookController {

    @FXML
    private JFXTextField booknameField;

    @FXML
    private JFXTextField numcopiesField;

    @FXML
    private JFXTextField authorField;

    @FXML
    private JFXTextField numpagesField;

    @FXML
    private JFXTextField priceField;

    @FXML
    private JFXTextField publish_yrField;

    @FXML
    private JFXTextField languageField;

    @FXML
    private JFXTextField GenreField;

    @FXML
    private JFXTextField publisherField;

    @FXML
    private JFXTextField Description;

    ArrayList<String> currGenres;

    @FXML
    void onMainPageClicked(MouseEvent event) throws IOException {
        Main.changeScene("Main");
    }

    public void insertBookProperly(MouseEvent mouseEvent) throws SQLException {
        if (checkIfBookAlreadyExists()) {
            createAccountController.showAlert("Book Already Exists", "");
            return;
        }
        if (!checkAuthExists()) {
            createAccountController.showAlert("Invalid Author ID", "This Author Doesn't Exist in DB");
            return;
        }
        if (!checkAllFieldExists()) {
            return;
        }

//        Update Book Table
        updateBookTable();
//        Published_by Table
        int bid = updatePubTable();
//        Written_By Table
        updateAuthTable(bid);
//        Book_Genre Table
        updateBookGenre(bid);

    }

    void updateBookGenre(final int bid) throws SQLException {
        if (GenreField.getText().length() != 0) {
            currGenres.add(GenreField.getText());
            GenreField.setText("");
        }
        for (int i = 0; i < currGenres.size(); i++) {
            int g_id = checkIfGenreExists(currGenres.get(i));
            if (g_id != 0) {
                try{
                    linkGenre(bid, g_id);
                }
                catch(SQLException e){
                   continue;
                }
            } else {
                g_id = insertGenre(currGenres.get(i));
                linkGenre(bid, g_id);
            }
        }
        currGenres.clear();
    }

    int insertGenre(String genre) throws SQLException {
        String query = "insert into genre (genre) value (?);";
        PreparedStatement pstmt = userLoginController.connection.prepareStatement(query);
        pstmt.setString(1, genre);
        pstmt.executeUpdate();
        String q = "select * from genre where genre=?;";
        PreparedStatement pst = userLoginController.connection.prepareStatement(q);
        pst.setString(1, genre);
        ResultSet rs = pst.executeQuery();
        rs.next();
        return rs.getInt("g_id");
    }

    void linkGenre(int bid, int gid) throws SQLException {
        String query = "insert into book_genre (b_id,g_id) value (?,?); ";
        PreparedStatement pstmt = userLoginController.connection.prepareStatement(query);
        pstmt.setInt(1, bid);
        pstmt.setInt(2, gid);
        pstmt.executeUpdate();
    }

    int checkIfGenreExists(String Genre) throws SQLException {
        String query = "Select * from genre where genre=?;";
        PreparedStatement pstmt = userLoginController.connection.prepareStatement(query);
        pstmt.setString(1, Genre);
        ResultSet rs = pstmt.executeQuery();
        if (!rs.next()) {
            return 0;
        } else {
            return rs.getInt("g_id");
        }
    }

    public void updateAuthTable(int bid) throws SQLException {
        String query = "insert into written_by (b_id, auth_id) value (?,?);";
        PreparedStatement pstmt = userLoginController.connection.prepareStatement(query);
        pstmt.setInt(1, bid);
        pstmt.setInt(2, Integer.parseInt(authorField.getText()));
        pstmt.executeUpdate();
        return;
    }

    public void updateBookTable() throws SQLException {
        String query = "insert into books (b_name,price,description,pub_year,num_pages,num_copies,language) value (?,?,?,?,?,?,?);";
        PreparedStatement pstmt = userLoginController.connection.prepareStatement(query);
        pstmt.setString(1, booknameField.getText());
        pstmt.setInt(2, Integer.parseInt(priceField.getText()));
        pstmt.setString(3, Description.getText());
        pstmt.setInt(4, Integer.parseInt(publish_yrField.getText()));
        pstmt.setInt(5, Integer.parseInt(numpagesField.getText()));
        pstmt.setInt(6, Integer.parseInt(numcopiesField.getText()));
        pstmt.setString(7, languageField.getText());
        pstmt.executeUpdate();
    }

    public int updatePubTable() throws SQLException {
        String query = "Select * from books where b_name=?;";
        PreparedStatement pstmt = userLoginController.connection.prepareStatement(query);
        pstmt.setString(1, booknameField.getText());
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        int bid = rs.getInt("b_id");
        String q = "insert into published_by (b_id, pub_id) value (?,?);";
        PreparedStatement pst = userLoginController.connection.prepareStatement(q);
        pst.setInt(1, bid);
        pst.setInt(2, Publication.pub_id);
        pst.executeUpdate();
        return bid;
    }

    public boolean checkAuthExists() throws SQLException {
        String query;
        try {
            query = "select * from authors where auth_id = '" + Integer.parseInt(authorField.getText()) + "';";
        } catch (NumberFormatException e) {
            createAccountController.showAlert("Invalid Number Format", "Author ID Must be int");
            return false;
        }
        ResultSet res = userLoginController.executer.executeQuery(query);

        return res.next();
    }

    public void insertGenreButtonPressed(MouseEvent mouseEvent) {
        if (GenreField.getText().length() == 0) {
            createAccountController.showAlert("Field Empty", "Genre Field Cannot Be Empty");
            return;
        }
        currGenres.add(GenreField.getText());
        GenreField.setText("");
    }

    private boolean checkIfBookAlreadyExists() throws SQLException {
        String query = "Select * from books where b_name=?;";
        PreparedStatement pstmt = userLoginController.connection.prepareStatement(query);
        pstmt.setString(1, booknameField.getText());
        ResultSet rs = pstmt.executeQuery();
        return rs.next();
    }

    private boolean checkAllFieldExists() {
        String name = booknameField.getText();
        String num_copies = numcopiesField.getText();
        String num_pages = numpagesField.getText();
        String price = priceField.getText();
        String author = authorField.getText();
        String pub_yr = publish_yrField.getText();
        String lang = languageField.getText();
        String genre = GenreField.getText();
        String book_desc = Description.getText();
        if (
                name.length() == 0 || num_copies.length() == 0 || num_pages.length() == 0 ||
                        price.length() == 0 || author.length() == 0 || pub_yr.length() == 0 ||
                        lang.length() == 0 || genre.length() == 0 ||
                        book_desc.length() == 0
        ) {
            createAccountController.showAlert("Book Addition Failed", "Enter valid book entries");
            return false;
        }
        try {
            int copies = 0;
            int num_of_pages = 0;
            int price_ = 0;
            int pub_year = 0;
            copies = Integer.parseInt(num_copies);
            num_of_pages = Integer.parseInt(num_pages);
            price_ = Integer.parseInt(price);
            pub_year = Integer.parseInt(pub_yr);
            if (copies <= 0 || num_of_pages <= 0 || price_ <= 0 || pub_year <= 0) {
                createAccountController.showAlert("Book Addition Failed", "Enter valid book entries");
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            createAccountController.showAlert("Book Addition Failed", "Enter valid book entries");
            return false;
        }

        return true;
    }


    public void backButtonPressed(MouseEvent mouseEvent) throws IOException {
        Main.changeScene("publisher_home");
    }

    @FXML
    void initialize() {
//        assert background != null : "fx:id=\"background\" was not injected: check your FXML file 'addNewBook.fxml'.";
//        assert gridpane != null : "fx:id=\"gridpane\" was not injected: check your FXML file 'addNewBook.fxml'.";
//        assert userNameLabel != null : "fx:id=\"userNameLabel\" was not injected: check your FXML file 'addNewBook.fxml'.";
//        assert emailLabel != null : "fx:id=\"emailLabel\" was not injected: check your FXML file 'addNewBook.fxml'.";
//        assert passwordLabel != null : "fx:id=\"passwordLabel\" was not injected: check your FXML file 'addNewBook.fxml'.";
//        assert confirmPassLabel != null : "fx:id=\"confirmPassLabel\" was not injected: check your FXML file 'addNewBook.fxml'.";
//        assert numberLabel != null : "fx:id=\"numberLabel\" was not injected: check your FXML file 'addNewBook.fxml'.";
        assert booknameField != null : "fx:id=\"booknameField\" was not injected: check your FXML file 'addNewBook.fxml'.";
        assert numcopiesField != null : "fx:id=\"numcopiesField\" was not injected: check your FXML file 'addNewBook.fxml'.";
        assert authorField != null : "fx:id=\"authorField\" was not injected: check your FXML file 'addNewBook.fxml'.";
//        assert numberLabel1 != null : "fx:id=\"numberLabel1\" was not injected: check your FXML file 'addNewBook.fxml'.";
        assert publish_yrField != null : "fx:id=\"publish_yrField\" was not injected: check your FXML file 'addNewBook.fxml'.";
//        assert numberLabel11 != null : "fx:id=\"numberLabel11\" was not injected: check your FXML file 'addNewBook.fxml'.";
        assert languageField != null : "fx:id=\"languageField\" was not injected: check your FXML file 'addNewBook.fxml'.";
//        assert numberLabel111 != null : "fx:id=\"numberLabel111\" was not injected: check your FXML file 'addNewBook.fxml'.";
        assert GenreField != null : "fx:id=\"GenreField\" was not injected: check your FXML file 'addNewBook.fxml'.";
//        assert numberLabel1112 != null : "fx:id=\"numberLabel1112\" was not injected: check your FXML file 'addNewBook.fxml'.";
        assert Description != null : "fx:id=\"Description\" was not injected: check your FXML file 'addNewBook.fxml'.";
        assert priceField != null : "fx:id=\"priceField\" was not injected: check your FXML file 'addNewBook.fxml'.";
        assert numpagesField != null : "fx:id=\"numpagesField\" was not injected: check your FXML file 'addNewBook.fxml'.";
//        assert addBookButton != null : "fx:id=\"addBookButton\" was not injected: check your FXML file 'addNewBook.fxml'.";
//        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'addNewBook.fxml'.";
//        assert mainPageButton != null : "fx:id=\"mainPageButton\" was not injected: check your FXML file 'addNewBook.fxml'.";
//        assert insertGenreButton != null : "fx:id=\"insertGenreButton\" was not injected: check your FXML file 'addNewBook.fxml'.";
        currGenres = new ArrayList<String>();
    }

}
