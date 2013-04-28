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
import de.schillerschule.kuwasys20.User.UserBean.User;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
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
	public String CSVDownloadStudents() {

		String filename = "download.csv";

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
	public String CSVDownloadCourses() {

		String filename = "download.csv";

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
	 * Exportfunktion einer CSV-Datei für die gesamte Kursliste TODO Tests wegen
	 * nicht- und aktivierten Kursen
	 * 
	 * @return Facelet "courses"
	 */
	public String CSVDownloadClass() {

		String filename = "download.csv";

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
	 * Exportfunktion einer PDF-Datei für ...
	 * 
	 * @return Facelet "users"
	 * @throws IOException
	 */
	public String pdfDownloadUser() throws IOException {


		String filename = "download.pdf";
		
		try {

			FacesContext fc = FacesContext.getCurrentInstance();
			ExternalContext ec = fc.getExternalContext();
			OutputStream os = ec.getResponseOutputStream();

			// Dateiinformationen setzen
			ec.responseReset();
			ec.setResponseContentType("application/pdf");
			ec.setResponseCharacterEncoding("utf-8");
			ec.setResponseHeader("Expires", "0");
	        ec.setResponseHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
	        ec.setResponseHeader("Pragma", "public");
			ec.setResponseHeader("Content-Disposition",
					"attachment; filename=\"" + filename + "\"");
			
			Document doc = new Document();

			PdfWriter writer = PdfWriter.getInstance(doc, os);

			// Dokument beginnen
			doc.open();

			List<User> usersTeacher = dbh.listClassesTeacher(dbh.getUserId());
			List<User> users = dbh.listUsers();
			
			// TODO PDF Erstellung - externe PDF Klasse schreiben
			
			doc.add(new Paragraph("Passwortliste für Lehrer:"));
			for (User userTeacher : usersTeacher) {
				doc.add(new Paragraph(userTeacher.get_vorname() + " " + userTeacher.get_nachname()));
				for (User user : users) {
					doc.add(new Paragraph(user.get_vorname() + " " + user.get_nachname() + " " + user.get_username() + " " + user.get_passwort()));
				}			
			}
			
			/*PdfContentByte cb = writer.getDirectContent();
			BaseFont bf = BaseFont.createFont();
			// setImage(cb, "img/memory.png", 40);
			cb.beginText();
			cb.setFontAndSize(bf, 12);
			cb.moveText(20, 105);
			cb.showText("Falsches Üben von Xylophonmusik quält jeden größeren Zwerg.");
			cb.moveText(120, -16);
			cb.setCharacterSpacing(2);
			cb.setWordSpacing(12);
			cb.newlineShowText("Erst recht auch jeden kleineren.");
			cb.endText();*/

			// Dokument beenden
			doc.close();

			os.flush();
			os.close();

			
			// "response" abschließen, sonst wird HTML Kontext 
			// der aktuellen Seite in die Datei geschrieben
			fc.responseComplete();
			

		} catch (DocumentException de) {
			System.out.println("Error during PDF creation: " + de);
		} catch (IOException ioe) {
			System.out.println("Error during PDF creation: " + ioe);
		}
		return "users";
	}
}
