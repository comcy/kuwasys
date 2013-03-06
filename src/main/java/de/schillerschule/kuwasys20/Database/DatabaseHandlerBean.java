package de.schillerschule.kuwasys20.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * POSTGRES DB Connection und JDBC-Driver
 * 
 * @author cy
 * 
 */
public class DatabaseHandlerBean {

	// Variablen
	// Datenbank Verbindung
	static String driver = "org.postgresql.Driver";
	static String url = "jdbc:postgresql://localhost/kuwasys";
	static String user = "ijcy";
	static String password = "12kuwasys34";

	int eId;

	public int geteId() {
		return eId;
	}
	
	public void seteId(int eId) {
		this.eId = eId;
	}

	public static Connection getConnection() throws Exception {
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(url, user, password);
        return conn;
      }
	
	static Connection connection;
	static Statement statement;
	static ResultSet result;

	/**
	 * Methode die Verbindung zur Datenbank herstellt
	 * @throws ClassNotFoundException 
	 */
	public static void SQLConnection() throws ClassNotFoundException {
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, user, password);
			statement = connection.createStatement();
			result = statement.executeQuery("SELECT VERSION()");
			if (result.next()) {
				System.out.println(result.getString(1)); // DEBUG - Connection
			}
		} catch (SQLException ex) {
			System.out.println("Error during DB connection " + ex);
		}
		catch (ClassNotFoundException ex){
			System.out.println("Error during DB connection " + ex);
		}
	}
}