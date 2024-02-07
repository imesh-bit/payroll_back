/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.Date;

/**
 *
 * @author Nimesha
 */
public class BeanSalaryMonth {

    private String monthcode;
    private int year;
    private int monthid;
    private String monthname;
    private Date createDate;
    private boolean runpayroll;
    private boolean stopattendance;
    private boolean stopallowance;
    private boolean stopadvance;
    private String comcode;
    private String user;

    public BeanSalaryMonth() {
    }

    public String getMonthcode() {
        return monthcode;
    }

    public void setMonthcode(String monthcode) {
        this.monthcode = monthcode;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonthid() {
        return monthid;
    }

    public void setMonthid(int monthid) {
        this.monthid = monthid;
    }

    public String getMonthname() {
        return monthname;
    }

    public void setMonthname(String monthname) {
        this.monthname = monthname;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public boolean isStopadvance() {
        return stopadvance;
    }

    public void setStopadvance(boolean stopadvance) {
        this.stopadvance = stopadvance;
    }

    public boolean isRunpayroll() {
        return runpayroll;
    }

    public void setRunpayroll(boolean runpayroll) {
        this.runpayroll = runpayroll;
    }

    public boolean isStopattendance() {
        return stopattendance;
    }

    public void setStopattendance(boolean stopattendance) {
        this.stopattendance = stopattendance;
    }

    public boolean isStopallowance() {
        return stopallowance;
    }

    public void setStopallowance(boolean stopallowance) {
        this.stopallowance = stopallowance;
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
    
    
}
