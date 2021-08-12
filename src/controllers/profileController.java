/**
 * Sample Skeleton for 'profile.fxml' Controller Class
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
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import mainApplication.profileTable;

public class profileController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="home"
    private JFXButton home; // Value injected by FXMLLoader

    @FXML // fx:id="search_bar"
    private JFXTextField search_bar; // Value injected by FXMLLoader

    @FXML
    private FontAwesomeIconView search_icon;

    @FXML // fx:id="table"
    private TableView<profileTable> table; // Value injected by FXMLLoader

    @FXML // fx:id="userWelcomeLabel"
    private Label userWelcomeLabel; // Value injected by FXMLLoader

    @FXML // fx:id="user_icon"
    private FontAwesomeIconView user_icon; // Value injected by FXMLLoader

    @FXML // fx:id="userWelcomeLabel1"
    private Label userWelcomeLabel1; // Value injected by FXMLLoader

    @FXML // fx:id="currBookName"
    private Label currBookName; // Value injected by FXMLLoader

    @FXML
    void homePressed(MouseEvent event) throws IOException {
        userLoginController.changeScene("home");
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
        assert home != null : "fx:id=\"home\" was not injected: check your FXML file 'profile.fxml'.";
        assert search_bar != null : "fx:id=\"search_bar\" was not injected: check your FXML file 'profile.fxml'.";
        assert search_icon != null : "fx:id=\"search_icon\" was not injected: check your FXML file 'profile.fxml'.";
        assert table != null : "fx:id=\"table\" was not injected: check your FXML file 'profile.fxml'.";
        assert userWelcomeLabel != null : "fx:id=\"userWelcomeLabel\" was not injected: check your FXML file 'profile.fxml'.";
        assert user_icon != null : "fx:id=\"user_icon\" was not injected: check your FXML file 'profile.fxml'.";
        assert userWelcomeLabel1 != null : "fx:id=\"userWelcomeLabel1\" was not injected: check your FXML file 'profile.fxml'.";
        assert currBookName != null : "fx:id=\"currBookName\" was not injected: check your FXML file 'profile.fxml'.";
        userWelcomeLabel.setText("Hi, "+userLoginController.currUser.getUsername());
        search_bar.setOnKeyReleased(e->{
            try {
                if(search_bar.getText().length()>0){
                    initializeTable();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    }

    void initializeTable() throws SQLException {
        int bid;
        try{
            bid = Integer.parseInt(search_bar.getText());
        }
        catch(NumberFormatException e){
            createAccountController.showAlert("Incorrect Number Formatting","Please Enter Valid Integer");
            return;
        }
        TableColumn<profileTable, String> c1 = new TableColumn<profileTable, String>();
        c1.setCellValueFactory(new PropertyValueFactory<profileTable, String>("username"));
        c1.setText("Users");

        TableColumn<profileTable, String> c2 = new TableColumn<profileTable, String>();
        c2.setCellValueFactory(new PropertyValueFactory<profileTable, String>("comment"));
        c2.setText("Comments");

        TableColumn<profileTable, Integer> c3 = new TableColumn<profileTable, Integer>();
        c3.setCellValueFactory(new PropertyValueFactory<profileTable, Integer>("rating"));
        c3.setText("Ratings");

        table.getColumns().setAll(c1,c2,c3);
        table.setItems(profileTable.getAllCommentsForBook(bid));
        ResultSet rs = userLoginController.executer.executeQuery("select b_name from books where b_id="+bid+";");
        if(rs.next()){
            currBookName.setText(rs.getString("b_name"));
        }
        else{
            currBookName.setText("");
        }

    }
}
