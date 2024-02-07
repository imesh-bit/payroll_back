/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author Nimesha
 */
public class BeanLogin {

    private int empId;
    private String user;
    private String loguserName;
    private String email;
    private String password;
    private String comcode;
    private String loccode;
    private String usergroup;
    private boolean validuser;
    private String division;
    private BeanUsergroup userPreviledges;

    public BeanLogin() {
    }

    public String getLoguserName() {
        return loguserName;
    }

    public void setLoguserName(String loguserName) {
        this.loguserName = loguserName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getComcode() {
        return comcode;
    }

    public void setComcode(String comcode) {
        this.comcode = comcode;
    }

    public String getLoccode() {
        return loccode;
    }

    public void setLoccode(String loccode) {
        this.loccode = loccode;
    }

    public String getUsergroup() {
        return usergroup;
    }

    public void setUsergroup(String usergroup) {
        this.usergroup = usergroup;
    }

    public boolean isValiduser() {
        return validuser;
    }

    public void setValiduser(boolean validuser) {
        this.validuser = validuser;
    }

    public BeanUsergroup getUserPreviledges() {
        return userPreviledges;
    }

    public void setUserPreviledges(BeanUsergroup userPreviledges) {
        this.userPreviledges = userPreviledges;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    
  
    

}
