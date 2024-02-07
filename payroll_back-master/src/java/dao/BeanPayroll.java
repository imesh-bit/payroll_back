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
public class BeanPayroll {

    private String monthcode;
    private String salarytype;
    private int empid;
    private int empcode;
    private String empname;
    private int categoryid;
    private String category;
    private int groupid;
    private String group;
    private String empStatus;
    private String typofemp;
    private String division;
    private String designation;
    private BigDecimal basicsalary;
    private BigDecimal basicarreas;
    private BigDecimal budgetallowance;
    private BigDecimal bra2;
    private BigDecimal nopay;
    private BigDecimal nopayDates;
    private String allow1Name;
    private BigDecimal allow1;
    private String allow2Name;
    private BigDecimal allow2;
    private String allow3Name;
    private BigDecimal allow3;
    private String allow4Name;
    private BigDecimal allow4;
    private String allow5Name;
    private BigDecimal allow5;
    private BigDecimal noOfAttendance;
    private BigDecimal saladvance;
    private BigDecimal payCutvalue;
    private BigDecimal otpay;
    private int loanid;
    private BigDecimal loanvalue;
    private String deduct1Name;
    private BigDecimal deduct1;
    private String deduct2Name;
    private BigDecimal deduct2;
    private String deduct3Name;
    private BigDecimal deduct3;
    private String deduct4Name;
    private BigDecimal deduct4;
    private String deduct5Name;
    private BigDecimal deduct5;
    private BigDecimal paye;
    private BigDecimal etf;
    private BigDecimal epf8;
    private BigDecimal epf12;
    private BigDecimal grossalary;
    private BigDecimal netsalary;
    private BigDecimal bonus;
    private String user;
    private String comcode;
    private boolean activate;
    private String remark;
    private String noncash1Name;
    private BigDecimal noncash1;
    private String noncash2Name;
    private BigDecimal noncash2;
    private String noncash3Name;
    private BigDecimal noncash3;
    private String noncash4Name;
    private BigDecimal noncash4;
    private String noncash5Name;
    private BigDecimal noncash5;
    private String noncash6Name;
    private BigDecimal noncash6;
    private String noncash7Name;
    private BigDecimal noncash7;
    private String noncash8Name;
    private BigDecimal noncash8;
    private String noncash9Name;
    private BigDecimal noncash9;
    private String noncash10Name;
    private BigDecimal noncash10;
    private BeanLoan beanLoan;
    private BeanEmployeeBank employeeBank;

    public BeanPayroll() {
    }

    public BeanLoan getBeanLoan() {
        return beanLoan;
    }

    public void setBeanLoan(BeanLoan beanLoan) {
        this.beanLoan = beanLoan;
    }

    public BeanEmployeeBank getEmployeeBank() {
        return employeeBank;
    }

    public void setEmployeeBank(BeanEmployeeBank employeeBank) {
        this.employeeBank = employeeBank;
    }

    public String getEmpStatus() {
        return empStatus;
    }

    public void setEmpStatus(String empStatus) {
        this.empStatus = empStatus;
    }

    public String getSalarytype() {
        return salarytype;
    }

    public void setSalarytype(String salarytype) {
        this.salarytype = salarytype;
    }

    public int getEmpcode() {
        return empcode;
    }

    public void setEmpcode(int empcode) {
        this.empcode = empcode;
    }

    public String getMonthcode() {
        return monthcode;
    }

    public void setMonthcode(String monthcode) {
        this.monthcode = monthcode;
    }

    public int getEmpid() {
        return empid;
    }

    public void setEmpid(int empid) {
        this.empid = empid;
    }

    public String getEmpname() {
        return empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname;
    }

