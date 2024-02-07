/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import com.google.gson.JsonObject;
import dao.BeanUsergroup;
import daoImpls.ImplUserGroup;
import interfaces.InterfaceUserGroup;
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
public class ModelUserGroup {

    Connection connection;
    InterfaceUserGroup userGroup;

    public ModelUserGroup(Connection connection) {
        this.connection = connection;
        userGroup = new ImplUserGroup();
    }

    public JSONArray getSelectedUsergroup(String UserGroup) throws Exception {

        BeanUsergroup bean = userGroup.getSelectedUserGroup(connection, UserGroup);

        JSONArray jsa = new JSONArray();
        LinkedHashMap mp = new LinkedHashMap();
        mp.put("userGroup", UserGroup);
        mp.put("p1", bean.isP1());
        mp.put("p2", bean.isP2());
        mp.put("p3", bean.isP3());
        mp.put("p4", bean.isP4());
        mp.put("p5", bean.isP5());
        mp.put("p6", bean.isP6());
        mp.put("p7", bean.isP7());
        mp.put("p8", bean.isP8());
        mp.put("p9", bean.isP9());
        mp.put("p10", bean.isP10());
        mp.put("p11", bean.isP11());
        mp.put("p12", bean.isP12());
        mp.put("p13", bean.isP13());
        mp.put("p14", bean.isP14());
        mp.put("p15", bean.isP15());
        mp.put("p16", bean.isP16());
        mp.put("p17", bean.isP17());
        mp.put("p18", bean.isP18());
        mp.put("p19", bean.isP19());
        jsa.add(mp);
        return jsa;
    }

    public JSONArray getGroupList() throws Exception {
        JSONArray jsa = new JSONArray();
        try {

            List<BeanUsergroup> itemList = userGroup.getUserGroupList(connection);
            if (itemList.isEmpty()) {
                LinkedHashMap mp = new LinkedHashMap();
                jsa.add(mp);
            } else {
                Iterator<BeanUsergroup> Listiterator = itemList.iterator();

                while (Listiterator.hasNext()) {
                    BeanUsergroup bean = Listiterator.next();
                    LinkedHashMap mp = new LinkedHashMap();
                    mp.put("userGroup", bean.getUsergroup());

                    jsa.add(mp);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsa;
    }

    public JSONObject saveUserGroup(JsonObject requestJson, Connection connection) throws Exception {
        JSONObject jsa = new JSONObject();

        BeanUsergroup bean = new BeanUsergroup();

        JsonObject userData = requestJson.getAsJsonObject();

        bean.setUsergroup(userData.get("usergroup").getAsString());
        bean.setP1(userData.get("p1").getAsBoolean());
        bean.setP2(userData.get("p2").getAsBoolean());
        bean.setP3(userData.get("p3").getAsBoolean());
        bean.setP4(userData.get("p4").getAsBoolean());
        bean.setP5(userData.get("p5").getAsBoolean());
        bean.setP6(userData.get("p6").getAsBoolean());
        bean.setP7(userData.get("p7").getAsBoolean());
        bean.setP8(userData.get("p8").getAsBoolean());
        bean.setP9(userData.get("p9").getAsBoolean());
        bean.setP10(userData.get("p10").getAsBoolean());
        bean.setP11(userData.get("p11").getAsBoolean());
        bean.setP12(userData.get("p12").getAsBoolean());
        bean.setP13(userData.get("p13").getAsBoolean());
        bean.setP14(userData.get("p14").getAsBoolean());
        bean.setP15(userData.get("p15").getAsBoolean());
        bean.setP16(userData.get("p16").getAsBoolean());
        bean.setP17(userData.get("p17").getAsBoolean());
        bean.setP18(userData.get("p18").getAsBoolean());
        bean.setP19(userData.get("p19").getAsBoolean());
        String status = null;
        if (userGroup.checkExistingUsergroup(connection, bean.getUsergroup())) {
            status = userGroup.updateUserGroup(bean);
        } else {
            status = userGroup.saveUserGroup(bean);
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
