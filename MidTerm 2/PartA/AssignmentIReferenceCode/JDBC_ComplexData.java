import java.sql.*;
import java.io.*;
import java.util.Date;
import java.util.UUID;
import java.text.*;
import java.nio.*;
/*
run the following commands in SQLPlus
drop table staff;
create table staff (ID raw(16), name char(10), address char(10), picture blob, startdate date);
*/
public class JDBC_ComplexData {
	public static byte[] asBytes(UUID uuid) {
    		ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
    		bb.putLong(uuid.getMostSignificantBits());
    		bb.putLong(uuid.getLeastSignificantBits());
    		return bb.array();
  	}
	public static UUID asUuid(byte[] bytes) {
    		ByteBuffer bb = ByteBuffer.wrap(bytes);
    		long firstLong = bb.getLong();
    		long secondLong = bb.getLong();
    		return new UUID(firstLong, secondLong);
  	}
public static void main(String args[]) {
  		Connection con = null;
        	try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (Exception ex) {
			System.out.println("Message: " + ex.getMessage ()); 
			return;
                }
        	try {
        		con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "oracle1");
        		PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO staff (ID,name, address, picture, startdate) VALUES (?,?,?,?,?)");
			UUID uuid = UUID.randomUUID();
			preparedStatement.setBytes(1,asBytes(uuid));
			preparedStatement.setString(2, "John");
            		preparedStatement.setString(3, "Burnaby");
                	try {
				FileInputStream fin = new FileInputStream("blah.jpg");
      				preparedStatement.setBinaryStream(4, fin);
			} catch (Exception ex) { }
			java.util.Date date = new java.util.Date();
			long t = date.getTime();
			java.sql.Date sqlDate = new java.sql.Date(t);
			preparedStatement.setDate(5, sqlDate);
			int row = preparedStatement.executeUpdate();
			preparedStatement.close();
//Now read the table
			Statement stmt2 = con.createStatement( );
            		ResultSet rs = stmt2.executeQuery("SELECT id, name, address, picture, startdate FROM staff");
			ResultSetMetaData rsmd = rs.getMetaData();
			int numberOfColumns = rsmd.getColumnCount();
			for (int i = 1; i <= numberOfColumns; i++) 
				System.out.print("   \t" + rsmd.getColumnName(i) + " \t\t");
	        	while (rs.next()) {
        			byte[] raw = rs.getBytes(1);
              			UUID sid  = asUuid(raw);
                		String sname = rs.getString(2);
                		String saddress = rs.getString(3);
				Blob b = rs.getBlob(4);
				byte bArr[] = b.getBytes(1, (int) b.length());
				try {
					FileOutputStream fout = new FileOutputStream("test2.jpg");
					fout.write(bArr);
                		} catch (Exception ex) { }
				java.util.Date startDate = rs.getDate(5);
    	        		System.out.println("\t "+ sid +"\t" + sname + " \t" + saddress +"\t "+ " test2.jpg " +"\t "+startDate);
			}
			stmt2.close();
			con.close();
        	} catch(SQLException ex) {
            	
            	   while (ex != null) { 
                	System.out.println("Message: " + ex.getMessage ()); 
                	System.out.println("SQLState: " + ex.getSQLState ()); 
                	System.out.println("ErrorCode: " + ex.getErrorCode ()); 
                	ex = ex.getNextException(); 
                	System.out.println("");
            	  } 
               }
       }
}


