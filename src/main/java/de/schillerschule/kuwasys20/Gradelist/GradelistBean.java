package de.schillerschule.kuwasys20.Gradelist;

import java.io.Serializable;
import java.util.*;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import de.schillerschule.kuwasys20.Controller.kuwasysControllerBean;
import de.schillerschule.kuwasys20.Database.DatabaseHandler;

@ManagedBean(name = "notelistBean")
@RequestScoped

public class GradelistBean{

	private static List<Grades> gradelist = new ArrayList<Grades>();

	private int id;
	private int note;
	private String bemerkung;
	private int userid;
	private int kursid;
	
	public String addToGradelist(){
		DatabaseHandler.addToGradelist(note, bemerkung, userid, kursid);
		return kuwasysControllerBean.goGradelist();
	}
	
	public static void addToGradelist(Grades g){
		gradelist.add(g);
	}
	public static void emptyGradelist(){
		gradelist.clear();
	}
	
	public void setGradelist(List<Grades> gradelist) {
		GradelistBean.gradelist = gradelist;
	}	
	
	// Set-Methoden
	public void setId(int id) {
		this.id = id;
	}
	
	public void setNote(int note) {
		this.note = note;
	}
	
	public void setBemerkung(String bemerkung) {
		this.bemerkung = bemerkung;
	}

	public void setUsersid(int usersid) {
		this.userid = usersid;
	}
	
	public void setKursid(int kursid) {
		this.kursid = kursid;
	}
	
	// Get-Methoden
	public int getId() {
		return id;
	}
	
	public int getNote() {
		return note;
	}
	
	public String getBemerkung() {
		return bemerkung;
	}
	
	public int getUsersid() {
		return userid;
	}

	public int getKursid() {
		return kursid;
	}

	public List<Grades> getGradelist() {
		return gradelist;
	}
	
	public static class Grades implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
		
		private int _id;
		private int _note;
		private String _bemerkung;
		private int _userid;
		private int _kursid;
	
		public Grades (int id, int note, String bemerkung, int usersid, int kursid) {
		    _id = id;
		    _note = note;
		    _bemerkung = bemerkung;
		    _userid = usersid;
		    _kursid = kursid;
		    
		}
			
		// Set-Methoden
		public void set_id(int _id) {
			this._id = _id;
		}
		
		public void set_note(int _note) {
			this._note = _note;
		}
		
		public void set_bemerkung(String _bemerkung) {
			this._bemerkung = _bemerkung;
		}
		
		public void set_usersid(int _usersid) {
			this._userid = _usersid;
		}
		
		public void set_kursid(int _kursid) {
			this._kursid = _kursid;
		}
		
		// Get-Methoden		
		public int get_id() {
			return _id;
		}
		
		public int get_note() {
			return _note;
		}
		
		public String get_bemerkung() {
			return _bemerkung;
		}
		
		public int get_userid() {
			return _userid;
		}
		
		public int get_kursid() {
			return _kursid;
		}
	}
}
