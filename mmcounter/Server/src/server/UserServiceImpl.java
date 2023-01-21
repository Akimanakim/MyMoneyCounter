package server;

import api.data.Account;
import api.data.Transaction;
import api.services.UserService;

import java.sql.*;

import java.util.ArrayList;
import java.util.UUID;

public class UserServiceImpl implements UserService {

    private Account currentAccount;

    @Override
    public int getValidAccount(Account account){
        ResultSet resSet = null;

        String select = "SELECT * FROM accounts WHERE login =? and password =?";

        try {
            Connection conn = DatabaseManager.getInstance().getConnection();
            PreparedStatement pst = conn.prepareStatement(select);
            pst.setString(1, account.getName());
            pst.setString(2, account.getPassword());
            resSet = pst.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        int counter = 0;
        while (true) {
            try {
                if (!resSet.next()) break;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            counter++;
        }
        return counter;
    }

    @Override
    public void setCurrentAccount(Account account){
        this.currentAccount = account;
    }

    @Override
    public Account getCurrentAccount(){
        return currentAccount;
    }

    @Override
    public void writeToDatabase(Account account) {

        String query = "INSERT INTO accounts(id, login, password) VALUES(?, ?, ?)";
        Connection conn = DatabaseManager.getInstance().getConnection();

        try (PreparedStatement pst = conn.prepareStatement(query)){
            String id = UUID.randomUUID().toString();
            pst.setString(1, id);
            pst.setString(2, account.getName());
            pst.setString(3, account.getPassword());
            pst.executeUpdate();
            System.out.println("Successfully added");
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Transaction> writeToTable() {
        ArrayList<Transaction> transactions = new ArrayList<>();
        Connection conn = DatabaseManager.getInstance().getConnection();
        String accountName = currentAccount.getName();

        String transactionQuery = "SELECT id, transaction_date, category, amount, isexpense, description FROM transactions WHERE account = '" + accountName +"'";

        try{
            Statement statement = conn.createStatement();
            ResultSet queryOutput = statement.executeQuery(transactionQuery);

            while (queryOutput.next()){

                String queTransId = queryOutput.getString("id");
                String queTransDate = queryOutput.getString("transaction_date");
                String queTransCategory = queryOutput.getString("category");
                Integer queTransAmount = queryOutput.getInt("amount");
                Boolean queTransIsExpense = queryOutput.getBoolean("isexpense");
                String queTransDescription = queryOutput.getString("description");

                transactions.add(new Transaction(queTransId, queTransAmount, queTransDate, queTransCategory, queTransIsExpense, queTransDescription));
            }
            //conn.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return transactions;
    }
    @Override
    public void setNewTransaction(Transaction transaction){
        String query = "INSERT INTO transactions(id, account, transaction_date, category, amount, isExpense, description) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?)";
        Connection conn = DatabaseManager.getInstance().getConnection();

        try (PreparedStatement pst = conn.prepareStatement(query)){

            pst.setString(1, transaction.getId());
            pst.setString(2, currentAccount.getName());
            pst.setString(3, transaction.getDate());
            pst.setString(4, transaction.getCategory());
            pst.setInt(5, transaction.getAmount());
            pst.setBoolean(6, transaction.isExpense());
            pst.setString(7, transaction.getDescription());
            pst.executeUpdate();
            System.out.println("Successfully added");
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void deleteFromDatabase(Transaction transaction){
        String query = "DELETE FROM transactions WHERE id = '" +transaction.getId()+"'";
        Connection conn = DatabaseManager.getInstance().getConnection();
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.executeUpdate();
            System.out.println("Transaction has benn deleted");
            //conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
