/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.ModelLogin;
import org.json.simple.JSONObject;
import util.JDBC;

/**
 *
 * @author Nimesha
 */
@WebServlet(name = "ControllerLogin", urlPatterns = {"/ControllerLogin"})
public class ControllerLogin extends HttpServlet {

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

        try ( PrintWriter out = response.getWriter();  Connection connection = JDBC.con_payroll();) {
               ModelLogin model = new ModelLogin(connection);
            /*check username and password combination you have entered is invalid or invalid*/
            if (request.getParameter("loginCheck") != null) {
                
                String userName = request.getParameter("userName");
                String password = chkmd5value(request.getParameter("password"), connection);
                
             
                JSONObject job = new JSONObject();

                job.put("loginDetails", model.getLoginDetails(request,userName, password));
                out.print(job);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String chkmd5value(String pw, Connection connection) {
        String hashword = null;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT MD5(?)");
            ps.setString(1, pw);
            ResultSet rs = ps.executeQuery();
            rs.next();
            hashword = rs.getString(1);
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hashword;
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
