
package de.schillerschule.kuwasys20.Controller;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import de.schillerschule.kuwasys20.Database.DatabaseHandler;

/**
 * Bean für die gesamte Navigationsstruktur des Systems
 */
@ManagedBean(name = "kuwasys")
@RequestScoped
public class kuwasysControllerBean {

	public static int phase;
	public static int tertial;	
	public static int year;
	public int phaseChanged;
	@SuppressWarnings("unused")
	private int ss = DatabaseHandler.systemState();
	
	public int getPhase() {
		return kuwasysControllerBean.phase;
	}

	public static void setPhase(int phase) {
		kuwasysControllerBean.phase = phase;
	}
	
	public int getPhaseChanged() {
		phaseChanged=phase;
		return phaseChanged;
	}


	public void setPhaseChanged(int phase) {
		phaseChanged = phase;
	}


	public int getTertial() {
		return kuwasysControllerBean.tertial;
	}

	public static void setTertial(int tertial) {
		kuwasysControllerBean.tertial = tertial;
	}
	
	public int getYear() {
		return kuwasysControllerBean.year;
	}

	public static void setYear(int year) {
		kuwasysControllerBean.year = year;
	}
	
	public kuwasysControllerBean() {
	}

	public String systemPhase(){
		switch (phase) {
		case 1: return "Kursplanung";
		case 2: return "Kurswahl";
		case 3: return "Unterricht";
		default: return "ÜNGÜLTIG!";
		}
	}
	
	public String phaseCommit(){
		DatabaseHandler.commitPhase(phaseChanged);
		return goSystem();
	}
	
	public String nextTertial(){
		tertial++;
		if (tertial > 3){
			year++;
			tertial=1;
		}
		DatabaseHandler.commitTertial(tertial, year);
		DatabaseHandler.commitPhase(1);
		return goSystem();
	}
	
	public String userRole() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (context.getExternalContext().isUserInRole("admin"))
			return "Admin";
		if (context.getExternalContext().isUserInRole("lehrer"))
			return "Lehrer";
		if (context.getExternalContext().isUserInRole("schueler"))
			return "Schüler";
		else
			return null;
	}

	public int countPositions(){
		return DatabaseHandler.countPositions();
	}
	public int countEssential(){
		return DatabaseHandler.countEssentialcourse();
	}
	
	
	public String goLogout() throws IOException {
		ExternalContext ec = FacesContext.getCurrentInstance()
				.getExternalContext();
		ec.invalidateSession();
		ec.redirect("kuwasys.jsf");

		return null;
	}

	// Navigationsaufrufe 
	/**
	 * 
	 * @return
	 */
	public String goHome() {
		return "kuwasys";
	}
	
	/**
	 * Neuen User hinzufügen
	 * 
	 * @return Facelet "useradd"
	 */
	public String goAddUser() {
		return "studentadd";
	}
	
	/**
	 * Neuen Lehrer hinzufügen
	 * 
	 * @return Facelet "teacheradd"
	 */
	public String goAddTeacher() {
		return "teacheradd";
	}
	
	public static String goUsers() {
		DatabaseHandler.listUsers();
		return "users";
	}
	
	public static String goTeachers() {
		DatabaseHandler.listTeachers();
		return "teachers";
	}

	/**
	 * Schüler in die Datenbank importieren
	 * 
	 * @return Facelet "csvimport"
	 */
	public String goImportCSV(){
    	return "csvimport";
    }
	
	/**
	 * Kursübersicht anzeigen
	 * 
	 * @return Facelet "courses"
	 */
	public static String goCourses(){
		FacesContext context = FacesContext.getCurrentInstance();
		if (context.getExternalContext().isUserInRole("admin"))
			DatabaseHandler.listCourses();
		if (context.getExternalContext().isUserInRole("lehrer"))
			DatabaseHandler.listCoursesTeacher(DatabaseHandler.getUserId());
		if (context.getExternalContext().isUserInRole("schueler"))
			DatabaseHandler.listCoursesStudent(DatabaseHandler.getUserId());
		return "courses";
    }	
	
	/**
	 * Klassen eines Lehrers anzeigen (eigene) 
	 * 
	 * @return Facelet "courses"
	 */
	public static String goClasses(){
		FacesContext context = FacesContext.getCurrentInstance();
		if (context.getExternalContext().isUserInRole("admin"))
			DatabaseHandler.listClasses();
		if (context.getExternalContext().isUserInRole("lehrer"))
			DatabaseHandler.listClassesTeacher(DatabaseHandler.getUserId());
		return "classes";
    }
		
	/**
	 * Kurs hinzufügen
	 * 
	 * @return Facelet "courseadd"
	 */
	public String goAddCourse(){
		return "courseadd";
    }		
	
	/**
	 * Systemstatus anzeigen
	 * 
	 * @return Facelet "system"
	 */
	public String goSystem(){
		return "system";
    }	
	
	/**
	 * Systemphase setzen
	 * 
	 * @return Facelet "phaseset"
	 */
	public String goPhaseSet(){
		return "phaseset";
    }		

	/**
	 * Systemtertial inkrementieren
	 * 
	 * @return Facelet "tertialnext"
	 */
	public String goTertialNext(){
		return "tertialnext";
    }	
	
	/**
	 * GRADELIST FACES
	 */
	
	/**
	 * Notenlisten anzeigen
	 * @return Facelet "gradelist" 
	 */
	public static String goGradelist(){
		DatabaseHandler.listGradelist();
		return "gradelist";
	}
	
	/**
	 * In Kurs einschreiben
	 * @return Facelet "gradelistadd" 
	 */
	public String goAddGradelist(){
		DatabaseHandler.listCoursesAttendable(DatabaseHandler.getUserId());
		return "gradelistadd";
	}
	
	public static String goUsereditor(){
		return "usereditor";
	}
	
	public static String goChangePassword(){
		return "passwordedit";
	}
	
}


