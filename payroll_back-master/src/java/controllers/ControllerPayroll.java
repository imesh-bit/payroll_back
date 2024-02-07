/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dao.BeanEmployee;
import dao.BeanSystemLog;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.ModelCreateCust;
import models.ModelPayroll;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import threads.payroll.AutoSaveThread;
import util.JDBC;
import util.JWTTokenGenerator;

/**
 *
 * @author Nimesha
 */
@WebServlet(name = "ControllerPayroll", urlPatterns = {"/ControllerPayroll"})
public class ControllerPayroll extends HttpServlet {

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

                ModelPayroll model = new ModelPayroll(connection);

                if (request.getParameter("getAllPayRollData") != null) {
                    System.out.println("getAllPayRollData");
                    String SalaryMonth = request.getParameter("monthCode");
                    String SalaryType = "Normal";
                    String Company = BeanSystemLog.getComcode();

                    response.setContentType("application/json");
                    response.getWriter().print(model.getPayrollData(connection, SalaryMonth, SalaryType, Company));

                }
                if (request.getParameter("getExcelData") != null) {
                    System.out.println("getExcelData");
                    String SalaryMonth = request.getParameter("monthCode");
                    String SalaryType = "Normal";
                    String Company = BeanSystemLog.getComcode();

                    response.setContentType("application/json");
                    response.getWriter().print(model.getExcelData(connection, SalaryMonth, SalaryType, Company));

                }
                if (request.getParameter("runWizard") != null) {
                    System.out.println("runWizard");
                    JSONObject jsa = new JSONObject();

                    String SalaryMonth = request.getParameter("monthCode");
                    String SalaryType = "Normal";
                    String Company = BeanSystemLog.getComcode();

                    if (model.isFinalized(connection, SalaryMonth, Company)) {
                        jsa.put("type", "error");
                        jsa.put("message", "Error:Payroll already finalized");
                    } else if (model.isAllStopped(connection, SalaryMonth, Company)) {
                        List<BeanEmployee> beansList = model.getSelectedEmployeeListSalary(connection, Company, SalaryType, SalaryMonth);
                        System.out.println("beansList size " + beansList.size());
                        if (beansList.isEmpty()) {
                            jsa.put("type", "error");
                            jsa.put("message", "Error:There is no employees for run payroll wizard");
                        } else {
                            System.out.println("call AutoSaveThread ");
                            AutoSaveThread AutoSaveThread = model.CallAutoSaveThread(beansList, model);
                            AutoSaveThread.join();
                            // double processValue = AutoSaveThread.GetProgressValue();
                            String message = AutoSaveThread.getSaveString();

                            System.out.println("AutoSaveThread Message " + message);

                            if (message.startsWith("Error:")) {
                                jsa.put("type", "error");
                                jsa.put("message", message);
                            } else {
                                jsa.put("type", "sucess");
                                jsa.put("message", message);
                            }

                        }
                    } else {
                        jsa.put("type", "error");
                        jsa.put("message", "Payroll wizard cannot be run,Check the payroll month");
                    }

                    response.setContentType("application/json");
                    out.print(jsa);

                }
                if (request.getParameter("getEmpPayRollData") != null) {
                    String SalaryMonth = request.getParameter("monthCode");
                    String Company = BeanSystemLog.getComcode();
                    int EmpId = Integer.parseInt(request.getParameter("EmpId"));

                    response.setContentType("application/json");
                    response.getWriter().print(model.getEmployeePayrollData(connection, SalaryMonth, EmpId, Company));

                }
                if (request.getParameter("updateEmpPayroll") != null) {
                    JSONObject jsa = new JSONObject();
                    System.out.println("updateEmpPayroll " + request.getParameter("updateEmpPayroll"));

                    String body = IOUtils.toString(request.getReader());

                    JsonParser parser = new JsonParser();
                    JsonObject requestJson = (JsonObject) parser.parse(body);

                    JsonObject payData = requestJson.getAsJsonObject();

                    String Company = BeanSystemLog.getComcode();
                    String SalaryMonth = payData.get("monthCode").getAsString();
                    int empID = payData.get("empID").getAsInt();
                    BigDecimal basicSal = payData.get("basicSal").getAsBigDecimal();
                    BigDecimal salArreas = payData.get("salArreas").getAsBigDecimal();
                    BigDecimal payCut = payData.get("payCut").getAsBigDecimal();
                    BigDecimal NoPay = payData.get("NoPay").getAsBigDecimal();
                    BigDecimal nopaydates = payData.get("nopaydates").getAsBigDecimal();
                    BigDecimal otpay = payData.get("otpay").getAsBigDecimal();
                    String remark = payData.get("remark").getAsString();

                    System.out.println(SalaryMonth + "***" + empID + "***" + basicSal);

                    if (model.isFinalized(connection, SalaryMonth, Company)) {
                        jsa.put("type", "error");
                        jsa.put("message", "Error:Payroll already finalized");
                    } else {
                        String message = model.updatePayrollData(connection, SalaryMonth, empID, basicSal, salArreas, payCut, NoPay, nopaydates, otpay, remark);
                        if (message.startsWith("Error:")) {
                            jsa.put("type", "error");
                            jsa.put("message", message);
                        } else {
                            jsa.put("type", "sucess");
                            jsa.put("message", message);
                        }
                    }

                    response.setContentType("application/json");
                    out.print(jsa);
                }

