/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import operations.dbOperations;

/**
 *
 * @author shibo
 */
public class sqlTool extends HttpServlet {

    @Override
    public void init() throws ServletException {
        dbOperations d = new dbOperations();
        d.init();
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet sqlTool</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet sqlTool at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String command = request.getParameter("command");
            System.out.println("kjjjjjjjjjjjjjjjjjjjjjjjjjjjjj" + command);
            dbOperations d = new dbOperations();
            //null;
            Object rs = d.run(command);
           
           
            PrintWriter out = response.getWriter();
           
            out.print("<form action=\"sqlTool\" >\n"
                            + "             <textarea rows=\"20\" cols=\"100\" name=\"command\">\n"
                            + "               \n"
                            + "</textarea> \n"
                            + "            <input type=\"submit\" />\n"
                            + "            \n"
                            + "        </form>");
            if (rs instanceof ArrayList) {
                out.print("dksjkdjskdjs"+rs);
                
               ArrayList<Person> rs2= (ArrayList<Person>) rs;
                    int rowCount = 0;
                    
                    
                    out.println("<P ALIGN='center'><TABLE BORDER=1>");
                   
                    // the data
                    for (int i = 0; i < rs2.size(); i++) {
                       Person p=new Person();
                       p=rs2.get(i);
                        out.println("<TR>");
                     out.println("<TD>" + p.getEmail() + "</TD>");
                     out.println("<TD>" + p.getUsername() + "</TD>");
                     out.println("<TD>" + p.getPassword() + "</TD>");
                   
                        out.println("</TR>");
                      
                        }
                        
                    
                    out.println("</TABLE></P>");

                    out.print("</table>");
               

            } else if (rs instanceof Integer) {
                
                out.print("" + rs + " rows updated ");

            } else {

                out.print("error command");
            }
        } catch (SQLException ex) {
            Logger.getLogger(sqlTool.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

 

}
