/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Nimesha
 */
public class BeanLoan {

    private int loanid;
    private Date loandate;
    private BigDecimal loanvalue;
    private String reason;
    private BigDecimal monthlydeduction;
    private int noofinstallments;
    private String deductfrom;
    private int garanteeid1;
    private int garanteeid2;
    private boolean setoff;
    private int empid;
    private int empcode;
    private String empname;
    private String designation;
    private String typeofloan;
    private String comcode;
    private String user;
    private BigDecimal outstanding;
    private Date settlementdate;
    private BigDecimal settlementamount;
    private String settlementnote;
    private BigDecimal consolidatedSalary;
    private List<BeanLoanSettlement> settlementList;

    public BeanLoan() {
    }

    public int getLoanid() {
        return loanid;
    }

    public void setLoanid(int loanid) {
        this.loanid = loanid;
    }

    public BigDecimal getConsolidatedSalary() {
        return consolidatedSalary;
    }

    public void setConsolidatedSalary(BigDecimal consolidatedSalary) {
        this.consolidatedSalary = consolidatedSalary;
    }

    public Date getLoandate() {
        return loandate;
    }

    public void setLoandate(Date loandate) {
        this.loandate = loandate;
    }

    public BigDecimal getLoanvalue() {
        return loanvalue;
    }

    public void setLoanvalue(BigDecimal loanvalue) {
        this.loanvalue = loanvalue;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public BigDecimal getMonthlydeduction() {
        return monthlydeduction;
    }

    public void setMonthlydeduction(BigDecimal monthlydeduction) {
        this.monthlydeduction = monthlydeduction;
    }

    public int getNoofinstallments() {
        return noofinstallments;
    }

    public void setNoofinstallments(int noofinstallments) {
        this.noofinstallments = noofinstallments;
    }

    public String getDeductfrom() {
        return deductfrom;
    }

    public void setDeductfrom(String deductfrom) {
        this.deductfrom = deductfrom;
    }

    public int getGaranteeid1() {
        return garanteeid1;
    }

    public void setGaranteeid1(int garanteeid1) {
        this.garanteeid1 = garanteeid1;
    }

    public int getGaranteeid2() {
        return garanteeid2;
    }

    public void setGaranteeid2(int garanteeid2) {
        this.garanteeid2 = garanteeid2;
    }

    public boolean isSetoff() {
        return setoff;
    }

    public void setSetoff(boolean setoff) {
        this.setoff = setoff;
    }

    public int getEmpid() {
        return empid;
    }

    public void setEmpid(int empid) {
        this.empid = empid;
    }

    public String getTypeofloan() {
        return typeofloan;
    }

    public void setTypeofloan(String typeofloan) {
        this.typeofloan = typeofloan;
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

    public BigDecimal getOutstanding() {
        return outstanding;
    }

    public void setOutstanding(BigDecimal outstanding) {
        this.outstanding = outstanding;
    }

    public Date getSettlementdate() {
        return settlementdate;
    }

    public void setSettlementdate(Date settlementdate) {
        this.settlementdate = settlementdate;
    }

    public BigDecimal getSettlementamount() {
        return settlementamount;
    }

    public void setSettlementamount(BigDecimal settlementamount) {
        this.settlementamount = settlementamount;
    }

    public String getSettlementnote() {
        return settlementnote;
    }

    public void setSettlementnote(String settlementnote) {
        this.settlementnote = settlementnote;
    }

    public List<BeanLoanSettlement> getSettlementList() {
        return settlementList;
    }

    public void setSettlementList(List<BeanLoanSettlement> settlementList) {
        this.settlementList = settlementList;
    }

    public int getEmpcode() {
        return empcode;
    }

    public void setEmpcode(int empcode) {
        this.empcode = empcode;
    }

    public String getEmpname() {
        return empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    @Override
    public String toString() {
        return "BeanLoan{" + "loanid=" + loanid + ", loanvalue=" + loanvalue + ", monthlydeduction=" + monthlydeduction + ", noofinstallments=" + noofinstallments + ", deductfrom=" + deductfrom + ", empid=" + empid + '}';
    }

}
