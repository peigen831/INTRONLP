package assignment5ir;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
	
	private Connection con;
	
	public static void main( String args[] ) {
		DatabaseConnector dbCon = new DatabaseConnector();
		try {
			dbCon.openConnection();
			dbCon.closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Opened database successfully");
	}
	
	public DatabaseConnector() {
		con = null;
	}
	
	public Connection getConnection() {
		return con;
	}
	
	private Connection openConnection() throws SQLException, ClassNotFoundException {
		if (con != null) {
			closeConnection();
		}
		Class.forName("org.sqlite.JDBC");
		con = DriverManager.getConnection("jdbc:sqlite:test.db");
		return con;
	}
	
	private void closeConnection() throws SQLException {
		con.close();
	}
	
}
