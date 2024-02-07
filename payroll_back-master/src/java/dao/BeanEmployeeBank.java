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
public class BeanEmployeeBank {

    private int empid;
    private int bankid1;
    private String accno1;
    private BigDecimal amount1;
    private int bankid2;
    private String accno2;
    private BigDecimal amount2;
    private String user;
    private String comcode;

    public BeanEmployeeBank() {
    }

    public int getEmpid() {
        return empid;
    }

    public void setEmpid(int empid) {
        this.empid = empid;
    }

    public int getBankid1() {
        return bankid1;
    }

    public void setBankid1(int bankid1) {
        this.bankid1 = bankid1;
    }

    public String getAccno1() {
        return accno1;
    }

    public void setAccno1(String accno1) {
        this.accno1 = accno1;
    }

    public BigDecimal getAmount1() {
        return amount1;
    }

    public void setAmount1(BigDecimal amount1) {
        this.amount1 = amount1;
    }

    public int getBankid2() {
        return bankid2;
    }

    public void setBankid2(int bankid2) {
        this.bankid2 = bankid2;
    }

    public String getAccno2() {
        return accno2;
    }

    public void setAccno2(String accno2) {
        this.accno2 = accno2;
    }

    public BigDecimal getAmount2() {
        return amount2;
    }

    public void setAmount2(BigDecimal amount2) {
        this.amount2 = amount2;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getComcode() {
        return comcode;
    }

    public void setComcode(String comcode) {
        this.comcode = comcode;
    }
    
    
}
