package controllers;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import mainApplication.Main;
import mainApplication.WalletTable;

import java.io.IOException;
import java.sql.SQLException;

public class PurchasesController {

    @FXML
    private JFXButton wallet;

    @FXML
    private TableView<WalletTable> table;

    @FXML
    private Label PrevTransLabel;

    @FXML
    private Label userWelcomeLabel;

    @FXML
    private FontAwesomeIconView user_icon;

    @FXML
    void tablePressed(MouseEvent event) {

    }

    @FXML
    void user_iconPressed(MouseEvent event) {

    }
    @FXML
    void initialize() throws SQLException, InterruptedException {
        System.out.println("lol");
        userWelcomeLabel.setText(userLoginController.currUser.getUsername() + "'s purchases");
        if(table == null) System.out.println("Null table");
        table.getColumns().clear();
        TableColumn<WalletTable, Integer> c1 = new TableColumn<WalletTable, Integer>();
        c1.setCellValueFactory(new PropertyValueFactory<WalletTable, Integer>("total_bill"));
        c1.setText("Total Bill");

        TableColumn<WalletTable, String> c2 = new TableColumn<WalletTable, String>();
        c2.setCellValueFactory(new PropertyValueFactory<WalletTable, String>("b_name"));
        c2.setText("Book Name");

        TableColumn<WalletTable, String> c3 = new TableColumn<WalletTable, String>();
        c3.setCellValueFactory(new PropertyValueFactory<WalletTable, String>("ordered_at"));
        c3.setText("Transaction Time & Date");
        c1.prefWidthProperty().bind(table.widthProperty().multiply(0.25));
        c2.prefWidthProperty().bind(table.widthProperty().multiply(0.375));
        c3.prefWidthProperty().bind(table.widthProperty().multiply(0.375));
        c1.setStyle( "-fx-alignment: CENTER;");
        c2.setStyle( "-fx-alignment: CENTER;");
        c3.setStyle( "-fx-alignment: CENTER;");

        table.getColumns().addAll(c2, c1, c3);

        ObservableList<WalletTable> ob = WalletTable.getObservableListForWalletTable();
        table.setItems(ob);
    }


    @FXML
    void walletPressed(MouseEvent event) throws IOException {
        Main.changeScene("Wallet");
    }

}
