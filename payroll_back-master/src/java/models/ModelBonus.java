/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import com.google.gson.JsonObject;
import dao.BeanBonus;
import dao.BeanSalaryMonth;
import daoImpls.ImplBonus;
import daoImpls.ImplSalaryMonth;
import interfaces.InterfaceBonus;
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
public class ModelBonus {

    Connection connection;
    InterfaceBonus bonus;
    InterfaceSalaryMonth salaryMonth;

    public ModelBonus(Connection connection) {
        this.connection = connection;
        this.bonus = new ImplBonus();
        this.salaryMonth = new ImplSalaryMonth();
    }

    public JSONArray getBonusList(String comCode, String monthCode) throws Exception {
        JSONArray jsa = new JSONArray();
        try {

            List<BeanBonus> itemList = bonus.getBonustList(connection, comCode, monthCode);
            if (itemList.isEmpty()) {
                LinkedHashMap mp = new LinkedHashMap();
                jsa.add(mp);
            } else {
                Iterator<BeanBonus> Listiterator = itemList.iterator();

                while (Listiterator.hasNext()) {
                    BeanBonus bean = Listiterator.next();
                    LinkedHashMap mp = new LinkedHashMap();
                    mp.put("id", bean.getBonusid());
                    mp.put("empid", bean.getEmpid());
                    mp.put("empcode", bean.getEmpCode());
                    mp.put("empname", bean.getEmpName());
                    mp.put("division", bean.getDivision());
                    mp.put("reference", bean.getReference());
                    mp.put("amount", bean.getAmount());
                    jsa.add(mp);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsa;
    }

    public JSONObject saveBonus(JsonObject requestJson, Connection connection) throws Exception {
        JSONObject jsa = new JSONObject();

        BeanBonus bean = new BeanBonus();

        JsonObject bonusData = requestJson.getAsJsonObject();

        bean.setBonusid(bonusData.get("bonusid").getAsInt());
        bean.setEmpid(bonusData.get("empid").getAsInt());
        bean.setMonthcode(bonusData.get("monthCode").getAsString());
        bean.setReference(bonusData.get("reference").getAsString());
        bean.setAmount(bonusData.get("amount").getAsBigDecimal());

        BeanSalaryMonth month = salaryMonth.getSelectedMonthStatus(connection, bean.getMonthcode());
        if (month.isRunpayroll()) {
            jsa.put("message", "Selected Month Is Already Closed");
            jsa.put("type", "error");
        } else {
            if (month.isStopadvance()) {
                jsa.put("message", "Bonus Entries Were Already Blocked For This Month");
                jsa.put("type", "error");
            } else {
                String status = null;
                if (bonusData.get("bonusid").getAsInt() != 0) {
                    status = bonus.updateBonus(bean);
                } else {
                    status = bonus.saveBonus(bean);
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

    public JSONObject deleteBonus(JsonObject requestJson, Connection connection) throws Exception {
        JSONObject jsa = new JSONObject();

        BeanBonus bean = new BeanBonus();

        JsonObject bonusData = requestJson.getAsJsonObject();

        bean.setBonusid(bonusData.get("bonusid").getAsInt());
        bean.setEmpid(bonusData.get("empid").getAsInt());
        bean.setMonthcode(bonusData.get("monthCode").getAsString());
        bean.setReference(bonusData.get("reference").getAsString());
        bean.setAmount(bonusData.get("amount").getAsBigDecimal());

        BeanSalaryMonth month = salaryMonth.getSelectedMonthStatus(connection, bean.getMonthcode());
        if (month.isRunpayroll()) {
            jsa.put("message", "Selected Month Is Already Closed");
            jsa.put("type", "error");
        } else {

            String status = null;
            status = bonus.deleteBonus(bean);

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
