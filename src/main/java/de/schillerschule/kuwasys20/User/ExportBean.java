package de.schillerschule.kuwasys20.User;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import de.schillerschule.kuwasys20.Course.CourseBean.Course;
import de.schillerschule.kuwasys20.Database.DatabaseHandler;
import de.schillerschule.kuwasys20.Teacher.TeacherBean.Teacher;
import de.schillerschule.kuwasys20.User.UserBean.User;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@ManagedBean(name = "exportBean")
@RequestScoped
public class ExportBean implements Serializable {

	private static final long serialVersionUID = 1L;

	DatabaseHandler dbh = new DatabaseHandler();

	/**
	 * Exportfunktion einer CSV-Datei für die gesamte Schülerliste TODO
	 * Usernamen und Passwort
	 * 
	 * @return Facelet "users"
	 */
	public String csvDownloadStudents() {

		String filename = "Schülerliste.csv";

		try {
			FacesContext fc = FacesContext.getCurrentInstance();

			ExternalContext ec = fc.getExternalContext();

			ec.responseReset();
			ec.setResponseContentType("text/comma-separated-values");
			ec.setResponseHeader("Content-Disposition",
					"attachment; filename=\"" + filename + "\"");

			OutputStream os = ec.getResponseOutputStream();
			PrintStream ps = new PrintStream(os);

			List<User> users = dbh.listUsers();

			// DEBUG
			System.out.println("CSV Export - Schülerliste:");
			System.out.println("--------------------------");

			for (User user : users) {
				ps.print(user.get_vorname() + ";" + user.get_nachname() + ";"
						+ user.get_geburtstag() + ";" + user.get_konfession()
						+ ";" + user.get_klasse() + ";" + user.get_username()
						+ ";" + user.get_passwort() + "\n");

				// DEBUG
				System.out.println(user.get_vorname());
				System.out.println(user.get_nachname());
				System.out.println(user.get_geburtstag());
				System.out.println(user.get_konfession());
				System.out.println(user.get_klasse());
				System.out.println(user.get_username());
				System.out.println(user.get_passwort());
				System.out.println("--------------------------");
			}

			ps.flush();
			ps.close();

			fc.responseComplete();
		} catch (IOException ex) {
			System.out.println("File Export Error: " + ex);
		}
		return "users";
	}

	/**
	 * Exportfunktion einer CSV-Datei für die gesamte Kursliste TODO Tests wegen
	 * nicht- und aktivierten Kursen
	 * 
	 * @return Facelet "courses"
	 */
	public String csvDownloadCourses() {

		String filename = "Kursliste.csv";

		try {
			FacesContext fc = FacesContext.getCurrentInstance();

			ExternalContext ec = fc.getExternalContext();

			ec.responseReset();
			ec.setResponseContentType("text/comma-separated-values");
			ec.setResponseHeader("Content-Disposition",
					"attachment; filename=\"" + filename + "\"");

			OutputStream os = ec.getResponseOutputStream();
			PrintStream ps = new PrintStream(os);

			List<Course> courses = dbh.listCourses();

			// DEBUG
			System.out.println("CSV Export - Kursliste:");
			System.out.println("--------------------------");

			for (Course course : courses) {
				ps.print(course.get_name() + ";" + course.get_faecherverbund()
						+ ";" + course.get_termin() + ";"
						+ course.get_beschreibung() + ";"
						+ course.get_kurslehrerName() + ";"
						+ course.get_teilnehmerzahl() + "\n");

				// DEBUG
				System.out.println(course.get_name());
				System.out.println(course.get_faecherverbund());
				System.out.println(course.get_termin());
				System.out.println(course.get_beschreibung());
				System.out.println(course.get_kurslehrerName());
				System.out.println(course.get_teilnehmerzahl());
				System.out.println("--------------------------");
			}

			ps.flush();
			ps.close();

			fc.responseComplete();
		} catch (IOException ex) {
			System.out.println("File Export Error: " + ex);
		}
		return "courses";
	}

