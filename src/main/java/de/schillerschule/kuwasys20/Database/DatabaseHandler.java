package de.schillerschule.kuwasys20.Database;

import de.schillerschule.kuwasys20.Controller.kuwasysControllerBean;
import de.schillerschule.kuwasys20.Course.*;
import de.schillerschule.kuwasys20.Course.CourseBean.Course;
import de.schillerschule.kuwasys20.Gradelist.GradelistBean;
import de.schillerschule.kuwasys20.Gradelist.GradelistBean.Grades;
import de.schillerschule.kuwasys20.Teacher.TeacherBean;
import de.schillerschule.kuwasys20.Teacher.TeacherBean.Teacher;
import de.schillerschule.kuwasys20.User.UserBean;
import de.schillerschule.kuwasys20.User.UserBean.User;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.*;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

public class DatabaseHandler {

	Connection connection;
	Connection connection2;
	Connection connection3;
	Connection connection4;

	Statement statement;
	Statement statement2;
	Statement statement3;
	Statement statement4;

	ResultSet result;
	ResultSet result2;
	ResultSet result3;
	ResultSet result4;

	private static FacesMessage messageName;

	/** SQL METHODEN - DB CONNECTION **/

	public void SQLConnection() {
		try {

			InitialContext cxt = new InitialContext();
			DataSource ds = (DataSource) cxt
					.lookup("java:/comp/env/jdbc/postgres");
			connection = ds.getConnection();
			System.out.println("DB open");
			statement = connection.createStatement();
			result = statement.executeQuery("SELECT VERSION()");
			if (result.next()) {
				// System.out.println(result.getString(1)); // DEBUG -
				// Connection
			}
		} catch (SQLException ex) {
			System.out.println("Error during DB connection " + ex);
			ex.printStackTrace();
		} catch (NamingException ex) {
			System.out.println("Error during DB connection " + ex);
			ex.printStackTrace();
		}
	}

	public void SQLConnection2() {
		try {

			InitialContext cxt = new InitialContext();
			DataSource ds = (DataSource) cxt
					.lookup("java:/comp/env/jdbc/postgres");
			connection2 = ds.getConnection();
			System.out.println("   DB2 open");
			statement2 = connection2.createStatement();
			result2 = statement2.executeQuery("SELECT VERSION()");
			if (result2.next()) {
				// System.out.println(result2.getString(1)); // DEBUG -
				// Connection
			}
		} catch (SQLException ex) {
			System.out.println("Error during DB2 connection " + ex);
			ex.printStackTrace();
		} catch (NamingException ex) {
			System.out.println("Error during DB2 connection " + ex);
			ex.printStackTrace();
		}
	}

	public void SQLConnection3() {
		try {

			InitialContext cxt = new InitialContext();
			DataSource ds = (DataSource) cxt
					.lookup("java:/comp/env/jdbc/postgres");
			connection3 = ds.getConnection();
			System.out.println("      DB3 open");
			statement3 = connection3.createStatement();
			result3 = statement3.executeQuery("SELECT VERSION()");
			if (result3.next()) {
				// System.out.println(result3.getString(1)); // DEBUG -
				// Connection
			}
		} catch (SQLException ex) {
			System.out.println("Error during DB3 connection " + ex);
			ex.printStackTrace();
		} catch (NamingException ex) {
			System.out.println("Error during DB3 connection " + ex);
			ex.printStackTrace();
		}
	}

	public void SQLConnection4() {
		try {

			InitialContext cxt = new InitialContext();
			DataSource ds = (DataSource) cxt
					.lookup("java:/comp/env/jdbc/postgres");
			connection4 = ds.getConnection();
			System.out.println("      	DB4 open");
			statement4 = connection4.createStatement();
			result4 = statement4.executeQuery("SELECT VERSION()");
			if (result4.next()) {
				// System.out.println(result3.getString(1)); // DEBUG -
				// Connection
			}
		} catch (SQLException ex) {
			System.out.println("Error during DB4 connection " + ex);
			ex.printStackTrace();
		} catch (NamingException ex) {
			System.out.println("Error during DB4 connection " + ex);
			ex.printStackTrace();
		}
	}

	public void SQLConnectionClose() {
		try {
			connection.close();
			System.out.println("DB close");
		} catch (SQLException ex) {
			System.out.println("Error during DB connection " + ex);
			ex.printStackTrace();
		}
	}

	public void SQLConnectionClose2() {
		try {
			connection2.close();
			System.out.println("   DB2 close");
		} catch (SQLException ex) {
			System.out.println("Error during DB2 connection " + ex);
			ex.printStackTrace();
		}
	}

	public void SQLConnectionClose3() {
		try {
			connection3.close();
			System.out.println("      DB3 close");
		} catch (SQLException ex) {
			System.out.println("Error during DB3 connection " + ex);
			ex.printStackTrace();
		}
	}

	public void SQLConnectionClose4() {
		try {
			connection4.close();
			System.out.println("      	DB4 close");
		} catch (SQLException ex) {
			System.out.println("Error during DB4 connection " + ex);
			ex.printStackTrace();
		}
	}

	/** USER METHODEN **/

