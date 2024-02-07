/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Nimesha
 */
public class JDBC {
    //cloud
    static String IpAddress = "dbaas-db-4193036-do-user-14072158-0.c.db.ondigitalocean.com:25060";
    static String user = "doadmin";
    static String password = "AVNS_OYS2OOX5aE_vGe-Nlau";

  /*  //test db
    static String IpAddress = "127.0.0.1:3306";
    static String user = "root";
    static String password = "";*/

    public static Connection con_payroll() throws Exception {
        String url;
        url = "jdbc:mysql://" + IpAddress + "/payroll?zeroDateTimeBehavior=convertToNull&noAccessToProcedureBodies=true&autoReconnect=true&useUnicode=yes&characterEncoding=UTF-8&useSSL=true";
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection con = DriverManager.getConnection(url, user, password);
        return con;
    }

}
