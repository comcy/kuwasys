package de.schillerschule.kuwasys20.Teacher;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import de.schillerschule.kuwasys20.Database.DatabaseHandler;

/**
 * Klasse für Teacher-Handling im System
 * 
 * @author cy
 * 
 */
@ManagedBean(name = "teacherBean")
@RequestScoped
public class TeacherBean implements Serializable {

	private static Logger logger = Logger.getLogger(TeacherBean.class
			.getCanonicalName());

	private static final long serialVersionUID = 1L;

	// Property-Strings
	private String name;
	private String lastname;
	private String geb;
	private String gebDay;
	private String gebMonth;
	private String gebYear;
	private String klasse;
	
	// Default Strings: "schueler"
	public static String role = "lehrer";
	public static String konfession = "N.A."; 

	public TeacherBean() {
	}

	// Get-Methoden
	/**
	 * Vorname
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Nachname
	 * 
	 * @return lastname
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * Klasse
	 * 
	 * @return klasse
	 */
	public String getKlasse() {
		return klasse;
	}

	/**
	 * Geburtstdatum Tag
	 * 
	 * @return gebDay
	 */
	public String getGebDay() {
		return gebDay;
	}

	/**
	 * Geburtstdatum Monat
	 * 
	 * @return gebMonth
	 */
	public String getGebMonth() {
		return gebMonth;
	}

	/**
	 * Geburtstdatum Tag Jahr
	 * 
	 * @return gebYear
	 */
	public String getGebYear() {
		return gebYear;
	}

	// Set-Methoden
	/**
	 * Vorname
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Nachname
	 * 
	 * @param lastname
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * Klasse
	 * 
	 * @param klasse
	 */
	public void setKlasse(String klasse) {
		this.klasse = klasse;
	}

	/**
	 * Geburtsdatum
	 * 
	 * @param gebDay
	 *            , gebMonth, gebYear
	 */
	public void setGebDay(String gebDay) {
		this.gebDay = gebDay;
	}

	public void setGebMonth(String gebMonth) {
		this.gebMonth = gebMonth;
	}

	public void setGebYear(String gebYear) {
		this.gebYear = gebYear;
	}

	public void setGeb(String geb) {
		this.geb = geb;
	}
	
	/**
	 * Neuen User anlegen
	 * 
	 * @return Facelet "useraddsuccess"
	 */
	public String sendTeacher() {

		// DEBUG
		System.out.println("Klasse: " + klasse);
		System.out.println("Nachname: " + name);
		System.out.println("Vorname: " + lastname);
		System.out.println("Geburtstag: " + geb);
		
		geb = gebYear + gebMonth + gebDay; // Geburtstag formatieren
		
		DatabaseHandler.SQLConnection();
		DatabaseHandler.addUser(klasse, lastname, name, geb, konfession, role);
		DatabaseHandler.SQLConnectionClose();
		
		logger.info("Lehrer: " + name + " " + lastname + " angelegt");
		return "teacheraddsuccess";
	}

}