	/**
	 * Exportfunktion einer CSV-Datei für die gesamte Klassenliste TODO Tests
	 * wegen nicht- und aktivierten Kursen
	 * 
	 * @return Facelet "courses"
	 */
	public String csvDownloadClass() {

		String filename = "Klassenliste.csv";

		try {
			FacesContext fc = FacesContext.getCurrentInstance();

			ExternalContext ec = fc.getExternalContext();

			ec.responseReset();
			ec.setResponseContentType("text/comma-separated-values");
			ec.setResponseHeader("Content-Disposition",
					"attachment; filename=\"" + filename + "\"");

			OutputStream os = ec.getResponseOutputStream();
			PrintStream ps = new PrintStream(os);

			List<User> users = dbh.listClassesTeacher(dbh.getUserId());

			// DEBUG
			System.out.println("CSV Export - Schülerliste:");
			System.out.println("--------------------------");

			for (User user : users) {
				ps.print(user.get_vorname() + ";" + user.get_nachname() + ";"
						+ user.get_geburtstag() + ";" + user.get_konfession()
						+ ";" + user.get_klasse() + ";" + user.get_username()
						+ ";" + user.get_passwort() + "\n");

				// DEBUG
				System.out.println(user.get_vorname());
				System.out.println(user.get_nachname());
				System.out.println(user.get_geburtstag());
				System.out.println(user.get_konfession());
				System.out.println(user.get_klasse());
				System.out.println(user.get_username());
				System.out.println(user.get_passwort());
				System.out.println("--------------------------");
			}

			ps.flush();
			ps.close();

			fc.responseComplete();
		} catch (IOException ex) {
			System.out.println("CSV File Export Error: " + ex);
		}
		return "courses";
	}

	/**
	 * Exportfunktion einer PDF-Datei für die Klassenliste des betreffenden Lehrers
	 * mit den Usernamen und Passwörtern
	 * 
	 * @return Facelet "users"
	 * @throws IOException
	 */
	public String pdfDownloadClass() throws IOException {

		String filename = "Schüler_Passwortliste.pdf";

		try {

			FacesContext fc = FacesContext.getCurrentInstance();
			ExternalContext ec = fc.getExternalContext();
			OutputStream os = ec.getResponseOutputStream();

			// Dateiinformationen setzen
			ec.responseReset();
			ec.setResponseContentType("application/pdf");
			ec.setResponseCharacterEncoding("utf-8");
			ec.setResponseHeader("Expires", "0");
			ec.setResponseHeader("Cache-Control",
					"must-revalidate, post-check=0, pre-check=0");
			ec.setResponseHeader("Pragma", "public");
			ec.setResponseHeader("Content-Disposition",
					"attachment; filename=\"" + filename + "\"");

			Document doc = new Document();

			// PdfWriter writer =
			PdfWriter.getInstance(doc, os);

			// Schriftarten definieren
			// Helvetica, fett
			Font font1 = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
			// Courier kursiv
			Font font2 = new Font(Font.FontFamily.COURIER, 16);
			// Roman, normal
			Font font3 = new Font(Font.FontFamily.TIMES_ROMAN, 12);
			// Roman, fett
			Font font4 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

			// Header Bild "KuWaSys" definieren
			// Bild "Header" auch unter "/home/ijcy/pics/"
			Image headerImage = Image
					.getInstance("http://141.10.50.250:8080/kuwasys20/javax.faces.resource/header.jpg.jsf?ln=img");
			headerImage.scaleToFit(500, 150);

			// List<User> anlegen
			List<User> users = dbh.listClassesTeacher(dbh.getUserId());

			// Tabellen-Objekt anlegen
			PdfPTable tableHead = new PdfPTable(4); // 4 Spalten
			PdfPTable tableCont = new PdfPTable(4); // 4 Spalten
			tableHead.setWidthPercentage(100);
			tableCont.setWidthPercentage(100);
			tableHead.setSpacingBefore(10f);

			doc.open(); // Dokument beginnen

			// TODO PDF Erstellung - externe PDF Klasse schreiben ???

			doc.add(headerImage);
			doc.add(new Paragraph("Passwortliste der Klasse: ", font1));
			doc.add(new Paragraph(dbh.showUserClass(dbh.getUserId()), font2));
			doc.add(new Paragraph("Klassenlehrer : ", font1));
			doc.add(new Paragraph(dbh.showUserFullName(dbh.getUserId()), font2));
			// doc.add(new
			// Paragraph("----------------------------------------------------",
			// font2));

			// statischen Kopf der Tabelle erzeugen
			PdfPCell cellVName = new PdfPCell(new Paragraph("Vorname", font4));
			PdfPCell cellNName = new PdfPCell(new Paragraph("Nachname", font4));
			PdfPCell cellUsername = new PdfPCell(new Paragraph("Username",
					font4));
			PdfPCell cellPasswort = new PdfPCell(new Paragraph("Passwort",
					font4));

			tableHead.addCell(cellVName);
			tableHead.addCell(cellNName);
			tableHead.addCell(cellUsername);
			tableHead.addCell(cellPasswort);

			doc.add(tableHead); // Tabellenkopf hinzufügen

			for (User user : users) {
				
				// dynamische Usertabelle erzeugen
				PdfPCell cellVNameDyn = new PdfPCell(new Paragraph(
						user.get_vorname(), font3));
				PdfPCell cellNNameDyn = new PdfPCell(new Paragraph(
						user.get_nachname(), font3));
				PdfPCell cellUsernameDyn = new PdfPCell(new Paragraph(
						user.get_username(), font3));
				PdfPCell cellPasswortDyn = new PdfPCell(new Paragraph(
						user.get_passwort(), font3));

				tableCont.addCell(cellVNameDyn);
				tableCont.addCell(cellNNameDyn);
				tableCont.addCell(cellUsernameDyn);
				tableCont.addCell(cellPasswortDyn);
			}
			doc.add(tableCont); // Tabelle mit dynamischen Inhalt hinzufügen

			doc.close(); // Dokument beenden

			os.flush();
			os.close();

			fc.responseComplete(); // "response" abschließen, sonst wird HTML
									// Kontext
									// der aktuellen Seite in die Datei
									// geschrieben

		} catch (DocumentException de) {
			System.out.println("Error during PDF creation: " + de);
		} catch (IOException ioe) {
			System.out.println("Error during PDF creation: " + ioe);
		}
		return "users";
	}
	
