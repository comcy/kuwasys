package de.schillerschule.kuwasys20;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 * Klasse zum Anlegen von neuen Usern im System: - Schueler - Lehrer - ernennen
 * eines neuen Admins
 * 
 * @author cy
 * 
 */
@ManagedBean(name = "addUserBean")
@RequestScoped
public class AddUserBean {

	// Property-Strings
	private String name;
	private String lastname;
	private String geb;

	public AddUserBean() {
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
	 * Methode für die Navigation
	 * 
	 * @return Facelet "useradd"
	 */
	public String home() {
		return "useradd";
	}

	/**
	 * Neuen User anlegen
	 * 
	 * @return Facelet "useraddsuccess"
	 */
	public String send() {

		// SQL Statement um User in DB einzufügen
		
		return "useraddsuccess";
	}

}
