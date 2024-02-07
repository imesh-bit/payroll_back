/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import com.google.gson.JsonObject;
import dao.BeanCategory;
import daoImpls.ImplCategory;
import interfaces.InterfaceCategory;
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
public class ModelCategory {

    Connection connection;
    InterfaceCategory category;

    public ModelCategory(Connection connection) {
        this.connection = connection;
        category = new ImplCategory();
    }

    public JSONArray getCategoryList() throws Exception {
        JSONArray jsa = new JSONArray();
        try {

            List<BeanCategory> itemList = category.getCategoryList(connection);

            if (itemList.isEmpty()) {
                LinkedHashMap mp = new LinkedHashMap();
                mp.put("categoryid", 0);
                mp.put("categoryname", "No Matching Data");
                jsa.add(mp);
            } else {
                Iterator<BeanCategory> Listiterator = itemList.iterator();
                while (Listiterator.hasNext()) {
                    BeanCategory bean = Listiterator.next();
                    LinkedHashMap mp = new LinkedHashMap();
                    mp.put("categoryid", bean.getCategoryid());
                    mp.put("categoryname", bean.getCategoryname());
                    mp.put("user", bean.getUser());
                    jsa.add(mp);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsa;
    }

    public JSONObject saveCategory(JsonObject requestJson, Connection connection) throws Exception {
        JSONObject jsa = new JSONObject();

        BeanCategory bean = new BeanCategory();

        JsonObject allowanceData = requestJson.getAsJsonObject();

        bean.setCategoryid(allowanceData.get("categoryid").getAsInt());
        if (allowanceData.get("categoryname") != null) {
            bean.setCategoryname(allowanceData.get("categoryname").getAsString());
        } else {
            bean.setCategoryname("");
        }

        String status = null;
        if (allowanceData.get("categoryid").getAsInt() != 0) {
            status = category.updateCategory(bean);
        } else {
            status = category.saveCategory(bean);
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

    public JSONObject deleteCategory(JsonObject requestJson, Connection connection) throws Exception {
        JSONObject jsa = new JSONObject();

        JsonObject jsonData = requestJson.getAsJsonObject();
        int categoryid = Integer.parseInt(jsonData.get("categoryid").getAsString());
        String empcategory = jsonData.get("category").getAsString();

        String status = category.deleteCategory(categoryid, empcategory);

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
