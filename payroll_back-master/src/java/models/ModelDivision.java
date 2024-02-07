/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import com.google.gson.JsonObject;
import dao.BeanDivision;
import daoImpls.ImplDivision;
import interfaces.InterfaceDivision;
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
public class ModelDivision {

    Connection connection;
    InterfaceDivision division;

    public ModelDivision(Connection connection) {
        this.connection = connection;
        this.division = new ImplDivision();
    }

    public JSONArray getDivisionList() throws Exception {
        JSONArray jsa = new JSONArray();
        try {

            List<BeanDivision> itemList = division.getDivisionList(connection);
            if (itemList.isEmpty()) {
                LinkedHashMap mp = new LinkedHashMap();
                mp.put("id", 0);
                mp.put("division", "No Matching Data");
                jsa.add(mp);
            } else {
                Iterator<BeanDivision> Listiterator = itemList.iterator();

                while (Listiterator.hasNext()) {
                    BeanDivision bean = Listiterator.next();
                    LinkedHashMap mp = new LinkedHashMap();
                    mp.put("id", bean.getId());
                    mp.put("division", bean.getDivision());
                    mp.put("user", bean.getUser());

                    jsa.add(mp);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsa;
    }

    public JSONObject saveDivision(JsonObject requestJson, Connection connection) throws Exception {
        JSONObject jsa = new JSONObject();

        BeanDivision bean = new BeanDivision();

        JsonObject allowanceData = requestJson.getAsJsonObject();

        bean.setId(allowanceData.get("id").getAsInt());
        if (allowanceData.get("division") != null) {
            bean.setDivision(allowanceData.get("division").getAsString());
        } else {
            bean.setDivision("");
        }

        String status = null;
        if (allowanceData.get("id").getAsInt() != 0) {
            status = division.updateDivision(bean);
        } else {
            status = division.saveDivision(bean);
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

    public JSONObject deleteDivision(JsonObject requestJson, Connection connection) throws Exception {
        JSONObject jsa = new JSONObject();

        JsonObject jsonData = requestJson.getAsJsonObject();
        int divisionid = Integer.parseInt(jsonData.get("divisionid").getAsString());
        String empdivision = jsonData.get("empdivision").getAsString();

        String status = division.deleteDivision(divisionid, empdivision);

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
