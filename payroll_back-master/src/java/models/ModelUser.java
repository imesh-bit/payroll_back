/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import com.google.gson.JsonObject;
import dao.BeanDesignation;
import dao.BeanEmployee;
import dao.BeanEmployeeBank;
import dao.BeanUser;
import daoImpls.ImplUser;
import interfaces.InterfaceUser;
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
public class ModelUser {

    Connection connection;
    InterfaceUser user;

    public ModelUser(Connection connection) {
        this.connection = connection;
        this.user = new ImplUser();
    }

    public JSONArray getUserList() throws Exception {
        JSONArray jsa = new JSONArray();
        try {

            List<BeanUser> itemList = user.getUserList(connection);
            if (itemList.isEmpty()) {
                LinkedHashMap mp = new LinkedHashMap();
                jsa.add(mp);
            } else {
                Iterator<BeanUser> Listiterator = itemList.iterator();

                while (Listiterator.hasNext()) {
                    BeanUser bean = Listiterator.next();
                    LinkedHashMap mp = new LinkedHashMap();
                    mp.put("empid", bean.getEmpid());
                    mp.put("username", bean.getUsername());
                    mp.put("fullname", bean.getFullname());
                    mp.put("usergroup", bean.getUsergroup());
                    mp.put("email", bean.getEmail());
                    mp.put("phoneno", bean.getMobileno());
                    mp.put("status", bean.isStatus());
                    jsa.add(mp);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsa;
    }

    public JSONArray getSelectedUser(int empId) throws Exception {

        BeanUser bean = user.getSelectedUser(connection, empId);

        JSONArray jsa = new JSONArray();
        LinkedHashMap mp = new LinkedHashMap();
        mp.put("empid", bean.getEmpid());
        mp.put("username", bean.getUsername());
        mp.put("fullname", bean.getFullname());
        mp.put("usergroup", bean.getUsergroup());
        mp.put("email", bean.getEmail());
        mp.put("phoneno", bean.getMobileno());
        mp.put("status", bean.isStatus());

        jsa.add(mp);
        return jsa;
    }

    public JSONObject saveUser(JsonObject requestJson, Connection connection) throws Exception {
        JSONObject jsa = new JSONObject();

        BeanUser bean = new BeanUser();

        JsonObject userData = requestJson.getAsJsonObject();

        bean.setEmpid(userData.get("empid").getAsInt());
        bean.setUsername(userData.get("username").getAsString());
        bean.setFullname(userData.get("fullname").getAsString());
        bean.setUsergroup(userData.get("usergroup").getAsString());
        bean.setEmail(userData.get("email").getAsString());
        bean.setMobileno(userData.get("mobileno").getAsString());
        bean.setStatus(userData.get("status").getAsBoolean());
        bean.setPassword(userData.get("password").getAsString());

        String status = null;
        if (user.checkForUpdate(connection, bean.getEmpid())) {
            status = user.updateUser(bean);
        } else {
            status = user.saveUser(bean);
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
