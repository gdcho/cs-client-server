import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.*;

public class HitServlet extends HttpServlet {
  private int mCount;
 
  public void doGet(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {
      

      HttpSession session = request.getSession(true);
      if (request.getParameterMap().containsKey("userid")) {
           
                 session.setAttribute("userid", request.getParameter("userid"));
                 session.setAttribute("count", 0);
           
      }
      
      String userid = (String)session.getAttribute("userid");           
      // Set response content type.
      response.setContentType("text/html");
      response.setCharacterEncoding("UTF-8");
 
      PrintWriter out = response.getWriter();

      if ( userid == null) {  
        out.println("<html>\n" +
                "<body>\n" + 
                "<form action=\"/midp/hits\" method=\"GET\">\n" +
                "First Name: <input type=\"text\" name=\"userid\">\n"   +
                "<br />\n" +
                "Last Name: <input type=\"text\" name=\"password\" />\n"   +
                "<input type=\"submit\" value=\"Submit\" />\n"
                + 
                "</form>\n</body>\n</html\n");
      } else {
          Integer count = (Integer)session.getAttribute("count");
          count = count + 1;
          session.setAttribute("count", count);         
          out.println("<html>");
          out.println("<meta charset='UTF-8'>");
          out.println("<body>");
          out.println("<p>You are logged in as: " + userid.toUpperCase() + "</p>");
          out.println("<div>");
          out.println("<form action='/midp/hits' method='GET'>");
out.println("<div class='image'>");
          if (count == 1 ) {
               out.println("<img id='changeImg' src='./images/Spider-Man.png' alt='Spider-Man' height='42' width='42'>");
          } else {
              out.println("<img id='changeImg' src='./images/blah.jpg' alt='Spider-Man' height='42' width='42'>");
          }
          out.println("<div class='changeImg'>");
          out.println("<br>");
          out.println("<div class='button'>");          
          out.println("<button class='button' id='prev'>Prev</button>");
          out.println("<button class='button' id='next'>Next</button>");
          out.println("</div></div><br>");
          out.println("</form>");
          out.println("</body></html>");
          out.close();
      }
   }
}