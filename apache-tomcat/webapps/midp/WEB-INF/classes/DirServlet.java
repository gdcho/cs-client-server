import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.*;
public class DirServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String topPart = "<!DOCTYPE html><html><body><ul>";
        String bottomPart = "</ul></body></html>";
        // out.println(topPart+getListing("C:\\" + request.getParameter("path"))+bottomPart);
        String basePath = System.getProperty("user.home") + File.separator + request.getParameter("path");
        out.println(topPart + getListing(basePath) + bottomPart);
    }
    private String getListing(String path) {
        //   String dirList =  null;
        StringBuilder dirList = new StringBuilder();
        File dir = new File(path);
//       String[] chld = dir.list();
//       for(int i = 0; i < chld.length; i++){
//          if ((new File(path+chld[i])).isDirectory())
//             dirList += "<li><button type=\"button\">"+chld[i]+"</button></li>";
//          else
//             dirList += "<li>"+chld[i]+"</li>";
//       }
//       return dirList;
//   }
// Check if the directory exists and is indeed a directory
        if (!dir.exists() || !dir.isDirectory()) {
            return "<li>Invalid directory</li>";
        }

        String[] chld = dir.list();
        if (chld != null) {
            for (String name : chld) {
                File file = new File(dir, name);
                if (file.isDirectory()) {
                    // If it's a directory, display it with a button
                    dirList.append("<li><button type=\"button\">").append(name).append("</button></li>");
                } else {
                    // If it's a file, display it normally
                    dirList.append("<li>").append(name).append("</li>");
                }
            }
        } else {
            // Handle the case where dir.list() returns null
            dirList.append("<li>Error reading directory</li>");
        }

        return dirList.toString();
    }
}
