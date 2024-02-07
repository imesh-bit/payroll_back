/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import com.google.gson.JsonObject;
import dao.BeanDesignation;
import daoImpls.ImplDesignation;
import interfaces.InterfaceDesignation;
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
public class ModelDesignation {

    Connection connection;
    InterfaceDesignation designation;

    public ModelDesignation(Connection connection) {
        this.connection = connection;
        this.designation = new ImplDesignation();
    }

    public JSONArray getDesignationList() throws Exception {
        JSONArray jsa = new JSONArray();
        try {

            List<BeanDesignation> itemList = designation.getDesignationList(connection);
            if (itemList.isEmpty()) {
                LinkedHashMap mp = new LinkedHashMap();
                mp.put("id", 0);
                mp.put("designation", "No Matching Data");
                jsa.add(mp);
            } else {
                Iterator<BeanDesignation> Listiterator = itemList.iterator();

                while (Listiterator.hasNext()) {
                    BeanDesignation bean = Listiterator.next();
                    LinkedHashMap mp = new LinkedHashMap();
                    mp.put("id", bean.getId());
                    mp.put("designation", bean.getDesignation());
                    mp.put("user", bean.getUser());
                    jsa.add(mp);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsa;
    }

    public JSONObject saveDesignation(JsonObject requestJson, Connection connection) throws Exception {
        JSONObject jsa = new JSONObject();

        BeanDesignation bean = new BeanDesignation();

        JsonObject allowanceData = requestJson.getAsJsonObject();

        bean.setId(allowanceData.get("id").getAsInt());
        if (allowanceData.get("designation") != null) {
            bean.setDesignation(allowanceData.get("designation").getAsString());
        } else {
            bean.setDesignation("");
        }

        String status = null;
        if (allowanceData.get("id").getAsInt() != 0) {
            status = designation.updateDesignation(bean);
        } else {
            status = designation.saveDesignation(bean);
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

    public JSONObject deleteDesignation(JsonObject requestJson, Connection connection) throws Exception {
        JSONObject jsa = new JSONObject();

        JsonObject jsonData = requestJson.getAsJsonObject();
        int id = Integer.parseInt(jsonData.get("id").getAsString());
        String empdesignation = jsonData.get("designation").getAsString();

        String status = designation.deleteDesignation(id, empdesignation);

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
