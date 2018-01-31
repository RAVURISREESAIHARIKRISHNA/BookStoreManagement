package bookstoremanagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Dispatch")
public class Dispatch extends HttpServlet {
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cookie cook[] = request.getCookies();
		String isbn = cook[0].getValue();
		cook[0].setMaxAge(0);
		response.addCookie(cook[0]);
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "store", "store");
			Statement st = con.createStatement();
			String update="UPDATE BOOKS SET QTY = QTY-1 WHERE ISBN='"+isbn+"' AND QTY > 0";
			st.executeUpdate(update);
			String success="<!DOCTYPE html>\r\n" + 
					"<html>\r\n" + 
					"    <head>\r\n" + 
					"        <title>\r\n" + 
					"            Successful\r\n" + 
					"        </title>\r\n" + 
					"    </head>\r\n" + 
					"    <body style=\"background:#FFFFCC \" onload=\"alert('Success');\">\r\n" + 
					"        <h1>Payment Successful !</h1>\r\n" + 
					"        <h3>Check your Mail for updates</h3>\r\n" + 
					"        <a href=\"/Book_Store_Management_System/index.html\">Home</a>\r\n" + 
					"    </body>\r\n" + 
					"</html>";
			PrintWriter pw = response.getWriter();
			response.setContentType("text/html");
			pw.println(success);
		}catch(ClassNotFoundException e) {
			
		}catch(SQLException e) {
			
		}
	}
}
