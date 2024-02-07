/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import dao.BeanLogin;
import dao.BeanSystemLog;
import daoImpls.ImplUserLogin;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import org.json.simple.JSONArray;
import interfaces.InterfaceUserLogin;
import javax.servlet.http.HttpServletRequest;
import util.JWTTokenGenerator;

/**
 *
 * @author Nimesha
 */
public class ModelLogin {

    Connection connection;
    InterfaceUserLogin login;

    public ModelLogin(Connection connection) {
        this.connection = connection;
        login = new ImplUserLogin();
    }

    public JSONArray getLoginDetails(HttpServletRequest request, String userName, String password) throws Exception {

        JSONArray jsa = new JSONArray();

        BeanLogin bean = login.isValidUser(connection, userName, password);

        if (bean.isValiduser()) {
            JWTTokenGenerator tokenGenerator = new JWTTokenGenerator();
            String token = tokenGenerator.createJWT(bean.getLoguserName(), bean.getEmail(), userName);

            BeanSystemLog.empid = bean.getEmpId();
            BeanSystemLog.user = userName;
            BeanSystemLog.comcode = bean.getComcode();
            BeanSystemLog.division = bean.getDivision();
            BeanSystemLog.jwtToken = token;

            Map mp = new HashMap();
            mp.put("validUser", bean.isValiduser());
            mp.put("empid", bean.getEmpId());
            mp.put("comCode", bean.getComcode());
            mp.put("locCode", bean.getLoccode());
            mp.put("division", bean.getDivision());
            mp.put("userGroup", bean.getUsergroup());
            mp.put("p1", bean.getUserPreviledges().isP1());
            mp.put("p2", bean.getUserPreviledges().isP2());
            mp.put("p3", bean.getUserPreviledges().isP3());
            mp.put("p4", bean.getUserPreviledges().isP4());
            mp.put("p5", bean.getUserPreviledges().isP5());
            mp.put("p6", bean.getUserPreviledges().isP6());
            mp.put("p7", bean.getUserPreviledges().isP7());
            mp.put("p8", bean.getUserPreviledges().isP8());
            mp.put("p9", bean.getUserPreviledges().isP9());
            mp.put("p10", bean.getUserPreviledges().isP10());
            mp.put("p11", bean.getUserPreviledges().isP11());
            mp.put("p12", bean.getUserPreviledges().isP12());
            mp.put("p13", bean.getUserPreviledges().isP13());
            mp.put("p14", bean.getUserPreviledges().isP14());
            mp.put("p15", bean.getUserPreviledges().isP15());
            mp.put("p16", bean.getUserPreviledges().isP16());
            mp.put("p17", bean.getUserPreviledges().isP17());
            mp.put("p18", bean.getUserPreviledges().isP18());
            mp.put("p19", bean.getUserPreviledges().isP19());
            mp.put("token", token);

            String status = login.saveLoginDetails(connection);
            if (status.startsWith("Error:")) {
                mp.put("message", status);
                mp.put("type", "error");
                jsa.add(mp);
            } else {
                mp.put("message", "sucess");
                mp.put("type", "sucess");
            }

            jsa.add(mp);

        } else {
            Map mp = new HashMap();
            mp.put("message", "Invalid User!");
            mp.put("type", "error");
            jsa.add(mp);
        }

        return jsa;
    }

}