	/**
	 * Exportfunktion einer PDF-Datei für ...
	 * 
	 * @return Facelet "users"
	 * @throws IOException
	 */
	public String pdfDownloadTeachers() throws IOException {

		String filename = "Lehrer_Passwortliste.pdf";

		try {

			FacesContext fc = FacesContext.getCurrentInstance();
			ExternalContext ec = fc.getExternalContext();
			OutputStream os = ec.getResponseOutputStream();

			// Dateiinformationen setzen
			ec.responseReset();
			ec.setResponseContentType("application/pdf");
			ec.setResponseCharacterEncoding("utf-8");
			ec.setResponseHeader("Expires", "0");
			ec.setResponseHeader("Cache-Control",
					"must-revalidate, post-check=0, pre-check=0");
			ec.setResponseHeader("Pragma", "public");
			ec.setResponseHeader("Content-Disposition",
					"attachment; filename=\"" + filename + "\"");

			Document doc = new Document();

			// PdfWriter writer =
			PdfWriter.getInstance(doc, os);

			// Schriftarten definieren
			// Helvetica, fett
			Font font1 = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
			// Roman, normal
			Font font3 = new Font(Font.FontFamily.TIMES_ROMAN, 12);
			// Roman, fett
			Font font4 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

			// Header Bild "KuWaSys" definieren
			// Bild "Header" auch unter "/home/ijcy/pics/"
			Image headerImage = Image
					.getInstance("http://141.10.50.250:8080/kuwasys20/javax.faces.resource/header.jpg.jsf?ln=img");
			headerImage.scaleToFit(500, 150);

			// List<User> anlegen
			List<Teacher> teachers = dbh.listTeachers();

			// Tabellen-Objekt anlegen
			PdfPTable tableHead = new PdfPTable(4); // 4 Spalten
			PdfPTable tableCont = new PdfPTable(4); // 4 Spalten
			tableHead.setWidthPercentage(100);
			tableCont.setWidthPercentage(100);
			tableHead.setSpacingBefore(10f);

			doc.open(); // Dokument beginnen

			// TODO PDF Erstellung - externe PDF Klasse schreiben ???

			doc.add(headerImage);
			doc.add(new Paragraph("Lehrer Passwortliste", font1));
			
			// statischen Kopf der Tabelle erzeugen
			PdfPCell cellVName = new PdfPCell(new Paragraph("Vorname", font4));
			PdfPCell cellNName = new PdfPCell(new Paragraph("Nachname", font4));
			PdfPCell cellUsername = new PdfPCell(new Paragraph("Username",
					font4));
			PdfPCell cellPasswort = new PdfPCell(new Paragraph("Passwort",
					font4));

			tableHead.addCell(cellVName);
			tableHead.addCell(cellNName);
			tableHead.addCell(cellUsername);
			tableHead.addCell(cellPasswort);

			doc.add(tableHead); // Tabellenkopf hinzufügen

			for (Teacher teacher : teachers) {
				
				// dynamische Usertabelle erzeugen
				PdfPCell cellVNameDyn = new PdfPCell(new Paragraph(
						teacher.get_vorname(), font3));
				PdfPCell cellNNameDyn = new PdfPCell(new Paragraph(
						teacher.get_nachname(), font3));
				PdfPCell cellUsernameDyn = new PdfPCell(new Paragraph(
						teacher.get_username(), font3));
				PdfPCell cellPasswortDyn = new PdfPCell(new Paragraph(
						teacher.get_passwort(), font3));

				tableCont.addCell(cellVNameDyn);
				tableCont.addCell(cellNNameDyn);
				tableCont.addCell(cellUsernameDyn);
				tableCont.addCell(cellPasswortDyn);
			}
			doc.add(tableCont); // Tabelle mit dynamischen Inhalt hinzufügen

			doc.close(); // Dokument beenden

			os.flush();
			os.close();

			fc.responseComplete(); // "response" abschließen, sonst wird HTML
									// Kontext
									// der aktuellen Seite in die Datei
									// geschrieben

		} catch (DocumentException de) {
			System.out.println("Error during PDF creation: " + de);
		} catch (IOException ioe) {
			System.out.println("Error during PDF creation: " + ioe);
		}
		return "users";
	}
	
