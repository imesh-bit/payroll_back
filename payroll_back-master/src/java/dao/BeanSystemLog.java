/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author Nimesha
 */
public class BeanSystemLog {

    public static String event;
    public static String description;
    public static String comcode;
    public static String user;
    public static String jwtToken;
    public static String division;
    public static int empid;

    public BeanSystemLog() {
    }

    public static int getEmpid() {
        return empid;
    }

    public static void setEmpid(int empid) {
        BeanSystemLog.empid = empid;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        BeanSystemLog.event = event;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        BeanSystemLog.description = description;
    }

    public static String getComcode() {
        return comcode;
    }

    public void setComcode(String comcode) {
        BeanSystemLog.comcode = comcode;
    }

    public static String getUser() {
        return user;
    }

    public void setUser(String user) {
        BeanSystemLog.user = user;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        BeanSystemLog.jwtToken = jwtToken;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        BeanSystemLog.division = division;
    }

    @Override
    public String toString() {
        return "BeanSystemLog{" + "event=" + event + ", description=" + description + ", comcode=" + comcode + ", user=" + user + ", jwtToken=" + jwtToken + ", division=" + division + '}';
    }

}
