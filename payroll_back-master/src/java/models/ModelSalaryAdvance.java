/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import com.google.gson.JsonObject;
import dao.BeanSalaryAdvance;
import dao.BeanSalaryMonth;
import daoImpls.ImplSalaryAdvance;
import daoImpls.ImplSalaryMonth;
import interfaces.InterfaceSalaryAdvance;
import interfaces.InterfaceSalaryMonth;
import java.sql.Connection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Nimesha
 */
public class ModelSalaryAdvance {

    Connection connection;
    InterfaceSalaryAdvance salaryAdvance;
    InterfaceSalaryMonth salaryMonth;

    public ModelSalaryAdvance(Connection connection) {
        this.connection = connection;
        this.salaryAdvance = new ImplSalaryAdvance();
        this.salaryMonth = new ImplSalaryMonth();
    }

    public JSONArray getAdvanceList(String comCode, String monthCode) throws Exception {
        JSONArray jsa = new JSONArray();
        try {

            List<BeanSalaryAdvance> itemList = salaryAdvance.getAdvanceList(connection, comCode, monthCode);
            if (itemList.isEmpty()) {
                LinkedHashMap mp = new LinkedHashMap();
                jsa.add(mp);
            } else {
                Iterator<BeanSalaryAdvance> Listiterator = itemList.iterator();

                while (Listiterator.hasNext()) {
                    BeanSalaryAdvance bean = Listiterator.next();
                    LinkedHashMap mp = new LinkedHashMap();
                    mp.put("empid", bean.getEmpid());
                    mp.put("empcode", bean.getEmpCode());
                    mp.put("empname", bean.getEmpName());
                    mp.put("division", bean.getDivision());
                    mp.put("salary", bean.getSalary());
                    mp.put("amount", bean.getAmount());
                    mp.put("reason", bean.getReason());
                    jsa.add(mp);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsa;
    }

    public JSONObject saveSalaryAdvance(JsonObject requestJson, Connection connection) throws Exception {
        JSONObject jsa = new JSONObject();

        BeanSalaryAdvance bean = new BeanSalaryAdvance();

        JsonObject allowanceData = requestJson.getAsJsonObject();

        bean.setEmpid(allowanceData.get("empid").getAsInt());
        bean.setMonthcode(allowanceData.get("monthCode").getAsString());
        bean.setReason(allowanceData.get("reason").getAsString());
        bean.setAmount(allowanceData.get("amount").getAsBigDecimal());

        BeanSalaryMonth month = salaryMonth.getSelectedMonthStatus(connection, bean.getMonthcode());
        if (month.isRunpayroll()) {
            jsa.put("message", "Selected Month Is Already Closed");
            jsa.put("type", "error");
        } else {
            if (month.isStopadvance()) {
                jsa.put("message", "Salary Advance Entries Were Already Blocked For This Month");
                jsa.put("type", "error");
            } else {
                String status = null;
                if (allowanceData.get("new").getAsBoolean()) {
                    status = salaryAdvance.saveAdvance(bean);
                } else {
                    status = salaryAdvance.updateAdvance(bean);
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

    public JSONObject deleteSalaryAdvance(JsonObject requestJson, Connection connection) throws Exception {
        JSONObject jsa = new JSONObject();

        BeanSalaryAdvance bean = new BeanSalaryAdvance();

        JsonObject allowanceData = requestJson.getAsJsonObject();

        bean.setEmpid(allowanceData.get("empid").getAsInt());
        bean.setMonthcode(allowanceData.get("monthCode").getAsString());
        bean.setReason(allowanceData.get("reason").getAsString());
        bean.setAmount(allowanceData.get("amount").getAsBigDecimal());

        BeanSalaryMonth month = salaryMonth.getSelectedMonthStatus(connection, bean.getMonthcode());
        if (month.isRunpayroll()) {
            jsa.put("message", "Selected Month Is Already Closed");
            jsa.put("type", "error");
        } else {
            String status = null;
            status = salaryAdvance.deleteAdvance(bean);

            if (status.startsWith("Error:")) {
                jsa.put("message", status);
                jsa.put("type", "error");
            } else {
                jsa.put("message", status);
                jsa.put("type", "sucess");
            }
        }

        return jsa;
    }
}
