package de.schillerschule.kuwasys20.Course;

import java.io.Serializable;
import java.util.*;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import de.schillerschule.kuwasys20.Controller.kuwasysControllerBean;
import de.schillerschule.kuwasys20.Database.DatabaseHandler;

@ManagedBean(name = "courseBean")
@RequestScoped

public class CourseBean {

	private static List<Course> courses = new ArrayList<Course>();
	private static ArrayList<SelectItem> alleKonfessionen = new ArrayList<SelectItem>();

	private int id;
	private String name;
	private int kurslehrer = 0;
	private String kurslehrerName;
	private String faecherverbund;
	private int termin;
	private String beschreibung;
	private int teilnehmerzahl;
	private boolean sport;
	private ArrayList<String> konfessionen = new ArrayList<String>();

	public CourseBean() {
		DatabaseHandler.populateAllConfessions();
	}

	public String addCourse() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (context.getExternalContext().isUserInRole("lehrer")) {
			kurslehrer = DatabaseHandler.getUserId();
		}
		DatabaseHandler.addCourse(name, faecherverbund, kurslehrer, termin,
				beschreibung, teilnehmerzahl,  sport, konfessionen);
		return kuwasysControllerBean.goCourses();
	}

	public static void addToCourses(Course c) {
		courses.add(c);
	}

	public static void emptyCourses() {
		courses.clear();
	}

	public void confessionSelectionChanged(ValueChangeEvent evt) {

		Object[] selectedValues = (Object[]) evt.getNewValue();

		if (selectedValues.length == 0)
			konfessionen.clear();
		else {
			for (int i = 0; i < selectedValues.length; i++) {
				konfessionen.add((String) selectedValues[i]);
			}
		}
		for (String S : konfessionen)
			System.out.println(S);
	}

	public static void addToAllConfessions(String reli) {
		alleKonfessionen.add(new SelectItem(reli));
	}

	public static void clearAllConfessions() {
		alleKonfessionen.clear();
	}
	
	public boolean bundleChosen(String bundle){
		if (bundle.equals("sport"))
			return DatabaseHandler.sportChosen(DatabaseHandler.getUserId());
		else if (bundle.equals("reli"))
			return DatabaseHandler.reliChosen(DatabaseHandler.getUserId());
		else
			return DatabaseHandler.bundleChosen(DatabaseHandler.getUserId(), bundle);
	}
	
	
	public int isDateConflicting(){
		for (int i=1; i<=10; i++)
			if (DatabaseHandler.isDateConflicting(DatabaseHandler.getUserId(), i))
				return i;
		return 0;
	}
	
	

	/**
	 * GETTER & SETTER
	 * 
	 */

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		CourseBean.courses = courses;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getKurslehrer() {
		return kurslehrer;
	}

	public void setKurslehrer(int kurslehrer) {
		this.kurslehrer = kurslehrer;
	}

	public String getKurslehrerName() {
		return kurslehrerName;
	}

	public void setKurslehrerName(String kurslehrerName) {
		this.kurslehrerName = kurslehrerName;
	}

	public String getFaecherverbund() {
		return faecherverbund;
	}

	public void setFaecherverbund(String faecherverbund) {
		this.faecherverbund = faecherverbund;
	}

	public int getTermin() {
		return termin;
	}

	public void setTermin(int termin) {
		this.termin = termin;
	}

	public String getBeschreibung() {
		return beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	public int getTeilnehmerzahl() {
		return teilnehmerzahl;
	}

	public void setTeilnehmerzahl(int teilnehmerzahl) {
		this.teilnehmerzahl = teilnehmerzahl;
	}

	public ArrayList<SelectItem> getAlleKonfessionen() {
		return alleKonfessionen;
	}

	public boolean isSport() {
		return sport;
	}

	public void setSport(boolean sport) {
		this.sport = sport;
	}

	public static void setAlleKonfessionen(
			ArrayList<SelectItem> alleKonfessionen) {
		CourseBean.alleKonfessionen = alleKonfessionen;
	}

	public static class Course implements Serializable {
		/**
		 * serial id for serialisation versioning
		 */
		private static final long serialVersionUID = 1L;
		private int _id;
		private String _name;
		private int _kurslehrer;
		private String _kurslehrerName;
		private String _faecherverbund;
		private int _termin;
		private String _beschreibung;
		private int _jahr;
		private int _tertial;
		private int _teilnehmerzahl;
		private int _teilnehmerzahlAktuell;
		private boolean _pflichtkurs;
		private boolean _sport;

		public Course(int id, String name, int kurslehrer,
				String faecherverbund, int termin, String beschreibung, int jahr, int tertial, int teilnehmerzahl, boolean pflichtkurs, boolean sport) {
			System.out.println("CourseConstructor");
			_id = id;
			_name = name;
			_kurslehrer = kurslehrer;
			_kurslehrerName = DatabaseHandler.showUserFullName(kurslehrer);
			_faecherverbund = faecherverbund;
			_termin = termin;
			_beschreibung = beschreibung;
			set_jahr(jahr);
			set_tertial(tertial);
			set_teilnehmerzahl(teilnehmerzahl);
			set_teilnehmerzahlAktuell(DatabaseHandler.countCourseParticipants(id));
			set_pflichtkurs(pflichtkurs);
			set_sport(sport);
		}

		public String attendCourse() {
			DatabaseHandler.addToGradelist(0, "", DatabaseHandler.getUserId(),_id);
			return kuwasysControllerBean.goCourses();
		}
		
		public String unAttendCourse(){
			DatabaseHandler.removeFromGradelist(DatabaseHandler.getUserId(),_id);
			return kuwasysControllerBean.goCourses();
		}
		
		public String activateCourse(){
			DatabaseHandler.activateCourse(_id);
			return kuwasysControllerBean.goCourses();
		}
		
		public String deActivateCourse(){
			DatabaseHandler.deActivateCourse(_id);
			return kuwasysControllerBean.goCourses();
		}
		
		public String toggleEssentialCourse(){
			System.out.println("SET "+_id+" ESSENTIAL");
			DatabaseHandler.toggleEssentialCourse(_id);
			return kuwasysControllerBean.goCourses();
		}
		
		public boolean isCurrentTertial(){
			if (_jahr==kuwasysControllerBean.year&&_tertial==kuwasysControllerBean.tertial)
				return true;
			else
				return false;
		}
		
		public double courseGrade(){
			return DatabaseHandler.getCourseGrade(DatabaseHandler.getUserId(),_id);
		}
		
		public boolean freePositions(){
			if (_teilnehmerzahl>_teilnehmerzahlAktuell)
				return true;
			else 
				return false;
		}

		public int get_id() {
			return _id;
		}

		public void set_id(int _id) {
			this._id = _id;
		}

		public String get_name() {
			return _name;
		}

		public void set_name(String _name) {
			this._name = _name;
		}

		public int get_kurslehrer() {
			return _kurslehrer;
		}

		public void set_kurslehrer(int _kurslehrer) {
			this._kurslehrer = _kurslehrer;
		}

		public String get_faecherverbund() {
			return _faecherverbund;
		}

		public void set_faecherverbund(String _faecherverbund) {
			this._faecherverbund = _faecherverbund;
		}

		public int get_termin() {
			return _termin;
		}

		public void set_termin(int _termin) {
			this._termin = _termin;
		}

		public String get_beschreibung() {
			return _beschreibung;
		}

		public void set_beschreibung(String _beschreibung) {
			this._beschreibung = _beschreibung;
		}

		public String get_kurslehrerName() {
			return _kurslehrerName;
		}

		public void set_kurslehrerName(String _kurslehrerName) {
			this._kurslehrerName = _kurslehrerName;
		}

		public int get_jahr() {
			return _jahr;
		}

		public void set_jahr(int _jahr) {
			this._jahr = _jahr;
		}

		public int get_tertial() {
			return _tertial;
		}

		public void set_tertial(int _tertial) {
			this._tertial = _tertial;
		}

		public int get_teilnehmerzahl() {
			return _teilnehmerzahl;
		}

		public void set_teilnehmerzahl(int _teilnehmerzahl) {
			this._teilnehmerzahl = _teilnehmerzahl;
		}

		public int get_teilnehmerzahlAktuell() {
			return _teilnehmerzahlAktuell;
		}

		public void set_teilnehmerzahlAktuell(int _teilnehmerzahlAktuell) {
			this._teilnehmerzahlAktuell = _teilnehmerzahlAktuell;
		}

		public boolean is_pflichtkurs() {
			return _pflichtkurs;
		}

		public void set_pflichtkurs(boolean _pflichtkurs) {
			this._pflichtkurs = _pflichtkurs;
		}

		public boolean is_sport() {
			return _sport;
		}

		public void set_sport(boolean _sport) {
			this._sport = _sport;
		}
	}
}
