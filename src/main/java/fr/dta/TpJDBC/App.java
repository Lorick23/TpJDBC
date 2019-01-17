package fr.dta.TpJDBC;

import java.sql.*;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws SQLException {

		String url = "jdbc:postgresql://localhost:5432/TPJDBC";

		try (Connection conn = DriverManager.getConnection(url, "Lorick2", "postgresql");
				Statement stmt = conn.createStatement()) {
			stmt.executeUpdate("DROP TABLE IF EXISTS buyBy cascade");
			stmt.executeUpdate("DROP TABLE IF EXISTS book cascade");
			stmt.executeUpdate("DROP TABLE IF EXISTS client cascade");

			stmt.executeUpdate(
					"CREATE TABLE book(id serial PRIMARY KEY NOT NULL, title varchar(255) NOT NULL, author varchar(255) NOT NULL)");

			stmt.executeUpdate(
					"CREATE TABLE client(id serial PRIMARY KEY NOT NULL, lastname varchar(255) NOT NULL, firstname varchar(255) NOT NULL, gender varchar(1) NOT NULL, fav_book INTEGER)");

			stmt.executeUpdate(
					"CREATE TABLE buyBy(id_book INT NOT NULL, id_client INT NOT NULL, PRIMARY KEY(id_client, id_book), FOREIGN KEY (id_client) references client(id), FOREIGN KEY (id_book) references book(id))");

			Book bookA = new Book("Le Seigneur des Anneaux", "J.R.R Tolkien");
			Services.addBook(bookA);
			Book bookB = new Book("Devenir Batman en 10 leçons", "Batman");
			Services.addBook(bookB);
			Book bookC = new Book("L'art de voler", "Arsène Lupin");
			Services.addBook(bookC);

			Client clientA = new Client("Mattera", "Lorick", Gender.M);
			Services.addClient(clientA);
			Client clientB = new Client("Payen", "Marine", Gender.F, 2);
			Services.addClient(clientB);

			Services.Buy(bookA, clientA);
			Services.Buy(bookC, clientA);
			Services.Buy(bookA, clientB);

			System.out.println(Services.getClientFromBook(bookA));
			System.out.println(Services.getBookFromClient(clientA));
			System.out.println(Services.getClientsWhichPaid());

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
