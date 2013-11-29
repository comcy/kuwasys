package de.schillerschule.kuwasys20.Course;

import java.io.Serializable;
import java.util.*;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import de.schillerschule.kuwasys20.Controller.kuwasysControllerBean;
import de.schillerschule.kuwasys20.Database.DatabaseHandler;
import de.schillerschule.kuwasys20.User.UserBean;
import de.schillerschule.kuwasys20.User.UserBean.User;

@ManagedBean(name = "courseBean")
@RequestScoped
public class CourseBean implements Serializable {

	FacesContext context = FacesContext.getCurrentInstance();

	private static Logger logger = Logger.getLogger(UserBean.class
			.getCanonicalName());

	private static final long serialVersionUID = 1L;

	private List<Course> courses = new ArrayList<Course>();
	private List<Course> coursesActive = new ArrayList<Course>();
	private List<Course> coursesInactive = new ArrayList<Course>();

	private ArrayList<SelectItem> alleKonfessionen = new ArrayList<SelectItem>();

	DatabaseHandler dbh = new DatabaseHandler();

	private int id;
	private String name;
	private int kurslehrer = 0;
	private String kurslehrerName;
	private String faecherverbund;
	private int termin;
	private String beschreibung;
	private int teilnehmerzahl;
	private boolean sport;
	private String raum;

	private ArrayList<String> konfessionen = new ArrayList<String>();

	public CourseBean() {
		alleKonfessionen = dbh.populateAllConfessions();
	}

	public String addCourse() {
		if (context.getExternalContext().isUserInRole("lehrer")) {
			kurslehrer = dbh.getUserId();
		}
		dbh.addCourse(name, faecherverbund, raum, kurslehrer, termin,
				beschreibung, teilnehmerzahl, sport, konfessionen);
		// return kuwasysControllerBean.goCourses();
		return "courses";
	}

	public void addToCourses(Course c) {
		courses.add(c);
	}

