package controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransferMoneyController {

    @FXML
    private JFXTextField usernameField;

    @FXML
    private JFXTextField cardNoField;

    @FXML
    private JFXTextField TransAmountField;

    @FXML
    private Label amountExceptionLabel;

    @FXML
    private Label paymentSuccessfulLabel;

    @FXML
    private Label cardNoExceptionLabel;

    @FXML
    private Label usernameExceptionLabel;


    public void confirmClicked(MouseEvent mouseEvent) throws SQLException {
        usernameExceptionLabel.setVisible(false);
        cardNoExceptionLabel.setVisible(false);
        amountExceptionLabel.setVisible(false);
        String entered_u_name = usernameField.getText();
        String entered_card_no = cardNoField.getText();
        int entered_amount = Integer.valueOf(TransAmountField.getText());
        String query = "select * from useraccounts where username = '" + entered_u_name + "';";
        ResultSet rs = userLoginController.executer.executeQuery(query);
        if(rs.next() == false){
            usernameExceptionLabel.setText("No such user found");
            usernameExceptionLabel.setVisible(true);
        }
        else{
            int entered_u_id = rs.getInt("u_id");
            String correct_cardNo = rs.getString("card_number");
            int recipient_bal = rs.getInt("balance");
            if(correct_cardNo.equals(entered_card_no)){ // check if java == issue happens
                String q = "select * from useraccounts where u_id = " +
                        userLoginController.currUser.getU_id() + ";";
                ResultSet rs_ = userLoginController.executer.executeQuery(q);
                rs_.next();
                int balance = rs_.getInt("balance");
                if(entered_amount <= 0) {
                    amountExceptionLabel.setText("Enter Valid Amount !");
                    amountExceptionLabel.setVisible(true);
                    return;
                }
                if(balance >= entered_amount) {
                    q = "Update useraccounts set balance = " + (balance - entered_amount) +
                            " where u_id = " + userLoginController.currUser.getU_id() + ";";
                    userLoginController.executer.executeUpdate(q);
                    q = "Update useraccounts set balance = " + (recipient_bal + entered_amount) +
                            " where username = '" + entered_u_name + "';";
                    userLoginController.executer.executeUpdate(q);
                    String Query = "INSERT INTO transactions (payer_id, recipient_id, amount,trans_time) VALUE (?,?, ?,?);";
                    PreparedStatement pstmt = userLoginController.connection.prepareStatement(Query);
                    pstmt.setInt(1,userLoginController.currUser.getU_id());
                    pstmt.setInt(2,entered_u_id);
                    pstmt.setInt(3,entered_amount);
                    String q0 = "Select now() as curr_time;";
                    ResultSet rs1 = userLoginController.executer.executeQuery(q0);
                    rs1.next();
                    pstmt.setTimestamp(4,rs1.getTimestamp("curr_time"));
                    pstmt.executeUpdate();
                    paymentSuccessfulLabel.setText("Payment Succesful !");
                    paymentSuccessfulLabel.setVisible(true);
                }
                else{
                    amountExceptionLabel.setText("Not enough Money !");
                    amountExceptionLabel.setVisible(true);
                }
            }
            else {
                cardNoExceptionLabel.setText("Incorrect Card Number");
                cardNoExceptionLabel.setVisible(true);
            }
        }
    }

    public void back2walletPressed(MouseEvent mouseEvent) throws IOException {
        userLoginController.changeScene("Wallet");
    }
}
