import java.sql.*;

public class JDBC_Insert {
	public static void main(String args[]) {
		Connection con = null;
		try {
			// Load MySQL JDBC Driver
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		try {
			// Establish connection to MySQL database
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");

			// Disable auto-commit mode for transaction
			con.setAutoCommit(false);

			// Prepare the SQL INSERT statement
			PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO staff (name, address) VALUES (?, ?)");

			// First row
			preparedStatement.setString(1, "AAA");
			preparedStatement.setString(2, "AAA");
			int row = preparedStatement.executeUpdate();

			// Second row
			preparedStatement.setString(1, "BBB");
			preparedStatement.setString(2, "BBB");
			row = preparedStatement.executeUpdate();

			// Third row
			preparedStatement.setString(1, "CCC");
			preparedStatement.setString(2, "CCC");
			row = preparedStatement.executeUpdate();

			// Commit the transaction
			con.commit();
			con.setAutoCommit(true);

			// Select all records from the staff table
			Statement stmt2 = con.createStatement();
			ResultSet rs = stmt2.executeQuery("SELECT * FROM staff");

			// Using ResultSetMetaData to print the column names of the query result
			ResultSetMetaData rsmd = rs.getMetaData();
			int numberOfColumns = rsmd.getColumnCount();
			for (int i = 1; i <= numberOfColumns; i++) {
				System.out.println("   " + rsmd.getColumnName(i));
			}

			System.out.println("\n\n");

			// Print each row in the result set
			while (rs.next()) {
				String sname = rs.getString("name");
				String saddress = rs.getString("address");
				System.out.println("   " + sname + "  " + saddress);
			}

			stmt2.close();
			System.out.println("\n\n");

			// Using DatabaseMetaData to print the column names of the 'staff' table
			DatabaseMetaData md = con.getMetaData();
			rs = md.getColumns(null, null, "staff", null);
			while (rs.next()) {
				String name = rs.getString("COLUMN_NAME");
				String type = rs.getString("TYPE_NAME");
				int size = rs.getInt("COLUMN_SIZE");
				System.out.println("Column name: [" + name + "]; type: [" + type + "]; size: [" + size + "]");
			}

			// Close the connection
			con.close();
		} catch (SQLException ex) {
			// Rollback in case of exception
			try {
				if (con != null) {
					con.rollback();
					con.close();
				}
			} catch (SQLException e) {
				System.out.println("\nError rolling back\n");
			}

			// Print the SQLException details
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
