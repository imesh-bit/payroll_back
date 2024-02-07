/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import com.google.gson.JsonObject;
import dao.BeanAllowanceRequest;
import dao.BeanSalaryMonth;
import daoImpls.ImplAllowanceRequest;
import daoImpls.ImplSalaryMonth;
import interfaces.InterfaceAllowanceRequest;
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
public class ModelAllowanceRequest {

    Connection connection;
    InterfaceAllowanceRequest allowanceRequest;
    InterfaceSalaryMonth salaryMonth;
    ModelAllowance model;
    ModelNoncashBenifit modelnoncash;

    public ModelAllowanceRequest(Connection connection) {
        this.connection = connection;
        this.allowanceRequest = new ImplAllowanceRequest();
        this.salaryMonth = new ImplSalaryMonth();
        this.model = new ModelAllowance(connection);
        this.modelnoncash = new ModelNoncashBenifit(connection);
    }

    public JSONArray getAllowanceRequestList(String comCode, String monthCode, String reqType) throws Exception {
        JSONArray jsa = new JSONArray();
        try {

            List<BeanAllowanceRequest> itemList = allowanceRequest.getAllowanceRequestList(connection, comCode, monthCode, reqType);

            if (itemList.isEmpty()) {
                LinkedHashMap mp = new LinkedHashMap();
                jsa.add(mp);
            } else {
                Iterator<BeanAllowanceRequest> Listiterator = itemList.iterator();

                while (Listiterator.hasNext()) {
                    BeanAllowanceRequest bean = Listiterator.next();
                    LinkedHashMap mp = new LinkedHashMap();
                    mp.put("id", bean.getId());
                    mp.put("empid", bean.getEmpid());
                    mp.put("empcode", bean.getEmpCode());
                    mp.put("empname", bean.getEmpName());
                    mp.put("division", bean.getDivision());
                    mp.put("allowancecode", bean.getAllowanceCode());
                    if (bean.getAllowanceType().equalsIgnoreCase("A")) {
                        mp.put("allowancename", model.getAllowanceMap(connection).get(bean.getAllowanceCode()));
                        //System.out.println("allowancecode " + bean.getAllowanceCode() + " allowancename " + model.getAllowanceMap(connection).get(bean.getAllowanceCode()));
                    } else {
                        mp.put("allowancename", modelnoncash.getNoncashMap(connection).get(bean.getAllowanceCode()));
                        //  System.out.println("allowancecode " + bean.getAllowanceCode() + " allowancename " + modelnoncash.getNoncashMap(connection).get(bean.getAllowanceCode()));
                    }

                    mp.put("amount", bean.getAmount());
                    jsa.add(mp);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsa;
    }

    public JSONObject saveAllowanceRequest(JsonObject requestJson, Connection connection) throws Exception {
        JSONObject jsa = new JSONObject();

        BeanAllowanceRequest bean = new BeanAllowanceRequest();

        JsonObject allowanceData = requestJson.getAsJsonObject();

        bean.setId(allowanceData.get("id").getAsInt());
        bean.setEmpid(allowanceData.get("empid").getAsInt());
        bean.setMonthcode(allowanceData.get("monthCode").getAsString());
        bean.setAllowanceCode(allowanceData.get("allowancecode").getAsString());
        bean.setAmount(allowanceData.get("amount").getAsBigDecimal());
        bean.setAllowanceType(allowanceData.get("reqType").getAsString());

        BeanSalaryMonth month = salaryMonth.getSelectedMonthStatus(connection, bean.getMonthcode());
        if (month.isRunpayroll()) {
            jsa.put("message", "Selected Month Is Already Closed");
            jsa.put("type", "error");
        } else {
            if (month.isStopallowance()) {
                jsa.put("message", "Allowance Request Were Already Blocked For This Month");
                jsa.put("type", "error");
            } else {
                String status = null;
                if (allowanceData.get("id").getAsInt() != 0) {
                    status = allowanceRequest.updateAllowanceRequest(bean);
                } else {
                    status = allowanceRequest.saveAllowanceRequest(bean);
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

    public JSONObject deleteAllowanceRequest(JsonObject requestJson, Connection connection) throws Exception {
        JSONObject jsa = new JSONObject();

        BeanAllowanceRequest bean = new BeanAllowanceRequest();

        JsonObject allowanceData = requestJson.getAsJsonObject();

        bean.setId(allowanceData.get("id").getAsInt());
        bean.setAllowanceType(allowanceData.get("allowtype").getAsString());
        bean.setEmpid(allowanceData.get("empid").getAsInt());
        bean.setMonthcode(allowanceData.get("monthCode").getAsString());
        bean.setAllowanceCode(allowanceData.get("allowancecode").getAsString());
        bean.setAmount(allowanceData.get("amount").getAsBigDecimal());

        BeanSalaryMonth month = salaryMonth.getSelectedMonthStatus(connection, bean.getMonthcode());
        if (month.isRunpayroll()) {
            jsa.put("message", "Selected Month Is Already Closed");
            jsa.put("type", "error");
        } else {
            if (month.isStopallowance()) {
                jsa.put("message", "Allowance Request Were Already Blocked For This Month");
                jsa.put("type", "error");
            } else {
                String status = null;
                status = allowanceRequest.deleteAllowanceRequest(bean);

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
