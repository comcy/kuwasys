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
	private int usersid;
	private int kursid;
	
	public String addToGradelist(){
		DatabaseHandler.addToGradelist(note, bemerkung, usersid, kursid);
		return kuwasysControllerBean.gradelist();
	}
	
	public static void addToGradelist(Gradelist n){
		gradelist.add(c);
	}
	public static void emptyGradelist(){
		gradelist.clear();
	}
	
	public void setGradelist(List<Gradelist> gradelist) {
		GradelistBean.grades = grades;
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
		this.usersid = usersid;
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
		return usersid;
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
		private int _usersid;
		private int _kursid;
	
		public Gradelist (int id, int note, String bemerkung, int usersid, int kursid) {
		    _id = id;
		    _note = note;
		    _bemerkung = bemerkung;
		    _usersid = usersid;
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
			this._usersid = _usersid;
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
			return _usersid;
		}
		
		public int get_kursid() {
			return _kursid;
		}
	}
}
