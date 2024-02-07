/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import dao.BeanTypeofEmployee;
import daoImpls.ImplTypeofEmployee;
import interfaces.InterfaceTypeofEmployee;
import java.sql.Connection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import org.json.simple.JSONArray;

/**
 *
 * @author Nimesha
 */
public class ModelEmpTypes {

    Connection connection;
    InterfaceTypeofEmployee employee;

    public ModelEmpTypes(Connection connection) {
        this.connection = connection;
        this.employee = new ImplTypeofEmployee();
    }

    public JSONArray getEmpTypeList() throws Exception {
        JSONArray jsa = new JSONArray();
        try {

            List<BeanTypeofEmployee> itemList = employee.getEmpTypeList(connection);
            if (itemList.isEmpty()) {
                LinkedHashMap mp = new LinkedHashMap();
                jsa.add(mp);
            } else {
                Iterator<BeanTypeofEmployee> Listiterator = itemList.iterator();

                while (Listiterator.hasNext()) {
                    BeanTypeofEmployee bean = Listiterator.next();
                    LinkedHashMap mp = new LinkedHashMap();
                    mp.put("id", bean.getId());
                    mp.put("emptyp", bean.getEmpType());
                    mp.put("isExecutive", bean.getIsExecutive());

                    jsa.add(mp);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsa;
    }
}
