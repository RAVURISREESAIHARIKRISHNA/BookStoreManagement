package bookstoremanagement;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/LogInValidate")
public class LogInValidate extends HttpServlet {
	@Override
	public void doPost(HttpServletRequest request , HttpServletResponse response) throws ServletException,IOException{
		System.out.println(request.getParameter("isbn"));
		Cookie cook = new Cookie("isbn",request.getParameter("isbn"));
		cook.setMaxAge(60*60*24);
		response.addCookie(cook);
		response.setContentType("text/html");
		String html="<!DOCTYPE>\r\n" + 
				"<html>\r\n" + 
				"<head>\r\n" + 
				"<title> login in page</title>\r\n" + 
				"</head>\r\n" + 
				"<body style=\"background:#FFFFCC \" >\r\n" + 
				"<center>\r\n" + 
				"<h1>LogIn</h1>\r\n" + 
				"<P></p>\r\n" + 
				"<form action=\"LogInCheck\" onsubmit=\"return validateSubmit()\" method=\"POST\">\r\n" + 
				"<input type=\"text\" placeholder=\"Username\" name=\"Username\" required><br><br>\r\n" + 
				"<input type=\"password\" placeholder=\"password\" name=\"password\" required><br><br>\r\n" + 
				"<button type=\"submit\">LogIn</button>\r\n" + 
				"</center>\r\n" + 
				"<form >\r\n" + 
				"</form>\r\n" + 
				"</body>\r\n" + 
				"</html>";
		PrintWriter pw = response.getWriter();
		pw.println(html);
	}
}
