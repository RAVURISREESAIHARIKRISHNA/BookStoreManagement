package bookstoremanagement;

import java.io.IOException;
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
			while(iter.hasNext()) {
				System.out.println(iter.next());
			}
		}catch(ClassNotFoundException e) {
			System.out.println("Class Not found");
		}catch(SQLException e) {
			System.out.println("SQLException");
		}
	}
}
