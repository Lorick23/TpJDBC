package fr.dta.tpjdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Services {

	static String url = "jdbc:postgresql://localhost:5432/TPJDBC";
	private static final Logger LOGGER = LoggerFactory.getLogger(Services.class);

	private Services() {
		throw new IllegalStateException("Utility class");
	}

	public static void addClient(Client client) throws SQLException {
		
		ResultSet generatedKeys = null;
		
		try (Connection conn = DriverManager.getConnection(url, "Lorick2", "postgresql");
				PreparedStatement stmt = conn.prepareStatement(
						"INSERT INTO client(lastname, firstname, gender, favBook) VALUES(?, ?, ?, ?)",
						Statement.RETURN_GENERATED_KEYS)) {

			stmt.setString(1, client.getLastname());
			stmt.setString(2, client.getFirstname());
			stmt.setString(3, client.getGender().name());
			stmt.setObject(4, client.getFavBook(), java.sql.Types.INTEGER);
			stmt.executeUpdate();

			generatedKeys = stmt.getGeneratedKeys();
			generatedKeys.next();
			client.setId(generatedKeys.getInt("id"));

		} catch (Exception e) {
			LOGGER.trace(e.getMessage());
		}finally {
			if(generatedKeys != null) {
				generatedKeys.close();
			}
		}
	}

	public static void addBook(Book book) throws SQLException {
		
		ResultSet generatedKeys = null;
		
		try (Connection conn = DriverManager.getConnection(url, "Lorick2", "postgresql");
				PreparedStatement stmt = conn.prepareStatement("INSERT INTO book(title, author) VALUES(?, ?)",
						Statement.RETURN_GENERATED_KEYS)) {

			stmt.setString(1, book.getTitle());
			stmt.setString(2, book.getAuthor());
			stmt.executeUpdate();

			generatedKeys = stmt.getGeneratedKeys();
			generatedKeys.next();
			book.setId(generatedKeys.getInt("id"));

		} catch (Exception e) {
			LOGGER.trace(e.getMessage());
		} finally {
			if(generatedKeys != null) {
				generatedKeys.close();
			}
		}
	}

	public static void Buy(Book book, Client client) {

		try (Connection conn = DriverManager.getConnection(url, "Lorick2", "postgresql");
				PreparedStatement stmt = conn.prepareStatement("INSERT INTO buyBy(id_book, id_client) VALUES(?, ?)")) {
			stmt.setInt(1, book.getId());
			stmt.setInt(2, client.getId());
			stmt.executeUpdate();

			LOGGER.info(
					book.getTitle() + " a bien été acheté par " + client.getLastname() + " " + client.getFirstname());

		} catch (Exception e) {
			LOGGER.trace(e.getMessage());
		}
	}

	public static String getClientFromBook(Book book) throws SQLException {
		
		ResultSet resultSet = null;

		try (Connection conn = DriverManager.getConnection(url, "Lorick2", "postgresql");
				PreparedStatement stmt = conn.prepareStatement("SELECT lastname, firstname, gender from client as C\r\n"
						+ "join buyBy as BB on C.id = BB.id_client\r\n" + "where BB.id_book = ?")) {
			stmt.setInt(1, book.getId());

			resultSet = stmt.executeQuery();

			StringBuffer sb = new StringBuffer();
			sb.append("\n");
			while (resultSet.next()) {
				sb.append(resultSet.getString("lastname") + " " + resultSet.getString("firstname") + " "
						+ resultSet.getString("gender") + "\n");
			}
			return sb.toString();

		} catch (Exception e) {
			LOGGER.trace(e.getMessage());
		} finally {
			if(resultSet != null) {
				resultSet.close();
			}
		}
		return null;
	}

	public static String getBookFromClient(Client client) throws SQLException {

		ResultSet resultSet = null;
		
		try (Connection conn = DriverManager.getConnection(url, "Lorick2", "postgresql");
				PreparedStatement stmt = conn.prepareStatement("SELECT title, author from book as B\r\n"
						+ "join buyBy as BB on B.id = BB.id_book\r\n" + "where BB.id_client = ?")) {

			stmt.setInt(1, client.getId());

			resultSet = stmt.executeQuery();
			StringBuffer sb = new StringBuffer("\n");
			while (resultSet.next()) {
				sb.append(resultSet.getString("title") + " - " + resultSet.getString("author") + "\n");
			}
			return sb.toString();

		} catch (Exception e) {
			LOGGER.trace(e.getMessage());
		} finally {
			if(resultSet != null) {
				resultSet.close();
			}
		}
		return null;
	}

	public static String getClientsWhichPaid() throws SQLException {
		
		ResultSet resultSet = null;

		try (Connection conn = DriverManager.getConnection(url, "Lorick2", "postgresql");
				PreparedStatement stmt = conn.prepareStatement(
						"SELECT DISTINCT BB.id_client, C.lastname, C.firstname, C.gender from buyBy as BB\r\n"
								+ "join client as C on C.id = BB.id_client\r\n" + "order by BB.id_client asc")) {

			resultSet = stmt.executeQuery();
			StringBuffer sb = new StringBuffer();
			sb.append("\n");
			while (resultSet.next()) {

				sb.append(resultSet.getString("id_client") + " - " + resultSet.getString("lastname") + " "
						+ resultSet.getString("firstname") + " " + resultSet.getString("gender") + "\n");
			}
			return sb.toString();
		} catch (Exception e) {
			LOGGER.trace(e.getMessage());
		} finally {
			if(resultSet != null) {
				resultSet.close();
			}
		}
		return null;
	}
}
