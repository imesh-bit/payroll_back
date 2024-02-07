/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import com.google.gson.JsonObject;
import dao.BeanSalaryMonth;
import daoImpls.ImplSalaryMonth;
import interfaces.InterfaceSalaryMonth;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Nimesha
 */
public class ModelSalaryMonth {

    Connection connection;
    InterfaceSalaryMonth salaryMonth;

    public ModelSalaryMonth(Connection connection) {
        this.connection = connection;
        this.salaryMonth = new ImplSalaryMonth();
    }

    public JSONArray getSalMonthList(Connection connection) throws Exception {
        JSONArray jsa = new JSONArray();
        try {

            List<BeanSalaryMonth> itemList = salaryMonth.getMonthList(connection);
            if (itemList.isEmpty()) {
                LinkedHashMap mp = new LinkedHashMap();
                jsa.add(mp);
            } else {
                Iterator<BeanSalaryMonth> Listiterator = itemList.iterator();

                while (Listiterator.hasNext()) {
                    BeanSalaryMonth bean = Listiterator.next();
                    LinkedHashMap mp = new LinkedHashMap();
                    mp.put("monthcode", bean.getMonthcode());
                    mp.put("monthname", bean.getMonthname());
                    mp.put("monthid", bean.getMonthid());
                    mp.put("year", bean.getYear());
                    mp.put("runpayroll", bean.isRunpayroll());
                    mp.put("stopattendance", bean.isStopallowance());
                    mp.put("stopallowance", bean.isStopallowance());
                    mp.put("stopadvance", bean.isStopadvance());
                    jsa.add(mp);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsa;
    }

    public JSONObject saveMonth(JsonObject requestJson, Connection connection) throws Exception {
        JSONObject jsa = new JSONObject();

        BeanSalaryMonth bean = new BeanSalaryMonth();
        DateFormat Year_Format = new SimpleDateFormat("yyyy");

        JsonObject monthData = requestJson.getAsJsonObject();

        bean.setMonthcode(monthData.get("monthname").getAsString() + "-" + monthData.get("year").getAsInt());
        bean.setMonthid(monthData.get("monthid").getAsInt());
        bean.setMonthname(monthData.get("monthname").getAsString());
        bean.setYear(monthData.get("year").getAsInt());
        bean.setCreateDate(new Date());
        bean.setRunpayroll(monthData.get("runpayroll").getAsBoolean());
        bean.setStopallowance(monthData.get("stopallowance").getAsBoolean());
        bean.setStopattendance(monthData.get("stopattendance").getAsBoolean());
        bean.setStopadvance(monthData.get("stopadvance").getAsBoolean());

        String status = null;
        if (salaryMonth.checkCreateMonth(connection, bean.getMonthcode())) {
            status = salaryMonth.updateMonthDetails(bean);
        } else {
            status = salaryMonth.saveMonthDetails(bean);
        }

        if (status.startsWith("Error:")) {
            jsa.put("message", status);
            jsa.put("type", "error");
        } else {
            jsa.put("message", status);
            jsa.put("type", "sucess");
        }

        return jsa;
    }
}
