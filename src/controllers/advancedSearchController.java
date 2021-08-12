/**
 * Sample Skeleton for 'advancedSearch.fxml' Controller Class
 */

package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
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
import javafx.scene.layout.VBox;
import mainApplication.HomeTable;

public class advancedSearchController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="home"
    private JFXButton home; // Value injected by FXMLLoader

    @FXML // fx:id="results"
    private TableView<HomeTable> results; // Value injected by FXMLLoader

    @FXML // fx:id="userWelcomeLabel"
    private Label userWelcomeLabel; // Value injected by FXMLLoader

    @FXML // fx:id="user_icon"
    private FontAwesomeIconView user_icon; // Value injected by FXMLLoader

    @FXML // fx:id="searchButton"
    private JFXButton searchButton; // Value injected by FXMLLoader

    @FXML // fx:id="verBox"
    private VBox verBox; // Value injected by FXMLLoader

    @FXML // fx:id="searchBarBookName"
    private JFXTextField searchBarBookName; // Value injected by FXMLLoader

    @FXML // fx:id="searchBarAuthName"
    private JFXTextField searchBarAuthName; // Value injected by FXMLLoader

    @FXML // fx:id="searchBarLangauge"
    private JFXTextField searchBarLangauge; // Value injected by FXMLLoader

    @FXML // fx:id="searchBarYear"
    private JFXTextField searchBarYear; // Value injected by FXMLLoader

    @FXML // fx:id="searchBarPrice"
    private JFXTextField searchBarPrice; // Value injected by FXMLLoader

    @FXML
    private JFXTextField searchBarNationality; // Value injected by FXMLLoader

    @FXML // fx:id="MustBeInStockCheckBox"
    private JFXCheckBox MustBeInStockCheckBox; // Value injected by FXMLLoader

    @FXML
    void homePressed(MouseEvent event) throws IOException {
        userLoginController.changeScene("home");
    }

    @FXML
    void searchButtonPressed(MouseEvent event) throws SQLException {
        if(MustBeInStockCheckBox.isSelected()){
            String query = "select * from " +
                    "Books Natural join written_by " +
                    "natural join authors natural join " +
                    "book_genre natural join genre" +
                    " where b_name like ? and auth_name like ? " +
                    "and language like ? and pub_year like ? " +
                    "and price < ? and nationality like ? and " +
                    "num_copies>0 group by b_id;";
            PreparedStatement pstmt = userLoginController.connection.prepareStatement(query);
            pstmt.setString(1, "%"+searchBarBookName.getText()+"%");
            pstmt.setString(2, "%"+searchBarAuthName.getText()+"%");
            pstmt.setString(3, "%"+searchBarLangauge.getText()+"%");
            pstmt.setString(4, "%"+searchBarYear.getText()+"%");
            if(searchBarPrice.getText().length()!=0){
                int price = getPrice();
                if(price==-Integer.MAX_VALUE){
                    return;
                }
                pstmt.setInt(5, price);
            }
            else {
                pstmt.setInt(5, 10000);
            }
            pstmt.setString(6, "%"+searchBarNationality.getText()+"%");
            ResultSet rs = pstmt.executeQuery();
            initializeTable(rs);
        }
        else{
            String query = "select * from Books Natural join written_by" +
                    " natural join authors natural join book_genre natural join genre" +
                    " where b_name like ? and auth_name like ? and " +
                    "language like ? and pub_year like ? and " +
                    "price < ? and nationality like ? group by b_id;";
            PreparedStatement pstmt = userLoginController.connection.prepareStatement(query);
            pstmt.setString(1, "%"+searchBarBookName.getText()+"%");
            pstmt.setString(2, "%"+searchBarAuthName.getText()+"%");
            pstmt.setString(3, "%"+searchBarLangauge.getText()+"%");
            pstmt.setString(4, "%"+searchBarYear.getText()+"%");
            if(searchBarPrice.getText().length()!=0){
                int price = getPrice();
                if(price==-Integer.MAX_VALUE){
                    return;
                }
                pstmt.setInt(5, price);
            }
            else {
                pstmt.setInt(5, 10000);
            }
            pstmt.setString(6, "%"+searchBarNationality.getText()+"%");
            ResultSet rs = pstmt.executeQuery();
            initializeTable(rs);
        }
    }

    int getPrice(){
        try{
            String s = searchBarPrice.getText();
            int price = Integer.parseInt(s);
            return price;
        }
        catch(NumberFormatException e){
            createAccountController.showAlert("Incorrect Price Format","Price Must Be Integer");
        }
        return -Integer.MAX_VALUE;
    }

    @FXML
    void search_barPressed(MouseEvent event) {

    }

    @FXML
    void tablePressed(MouseEvent event) {

    }

    @FXML
    void user_iconPressed(MouseEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert home != null : "fx:id=\"home\" was not injected: check your FXML file 'advancedSearch.fxml'.";
        assert results != null : "fx:id=\"results\" was not injected: check your FXML file 'advancedSearch.fxml'.";
        assert userWelcomeLabel != null : "fx:id=\"userWelcomeLabel\" was not injected: check your FXML file 'advancedSearch.fxml'.";
        assert user_icon != null : "fx:id=\"user_icon\" was not injected: check your FXML file 'advancedSearch.fxml'.";
        assert searchButton != null : "fx:id=\"searchButton\" was not injected: check your FXML file 'advancedSearch.fxml'.";
        assert verBox != null : "fx:id=\"verBox\" was not injected: check your FXML file 'advancedSearch.fxml'.";
        assert searchBarBookName != null : "fx:id=\"searchBarBookName\" was not injected: check your FXML file 'advancedSearch.fxml'.";
        assert searchBarAuthName != null : "fx:id=\"searchBarAuthName\" was not injected: check your FXML file 'advancedSearch.fxml'.";
        assert searchBarLangauge != null : "fx:id=\"searchBarLangauge\" was not injected: check your FXML file 'advancedSearch.fxml'.";
        assert searchBarYear != null : "fx:id=\"searchBarYear\" was not injected: check your FXML file 'advancedSearch.fxml'.";
        assert searchBarPrice != null : "fx:id=\"searchBarPrice\" was not injected: check your FXML file 'advancedSearch.fxml'.";
        assert MustBeInStockCheckBox != null : "fx:id=\"MustBeInStockCheckBox\" was not injected: check your FXML file 'advancedSearch.fxml'.";
        userWelcomeLabel.setText("Hi, "+userLoginController.currUser.getUsername());
    }

    void initializeTable(ResultSet rs) throws SQLException {
        results.getColumns().clear();
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

        results.getColumns().addAll(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11);
        ObservableList<HomeTable> ob = HomeTable.getConditionedObservableListForHomeTable(rs);
        results.setItems(ob);
    }
}
