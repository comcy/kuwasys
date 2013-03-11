package de.schillerschule.kuwasys20.User;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean(name = "exportBean")
@RequestScoped
public class ExportBean implements Serializable {

	private static final long serialVersionUID = 1L;

	public String download() {

		String filename = "download.pdf";

		try {
			FacesContext fc = FacesContext.getCurrentInstance();

			ExternalContext ec = fc.getExternalContext();

			ec.responseReset();
			ec.setResponseContentType("text/plain");
			ec.setResponseContentLength(1000);
			ec.setResponseHeader("Content-Disposition",
					"attachment; filename=\"" + filename + "\"");

			OutputStream output = ec.getResponseOutputStream();
			// TODO the Output

			fc.responseComplete();
		} catch (IOException ex) {
			System.out.println("File Export Error: " + ex);

		}
		return "users";
	}
}
