package bookstoremanagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/LogInCheck")
public class LogInCheck extends HttpServlet {
	@Override
	public void doPost(HttpServletRequest request , HttpServletResponse response) throws ServletException,IOException{
		String email = request.getParameter("Username");
		String pass = request.getParameter("password");
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "store", "store");
			Statement st = con.createStatement();
			String query = "SELECT * FROM USERS WHERE EMAIL = '"+email+"' AND PASS='"+pass+"'";
			ResultSet rs = st.executeQuery(query);
			RequestDispatcher view = null;
			if(!rs.next()) {
//				Wrong....
				view = request.getRequestDispatcher("/passwrong.html");
				view.forward(request, response);
			}else {
				String payment = "<!DOCTYPE html>\r\n" + 
						"<html>\r\n" + 
						"    <head>\r\n" + 
						"        <title>\r\n" + 
						"            Payment\r\n" + 
						"        </title>\r\n" + 
						"    </head>\r\n" + 
						"    <body style=\"background:#FFFFCC \">\r\n" + 
						"        <h1>Payment</h1>\r\n" + 
						"        <center>\r\n" + 
						"            <div style=\" margin: auto;\r\n" + 
						"            width: 60%;\r\n" + 
						"            border: 3px solid #73AD21;\r\n" + 
						"            padding: 10px;\">\r\n" + 
						"            <form action=\"Dispatch\" method=\"POST\">\r\n" + 
						"            Credit Card <input type=\"number\" maxlength=\"16\" required>\r\n" + 
						"            <p></p>\r\n" + 
						"            Card on Name <input type=\"text\" maxlength=\"10\" required>\r\n" + 
						"            <p></p>\r\n" + 
						"            Expiry Date <input type=\"date\" required>\r\n" + 
						"            <p></p>\r\n" + 
						"            CVV <input type=\"password\" maxlength=\"3\" required>\r\n" + 
						"            <p></p>\r\n" + 
						"            \r\n" + 
						"                <input type=\"submit\" value=\"Pay\">\r\n" + 
						"            </form>\r\n" + 
						"        </div>\r\n" + 
						"        </center>\r\n" + 
						"    </body>\r\n" + 
						"</html>";
				PrintWriter pw = response.getWriter();
				response.setContentType("text/html");
				pw.println(payment);
			}
		}catch(ClassNotFoundException e) {
			
		}catch(SQLException e) {
			
		}
	}
}
