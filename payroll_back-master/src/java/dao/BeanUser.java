package dao;

import java.util.Date;

/**
 *
 * @author Nimesha
 */
public class BeanUser {

    private int empid;
    private String username;
    private String password;
    private String fullname;
    private String comcode;
    private String loccode;
    private Date regdate;
    private Date terminatedate;
    private String usergroup;
    private String email;
    private String mobileno;
    private boolean status;
    private String dataentryuser;

    public BeanUser() {
    }

    public int getEmpid() {
        return empid;
    }

    public void setEmpid(int empid) {
        this.empid = empid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
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

    public Date getRegdate() {
        return regdate;
    }

    public void setRegdate(Date regdate) {
        this.regdate = regdate;
    }

    public Date getTerminatedate() {
        return terminatedate;
    }

    public void setTerminatedate(Date terminatedate) {
        this.terminatedate = terminatedate;
    }

    public String getUsergroup() {
        return usergroup;
    }

    public void setUsergroup(String usergroup) {
        this.usergroup = usergroup;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getDataentryuser() {
        return dataentryuser;
    }

    public void setDataentryuser(String dataentryuser) {
        this.dataentryuser = dataentryuser;
    }
    
    
}
