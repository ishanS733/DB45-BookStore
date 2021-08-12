package mainApplication;

import controllers.userLoginController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class profileTable {
    String username, comment;
    int rating;

    public profileTable(String username, String comment, int rating) {
        this.username = username;
        this.comment = comment;
        this.rating = rating;
    }

    public static ObservableList<profileTable> getAllCommentsForBook(int bid) throws SQLException {
        String query = "Select * from useraccounts natural join comments natural join books where b_id=?;";
        PreparedStatement pstmt = userLoginController.connection.prepareStatement(query);
        pstmt.setInt(1,bid);
        return getUserComments(pstmt.executeQuery());
    }

    public static ObservableList<profileTable> getUserComments(ResultSet rs) throws SQLException {
        ObservableList<profileTable> list = FXCollections.observableArrayList();
        while(rs.next()){
            list.add(new profileTable(rs.getString("username"), rs.getString("commentByUser") , rs.getInt("rating") ));
        }
        return list;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
