/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import com.google.gson.JsonObject;
import dao.BeanOccupationalGroup;
import daoImpls.ImplOccupationalGroup;
import interfaces.InterfaceOccupationalGroup;
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
public class ModelOccupationalGroup {

    Connection connection;
    InterfaceOccupationalGroup occGroup;

    public ModelOccupationalGroup(Connection connection) {
        this.connection = connection;
        this.occGroup = new ImplOccupationalGroup();
    }

    public JSONArray getOccupationalList() throws Exception {
        JSONArray jsa = new JSONArray();
        try {

            List< BeanOccupationalGroup> itemList = occGroup.getOccupationalGroupList(connection);
            if (itemList.isEmpty()) {
                LinkedHashMap mp = new LinkedHashMap();
                mp.put("groupid", 0);
                mp.put("groupname", "No Matching Data");
                jsa.add(mp);
            } else {
                Iterator< BeanOccupationalGroup> Listiterator = itemList.iterator();

                while (Listiterator.hasNext()) {
                    BeanOccupationalGroup bean = Listiterator.next();
                    LinkedHashMap mp = new LinkedHashMap();
                    mp.put("groupid", bean.getGroupid());
                    mp.put("groupname", bean.getGroupname());
                    mp.put("user", bean.getUser());
                    jsa.add(mp);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsa;
    }

    public JSONObject saveOccupationalGroup(JsonObject requestJson, Connection connection) throws Exception {
        JSONObject jsa = new JSONObject();

        BeanOccupationalGroup bean = new BeanOccupationalGroup();

        JsonObject allowanceData = requestJson.getAsJsonObject();

        bean.setGroupid(allowanceData.get("groupid").getAsInt());
        if (allowanceData.get("groupname") != null) {
            bean.setGroupname(allowanceData.get("groupname").getAsString());
        } else {
            bean.setGroupname("");
        }

        String status = null;
        if (allowanceData.get("groupid").getAsInt() != 0) {
            status = occGroup.updateOccupationalGroup(bean);
        } else {
            status = occGroup.saveOccupationalGroup(bean);
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

    public JSONObject deleteGroup(JsonObject requestJson, Connection connection) throws Exception {
        JSONObject jsa = new JSONObject();

        JsonObject jsonData = requestJson.getAsJsonObject();
        int groupId = Integer.parseInt(jsonData.get("groupId").getAsString());
        String empgroup = jsonData.get("empgroup").getAsString();

        String status = occGroup.deleteGroup(groupId, empgroup);

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
