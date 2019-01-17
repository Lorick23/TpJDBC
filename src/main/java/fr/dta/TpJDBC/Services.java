package fr.dta.TpJDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Services {

	public static void addClient(Client client) throws SQLException {
		String url = "jdbc:postgresql://localhost:5432/TPJDBC";
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
//		try (Connection conn = DriverManager.getConnection(url, "Lorick2", "postgresql");
//				PreparedStatement stmt = conn.prepareStatement(
//						"INSERT INTO client(lastname, firstname, gender, fav_book) VALUES(?, ?, ?, ?)",
//						Statement.RETURN_GENERATED_KEYS)) {
		try {

			 conn = DriverManager.getConnection(url, "Lorick2", "postgresql");
			 stmt = conn.prepareStatement(
					"INSERT INTO client(lastname, firstname, gender, fav_book) VALUES(?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			stmt.setString(1, client.getLastname());
			stmt.setString(2, client.getFirstname());
			stmt.setString(3, client.getGender().name());
			stmt.setObject(4, client.getFav_book(), java.sql.Types.INTEGER);
			stmt.executeUpdate();

			ResultSet generatedKeys = stmt.getGeneratedKeys();
			generatedKeys.next();
			client.setId(generatedKeys.getInt("id"));

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			conn.close();
			stmt.close();
		}
	}

	public static void addBook(Book book) {
		String url = "jdbc:postgresql://localhost:5432/TPJDBC";
		
//		Connection conn = null;
//		PreparedStatement stmt = null;

		try (Connection conn = DriverManager.getConnection(url, "Lorick2", "postgresql");
				PreparedStatement stmt = conn.prepareStatement("INSERT INTO book(title, author) VALUES(?, ?)",
						Statement.RETURN_GENERATED_KEYS)) {
//		try {
//			 conn = DriverManager.getConnection(url, "Lorick2", "postgresql");
//			 stmt = conn.prepareStatement("INSERT INTO book(title, author) VALUES(?, ?)",
//					Statement.RETURN_GENERATED_KEYS);

			stmt.setString(1, book.getTitle());
			stmt.setString(2, book.getAuthor());
			stmt.executeUpdate();

			ResultSet generatedKeys = stmt.getGeneratedKeys();
			generatedKeys.next();
			book.setId(generatedKeys.getInt("id"));

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void Buy(Book book, Client client) {
		String url = "jdbc:postgresql://localhost:5432/TPJDBC";

		try (Connection conn = DriverManager.getConnection(url, "Lorick2", "postgresql");
				PreparedStatement stmt = conn.prepareStatement("INSERT INTO buyBy(id_book, id_client) VALUES(?, ?)")) {
			stmt.setInt(1, book.getId());
			stmt.setInt(2, client.getId());
			stmt.executeUpdate();

			System.out.println(
					book.getTitle() + " a bien été acheté par " + client.getLastname() + " " + client.getFirstname());

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static String getClientFromBook(Book book) {
		String url = "jdbc:postgresql://localhost:5432/TPJDBC";

		try (Connection conn = DriverManager.getConnection(url, "Lorick2", "postgresql");
				PreparedStatement stmt = conn.prepareStatement("SELECT lastname, firstname, gender from client as C\r\n"
						+ "join buyBy as BB on C.id = BB.id_client\r\n" + "where BB.id_book = ?")) {
			stmt.setInt(1, book.getId());
			ResultSet resultSet = stmt.executeQuery();
			String result = "\n";
			while (resultSet.next()) {
				result += resultSet.getString("lastname");
				result += " " + resultSet.getString("firstname");
				result += " " + resultSet.getString("gender") + "\n";
			}
			return result;

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public static String getBookFromClient(Client client) {
		String url = "jdbc:postgresql://localhost:5432/TPJDBC";

		try (Connection conn = DriverManager.getConnection(url, "Lorick2", "postgresql");
				PreparedStatement stmt = conn.prepareStatement("SELECT title, author from book as B\r\n"
						+ "join buyBy as BB on B.id = BB.id_book\r\n" + "where BB.id_client = ?")) {
			stmt.setInt(1, client.getId());
			ResultSet resultSet = stmt.executeQuery();
			String result = "\n";
			while (resultSet.next()) {
				result += resultSet.getString("title");
				result += " - " + resultSet.getString("author") + "\n";
			}
			return result;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public static String getClientsWhichPaid() {
		String url = "jdbc:postgresql://localhost:5432/TPJDBC";

		try (Connection conn = DriverManager.getConnection(url, "Lorick2", "postgresql");
				PreparedStatement stmt = conn.prepareStatement(
						"SELECT DISTINCT BB.id_client, C.lastname, C.firstname, C.gender from buyBy as BB\r\n"
								+ "join client as C on C.id = BB.id_client\r\n" + "order by BB.id_client asc")) {
			ResultSet resultSet = stmt.executeQuery();

			String result = "\n";
			while (resultSet.next()) {
				result += resultSet.getString("id_client");
				result += " - " + resultSet.getString("lastname");
				result += " " + resultSet.getString("firstname");
				result += " " + resultSet.getString("gender") + "\n";
			}
			return result;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
}
