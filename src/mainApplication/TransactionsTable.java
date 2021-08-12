package mainApplication;

import controllers.userLoginController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TransactionsTable {
    int payer_id;
    String trans_time;
    int recipient_id;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    int amount;

    public int getPayer_id() {
        return payer_id;
    }

    public void setPayer_id(int payer_id) {
        this.payer_id = payer_id;
    }

    public int getRecipient_id() {
        return recipient_id;
    }

    public void setRecipient_id(int recipient_id) {
        this.recipient_id = recipient_id;
    }



    public String getTrans_time() {
        return trans_time;
    }

    public void setTrans_time(String trans_time) {
        this.trans_time = trans_time;
    }

    // select * from transactionss where payer_id = 1 or recipient_id = 1 ;

    public static ResultSet runQueryForTransactionsTable() throws SQLException {
        String Query = "select * from transactions where payer_id = "+ userLoginController.currUser.u_id
                        + " or recipient_id = " + userLoginController.currUser.u_id + " order by trans_time asc;";
        ResultSet rs = userLoginController.executer.executeQuery(Query);
        return rs;
    }

    public static TransactionsTable getNewTransactionsTable(ResultSet rs) throws SQLException {
        TransactionsTable h = new TransactionsTable();
        h.payer_id = rs.getInt("payer_id");
        h.recipient_id = rs.getInt("recipient_id");
        h.trans_time = rs.getString("trans_time");
        h.amount = rs.getInt("amount");
        return h;
    }

    public static ObservableList<TransactionsTable> getConditionedObservableListForTransactionsTable(ResultSet rs) throws SQLException {
        ArrayList<TransactionsTable> tb = new ArrayList<TransactionsTable>();
        while (rs.next()) {
            tb.add(TransactionsTable.getNewTransactionsTable(rs));
        }
        return FXCollections.observableArrayList(tb);
    }

    public static ObservableList<TransactionsTable> getObservableListForTransactionsTable() throws SQLException {
        ResultSet rs = TransactionsTable.runQueryForTransactionsTable();
        ArrayList<TransactionsTable> tb = new ArrayList<TransactionsTable>();
        while (rs.next()) {
            tb.add(TransactionsTable.getNewTransactionsTable(rs));
        }
        return FXCollections.observableArrayList(tb);
    }

}
