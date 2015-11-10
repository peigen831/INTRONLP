package assignment5ir;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import common.DatabaseConnector;

public class DatabaseConnector5 extends DatabaseConnector {
	
	public DatabaseConnector5(String packageName) {
		super(packageName);
		con = null;
	}
	
	public long insertTerm(String term) throws SQLException {
		PreparedStatement stmt = null;
		String sql = "INSERT OR IGNORE INTO `term` (`term`) VALUES (?)";
		
		stmt = con.prepareStatement(sql);
		stmt.setString(1, term);
		stmt.executeUpdate();
		con.commit();
		
		return getInsertId();
	}
	
	public long insertDocument(String documentFilepath) throws SQLException {
		PreparedStatement stmt = null;
		String sql = "INSERT OR IGNORE INTO `document` (`filepath`) VALUES (?)";
		
		stmt = con.prepareStatement(sql);
		stmt.setString(1, documentFilepath);
		stmt.executeUpdate();
		con.commit();
		
		return getInsertId();
	}
	
	public long insertRelation(Relation relation) throws SQLException {
		PreparedStatement stmt = null;
		String sql = "INSERT OR IGNORE INTO `relation` "
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
	
	public long getTotalNumberOfDocuments() throws SQLException {
		PreparedStatement stmt = null;
		long result = -1;
		String sql = "SELECT COUNT(*) FROM `document`";
		
		stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			result = rs.getLong(1);
		}
		rs.close();
		return result;
	}
	
	public long getDocumentFrequency(String term) throws SQLException {
		PreparedStatement stmt = null;
		long result = -1;
		String sql = "SELECT COUNT(*) FROM `relation` "
				+ "INNER JOIN `term` ON `term`.`id` = `relation`.`term_id` "
				+ "WHERE `term` = ?";
		
		stmt = con.prepareStatement(sql);
		stmt.setString(1, term);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			result = rs.getLong(1);
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
	
	public ArrayList<Relation> getRelationsGivenTerms(String[] terms) throws SQLException {
		PreparedStatement stmt = null;
		ArrayList<Relation> result = new ArrayList<>();
		String sql = "SELECT `term`, `filepath`, `termFrequency` FROM `relation` "
				+ "INNER JOIN `term` ON `term`.`id` = `relation`.`term_id` "
				+ "INNER JOIN `document` ON `document`.`id` = `relation`.`document_id` "
				+ "WHERE `term` IN (";
		for (int i = 0; i < terms.length; i++) {
			sql += "?";
			if (i < terms.length - 1) {
				sql += ", ";
			}
			else {
				sql += ") GROUP BY `document_id` HAVING COUNT(`term_id`) == ?"; // HAVING COUNT(`term_id`) == ?
			}
		}
		
		stmt = con.prepareStatement(sql);
		
		for (int i = 0; i < terms.length; i++) {
			stmt.setString(i + 1, terms[i]);
		}
		stmt.setInt(terms.length + 1, terms.length);
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
	
	public ArrayList<Relation> getRelationGivenTerm(String term) throws SQLException {
		PreparedStatement stmt = null;
		ArrayList<Relation> result = new ArrayList<>();
		String sql = "SELECT `term`, `filepath`, `termFrequency` FROM `relation` "
				+ "INNER JOIN `term` ON `term`.`id` = `relation`.`term_id` "
				+ "INNER JOIN `document` ON `document`.`id` = `relation`.`document_id` "
				+ "WHERE `term` = ?";
		
		stmt = con.prepareStatement(sql);
		stmt.setString(1, term);
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
	
	public static void main(String[] args) {
		DatabaseConnector5.printDatabase();
	}
	
	private static void printDatabase() {
		DatabaseConnector5 db = new DatabaseConnector5("assignment5ir");
		try {
			db.openConnection();
			System.out.println("-------- TERMS --------");
			ArrayList<String> list = db.selectAllTerms();
			for (String item : list) {
				System.out.println(item);
			}
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("-------- DOCUMENTS --------");
			list = db.selectAllDocuments();
			for (String item : list) {
				System.out.println(item);
			}
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("-------- RELATIONS --------");
			ArrayList<Relation> list2 = db.selectAllRelations();
			for (Relation item : list2) {
				System.out.println(item);
			}
			db.closeConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
