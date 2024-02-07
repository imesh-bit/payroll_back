/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import com.google.gson.JsonObject;
import dao.BeanNoncashBenifit;
import daoImpls.ImplNoncashBenifit;
import interfaces.InterfaceNoncashBenifit;
import java.sql.Connection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Nimesha
 */
public class ModelNoncashBenifit {
    
    Connection connection;
    InterfaceNoncashBenifit noncash;
    
    public ModelNoncashBenifit(Connection connection) {
        this.connection = connection;
        this.noncash = new ImplNoncashBenifit();
    }
    
    public JSONArray getNoncashList() throws Exception {
        
        BeanNoncashBenifit bean = noncash.getNoncashList(connection);
        
        JSONArray jsa = new JSONArray();
        LinkedHashMap mp = new LinkedHashMap();
        mp.put("id", bean.getId());
        mp.put("noncash1", bean.getNoncash1());
        mp.put("noncash2", bean.getNoncash2());
        mp.put("noncash3", bean.getNoncash3());
        mp.put("noncash4", bean.getNoncash4());
        mp.put("noncash5", bean.getNoncash5());
        mp.put("noncash6", bean.getNoncash6());
        mp.put("noncash7", bean.getNoncash7());
        mp.put("noncash8", bean.getNoncash8());
        mp.put("noncash9", bean.getNoncash9());
        mp.put("noncash10", bean.getNoncash10());
        
        jsa.add(mp);
        return jsa;
    }
    
    public HashMap getNoncashMap(Connection connection) throws Exception {
        HashMap mp = new HashMap();
        
        BeanNoncashBenifit bean = noncash.getNoncashList(connection);
        
        mp.put("id", bean.getId());
        mp.put("noncash1", bean.getNoncash1());
        mp.put("noncash2", bean.getNoncash2());
        mp.put("noncash3", bean.getNoncash3());
        mp.put("noncash4", bean.getNoncash4());
        mp.put("noncash5", bean.getNoncash5());
        mp.put("noncash6", bean.getNoncash6());
        mp.put("noncash7", bean.getNoncash7());
        mp.put("noncash8", bean.getNoncash8());
        mp.put("noncash9", bean.getNoncash9());
        mp.put("noncash10", bean.getNoncash10());
        
        return mp;
    }
    
    public JSONObject saveNoncash(JsonObject requestJson, Connection connection) throws Exception {
        JSONObject jsa = new JSONObject();
        
        BeanNoncashBenifit bean = new BeanNoncashBenifit();
        
        JsonObject noncashData = requestJson.getAsJsonObject();
        
        bean.setId(noncashData.get("id").getAsInt());
        if (noncashData.get("noncash1") != null) {
            bean.setNoncash1(noncashData.get("noncash1").getAsString());
        } else {
            bean.setNoncash1("");
        }
        if (noncashData.get("noncash2") != null) {
            bean.setNoncash2(noncashData.get("noncash2").getAsString());
        } else {
            bean.setNoncash2("");
        }
        if (noncashData.get("noncash3") != null) {
            bean.setNoncash3(noncashData.get("noncash3").getAsString());
        } else {
            bean.setNoncash3("");
        }
        if (noncashData.get("noncash4") != null) {
            bean.setNoncash4(noncashData.get("noncash4").getAsString());
        } else {
            bean.setNoncash4("");
        }
        if (noncashData.get("noncash5") != null) {
            bean.setNoncash5(noncashData.get("noncash5").getAsString());
        } else {
            bean.setNoncash5("");
        }
        if (noncashData.get("noncash6") != null) {
            bean.setNoncash6(noncashData.get("noncash6").getAsString());
        } else {
            bean.setNoncash6("");
        }
        if (noncashData.get("noncash7") != null) {
            bean.setNoncash7(noncashData.get("noncash7").getAsString());
        } else {
            bean.setNoncash7("");
        }
        if (noncashData.get("noncash8") != null) {
            bean.setNoncash8(noncashData.get("noncash8").getAsString());
        } else {
            bean.setNoncash8("");
        }
        if (noncashData.get("noncash9") != null) {
            bean.setNoncash9(noncashData.get("noncash9").getAsString());
        } else {
            bean.setNoncash9("");
        }
        if (noncashData.get("noncash9") != null) {
            bean.setNoncash9(noncashData.get("noncash9").getAsString());
        } else {
            bean.setNoncash9("");
        }
        if (noncashData.get("noncash10") != null) {
            bean.setNoncash10(noncashData.get("noncash10").getAsString());
        } else {
            bean.setNoncash10("");
        }
        
        String status = null;
        if (noncashData.get("id").getAsInt() != 0) {
            status = noncash.updateNoncash(bean);
        } else {
            status = noncash.saveNoncash(bean);
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
