package mainApplication;

import controllers.userLoginController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CartTable {
    int b_id, price, pub_year, num_pages;
    String b_name, description, language, auth_name, nationality, genre;

    public static ResultSet runQueryForCartTable() throws SQLException {
        String Query = "select * from Books Natural join written_by natural join authors natural join book_genre natural join genre natural join cart where u_id = '" +
                userLoginController.currUser.u_id +
                "' group by b_id order by b_id asc;";
        ResultSet rs = userLoginController.executer.executeQuery(Query);
        return rs;
    }

    public static CartTable getNewCartTable(ResultSet rs) throws SQLException {
        CartTable h = new CartTable();
        h.b_id = rs.getInt("b_id");
        h.price = rs.getInt("price");
        h.pub_year = rs.getInt("pub_year");
        h.num_pages = rs.getInt("num_pages");
        h.b_name = rs.getString("b_name");
        h.description = rs.getString("description");
        h.language = rs.getString("language");
        h.auth_name = rs.getString("auth_name");
        h.nationality = rs.getString("nationality");
//        h.genre = rs.getString("genre");
           String q = "select * from book_genre natural join genre where b_id = " + h.b_id + ";";
        Statement exec = userLoginController.connection.createStatement();
        ResultSet rs_ = exec.executeQuery(q);
        String gen = "";
        while (rs_.next()){
            gen += rs_.getString("genre");
            gen += ",";
        }
        h.genre = gen;
        return h;
    }

    public static ObservableList<CartTable> getConditionedObservableListForCartTable(ResultSet rs) throws SQLException {
        ArrayList<CartTable> tb = new ArrayList<CartTable>();
        while (rs.next()) {
            tb.add(CartTable.getNewCartTable(rs));
        }
        return FXCollections.observableArrayList(tb);
    }
    public static ObservableList<CartTable> getObservableListForCartTable() throws SQLException {
        ResultSet rs = CartTable.runQueryForCartTable();
        ArrayList<CartTable> tb = new ArrayList<CartTable>();
        while (rs.next()) {
            tb.add(CartTable.getNewCartTable(rs));
        }
        return FXCollections.observableArrayList(tb);
    }
    @Override
    public String toString() {
        return "CartTable{" +
                "b_id=" + b_id +
                ", price=" + price +
                ", pub_year=" + pub_year +
                ", num_pages=" + num_pages +
                ", b_name='" + b_name + '\'' +
                ", description='" + description + '\'' +
                ", language='" + language + '\'' +
                ", auth_name='" + auth_name + '\'' +
                ", nationality='" + nationality + '\'' +
                ", genre='" + genre + '\'' +
                '}';
    }

    public int getB_id() {
        return b_id;
    }

    public void setB_id(int b_id) {
        this.b_id = b_id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPub_year() {
        return pub_year;
    }

    public void setPub_year(int pub_year) {
        this.pub_year = pub_year;
    }

    public int getNum_pages() {
        return num_pages;
    }

    public void setNum_pages(int num_pages) {
        this.num_pages = num_pages;
    }

    public String getB_name() {
        return b_name;
    }

    public void setB_name(String b_name) {
        this.b_name = b_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getAuth_name() {
        return auth_name;
    }

    public void setAuth_name(String auth_name) {
        this.auth_name = auth_name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