	public void emptyCourses() {
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

	public void addToAllConfessions(String reli) {
		alleKonfessionen.add(new SelectItem(reli));
	}

	public void clearAllConfessions() {
		alleKonfessionen.clear();
	}

	public boolean bundleChosen(String bundle) {
		if (bundle.equals("sport"))
			return dbh.sportChosen(dbh.getUserId());
		else if (bundle.equals("reli"))
			return dbh.reliChosen(dbh.getUserId());
		else
			return dbh.bundleChosen(dbh.getUserId(), bundle);
	}

	public String isDateConflicting() {
		for (int i = 1; i <= 10; i++)
			if (dbh.isDateConflicting(dbh.getUserId(), i))
				return dbh.translateDate(i);
		return "0";
	}

	public Course getSingleCourse(int id) {
		return dbh.getCourse(id);
	}

	/**
	 * Vorhandenen Kurs in DB updaten
	 * 
	 * @return
	 */
	public String sendCourseUpdate(String strid, String name,
			String faecherverbund, String raum, String strteilnehmerzahl,
			String strkurslehrer, String strtermin, String beschreibung) {

		int id = Integer.parseInt(strid);
		int teilnehmerzahl = Integer.parseInt(strteilnehmerzahl);
		int kurslehrer = Integer.parseInt(strkurslehrer);
		int termin = 1;
		String[] termine = { "MO VO", "MO NA", "DI VO", "DI NA", "MI VO",
				"MI NA", "DO VO", "DO NA", "FR VO", "FR NA" };

		for (int i = 0; i < 10; i++)
			if (termine[i].equals(strtermin))
				termin = termin + i;

		// TODO MESSAGE/LOG DEBUG
		System.out.println("ID: " + id);
		System.out.println("Kursname: " + name);
		System.out.println("faecherverbund: " + faecherverbund);
		System.out.println("raum: " + raum);
		System.out.println("Teilnehmerzahl: " + teilnehmerzahl);
		System.out.println("Kurslehrer: " + kurslehrer);
		System.out.println("Termin: " + strtermin);
		System.out.println("Beschreibung: " + beschreibung);

		dbh.updateCourse(id, name, faecherverbund, raum, teilnehmerzahl,
				kurslehrer, termin, beschreibung);

		logger.info("Kurs: " + name + " " + faecherverbund + " geändert!");
		return "courseupdatesuccess";
	}

	/**
	 * GETTER & SETTER
	 * 
	 */

	// active courses
	public void addToCoursesActive(Course c) {
		coursesActive.add(c);
	}

	public void emptyCoursesActive() {
		coursesActive.clear();
	}

	public List<Course> getCoursesActive() {
		return dbh.listCoursesActive();
	}

	public void setCoursesActive(List<Course> coursesActive) {
		this.coursesInactive = coursesActive;
	}

	// inactive courses
	public void addToCoursesInactive(Course c) {
		coursesInactive.add(c);
	}

	public void emptyCoursesInactive() {
		coursesInactive.clear();
	}

	public List<Course> getCoursesInactive() {
		return dbh.listCoursesInactive();
	}

	public void setCoursesInactive(List<Course> coursesInactive) {
		this.coursesInactive = coursesInactive;
	}

	public List<Course> getCourses() {
		if (context.getExternalContext().isUserInRole("admin"))
			return dbh.listCourses();
		if (context.getExternalContext().isUserInRole("lehrer"))
			return dbh.listCoursesTeacher(dbh.getUserId());
		if (context.getExternalContext().isUserInRole("schueler"))
			return dbh.listCoursesStudent(dbh.getUserId());
		else
			return courses;
	}

	public List<Course> getCoursesAttendable() {
		if (context.getExternalContext().isUserInRole("schueler"))
			return dbh.listCoursesAttendable(dbh.getUserId());
		else
			return null;
	}

	public List<Course> getCourseAttenders() {
		if (context.getExternalContext().isUserInRole("admin"))
			return dbh.listCourses();
		if (context.getExternalContext().isUserInRole("lehrer"))
			return dbh.listCoursesTeacher(dbh.getUserId());
		else
			return null;
	}

	public List<User> getCourseAttenders(int id) {
		return dbh.listCourseParticipants(id);
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
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

	public void setRaum(String raum) {
		this.raum = raum;
	}

	public String getRaum() {
		return raum;
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

	public void setAlleKonfessionen(ArrayList<SelectItem> alleKonfessionen) {
		this.alleKonfessionen = alleKonfessionen;
	}

	public static class Course implements Serializable {
		/**
		 * serial id for serialisation versioning
		 */
		private static final long serialVersionUID = 1L;

		DatabaseHandler dbh = new DatabaseHandler();

		private int _id;
		private String _name;
		private int _kurslehrer;
		private String _kurslehrerName;
		private String _faecherverbund;
		private String _termin;
		private String _beschreibung;
		private int _jahr;
		private int _tertial;
		private int _teilnehmerzahl;
		private int _teilnehmerzahlAktuell;
		private boolean _pflichtkurs;
		private boolean _sport;
		private String _raum;

		public Course(int id, String name, int kurslehrer,
				String faecherverbund, String termin, String beschreibung,
				int jahr, int tertial, int teilnehmerzahl, boolean pflichtkurs,
				boolean sport, String raum) {
			System.out.println("CourseConstructor");
			_id = id;
			_name = name;
			_kurslehrer = kurslehrer;
			_kurslehrerName = dbh.showUserFullName(kurslehrer);
			_faecherverbund = faecherverbund;
			_raum = raum;
			_termin = termin;
			_beschreibung = beschreibung;
			set_jahr(jahr);
			set_tertial(tertial);
			set_teilnehmerzahl(teilnehmerzahl);
			set_teilnehmerzahlAktuell(dbh.countCourseParticipants(id));
			set_pflichtkurs(pflichtkurs);
			set_sport(sport);

		}

		public String attendCourse() {
			dbh.addToGradelist(0, "", dbh.getUserId(), _id);
			// return kuwasysControllerBean.goCourses();
			return "courses";
		}

		public String unAttendCourse() {
			dbh.removeFromGradelist(dbh.getUserId(), _id);
			// return kuwasysControllerBean.goCourses();
			return "courses";
		}

		public String activateCourse() {
			dbh.activateCourse(_id);
			// return kuwasysControllerBean.goCourses();
			return "courses";
		}

		public String deActivateCourse() {
			dbh.deActivateCourse(_id);
			// return kuwasysControllerBean.goCourses();
			return "courses";
		}

		public String toggleEssentialCourse() {
			System.out.println("SET " + _id + " ESSENTIAL");
			dbh.toggleEssentialCourse(_id);
			// return kuwasysControllerBean.goCourses();
			return "courses";
		}

		public List<User> getCourseAttenders() {
			System.out.println("listCourseAttenders " + _id);
			return dbh.listCourseParticipants(_id);
		}

		public boolean isCurrentTertial() {
			if (_jahr == kuwasysControllerBean.year
					&& _tertial == kuwasysControllerBean.tertial)
				return true;
			else
				return false;
		}

		public double courseGrade() {
			return dbh.getCourseGrade(dbh.getUserId(), _id);
		}

		public boolean freePositions() {
			if (_teilnehmerzahl > _teilnehmerzahlAktuell)
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

		public String get_raum() {
			return _raum;
		}

		public void set_raum(String _raum) {
			this._raum = _raum;
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

		public String get_termin() {
			return _termin;
		}

		public void set_termin(String _termin) {
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
