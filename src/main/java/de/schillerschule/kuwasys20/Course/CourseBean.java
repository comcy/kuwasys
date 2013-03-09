package de.schillerschule.kuwasys20.Course;

import java.io.Serializable;
import java.util.*;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import de.schillerschule.kuwasys20.Controller.kuwasysControllerBean;
import de.schillerschule.kuwasys20.Database.DatabaseHandler;

@ManagedBean(name = "courseBean")
@RequestScoped

/**
 * DOCUMENT ME!
 * @author Thomas Spiegl (latest modification by $Author: grantsmith $)
 * @version $Revision: 472610 $ $Date: 2006-11-08 14:46:34 -0500 (miÃ©, 08 nov 2006) $
 */
public class CourseBean{

	private static List<Course> courses = new ArrayList<Course>();

	private int id;
	private String name;
	private int kurslehrer;
	private String faecherverbund;
	private int termin;
	private String beschreibung;
	
	public String addCourse(){
		DatabaseHandler.addCourse(name, faecherverbund, 5/**kurslehrer**/, termin, beschreibung);
		return kuwasysControllerBean.courses();
	}
	
	public static void addToCourses(Course c){
		courses.add(c);
	}
	public static void emptyCourses(){
		courses.clear();
	}
	
	
	
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


	
	public static class Course
	    implements Serializable
	{
		/**
		 * serial id for serialisation versioning
		 */
		private static final long serialVersionUID = 1L;
		private int _id;
		private String _name;
		private int _kurslehrer;
		private String _faecherverbund;
		private int _termin;
		private String _beschreibung;
		
		
		public Course (int id, String name, int kurslehrer, String faecherverbund, int termin, String beschreibung)
		{
		    _id = id;
		    _name = name;
		    _kurslehrer = kurslehrer;
		    _faecherverbund = faecherverbund;
		    _termin = termin;
		    _beschreibung= beschreibung;
		    
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
	}
}
