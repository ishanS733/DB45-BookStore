package mainApplication;

import controllers.userLoginController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class WalletTable {
    int total_bill;
    String ordered_at;
    String b_name;
    int no_of_copies;

    public String getB_name() {
        return b_name;
    }

    public void setB_name(String b_name) {
        this.b_name = b_name;
    }



    public int getNo_of_copies() {
        return no_of_copies;
    }

    public void setNo_of_copies(int no_of_copies) {
        this.no_of_copies = no_of_copies;
    }


    public int getTotal_bill() {
        return total_bill;
    }

    public void setTotal_bill(int total_bill) {
        this.total_bill = total_bill;
    }

    public String getOrdered_at() {
        return ordered_at;
    }

    public void setOrdered_at(String ordered_at) {
        this.ordered_at = ordered_at;
    }

    public static ResultSet runQueryForWalletTable() throws SQLException {
//        String Query = "select * from all_order natural join books where u_id = " +
//                userLoginController.currUser.u_id +
//                ";";
        String Query = "select * from books  join all_order using (b_id) where u_id = " +
                userLoginController.currUser.u_id +
                ";";
        ResultSet rs = userLoginController.executer.executeQuery(Query);
        return rs;
    }


    public static WalletTable getNewWalletTable(ResultSet rs) throws SQLException {
        WalletTable h = new WalletTable();
        h.b_name = rs.getString("b_name");
        h.ordered_at = rs.getString("ordered_at");
        h.total_bill = rs.getInt("total_bill");
        h.no_of_copies = rs.getInt("num_copies");
        return h;
    }

    public static ObservableList<WalletTable> getConditionedObservableListForWalletTable(ResultSet rs) throws SQLException {
        ArrayList<WalletTable> tb = new ArrayList<WalletTable>();
        while (rs.next()) {
            tb.add(WalletTable.getNewWalletTable(rs));
        }
        return FXCollections.observableArrayList(tb);
    }

    public static ObservableList<WalletTable> getObservableListForWalletTable() throws SQLException {
        ResultSet rs = WalletTable.runQueryForWalletTable();
        ArrayList<WalletTable> tb = new ArrayList<WalletTable>();
        while (rs.next()) {
            tb.add(WalletTable.getNewWalletTable(rs));
        }
        return FXCollections.observableArrayList(tb);
    }

    @Override
    public String toString() {
        return "WalletTable{" +
                " book name = " + b_name + '\'' +
                ", total_bill = " + total_bill +
                ", ordered_at = '" + ordered_at + '\'' +
                '}';
    }


}
