package assignment5ir;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import common.DatabaseConnector;

public class DatabaseConnector5 extends DatabaseConnector {
	
	public static void main( String args[] ) {
		DatabaseConnector5 dbCon = new DatabaseConnector5("assignment5ir");
		try {
			dbCon.openConnection();
			dbCon.insertTerm("test");
			dbCon.insertDocument("C:\\Users\\Amanda\\Documents\\pie.txt");
			Relation relation = new Relation("test", "C:\\Users\\Amanda\\Documents\\pie.txt", 5);
			dbCon.insertRelation(relation);
			System.out.println(dbCon.selectAllTerms().toString());
			System.out.println(dbCon.selectAllDocuments().toString());
			System.out.println(dbCon.selectAllRelations().toString());
			dbCon.closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Opened database successfully");
	}
	
	public DatabaseConnector5(String packageName) {
		super(packageName);
		con = null;
	}
	
	public long insertTerm(String term) throws SQLException {
		PreparedStatement stmt = null;
		String sql = "INSERT INTO `term` (`term`) VALUES (?)";
		
		stmt = con.prepareStatement(sql);
		stmt.setString(1, term);
		stmt.executeUpdate();
		con.commit();
		
		return getInsertId();
	}
	
	public long insertDocument(String documentFilepath) throws SQLException {
		PreparedStatement stmt = null;
		String sql = "INSERT INTO `document` (`filepath`) VALUES (?)";
		
		stmt = con.prepareStatement(sql);
		stmt.setString(1, documentFilepath);
		stmt.executeUpdate();
		con.commit();
		
		return getInsertId();
	}
	
	public long insertRelation(Relation relation) throws SQLException {
		PreparedStatement stmt = null;
		String sql = "INSERT INTO `relation` "
				+ "(`term_id`, `document_id`, `termFrequency`) VALUES "
				+ "((SELECT `id` FROM `term` WHERE `term` = ?), "
				+ "(SELECT `id` FROM `document` WHERE `filepath` = ?), ?)";
		
		stmt = con.prepareStatement(sql);
		stmt.setString(1, relation.getTerm());
		stmt.setString(2, relation.getDocumentFilepath());
		stmt.setInt(3, relation.getTermFrequency());
		stmt.executeUpdate();
		con.commit();
		
		return getInsertId();
	}
	
	public ArrayList<String> selectAllTerms() throws SQLException {
		PreparedStatement stmt = null;
		ArrayList<String> result = new ArrayList<>();
		String sql = "SELECT `term` FROM `term`";
		
		stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			result.add(rs.getString("term"));
		}
		rs.close();
		return result;
	}
	
	public ArrayList<String> selectAllDocuments() throws SQLException {
		PreparedStatement stmt = null;
		ArrayList<String> result = new ArrayList<>();
		String sql = "SELECT `filepath` FROM `document`";
		
		stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			result.add(rs.getString("filepath"));
		}
		rs.close();
		return result;
	}
	
	public ArrayList<Relation> selectAllRelations() throws SQLException {
		PreparedStatement stmt = null;
		ArrayList<Relation> result = new ArrayList<>();
		String sql = "SELECT `term`, `filepath`, `termFrequency` FROM `relation` "
				+ "INNER JOIN `term` ON `term`.`id` = `relation`.`term_id` "
				+ "INNER JOIN `document` ON `document`.`id` = `relation`.`document_id`";
		
		stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			Relation relation = new Relation();
			relation.setTerm(rs.getString("term"));
			relation.setDocumentFilepath(rs.getString("filepath"));
			relation.setTermFrequency(rs.getInt("termFrequency"));
			result.add(relation);
		}
		rs.close();
		return result;
	}
	
}
