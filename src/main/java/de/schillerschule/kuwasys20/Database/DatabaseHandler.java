package de.schillerschule.kuwasys20.Database;

import de.schillerschule.kuwasys20.Controller.kuwasysControllerBean;
import de.schillerschule.kuwasys20.Course.*;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.*;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public class DatabaseHandler {

	static Connection connection;
	static Statement statement;
	static ResultSet result;

	private static FacesMessage messageName;
	private static FacesMessage messageUsername;
	private static FacesMessage messagePassword;

	
	/**
	 * SQL METHODEN - DB CONNECTION
	 */
	
	public static void SQLConnection() {
		try {

			InitialContext cxt = new InitialContext();
			DataSource ds = (DataSource) cxt
					.lookup("java:/comp/env/jdbc/postgres");
			connection = ds.getConnection();
			System.out.println("DB open");
			statement = connection.createStatement();
			result = statement.executeQuery("SELECT VERSION()");
			if (result.next()) {
				System.out.println(result.getString(1)); // DEBUG - Connection
			}
		} catch (SQLException ex) {
			System.out.println("Error during DB connection " + ex);
			ex.printStackTrace();
		} catch (NamingException ex) {
			System.out.println("Error during DB connection " + ex);
			ex.printStackTrace();
		}
	}

	public static void SQLConnectionClose() {
		try {
			connection.close();
			System.out.println("DB close");
		} catch (SQLException ex) {
			System.out.println("Error during DB connection " + ex);
			ex.printStackTrace();
		}
	}

	
	/**
	 * USER METHODEN
	 */
	
	
	/**
	 * createUsername: zum automatischen erstellen des Usernamens aus dem echten
	 * Vor- und Nachnamen
	 * 
	 **/
	public static String createUsername(String vname, String nname) {

		// Marker für aktuellen Usernamen
		boolean isCurrentUsername = true;

		String username = "";
		String usernameDB = "";

		vname = vname.substring(0, 2);
		nname = nname.substring(0, 3);
		while (isCurrentUsername) {
			for (int i = 0; i < 1000; i++) {
				username = nname + vname + i;

				// Prüfen ob erstellter Name in DB schon vorhanden
				try {
					// Check ob users_id bereits vorhanden:
					statement = connection.createStatement();
					result = statement
							.executeQuery("SELECT users_username FROM users "
									+ "WHERE users_username = '" + username
									+ "';");
					while (result.next()) {
						usernameDB = result.getString(1);
					}
					if (username.equals(usernameDB)) {
						isCurrentUsername = true;
					} else {
						isCurrentUsername = false;
						break;
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		}
		System.out.println("Username: " + username);
		return username;
	}

	/**
	 * Generiert Passwort für aktuellen User
	 * 
	 * @return
	 */
	public static String createPassword() {

		SecureRandom random = new SecureRandom();
		String password = new BigInteger(130, random).toString(32);
		System.out.println("Passwort: " + password);

		return password;
	}

	/**
	 * Fügt Datein eines neuen Users (Schüler oder Lehrer) in die Datenbank ein
	 * 
	 * @param klasse
	 * @param nname
	 * @param vname
	 * @param geb
	 * @param konf
	 */
	public static void addUser(String klasse, String nname, String vname,
			String geb, String konf, String role) {

		// Marker für aktuelle Userdaten
		boolean isCurrentUser = true;

		// DB-Check Strings
		String nnameDB = "";
		String vnameDB = "";
		String gebDB = "";

		// Username und Passwort generieren
		String username = createUsername(vname, nname);
		String password = createPassword();

		try {
			statement = connection.createStatement();
			result = statement
					.executeQuery("SELECT users_nachname, users_vorname, users_geburtstag FROM users "
							+ "WHERE users_nachname = '"
							+ nname
							+ "' AND users_vorname = '"
							+ vname
							+ "' AND users_geburtstag = '" + geb + "';");

			while (result.next()) {
				nnameDB = result.getString(1);
				vnameDB = result.getString(2);
				gebDB = result.getString(3);
			}
			if ((nname.equals(nnameDB) && vname.equals(vnameDB) && geb
					.equals(gebDB))) {
				isCurrentUser = true;
				messageName = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Der User " + vname + " " + nname
								+ " existiert bereits!", null);

				System.out.println(">>> User bereits in DB vorhanden!");
			} else {
				isCurrentUser = false;
			}
			if (!isCurrentUser) { // Wenn der aktuelle User noch nicht in der
									// Datenbank vorhanden ist - INSERT
				statement = connection.createStatement();
				statement
						.executeUpdate("INSERT INTO users(users_nachname, users_vorname, " // User
																							// anlegen
								+ "users_geburtstag, users_konfession, "
								+ "users_klasse, users_username, users_passwort, "
								+ "users_rolle) VALUES (" + "'"
								+ nname
								+ "', "
								+ "'"
								+ vname
								+ "', "
								+ "'"
								+ geb
								+ "', "
								+ "'"
								+ konf
								+ "', "
								+ "'"
								+ klasse
								+ "', "
								+ "'"
								+ username
								+ "', "
								+ "'"
								+ password + "', " + "'" + role + "');");
				System.out.println(">>> INSERT USER"); // DEBUG

				// User in DB Rolle zuweisen
				addRole(username, role);

				messageName = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Der User " + vname + " " + nname
								+ " wurde erfolgreich angelegt und erhielt "
								+ "die Rolle '" + role + "'.", null);
				messageUsername = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Username: " + username + "", null);
				messagePassword = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Passwort: " + password + "", null);

			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		FacesContext.getCurrentInstance().addMessage("useraddsuccess_name",
				messageName);
		FacesContext.getCurrentInstance().addMessage("useraddsuccess_username",
				messageUsername);
		FacesContext.getCurrentInstance().addMessage("useraddsuccess_password",
				messagePassword);

		FacesContext.getCurrentInstance().addMessage("teacheraddsuccess_name",
				messageName);
		FacesContext.getCurrentInstance().addMessage("teacheraddsuccess_username",
				messageUsername);
		FacesContext.getCurrentInstance().addMessage("teacheraddsuccess_password",
				messagePassword);
		
		callMessage();
		
	}
	
	public static void callMessage(){
		FacesContext.getCurrentInstance().addMessage("csvimport",
				messageName);
	}

	public static String showUserFullName() {
		SQLConnection();
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext externalContext = fc.getExternalContext();
		ResultSet rs = null;
		PreparedStatement pst = null;
		String stm = "Select users_nachname,users_vorname from users where users_username='"
				+ externalContext.getUserPrincipal().getName() + "'";
		String vorName = "";
		String nachName = "";
		try {
			pst = connection.prepareStatement(stm);
			pst.execute();
			rs = pst.getResultSet();

			while (rs.next()) {

				nachName = rs.getString(1);
				vorName = rs.getString(2);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		SQLConnectionClose();
		return vorName + " " + nachName;
	}

	/**
	 * Gibt gegebenem User die gegebenen Rechte
	 * 
	 * @param username, role
	 */
	public static void addRole(String username, String role) {

		try {
			statement = connection.createStatement();
			statement.executeUpdate("INSERT INTO roles("
					+ "users_username, roles_rolle)" + "VALUES ('" + username
					+ "', " + "'" + role + "');");
			System.out.println(">>> INSERT ROLE"); // DEBUG
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * COURSE METHODEN
	 */
	
	public static void listCourses() {
		SQLConnection();
		try {
			statement = connection.createStatement();
			result = statement.executeQuery("SELECT * FROM course");
			CourseBean.emptyCourses();
			while (result.next()) {
				System.out.println(result.getInt("course_id")
						+ result.getString("course_name")
						+ result.getInt("course_kurslehrer")
						+ result.getString("course_faecherverbund")
						+ result.getInt("course_termin")
						+ result.getString("course_beschreibung"));
				CourseBean.addToCourses(new CourseBean.Course(result
						.getInt("course_id"), result.getString("course_name"),
						result.getInt("course_kurslehrer"), result
								.getString("course_faecherverbund"), result
								.getInt("course_termin"), result
								.getString("course_beschreibung")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		SQLConnectionClose();
	}

	public static void addCourse(String name, String faecherverbund,
			int kurslehrer, int termin, String beschreibung) {
		try {
			SQLConnection();
			statement = connection.createStatement();
			statement
					.executeUpdate("INSERT INTO course (course_name, course_faecherverbund, course_kurslehrer, course_termin, course_beschreibung)"
							+ "VALUES ('"
							+ name
							+ "', '"
							+ faecherverbund
							+ "', "
							+ kurslehrer
							+ ", "
							+ termin
							+ ", '"
							+ beschreibung + "');");
			System.out.println(">>> INSERT COURSE"); // DEBUG
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		SQLConnectionClose();
	}
	
	/**
	 * SYSTEM METHODEN
	 */
	
	public static void systemState() {
		try {
			SQLConnection();
			statement = connection.createStatement();
			result = statement.executeQuery("SELECT * FROM system;");	
			if (result.next()){
				kuwasysControllerBean.setPhase(result.getInt("system_phase"));
				kuwasysControllerBean.setTertial(result.getInt("system_tertial"));
				kuwasysControllerBean.setYear(result.getInt("system_jahr"));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		SQLConnectionClose();
	}
	

	
	
	/**
	 * GRADELIST METHODEN
	 */
	public static void listGradelist() {
		SQLConnection();
		try {
			statement = connection.createStatement();
			result = statement.executeQuery("SELECT * FROM course");
			CourseBean.emptyCourses();
			while (result.next()) {
				System.out.println(result.getInt("course_id")
						+ result.getString("course_name")
						+ result.getInt("course_kurslehrer")
						+ result.getString("course_faecherverbund")
						+ result.getInt("course_termin")
						+ result.getString("course_beschreibung"));
				CourseBean.addToCourses(new CourseBean.Course(result
						.getInt("course_id"), result.getString("course_name"),
						result.getInt("course_kurslehrer"), result
								.getString("course_faecherverbund"), result
								.getInt("course_termin"), result
								.getString("course_beschreibung")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		SQLConnectionClose();
	}

	public static void addToGradelist(int note, String bemerkung,
			int usersid, int kursid) {
		try {
			SQLConnection();
			statement = connection.createStatement();
			statement
					.executeUpdate("INSERT INTO course (course_name, course_faecherverbund, course_kurslehrer, course_termin, course_beschreibung)"
							+ "VALUES ('"
							+ note
							+ "', '"
							+ faecherverbund
							+ "', "
							+ kurslehrer
							+ ", "
							+ termin
							+ ", '"
							+ beschreibung + "');");
			System.out.println(">>> INSERT COURSE"); // DEBUG
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		SQLConnectionClose();
	}
	
	

	public static void commitPhase(int p){
		try {
			SQLConnection();
			statement = connection.createStatement();
			statement.executeUpdate("UPDATE system SET system_phase="+p+";");	
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		SQLConnectionClose();
	}

	public static void commitTertial(int tertial, int year) {
		try {
			SQLConnection();
			statement = connection.createStatement();
			statement.executeUpdate("UPDATE system SET system_jahr="+year+", system_tertial="+tertial+";");	
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		SQLConnectionClose();
		
	}
	
}