	/**
	 * Exportfunktion einer CSV-Datei für das Kursbuch eines Schülers 
	 * TODO SQL Abfrage
	 * 
	 * @return Facelet "coursebook"
	 */
	public String csvDownloadCoursebook() {

		String filename = "Kursbuch.csv";

		try {
			FacesContext fc = FacesContext.getCurrentInstance();

			ExternalContext ec = fc.getExternalContext();

			ec.responseReset();
			ec.setResponseContentType("text/comma-separated-values");
			ec.setResponseHeader("Content-Disposition",
					"attachment; filename=\"" + filename + "\"");

			OutputStream os = ec.getResponseOutputStream();
			PrintStream ps = new PrintStream(os);

			List<User> users = dbh.listClassesTeacher(dbh.getUserId());

			// DEBUG
			System.out.println("CSV Export - Schülerliste:");
			System.out.println("--------------------------");

			for (User user : users) {
				ps.print(user.get_vorname() + ";" + user.get_nachname() + ";"
						+ user.get_geburtstag() + ";" + user.get_konfession()
						+ ";" + user.get_klasse() + ";" + user.get_username()
						+ ";" + user.get_passwort() + "\n");

				// DEBUG
				System.out.println(user.get_vorname());
				System.out.println(user.get_nachname());
				System.out.println(user.get_geburtstag());
				System.out.println(user.get_konfession());
				System.out.println(user.get_klasse());
				System.out.println(user.get_username());
				System.out.println(user.get_passwort());
				System.out.println("--------------------------");
			}

			ps.flush();
			ps.close();

			fc.responseComplete();
		} catch (IOException ex) {
			System.out.println("CSV File Export Error: " + ex);
		}
		return "coursebook";
	}