    public int getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(int categoryid) {
        this.categoryid = categoryid;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getGroupid() {
        return groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getTypofemp() {
        return typofemp;
    }

    public void setTypofemp(String typofemp) {
        this.typofemp = typofemp;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public BigDecimal getBasicsalary() {
        return basicsalary;
    }

    public void setBasicsalary(BigDecimal basicsalary) {
        this.basicsalary = basicsalary;
    }

    public BigDecimal getBasicarreas() {
        return basicarreas;
    }

    public void setBasicarreas(BigDecimal basicarreas) {
        this.basicarreas = basicarreas;
    }

    public BigDecimal getBudgetallowance() {
        return budgetallowance;
    }

    public void setBudgetallowance(BigDecimal budgetallowance) {
        this.budgetallowance = budgetallowance;
    }

    public BigDecimal getBra2() {
        return bra2;
    }

    public void setBra2(BigDecimal bra2) {
        this.bra2 = bra2;
    }

    public BigDecimal getAllow1() {
        return allow1;
    }

    public void setAllow1(BigDecimal allow1) {
        this.allow1 = allow1;
    }

    public BigDecimal getAllow2() {
        return allow2;
    }

    public void setAllow2(BigDecimal allow2) {
        this.allow2 = allow2;
    }

    public BigDecimal getAllow3() {
        return allow3;
    }

    public void setAllow3(BigDecimal allow3) {
        this.allow3 = allow3;
    }

    public BigDecimal getAllow4() {
        return allow4;
    }

    public void setAllow4(BigDecimal allow4) {
        this.allow4 = allow4;
    }

    public BigDecimal getAllow5() {
        return allow5;
    }

    public void setAllow5(BigDecimal allow5) {
        this.allow5 = allow5;
    }

    public BigDecimal getSaladvance() {
        return saladvance;
    }

    public void setSaladvance(BigDecimal saladvance) {
        this.saladvance = saladvance;
    }

    public int getLoanid() {
        return loanid;
    }

    public void setLoanid(int loanid) {
        this.loanid = loanid;
    }

    public BigDecimal getLoanvalue() {
        return loanvalue;
    }

    public void setLoanvalue(BigDecimal loanvalue) {
        this.loanvalue = loanvalue;
    }

    public BigDecimal getDeduct1() {
        return deduct1;
    }

    public void setDeduct1(BigDecimal deduct1) {
        this.deduct1 = deduct1;
    }

    public BigDecimal getDeduct2() {
        return deduct2;
    }

    public void setDeduct2(BigDecimal deduct2) {
        this.deduct2 = deduct2;
    }

    public BigDecimal getDeduct3() {
        return deduct3;
    }

    public void setDeduct3(BigDecimal deduct3) {
        this.deduct3 = deduct3;
    }

    public BigDecimal getDeduct4() {
        return deduct4;
    }

    public void setDeduct4(BigDecimal deduct4) {
        this.deduct4 = deduct4;
    }

    public BigDecimal getDeduct5() {
        return deduct5;
    }

    public void setDeduct5(BigDecimal deduct5) {
        this.deduct5 = deduct5;
    }

    public BigDecimal getPaye() {
        return paye;
    }

    public void setPaye(BigDecimal paye) {
        this.paye = paye;
    }

    public BigDecimal getEtf() {
        return etf;
    }

    public void setEtf(BigDecimal etf) {
        this.etf = etf;
    }

    public BigDecimal getEpf8() {
        return epf8;
    }

    public void setEpf8(BigDecimal epf8) {
        this.epf8 = epf8;
    }

    public BigDecimal getEpf12() {
        return epf12;
    }

    public void setEpf12(BigDecimal epf12) {
        this.epf12 = epf12;
    }

    public BigDecimal getGrossalary() {
        return grossalary;
    }

    public void setGrossalary(BigDecimal grossalary) {
        this.grossalary = grossalary;
    }

    public BigDecimal getNetsalary() {
        return netsalary;
    }

    public void setNetsalary(BigDecimal netsalary) {
        this.netsalary = netsalary;
    }

    public BigDecimal getBonus() {
        return bonus;
    }

    public void setBonus(BigDecimal bonus) {
        this.bonus = bonus;
    }

    public BigDecimal getNopayDates() {
        return nopayDates;
    }

    public void setNopayDates(BigDecimal nopayDates) {
        this.nopayDates = nopayDates;
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

    public boolean isActivate() {
        return activate;
    }

    public void setActivate(boolean activate) {
        this.activate = activate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAllow1Name() {
        return allow1Name;
    }

    public void setAllow1Name(String allow1Name) {
        this.allow1Name = allow1Name;
    }

    public String getAllow2Name() {
        return allow2Name;
    }

    public void setAllow2Name(String allow2Name) {
        this.allow2Name = allow2Name;
    }

    public String getAllow3Name() {
        return allow3Name;
    }

    public void setAllow3Name(String allow3Name) {
        this.allow3Name = allow3Name;
    }

    public String getAllow4Name() {
        return allow4Name;
    }

    public void setAllow4Name(String allow4Name) {
        this.allow4Name = allow4Name;
    }

    public String getAllow5Name() {
        return allow5Name;
    }

    public void setAllow5Name(String allow5Name) {
        this.allow5Name = allow5Name;
    }

    public BigDecimal getNoOfAttendance() {
        return noOfAttendance;
    }

    public void setNoOfAttendance(BigDecimal noOfAttendance) {
        this.noOfAttendance = noOfAttendance;
    }

    public BigDecimal getPayCutvalue() {
        return payCutvalue;
    }

    public void setPayCutvalue(BigDecimal payCutvalue) {
        this.payCutvalue = payCutvalue;
    }

    public String getDeduct1Name() {
        return deduct1Name;
    }

    public void setDeduct1Name(String deduct1Name) {
        this.deduct1Name = deduct1Name;
    }

    public String getDeduct2Name() {
        return deduct2Name;
    }

    public void setDeduct2Name(String deduct2Name) {
        this.deduct2Name = deduct2Name;
    }

    public String getDeduct3Name() {
        return deduct3Name;
    }

    public void setDeduct3Name(String deduct3Name) {
        this.deduct3Name = deduct3Name;
    }

    public String getDeduct4Name() {
        return deduct4Name;
    }

    public void setDeduct4Name(String deduct4Name) {
        this.deduct4Name = deduct4Name;
    }

    public String getDeduct5Name() {
        return deduct5Name;
    }

    public void setDeduct5Name(String deduct5Name) {
        this.deduct5Name = deduct5Name;
    }

    public BigDecimal getNopay() {
        return nopay;
    }

    public void setNopay(BigDecimal nopay) {
        this.nopay = nopay;
    }

    public BigDecimal getOtpay() {
        return otpay;
    }

    public void setOtpay(BigDecimal otpay) {
        this.otpay = otpay;
    }

    public String getNoncash1Name() {
        return noncash1Name;
    }

    public void setNoncash1Name(String noncash1Name) {
        this.noncash1Name = noncash1Name;
    }

    public BigDecimal getNoncash1() {
        return noncash1;
    }

    public void setNoncash1(BigDecimal noncash1) {
        this.noncash1 = noncash1;
    }

    public String getNoncash2Name() {
        return noncash2Name;
    }

    public void setNoncash2Name(String noncash2Name) {
        this.noncash2Name = noncash2Name;
    }

    public BigDecimal getNoncash2() {
        return noncash2;
    }

    public void setNoncash2(BigDecimal noncash2) {
        this.noncash2 = noncash2;
    }

    public String getNoncash3Name() {
        return noncash3Name;
    }

    public void setNoncash3Name(String noncash3Name) {
        this.noncash3Name = noncash3Name;
    }

    public BigDecimal getNoncash3() {
        return noncash3;
    }

    public void setNoncash3(BigDecimal noncash3) {
        this.noncash3 = noncash3;
    }

    public String getNoncash4Name() {
        return noncash4Name;
    }

    public void setNoncash4Name(String noncash4Name) {
        this.noncash4Name = noncash4Name;
    }

    public BigDecimal getNoncash4() {
        return noncash4;
    }

    public void setNoncash4(BigDecimal noncash4) {
        this.noncash4 = noncash4;
    }

    public String getNoncash5Name() {
        return noncash5Name;
    }

    public void setNoncash5Name(String noncash5Name) {
        this.noncash5Name = noncash5Name;
    }

    public BigDecimal getNoncash5() {
        return noncash5;
    }

    public void setNoncash5(BigDecimal noncash5) {
        this.noncash5 = noncash5;
    }

    public String getNoncash6Name() {
        return noncash6Name;
    }

    public void setNoncash6Name(String noncash6Name) {
        this.noncash6Name = noncash6Name;
    }

    public BigDecimal getNoncash6() {
        return noncash6;
    }

    public void setNoncash6(BigDecimal noncash6) {
        this.noncash6 = noncash6;
    }

    public String getNoncash7Name() {
        return noncash7Name;
    }

    public void setNoncash7Name(String noncash7Name) {
        this.noncash7Name = noncash7Name;
    }

    public BigDecimal getNoncash7() {
        return noncash7;
    }

    public void setNoncash7(BigDecimal noncash7) {
        this.noncash7 = noncash7;
    }

    public String getNoncash8Name() {
        return noncash8Name;
    }

    public void setNoncash8Name(String noncash8Name) {
        this.noncash8Name = noncash8Name;
    }

    public BigDecimal getNoncash8() {
        return noncash8;
    }

    public void setNoncash8(BigDecimal noncash8) {
        this.noncash8 = noncash8;
    }

    public String getNoncash9Name() {
        return noncash9Name;
    }

    public void setNoncash9Name(String noncash9Name) {
        this.noncash9Name = noncash9Name;
    }

    public BigDecimal getNoncash9() {
        return noncash9;
    }

    public void setNoncash9(BigDecimal noncash9) {
        this.noncash9 = noncash9;
    }

    public String getNoncash10Name() {
        return noncash10Name;
    }

    public void setNoncash10Name(String noncash10Name) {
        this.noncash10Name = noncash10Name;
    }

    public BigDecimal getNoncash10() {
        return noncash10;
    }

    public void setNoncash10(BigDecimal noncash10) {
        this.noncash10 = noncash10;
    }

}
