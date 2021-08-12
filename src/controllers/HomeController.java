/**
 * Sample Skeleton for 'home.fxml' Controller Class
 */

package controllers;

import com.gluonhq.charm.glisten.control.DropdownButton;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import mainApplication.HomeTable;
import mainApplication.Main;

public class HomeController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="home"
    private JFXButton home; // Value injected by FXMLLoader

    @FXML // fx:id="my_account"
    private JFXButton my_account; // Value injected by FXMLLoader

    @FXML // fx:id="profile"
    private JFXButton profile; // Value injected by FXMLLoader

    @FXML // fx:id="wallet"
    private JFXButton wallet; // Value injected by FXMLLoader

    @FXML // fx:id="wishlist"
    private JFXButton wishlist; // Value injected by FXMLLoader

    @FXML // fx:id="logout"
    private JFXButton logout; // Value injected by FXMLLoader

    @FXML // fx:id="search_icon"
    private FontAwesomeIconView search_icon; // Value injected by FXMLLoader

    @FXML // fx:id="search_bar"
    private JFXTextField search_bar; // Value injected by FXMLLoader

    @FXML // fx:id="sort"
    private DropdownButton sort; // Value injected by FXMLLoader

    @FXML // fx:id="advanced_search"
    private JFXButton advanced_search; // Value injected by FXMLLoader

    @FXML // fx:id="go_to_cart"
    private JFXButton go_to_cart; // Value injected by FXMLLoader

    @FXML // fx:id="user_icon"
    private FontAwesomeIconView user_icon; // Value injected by FXMLLoader

    @FXML // fx:id="table"
    private TableView<HomeTable> table; // Value injected by FXMLLoader

    @FXML
    private Label userWelcomeLabel;

    @FXML
    private JFXButton buyButton;

    @FXML
    private JFXButton addToCart;

    @FXML
    private JFXButton helpButton;


    public JFXButton getGo_to_cart() {
        return go_to_cart;
    }

    @FXML // fx:id="addToWishlist"
    private JFXButton addToWishlist; // Value injected by FXMLLoader

    @FXML
    void addToWishlistPressed(MouseEvent event) throws SQLException {
        HomeTable currItem = table.getSelectionModel().getSelectedItem();
        try{
            if(currItem!=null){
                String query = "insert into wishlist (u_id, b_id) value (?,?)";
                PreparedStatement pstmt = userLoginController.connection.prepareStatement(query);
                pstmt.setInt(1,userLoginController.currUser.getU_id());
                pstmt.setInt(2,currItem.getB_id());
                pstmt.executeUpdate();
            }
        }
        catch(SQLIntegrityConstraintViolationException e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void helpButtonPressed(MouseEvent event) throws IOException {
        userLoginController.changeScene("help");
    }

    @FXML
    void addToCartPressed(MouseEvent event) throws SQLException {
        HomeTable currItem = table.getSelectionModel().getSelectedItem();
        if(currItem!=null){
            boolean numOfCopies=checkIfAlreadyInCart(currItem);
            if(numOfCopies){
                createAccountController.showAlert("Failed To Add to Cart","Already Present In Cart");
            }
            else{
                String Query = "INSERT INTO CART (u_id, b_id) VALUE (?,?);";
                PreparedStatement pstmt = userLoginController.connection.prepareStatement(Query);
                pstmt.setInt(1,userLoginController.currUser.getU_id());
                pstmt.setInt(2,currItem.getB_id());
                pstmt.executeUpdate();
                createAccountController.showAlert("Successfully Added to Cart","Added In Cart");
            }
        }
    }

    boolean checkIfAlreadyInCart(HomeTable currItem) throws SQLException {
        String Query = "Select * from cart where u_id=? and b_id=?;";
        PreparedStatement pstmt = userLoginController.connection.prepareStatement(Query);
        pstmt.setInt(1,userLoginController.currUser.getU_id());
        pstmt.setInt(2,currItem.getB_id());
        ResultSet rs = pstmt.executeQuery();
        return rs.next();
    }

    @FXML
    void buyButtonPressed(MouseEvent event) throws SQLException, InterruptedException {
        HomeTable currItem = table.getSelectionModel().getSelectedItem();
        String query = "select balance from useraccounts where u_id=?;";
        PreparedStatement pstmt = userLoginController.connection.prepareStatement(query);
        pstmt.setInt(1,userLoginController.currUser.getU_id());
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        int balance = rs.getInt("balance");
        if(currItem!=null && currItem.getNum_copies()>0){
            if(balance>=currItem.getPrice()){
                int newBal = balance-currItem.getPrice();
                String query2 = "update useraccounts set balance=? where u_id=?";
                pstmt = userLoginController.connection.prepareStatement(query2);
                pstmt.setInt(1, newBal);
                pstmt.setInt(2, userLoginController.currUser.getU_id());
                pstmt.executeUpdate();
                afterSuccesfullyPurchased(currItem);
                createAccountController.showAlert("Succesfully Purchased","Balance Deducted");
                initializeTable();
            }
            else{
                createAccountController.showAlert("Not Enough Balance","Transaction Failed");
            }
        }
        else{
            createAccountController.showAlert("Please Select Book First","No Book Selected");
        }
    }

    void afterSuccesfullyPurchased(HomeTable currItem) throws SQLException {
        String query = "insert into all_order (u_id, b_id, total_bill, ordered_at) value (?,?,?,?);";
        PreparedStatement pstmt = userLoginController.connection.prepareStatement(query);
        pstmt.setInt(1,userLoginController.currUser.getU_id());
        pstmt.setInt(2,currItem.getB_id());
        pstmt.setInt(3,currItem.getPrice());
        String q0 = "Select now() as curr_time;";
        ResultSet rs = userLoginController.executer.executeQuery(q0);
        rs.next();
        pstmt.setTimestamp(4,rs.getTimestamp("curr_time"));
        pstmt.executeUpdate();
        //Changing Stock
        query = "update books set num_copies=? where b_id=?;";
        pstmt = userLoginController.connection.prepareStatement(query);
        pstmt.setInt(1,currItem.getNum_copies()-1);
        pstmt.setInt(2, currItem.getB_id());
        pstmt.executeUpdate();
    }

    @FXML
    void advanced_searchPressed(MouseEvent event) throws IOException {
        userLoginController.changeScene("advancedSearch");
    }

    @FXML
    void go_to_cartPressed(MouseEvent event) throws IOException {
        System.out.println("go_to_cartPressed");
        Main.changeScene("cart");
    }

    @FXML
    void homePressed(MouseEvent event) {
        System.out.println("homePressed");
    }

    @FXML
    void logoutPressed(MouseEvent event) throws IOException {
        System.out.println("logoutPressed");
        userLoginController.changeScene("Main");
        File fs = new File("src/mainApplication/currUser");
        if (fs.exists()) {
            fs.delete();
        }

    }

    @FXML
    void my_accountPressed(MouseEvent event) throws IOException {
        Main.changeScene("myAccount");

    }

    @FXML
    void profilePressed(MouseEvent event) throws IOException {
        userLoginController.changeScene("profile");
    }

    @FXML
    void search_barPressed(MouseEvent event) {
        System.out.println("search_barPressed");
    }

    @FXML
    void search_iconPressed(MouseEvent event) throws SQLException {
        String userSearch = search_bar.getText();
        String Query = "Select * from books Natural join written_by natural join authors natural join book_genre natural join genre where b_name like ? group by b_id order by b_id asc;";
        PreparedStatement pstmt = userLoginController.connection.prepareStatement(Query);
        pstmt.setString(1, "%" + userSearch + "%");
        ResultSet rs = pstmt.executeQuery();
        table.setItems(HomeTable.getConditionedObservableListForHomeTable(rs));
    }

    @FXML
    void sortPressed(MouseEvent event) {
        System.out.println("sortPressed");
    }


    @FXML
    void tablePressed(MouseEvent event) {
        System.out.println("tablePressed");
    }

    @FXML
    void user_iconPressed(MouseEvent event) {
        System.out.println("user_iconPressed");
    }

    @FXML
    void walletPressed(MouseEvent event) throws IOException {
        System.out.println("walletPressed");
        Main.changeScene("Wallet");
    }

    @FXML
    void wishlistPressed(MouseEvent event) throws IOException {
        System.out.println("wishlistPressed");
        Main.changeScene("wishlist");
    }

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws SQLException, InterruptedException {
        assert home != null : "fx:id=\"home\" was not injected: check your FXML file 'home.fxml'.";
        assert my_account != null : "fx:id=\"my_account\" was not injected: check your FXML file 'home.fxml'.";
        assert profile != null : "fx:id=\"profile\" was not injected: check your FXML file 'home.fxml'.";
        assert wallet != null : "fx:id=\"wallet\" was not injected: check your FXML file 'home.fxml'.";
        assert wishlist != null : "fx:id=\"wishlist\" was not injected: check your FXML file 'home.fxml'.";
        assert logout != null : "fx:id=\"logout\" was not injected: check your FXML file 'home.fxml'.";
        assert search_icon != null : "fx:id=\"search_icon\" was not injected: check your FXML file 'home.fxml'.";
        assert search_bar != null : "fx:id=\"search_bar\" was not injected: check your FXML file 'home.fxml'.";
        assert sort != null : "fx:id=\"sort\" was not injected: check your FXML file 'home.fxml'.";
        assert advanced_search != null : "fx:id=\"advanced_search\" was not injected: check your FXML file 'home.fxml'.";
        assert go_to_cart != null : "fx:id=\"go_to_cart\" was not injected: check your FXML file 'home.fxml'.";
        assert user_icon != null : "fx:id=\"user_icon\" was not injected: check your FXML file 'home.fxml'.";
        assert table != null : "fx:id=\"table\" was not injected: check your FXML file 'home.fxml'.";
        userWelcomeLabel.setText("Hi, "+userLoginController.currUser.getUsername());
        search_bar.setOnKeyReleased(e->{
            try {
                this.search_iconPressed(null);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        this.initializeTable();
    }

    void initializeTable() throws SQLException, InterruptedException {
        table.getColumns().clear();
        TableColumn<HomeTable, Integer> c1 = new TableColumn<HomeTable, Integer>();
        c1.setCellValueFactory(new PropertyValueFactory<HomeTable, Integer>("b_id"));
        c1.setText("#");

        TableColumn<HomeTable, String> c2 = new TableColumn<HomeTable, String>();
        c2.setCellValueFactory(new PropertyValueFactory<HomeTable, String>("b_name"));
        c2.setText("Name");

        TableColumn<HomeTable, String> c3 = new TableColumn<HomeTable, String>();
        c3.setCellValueFactory(new PropertyValueFactory<HomeTable, String>("auth_name"));
        c3.setText("Author");

        TableColumn<HomeTable, Integer> c4 = new TableColumn<HomeTable, Integer>();
        c4.setCellValueFactory(new PropertyValueFactory<HomeTable, Integer>("pub_year"));
        c4.setText("Year");

        TableColumn<HomeTable, String> c5 = new TableColumn<HomeTable, String>();
        c5.setCellValueFactory(new PropertyValueFactory<HomeTable, String>("genre"));
        c5.setText("Genre");

        TableColumn<HomeTable, Integer> c6 = new TableColumn<HomeTable, Integer>();
        c6.setCellValueFactory(new PropertyValueFactory<HomeTable, Integer>("num_pages"));
        c6.setText("Number of Pages");

        TableColumn<HomeTable, Integer> c7 = new TableColumn<HomeTable, Integer>();
        c7.setCellValueFactory(new PropertyValueFactory<HomeTable, Integer>("num_copies"));
        c7.setText("Number Of Copies");

        TableColumn<HomeTable, Integer> c8 = new TableColumn<HomeTable, Integer>();
        c8.setCellValueFactory(new PropertyValueFactory<HomeTable, Integer>("price"));
        c8.setText("Price");

        TableColumn<HomeTable, String> c9 = new TableColumn<HomeTable, String>();
        c9.setCellValueFactory(new PropertyValueFactory<HomeTable, String>("nationality"));
        c9.setText("nationality");

        TableColumn<HomeTable, String> c10 = new TableColumn<HomeTable, String>();
        c10.setCellValueFactory(new PropertyValueFactory<HomeTable, String>("language"));
        c10.setText("language");

        TableColumn<HomeTable, String> c11 = new TableColumn<HomeTable, String>();
        c11.setCellValueFactory(new PropertyValueFactory<HomeTable, String>("description"));
        c11.setText("description");

        table.getColumns().addAll(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11);
        ObservableList<HomeTable> ob = HomeTable.getObservableListForHomeTable();
        table.setItems(ob);
    }

    public void reviewButtonPressed(MouseEvent mouseEvent) throws IOException{
        userLoginController.changeScene("reviewScreen");
    }

    public void yourReviewsButtonPressed(MouseEvent mouseEvent) throws IOException {
        userLoginController.changeScene("yourReviews");
    }

}
