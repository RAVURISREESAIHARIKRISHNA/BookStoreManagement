package bookstoremanagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.TreeSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/search")
public class Search extends HttpServlet {
	
	@Override
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		String query = request.getParameter("query");
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "store", "store");
			Statement st = con.createStatement();
			String isbnSearch = "SELECT ISBN FROM BOOKS WHERE ISBN LIKE'%"+query+"%' or BOOK_NAME LIKE '%"+query+"%' or AUTHOR LIKE '%"+query+"%'";
			System.out.println(isbnSearch);
			ResultSet rsisbn = st.executeQuery(isbnSearch);
			TreeSet<String> isbns = new TreeSet<>();
			while(rsisbn.next()) {
				isbns.add(rsisbn.getString("ISBN"));
			}
			Iterator<String> iter = isbns.iterator();
			String getDataFromDB = "SELECT ISBN,BOOK_NAME,AUTHOR,PRICE FROM BOOKS WHERE ISBN in (";
			while(iter.hasNext()) {
				getDataFromDB += "'"+ iter.next() + "'" + ",";
			}
			getDataFromDB = getDataFromDB.substring(0,getDataFromDB.length()-1);
			getDataFromDB += ")";
			ResultSet searchBooks = st.executeQuery(getDataFromDB);
			String html="<!DOCTYPE html>\r\n" + 
					"<html>\r\n" + 
					"    <head>\r\n" + 
					"        <title>Search</title>\r\n" + 
					"    </head>\r\n" + 
					"    <body>\r\n" + 
					"        <center>";
			while(searchBooks.next()) {
				html += "<div>\r\n" + 
						"                <h2>";
				html += searchBooks.getString("BOOK_NAME");
				html += "</h2><font size=\"2\">By ";
				html += searchBooks.getString("AUTHOR");
				html += "<p></p></font>\r\n" + 
						"                ";
				html += "$"+searchBooks.getString("PRICE");
				html += "\r\n" + 
						"                <button id=\"";
				html += searchBooks.getString("ISBN");
				html += "\" onclick=\"clicked(this.id)\">Buy</button>\r\n" + 
						"                <hr>\r\n" + 
						"            </div>";
				
			}
			html +=" </center>\r\n" + 
					"        <script type=\"text/javascript\" src=\"template.js\"></script>\r\n" + 
					"    </body>\r\n" + 
					"</html>";
			PrintWriter pw = response.getWriter();
			response.setContentType("text/html");
			pw.println(html);
		}catch(ClassNotFoundException e) {
			System.out.println("Class Not found");
		}catch(SQLException e) {
			System.out.println("SQLException");
		}
	}
}