	/**
	 * Exportfunktion einer PDF-Datei für das Kursbuch eines Schülers
	 * TODO SQL Abfrage
	 * 
	 * @return Facelet "coursebook"
	 * @throws IOException
	 */
	public String pdfDownloadCoursebook() throws IOException {

		String filename = "Kursbuch.pdf";

		try {

			FacesContext fc = FacesContext.getCurrentInstance();
			ExternalContext ec = fc.getExternalContext();
			OutputStream os = ec.getResponseOutputStream();

			// Dateiinformationen setzen
			ec.responseReset();
			ec.setResponseContentType("application/pdf");
			ec.setResponseCharacterEncoding("utf-8");
			ec.setResponseHeader("Expires", "0");
			ec.setResponseHeader("Cache-Control",
					"must-revalidate, post-check=0, pre-check=0");
			ec.setResponseHeader("Pragma", "public");
			ec.setResponseHeader("Content-Disposition",
					"attachment; filename=\"" + filename + "\"");

			Document doc = new Document();

			// PdfWriter writer =
			PdfWriter.getInstance(doc, os);

			// Schriftarten definieren
			// Helvetica, fett
			Font font1 = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
			// Courier kursiv
			Font font2 = new Font(Font.FontFamily.COURIER, 16);
			// Roman, normal
			Font font3 = new Font(Font.FontFamily.TIMES_ROMAN, 12);
			// Roman, fett
			Font font4 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

			// Header Bild "KuWaSys" definieren
			// Bild "Header" auch unter "/home/ijcy/pics/"
			Image headerImage = Image
					.getInstance("http://141.10.50.250:8080/kuwasys20/javax.faces.resource/header.jpg.jsf?ln=img");
			headerImage.scaleToFit(500, 150);

			// List<User> anlegen
			List<User> users = dbh.listClassesTeacher(dbh.getUserId());

			// Tabellen-Objekt anlegen
			PdfPTable tableHead = new PdfPTable(4); // 4 Spalten
			PdfPTable tableCont = new PdfPTable(4); // 4 Spalten
			tableHead.setWidthPercentage(100);
			tableCont.setWidthPercentage(100);
			tableHead.setSpacingBefore(10f);

			doc.open(); // Dokument beginnen

			// TODO PDF Erstellung - externe PDF Klasse schreiben ???

			doc.add(headerImage);
			doc.add(new Paragraph("Passwortliste der Klasse: ", font1));
			doc.add(new Paragraph(dbh.showUserClass(dbh.getUserId()), font2));
			doc.add(new Paragraph("Klassenlehrer : ", font1));
			doc.add(new Paragraph(dbh.showUserFullName(dbh.getUserId()), font2));
			// doc.add(new
			// Paragraph("----------------------------------------------------",
			// font2));

			// statischen Kopf der Tabelle erzeugen
			PdfPCell cellVName = new PdfPCell(new Paragraph("Vorname", font4));
			PdfPCell cellNName = new PdfPCell(new Paragraph("Nachname", font4));
			PdfPCell cellUsername = new PdfPCell(new Paragraph("Username",
					font4));
			PdfPCell cellPasswort = new PdfPCell(new Paragraph("Passwort",
					font4));

			tableHead.addCell(cellVName);
			tableHead.addCell(cellNName);
			tableHead.addCell(cellUsername);
			tableHead.addCell(cellPasswort);

			doc.add(tableHead); // Tabellenkopf hinzufügen

			for (User user : users) {
				
				// dynamische Usertabelle erzeugen
				PdfPCell cellVNameDyn = new PdfPCell(new Paragraph(
						user.get_vorname(), font3));
				PdfPCell cellNNameDyn = new PdfPCell(new Paragraph(
						user.get_nachname(), font3));
				PdfPCell cellUsernameDyn = new PdfPCell(new Paragraph(
						user.get_username(), font3));
				PdfPCell cellPasswortDyn = new PdfPCell(new Paragraph(
						user.get_passwort(), font3));

				tableCont.addCell(cellVNameDyn);
				tableCont.addCell(cellNNameDyn);
				tableCont.addCell(cellUsernameDyn);
				tableCont.addCell(cellPasswortDyn);
			}
			doc.add(tableCont); // Tabelle mit dynamischen Inhalt hinzufügen

			doc.close(); // Dokument beenden

			os.flush();
			os.close();

			fc.responseComplete(); // "response" abschließen, sonst wird HTML
									// Kontext
									// der aktuellen Seite in die Datei
									// geschrieben

		} catch (DocumentException de) {
			System.out.println("Error during PDF creation: " + de);
		} catch (IOException ioe) {
			System.out.println("Error during PDF creation: " + ioe);
		}
		return "coursebook";
	}
	
