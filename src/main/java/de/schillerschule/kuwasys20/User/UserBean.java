package de.schillerschule.kuwasys20.User;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import de.schillerschule.kuwasys20.Database.UserDatabaseHandler;

/**
 * Klasse für User-Handling im System
 * 
 * @author cy
 * 
 */
@ManagedBean(name = "userBean")
@RequestScoped
public class UserBean {

	// Property-Strings
	private String name;
	private String lastname;
	private String geb;

	public UserBean() {
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
	 * Geburtstdatum
	 * 
	 * @return geb
	 */
	public String getGeb() {
		return geb;
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
	 * Geburtsdatum
	 * 
	 * @param geb
	 */
	public void setGeb(String geb) {
		this.geb = geb;
	}

	/**
	 * Neuen User anlegen
	 * 
	 * @return Facelet "useraddsuccess"
	 */
	public String send() {

		// TODO SQL Aufruf um User in DB einzufügen

		return "useraddsuccess";
	}

	/**
	 * Username des aktuellen Users zurückgeben
	 * 
	 * @return username
	 */
	public String showUsername() {
		UserDatabaseHandler.SQLConnection();
		String username = UserDatabaseHandler.showUserFullName();
		System.out.println("snap!");
		return username;
	}

}
