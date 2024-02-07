/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import com.google.gson.JsonObject;
import dao.BeanInventory;
import daoImpls.ImplInventory;
import interfaces.InterfaceInventory;
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
public class ModelInventory {

    Connection connection;
    InterfaceInventory inventory;

    public ModelInventory(Connection connection) {
        this.connection = connection;
        this.inventory = new ImplInventory();
    }

    public JSONArray getInventoryList(int empid) throws Exception {
        JSONArray jsa = new JSONArray();
        try {

            List<BeanInventory> itemList = inventory.getInventoryList(connection, empid);
            if (itemList.isEmpty()) {
                LinkedHashMap mp = new LinkedHashMap();
                jsa.add(mp);
            } else {
                Iterator<BeanInventory> Listiterator = itemList.iterator();

                while (Listiterator.hasNext()) {
                    BeanInventory bean = Listiterator.next();
                    LinkedHashMap mp = new LinkedHashMap();
                    mp.put("empid", bean.getEmpid());
                    mp.put("empname", bean.getEmpname());
                    mp.put("itemid", bean.getItemid());
                    mp.put("item", bean.getItem());
                    mp.put("qty", bean.getQty());
                    mp.put("description", bean.getDescription());
                    jsa.add(mp);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsa;
    }

    public JSONObject saveInventory(JsonObject requestJson, Connection connection) throws Exception {
        JSONObject jsa = new JSONObject();

        BeanInventory bean = new BeanInventory();

        JsonObject invData = requestJson.getAsJsonObject();
        bean.setItemid(invData.get("itemid").getAsInt());
        bean.setItem(invData.get("item").getAsString());
        bean.setEmpid(invData.get("empid").getAsInt());
        bean.setDescription(invData.get("description").getAsString());
        bean.setQty(invData.get("qty").getAsInt());

        String status = null;
        if (invData.get("itemid").getAsInt() != 0) {
            status = inventory.updateItem(bean);
        } else {
            status = inventory.saveItem(bean);
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

    public JSONObject deleteInventory(JsonObject requestJson, Connection connection) throws Exception {
        JSONObject jsa = new JSONObject();

        JsonObject jsonData = requestJson.getAsJsonObject();
        int empid = Integer.parseInt(jsonData.get("empid").getAsString());
        int itemid = Integer.parseInt(jsonData.get("itemid").getAsString());
        String item = jsonData.get("item").getAsString();

        String status = inventory.deleteItem(itemid, item, empid);

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
