package mainApplication;

import com.sun.xml.internal.txw2.output.DumpSerializer;
import controllers.userLoginController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class yourReviews {
    int c_id, b_id, Rating;
    String comment, b_name;

    yourReviews(int c_id, int b_id,String b_name, int Rating, String comment){
        this.c_id=c_id;
        this.b_id=b_id;
        this.b_name=b_name;
        this.Rating=Rating;
        this.comment=comment;
    }

    public static ObservableList<yourReviews> getAllReviews() throws SQLException {
        ObservableList<yourReviews> list = FXCollections.observableArrayList();
        String query = "Select * from books natural join comments where u_id=?;";
        PreparedStatement pstmt  = userLoginController.connection.prepareStatement(query);
        pstmt.setInt(1,userLoginController.currUser.getU_id());
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()){
            list.add(new yourReviews(rs.getInt("c_id"), rs.getInt("b_id"), rs.getString("b_name"), rs.getInt("rating"), rs.getString("commentByUser")   ));

        }
        return list;
    }

    public int getC_id() {
        return c_id;
    }

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }

    public int getB_id() {
        return b_id;
    }

    public void setB_id(int b_id) {
        this.b_id = b_id;
    }

    public int getRating() {
        return Rating;
    }

    public void setRating(int rating) {
        Rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getB_name() {
        return b_name;
    }

    public void setB_name(String b_name) {
        this.b_name = b_name;
    }
}
