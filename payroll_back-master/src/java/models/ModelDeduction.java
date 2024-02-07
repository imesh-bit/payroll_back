/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import com.google.gson.JsonObject;
import dao.BeanDeduction;
import daoImpls.ImplDeduction;
import interfaces.InterfaceDeduction;
import java.sql.Connection;
import java.util.LinkedHashMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Nimesha
 */
public class ModelDeduction {

    Connection connection;
    InterfaceDeduction deduction;

    public ModelDeduction(Connection connection) {
        this.connection = connection;
        deduction = new ImplDeduction();
    }

    public JSONArray getDeductionList() throws Exception {

        BeanDeduction bean = deduction.getDeductionList(connection);

        JSONArray jsa = new JSONArray();
        LinkedHashMap mp = new LinkedHashMap();
        mp.put("id", bean.getId());
        mp.put("deduct1", bean.getDeduct1());
        mp.put("deduct2", bean.getDeduct2());
        mp.put("deduct3", bean.getDeduct3());
        mp.put("deduct4", bean.getDeduct4());
        mp.put("deduct5", bean.getDeduct5());

        jsa.add(mp);
        return jsa;
    }

    public JSONObject saveDeduction(JsonObject requestJson, Connection connection) throws Exception {
        JSONObject jsa = new JSONObject();

        BeanDeduction bean = new BeanDeduction();

        JsonObject deductionData = requestJson.getAsJsonObject();

        bean.setId(deductionData.get("id").getAsInt());
        if (deductionData.get("deduct1") != null) {
            bean.setDeduct1(deductionData.get("deduct1").getAsString());
        } else {
            bean.setDeduct1("");
        }
        if (deductionData.get("deduct2") != null) {
            bean.setDeduct2(deductionData.get("deduct2").getAsString());
        } else {
            bean.setDeduct2("");
        }
        if (deductionData.get("deduct3") != null) {
            bean.setDeduct3(deductionData.get("deduct3").getAsString());
        } else {
            bean.setDeduct3("");
        }
        if (deductionData.get("deduct4") != null) {
            bean.setDeduct4(deductionData.get("deduct4").getAsString());
        } else {
            bean.setDeduct4("");
        }
        if (deductionData.get("deduct5") != null) {
            bean.setDeduct5(deductionData.get("deduct5").getAsString());
        } else {
            bean.setDeduct5("");
        }

        String status = null;
        if (deductionData.get("id").getAsInt() != 0) {
            status = deduction.updateDeduction(bean);
        } else {
            status = deduction.saveDeduction(bean);
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
