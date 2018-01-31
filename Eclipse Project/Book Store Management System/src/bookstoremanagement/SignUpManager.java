package bookstoremanagement;

import java.io.IOException;
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

@WebServlet("/SignUpManager")
public class SignUpManager extends HttpServlet {
	@Override
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		String email = request.getParameter("email");
		String pass = request.getParameter("password");
		String name = request.getParameter("name");
		RequestDispatcher view = null;
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "store", "store");
			Statement st = con.createStatement();
			String query = "SELECT * FROM USERS WHERE EMAIL ='"+email+"'";
			ResultSet rs = st.executeQuery(query);
			if(!rs.next()) {
				String update = "insert into USERS values('"+email+"','"+pass+"','"+name+"')";
				st.executeUpdate(update);
				view = request.getRequestDispatcher("/index.html");
				view.forward(request, response);
			}else {
				view = request.getRequestDispatcher("/signup.html");
				view.forward(request, response);
			}
		}catch(ClassNotFoundException e) {
			
		}catch(SQLException e) {
			
		}
	}
}
