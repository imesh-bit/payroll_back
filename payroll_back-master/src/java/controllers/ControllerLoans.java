/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dao.BeanSystemLog;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.ModelLoans;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import util.JDBC;
import util.JWTTokenGenerator;

/**
 *
 * @author Nimesha
 */
@WebServlet(name = "ControllerLoans", urlPatterns = {"/ControllerLoans"})
public class ControllerLoans extends HttpServlet {

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

        String requestTokenHeader = request.getHeader("Authorization");
        System.out.println("requestTokenHeader " + requestTokenHeader);
        JWTTokenGenerator jwt = new JWTTokenGenerator();
        try ( PrintWriter out = response.getWriter();  Connection connection = JDBC.con_payroll();) {
            if (jwt.validateToken(requestTokenHeader)) {
                ModelLoans model = new ModelLoans(connection);

                if (request.getParameter("getLoanList") != null) {
                    String setoff = request.getParameter("setoff");
                    String comCode = BeanSystemLog.getComcode();

                    response.setContentType("application/json");
                    response.getWriter().print(model.getLoanList(comCode, setoff.equals("1") ? 1 : 0));

                }
                if (request.getParameter("getSelectedLoan") != null) {

                    int loanid = Integer.parseInt(request.getParameter("loanid"));

                    response.setContentType("application/json");
                    response.getWriter().print(model.getSelectedLoan(loanid));

                }
                if (request.getParameter("getSelectedSettlementList") != null) {

                    int loanid = Integer.parseInt(request.getParameter("loanid"));

                    response.setContentType("application/json");
                    response.getWriter().print(model.getSelectedSettlementList(loanid));

                }
                if (request.getParameter("saveLoan") != null) {
                    try {

                        String body = IOUtils.toString(request.getReader());
                        //  System.out.println("body " + body);

                        JsonParser parser = new JsonParser();
                        JsonObject requestJson = (JsonObject) parser.parse(body);

                        JSONObject jsonResponse = model.saveLoan(requestJson, connection);
                        response.setContentType("application/json");
                        out.print(jsonResponse);
                    } catch (Exception e) {
                        out.print("notsaved|-|-|-");
                        e.printStackTrace();
                    }
                }
                if (request.getParameter("settleLoan") != null) {
                    try {

                        String body = IOUtils.toString(request.getReader());
                        //  System.out.println("body " + body);

                        JsonParser parser = new JsonParser();
                        JsonObject requestJson = (JsonObject) parser.parse(body);

                        JSONObject jsonResponse = model.settleLoan(requestJson, connection);
                        response.setContentType("application/json");
                        out.print(jsonResponse);
                    } catch (Exception e) {
                        out.print("notsaved|-|-|-");
                        e.printStackTrace();
                    }
                }
                if (request.getParameter("getLoanOutstandingList") != null) {
                    int empId = Integer.parseInt(request.getParameter("empId"));
                    String comCode = BeanSystemLog.getComcode();

                    response.setContentType("application/json");
                    response.getWriter().print(model.getLoanOutstandingList(comCode, empId));

                }
            } else {
                JSONObject jsa = new JSONObject();
                jsa.put("message", "Invalid token");

                response.setContentType("application/json");
                out.print(jsa);
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
