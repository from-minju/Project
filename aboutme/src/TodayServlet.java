

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

/**
 * Servlet implementation class TodayServlet
 */
@WebServlet("/today")
public class TodayServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public TodayServlet() {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head><title>시간정보</title></head>");
		out.println("<body>");
		out.println("<a href=\"http://localhost:8080/aboutme/index.html\">메인화면</a>");
		LocalDateTime dateTime = LocalDateTime.now();
		
		out.println("<h1>현재시간 : " + dateTime + "</h1>");
		
		out.println("</body></html>");
		
	}


}
