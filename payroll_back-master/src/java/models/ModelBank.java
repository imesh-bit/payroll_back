/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import com.google.gson.JsonObject;
import dao.BeanBank;
import dao.BeanSystemLog;
import daoImpls.ImplBank;
import interfaces.InterfaceBank;
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
public class ModelBank {

    Connection connection;
    InterfaceBank bank;

    public ModelBank(Connection connection) {
        this.connection = connection;
        this.bank = new ImplBank();
    }

    public JSONArray getbankList() throws Exception {
        JSONArray jsa = new JSONArray();
        try {

            List<BeanBank> itemList = bank.getBankList(connection);
            if (itemList.isEmpty()) {
                LinkedHashMap mp = new LinkedHashMap();
                jsa.add(mp);
            } else {
                Iterator<BeanBank> Listiterator = itemList.iterator();

                while (Listiterator.hasNext()) {
                    BeanBank bean = Listiterator.next();
                    LinkedHashMap mp = new LinkedHashMap();
                    mp.put("id", bean.getId());
                    mp.put("bankname", bean.getBankname());
                    mp.put("bankcode", bean.getBankcode());
                    mp.put("branchname", bean.getBranchname());
                    mp.put("branchcode", bean.getBranchcode());
                    mp.put("user", bean.getUser());
                    jsa.add(mp);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsa;
    }

    public JSONObject saveBank(JsonObject requestJson, Connection connection) throws Exception {
        JSONObject jsa = new JSONObject();

        BeanBank bean = new BeanBank();

        JsonObject allowanceData = requestJson.getAsJsonObject();

        bean.setId(allowanceData.get("id").getAsInt());
        if (allowanceData.get("bankname") != null) {
            bean.setBankname(allowanceData.get("bankname").getAsString());
        } else {
            bean.setBankname("");
        }
        if (allowanceData.get("bankcode") != null) {
            bean.setBankcode(allowanceData.get("bankcode").getAsString());
        } else {
            bean.setBankcode("");
        }
        if (allowanceData.get("branchname") != null) {
            bean.setBranchname(allowanceData.get("branchname").getAsString());
        } else {
            bean.setBranchname("NA");
        }
        if (allowanceData.get("branchcode") != null) {
            bean.setBranchcode(allowanceData.get("branchcode").getAsString());
        } else {
            bean.setBranchcode("NA");
        }
        bean.setUser(BeanSystemLog.user);
        bean.setComcode(BeanSystemLog.comcode);

        String status = null;
        if (allowanceData.get("id").getAsInt() != 0) {
            status = bank.updateBank(bean);
        } else {
            status = bank.saveBank(bean);
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

    public JSONObject deleteBank(JsonObject requestJson, Connection connection) throws Exception {
        JSONObject jsa = new JSONObject();

        JsonObject jsonData = requestJson.getAsJsonObject();
        int bankid = Integer.parseInt(jsonData.get("bankid").getAsString());
        String empbank = jsonData.get("empbank").getAsString();

        String status = bank.deleteBank(bankid, empbank);

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