                if (request.getParameter("finalizePayroll") != null) {
                    System.out.println("finalizePayroll");
                    JSONObject jsa = new JSONObject();

                    String SalaryMonth = request.getParameter("monthCode");
                    String SalaryType = "Normal";
                    String Company = BeanSystemLog.getComcode();
                    String message = model.finalizePayRoll(connection, SalaryMonth, SalaryType, Company);

                    if (message.startsWith("Error:")) {
                        jsa.put("type", "error");
                        jsa.put("message", message);
                    } else {
                        jsa.put("type", "sucess");
                        jsa.put("message", message);
                    }

                    response.setContentType("application/json");
                    out.print(jsa);
                }

                if (request.getParameter("removeEntries") != null) {
                    System.out.println("removeEntries");
                    JSONObject jsa = new JSONObject();

                    String SalaryMonth = request.getParameter("monthCode");
                    String SalaryType = "Normal";
                    String Company = BeanSystemLog.getComcode();

                    if (model.isFinalized(connection, SalaryMonth, Company)) {
                        jsa.put("type", "error");
                        jsa.put("message", "Error:Payroll already finalized");
                    } else {
                        String message = model.deleteAllPayrollEntries(connection, Company, SalaryMonth, SalaryType);
                        if (message.startsWith("Error:")) {
                            jsa.put("type", "error");
                            jsa.put("message", message);
                        } else {
                            jsa.put("type", "sucess");
                            jsa.put("message", message);
                        }
                    }

                    response.setContentType("application/json");
                    out.print(jsa);
                }
                if (request.getParameter("createCust") != null) {
                    System.out.println("createCust");
                    ModelCreateCust modelCust = new ModelCreateCust(connection);
                    JSONObject jsa = new JSONObject();

                    String fileName = request.getParameter("fileName");
                    String SalaryMonth = request.getParameter("monthCode");
                    String SalaryType = "Normal";
                    String Company = BeanSystemLog.getComcode();
                    String description = request.getParameter("description");
                    String purposeCode = request.getParameter("purposeCode");
                    String purposeDescription = request.getParameter("purposeDescription");

                    String absoluteDiskPath = getServletContext().getRealPath("/DownloadingFiles/");

                    File directory = new File(absoluteDiskPath);
                    if (!directory.exists()) {
                        directory.mkdir();
                    }

                    File f = new File(absoluteDiskPath + "/" + fileName + ".txt");

                    String message = modelCust.settxtCustFile(connection, Company, f.getAbsolutePath(), SalaryMonth, SalaryType, description, purposeCode, purposeDescription);

                    if (message.startsWith("Error:")) {
                        jsa.put("type", "error");
                        jsa.put("message", message);
                    } else {
                        jsa.put("type", "sucess");
                        jsa.put("message", message);
                    }

                    response.setContentType("application/json");
                    out.print(jsa);
                }
                if (request.getParameter("downloadCust") != null) {
                    String fileName = request.getParameter("fileName");
                    FileInputStream fileInputStream = null;
                    PrintWriter responseWriter = null;

                    try {
                        String filePath = getServletContext().getRealPath("/DownloadingFiles/") + fileName + ".txt";
                        File file = new File(filePath);

                        if (file.exists() && file.isFile()) {
//                            System.out.println("File exists: " + file.exists());
//                            System.out.println("File size: " + file.length());
                            String mimeType = getServletContext().getMimeType(filePath);

                            if (mimeType == null) {
                                mimeType = "text/plain";
                            }
                            response.setContentType(mimeType);
                            response.addHeader("Content-Disposition", "attachment; filename=" + fileName);

                            fileInputStream = new FileInputStream(file);
                            responseWriter = response.getWriter();

                            byte[] buffer = new byte[1024];
                            int bytesRead;
                            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                                responseWriter.write(new String(buffer, 0, bytesRead));
                            }

                          //  System.out.println("File sent successfully.");
                        } else {
                            System.out.println("File does not exist or is not a regular file.");
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } finally {
                        try {
                            if (fileInputStream != null) {
                                fileInputStream.close();
                            }
                            if (responseWriter != null) {
                                responseWriter.close();
                            }
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }

                }
            } else {
                JSONObject jsa = new JSONObject();
                jsa.put("message", "Invalid token");
                jsa.put("type", "error");
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
