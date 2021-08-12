package controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import mainApplication.Main;
import mainApplication.TransactionsTable;
import mainApplication.WalletTable;

import java.io.IOException;
import java.sql.SQLException;

public class TransactionsController {
    @FXML
    private Label userWelcomeLabel;
    @FXML
    private TableView<TransactionsTable> table;
    @FXML
    void initialize() throws SQLException {
        userWelcomeLabel.setText(userLoginController.currUser.getUsername() + "'s transactions");
        if(table == null) System.out.println("Null table");
        table.getColumns().clear();
        TableColumn<TransactionsTable, Integer> c1 = new TableColumn<TransactionsTable, Integer>();
        c1.setCellValueFactory(new PropertyValueFactory<TransactionsTable, Integer>("payer_id"));
        c1.setText("Payer's ID");

        TableColumn<TransactionsTable, Integer> c2 = new TableColumn<TransactionsTable, Integer>();
        c2.setCellValueFactory(new PropertyValueFactory<TransactionsTable, Integer>("recipient_id"));
        c2.setText("Recipient's ID");

        TableColumn<TransactionsTable, Integer> c3 = new TableColumn<TransactionsTable, Integer>();
        c3.setCellValueFactory(new PropertyValueFactory<TransactionsTable, Integer>("Amount"));
        c3.setText("Amount");

        TableColumn<TransactionsTable, String> c4 = new TableColumn<TransactionsTable, String>();
        c4.setCellValueFactory(new PropertyValueFactory<TransactionsTable, String>("trans_time"));
        c4.setText("Transaction Time & Date");

        table.getColumns().addAll(c1, c2, c3, c4);


        ObservableList<TransactionsTable> ob = TransactionsTable.getObservableListForTransactionsTable();
        table.setItems(ob);
    }

    public void walletPressed(MouseEvent mouseEvent) throws IOException {
        Main.changeScene("Wallet");
    }

    public void tablePressed(MouseEvent mouseEvent) {
    }

    public void user_iconPressed(MouseEvent mouseEvent) {
    }
}
