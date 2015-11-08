package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DatabaseConnector {
	
	protected Connection con;
	protected String packageName;
	
	protected DatabaseConnector(String packageName) {
		this.packageName = packageName;
		con = null;
	}
	
	protected long getInsertId() throws SQLException {
		PreparedStatement stmt = null;
		long insertId = -1;
		
		stmt = con.prepareStatement("SELECT last_insert_rowid()");
		ResultSet generatedKeys = stmt.executeQuery();
		if (generatedKeys.next()) {
		    insertId = generatedKeys.getLong(1);
		}
		
		return insertId;
	}
	
	public Connection getConnection() {
		return con;
	}
	
	public Connection openConnection() throws SQLException, ClassNotFoundException {
		if (con != null) {
			closeConnection();
		}
		Class.forName("org.sqlite.JDBC");
		con = DriverManager.getConnection("jdbc:sqlite:" + packageName + ".db");
		System.out.println("jdbc:sqlite:" + packageName + ".db");
		con.setAutoCommit(false);
		return con;
	}
	
	public void closeConnection() throws SQLException {
		con.close();
	}
	
}
