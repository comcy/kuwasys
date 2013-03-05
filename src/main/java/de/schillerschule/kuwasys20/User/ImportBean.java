package de.schillerschule.kuwasys20.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.StringTokenizer;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.RollbackException;
import javax.validation.ConstraintViolationException;

import org.apache.myfaces.custom.fileupload.UploadedFile;
import org.hibernate.tool.hbm2ddl.ImportScriptException;

import de.schillerschule.kuwasys20.Database.DatabaseHandler;

/**
 * Managed Bean für den Upload von CSV-Dokumenten zum Import von Schülern.
 */
@ManagedBean(name= "importBean")
@RequestScoped
public class ImportBean implements Serializable {

	private static Logger logger = Logger.getLogger(ImportBean.class
			.getCanonicalName());

	private static final long serialVersionUID = 1L;

	private String fileName;
	private UploadedFile file;
	
	static Connection connection;
	static Statement statement;
	static ResultSet result;

	/**
	 * Führt einen Import mit der hochgeladenen Datei durch.
	 */
	public void doImport() {
		// TODO DEBUG
		logger.info("File type: " + file.getContentType());
		logger.info("File name: " + file.getName());
		logger.info("File size: " + file.getSize() + " bytes");

		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Import erfolgreich", null);

		// Stringvariablen für ausgelesene Daten
		String line = "";

		String klasse = ""; // (1)
		String nname = ""; // (2)
		String vname = ""; // (3)
		String geb = ""; // (4)
		String empty = ""; // (5)
		String konf = ""; // (6)
		
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));

			StringTokenizer st = null;
			int lineNumber = 0, tokenNumber = 0;

			// CSV zeilenweise lesen
			// Aufbau der CSV Datei:
			// (1) 'Klasse', (2) 'Nachname', (3) 'Vorname', (4) 'Geburtsdatum',
			// (5) 'EMPTY' (6) 'Religionsunterricht'
			System.out.println("--------------------------");
			line = reader.readLine(); // erste Zeile überspringen
			while ((line = reader.readLine()) != null) {
				String encode = new String(line.getBytes(),"LATIN1");
				
				lineNumber++;
				st = new StringTokenizer(encode, ","); // Trennzeichen
				while (st.hasMoreTokens()) {
					tokenNumber++;
					switch (tokenNumber) {
					case 1:
						klasse = st.nextToken().replaceAll(
								"'", "");
					case 2:
						nname = st.nextToken().replaceAll(
								"'", "");
					case 3:
						vname = st.nextToken().replaceAll(
								"'", "");
					case 4:
						geb = st.nextToken().replaceAll(
								"'", "");
					case 5:
						empty = st.nextToken().replaceAll(
								"'", "");
					case 6:
						konf = st.nextToken().replaceAll(
								"'", "");
					}
				}

				// TODO DEBUG
				System.out.println("#" + lineNumber);
				System.out.println("Klasse: " + klasse);
				System.out.println("Nachname: " + nname);
				System.out.println("Vorname: " + vname);
				System.out.println("Geburtstag: " + geb);
				System.out.println("(unused): " + empty);
				System.out.println("Konfession: " + konf);

				// Datenbank Insert
				DBInsert(klasse, nname, vname, geb, konf, lineNumber);

				System.out.println("--------------------------");

				tokenNumber = 0;

			}
			reader.close();
			
		} catch (RollbackException e) {
			logger.info("Import fehlgeschlagen\n" + e.getMessage());

			message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Import fehlgeschlagen", null);
		} catch (ConstraintViolationException e) {
			logger.info("Import fehlgeschlagen\n" + e.getMessage());

			message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Import fehlgeschlagen", null);
		} catch (ImportScriptException e) {
			logger.info("Import fehlgeschlagen\n" + e.getMessage());

			message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Import fehlgeschlagen", null);
		} catch (IOException e) {
			logger.info("Upload fehlgeschlagen\n" + e.getMessage());

			message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Upload fehlgeschlagen", null);
		}
		catch (Exception e) {
			System.out.println("Error while reading CSV file " + e);
		}

		FacesContext.getCurrentInstance().addMessage("csvimport", message);

	}

	// Get-Methoden
	public String getFileName() {
		return fileName;
	}

	// Get-Methoden
	public UploadedFile getFile() {
		return file;
	}

	// Set-Methoden
	public void setFile(UploadedFile file) {
		this.file = file;
	}
	
	/**
	 * Schüler in die Datenbank importieren
	 * 
	 * @return Facelet "csvimport"
	 */
	public String home(){
		DatabaseHandler.SQLConnection(); // Datenbankverbindung herstellen
    	return "csvimport";
    }
	
	/**
	 * createUsername: zum automatischen erstellen des Usernamens aus dem echten
	 * Vor- und Nachnamen
	 * 
	 **/
	public static String createUsername(String vname, String nname, int count) {

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
		String passwort = new BigInteger(130, random).toString(32);
		System.out.println("Passwort: " + passwort);

		return passwort;
	}

	/**
	 * 
	 * Fügt Daten des aktuellen Users in die Datenbank hinzu
	 */
	public static void DBInsert(String klasse, String nname, String vname,
			String geb, String konf, int count) {

		// Marker für aktuelle Userdaten
		boolean isCurrentUser = true;

		// benötigte Strings
		String nnameDB = "";
		String vnameDB = "";
		String gebDB = "";

		// Username und Passwort generieren
		String username = createUsername(vname, nname, count);
		String passwort = createPassword();

		try {
			// Check ob users_username bereits vorhanden:
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
				System.out.println(">>> User bereits in DB vorhanden!");
			} else {
				isCurrentUser = false;
			}
			if (!isCurrentUser) { // Wenn der aktuelle User noch nicht in der Datenbank vorhanden ist - INSERT
				statement = connection.createStatement();
				statement
						.executeUpdate("INSERT INTO users(users_nachname, users_vorname, " // User anlegen
								+ "users_geburtstag, users_konfession, "
								+ "users_klasse, users_username, users_passwort, "
								+ "users_rolle) VALUES ("
								+ "'"
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
								+ passwort
								+ "', "
								+ "'schueler');"
								+ "INSERT INTO roles(users_username, roles_rolle)" // Rolle des aktuellen Users festlegen
								+ "VALUES ("
								+ "'" + username + "', "
								+ "'schueler');");
				System.out.println(">>> INSERT"); // DEBUG

			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

	}

}
 