	/**
	 * Exportfunktion einer PDF-Datei für einen neu angelegten Schüler
	 * TODO SQL Abfrage
	 * 
	 * @return Facelet "coursebook"
	 * @throws IOException
	 */
	public String pdfDownloadStudent() throws IOException {

		String filename = "Kursbuch.pdf";

		try {

			FacesContext fc = FacesContext.getCurrentInstance();
			ExternalContext ec = fc.getExternalContext();
			OutputStream os = ec.getResponseOutputStream();

			// Dateiinformationen setzen
			ec.responseReset();
			ec.setResponseContentType("application/pdf");
			ec.setResponseCharacterEncoding("utf-8");
			ec.setResponseHeader("Expires", "0");
			ec.setResponseHeader("Cache-Control",
					"must-revalidate, post-check=0, pre-check=0");
			ec.setResponseHeader("Pragma", "public");
			ec.setResponseHeader("Content-Disposition",
					"attachment; filename=\"" + filename + "\"");

			Document doc = new Document();

			// PdfWriter writer =
			PdfWriter.getInstance(doc, os);

			// Schriftarten definieren
			// Helvetica, fett
			Font font1 = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
			// Courier kursiv
			Font font2 = new Font(Font.FontFamily.COURIER, 16);
			// Roman, normal
			Font font3 = new Font(Font.FontFamily.TIMES_ROMAN, 12);
			// Roman, fett
			Font font4 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

			// Header Bild "KuWaSys" definieren
			// Bild "Header" auch unter "/home/ijcy/pics/"
			Image headerImage = Image
					.getInstance("http://141.10.50.250:8080/kuwasys20/javax.faces.resource/header.jpg.jsf?ln=img");
			headerImage.scaleToFit(500, 150);

			// List<User> anlegen
			List<User> users = dbh.listClassesTeacher(dbh.getUserId());

			// Tabellen-Objekt anlegen
			PdfPTable tableHead = new PdfPTable(4); // 4 Spalten
			PdfPTable tableCont = new PdfPTable(4); // 4 Spalten
			tableHead.setWidthPercentage(100);
			tableCont.setWidthPercentage(100);
			tableHead.setSpacingBefore(10f);

			doc.open(); // Dokument beginnen

			// TODO PDF Erstellung - externe PDF Klasse schreiben ???

			doc.add(headerImage);
			doc.add(new Paragraph("Passwortliste der Klasse: ", font1));
			doc.add(new Paragraph(dbh.showUserClass(dbh.getUserId()), font2));
			doc.add(new Paragraph("Klassenlehrer : ", font1));
			doc.add(new Paragraph(dbh.showUserFullName(dbh.getUserId()), font2));
			// doc.add(new
			// Paragraph("----------------------------------------------------",
			// font2));

			// statischen Kopf der Tabelle erzeugen
			PdfPCell cellVName = new PdfPCell(new Paragraph("Vorname", font4));
			PdfPCell cellNName = new PdfPCell(new Paragraph("Nachname", font4));
			PdfPCell cellUsername = new PdfPCell(new Paragraph("Username",
					font4));
			PdfPCell cellPasswort = new PdfPCell(new Paragraph("Passwort",
					font4));

			tableHead.addCell(cellVName);
			tableHead.addCell(cellNName);
			tableHead.addCell(cellUsername);
			tableHead.addCell(cellPasswort);

			doc.add(tableHead); // Tabellenkopf hinzufügen

			for (User user : users) {
				
				// dynamische Usertabelle erzeugen
				PdfPCell cellVNameDyn = new PdfPCell(new Paragraph(
						user.get_vorname(), font3));
				PdfPCell cellNNameDyn = new PdfPCell(new Paragraph(
						user.get_nachname(), font3));
				PdfPCell cellUsernameDyn = new PdfPCell(new Paragraph(
						user.get_username(), font3));
				PdfPCell cellPasswortDyn = new PdfPCell(new Paragraph(
						user.get_passwort(), font3));

				tableCont.addCell(cellVNameDyn);
				tableCont.addCell(cellNNameDyn);
				tableCont.addCell(cellUsernameDyn);
				tableCont.addCell(cellPasswortDyn);
			}
			doc.add(tableCont); // Tabelle mit dynamischen Inhalt hinzufügen

			doc.close(); // Dokument beenden

			os.flush();
			os.close();

			fc.responseComplete(); // "response" abschließen, sonst wird HTML
									// Kontext
									// der aktuellen Seite in die Datei
									// geschrieben

		} catch (DocumentException de) {
			System.out.println("Error during PDF creation: " + de);
		} catch (IOException ioe) {
			System.out.println("Error during PDF creation: " + ioe);
		}
		return "coursebook";
	}

