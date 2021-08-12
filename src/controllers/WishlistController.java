package controllers;

import com.gluonhq.charm.glisten.control.DropdownButton;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import mainApplication.WishlistTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

public class WishlistController {

    @FXML // fx:id="home"
    private JFXButton home; // Value injected by FXMLLoader

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


    @FXML // fx:id="user_icon"
    private FontAwesomeIconView user_icon; // Value injected by FXMLLoader

    @FXML
    private Label userWelcomeLabel;

    @FXML
    private JFXButton buyButton;

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    public static Stage mainStage;
    @FXML
    private Label userNameLabel;

    @FXML
    private TableView<WishlistTable> table; // Value injected by FXMLLoader

    @FXML
    private JFXButton removeFromWishlistButton;

    @FXML
    private JFXButton buyAllButton;

    public void homePressed(MouseEvent mouseEvent) throws Exception {
        userLoginController.userSetup();
    }

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws SQLException, InterruptedException {
        assert home != null : "fx:id=\"home\" was not injected: check your FXML file 'wishlist.fxml'.";
        assert logout != null : "fx:id=\"logout\" was not injected: check your FXML file 'wishlist.fxml'.";
        assert search_icon != null : "fx:id=\"search_icon\" was not injected: check your FXML file 'wishlist.fxml'.";
        assert search_bar != null : "fx:id=\"search_bar\" was not injected: check your FXML file 'wishlist.fxml'.";
        assert sort != null : "fx:id=\"sort\" was not injected: check your FXML file 'wishlist.fxml'.";
        assert advanced_search != null : "fx:id=\"advanced_search\" was not injected: check your FXML file 'wishlist.fxml'.";
        assert user_icon != null : "fx:id=\"user_icon\" was not injected: check your FXML file 'wishlist.fxml'.";
        assert table != null : "fx:id=\"table\" was not injected: check your FXML file 'wishlist.fxml'.";
        userWelcomeLabel.setText(userLoginController.currUser.getUsername() + "'s wishlist");
        this.initializeTable();
    }

    void initializeTable() throws SQLException, InterruptedException {
        if(table == null) System.out.println("Null table");
        table.getColumns().clear();
        TableColumn<WishlistTable, Integer> c1 = new TableColumn<WishlistTable, Integer>();
        c1.setCellValueFactory(new PropertyValueFactory<WishlistTable, Integer>("b_id"));
        c1.setText("#");

        TableColumn<WishlistTable, String> c2 = new TableColumn<WishlistTable, String>();
        c2.setCellValueFactory(new PropertyValueFactory<WishlistTable, String>("b_name"));
        c2.setText("Name");

        TableColumn<WishlistTable, String> c3 = new TableColumn<WishlistTable, String>();
        c3.setCellValueFactory(new PropertyValueFactory<WishlistTable, String>("auth_name"));
        c3.setText("Author");

        TableColumn<WishlistTable, Integer> c4 = new TableColumn<WishlistTable, Integer>();
        c4.setCellValueFactory(new PropertyValueFactory<WishlistTable, Integer>("pub_year"));
        c4.setText("Year");

        TableColumn<WishlistTable, String> c5 = new TableColumn<WishlistTable, String>();
        c5.setCellValueFactory(new PropertyValueFactory<WishlistTable, String>("genre"));
        c5.setText("Genre");

        TableColumn<WishlistTable, Integer> c6 = new TableColumn<WishlistTable, Integer>();
        c6.setCellValueFactory(new PropertyValueFactory<WishlistTable, Integer>("num_pages"));
        c6.setText("Number of Pages");


        TableColumn<WishlistTable, Integer> c8 = new TableColumn<WishlistTable, Integer>();
        c8.setCellValueFactory(new PropertyValueFactory<WishlistTable, Integer>("price"));
        c8.setText("Price");

        TableColumn<WishlistTable, String> c9 = new TableColumn<WishlistTable, String>();
        c9.setCellValueFactory(new PropertyValueFactory<WishlistTable, String>("nationality"));
        c9.setText("nationality");

        TableColumn<WishlistTable, String> c10 = new TableColumn<WishlistTable, String>();
        c10.setCellValueFactory(new PropertyValueFactory<WishlistTable, String>("language"));
        c10.setText("language");

        TableColumn<WishlistTable, String> c11 = new TableColumn<WishlistTable, String>();
        c11.setCellValueFactory(new PropertyValueFactory<WishlistTable, String>("description"));
        c11.setText("description");

        table.getColumns().addAll(c1, c2, c3, c4, c5, c6, c8, c9, c10, c11);
        ObservableList<WishlistTable> ob = WishlistTable.getObservableListForWishlistTable();
        table.setItems(ob);
    }

    public void logoutPressed(MouseEvent mouseEvent) {

    }

    public void search_iconPressed_wishlist(MouseEvent mouseEvent) {

    }

    public void search_barPressed(MouseEvent mouseEvent) {

    }

    public void advanced_searchPressed_wishlist(MouseEvent mouseEvent) {

    }

    public void tablePressed(MouseEvent mouseEvent) {

    }
    private int getBalance() throws SQLException {
        String query = "select balance from useraccounts where u_id=?;";
        PreparedStatement pstmt = userLoginController.connection.prepareStatement(query);
        pstmt.setInt(1,userLoginController.currUser.getU_id());
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        int balance = rs.getInt("balance");
        return balance;
    }

