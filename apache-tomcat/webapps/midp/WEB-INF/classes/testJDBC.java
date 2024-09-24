import java.sql.*;

public class testJDBC {
    public static void main(String[] args) {
        try {
            // Load MySQL JDBC Driver
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            // Establish connection to MySQL database
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");

            // Create a statement
            Statement stmt = con.createStatement();

            // Execute an INSERT query
            stmt.executeUpdate("INSERT INTO staff (name, address) VALUES ('tom','surrey')");

            // Close the statement and connection
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            System.out.println("\n--- SQLException caught ---\n");
            while (ex != null) {
                System.out.println("Message: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("ErrorCode: " + ex.getErrorCode());
                ex = ex.getNextException();
                System.out.println("");
            }
        }
    }
}
