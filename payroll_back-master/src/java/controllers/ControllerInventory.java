/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.ModelInventory;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import util.JDBC;
import util.JWTTokenGenerator;

/**
 *
 * @author Nimesha
 */
@WebServlet(name = "ControllerInventory", urlPatterns = {"/ControllerInventory"})
public class ControllerInventory extends HttpServlet {

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
                ModelInventory model = new ModelInventory(connection);

                if (request.getParameter("getInventoryList") != null) {
                    int empId = Integer.parseInt(request.getParameter("empId"));

                    response.setContentType("application/json");
                    response.getWriter().print(model.getInventoryList(empId));
                }
                if (request.getParameter("saveInventory") != null) {
                    try {

                        String body = IOUtils.toString(request.getReader());
                        //  System.out.println("body " + body);

                        JsonParser parser = new JsonParser();
                        JsonObject requestJson = (JsonObject) parser.parse(body);

                        JSONObject jsonResponse = model.saveInventory(requestJson, connection);
                        response.setContentType("application/json");
                        out.print(jsonResponse);

                    } catch (Exception e) {
                        out.print("notsaved|-|-|-");
                        e.printStackTrace();
                    }
                }
                if (request.getParameter("deleteInventory") != null) {
                    try {

                        String body = IOUtils.toString(request.getReader());
                        //  System.out.println("body " + body);

                        JsonParser parser = new JsonParser();
                        JsonObject requestJson = (JsonObject) parser.parse(body);

                        JSONObject jsonResponse = model.deleteInventory(requestJson, connection);
                        response.setContentType("application/json");
                        out.print(jsonResponse);

                    } catch (Exception e) {
                        out.print("notsaved|-|-|-");
                        e.printStackTrace();
                    }
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