	/**
	 * createUsername: zum automatischen erstellen des Usernamens aus dem echten
	 * Vor- und Nachnamen
	 * 
	 **/
	public String createUsername(String vname, String nname) {

		// Marker für aktuellen Usernamen
		boolean isCurrentUsername = true;

		vname = vname.replaceAll("(Ä|Ö|Ü|ä|ö|ü|ß|-)*", ""); // Sonderzeichen
															// filtern
		nname = nname.replaceAll("(Ä|Ö|Ü|ä|ö|ü|ß|-)*", "");
		String username = "";
		String usernameDB = "";

		vname = vname.substring(0, 2);
		nname = nname.substring(0, 3);
		while (isCurrentUsername) {
			for (int i = 1; i < 1000; i++) {
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
		String password = new BigInteger(32, random).toString(32);
		System.out.println("Passwort: " + password);

		return password;
	}

	/**
	 * Fügt Datein eines NEUEN Users (Schüler oder Lehrer) in die Datenbank ein
	 * 
	 * @param klasse
	 * @param nname
	 * @param vname
	 * @param geb
	 * @param konf
	 */
	public void addUser(String klasse, String nname, String vname, String geb,
			String konf, String role) {
		SQLConnection();
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

			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		FacesContext.getCurrentInstance().addMessage("studentaddsuccess_name",
				messageName);

		FacesContext.getCurrentInstance().addMessage("teacheraddsuccess_name",
				messageName);

		SQLConnectionClose();

	}

	public void updateUser(int id, String klasse, String nname, String vname,
			String konf) {

		SQLConnection();
		try {
			statement = connection.createStatement();
			statement.executeUpdate("UPDATE users SET users_nachname = '"
					+ nname + "', users_vorname = '" + vname
					+ "', users_konfession = '" + konf + "', users_klasse = '"
					+ klasse + "' WHERE users_id = " + id + ";");
			System.out.println(">>> UPDATE USER"); // DEBUG

			messageName = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Der User " + vname + " " + nname
							+ " wurde erfolgreich ge-updated!", null);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		FacesContext.getCurrentInstance().addMessage("userupdatesuccess_name",
				messageName);
	}

	public String showUserFullName() {
		System.out.println("schowUSerFullName");
		SQLConnection2();
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext externalContext = fc.getExternalContext();
		ResultSet rs = null;
		PreparedStatement pst = null;
		String stm = "Select users_nachname,users_vorname from users where users_username='"
				+ externalContext.getUserPrincipal().getName() + "'";
		String vorName = "";
		String nachName = "";
		try {
			pst = connection2.prepareStatement(stm);
			pst.execute();
			rs = pst.getResultSet();

			while (rs.next()) {

				nachName = rs.getString(1);
				vorName = rs.getString(2);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		SQLConnectionClose2();
		return vorName + " " + nachName;
	}

	/**
	 * Gibt Vor- und Nachname der übergebenen User-ID zurück
	 * 
	 * @param id
	 * @return
	 */
	public String showUserFullName(int id) {
		System.out.println("schowUserFullName(id)");
		SQLConnection2();
		ResultSet rs = null;
		PreparedStatement pst = null;
		String stm = "Select users_nachname,users_vorname from users where users_id="
				+ id + ";";
		String vorName = "";
		String nachName = "";
		try {
			pst = connection2.prepareStatement(stm);
			pst.execute();
			rs = pst.getResultSet();

			while (rs.next()) {

				nachName = rs.getString(1);
				vorName = rs.getString(2);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		SQLConnectionClose2();
		System.out.println(vorName + " " + nachName);
		return vorName + " " + nachName;
	}

	/**
	 * Gibt ID des angemeldeten User zurück
	 * 
	 * @param
	 */
	public int getUserId() {
		System.out.println("getUserId");
		int id = 0;
		SQLConnection3();
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext externalContext = fc.getExternalContext();
		ResultSet rs = null;
		PreparedStatement pst = null;
		String stm = "Select users_id from users where users_username='"
				+ externalContext.getUserPrincipal().getName() + "'";
		try {
			pst = connection3.prepareStatement(stm);
			pst.execute();
			rs = pst.getResultSet();

			while (rs.next()) {

				id = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		SQLConnectionClose3();

		return id;
	}

	/**
	 * Gibt Klasse der übergebenen User id zurück
	 * 
	 * @param id
	 * @return
	 */
	public String showUserClass(int id) {
		System.out.println("schowUserClass(id)");
		SQLConnection2();
		ResultSet rs = null;
		PreparedStatement pst = null;
		String stm = "Select users_klasse from users where users_id=" + id
				+ ";";
		String klasse = "";
		try {
			pst = connection2.prepareStatement(stm);
			pst.execute();
			rs = pst.getResultSet();

			while (rs.next()) {
				klasse = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		SQLConnectionClose2();
		System.out.println(klasse);
		return klasse;
	}

	/**
	 * Gibt konfession der übergebenen User id zurück
	 * 
	 * @param id
	 * @return
	 */
	public String getUserKonfession(int id) {
		SQLConnection2();
		ResultSet rs = null;
		PreparedStatement pst = null;
		String stm = "Select users_konfession from users where users_id=" + id
				+ ";";
		String konfession = "";
		try {
			pst = connection2.prepareStatement(stm);
			pst.execute();
			rs = pst.getResultSet();

			while (rs.next()) {
				konfession = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		SQLConnectionClose2();
		System.out.println(konfession);
		return konfession;
	}

	/**
	 * Ließt Vorname eines Users, der gegebenen id, aus DB aus
	 * 
	 * @param id
	 * @return vorname
	 */
	public String getUserFirstname(int id) {
		System.out.println("getUserFirstname(id)");
		SQLConnection2();
		ResultSet rs = null;
		PreparedStatement pst = null;
		String stm = "Select users_vorname from users where users_id=" + id
				+ ";";
		String vorname = "";
		try {
			pst = connection2.prepareStatement(stm);
			pst.execute();
			rs = pst.getResultSet();

			while (rs.next()) {
				vorname = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		SQLConnectionClose2();
		System.out.println(vorname);
		return vorname;
	}

	/**
	 * Ließt Nachname eines Users, der gegebenen id, aus DB aus
	 * 
	 * @param id
	 * @return nachname
	 */
	public String getUserLastname(int id) {
		System.out.println("getUserFirstname(id)");
		SQLConnection2();
		ResultSet rs = null;
		PreparedStatement pst = null;
		String stm = "Select users_nachname from users where users_id=" + id
				+ ";";
		String nachname = "";
		try {
			pst = connection2.prepareStatement(stm);
			pst.execute();
			rs = pst.getResultSet();

			while (rs.next()) {
				nachname = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		SQLConnectionClose2();
		System.out.println(nachname);
		return nachname;
	}

	/**
	 * Gibt den Usernamen des momentan eingeloggten Users zurück
	 * 
	 * @return username
	 */
	public String getUserUsername() {
		System.out.println("getUserUsermame");
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext externalContext = fc.getExternalContext();
		String username = "" + externalContext.getUserPrincipal().getName();

		return username;
	}

	/**
	 * Ließt Passwort eines Users, der gegebenen id, aus DB aus
	 * 
	 * @param id
	 * @return passwort
	 */
	public String getUserPassword(int id) {
		System.out.println("getUserPassword");

		SQLConnection2();
		ResultSet rs = null;
		PreparedStatement pst = null;
		String stm = "Select users_passwort from users where users_id=" + id
				+ ";";
		String password = "";

		try {
			pst = connection2.prepareStatement(stm);
			pst.execute();
			rs = pst.getResultSet();

			while (rs.next()) {
				password = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		SQLConnectionClose2();
		System.out.println(password);
		return password;
	}

	/**
	 * Gibt einem User, mit gegebenem usernamen, die gegebenen Rechte
	 * 
	 * @param username
	 * @param role
	 */
	public void addRole(String username, String role) {

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
	 * Userdaten über id selektieren
	 * 
	 * @param id
	 * @return
	 */
	public List<User> listEditorUser(int id) {
		ArrayList<User> users = new ArrayList<User>();
		SQLConnection();
		System.out.println("listEditorUser");
		try {
			statement = connection.createStatement();
			result = statement
					.executeQuery("SELECT * FROM users WHERE users_id = " + id
							+ ";");
			// ub.emptyUsers();
			while (result.next()) {
				System.out.println(result.getInt("users_id")
						+ result.getString("users_vorname")
						+ result.getString("users_nachname")
						+ result.getString("users_geburtstag")
						+ result.getString("users_konfession")
						+ result.getString("users_klasse")
						+ result.getString("users_username")
						+ result.getString("users_passwort")
						+ result.getString("users_rolle"));

				users.add(new UserBean.User(result.getInt("users_id"), result
						.getString("users_vorname"), result
						.getString("users_nachname"), result
						.getString("users_geburtstag"), result
						.getString("users_konfession"), result
						.getString("users_klasse"), result
						.getString("users_username"), result
						.getString("users_passwort"), result
						.getString("users_rolle"), false));

				System.out
						.println("Name: " + result.getString("users_vorname"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		SQLConnectionClose();
		return users;

	}

	public List<User> listUsers() {

		ArrayList<User> users = new ArrayList<User>();
		SQLConnection();
		try {
			statement = connection.createStatement();
			result = statement
					.executeQuery("SELECT * FROM users WHERE users_rolle = 'schueler'ORDER BY users_klasse,users_nachname;");
			// ub.emptyUsers();
			while (result.next()) {
				System.out.println(result.getInt("users_id")
						+ result.getString("users_vorname")
						+ result.getString("users_nachname")
						+ result.getString("users_geburtstag")
						+ result.getString("users_konfession")
						+ result.getString("users_klasse")
						+ result.getString("users_username")
						+ result.getString("users_passwort")
						+ result.getString("users_rolle"));
				users.add(new UserBean.User(result.getInt("users_id"), result
						.getString("users_vorname"), result
						.getString("users_nachname"), result
						.getString("users_geburtstag"), result
						.getString("users_konfession"), result
						.getString("users_klasse"), result
						.getString("users_username"), result
						.getString("users_passwort"), result
						.getString("users_rolle"), false));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		SQLConnectionClose();
		return users;
	}

	// TODO ??? Funktion nötig ???
	public void addToUsers(String klasse, String nachname, String vorname,
			String geburtstag, String konfession, String rolle) {
		try {
			SQLConnection();
			statement = connection.createStatement();
			statement
					.executeUpdate("INSERT INTO users (users_klasse, users_nachname,"
							+ "users_vorname, users_geburtstag, users_konfession, users_rolle)"
							+ "VALUES ("
							+ klasse
							+ ", '"
							+ nachname
							+ "', '"
							+ vorname
							+ "', '"
							+ geburtstag
							+ "', '"
							+ konfession + "', '" + rolle + "');");
			System.out.println(">>> INSERT USER"); // DEBUG
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		SQLConnectionClose();

	}

	/**
	 * TEACHER METHODEN
	 * 
	 * @return
	 */

	// Lehreransicht
	public List<Teacher> listTeachers() {
		List<Teacher> teacherList = new ArrayList<Teacher>();
		SQLConnection();
		try {
			statement = connection.createStatement();
			result = statement
					.executeQuery("SELECT * FROM users WHERE users_rolle = 'lehrer'ORDER BY users_nachname,users_vorname;");
			// TeacherBean.emptyTeachers();
			while (result.next()) {
				System.out.println(result.getInt("users_id")
						+ result.getString("users_vorname")
						+ result.getString("users_nachname")
						+ result.getString("users_geburtstag")
						+ result.getString("users_konfession")
						+ result.getString("users_klasse")
						+ result.getString("users_username")
						+ result.getString("users_passwort")
						+ result.getString("users_rolle"));
				teacherList.add(new TeacherBean.Teacher(result
						.getInt("users_id"), result.getString("users_vorname"),
						result.getString("users_nachname"), result
								.getString("users_geburtstag"), result
								.getString("users_konfession"), result
								.getString("users_klasse"), result
								.getString("users_username"), result
								.getString("users_passwort"), result
								.getString("users_rolle")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		SQLConnectionClose();
		return teacherList;
	}

	// TODO ??? Funktion nötig ???
	public void addToTeachers(String klasse, String nachname, String vorname,
			String geburtstag, String konfession, String rolle) {
		try {
			SQLConnection();
			statement = connection.createStatement();
			statement
					.executeUpdate("INSERT INTO users (users_klasse, users_nachname,"
							+ "users_vorname, users_geburtstag, users_konfession, users_rolle)"
							+ "VALUES ("
							+ klasse
							+ ", '"
							+ nachname
							+ "', '"
							+ vorname
							+ "', '"
							+ geburtstag
							+ "', '"
							+ konfession + "', '" + rolle + "');");
			System.out.println(">>> INSERT TEACHER"); // DEBUG
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		SQLConnectionClose();

	}

	// Klassenansichte

	public ArrayList<User> listClassesTeacher(int id) {
		ArrayList<User> users = new ArrayList<User>();
		SQLConnection();
		try {
			statement = connection.createStatement();
			result = statement.executeQuery("SELECT * " + "FROM users "
					+ "WHERE users_klasse = ( "
					+ "SELECT users_klasse FROM users " + "WHERE users_id = "
					+ id + ") " + "AND users_rolle != 'lehrer' "
					+ "ORDER BY users_nachname,users_vorname;");
			// UserBean.emptyUsers();
			while (result.next()) {
				System.out.println(result.getInt("users_id")
						+ result.getString("users_vorname")
						+ result.getString("users_nachname")
						+ result.getString("users_geburtstag")
						+ result.getString("users_konfession")
						+ result.getString("users_username")
						+ result.getString("users_passwort")
						+ result.getString("users_klasse")
						+ result.getString("users_rolle"));
				users.add(new UserBean.User(result.getInt("users_id"), result
						.getString("users_vorname"), result
						.getString("users_nachname"), result
						.getString("users_geburtstag"), result
						.getString("users_konfession"), result
						.getString("users_klasse"), result
						.getString("users_username"), result
						.getString("users_passwort"), result
						.getString("users_rolle"), false));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		SQLConnectionClose();
		return users;
	}

	public ArrayList<User> listClassesTeacherSchedule(int id) {
		ArrayList<User> users = new ArrayList<User>();
		SQLConnection();
		try {
			statement = connection.createStatement();
			result = statement
					.executeQuery("SELECT * "
							+ "FROM users "
							+ "WHERE users_klasse = ( "
							+ "SELECT users_klasse FROM users "
							+ "WHERE users_id = "
							+ id
							+ ") "
							+ "AND users_rolle != 'lehrer' ORDER BY users_nachname,users_vorname;");
			// UserBean.emptyUsers();
			while (result.next()) {
				System.out.println(result.getInt("users_id")
						+ result.getString("users_vorname")
						+ result.getString("users_nachname")
						+ result.getString("users_geburtstag")
						+ result.getString("users_konfession")
						+ result.getString("users_username")
						+ result.getString("users_passwort")
						+ result.getString("users_klasse")
						+ result.getString("users_rolle"));
				users.add(new UserBean.User(result.getInt("users_id"), result
						.getString("users_vorname"), result
						.getString("users_nachname"), result
						.getString("users_geburtstag"), result
						.getString("users_konfession"), result
						.getString("users_klasse"), result
						.getString("users_username"), result
						.getString("users_passwort"), result
						.getString("users_rolle"), false, courseName(
						result.getInt("users_id"), 1), courseName(
						result.getInt("users_id"), 2), courseName(
						result.getInt("users_id"), 3), courseName(
						result.getInt("users_id"), 4), courseName(
						result.getInt("users_id"), 5), courseName(
						result.getInt("users_id"), 6), courseName(
						result.getInt("users_id"), 7), courseName(
						result.getInt("users_id"), 8), courseName(
						result.getInt("users_id"), 9), courseName(
						result.getInt("users_id"), 10)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		SQLConnectionClose();
		return users;
	}

	public ArrayList<User> listClassesSchedule() {
		ArrayList<User> users = new ArrayList<User>();
		SQLConnection();
		try {
			statement = connection.createStatement();
			result = statement
					.executeQuery("SELECT * FROM users WHERE users_rolle='schueler' ORDER BY users_klasse,users_nachname,users_vorname");
			// UserBean.emptyUsers();
			while (result.next()) {
				System.out.println(result.getInt("users_id")
						+ result.getString("users_vorname")
						+ result.getString("users_nachname")
						+ result.getString("users_geburtstag")
						+ result.getString("users_konfession")
						+ result.getString("users_klasse")
						+ result.getString("users_username")
						+ result.getString("users_passwort")
						+ result.getString("users_rolle"));
				users.add(new UserBean.User(result.getInt("users_id"), result
						.getString("users_vorname"), result
						.getString("users_nachname"), result
						.getString("users_geburtstag"), result
						.getString("users_konfession"), result
						.getString("users_klasse"), result
						.getString("users_username"), result
						.getString("users_passwort"), result
						.getString("users_rolle"), false, courseName(
						result.getInt("users_id"), 1), courseName(
						result.getInt("users_id"), 2), courseName(
						result.getInt("users_id"), 3), courseName(
						result.getInt("users_id"), 4), courseName(
						result.getInt("users_id"), 5), courseName(
						result.getInt("users_id"), 6), courseName(
						result.getInt("users_id"), 7), courseName(
						result.getInt("users_id"), 8), courseName(
						result.getInt("users_id"), 9), courseName(
						result.getInt("users_id"), 10)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		SQLConnectionClose();
		return users;
	}

	public ArrayList<User> listClasses() {
		ArrayList<User> users = new ArrayList<User>();
		SQLConnection();
		try {
			statement = connection.createStatement();
			result = statement.executeQuery("SELECT * FROM users");
			// UserBean.emptyUsers();
			while (result.next()) {
				System.out.println(result.getInt("users_id")
						+ result.getString("users_vorname")
						+ result.getString("users_nachname")
						+ result.getString("users_geburtstag")
						+ result.getString("users_konfession")
						+ result.getString("users_klasse")
						+ result.getString("users_username")
						+ result.getString("users_passwort")
						+ result.getString("users_rolle"));
				users.add(new UserBean.User(result.getInt("users_id"), result
						.getString("users_vorname"), result
						.getString("users_nachname"), result
						.getString("users_geburtstag"), result
						.getString("users_konfession"), result
						.getString("users_klasse"), result
						.getString("users_username"), result
						.getString("users_passwort"), result
						.getString("users_rolle"), false));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		SQLConnectionClose();
		return users;
	}

	/**
	 * COURSE METHODEN
	 * 
	 * @return
	 */

	public Course getCourse(int id) {
		Course course = null;
		SQLConnection();
		try {
			statement = connection.createStatement();
			System.out.println("SELECT * FROM course WHERE course_id=" + id + ";");
			result = statement.executeQuery("SELECT * FROM course WHERE course_id=" + id + ";");
			// CourseBean.emptyCourses();
			while (result.next()) {
				System.out.println(result.getInt("course_id")
						+ result.getString("course_name")
						+ result.getInt("course_kurslehrer")
						+ result.getString("course_faecherverbund")
						+ result.getInt("course_termin")
						+ result.getString("course_beschreibung"));
				course = new CourseBean.Course(result.getInt("course_id"),
						result.getString("course_name"), result
								.getInt("course_kurslehrer"), result
								.getString("course_faecherverbund"),
						translateDate(result.getInt("course_termin")), result
								.getString("course_beschreibung"), result
								.getInt("course_schuljahr"), result
								.getInt("course_tertial"), result
								.getInt("course_teilnehmerzahl"), result
								.getBoolean("course_pflichtkurs"), result
								.getBoolean("course_sport"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		SQLConnectionClose();
		return course;
	}	
	
	
	
	
	public ArrayList<Course> listCourses() {
		ArrayList<Course> courses = new ArrayList<Course>();
		SQLConnection();
		try {
			statement = connection.createStatement();
			result = statement
					.executeQuery("SELECT * FROM course ORDER BY course_termin");
			// CourseBean.emptyCourses();
			while (result.next()) {
				System.out.println(result.getInt("course_id")
						+ result.getString("course_name")
						+ result.getInt("course_kurslehrer")
						+ result.getString("course_faecherverbund")
						+ result.getInt("course_termin")
						+ result.getString("course_beschreibung"));
				courses.add(new CourseBean.Course(result.getInt("course_id"),
						result.getString("course_name"), result
								.getInt("course_kurslehrer"), result
								.getString("course_faecherverbund"),
						translateDate(result.getInt("course_termin")), result
								.getString("course_beschreibung"), result
								.getInt("course_schuljahr"), result
								.getInt("course_tertial"), result
								.getInt("course_teilnehmerzahl"), result
								.getBoolean("course_pflichtkurs"), result
								.getBoolean("course_sport")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		SQLConnectionClose();
		return courses;
	}

	public void addCourse(String name, String faecherverbund, int kurslehrer,
			int termin, String beschreibung, int teilnehmerzahl, boolean sport,
			ArrayList<String> konfessionen) {
		int id = 0;
		try {
			SQLConnection();
			statement = connection.createStatement();
			statement
					.executeUpdate("INSERT INTO course (course_name, course_faecherverbund, course_kurslehrer, course_termin, course_beschreibung, course_teilnehmerzahl, course_sport)"
							+ "VALUES ('"
							+ name
							+ "', '"
							+ faecherverbund
							+ "', "
							+ kurslehrer
							+ ", "
							+ termin
							+ ", '"
							+ beschreibung
							+ "',"
							+ teilnehmerzahl
							+ ","
							+ sport + ");");
			if (!konfessionen.isEmpty()) {
				result = statement
						.executeQuery("SELECT course_id FROM course WHERE course_name='"
								+ name
								+ "' AND course_faecherverbund='"
								+ faecherverbund
								+ "' AND course_kurslehrer="
								+ kurslehrer
								+ " AND course_termin="
								+ termin
								+ ";");
				if (result.next())
					id = result.getInt("course_id");
				for (String k : konfessionen)
					statement
							.executeUpdate("INSERT INTO course_religion (course_religion_id, course_religion_konfession) VALUES ("
									+ id + ",'" + k + "');");
			}

			System.out.println(">>> INSERT COURSE"); // DEBUG
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		SQLConnectionClose();
	}

	public ArrayList<SelectItem> populateAllConfessions() {
		ArrayList<SelectItem> allConfessions = new ArrayList<SelectItem>();
		SQLConnection();

		try {
			statement = connection.createStatement();
			result = statement
					.executeQuery("SELECT DISTINCT users_konfession FROM users WHERE users_konfession != 'admin' AND users_konfession != 'lehrer' AND users_konfession != 'null';");
			// CourseBean.clearAllConfessions();
			while (result.next()) {
				// System.out.println(result.getString("course_religion_konfession"));
				allConfessions.add(new SelectItem(result
						.getString("users_konfession")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		SQLConnectionClose();
		return allConfessions;
	}

	public ArrayList<Course> listCoursesStudent(int id) {
		ArrayList<Course> courses = new ArrayList<Course>();
		SQLConnection();
		try {
			statement = connection.createStatement();
			result = statement
					.executeQuery("SELECT gradelist.gradelist_kursid, "
							+ "course.course_id, gradelist.gradelist_userid, course.course_beschreibung, course.course_termin, course.course_faecherverbund, course.course_kurslehrer, course.course_name, course.course_schuljahr, course.course_tertial, course.course_teilnehmerzahl, course.course_pflichtkurs, course.course_sport "
							+ "FROM  public.course, public.gradelist "
							+ "WHERE course.course_id = gradelist.gradelist_kursid "
							+ "AND gradelist.gradelist_userid=" + id
							+ "AND course.course_schuljahr="
							+ kuwasysControllerBean.year + ""
							+ "AND course.course_tertial="
							+ kuwasysControllerBean.tertial
							+ "AND gradelist.gradelist_jahr="
							+ kuwasysControllerBean.year + ""
							+ "AND gradelist.gradelist_tertial="
							+ kuwasysControllerBean.tertial
							+ "AND gradelist.gradelist_note=0"
							+ "ORDER BY course.course_termin" + ";");
			// CourseBean.emptyCourses();
			while (result.next()) {
				System.out.println(result.getInt("course_id")
						+ result.getString("course_name")
						+ result.getInt("course_kurslehrer")
						+ result.getString("course_faecherverbund")
						+ result.getInt("course_termin")
						+ result.getString("course_beschreibung"));
				courses.add(new CourseBean.Course(result.getInt("course_id"),
						result.getString("course_name"), result
								.getInt("course_kurslehrer"), result
								.getString("course_faecherverbund"),
						translateDate(result.getInt("course_termin")), result
								.getString("course_beschreibung"), result
								.getInt("course_schuljahr"), result
								.getInt("course_tertial"), result
								.getInt("course_teilnehmerzahl"), result
								.getBoolean("course_pflichtkurs"), result
								.getBoolean("course_sport")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		SQLConnectionClose();
		return courses;
	}

	public double getCourseGrade(int userId, int courseId) {
		System.out.println("getCourseGrade");
		double grade = 0;
		SQLConnection2();
		try {
			statement2 = connection2.createStatement();
			result2 = statement2.executeQuery("SELECT gradelist_note "
					+ "FROM gradelist " + "WHERE gradelist_userid=" + userId
					+ "AND gradelist_kursid=" + courseId + ";");
			while (result2.next()) {
				grade = java.lang.Math.round(result2
						.getDouble("gradelist_note") * 100) / 100.;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		SQLConnectionClose2();
		return grade;
	}

	public List<Course> listCoursesTeacher(int id) {
		ArrayList<Course> courses = new ArrayList<Course>();
		SQLConnection();
		try {
			statement = connection.createStatement();
			result = statement
					.executeQuery("SELECT * FROM course WHERE course_kurslehrer="
							+ id + " ORDER BY course_termin;");
			// CourseBean.emptyCourses();
			while (result.next()) {
				System.out.println(result.getInt("course_id")
						+ result.getString("course_name")
						+ result.getInt("course_kurslehrer")
						+ result.getString("course_faecherverbund")
						+ result.getInt("course_termin")
						+ result.getString("course_beschreibung"));
				courses.add(new CourseBean.Course(result.getInt("course_id"),
						result.getString("course_name"), result
								.getInt("course_kurslehrer"), result
								.getString("course_faecherverbund"),
						translateDate(result.getInt("course_termin")), result
								.getString("course_beschreibung"), result
								.getInt("course_schuljahr"), result
								.getInt("course_tertial"), result
								.getInt("course_teilnehmerzahl"), result
								.getBoolean("course_pflichtkurs"), result
								.getBoolean("course_sport")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		SQLConnectionClose();
		return courses;
	}

	public List<Course> listCoursesAttendable(int id) {
		ArrayList<Course> courses = new ArrayList<Course>();
		SQLConnection();
		try {
			statement = connection.createStatement();
			result = statement
					.executeQuery("  SELECT * FROM course LEFT OUTER JOIN "
							+ "(SELECT gradelist_kursid, gradelist_note FROM gradelist "
							+ "WHERE  (gradelist_userid="
							+ id
							+ "))as a "
							+ "ON course.course_id=a.gradelist_kursid "
							+ "WHERE a.gradelist_kursid IS NULL "
							+ "AND course.course_schuljahr="
							+ kuwasysControllerBean.year
							+ ""
							+ "AND course.course_tertial="
							+ kuwasysControllerBean.tertial
							+ " ORDER BY course.course_termin,course.course_faecherverbund;");
			// CourseBean.emptyCourses();
			while (result.next()) {
				System.out.println(result.getInt("course_id")
						+ result.getString("course_name")
						+ result.getInt("course_kurslehrer")
						+ result.getString("course_faecherverbund")
						+ result.getInt("course_termin")
						+ result.getString("course_beschreibung"));
				courses.add(new CourseBean.Course(result.getInt("course_id"),
						result.getString("course_name"), result
								.getInt("course_kurslehrer"), result
								.getString("course_faecherverbund"),
						translateDate(result.getInt("course_termin")), result
								.getString("course_beschreibung"), result
								.getInt("course_schuljahr"), result
								.getInt("course_tertial"), result
								.getInt("course_teilnehmerzahl"), result
								.getBoolean("course_pflichtkurs"), result
								.getBoolean("course_sport")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		SQLConnectionClose();
		return courses;
	}

	public List<User> listCourseParticipants(int id) {
		List<User> participants = new ArrayList<User>();
		SQLConnection4();
		try {
			statement4 = connection4.createStatement();
			result4 = statement4.executeQuery("SELECT * FROM gradelist "
					+ "JOIN users "
					+ "ON gradelist.gradelist_userid=users.users_id "
					+ "WHERE gradelist.gradelist_note=0 "
					+ " AND gradelist.gradelist_jahr="
					+ kuwasysControllerBean.year
					+ " AND gradelist.gradelist_tertial="
					+ kuwasysControllerBean.tertial + "AND gradelist_kursid="
					+ id
					+ " ORDER BY users_klasse,users_nachname,users_vorname;");
			// UserBean.emptyUsers();
			while (result4.next()) {
				System.out.println(result4.getInt("users_id")
						+ result4.getString("users_vorname")
						+ result4.getString("users_nachname")
						+ result4.getString("users_geburtstag")
						+ result4.getString("users_konfession")
						+ result4.getString("users_klasse")
						+ result4.getString("users_username")
						+ result4.getString("users_passwort")
						+ result4.getString("users_rolle"));
				participants.add(new UserBean.User(result4.getInt("users_id"),
						result4.getString("users_vorname"), result4
								.getString("users_nachname"), result4
								.getString("users_geburtstag"), result4
								.getString("users_konfession"), result4
								.getString("users_klasse"), result4
								.getString("users_username"), result4
								.getString("users_passwort"), result4
								.getString("users_rolle"), false));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		SQLConnectionClose4();

		return participants;
	}

	public int countCourseParticipants(int id) {
		SQLConnection3();
		int participants = 0;
		try {
			statement3 = connection3.createStatement();
			result3 = statement3
					.executeQuery(" SELECT COUNT(gradelist.gradelist_kursid) "
							+ "FROM course JOIN gradelist "
							+ "ON gradelist.gradelist_kursid=course.course_id "
							+ "WHERE course.course_id=" + id
							+ "AND course.course_schuljahr="
							+ kuwasysControllerBean.year + ""
							+ "AND course.course_tertial="
							+ kuwasysControllerBean.tertial
							+ " AND gradelist.gradelist_jahr="
							+ kuwasysControllerBean.year
							+ " AND gradelist.gradelist_tertial="
							+ kuwasysControllerBean.tertial + ";");
			while (result3.next()) {
				participants = result3.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		SQLConnectionClose3();
		return participants;
	}

	public void activateCourse(int id) {
		SQLConnection();
		try {
			statement = connection.createStatement();
			statement.executeUpdate("UPDATE course SET course_schuljahr="
					+ kuwasysControllerBean.year + ", course_tertial="
					+ kuwasysControllerBean.tertial + " WHERE course_id=" + id
					+ ";");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		SQLConnectionClose();
	}

	public void deActivateCourse(int id) {
		SQLConnection();
		try {
			statement = connection.createStatement();
			if (kuwasysControllerBean.tertial > 1)
				statement.executeUpdate("UPDATE course SET course_schuljahr="
						+ kuwasysControllerBean.year + ", course_tertial="
						+ (kuwasysControllerBean.tertial - 1)
						+ " WHERE course_id=" + id + ";");
			else
				statement.executeUpdate("UPDATE course SET course_schuljahr="
						+ (kuwasysControllerBean.year - 1)
						+ ", course_tertial=3 WHERE course_id=" + id + ";");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		SQLConnectionClose();
	}

	public void toggleEssentialCourse(int id) {
		SQLConnection();
		try {
			statement = connection.createStatement();
			statement
					.executeUpdate("UPDATE course SET course_pflichtkurs=NOT course_pflichtkurs WHERE course_id="
							+ id + ";");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		SQLConnectionClose();
	}

	public int countPositions() {
		int free = 0;
		SQLConnection();
		try {
			statement = connection.createStatement();
			result = statement
					.executeQuery(" SELECT SUM(course.course_teilnehmerzahl) "
							+ "FROM course " + "WHERE course.course_schuljahr="
							+ kuwasysControllerBean.year
							+ "AND course.course_tertial="
							+ kuwasysControllerBean.tertial + ";");
			while (result.next()) {
				free = result.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		SQLConnectionClose();
		return free;
	}

	public int countEssentialcourse() {
		int count = 0;
		SQLConnection();
		try {
			statement = connection.createStatement();
			result = statement.executeQuery(" SELECT COUNT(course.course_id) "
					+ "FROM course " + "WHERE course.course_pflichtkurs "
					+ "AND course.course_schuljahr="
					+ kuwasysControllerBean.year + "AND course.course_tertial="
					+ kuwasysControllerBean.tertial + ";");
			while (result.next()) {
				count = result.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		SQLConnectionClose();
		return count;
	}

	public boolean bundleChosen(int id, String bundle) {
		System.out.println("bundleChosen-" + id + bundle);
		boolean chosen = false;
		SQLConnection2();
		try {
			statement2 = connection2.createStatement();
			result2 = statement2.executeQuery(" SELECT COUNT(*) "
					+ "FROM course JOIN gradelist "
					+ "ON course.course_id=gradelist.gradelist_kursid "
					+ "WHERE gradelist_userid=" + id
					+ " AND course.course_faecherverbund='" + bundle + "' "
					+ "AND course.course_schuljahr="
					+ kuwasysControllerBean.year + "AND course.course_tertial="
					+ kuwasysControllerBean.tertial
					+ "AND gradelist.gradelist_jahr="
					+ kuwasysControllerBean.year
					+ "AND gradelist.gradelist_tertial="
					+ kuwasysControllerBean.tertial
					+ "AND gradelist.gradelist_note=0" + ";");
			while (result2.next()) {
				chosen = (result2.getInt(1) > 0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		SQLConnectionClose2();

		return chosen;
	}

	public boolean sportChosen(int id) {
		System.out.println("sportChosen mit " + id);
		boolean chosen = false;
		SQLConnection2();
		try {
			statement2 = connection2.createStatement();
			result2 = statement2.executeQuery(" SELECT course_sport "
					+ "FROM course JOIN gradelist "
					+ "ON course.course_id=gradelist.gradelist_kursid "
					+ "WHERE gradelist_userid=" + id
					+ " AND course.course_sport"
					+ " AND course.course_schuljahr="
					+ kuwasysControllerBean.year
					+ " AND course.course_tertial="
					+ kuwasysControllerBean.tertial
					+ " AND gradelist.gradelist_jahr="
					+ kuwasysControllerBean.year
					+ " AND gradelist.gradelist_tertial="
					+ kuwasysControllerBean.tertial
					+ "AND gradelist.gradelist_note=0" + ";");
			while (result2.next()) {
				chosen = (result2.getBoolean(1));
				System.out.println("sportChosen-result" + chosen);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		SQLConnectionClose2();

		return chosen;
	}

	public boolean reliChosen(int id) {
		System.out.println("reliChosen " + id);
		boolean chosen = false;
		SQLConnection2();
		try {
			statement2 = connection2.createStatement();
			result2 = statement2
					.executeQuery(" SELECT * FROM gradelist JOIN "
							+ "(SELECT * FROM (SELECT * FROM users) as a JOIN "
							+ "(SELECT * FROM course JOIN course_religion "
							+ "ON course.course_id=course_religion.course_religion_id) AS b "
							+ "ON a.users_konfession=b.course_religion_konfession "
							+ "WHERE a.users_id=" + id + ") as c "
							+ "ON gradelist.gradelist_kursid=c.course_id "
							+ "WHERE gradelist.gradelist_userid=" + id
							+ "AND c.course_schuljahr="
							+ kuwasysControllerBean.year
							+ "AND c.course_tertial="
							+ kuwasysControllerBean.tertial
							+ " AND gradelist.gradelist_jahr="
							+ kuwasysControllerBean.year
							+ " AND gradelist.gradelist_tertial="
							+ kuwasysControllerBean.tertial
							+ "AND gradelist.gradelist_note=0" + ";");
			while (result2.next()) {
				chosen = true;
				System.out.println("sportChosen-result" + chosen);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		SQLConnectionClose2();

		return chosen;
	}

	public boolean isDateConflicting(int id, int date) {
		System.out.println("date conflicting " + date);
		boolean conflicting = false;
		SQLConnection2();
		try {
			statement2 = connection2.createStatement();
			result2 = statement2
					.executeQuery(" SELECT COUNT(*) FROM gradelist "
							+ "JOIN course ON gradelist.gradelist_kursid=course.course_id "
							+ "WHERE gradelist.gradelist_userid=" + id
							+ "AND course.course_termin=" + date
							+ "AND course.course_schuljahr="
							+ kuwasysControllerBean.year
							+ "AND course.course_tertial="
							+ kuwasysControllerBean.tertial
							+ " AND gradelist.gradelist_jahr="
							+ kuwasysControllerBean.year
							+ " AND gradelist.gradelist_tertial="
							+ kuwasysControllerBean.tertial
							+ "AND gradelist.gradelist_note=0" + ";");
			while (result2.next()) {
				if (result2.getInt(1) > 1)
					conflicting = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		SQLConnectionClose2();

		return conflicting;
	}

	public String courseName(int id, int date) {
		System.out.println("coursename " + date);
		String name = "-";
		SQLConnection2();
		try {
			statement2 = connection2.createStatement();
			result2 = statement2
					.executeQuery(" SELECT course_name FROM gradelist "
							+ "JOIN course ON gradelist.gradelist_kursid=course.course_id "
							+ "WHERE gradelist.gradelist_userid=" + id
							+ "AND course.course_termin=" + date
							+ "AND course.course_schuljahr="
							+ kuwasysControllerBean.year
							+ "AND course.course_tertial="
							+ kuwasysControllerBean.tertial
							+ " AND gradelist.gradelist_jahr="
							+ kuwasysControllerBean.year
							+ " AND gradelist.gradelist_tertial="
							+ kuwasysControllerBean.tertial
							+ "AND gradelist.gradelist_note=0" + ";");
			while (result2.next()) {
				name = result2.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		SQLConnectionClose2();

		return name;
	}

	/**
	 * SYSTEM METHODEN
	 * 
	 * @return
	 */

	public int systemState() {
		System.out.println("systemState");
		try {
			SQLConnection();
			statement = connection.createStatement();
			result = statement.executeQuery("SELECT * FROM system;");
			if (result.next()) {
				kuwasysControllerBean.setPhase(result.getInt("system_phase"));
				kuwasysControllerBean.setTertial(result
						.getInt("system_tertial"));
				kuwasysControllerBean.setYear(result.getInt("system_jahr"));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		SQLConnectionClose();
		return 0;
	}

	public void commitPhase(int p) {
		System.out.println("commitPhase");
		try {
			SQLConnection();
			statement = connection.createStatement();
			statement
					.executeUpdate("UPDATE system SET system_phase=" + p + ";");
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		SQLConnectionClose();
	}

	public void commitTertial(int tertial, int year) {
		System.out.println("commitTertial");
		try {
			SQLConnection();
			statement = connection.createStatement();
			statement.executeUpdate("UPDATE system SET system_jahr=" + year
					+ ", system_tertial=" + tertial + ";");
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		SQLConnectionClose();
	}

	/**
	 * GRADELIST METHODEN
	 */

	public List<Grades> listGradelist(int userid) {
		List<Grades> gradeList = new ArrayList<Grades>();
		SQLConnection();
		System.out.println("Zeuch mir die Nodalischd!");
		try {
			statement = connection.createStatement();
			result = statement
					.executeQuery("SELECT * FROM gradelist JOIN course ON gradelist.gradelist_kursid=course.course_id WHERE gradelist_userid="
							+ userid
							+ " AND gradelist_note <> 0 ORDER BY gradelist_jahr,gradelist_tertial;");
			// GradelistBean.emptyGradelist();
			while (result.next()) {
				System.out.println(result.getInt("gradelist_id")
						+ result.getDouble("gradelist_note")
						+ result.getString("gradelist_bemerkung")
						+ result.getInt("gradelist_userid")
						+ result.getInt("gradelist_kursid"));
				gradeList.add(new GradelistBean.Grades(result
						.getInt("gradelist_id"), result
						.getDouble("gradelist_note"), result
						.getString("gradelist_bemerkung"), result
						.getInt("gradelist_userid"), result
						.getString("course_name"), result
						.getInt("gradelist_jahr"), result
						.getInt("gradelist_tertial"), result
						.getString("course_faecherverbund")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		SQLConnectionClose();
		return gradeList;
	}

	public void addToGradelist(double note, String bemerkung, int userid,
			int kursid) {
		try {
			SQLConnection();
			statement = connection.createStatement();
			statement
					.executeUpdate("INSERT INTO gradelist (gradelist_note, gradelist_bemerkung, gradelist_userid, gradelist_kursid, gradelist_jahr, gradelist_tertial)"
							+ "VALUES ("
							+ note
							+ ", '"
							+ bemerkung
							+ "', "
							+ userid
							+ ", "
							+ kursid
							+ ", "
							+ kuwasysControllerBean.year
							+ ", "
							+ kuwasysControllerBean.tertial + ");");
			System.out.println(">>> INSERT GRADELIST"); // DEBUG
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		SQLConnectionClose();
	}

	public void removeFromGradelist(int userId, int kursId) {
		try {
			SQLConnection();
			statement = connection.createStatement();
			statement
					.executeUpdate("DELETE FROM gradelist WHERE gradelist_userid="
							+ userId + " AND gradelist_kursid=" + kursId + ";");
			System.out.println(">>> REMOVE GRADELIST"); // DEBUG
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		SQLConnectionClose();

	}

	/**
	 * Ändert für den gegebenen User das angegebene Passwort
	 * 
	 * @param id
	 *            des zu ändernden Users
	 * @param passwort
	 *            des angegebenen Users
	 */
	public void changePassword(int id, String passwort) {
		SQLConnection();
		try {
			statement = connection.createStatement();
			statement.executeUpdate("UPDATE users SET users_passwort = '"
					+ passwort + "' WHERE users_id = " + id + ";");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		SQLConnectionClose();

	}

	// TODO in Course ???
	/**
	 * Übersetzt int der Datumswerte aus DB in Datumskürzel
	 * 
	 * @param termin
	 * @return
	 */
	public String translateDate(int termin) {
		switch (termin) {
		case 1:
			return "MO VO";
		case 2:
			return "MO NA";
		case 3:
			return "DI VO";
		case 4:
			return "DI NA";
		case 5:
			return "MI VO";
		case 6:
			return "MI NA";
		case 7:
			return "DO VO";
		case 8:
			return "DO NA";
		case 9:
			return "FR VO";
		case 10:
			return "FR NA";
		default:
			return "FEHLER!";
		}
	}

	public void updateCourse(int id, String name, String faecherverbund,
			int teilnehmerzahl, String beschreibung) {

		SQLConnection();
		try {
			statement = connection.createStatement();
			statement.executeUpdate("UPDATE course SET course_name = '" + name
					+ "', course_faecherverbund = '" + faecherverbund
					+ "', course_teilnehmerzahl = " + teilnehmerzahl
					+ ", course_beschreibung = '" + beschreibung
					+ "' WHERE course_id = " + id + ";");
			System.out.println(">>> UPDATE USER"); // DEBUG

			messageName = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Der Kurs " + name + " " + faecherverbund
							+ " wurde erfolgreich ge-updated!", null);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		FacesContext.getCurrentInstance().addMessage(
				"courseupdatesuccess_name", messageName);
	}

}
