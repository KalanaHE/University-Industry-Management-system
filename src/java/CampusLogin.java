
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet(urlPatterns = {"/CampusLogin"})
public class CampusLogin extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
               Connection con=null;
              PreparedStatement pst=null;
              ResultSet rs=null;
              
           try{
           
             Class.forName("com.mysql.jdbc.Driver");
            con=(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/webapp","root",""); 
           
            String cam_name=request.getParameter("cam_name");
            String password=request.getParameter("password");
            String rem=request.getParameter("rem");
            
             String sql="SELECT cam_pass  FROM updatetable WHERE cam_name='"+cam_name+"'";
         
               pst=con.prepareStatement(sql);
                rs = pst.executeQuery();
                
                while(rs.next()){
                
                if(rs.getString(1).equals(password)){
                    
                     if(rem=="rem"){
                    
                      Cookie c1=new Cookie("userName",cam_name);
                    Cookie c2=new Cookie("password",password);
                    
                    c1.setMaxAge(60*60*24*7);
                    c2.setMaxAge(60*60*24*7);
                    
                    response.addCookie(c1);
                    response.addCookie(c2);
                    
                     }
                    
                    HttpSession session1=request.getSession(true);  
                     session1.setAttribute("cam_name",cam_name);  
                     
                     response.sendRedirect("Campus/CampusPage.jsp"); 
                                }
               
                }
                
                response.sendRedirect("Campus/CampusLogin.html"); 
                 
                     
                
            
           }
           catch(Exception e){
               
           }
            
            
        }
    }

            
        
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
