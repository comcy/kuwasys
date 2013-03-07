package de.schillerschule.kuwasys20.Course;

import java.io.Serializable;
import java.util.*;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "courseBean")
@RequestScoped

/**
 * DOCUMENT ME!
 * @author Thomas Spiegl (latest modification by $Author: grantsmith $)
 * @version $Revision: 472610 $ $Date: 2006-11-08 14:46:34 -0500 (miÃ©, 08 nov 2006) $
 */
public class CourseBean{

	private static List<Course> courses = new ArrayList<Course>();
	
	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		CourseBean.courses = courses;
	}
	
	public static void addCourse(Course c){
		courses.add(c);
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
		private String _kurslehrer;
		private String _faecherverbund;
		private int _termin;
		
		
		public Course (int id, String name, String kurslehrer, String faecherverbund, int termin)
		{
		    _id = id;
		    _name = name;
		    _kurslehrer = kurslehrer;
		    _faecherverbund = faecherverbund;
		    _termin = termin;
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
		
		public String get_kurslehrer() {
			return _kurslehrer;
		}
		
		public void set_kurslehrer(String _kurslehrer) {
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
	}
}
