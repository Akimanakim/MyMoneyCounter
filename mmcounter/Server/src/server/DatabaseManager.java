package server;

import java.sql.Connection;
import java.sql.DriverManager;
public class DatabaseManager {
    private static DatabaseManager dm;

    public Connection connection;

    private DatabaseManager() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        String url = "jdbc:postgresql://localhost:5432/mmcounter";
        String login = "postgres";
        String password = "4589";

        try {
            connection = DriverManager.getConnection(url, login, password);
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public static synchronized DatabaseManager getInstance(){
        if(dm == null){
            dm = new DatabaseManager();
        }
        return dm;
    }

    public Connection getConnection() {
        return connection;
    }
}
