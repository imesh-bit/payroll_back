/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dao.BeanSystemLog;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperRunManager;
import org.json.simple.JSONObject;
import util.JDBC;
import util.JWTTokenGenerator;

/**
 *
 * @author Nimesha
 */
@WebServlet(name = "ControllerSalarySlip", urlPatterns = {"/ControllerSalarySlip"})
public class ControllerSalarySlip extends HttpServlet {

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
        // response.setContentType("text/html;charset=UTF-8");

        String requestTokenHeader = request.getHeader("Authorization");
        System.out.println("requestTokenHeader " + requestTokenHeader);
        JWTTokenGenerator jwt = new JWTTokenGenerator();

        try ( Connection connection = JDBC.con_payroll();) {
// PrintWriter out = response.getWriter();  
            if (jwt.validateToken(requestTokenHeader)) {
                if (request.getParameter("printslip") != null) {
                    String empid = "";
                    String monthcode = request.getParameter("monthcode");
                    String usergroup = request.getParameter("usergroup");
                    if (usergroup.equalsIgnoreCase("Payslip User")) {
                        empid = String.valueOf(BeanSystemLog.getEmpid());
                    } else {
                        empid = request.getParameter("empid");
                    }

                    //ServletOutputStream outStream = response.getOutputStream();
                    File reportFile = new File(getServletContext().getRealPath("/reports/payslip.jasper"));

                    Map<String, Object> params = new HashMap<>();
                    params.put("empid", empid);
                    params.put("monthcode", monthcode);

                    System.out.println("empid " + empid);
                    System.out.println("monthcode " + monthcode);
                    byte[] bytes = JasperRunManager.runReportToPdf(reportFile.getPath(), params, connection);

                    //  response.setHeader("Content-Disposition", "inline; filename=\"" + reportFile.getName() + "\";");
                    response.setContentType("application/pdf");
                    response.setContentLength(bytes.length);
                    response.getOutputStream().write(bytes, 0, bytes.length);
                    response.getOutputStream().flush();
                    response.getOutputStream().close();

                }
            } else {
                JSONObject jsa = new JSONObject();
                jsa.put("message", "Invalid token");

                response.setContentType("application/json");
                response.getWriter().print(jsa);
            }

        } catch (Exception e) {
            e.printStackTrace();
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
