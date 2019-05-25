package college.dbProject.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connection {

    public static Connection conn = null;
    /** Connect to MySQL driver. */
    public static void connection() {
        try {
//            Class.forName("jdbc:mysql://localhost:3306/collegedb?autoReconnect=true&useSSL=false", "root", "root");
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
    /** Connect to MySQL database. */
    public static Connection connectionToMySQL() {
        connection();
        String host = "jdbc:mysql://localhost:3306/collegedb";
        String username = "root";
        String password = "root";
        try {
            //connect = DriverManager.getConnection(host, username, password);
            conn =
                    DriverManager.getConnection("jdbc:mysql://localhost/collegedb?" +
                            "user=root&password=root");

            // Do something with the Connection
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return conn;
    }

}
