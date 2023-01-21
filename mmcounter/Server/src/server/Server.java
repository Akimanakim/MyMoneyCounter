package server;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Server {
    public static void main(String[] args){

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e){
            throw new RuntimeException(e);
        }
        try {
            Connection conn = DatabaseManager.getInstance().getConnection();
            Statement statement = conn.createStatement();
            String sql1 = "CREATE TABLE accounts ( id varchar(40) NOT NULL PRIMARY KEY, login varchar(20) NOT NULL, password varchar(20) NOT NULL, balance INT)";
            statement.execute(sql1);
            String sql2 = "CREATE TABLE transactions ( id varchar(40) NOT NULL, account varchar(20) NOT NULL, transaction_date varchar(40) NOT NULL, " +
                    "category varchar(20) NOT NULL, amount INT NOT NULL, isExpense BOOLEAN NOT NULL, description varchar(50))";
            statement.execute(sql2);
            statement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}