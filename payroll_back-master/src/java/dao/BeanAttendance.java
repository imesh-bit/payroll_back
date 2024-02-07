/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.math.BigDecimal;

/**
 *
 * @author Nimesha
 */
public class BeanAttendance {

    private int id;
    private int empid;
    private int empCode;
    private String empName;
    private String monthCode;
    private String comCode;
    private String division;
    private BigDecimal noofdates;
    private String user;
    private BigDecimal attendanceAllowance;

    public BeanAttendance() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public int getEmpid() {
        return empid;
    }

    public void setEmpid(int empid) {
        this.empid = empid;
    }

    public int getEmpCode() {
        return empCode;
    }

    public void setEmpCode(int empCode) {
        this.empCode = empCode;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getComCode() {
        return comCode;
    }

    public void setComCode(String comCode) {
        this.comCode = comCode;
    }

    public String getMonthCode() {
        return monthCode;
    }

    public void setMonthCode(String monthCode) {
        this.monthCode = monthCode;
    }

    public BigDecimal getNoofdates() {
        return noofdates;
    }

    public void setNoofdates(BigDecimal noofdates) {
        this.noofdates = noofdates;
    }  

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public BigDecimal getAttendanceAllowance() {
        return attendanceAllowance;
    }

    public void setAttendanceAllowance(BigDecimal attendanceAllowance) {
        this.attendanceAllowance = attendanceAllowance;
    }

}
