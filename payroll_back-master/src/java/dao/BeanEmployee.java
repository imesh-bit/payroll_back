/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Nimesha
 */
public class BeanEmployee {

    private int empid;
    private Date date;
    private int empcode;
    private String titel;
    private String initials;
    private String surname;
    private String empinitialname;
    private String empfullname;
    private String nic;
    private Date datebirth;
    private int gender;
    private String empaddress;
    private String tele;
    private String email;
    private String status;
    private Date dateappointment;
    private Date probationenddate;
    private int groupid;
    private int categoryid;
    private String typofemp;
    private String division;
    private String designation;
    private String salarytyp;
    private BigDecimal basicsalary;
    private BigDecimal budgetallowance;
    private BigDecimal bra2;
    private BigDecimal allow1;
    private BigDecimal allow2;
    private BigDecimal allow3;
    private BigDecimal allow4;
    private BigDecimal allow5;
    private BigDecimal deduct1;
    private BigDecimal deduct2;
    private BigDecimal deduct3;
    private BigDecimal deduct4;
    private BigDecimal deduct5;
    private BigDecimal salary;
    private String epfno;
    private String remarks;
    private String comcode;
    private int active;
    private String imgfilename;
    private String user;
    private boolean epfAuto;
    private String salaryMonth;
    private String terminateNote;
    private Date terminateDate;
    private BeanEmployeeBank bankDetails;

    public BeanEmployee() {
    }

    public boolean isEpfAuto() {
        return epfAuto;
    }

    public void setEpfAuto(boolean epfAuto) {
        this.epfAuto = epfAuto;
    }

    public BeanEmployeeBank getBankDetails() {
        return bankDetails;
    }

    public void setBankDetails(BeanEmployeeBank bankDetails) {
        this.bankDetails = bankDetails;
    }

    public int getEmpid() {
        return empid;
    }

    public void setEmpid(int empid) {
        this.empid = empid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getEmpcode() {
        return empcode;
    }

    public void setEmpcode(int empcode) {
        this.empcode = empcode;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmpinitialname() {
        return empinitialname;
    }

    public void setEmpinitialname(String empinitialname) {
        this.empinitialname = empinitialname;
    }

    public String getEmpfullname() {
        return empfullname;
    }

    public void setEmpfullname(String empfullname) {
        this.empfullname = empfullname;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public Date getDatebirth() {
        return datebirth;
    }

    public void setDatebirth(Date datebirth) {
        this.datebirth = datebirth;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getEmpaddress() {
        return empaddress;
    }

    public void setEmpaddress(String empaddress) {
        this.empaddress = empaddress;
    }

    public String getTele() {
        return tele;
    }

    public void setTele(String tele) {
        this.tele = tele;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDateappointment() {
        return dateappointment;
    }

    public void setDateappointment(Date dateappointment) {
        this.dateappointment = dateappointment;
    }

    public Date getProbationenddate() {
        return probationenddate;
    }

    public void setProbationenddate(Date probationenddate) {
        this.probationenddate = probationenddate;
    }

    public int getGroupid() {
        return groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }

    public int getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(int categoryid) {
        this.categoryid = categoryid;
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

    public String getSalarytyp() {
        return salarytyp;
    }

    public void setSalarytyp(String salarytyp) {
        this.salarytyp = salarytyp;
    }

    public BigDecimal getBasicsalary() {
        return basicsalary;
    }

    public void setBasicsalary(BigDecimal basicsalary) {
        this.basicsalary = basicsalary;
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

    public String getEpfno() {
        return epfno;
    }

    public void setEpfno(String epfno) {
        this.epfno = epfno;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getComcode() {
        return comcode;
    }

    public void setComcode(String comcode) {
        this.comcode = comcode;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getImgfilename() {
        return imgfilename;
    }

    public void setImgfilename(String imgfilename) {
        this.imgfilename = imgfilename;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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

    public BigDecimal getSalary() {
        return salary;
    }

    public String getSalaryMonth() {
        return salaryMonth;
    }

    public void setSalaryMonth(String salaryMonth) {
        this.salaryMonth = salaryMonth;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getTerminateNote() {
        return terminateNote;
    }

    public void setTerminateNote(String terminateNote) {
        this.terminateNote = terminateNote;
    }

    public Date getTerminateDate() {
        return terminateDate;
    }

    public void setTerminateDate(Date terminateDate) {
        this.terminateDate = terminateDate;
    }

}