    private int getNumOfCopies_Stock(WishlistTable currItem) throws SQLException {
        String query = "select num_copies from books where b_id=?;";
        PreparedStatement pstmt = userLoginController.connection.prepareStatement(query);
        pstmt.setInt(1,currItem.getB_id());
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        return rs.getInt("num_copies");
    }
    @FXML
    void buyButtonPressed(MouseEvent event) throws SQLException, InterruptedException {
        WishlistTable currItem = table.getSelectionModel().getSelectedItem();
        int balance = getBalance();
        int stock=0;
        if(currItem!=null){
            stock = getNumOfCopies_Stock(currItem);
        }
        if(currItem!=null && stock>0){
            if(balance>=currItem.getPrice()){
                int newBal = balance-currItem.getPrice();
                String query2 = "update useraccounts set balance=? where u_id=?";
                PreparedStatement pstmt = userLoginController.connection.prepareStatement(query2);
                pstmt.setInt(1, newBal);
                pstmt.setInt(2, userLoginController.currUser.getU_id());
                pstmt.executeUpdate();
                afterSuccesfullyPurchased(currItem);
                createAccountController.showAlert("Succesfully Purchased","Balance Deducted");
                removeFromWishlistButtonPressed(null);
            }
            else{
                createAccountController.showAlert("Not Enough Balance","Transaction Failed");
            }
        }
        else{
            createAccountController.showAlert("Please Select Book First","No Book Selected");
        }
    }

    void afterSuccesfullyPurchased(WishlistTable currItem) throws SQLException {
        //Changes in All Order
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
        pstmt.setInt(1,getNumOfCopies_Stock(currItem)-1);
        pstmt.setInt(2, currItem.getB_id());
        pstmt.executeUpdate();
    }

    public void removeFromWishlistButtonPressed(MouseEvent mouseEvent) throws SQLException, InterruptedException {
        WishlistTable currItem = table.getSelectionModel().getSelectedItem();
        if(currItem!=null){
            String query = "delete from wishlist where u_id=? and b_id=?;";
            PreparedStatement pstmt = userLoginController.connection.prepareStatement(query);
            pstmt.setInt(1, userLoginController.currUser.getU_id());
            pstmt.setInt(2, currItem.getB_id());
            pstmt.executeUpdate();
            initializeTable();
        }
        else{
            createAccountController.showAlert("No Item Selected","Please Select Some Item First");
        }
    }

    public void buyAllButtonPressed(MouseEvent mouseEvent) throws SQLException, InterruptedException {
        String query = "select * from wishlist natural join books where u_id=?;";
        PreparedStatement pstmt = userLoginController.connection.prepareStatement(query);
        pstmt.setInt(1,userLoginController.currUser.getU_id());
        ResultSet rs = pstmt.executeQuery();
        int balance = getBalance();
        int currentCost=0;
        ArrayList<Integer> bid_s = new ArrayList<Integer>();
        ArrayList<Integer> prices = new ArrayList<Integer>();
        while(rs.next()){
            currentCost+=rs.getInt("price");
            bid_s.add(rs.getInt("b_id"));
            prices.add(rs.getInt("price"));
            if(rs.getInt("num_copies")<=0){
                createAccountController.showAlert("Out Of Stock","Some Book is Out of Stock");
                return;
            }
            if(balance<currentCost) {
                createAccountController.showAlert("Transaction Failed", "Not Enough Money");
                return;
            }
        }

        //Deduct Balance from UserAccount
        setBalance(balance-currentCost);
        //All Order insert
        for(int i=0;i<bid_s.size();i++){
            int currID = bid_s.get(i);
            int currPrice = prices.get(i);
            String q = "insert into all_order (u_id, b_id, total_bill, " +
                    "ordered_at) value (?,?,?,?);";
            pstmt = userLoginController.connection.prepareStatement(q);
            pstmt.setInt(1,userLoginController.currUser.getU_id());
            pstmt.setInt(2,currID);
            pstmt.setInt(3,currPrice);
            String q0 = "Select now() as curr_time;";
            ResultSet rs_ = userLoginController.executer.executeQuery(q0);
            rs_.next();
            pstmt.setTimestamp(4,rs_.getTimestamp("curr_time"));
            pstmt.executeUpdate();
        }
        //Empty Wishlist Table
        userLoginController.executer.executeUpdate("delete from wishlist where u_id="+userLoginController.currUser.getU_id()+";");
        //Update Books Stock
        updateBookStock( bid_s);
        initializeTable();
    }

    void updateBookStock(ArrayList<Integer> bids) throws SQLException {
        for(int i=0;i<bids.size();i++){
            String query = "Select num_copies from books where b_id=?;";
            PreparedStatement pstmt = userLoginController.connection.prepareStatement(query);
            pstmt.setInt(1,bids.get(i));
            ResultSet rs = pstmt.executeQuery();rs.next();
            int currQuantity = rs.getInt("num_copies");
            String q = "UPDATE books set num_copies=? where b_id=?;";
            pstmt = userLoginController.connection.prepareStatement(q);
            pstmt.setInt(1, currQuantity-1);
            pstmt.setInt(2, bids.get(i));
            pstmt.executeUpdate();
        }
    }

    void setBalance(int balance) throws SQLException {
        String query = "update useraccounts set balance=? where u_id=?;";
        PreparedStatement pstmt = userLoginController.connection.prepareStatement(query);
        pstmt.setInt(1,balance);
        pstmt.setInt(2,userLoginController.currUser.getU_id());
        pstmt.executeUpdate();
    }

    @FXML
    void addToWishlistPressed(MouseEvent event) throws SQLException {
        WishlistTable currItem = table.getSelectionModel().getSelectedItem();
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
    public void user_iconPressed(MouseEvent mouseEvent) {

    }


    public void clearWishlistPressed(MouseEvent mouseEvent) throws SQLException, InterruptedException {
        String query = "delete from wishlist where u_id=?;";
        PreparedStatement pstmt = userLoginController.connection.prepareStatement(query);
        pstmt.setInt(1,userLoginController.currUser.getU_id());
        pstmt.executeUpdate();
        initializeTable();
    }

}
