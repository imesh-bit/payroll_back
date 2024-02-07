/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import com.google.gson.JsonObject;
import dao.BeanAllowance;
import daoImpls.ImplAllowance;
import interfaces.InterfaceAllowance;
import java.sql.Connection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Nimesha
 */
public class ModelAllowance {

    Connection connection;
    InterfaceAllowance allowance;

    public ModelAllowance(Connection connection) {
        this.connection = connection;
        allowance = new ImplAllowance();
    }

    public JSONArray getAllowanceList() throws Exception {

        BeanAllowance bean = allowance.getAllowanceList(connection);

        JSONArray jsa = new JSONArray();
        LinkedHashMap mp = new LinkedHashMap();
        mp.put("id", bean.getId());
        mp.put("allow1", bean.getAllow1());
        mp.put("allow1_status", bean.isAllow1_status());
        mp.put("update_status1", bean.isUpdate_status1());
        mp.put("allow2", bean.getAllow2());
        mp.put("allow2_status", bean.isAllow2_status());
        mp.put("update_status2", bean.isUpdate_status2());
        mp.put("allow3", bean.getAllow3());
        mp.put("allow3_status", bean.isAllow3_status());
        mp.put("update_status3", bean.isUpdate_status3());
        mp.put("allow4", bean.getAllow4());
        mp.put("allow4_status", bean.isAllow4_status());
        mp.put("update_status4", bean.isUpdate_status4());
        mp.put("allow5", bean.getAllow5());
        mp.put("allow5_status", bean.isAllow5_status());
        mp.put("update_status5", bean.isUpdate_status5());

        jsa.add(mp);
        return jsa;
    }

    public HashMap getAllowanceMap(Connection connection) throws Exception {
        HashMap map = new HashMap();

        BeanAllowance bean = allowance.getAllowanceList(connection);

        map.put("allow1", bean.getAllow1());
        map.put("allow2", bean.getAllow2());
        map.put("allow3", bean.getAllow3());
        map.put("allow4", bean.getAllow4());
        map.put("allow5", bean.getAllow5());
        map.put("Noncash Benifit", "Noncash Benifit");

        return map;
    }
//For Employee Profile Allowane List

    public JSONArray getFixedAllowanceList() throws Exception {

        BeanAllowance bean = allowance.getAllowanceList(connection);

        JSONArray jsa = new JSONArray();
        LinkedHashMap mp = new LinkedHashMap();
        mp.put("id", bean.getId());
        if (bean.isAllow1_status()) {
            mp.put("allow1", bean.getAllow1());
        }
        if (bean.isAllow2_status()) {
            mp.put("allow2", bean.getAllow2());
        }

        if (bean.isAllow3_status()) {
            mp.put("allow3", bean.getAllow3());
        }

        if (bean.isAllow4_status()) {
            mp.put("allow4", bean.getAllow4());
        }
        if (bean.isAllow5_status()) {
            mp.put("allow5", bean.getAllow5());
        }

        jsa.add(mp);
        return jsa;
    }

    //For Allowance Request Allowane List
    public JSONArray getVariableAllowanceList() throws Exception {

        BeanAllowance bean = allowance.getAllowanceList(connection);

        JSONArray jsa = new JSONArray();
        LinkedHashMap mp = new LinkedHashMap();
        mp.put("id", bean.getId());
        if (!bean.isAllow1_status()) {
            mp.put("allow1", bean.getAllow1());
        }
        if (!bean.isAllow2_status()) {
            mp.put("allow2", bean.getAllow2());
        }

        if (!bean.isAllow3_status()) {
            mp.put("allow3", bean.getAllow3());
        }

        if (!bean.isAllow4_status()) {
            mp.put("allow4", bean.getAllow4());
        }
        if (!bean.isAllow5_status()) {
            mp.put("allow5", bean.getAllow5());
        }

        jsa.add(mp);
        return jsa;
    }

    public JSONObject saveAllowance(JsonObject requestJson, Connection connection) throws Exception {
        JSONObject jsa = new JSONObject();

        BeanAllowance bean = new BeanAllowance();

        JsonObject allowanceData = requestJson.getAsJsonObject();

        bean.setId(allowanceData.get("id").getAsInt());
        if (allowanceData.get("allow1") != null) {
            bean.setAllow1(allowanceData.get("allow1").getAsString());
        } else {
            bean.setAllow1("");
        }
        if (allowanceData.get("allow1_status") != null) {
            bean.setAllow1_status(allowanceData.get("allow1_status").getAsBoolean());
        } else {
            bean.setAllow1_status(false);
        }
        if (allowanceData.get("allow2") != null) {
            bean.setAllow2(allowanceData.get("allow2").getAsString());
        } else {
            bean.setAllow2("");
        }
        if (allowanceData.get("allow2_status") != null) {
            bean.setAllow2_status(allowanceData.get("allow2_status").getAsBoolean());
        } else {
            bean.setAllow2_status(false);
        }
        if (allowanceData.get("allow3") != null) {
            bean.setAllow3(allowanceData.get("allow3").getAsString());
        } else {
            bean.setAllow3("");
        }
        if (allowanceData.get("allow3_status") != null) {
            bean.setAllow3_status(allowanceData.get("allow3_status").getAsBoolean());
        } else {
            bean.setAllow3_status(false);
        }
        if (allowanceData.get("allow4") != null) {
            bean.setAllow4(allowanceData.get("allow4").getAsString());
        } else {
            bean.setAllow4("");
        }
        if (allowanceData.get("allow4_status") != null) {
            bean.setAllow4_status(allowanceData.get("allow4_status").getAsBoolean());
        } else {
            bean.setAllow4_status(false);
        }
        if (allowanceData.get("allow5") != null) {
            bean.setAllow5(allowanceData.get("allow5").getAsString());
        } else {
            bean.setAllow5("");
        }
        if (allowanceData.get("allow5_status") != null) {
            bean.setAllow5_status(allowanceData.get("allow5_status").getAsBoolean());
        } else {
            bean.setAllow5_status(false);
        }

        String status = null;
        if (allowanceData.get("id").getAsInt() != 0) {
            status = allowance.updateAllowance(bean);
        } else {
            status = allowance.saveAllowance(bean);
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
