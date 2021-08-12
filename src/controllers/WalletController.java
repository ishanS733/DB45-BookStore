package controllers;

import com.gluonhq.charm.glisten.control.DropdownButton;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import mainApplication.CartTable;
import mainApplication.Main;
import mainApplication.WalletTable;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WalletController {



    @FXML // fx:id="user_icon"
    private FontAwesomeIconView user_icon; // Value injected by FXMLLoader

    @FXML
    private Label userWelcomeLabel; //

    @FXML
    private Label cardNoLabel;

    @FXML
    private Label balanceLabel;

    @FXML // fx:id="home"
    private JFXButton home; // Value injected by FXMLLoader

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    public static Stage mainStage;

    @FXML
    private TableView<WalletTable> table; // Value injected by FXMLLoader


    public void homePressed(MouseEvent mouseEvent) throws Exception {
        userLoginController.userSetup();
    }

    public void tablePressed(MouseEvent mouseEvent) {
        System.out.println("Table pressed");
    }


    public void user_iconPressed(MouseEvent mouseEvent) {
        System.out.println("User icon pressed");
    }

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws SQLException, InterruptedException {
        assert home != null : "fx:id=\"home\" was not injected: check your FXML file 'Wallet.fxml'.";
        assert user_icon != null : "fx:id=\"user_icon\" was not injected: check your FXML file 'Wallet.fxml'.";
        assert table != null : "fx:id=\"table\" was not injected: check your FXML file 'Wallet.fxml'.";
        userWelcomeLabel.setText(userLoginController.currUser.getUsername() + "'s wallet");
        String query = "select * from useraccounts where u_id = " + userLoginController.currUser.getU_id() + ";";
        ResultSet rs = userLoginController.executer.executeQuery(query);
        int balance = 0;
        String card_num = "";
        while (rs.next()){
            balance += rs.getInt("balance");
            card_num = rs.getString("card_number");
        }
        balanceLabel.setText(String.valueOf(balance));
        cardNoLabel.setText(card_num);

    }

    public void transferClicked(MouseEvent mouseEvent) throws IOException {
        Main.changeScene("transferMoney");
    }

    public void purchasesClicked(MouseEvent mouseEvent) throws IOException {
        Main.changeScene("purchases");
    }

    public void transactionsClicked(MouseEvent mouseEvent) throws IOException {
        Main.changeScene("transactions");
    }
}
