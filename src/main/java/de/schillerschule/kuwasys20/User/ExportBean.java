package de.schillerschule.kuwasys20.User;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import de.schillerschule.kuwasys20.Course.CourseBean.Course;
import de.schillerschule.kuwasys20.Database.DatabaseHandler;
import de.schillerschule.kuwasys20.Gradelist.GradelistBean.Grades;
import de.schillerschule.kuwasys20.Teacher.TeacherBean.Teacher;
import de.schillerschule.kuwasys20.User.UserBean.User;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@ManagedBean(name = "exportBean")
@RequestScoped
public class ExportBean implements Serializable {

	private static final long serialVersionUID = 1L;

	DatabaseHandler dbh = new DatabaseHandler();

	/**
	 * Exportfunktion einer CSV-Datei für die gesamte Schülerliste
	 * 
	 * @return Facelet "users"
	 */
	public String csvDownloadStudents() {

		String filename = "Schülerliste_Alle.csv";

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
	 * Exportfunktion einer CSV-Datei für die gesamte Kursliste
	 * 
	 * @return Facelet "courses"
	 */
	public String csvDownloadCourses() {

		String filename = "Kursliste_Alle.csv";

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
	 * Exportfunktion einer CSV-Datei für die gesamte Klassenliste
	 * 
	 * @return Facelet "courses"
	 */
	public String csvDownloadClass() {

		String filename = "Klassenliste_" + dbh.showUserClass(dbh.getUserId())
				+ ".csv";

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
	 * Exportfunktion einer CSV-Datei für die Notenliste eines Kurses
	 * 
	 * @return Facelet "coursebook"
	 */
	public String csvDownloadGradelist() {

		Charset charset = Charset.forName("ISO-8859-1");
		// DEBUG
		System.out.println(charset);

		String filename = "Notenliste_"
				+ dbh.getCoursenameOfCourseid(Integer.parseInt(FacesContext
						.getCurrentInstance().getExternalContext()
						.getRequestParameterMap().get("id")))
				+ "_"
				+ dbh.getCourseyearOfCourseid(Integer.parseInt(FacesContext
						.getCurrentInstance().getExternalContext()
						.getRequestParameterMap().get("id")))
				+ "-"
				+ dbh.getCoursetertialOfCourseid(Integer.parseInt(FacesContext
						.getCurrentInstance().getExternalContext()
						.getRequestParameterMap().get("id"))) + ".csv";
		String csvContent = "";

		try {
			FacesContext fc = FacesContext.getCurrentInstance();

			ExternalContext ec = fc.getExternalContext();

			ec.responseReset();
			ec.setResponseContentType("application/vnd.xls");
			ec.setRequestCharacterEncoding("LATIN1");
			ec.setResponseHeader("Content-Disposition",
					"attachment; filename=\"" + filename + "\"");

			// OutpuStreamWriter kann encodieren
			// Windows "ISO-8859-1" Probleme treten nicht mehr auf
			OutputStream os = ec.getResponseOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os, "ISO-8859-1");

			// Kursteilnehmer auslesen
			List<User> participants = dbh.listCourseParticipants(Integer
					.parseInt(fc.getExternalContext().getRequestParameterMap()
							.get("id")));

			// DEBUG
			System.out.println("CSV Export - Notenliste");
			System.out.println("--------------------------");

			for (User participant : participants) {

				csvContent = participant.get_klasse()
						+ ";" // Klasse x
						+ participant.get_nachname()
						+ ";" // Name x
						+ participant.get_vorname()
						+ ";" // Vorname x
						+ dbh.getCourseFaecherverbundOfCourseid(participant
								.get_grade_kursid())
						+ ";" // Fächerverbund
						+ "20"
						+ participant.get_grade_jahr()
						+ "/ "
						+ participant.get_grade_tertial()
						+ ";" // Tertial/Schuljahr
						+ dbh.getCoursenameOfCourseid(participant
								.get_grade_kursid())
						+ ";" // Kursthema
						+ Double.toString(participant.get_grade_fachwissen())
								.replaceAll("[.]", ",")
						+ ";" // Kompetenzen:
						// Fachwissen
						+ Double.toString(participant.get_grade_sozial())
								.replaceAll("[.]", ",")
						+ ";" // Kompetenzen:
						// Sozial
						+ Double.toString(participant.get_grade_personal())
								.replaceAll("[.]", ",")
						+ ";" // Kompetenzen:
						// Personal
						+ Double.toString(participant.get_grade_methodisch())
								.replaceAll("[.]", ",")
						+ ";" // Kompetenzen:
						// Methodisch
						+ Double.toString(participant.get_grade_note())
								.replaceAll("[.]", ",") + ";" // Zehntelnote
						+ dbh.showUserFullName(dbh.getUserId()) + ";" // Lehrerin/Lehrer
																		// ->
																		// Nur
																		// für
																		// Kurslehrer-Export
																		// möglich
																		// da ID
																		// des
																		// aufrufenden
																		// Lehrers
																		// verwendet
																		// wird,
																		// also
																		// seine
																		// selbts
						+ participant.get_grade_bemerkung() + "\n"; // Bemerkungen
				osw.write(csvContent);
			}
			osw.flush();
			osw.close();

			fc.responseComplete();

		} catch (IOException ex) {
			System.out.println("CSV File Export Error: " + ex);
		}
		return "coursebook";
	}

	/**
	 * Exportfunktion einer CSV-Datei für die Notenliste einer Klasse
	 * 
	 * @return Facelet "coursebook"
	 */
	public String csvDownloadGradelistClass() {

		Charset charset = Charset.forName("ISO-8859-1");
		// DEBUG
		System.out.println(charset);

		String filename = "Notenliste_" + dbh.showUserClass(dbh.getUserId()) + ".csv";
				
		String csvContent = "";
		String csvContentUser = "";
		String csvContentCourses = "";
		try {
			FacesContext fc = FacesContext.getCurrentInstance();

			ExternalContext ec = fc.getExternalContext();

			ec.responseReset();
			ec.setResponseContentType("application/vnd.xls");
			ec.setRequestCharacterEncoding("LATIN1");
			ec.setResponseHeader("Content-Disposition",
					"attachment; filename=\"" + filename + "\"");

			// OutpuStreamWriter kann encodieren
			// Windows "ISO-8859-1" Probleme treten nicht mehr auf
			OutputStream os = ec.getResponseOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os, "ISO-8859-1");

			// DEBUG
			System.out.println("CSV Export - Notenliste");
			System.out.println("--------------------------");

			// Kursteilnehmer auslesen
			List<User> users = dbh.listClassesTeacher(dbh.getUserId());

			for (User user : users) {

				csvContentUser = 
						user.get_klasse() + ";" // Klasse
						+ user.get_nachname() + ";" // Nachname
						+ user.get_vorname() + ";"  // Vorname
						;

				
				 List<Grades> gradeList = dbh.listGradelist(user.get_id());
				  
				 for (Grades grade : gradeList) {
					 
					 csvContentCourses = 
							 
							grade.get_faecherverbund() + ";" // Fächerverbund
							+ "20" + Integer.toString(grade.get_jahr()) + "/ "// Jahr
							+ Integer.toString(grade.get_tertial()) + ";" // Tertial
							+ grade.get_kursname() + ";"	// Kursname
							+ Double.toString(grade.get_fachwissen()).replaceAll("[.]", ",") + ";" // Kompetenzen: Fachwissen
							+ Double.toString(grade.get_sozial()) .replaceAll("[.]", ",") + ";" // Kompetenzen: Sozial
							+ Double.toString(grade.get_personal()) .replaceAll("[.]", ",") + ";" // Kompetenzen: Personal
							+ Double.toString(grade.get_methodisch()) .replaceAll("[.]", ",") + ";" // Kompetenzen: Methodisch
							+ Double.toString(grade.get_note()) .replaceAll("[.]", ",") + ";" // Zehntelnote
							+ dbh.getTeachernameOfCourseid() + ";" // zugehörigen Kurslehrer abfragen
							+ grade.get_bemerkung(); // Bemerkungen
				  
					 csvContent = csvContentUser + csvContentCourses + "\n";
					 osw.write(csvContent); 
				 }
				 
			}
			osw.flush();
			osw.close();

			fc.responseComplete();

		} catch (IOException ex) {
			System.out.println("CSV File Export Error: " + ex);
		}
		return "coursebook";
	}

	/**
	 * Exportfunktion einer PDF-Datei für die Klassenliste des betreffenden
	 * Lehrers mit den Usernamen und Passwörtern
	 * 
	 * @return Facelet "users"
	 * @throws IOException
	 */
	public String pdfDownloadClass() throws IOException {

		String filename = "Klassen_Passwortliste_"
				+ dbh.showUserClass(dbh.getUserId()) + ".pdf";

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

			String relativeWebPath = "/resources/img/header.jpg";
			ServletContext servletContext = (ServletContext) ec.getContext();
			String absoluteDiskPath = servletContext
					.getRealPath(relativeWebPath);

			Image headerImage = Image.getInstance(absoluteDiskPath);
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
			String relativeWebPath = "/resources/img/header.jpg";
			ServletContext servletContext = (ServletContext) ec.getContext();
			String absoluteDiskPath = servletContext
					.getRealPath(relativeWebPath);

			Image headerImage = Image.getInstance(absoluteDiskPath);
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
	 * Exportfunktion einer PDF-Datei für das Datenblatt nach Passwortänderung
	 * des Admins
	 * 
	 * @return Facelet "coursebook"
	 * @throws IOException
	 */
	public String pdfDownloadPassword() throws IOException {

		String filename = "Passwort_" + dbh.getUserFirstname(dbh.getUserId())
				+ ".pdf";

		try {

			FacesContext fc = FacesContext.getCurrentInstance();
			ExternalContext ec = fc.getExternalContext();

			// Dateiinformationen setzen
			ec.responseReset();
			ec.setResponseHeader("Content-Type", "application/pdf");
			// ec.setResponseCharacterEncoding("utf-8");
			// ec.setResponseHeader("Expires", "0");
			// ec.setResponseHeader("Cache-Control",
			// "must-revalidate, post-check=0, pre-check=0");
			// ec.setResponseHeader("Pragma", "public");
			ec.setResponseHeader("Content-Disposition",
					"attachment; filename=\"" + filename + "\"");

			OutputStream os = ec.getResponseOutputStream();

			Document doc = new Document();

			PdfWriter.getInstance(doc, os);

			/**
			 * PDF Deklarationen
			 */
			// Schriftarten definieren
			// Helvetica, fett
			Font font1 = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
			// Courier kursiv
			Font font2 = new Font(Font.FontFamily.COURIER, 16);

			// Header Bild "KuWaSys" definieren
			// Bild "Header" auch unter "/home/ijcy/pics/"
			String relativeWebPath = "/resources/img/header.jpg";
			ServletContext servletContext = (ServletContext) ec.getContext();
			String absoluteDiskPath = servletContext
					.getRealPath(relativeWebPath);

			Image headerImage = Image.getInstance(absoluteDiskPath);
			headerImage.scaleToFit(500, 150);

			/**
			 * PDF Dokument Inhalt
			 */
			doc.open(); // Dokument beginnen

			doc.add(headerImage);
			doc.add(new Paragraph("Passwort Datenblatt: ", font1));
			doc.add(new Paragraph(dbh.showUserFullName(dbh.getUserId()), font2));

			doc.add(new Paragraph(
					"----------------------------------------------------",
					font2));

			doc.add(new Paragraph("Username: ", font1));
			doc.add(new Paragraph(dbh.getUserUsername(), font2));

			doc.add(new Paragraph("Passwort: ", font1));
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

	/**
	 * Exportfunktion einer PDF-Datei für den Kurs-Stundenplan eines Schülers
	 * 
	 * @return Facelet "courses"
	 * @throws IOException
	 */
	public String pdfDownloadCoursesTimetableStudent() throws IOException {

		String filename = "Kursplan_" + dbh.getUserFirstname(dbh.getUserId())
				+ ".pdf";

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

			Document doc = new Document(PageSize.A4.rotate());

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
			String relativeWebPath = "/resources/img/header.jpg";
			ServletContext servletContext = (ServletContext) ec.getContext();
			String absoluteDiskPath = servletContext
					.getRealPath(relativeWebPath);

			Image headerImage = Image.getInstance(absoluteDiskPath);
			headerImage.scaleToFit(500, 150);

			// List<User> anlegen
			List<User> users = dbh.listStudentSchedule(dbh.getUserId());

			// Tabellen-Objekt anlegen
			PdfPTable tableHead = new PdfPTable(6); // 6 Spalten
			PdfPTable tableCont = new PdfPTable(6); // 6 Spalten
			tableHead.setWidthPercentage(100);
			tableCont.setWidthPercentage(100);
			tableHead.setSpacingBefore(10f);

			doc.open(); // Dokument beginnen

			doc.add(headerImage);
			doc.add(new Paragraph("Kursplan von: ", font1));
			doc.add(new Paragraph(dbh.showUserFullName(dbh.getUserId()), font2));

			doc.add(new Paragraph(
					"----------------------------------------------------",
					font2));

			doc.add(new Paragraph("Zeitplan", font1));
			// statischen Kopf der Tabelle erzeugen
			PdfPCell cellTime = new PdfPCell(new Paragraph("Zeit", font4));
			PdfPCell cellMo = new PdfPCell(new Paragraph("Montag", font4));
			PdfPCell cellDi = new PdfPCell(new Paragraph("Dienstag", font4));
			PdfPCell cellMi = new PdfPCell(new Paragraph("Mittwoch", font4));
			PdfPCell cellDo = new PdfPCell(new Paragraph("Donnerstag", font4));
			PdfPCell cellFr = new PdfPCell(new Paragraph("Freitag", font4));

			tableHead.addCell(cellTime);
			tableHead.addCell(cellMo);
			tableHead.addCell(cellDi);
			tableHead.addCell(cellMi);
			tableHead.addCell(cellDo);
			tableHead.addCell(cellFr);

			doc.add(tableHead); // Tabellenkopf hinzufügen

			PdfPCell cellTimeVo = new PdfPCell(
					new Paragraph("Vormittag", font3));
			PdfPCell cellTimeNa = new PdfPCell(new Paragraph("Nachmittag",
					font3));

			for (User user : users) {

				// Initialisierung
				PdfPCell cellMoVoDyn;
				PdfPCell cellDiVoDyn;
				PdfPCell cellMiVoDyn;
				PdfPCell cellDoVoDyn;
				PdfPCell cellFrVoDyn;

				PdfPCell cellMoNaDyn;
				PdfPCell cellDiNaDyn;
				PdfPCell cellMiNaDyn;
				PdfPCell cellDoNaDyn;
				PdfPCell cellFrNaDyn;

				// MONTAG
				cellMoVoDyn = new PdfPCell(new Paragraph(user.get_termin1(),
						font3));
				cellMoNaDyn = new PdfPCell(new Paragraph(user.get_termin2(),
						font3));

				// DIENSTAG
				cellDiVoDyn = new PdfPCell(new Paragraph(user.get_termin3(),
						font3));
				cellDiNaDyn = new PdfPCell(new Paragraph(user.get_termin4(),
						font3));

				// MITTWOCH
				cellMiVoDyn = new PdfPCell(new Paragraph(user.get_termin5(),
						font3));
				cellMiNaDyn = new PdfPCell(new Paragraph(user.get_termin6(),
						font3));

				// DONNERSTAG
				cellDoVoDyn = new PdfPCell(new Paragraph(user.get_termin7(),
						font3));
				cellDoNaDyn = new PdfPCell(new Paragraph(user.get_termin8(),
						font3));

				// FREITAG
				cellFrVoDyn = new PdfPCell(new Paragraph(user.get_termin9(),
						font3));
				cellFrNaDyn = new PdfPCell(new Paragraph(user.get_termin10(),
						font3));

				tableCont.addCell(cellTimeVo);
				tableCont.addCell(cellMoVoDyn);
				tableCont.addCell(cellDiVoDyn);
				tableCont.addCell(cellMiVoDyn);
				tableCont.addCell(cellDoVoDyn);
				tableCont.addCell(cellFrVoDyn);

				tableCont.addCell(cellTimeNa);
				tableCont.addCell(cellMoNaDyn);
				tableCont.addCell(cellDiNaDyn);
				tableCont.addCell(cellMiNaDyn);
				tableCont.addCell(cellDoNaDyn);
				tableCont.addCell(cellFrNaDyn);
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
		return "courses";
	}

	/**
	 * Exportfunktion einer PDF-Datei für den Kurs-Stundenplan der Klasse eines
	 * Lehrers
	 * 
	 * @return Facelet "coursebook"
	 * @throws IOException
	 */
	public String pdfDownloadCoursesTimetableTeacher() throws IOException {

		String filename = "Terminplan_Klasse_"
				+ dbh.showUserClass(dbh.getUserId()) + ".pdf";

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

			Document doc = new Document(PageSize.A4.rotate());

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
			String relativeWebPath = "/resources/img/header.jpg";
			ServletContext servletContext = (ServletContext) ec.getContext();
			String absoluteDiskPath = servletContext
					.getRealPath(relativeWebPath);

			Image headerImage = Image.getInstance(absoluteDiskPath);
			headerImage.scaleToFit(500, 150);

			// List<User> anlegen
			List<User> users = dbh.listClassesTeacherSchedule(dbh.getUserId());

			// Tabellen-Objekt anlegen
			PdfPTable tableHead = new PdfPTable(7); // 7 Spalten
			PdfPTable tableCont = new PdfPTable(7); // 7 Spalten
			tableHead.setWidthPercentage(100);
			tableCont.setWidthPercentage(100);
			tableHead.setSpacingBefore(10f);

			// cell.setBorder(Rectangle.NO_BORDER); // removes border

			doc.open(); // Dokument beginnen

			doc.add(headerImage);
			doc.add(new Paragraph("Terminplan der Klasse: ", font1));
			doc.add(new Paragraph(dbh.showUserClass(dbh.getUserId()), font2));

			doc.add(new Paragraph("Zeitplan", font1));
			// statischen Kopf der Tabelle erzeugen

			PdfPCell cellVname = new PdfPCell(new Paragraph("Vorname", font4));
			PdfPCell cellNname = new PdfPCell(new Paragraph("Nachame", font4));

			PdfPCell cellMo = new PdfPCell(new Paragraph("Montag", font4));
			PdfPCell cellDi = new PdfPCell(new Paragraph("Dienstag", font4));
			PdfPCell cellMi = new PdfPCell(new Paragraph("Mittwoch", font4));
			PdfPCell cellDo = new PdfPCell(new Paragraph("Donnerstag", font4));
			PdfPCell cellFr = new PdfPCell(new Paragraph("Freitag", font4));

			cellVname.setBorder(Rectangle.NO_BORDER);
			cellNname.setBorder(Rectangle.NO_BORDER);
			cellMo.setBorder(Rectangle.NO_BORDER);
			cellDi.setBorder(Rectangle.NO_BORDER);
			cellMi.setBorder(Rectangle.NO_BORDER);
			cellDo.setBorder(Rectangle.NO_BORDER);
			cellFr.setBorder(Rectangle.NO_BORDER);

			cellVname.setBorderWidthBottom(1f);
			cellNname.setBorderWidthBottom(1f);
			cellMo.setBorderWidthBottom(1f);
			cellDi.setBorderWidthBottom(1f);
			cellMi.setBorderWidthBottom(1f);
			cellDo.setBorderWidthBottom(1f);
			cellFr.setBorderWidthBottom(1f);

			tableHead.addCell(cellVname);
			tableHead.addCell(cellNname);
			tableHead.addCell(cellMo);
			tableHead.addCell(cellDi);
			tableHead.addCell(cellMi);
			tableHead.addCell(cellDo);
			tableHead.addCell(cellFr);

			doc.add(tableHead); // Tabellenkopf hinzufügen

			for (User user : users) {

				// Initialisierung
				PdfPCell cellVnameVoDyn;
				PdfPCell cellNnameVoDyn;

				// Leerstrings
				PdfPCell cellVnameNaDyn = new PdfPCell(
						new Paragraph(" ", font3));
				PdfPCell cellNnameNaDyn = new PdfPCell(
						new Paragraph(" ", font3));

				PdfPCell cellMoVoDyn;
				PdfPCell cellDiVoDyn;
				PdfPCell cellMiVoDyn;
				PdfPCell cellDoVoDyn;
				PdfPCell cellFrVoDyn;

				PdfPCell cellMoNaDyn;
				PdfPCell cellDiNaDyn;
				PdfPCell cellMiNaDyn;
				PdfPCell cellDoNaDyn;
				PdfPCell cellFrNaDyn;

				// USER DATEN
				cellVnameVoDyn = new PdfPCell(new Paragraph(user.get_vorname(),
						font3));
				cellNnameVoDyn = new PdfPCell(new Paragraph(
						user.get_nachname(), font3));

				// MONTAG
				cellMoVoDyn = new PdfPCell(new Paragraph(user.get_termin1(),
						font3));
				cellMoNaDyn = new PdfPCell(new Paragraph(user.get_termin2(),
						font3));

				// DIENSTAG
				cellDiVoDyn = new PdfPCell(new Paragraph(user.get_termin3(),
						font3));
				cellDiNaDyn = new PdfPCell(new Paragraph(user.get_termin4(),
						font3));

				// MITTWOCH
				cellMiVoDyn = new PdfPCell(new Paragraph(user.get_termin5(),
						font3));
				cellMiNaDyn = new PdfPCell(new Paragraph(user.get_termin6(),
						font3));

				// DONNERSTAG
				cellDoVoDyn = new PdfPCell(new Paragraph(user.get_termin7(),
						font3));
				cellDoNaDyn = new PdfPCell(new Paragraph(user.get_termin8(),
						font3));

				// FREITAG
				cellFrVoDyn = new PdfPCell(new Paragraph(user.get_termin9(),
						font3));
				cellFrNaDyn = new PdfPCell(new Paragraph(user.get_termin10(),
						font3));

				// Rahmen entfernen
				// vormittags
				cellVnameVoDyn.setBorder(Rectangle.NO_BORDER);
				cellNnameVoDyn.setBorder(Rectangle.NO_BORDER);

				cellMoVoDyn.setBorder(Rectangle.NO_BORDER);
				cellDiVoDyn.setBorder(Rectangle.NO_BORDER);
				cellMiVoDyn.setBorder(Rectangle.NO_BORDER);
				cellDoVoDyn.setBorder(Rectangle.NO_BORDER);
				cellFrVoDyn.setBorder(Rectangle.NO_BORDER);

				// nahcmittags
				cellVnameNaDyn.setBorder(Rectangle.NO_BORDER);
				cellNnameNaDyn.setBorder(Rectangle.NO_BORDER);

				cellMoNaDyn.setBorder(Rectangle.NO_BORDER);
				cellDiNaDyn.setBorder(Rectangle.NO_BORDER);
				cellMiNaDyn.setBorder(Rectangle.NO_BORDER);
				cellDoNaDyn.setBorder(Rectangle.NO_BORDER);
				cellFrNaDyn.setBorder(Rectangle.NO_BORDER);

				// TRENNER
				// Name User
				cellNnameVoDyn.setBorderWidthRight(3f);
				cellNnameNaDyn.setBorderWidthRight(3f);

				// kompl. User
				cellVnameNaDyn.setBorderWidthBottom(2f);
				cellNnameNaDyn.setBorderWidthBottom(2f);
				cellMoNaDyn.setBorderWidthBottom(2f);
				cellDiNaDyn.setBorderWidthBottom(2f);
				cellMiNaDyn.setBorderWidthBottom(2f);
				cellDoNaDyn.setBorderWidthBottom(2f);
				cellFrNaDyn.setBorderWidthBottom(2f);

				// kompl. User ohne Namen
				cellMoVoDyn.setBorderWidthBottom(1f);
				cellMoVoDyn.setBorderColor(BaseColor.BLUE);
				cellDiVoDyn.setBorderWidthBottom(1f);
				cellDiVoDyn.setBorderColor(BaseColor.BLUE);
				cellMiVoDyn.setBorderWidthBottom(1f);
				cellMiVoDyn.setBorderColor(BaseColor.BLUE);
				cellDoVoDyn.setBorderWidthBottom(1f);
				cellDoVoDyn.setBorderColor(BaseColor.BLUE);
				cellFrVoDyn.setBorderWidthBottom(1f);
				cellFrVoDyn.setBorderColor(BaseColor.BLUE);

				// Tabelleninhalte hinzufügen
				// vormittags
				tableCont.addCell(cellVnameVoDyn);
				tableCont.addCell(cellNnameVoDyn);

				tableCont.addCell(cellMoVoDyn);
				tableCont.addCell(cellDiVoDyn);
				tableCont.addCell(cellMiVoDyn);
				tableCont.addCell(cellDoVoDyn);
				tableCont.addCell(cellFrVoDyn);

				// nachmittags
				tableCont.addCell(cellVnameNaDyn);
				tableCont.addCell(cellNnameNaDyn);

				tableCont.addCell(cellMoNaDyn);
				tableCont.addCell(cellDiNaDyn);
				tableCont.addCell(cellMiNaDyn);
				tableCont.addCell(cellDoNaDyn);
				tableCont.addCell(cellFrNaDyn);

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
		return "courses";
	}

	/**
	 * Exportfunktion einer PDF-Datei für den Kurs-Stundenplan aller Schüler
	 * nach Klassen sortiert
	 * 
	 * @return Facelet "coursebook"
	 * @throws IOException
	 */
	public String pdfDownloadCoursesTimetableAdmin() throws IOException {

		String filename = "Terminplan_Alle.pdf";

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

			Document doc = new Document(PageSize.A4.rotate());

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
			String relativeWebPath = "/resources/img/header.jpg";
			ServletContext servletContext = (ServletContext) ec.getContext();
			String absoluteDiskPath = servletContext
					.getRealPath(relativeWebPath);

			Image headerImage = Image.getInstance(absoluteDiskPath);
			headerImage.scaleToFit(500, 150);

			// List<User> anlegen
			List<User> users = dbh.listClassesSchedule();

			// Tabellen-Objekt anlegen
			PdfPTable tableHead = new PdfPTable(8); // 8 Spalten
			PdfPTable tableCont = new PdfPTable(8); // 8 Spalten
			tableHead.setWidthPercentage(100);
			tableCont.setWidthPercentage(100);
			tableHead.setSpacingBefore(10f);

			doc.open(); // Dokument beginnen

			doc.add(headerImage);
			doc.add(new Paragraph("Terminplan aller Klasse ", font1));

			doc.add(new Paragraph("Zeitplan", font1));
			// statischen Kopf der Tabelle erzeugen

			PdfPCell cellVname = new PdfPCell(new Paragraph("Vorname", font4));
			PdfPCell cellNname = new PdfPCell(new Paragraph("Nachame", font4));
			PdfPCell cellKlasse = new PdfPCell(new Paragraph("Klasse", font4));

			PdfPCell cellMo = new PdfPCell(new Paragraph("Montag", font4));
			PdfPCell cellDi = new PdfPCell(new Paragraph("Dienstag", font4));
			PdfPCell cellMi = new PdfPCell(new Paragraph("Mittwoch", font4));
			PdfPCell cellDo = new PdfPCell(new Paragraph("Donnerstag", font4));
			PdfPCell cellFr = new PdfPCell(new Paragraph("Freitag", font4));

			cellVname.setBorder(Rectangle.NO_BORDER);
			cellNname.setBorder(Rectangle.NO_BORDER);
			cellKlasse.setBorder(Rectangle.NO_BORDER);
			cellMo.setBorder(Rectangle.NO_BORDER);
			cellDi.setBorder(Rectangle.NO_BORDER);
			cellMi.setBorder(Rectangle.NO_BORDER);
			cellDo.setBorder(Rectangle.NO_BORDER);
			cellFr.setBorder(Rectangle.NO_BORDER);

			cellVname.setBorderWidthBottom(1f);
			cellNname.setBorderWidthBottom(1f);
			cellKlasse.setBorderWidthBottom(1f);
			cellMo.setBorderWidthBottom(1f);
			cellDi.setBorderWidthBottom(1f);
			cellMi.setBorderWidthBottom(1f);
			cellDo.setBorderWidthBottom(1f);
			cellFr.setBorderWidthBottom(1f);

			tableHead.addCell(cellVname);
			tableHead.addCell(cellNname);
			tableHead.addCell(cellKlasse);
			tableHead.addCell(cellMo);
			tableHead.addCell(cellDi);
			tableHead.addCell(cellMi);
			tableHead.addCell(cellDo);
			tableHead.addCell(cellFr);

			doc.add(tableHead); // Tabellenkopf hinzufügen

			for (User user : users) {

				// Namen
				// Initialisierung vormittags
				PdfPCell cellVnameVoDyn;
				PdfPCell cellNnameVoDyn;
				PdfPCell cellKlasseVoDyn;

				// Leerstrings nachmittags
				PdfPCell cellVnameNaDyn = new PdfPCell(
						new Paragraph(" ", font3));
				PdfPCell cellNnameNaDyn = new PdfPCell(
						new Paragraph(" ", font3));
				PdfPCell cellKlasseNaDyn = new PdfPCell(new Paragraph(" ",
						font3));

				// vormittags
				PdfPCell cellMoVoDyn;
				PdfPCell cellDiVoDyn;
				PdfPCell cellMiVoDyn;
				PdfPCell cellDoVoDyn;
				PdfPCell cellFrVoDyn;

				// nachmittags
				PdfPCell cellMoNaDyn;
				PdfPCell cellDiNaDyn;
				PdfPCell cellMiNaDyn;
				PdfPCell cellDoNaDyn;
				PdfPCell cellFrNaDyn;

				// USER DATEN
				cellVnameVoDyn = new PdfPCell(new Paragraph(user.get_vorname(),
						font3));
				cellNnameVoDyn = new PdfPCell(new Paragraph(
						user.get_nachname(), font3));
				cellKlasseVoDyn = new PdfPCell(new Paragraph(user.get_klasse(),
						font3));

				// MONTAG
				cellMoVoDyn = new PdfPCell(new Paragraph(user.get_termin1(),
						font3));
				cellMoNaDyn = new PdfPCell(new Paragraph(user.get_termin2(),
						font3));

				// DIENSTAG
				cellDiVoDyn = new PdfPCell(new Paragraph(user.get_termin3(),
						font3));
				cellDiNaDyn = new PdfPCell(new Paragraph(user.get_termin4(),
						font3));

				// MITTWOCH
				cellMiVoDyn = new PdfPCell(new Paragraph(user.get_termin5(),
						font3));
				cellMiNaDyn = new PdfPCell(new Paragraph(user.get_termin6(),
						font3));

				// DONNERSTAG
				cellDoVoDyn = new PdfPCell(new Paragraph(user.get_termin7(),
						font3));
				cellDoNaDyn = new PdfPCell(new Paragraph(user.get_termin8(),
						font3));

				// FREITAG
				cellFrVoDyn = new PdfPCell(new Paragraph(user.get_termin9(),
						font3));
				cellFrNaDyn = new PdfPCell(new Paragraph(user.get_termin10(),
						font3));

				// Rahmen entfernen
				// vormittags
				cellVnameVoDyn.setBorder(Rectangle.NO_BORDER);
				cellNnameVoDyn.setBorder(Rectangle.NO_BORDER);
				cellKlasseVoDyn.setBorder(Rectangle.NO_BORDER);

				cellMoVoDyn.setBorder(Rectangle.NO_BORDER);
				cellDiVoDyn.setBorder(Rectangle.NO_BORDER);
				cellMiVoDyn.setBorder(Rectangle.NO_BORDER);
				cellDoVoDyn.setBorder(Rectangle.NO_BORDER);
				cellFrVoDyn.setBorder(Rectangle.NO_BORDER);

				// nahcmittags
				cellVnameNaDyn.setBorder(Rectangle.NO_BORDER);
				cellNnameNaDyn.setBorder(Rectangle.NO_BORDER);
				cellKlasseNaDyn.setBorder(Rectangle.NO_BORDER);

				cellMoNaDyn.setBorder(Rectangle.NO_BORDER);
				cellDiNaDyn.setBorder(Rectangle.NO_BORDER);
				cellMiNaDyn.setBorder(Rectangle.NO_BORDER);
				cellDoNaDyn.setBorder(Rectangle.NO_BORDER);
				cellFrNaDyn.setBorder(Rectangle.NO_BORDER);

				// TRENNER
				// Name User
				cellKlasseVoDyn.setBorderWidthRight(3f);
				cellKlasseNaDyn.setBorderWidthRight(3f);

				// kompl. User
				cellVnameNaDyn.setBorderWidthBottom(2f);
				cellNnameNaDyn.setBorderWidthBottom(2f);
				cellKlasseNaDyn.setBorderWidthBottom(2f);
				cellMoNaDyn.setBorderWidthBottom(2f);
				cellDiNaDyn.setBorderWidthBottom(2f);
				cellMiNaDyn.setBorderWidthBottom(2f);
				cellDoNaDyn.setBorderWidthBottom(2f);
				cellFrNaDyn.setBorderWidthBottom(2f);

				// kompl. User ohne Namen
				cellMoVoDyn.setBorderWidthBottom(1f);
				cellMoVoDyn.setBorderColor(BaseColor.BLUE);
				cellDiVoDyn.setBorderWidthBottom(1f);
				cellDiVoDyn.setBorderColor(BaseColor.BLUE);
				cellMiVoDyn.setBorderWidthBottom(1f);
				cellMiVoDyn.setBorderColor(BaseColor.BLUE);
				cellDoVoDyn.setBorderWidthBottom(1f);
				cellDoVoDyn.setBorderColor(BaseColor.BLUE);
				cellFrVoDyn.setBorderWidthBottom(1f);
				cellFrVoDyn.setBorderColor(BaseColor.BLUE);

				// Tabelleninhalte hinzufügen
				// vormittags
				tableCont.addCell(cellVnameVoDyn);
				tableCont.addCell(cellNnameVoDyn);
				tableCont.addCell(cellKlasseVoDyn);

				tableCont.addCell(cellMoVoDyn);
				tableCont.addCell(cellDiVoDyn);
				tableCont.addCell(cellMiVoDyn);
				tableCont.addCell(cellDoVoDyn);
				tableCont.addCell(cellFrVoDyn);

				// nachmittags
				tableCont.addCell(cellVnameNaDyn);
				tableCont.addCell(cellNnameNaDyn);
				tableCont.addCell(cellKlasseNaDyn);

				tableCont.addCell(cellMoNaDyn);
				tableCont.addCell(cellDiNaDyn);
				tableCont.addCell(cellMiNaDyn);
				tableCont.addCell(cellDoNaDyn);
				tableCont.addCell(cellFrNaDyn);

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
		return "courses";
	}

	/**
	 * Exportfunktion einer PDF-Datei für einen neu angelegten Schüler
	 * 
	 * @return Facelet "coursebook"
	 * @throws IOException
	 */
	public String pdfDownloadCoursebookStudent() throws IOException {

		String filename = "Kursbuch_" + dbh.getUserFirstname(dbh.getUserId())
				+ ".pdf";

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
			String relativeWebPath = "/resources/img/header.jpg";
			ServletContext servletContext = (ServletContext) ec.getContext();
			String absoluteDiskPath = servletContext
					.getRealPath(relativeWebPath);

			Image headerImage = Image.getInstance(absoluteDiskPath);
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
	 * Exportfunktion einer PDF-Datei für das Kursbuch eines Schülers
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
			String relativeWebPath = "/resources/img/header.jpg";
			ServletContext servletContext = (ServletContext) ec.getContext();
			String absoluteDiskPath = servletContext
					.getRealPath(relativeWebPath);

			Image headerImage = Image.getInstance(absoluteDiskPath);
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

}
