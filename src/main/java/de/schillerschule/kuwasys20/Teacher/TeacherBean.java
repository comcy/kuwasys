package de.schillerschule.kuwasys20.Teacher;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import de.schillerschule.kuwasys20.Controller.kuwasysControllerBean;
import de.schillerschule.kuwasys20.Database.DatabaseHandler;

/**
 * ManagedBean-Klasse für Teacher-Handling im System
 * 
 * @author cy
 * 
 */
@ManagedBean(name = "teacherBean")
@RequestScoped
public class TeacherBean {

	private static Logger logger = Logger.getLogger(TeacherBean.class
			.getCanonicalName());
	
	private DatabaseHandler dbh = new DatabaseHandler();
	//private List<Teacher> teachers = new ArrayList<Teacher>();

	private int id;
	private String vorname;
	private String nachname;
	private String geburtstag;
	private String gebDay;
	private String gebMonth;
	private String gebYear;
	private String klasse;
	private String rolle;
	private String konfession; 
	
	public TeacherBean() {
	}
	
	public List<Teacher> getTeachers() {
		System.out.println("getTeachers");
		return dbh.listTeachers();
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public void  setRolle(String rolle) {
		rolle = "lehrer";
		this.rolle = rolle;
	}
	
	public String getRolle() {
		return rolle;
	}
	
	public void setGeburtstag(String geburtstag) {
		this.geburtstag = geburtstag;
	}
	
	public String getGeburtstag() {
		return geburtstag;
	}
	
	public String getKlasse() {
		return klasse;
	}

	public void setKlasse(String klasse) {
		this.klasse = klasse;
	}
	
	public String getKonfession() {
		return konfession;
	}

	public void setKonfession(String konfession) {
		konfession = "N.A.";
		this.konfession = konfession;
	}
	
	// Helfer für Geburtstag Splitting
	public String getGebDay() {
		return gebDay;
	}

	public String getGebMonth() {
		return gebMonth;
	}

	public String getGebYear() {
		return gebYear;
	}

	public void setGebDay(String gebDay) {
		this.gebDay = gebDay;
	}

	public void setGebMonth(String gebMonth) {
		this.gebMonth = gebMonth;
	}

	public void setGebYear(String gebYear) {
		this.gebYear = gebYear;
	}
	////
	
	/**
	 * Neuen User anlegen
	 * 
	 * @return Facelet "useraddsuccess"
	 */
	public String sendTeacher() {
		
		String rolle = "lehrer";
		
		// DEBUG
		System.out.println("Klasse: " + klasse);
		System.out.println("Nachname: " + vorname);
		System.out.println("Vorname: " + nachname);
		System.out.println("Geburtstag: " + geburtstag);
		
		geburtstag = gebYear + gebMonth + gebDay; // Geburtstag formatieren
		
		//DatabaseHandler.SQLConnection();
		dbh.addUser(klasse, nachname, vorname, geburtstag, konfession, rolle);
		//DatabaseHandler.SQLConnectionClose();
		
		logger.info("Lehrer: " + vorname + " " + nachname + " angelegt");
		return "teacheraddsuccess";
	}
	
	/*public static void emptyTeachers() {
		teachers.clear();	
	}*/
	
	public String addToTeachers(){
		dbh.addToTeachers(klasse, nachname, vorname, geburtstag, konfession, "lehrer");
		return kuwasysControllerBean.goTeachers();
	}
	
	/*public void addToTeachers(Teacher teacher){
		teachers.add(teacher);
	}*/
		
	/**
	 * Teacher-Klasse (Lehrer)
	 * @author cy
	 *
	 */
	
	public static class Teacher implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
		private int _id;
		private String _nachname;
		private String _vorname;
		private String _geburtstag;
		private String _klasse;
		private String _username;
		private String _passwort;
		private String _rolle;
		private String _konfession;
		
		public Teacher (int id, String vorname, String nachname, String geburtstag, String konfession, String klasse, String username, String passwort, String rolle) {
		   
			_id = id;
			_nachname = nachname;
			_vorname = vorname;
			_geburtstag = geburtstag;
			_klasse = klasse;
			_username = username;
			_passwort = passwort;
			_rolle = rolle;
			_konfession = konfession;
			
		}
		
		public int get_id() {
			return _id;
		}
		
		public void set_id(int _id) {
			this._id = _id;
		}
		
		public String get_nachname() {
			return _nachname;
		}
		
		public void set_nachame(String _nachname) {
			this._nachname = _nachname;
		}
		
		public String get_vorname() {
			return _vorname;
		}
		
		public void set_vorname(String _vorname) {
			this._vorname = _vorname;
		}
		
		public String get_geburtstag() {
			return _geburtstag;
		}
		
		public void set_geburtstag(String _geburtstag) {
			this._geburtstag = _geburtstag;
		}
		
		public String get_klasse() {
			return _klasse;
		}
		
		public void set_klasse(String _klasse) {
			this._klasse = _klasse;
		}
		
		public String get_username() {
			return _username;
		}
		
		public void set_username(String _username) {
			this._username = _username;
		}
		
		public String get_passwort() {
			return _passwort;
		}
		
		public void set_passwort(String _passwort) {
			this._passwort = _passwort;
		}
		
		public String get_rolle() {
			return _rolle;
		}
		
		public void set_rolle(String _rolle) {
			_rolle = "lehrer";
			this._rolle = _rolle;
		}
		
		public String get_konfession() {
			return _konfession;
		}

		public void set_konfession(String _konfession) {
			_konfession = "lehrer";
			this._konfession = _konfession;
		}
	}
}
