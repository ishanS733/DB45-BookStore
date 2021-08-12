package mainApplication;

import controllers.userLoginController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class weekWiseData {
    Timestamp time;
    int cash, quantity;
    weekWiseData(Timestamp time, int cash, int quantity){
        this.time=time;
        this.cash=cash;
        this.quantity=quantity;
    }

    public static ObservableList<weekWiseData> getWeekWiseData() throws SQLException{
        ObservableList<weekWiseData> data = FXCollections.observableArrayList();
        String query = "select ordered_at as time, sum(total_bill) as cash, count(b_id) as quantity from " +
                "all_order group by WEEK(time),YEAR(time);";
        ResultSet rs = userLoginController.executer.executeQuery(query);
        while(rs.next()){
            Timestamp st = rs.getTimestamp("time");
            data.add(new weekWiseData(st ,rs.getInt("cash"),rs.getInt("quantity")));
        }
        return data;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
