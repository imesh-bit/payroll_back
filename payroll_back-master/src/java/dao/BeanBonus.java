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
public class BeanBonus {

    private int bonusid;
    private int empid;
    private String monthcode;
    private int monthid;
    private BigDecimal amount;
    private BigDecimal payetax;
    private int fyear;
    private String comcode;
    private String user;
    private int empCode;
    private String empName;
    private String division;
    private String Reference;

    public int getBonusid() {
        return bonusid;
    }

    public void setBonusid(int bonusid) {
        this.bonusid = bonusid;
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

    public String getReference() {
        return Reference;
    }

    public void setReference(String Reference) {
        this.Reference = Reference;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
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

    public String getMonthcode() {
        return monthcode;
    }

    public void setMonthcode(String monthcode) {
        this.monthcode = monthcode;
    }

    public int getMonthid() {
        return monthid;
    }

    public void setMonthid(int monthid) {
        this.monthid = monthid;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getPayetax() {
        return payetax;
    }

    public void setPayetax(BigDecimal payetax) {
        this.payetax = payetax;
    }

    public int getFyear() {
        return fyear;
    }

    public void setFyear(int fyear) {
        this.fyear = fyear;
    }

    public String getComcode() {
        return comcode;
    }

    public void setComcode(String comcode) {
        this.comcode = comcode;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public BeanBonus() {
    }

}
