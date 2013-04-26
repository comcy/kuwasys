package de.schillerschule.kuwasys20.Gradelist;

import java.io.Serializable;
import java.util.*;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import de.schillerschule.kuwasys20.Controller.kuwasysControllerBean;
import de.schillerschule.kuwasys20.Database.DatabaseHandler;

@ManagedBean(name = "gradelistBean")
@RequestScoped

public class GradelistBean{

	DatabaseHandler dbh = new DatabaseHandler();
	private List<Grades> gradelists = new ArrayList<Grades>();
	
	private int id;
	private double note;
	private String bemerkung;
	private int userid;
	private String kursname;
	private int jahr;
	private int tertial;
	
	/*public String addToGradelist(){
		DatabaseHandler.addToGradelist(note, bemerkung, userid, kursid);
		return kuwasysControllerBean.goGradelist();
	}*/
	
	public void addToGradelist(Grades g){
		gradelists.add(g);
	}
	public void emptyGradelist(){
		gradelists.clear();
	}
	
	/*public void setGradelists(List<Grades> gradelist) {
		GradelistBean.gradelists = gradelist;
	}	*/
	
	public List<Grades> getGradelists() {
		return dbh.listGradelist(dbh.getUserId());
	}
	
	// Set-Methoden
	public void setId(int id) {
		this.id = id;
	}
	
	public void setNote(double note) {
		this.note = note;
	}
	
	public void setBemerkung(String bemerkung) {
		this.bemerkung = bemerkung;
	}

	public void setUsersid(int usersid) {
		this.userid = usersid;
	}
	
	public void setKursname(String kursname) {
		this.kursname = kursname;
	}
	
	// Get-Methoden
	public int getId() {
		return id;
	}
	
	public double getNote() {
		return note;
	}
	
	public String getBemerkung() {
		return bemerkung;
	}
	
	public int getUsersid() {
		return userid;
	}

	public String getKursname() {
		return kursname;
	}
	
	public static class Grades implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
		
		private int _id;
		private double _note;
		private String _bemerkung;
		private int _userid;
		private String _kursname;
		private int _jahr;
		private int _tertial;
		private String faecherverbund;
	
		public Grades (int id, double note, String bemerkung, int usersid, String kursname, int jahr, int tertial, String faecherverbund) {
		    _id = id;
		    _note = note;
		    _bemerkung = bemerkung;
		    _userid = usersid;
		    _kursname = kursname;
		    set_jahr(jahr);
		    set_tertial(tertial);
		    set_faecherverbund(faecherverbund);
		    
		}
			
		// Set-Methoden
		public void set_id(int _id) {
			this._id = _id;
		}
		
		public void set_note(double _note) {
			this._note = _note;
		}
		
		public void set_bemerkung(String _bemerkung) {
			this._bemerkung = _bemerkung;
		}
		
		public void set_usersid(int _usersid) {
			this._userid = _usersid;
		}
		
		public void set_kursid(String _kursname) {
			this._kursname = _kursname;
		}
		
		// Get-Methoden		
		public int get_id() {
			return _id;
		}
		
		public double get_note() {
			return _note;
		}
		
		public String get_bemerkung() {
			return _bemerkung;
		}
		
		public int get_userid() {
			return _userid;
		}
		
		public String get_kursname() {
			return _kursname;
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

		public String get_faecherverbund() {
			return faecherverbund;
		}

		public void set_faecherverbund(String faecherverbund) {
			this.faecherverbund = faecherverbund;
		}
	}
}
