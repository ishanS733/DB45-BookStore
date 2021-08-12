package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import mainApplication.Main;
import mainApplication.Publication;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PublisherHomeController {

    @FXML
    private Label userWelcomeLabel;

    @FXML
    private Label num_copies_exception_label;

    @FXML
    private Label b_id_exception_label;

    @FXML
    private FontAwesomeIconView user_icon;

    @FXML
    private JFXButton add_new_book_button;

    @FXML
    private JFXButton backButton;

    @FXML
    private JFXButton mainPageButton;

    @FXML
    private JFXTextField b_id_field;

    @FXML
    private JFXTextField no_of_copies_field;

    @FXML
    void initialize(){
        userWelcomeLabel.setText(Publication.pub_name + "'s Home Page");
    }
    @FXML
    void add_new_book_button_Clicked(MouseEvent event) throws IOException {
        Main.changeScene("addNewBook");
    }

    @FXML
    void backPressed(MouseEvent event) throws IOException {
        Main.changeScene("publisherLogin");
    }

    @FXML
    void onMainPageClicked(MouseEvent event) throws IOException {
        Main.changeScene("Main");
    }

    @FXML
    void user_iconPressed(MouseEvent event) {

    }

    int get_curr_pub_id(){
        return Publication.pub_id;
    }

    public void restock_button_Clicked(MouseEvent mouseEvent) throws SQLException {
        b_id_exception_label.setVisible(false);
        num_copies_exception_label.setVisible(false);
        String entered_copies = no_of_copies_field.getText();
        String entered_b_id = b_id_field.getText();
        int cops = 0;
        if(entered_copies.length() == 0){
            num_copies_exception_label.setVisible(true);
            return;
        }
        else {
            try{
                cops = Integer.parseInt(entered_copies);
                if(cops <= 0){
                    createAccountController.showAlert("Failed Update", "Invalid no of copies");
                    return;
                }
            } catch (Exception e) {
                createAccountController.showAlert("Failed Update", "Invalid no of copies");
                return;
            }
        }
        if(entered_b_id.length() == 0){
            b_id_exception_label.setVisible(true);
            return;
        }
        else{
            try {
                int bID =  Integer.parseInt(entered_b_id);
                if(bID <= 0){
                    createAccountController.showAlert("Failed Update", "Invalid Book ID");
                    return;
                }
            } catch (Exception e) {
                createAccountController.showAlert("Failed Update", "Invalid Book ID");
                return;
            }

        }
        int b_id = Integer.valueOf(entered_b_id);
        String query = "select * from published_by where pub_id = " + get_curr_pub_id() + " and b_id = "
                        + b_id +";";
        ResultSet rs = userLoginController.executer.executeQuery(query);
        if (!rs.next()){
            createAccountController.showAlert("Failed Update", "Not Your Book");
            return;
        }
        query = "select * from books where b_id = " + b_id + ";";
        rs = userLoginController.executer.executeQuery(query);
        rs.next();
        int copies_now = rs.getInt("num_copies");
        query = "update books set num_copies = " + (copies_now + cops) + " where b_id = " + b_id + ";";
        userLoginController.executer.executeUpdate(query);
        createAccountController.showAlert("Update Succesful", "Stock Updated");
        no_of_copies_field.setText("");
        b_id_field.setText("");
    }
}
