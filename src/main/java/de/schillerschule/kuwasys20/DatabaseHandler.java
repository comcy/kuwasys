package de.schillerschule.kuwasys20;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHandler {

	// Variablen
	// Datenbank Verbindung
	static String url = "jdbc:postgresql://localhost/kuwasys";
	static String user = "ijcy";
	static String password = "12kuwasys34";

	static Connection connection;
	static Statement statement;
	static ResultSet result;

	/**
	 * Methode die Verbindung zur Datenbank herstellt
	 */
	public static void SQLConnection() {
		try {
			connection = DriverManager.getConnection(url, user, password);
			statement = connection.createStatement();
			result = statement.executeQuery("SELECT VERSION()");
			if (result.next()) {
				System.out.println(result.getString(1)); // DEBUG - Connection Status
			}
		} catch (SQLException ex) {
			System.out.println("Error during DB connection " + ex); // DEBUG - Connection Error Status
		}
	}
}