	/**
	 * Exportfunktion einer PDF-Datei für das Datenblatt 
	 * nach Passwortänderung des Admins
	 * TODO SQL Abfrage
	 * 
	 * @return Facelet "coursebook"
	 * @throws IOException
	 */
	public String pdfDownloadPassword() throws IOException {

		String filename = "Admin_Datenblatt.pdf";

		try {

			FacesContext fc = FacesContext.getCurrentInstance();
			ExternalContext ec = fc.getExternalContext();

			// Dateiinformationen setzen
			ec.responseReset();
			ec.setResponseContentType("application/pdf");
			ec.setResponseCharacterEncoding("utf-8");
			ec.setResponseHeader("Expires", "0");
			ec.setResponseHeader("Cache-Control",
					"must-revalidate, post-check=0, pre-check=0");
			ec.setResponseHeader("Pragma", "public");
			ec.setResponseHeader("Content-Disposition",
					"attachment; filename=\"" + filename + "\"");

			OutputStream os = ec.getResponseOutputStream();

			Document doc = new Document();

			// PdfWriter writer =
			PdfWriter.getInstance(doc, os);

			// Schriftarten definieren
			// Helvetica, fett
			Font font1 = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
			// Courier kursiv
			Font font2 = new Font(Font.FontFamily.COURIER, 16);

			// Header Bild "KuWaSys" definieren
			// Bild "Header" auch unter "/home/ijcy/pics/"
			Image headerImage = Image
					.getInstance("http://141.10.50.250:8080/kuwasys20/javax.faces.resource/header.jpg.jsf?ln=img");
			headerImage.scaleToFit(500, 150);

			// PDF Dokument
			doc.open(); // Dokument beginnen

			doc.add(headerImage);
			doc.add(new Paragraph("Passwort Datenblatt: " , font1));
			doc.add(new Paragraph(dbh.showUserFullName(dbh.getUserId()), font2));
			
			doc.add(new Paragraph("----------------------------------------------------", font2));
			
			doc.add(new Paragraph("Username: " , font1));
			doc.add(new Paragraph(dbh.getUserUsername(), font2));
			
			doc.add(new Paragraph("Passwort: " , font1));
			doc.add(new Paragraph(dbh.getUserPassword(dbh.getUserId()), font2));		
					
			doc.close(); // Dokument beenden

			os.flush();
			os.close();

			fc.responseComplete(); // "response" abschließen, sonst wird HTML
									// Kontext
									// der aktuellen Seite in die Datei
									// geschrieben

		} catch (DocumentException de) {
			System.out.println("Error during PDF creation: " + de);
		} catch (IOException ioe) {
			System.out.println("Error during PDF creation: " + ioe);
		}
		return "passwordsuccess";
	}

}
