package mainApplication;

import controllers.userLoginController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

import java.sql.ResultSet;
import java.sql.SQLException;

public class pubStats {

    public static ObservableList<PieChart.Data> getPubStatsBooks() throws SQLException {
        ObservableList<PieChart.Data> pData = FXCollections.observableArrayList();
        String query = "select pub_name, sum(num_copies) as quantity from books natural join published_by natural join publications" +
                " group by pub_id";
        ResultSet rs = userLoginController.executer.executeQuery(query);
        while(rs.next()){
            pData.add(new PieChart.Data(rs.getString("pub_name"), rs.getInt("quantity")));
        }
        return pData;
    }

}
