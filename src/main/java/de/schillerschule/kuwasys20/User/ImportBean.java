package de.schillerschule.kuwasys20.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
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

import de.schillerschule.kuwasys20.Database.DatabaseHandlerBean;
import de.schillerschule.kuwasys20.Database.UserDatabaseHandler;

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
	
	/**
	 * Führt einen Import mit der hochgeladenen Datei durch.
	 */
	public void doImport() {
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
			
			// Datenbankverbindung herstellen
			UserDatabaseHandler.SQLConnection();
			
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

				System.out.println("#" + lineNumber);
				System.out.println("Klasse: " + klasse);
				System.out.println("Nachname: " + nname);
				System.out.println("Vorname: " + vname);
				System.out.println("Geburtstag: " + geb);
				System.out.println("(unused): " + empty);
				System.out.println("Konfession: " + konf);

				// User in DB einfügen
				UserDatabaseHandler.addUser(klasse, nname, vname, geb, konf, lineNumber);
				
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

}
 
