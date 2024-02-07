/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dao.BeanAttendance;
import dao.BeanSalaryMonth;
import daoImpls.ImplAttendance;
import daoImpls.ImplSalaryMonth;
import interfaces.InterfaceAttendance;
import interfaces.InterfaceSalaryMonth;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Nimesha
 */
public class ModelAttendance {

    Connection connection;
    InterfaceAttendance attendance;
    InterfaceSalaryMonth salaryMonth;

    public ModelAttendance(Connection connection) {
        this.connection = connection;
        this.attendance = new ImplAttendance();
        this.salaryMonth = new ImplSalaryMonth();
    }

    public JSONArray getAttendanceList(String comCode, String monthCode) throws Exception {
        JSONArray jsa = new JSONArray();
        try {

            List<BeanAttendance> itemList = attendance.getAttendancesList(connection, comCode, monthCode);

            if (itemList.isEmpty()) {
                LinkedHashMap mp = new LinkedHashMap();
                jsa.add(mp);
            } else {
                Iterator<BeanAttendance> Listiterator = itemList.iterator();

                while (Listiterator.hasNext()) {
                    BeanAttendance bean = Listiterator.next();
                    LinkedHashMap mp = new LinkedHashMap();
                    mp.put("id", bean.getId());
                    mp.put("empid", bean.getEmpid());
                    mp.put("empcode", bean.getEmpCode());
                    mp.put("empname", bean.getEmpName());
                    mp.put("division", bean.getDivision());
                    mp.put("noofdates", bean.getNoofdates());
                    mp.put("comcode", bean.getComCode());
                    mp.put("monthCode", bean.getMonthCode());
                    jsa.add(mp);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsa;
    }

    public JSONObject saveAttendance(JsonObject requestJson, Connection connection) throws Exception {
        JSONObject jsa = new JSONObject();

        BeanAttendance bean = new BeanAttendance();

        JsonObject allowanceData = requestJson.getAsJsonObject();

        bean.setId(allowanceData.get("id").getAsInt());
        bean.setEmpid(allowanceData.get("empid").getAsInt());
        bean.setMonthCode(allowanceData.get("monthCode").getAsString());
        bean.setNoofdates(allowanceData.get("noofdates").getAsBigDecimal());

        BeanSalaryMonth month = salaryMonth.getSelectedMonthStatus(connection, bean.getMonthCode());
        if (month.isRunpayroll()) {
            jsa.put("message", "Selected Month Is Already Closed");
            jsa.put("type", "error");
        } else {
            if (month.isStopallowance()) {
                jsa.put("message", "Attendance Were Already Blocked For This Month");
                jsa.put("type", "error");
            } else {
                String status = null;
                if (allowanceData.get("id").getAsInt() != 0) {
                    status = attendance.updateAttendance(bean);
                } else {
                    status = attendance.saveAttendance(bean);
                }

                if (status.startsWith("Error:")) {
                    jsa.put("message", status);
                    jsa.put("type", "error");
                } else {
                    jsa.put("message", status);
                    jsa.put("type", "sucess");
                }
            }
        }

        return jsa;
    }

    public JSONObject saveAttendanceList(JsonObject requestJson, Connection connection) throws Exception {
        JSONObject jsa = new JSONObject();

        JsonObject allowanceData = requestJson.getAsJsonObject();
        JsonArray attendanceDetails = allowanceData.get("excelData").getAsJsonArray();

        List<BeanAttendance> list = new ArrayList<>();
        for (JsonElement jsonElement : attendanceDetails) {
            JsonObject jo = jsonElement.getAsJsonObject();
            BeanAttendance bean = new BeanAttendance();
            bean.setEmpid(jo.get("systemid").getAsInt());
            bean.setMonthCode(allowanceData.get("monthCode").getAsString());
            bean.setNoofdates(jo.get("noofattendance").getAsBigDecimal());
            list.add(bean);
        }

        BeanSalaryMonth month = salaryMonth.getSelectedMonthStatus(connection, allowanceData.get("monthCode").getAsString());
        if (month.isRunpayroll()) {
            jsa.put("message", "Selected Month Is Already Closed");
            jsa.put("type", "error");
        } else {
            if (month.isStopallowance()) {
                jsa.put("message", "Attendance Were Already Blocked For This Month");
                jsa.put("type", "error");
            } else {
                String status = null;
                status = attendance.saveAttendanceList(list);

                if (status.startsWith("Error:")) {
                    jsa.put("message", status);
                    jsa.put("type", "error");
                } else {
                    jsa.put("message", status);
                    jsa.put("type", "sucess");
                }
            }
        }

        return jsa;
    }

    public JSONObject deleteAttendance(JsonObject requestJson, Connection connection) throws Exception {
        JSONObject jsa = new JSONObject();

        JsonObject allowanceData = requestJson.getAsJsonObject();

        BeanSalaryMonth month = salaryMonth.getSelectedMonthStatus(connection, allowanceData.get("monthCode").getAsString());
        if (month.isRunpayroll()) {
            jsa.put("message", "Selected Month Is Already Closed");
            jsa.put("type", "error");
        } else {
            if (month.isStopallowance()) {
                jsa.put("message", "Attendance Were Already Blocked For This Month");
                jsa.put("type", "error");
            } else {
                String status = null;
                status = attendance.deleteAttendance(allowanceData.get("id").getAsInt(), allowanceData.get("monthCode").getAsString(), allowanceData.get("empid").getAsInt());

                if (status.startsWith("Error:")) {
                    jsa.put("message", status);
                    jsa.put("type", "error");
                } else {
                    jsa.put("message", status);
                    jsa.put("type", "sucess");
                }
            }
        }

        return jsa;
    }
}
