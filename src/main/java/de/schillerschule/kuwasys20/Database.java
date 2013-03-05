package de.schillerschule.kuwasys20;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.*;
import javax.faces.event.ComponentSystemEvent;

 
@SuppressWarnings("unused")
@ManagedBean(name = "database", eager = true)
@SessionScoped
public class Database implements Serializable {


   private static final long serialVersionUID = 1L;

   public String getUserFullName(){
	   FacesContext fc = FacesContext.getCurrentInstance();
	   ExternalContext externalContext = fc.getExternalContext();
      ResultSet rs = null;
      PreparedStatement pst = null;
      Connection con = getConnection();
      String stm = "Select users_nachname,users_vorname from users where users_username='" + externalContext.getUserPrincipal().getName() + "'";
      String vorName="";
      String nachName="";
      try {   
         pst = con.prepareStatement(stm);
         pst.execute();
         rs = pst.getResultSet();

         while(rs.next()){
        	
            nachName=rs.getString(1);
            vorName=rs.getString(2);
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return vorName+" "+nachName;
   }

   public Connection getConnection(){
      Connection con = null;

      String url = "jdbc:postgresql://localhost/kuwasys";
      String user = "ijcy";
      String password = "12kuwasys34";
      try {
    	 Class.forName("org.postgresql.Driver");
         con = DriverManager.getConnection(url, user, password);
         System.out.println("Connection completed.");
      } catch (SQLException ex) {
         System.out.println(ex.getMessage());
      } catch (ClassNotFoundException e) {
		 System.out.println(e.getMessage());
		e.printStackTrace();
      }
      finally{
      }
      return con;
   }
}