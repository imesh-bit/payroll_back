/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package threads.payroll;

import dao.BeanAttendance;
import dao.BeanBonus;
import dao.BeanEmployee;
import dao.BeanEmployeeBank;
import dao.BeanLoan;
import dao.BeanPayroll;
import dao.BeanSalaryAdvance;
import dao.BeanSystemLog;
import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.ModelPayroll;
import util.JDBC;

/**
 *
 * @author Nimesha
 */
public class AutoSaveThread extends Thread {

    int Progress = 1;
    List<BeanEmployee> beansList;
    List<String> errorList = new ArrayList<>();
    String saveString = "";

    ModelPayroll model;
    BeanEmployee employeeDetails;
    BeanEmployeeBank empBank;
    BeanLoan empLoan;
    BeanSalaryAdvance salaryAdvance;
    BeanAttendance attendance;
    BeanBonus empBonus;
    Map allowanceNameList;
    Map deductionNameList;
    Map noncashNameList;
    Map allowanceReqList;
    Map noncashReqList;

    boolean chkEmpBasicDataThread = false;
    boolean chkEmpBankDataThread = false;
    boolean chkEmpLoanDataThread = false;
    boolean chkEmpAllowanceDataThread = false;
    boolean chkEmpNoncashDataThread = false;
    boolean chkEmpSalaryAdvanceDataThread = false;
    boolean chkEmpAttendanceDataThread = false;
    boolean chkEmpBonusDataThread = false;

    public AutoSaveThread(List<BeanEmployee> beansList, ModelPayroll model) {
        this.model = model;
        this.beansList = beansList;
    }

    @Override
    public void run() {

        List<BeanPayroll> payrollList = new ArrayList<>();
        Connection emp_connConnection = null;

        if (!beansList.isEmpty()) {
            try {
                emp_connConnection = JDBC.con_payroll();
                for (final BeanEmployee efpb : beansList) {

                    Thread empBasicDataThread = new empBasicDataThread(efpb, emp_connConnection);
                    Thread empBankDataThread = new empBankDataThread(efpb, emp_connConnection);
                    Thread empLoanDataThread = new empLoanDataThread(efpb, emp_connConnection);
                    Thread empAllownaceDataThread = new empAllowanceDataThread(efpb, emp_connConnection);
                    Thread empNoncashDataThread = new empNoncashDataThread(efpb, emp_connConnection);
                    Thread empSalaryAdvanceDataThread = new empSalaryAdvanceDataThread(efpb, emp_connConnection);
                    Thread empAttendanceDataThread = new empAttendanceDataThread(efpb, emp_connConnection);
                    Thread empBonusDataThread = new empBonusThread(efpb, emp_connConnection);

                    empBasicDataThread.start();
                    empBankDataThread.start();
                    empLoanDataThread.start();
                    empAllownaceDataThread.start();
                    empNoncashDataThread.start();
                    empSalaryAdvanceDataThread.start();
                    empAttendanceDataThread.start();
                    empBonusDataThread.start();

                    empBonusDataThread.join();
                    empAttendanceDataThread.join();
                    empSalaryAdvanceDataThread.join();
                    empNoncashDataThread.join();
                    empAllownaceDataThread.join();
                    empLoanDataThread.join();
                    empBankDataThread.join();
                    empBasicDataThread.join();

                    if (chkEmpBasicDataThread && chkEmpBankDataThread && chkEmpLoanDataThread && chkEmpAllowanceDataThread && chkEmpNoncashDataThread
                            && chkEmpSalaryAdvanceDataThread && chkEmpAttendanceDataThread && chkEmpBonusDataThread) {

                        if (employeeDetails != null) {

                            HashMap map;
                            map = model.EpfEtf_Calculation(emp_connConnection,
                                    efpb.getSalaryMonth(),
                                    (employeeDetails.getEmpcode() > 0),//EPFHave
                                    employeeDetails.getBasicsalary(),
                                    BigDecimal.ZERO,//salarre
                                    employeeDetails.getBudgetallowance(),
                                    employeeDetails.getBra2(),
                                    BigDecimal.ZERO,//payCut
                                    BigDecimal.ZERO,//NoPay
                                    attendance.getAttendanceAllowance(),//Attendance Incentive
                                    employeeDetails.getAllow2(),//Performance Incentive
                                    new BigDecimal(allowanceReqList.get("allow3") == null ? "0" : allowanceReqList.get("allow3").toString()).round(MathContext.DECIMAL128),//LeaveEncashment
                                    new BigDecimal(allowanceReqList.get("allow4") == null ? "0" : allowanceReqList.get("allow4").toString()).round(MathContext.DECIMAL128),
                                    new BigDecimal(allowanceReqList.get("allow5") == null ? "0" : allowanceReqList.get("allow5").toString()).round(MathContext.DECIMAL128),
                                    salaryAdvance != null ? salaryAdvance.getAmount() : BigDecimal.ZERO,
                                    BigDecimal.ZERO,//otpay
                                    empLoan != null ? empLoan.getMonthlydeduction() : BigDecimal.ZERO,
                                    employeeDetails.getDeduct1(),
                                    employeeDetails.getDeduct2(),
                                    employeeDetails.getDeduct3(),
                                    employeeDetails.getDeduct4(),
                                    employeeDetails.getDeduct5(),
                                    empBonus != null ? empBonus.getAmount() : BigDecimal.ZERO,//bonus
                                    new BigDecimal(noncashReqList.get("noncash1") == null ? "0" : noncashReqList.get("noncash1").toString()).round(MathContext.DECIMAL128), //Noncash Benifit
                                    new BigDecimal(noncashReqList.get("noncash2") == null ? "0" : noncashReqList.get("noncash2").toString()).round(MathContext.DECIMAL128),
                                    new BigDecimal(noncashReqList.get("noncash3") == null ? "0" : noncashReqList.get("noncash3").toString()).round(MathContext.DECIMAL128),
                                    new BigDecimal(noncashReqList.get("noncash4") == null ? "0" : noncashReqList.get("noncash4").toString()).round(MathContext.DECIMAL128),
                                    new BigDecimal(noncashReqList.get("noncash5") == null ? "0" : noncashReqList.get("noncash5").toString()).round(MathContext.DECIMAL128),
                                    new BigDecimal(noncashReqList.get("noncash6") == null ? "0" : noncashReqList.get("noncash6").toString()).round(MathContext.DECIMAL128),
                                    new BigDecimal(noncashReqList.get("noncash7") == null ? "0" : noncashReqList.get("noncash7").toString()).round(MathContext.DECIMAL128),
                                    new BigDecimal(noncashReqList.get("noncash8") == null ? "0" : noncashReqList.get("noncash8").toString()).round(MathContext.DECIMAL128),
                                    new BigDecimal(noncashReqList.get("noncash9") == null ? "0" : noncashReqList.get("noncash9").toString()).round(MathContext.DECIMAL128),
                                    new BigDecimal(noncashReqList.get("noncash10") == null ? "0" : noncashReqList.get("noncash10").toString()).round(MathContext.DECIMAL128));

                            BeanPayroll bean = new BeanPayroll();
                            bean.setMonthcode(efpb.getSalaryMonth());
                            bean.setEmpid(efpb.getEmpid());
                            bean.setEmpname(employeeDetails.getEmpinitialname());
                            bean.setEmpStatus(employeeDetails.getStatus());
                            bean.setCategory(model.getCategories(emp_connConnection).get(employeeDetails.getCategoryid()));
                            bean.setCategoryid(employeeDetails.getCategoryid());
                            bean.setGroup(model.getOccupationalGroups(emp_connConnection).get(employeeDetails.getGroupid()));
                            bean.setGroupid(employeeDetails.getGroupid());
                            bean.setTypofemp(employeeDetails.getTypofemp());
                            bean.setDivision(employeeDetails.getDivision());
                            bean.setDesignation(employeeDetails.getDesignation());
                            bean.setSalarytype(employeeDetails.getSalarytyp());
                            bean.setBasicsalary(employeeDetails.getBasicsalary());
                            bean.setBasicarreas(BigDecimal.ZERO);//Salary Arreas
                            bean.setBudgetallowance(employeeDetails.getBudgetallowance());
                            bean.setBra2(employeeDetails.getBra2());
                            bean.setNoOfAttendance(attendance.getNoofdates());
                            bean.setBonus(empBonus != null ? empBonus.getAmount() : BigDecimal.ZERO);//Bonus
                            bean.setAllow1Name(attendance.getAttendanceAllowance().compareTo(BigDecimal.ZERO) == 1 ? allowanceNameList.get("allow1").toString() : "");
                            bean.setAllow1(attendance.getAttendanceAllowance());
                            bean.setAllow2Name(allowanceNameList.get("allow2").toString());
                            bean.setAllow2(employeeDetails.getAllow2());
                            bean.setAllow3Name(allowanceNameList.get("allow3").toString());
                            bean.setAllow3(new BigDecimal(allowanceReqList.get("allow3") == null ? "0" : allowanceReqList.get("allow3").toString()).round(MathContext.DECIMAL128));
                            bean.setAllow4Name(allowanceNameList.get("allow4").toString());
                            bean.setAllow4(new BigDecimal(allowanceReqList.get("allow4") == null ? "0" : allowanceReqList.get("allow4").toString()).round(MathContext.DECIMAL128));
                            bean.setAllow5Name(allowanceNameList.get("allow5").toString());
                            bean.setAllow5(new BigDecimal(allowanceReqList.get("allow5") == null ? "0" : allowanceReqList.get("allow5").toString()).round(MathContext.DECIMAL128));
                            bean.setPayCutvalue(BigDecimal.ZERO);
                            bean.setNopay(BigDecimal.ZERO);
                            bean.setNopayDates(BigDecimal.ZERO);
                            bean.setSaladvance(salaryAdvance != null ? salaryAdvance.getAmount() : BigDecimal.ZERO);
                            bean.setOtpay(BigDecimal.ZERO);
                            bean.setLoanid(empLoan != null ? empLoan.getLoanid() : 0);
                            bean.setLoanvalue(empLoan != null ? empLoan.getMonthlydeduction() : BigDecimal.ZERO);
                            bean.setDeduct1Name(deductionNameList.get("deduct1").toString());
                            bean.setDeduct1(employeeDetails.getDeduct1());
                            bean.setDeduct2Name(deductionNameList.get("deduct2").toString());
                            bean.setDeduct2(employeeDetails.getDeduct2());
                            bean.setDeduct3Name(deductionNameList.get("deduct3").toString());
                            bean.setDeduct3(employeeDetails.getDeduct3());
                            bean.setDeduct4Name(deductionNameList.get("deduct4").toString());
                            bean.setDeduct4(employeeDetails.getDeduct4());
                            bean.setDeduct5Name(deductionNameList.get("deduct5").toString());
                            bean.setDeduct5(employeeDetails.getDeduct5());
                            bean.setPaye(new BigDecimal(map.get("payee").toString()).round(MathContext.DECIMAL128));
                            bean.setEtf(new BigDecimal(map.get("etf").toString()).round(MathContext.DECIMAL128));
                            bean.setEpf8(new BigDecimal(map.get("epf8").toString()).round(MathContext.DECIMAL128));
                            bean.setEpf12(new BigDecimal(map.get("epf12").toString()).round(MathContext.DECIMAL128));
                            bean.setGrossalary(new BigDecimal(map.get("grosSal").toString()).round(MathContext.DECIMAL128));
                            bean.setNetsalary(new BigDecimal(map.get("netsal").toString()).round(MathContext.DECIMAL128));
                            bean.setNoncash1Name(noncashNameList.get("noncash1").toString());
                            bean.setNoncash1(new BigDecimal(noncashReqList.get("noncash1") == null ? "0" : noncashReqList.get("noncash1").toString()).round(MathContext.DECIMAL128));
                            bean.setNoncash2Name(noncashNameList.get("noncash2").toString());
                            bean.setNoncash2(new BigDecimal(noncashReqList.get("noncash2") == null ? "0" : noncashReqList.get("noncash2").toString()).round(MathContext.DECIMAL128));
                            bean.setNoncash3Name(noncashNameList.get("noncash3").toString());
                            bean.setNoncash3(new BigDecimal(noncashReqList.get("noncash3") == null ? "0" : noncashReqList.get("noncash3").toString()).round(MathContext.DECIMAL128));
                            bean.setNoncash4Name(noncashNameList.get("noncash4").toString());
                            bean.setNoncash4(new BigDecimal(noncashReqList.get("noncash4") == null ? "0" : noncashReqList.get("noncash4").toString()).round(MathContext.DECIMAL128));
                            bean.setNoncash5Name(noncashNameList.get("noncash5").toString());
                            bean.setNoncash5(new BigDecimal(noncashReqList.get("noncash5") == null ? "0" : noncashReqList.get("noncash5").toString()).round(MathContext.DECIMAL128));
                            bean.setNoncash6Name(noncashNameList.get("noncash6").toString());
                            bean.setNoncash6(new BigDecimal(noncashReqList.get("noncash6") == null ? "0" : noncashReqList.get("noncash6").toString()).round(MathContext.DECIMAL128));
                            bean.setNoncash7Name(noncashNameList.get("noncash7").toString());
                            bean.setNoncash7(new BigDecimal(noncashReqList.get("noncash7") == null ? "0" : noncashReqList.get("noncash7").toString()).round(MathContext.DECIMAL128));
                            bean.setNoncash8Name(noncashNameList.get("noncash8").toString());
                            bean.setNoncash8(new BigDecimal(noncashReqList.get("noncash8") == null ? "0" : noncashReqList.get("noncash8").toString()).round(MathContext.DECIMAL128));
                            bean.setNoncash9Name(noncashNameList.get("noncash9").toString());
                            bean.setNoncash9(new BigDecimal(noncashReqList.get("noncash9") == null ? "0" : noncashReqList.get("noncash9").toString()).round(MathContext.DECIMAL128));
                            bean.setNoncash10Name(noncashNameList.get("noncash10").toString());
                            bean.setNoncash10(new BigDecimal(noncashReqList.get("noncash10") == null ? "0" : noncashReqList.get("noncash10").toString()).round(MathContext.DECIMAL128));
                            bean.setComcode(BeanSystemLog.getComcode());
                            bean.setUser(BeanSystemLog.getUser());
                            bean.setActivate(false);
                            bean.setRemark("");
                            bean.setBeanLoan(empLoan);

                            if (empBank == null) {
                                errorList.add("Employee Bank Data Not Found : " + efpb.getEmpid());
                                System.out.println("Employee Bank Data Not Found : " + efpb.getEmpid());
                                System.out.println("---------------------------------------------------------------------------");
                            } else {
                                //BeanEmployeeBank bankData = null;
                                empBank = model.reEditedBankDetails(empBank, new BigDecimal(map.get("netsal").toString()));

                                if (empBank == null) {
                                    if (new BigDecimal(map.get("netsal").toString()).compareTo(BigDecimal.ZERO) == 0) {
                                        errorList.add("Employee net salary cannot be zero : " + efpb.getEmpid());
                                        System.out.println("Employee net salary cannot be zero : " + efpb.getEmpid());
                                        System.out.println("---------------------------------------------------------------------------");
                                    } else {
                                        errorList.add("Check the employee banking details / Net salary remitance issue: " + efpb.getEmpid());
                                        System.out.println("Check the employee banking details / Net salary remitance issue: " + efpb.getEmpid());
                                        System.out.println("---------------------------------------------------------------------------");
                                    }
                                } else {
                                    bean.setEmployeeBank(empBank);
                                    payrollList.add(bean);
                                }
                            }
                        } else {
                            errorList.add("Check the employee details for system id : " + efpb.getEmpid());
                            System.out.println("Check the employee details for system id : " + efpb.getEmpid());
                            System.out.println("---------------------------------------------------------------------------");

                        }
                    }

                    Progress++;
                }

                if (!errorList.isEmpty()) {

                    StringBuilder error = new StringBuilder();

                    error.append("Error:").append("#");

                    for (int i = 0; i < errorList.size(); i++) {
                        error.append(errorList.get(i)).append("#");
                    }
                    error.deleteCharAt(error.lastIndexOf("#"));
                    saveString = error.toString().trim();
                } else {
                    for (BeanPayroll bp : payrollList) {
                        String message = model.savepayroll(emp_connConnection, bp);
                        if (message.startsWith("Error")) {
                            errorList.add(message + " : " + bp.getEmpid());
                            System.out.println("EMP ID : " + bp.getEmpid() + " Not Saved");
                            System.out.println("---------------------------------------------------------------------------");
                        } else {
                            System.out.println("EMP ID : " + bp.getEmpid() + " Saved");
                            System.out.println("---------------------------------------------------------------------------");
                        }
                    }
                    saveString = "Payroll Process Sucessfully Completed";
                }

                setSaveString(saveString);

            } catch (Exception e) {
                e.printStackTrace();
                try {
                    emp_connConnection.rollback();
                } catch (SQLException ex) {
                    Logger.getLogger(ModelPayroll.class.getName()).log(Level.SEVERE, null, ex);
                }
            } finally {
                try {
                    emp_connConnection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

        }

    }

    public double GetProgressValue() {
        double a = beansList.size();//times;
        double b = 100;
        double c = Progress;

        return (c / a * b);
    }

    public String getSaveString() {
        return saveString;
    }

    public void setSaveString(String saveString) {
        this.saveString = saveString;
    }

    private class empBasicDataThread extends Thread {

        BeanEmployee efpb;
        Connection connection;

        empBasicDataThread(BeanEmployee efpb, Connection connection) {
            this.efpb = efpb;
            this.connection = connection;
        }

        public void run() {
            try {
                chkEmpBasicDataThread = false;
                employeeDetails = model.collectEmployeeBasicDetails(connection, efpb.getComcode(), efpb.getEmpid());
                chkEmpBasicDataThread = true;
            } catch (Exception e) {
                e.printStackTrace();
                chkEmpBasicDataThread = false;
            }
        }
    }

    private class empBonusThread extends Thread {

        BeanEmployee efpb;
        Connection connection;

        empBonusThread(BeanEmployee efpb, Connection connection) {
            this.efpb = efpb;
            this.connection = connection;
        }

        public void run() {
            try {
                chkEmpBonusDataThread = false;
                empBonus = model.collectEmployeeBonusDetails(connection, efpb.getComcode(), efpb.getEmpid(), efpb.getSalaryMonth());
                chkEmpBonusDataThread = true;
            } catch (Exception e) {
                e.printStackTrace();
                chkEmpBonusDataThread = false;
            }
        }
    }

    private class empBankDataThread extends Thread {

        BeanEmployee efpb;
        Connection connection;

        empBankDataThread(BeanEmployee efpb, Connection connection) {
            this.efpb = efpb;
            this.connection = connection;
        }

        public void run() {
            try {
                chkEmpBankDataThread = false;
                empBank = model.collectEmployeeBankDetails(connection, efpb.getComcode(), efpb.getEmpid());
                chkEmpBankDataThread = true;
            } catch (Exception e) {
                e.printStackTrace();
                chkEmpBankDataThread = false;
            }
        }
    }

    private class empLoanDataThread extends Thread {

        BeanEmployee efpb;
        Connection connection;

        empLoanDataThread(BeanEmployee efpb, Connection connection) {
            this.efpb = efpb;
            this.connection = connection;
        }

        public void run() {
            try {
                chkEmpLoanDataThread = false;
                empLoan = model.collectEmployeeLoanDetails(connection, efpb.getComcode(), efpb.getEmpid());
                chkEmpLoanDataThread = true;
            } catch (Exception e) {
                e.printStackTrace();
                chkEmpLoanDataThread = false;
            }
        }
    }

    private class empAllowanceDataThread extends Thread {

        BeanEmployee efpb;
        Connection connection;

        empAllowanceDataThread(BeanEmployee efpb, Connection connection) {
            this.efpb = efpb;
            this.connection = connection;
        }

        public void run() {
            try {
                chkEmpAllowanceDataThread = false;
                allowanceNameList = model.getAllowanceList(connection);
                deductionNameList = model.getDeductionList(connection);
                allowanceReqList = model.collectAllowanceRequestDetails(connection, efpb.getComcode(), efpb.getEmpid(), efpb.getSalaryMonth());//Connection connection, String Company, int EmpID, String Mnth
                chkEmpAllowanceDataThread = true;
            } catch (Exception e) {
                e.printStackTrace();
                chkEmpAllowanceDataThread = false;
            }
        }
    }

    private class empNoncashDataThread extends Thread {

        BeanEmployee efpb;
        Connection connection;

        empNoncashDataThread(BeanEmployee efpb, Connection connection) {
            this.efpb = efpb;
            this.connection = connection;
        }

        public void run() {
            try {
                chkEmpNoncashDataThread = false;
                noncashNameList = model.getNoncashList(connection);
                noncashReqList = model.collectNoncashRequestDetails(connection, efpb.getComcode(), efpb.getEmpid(), efpb.getSalaryMonth());//Connection connection, String Company, int EmpID, String Mnth
                chkEmpNoncashDataThread = true;
            } catch (Exception e) {
                e.printStackTrace();
                chkEmpNoncashDataThread = false;
            }
        }
    }

    private class empSalaryAdvanceDataThread extends Thread {

        BeanEmployee efpb;
        Connection connection;

        empSalaryAdvanceDataThread(BeanEmployee efpb, Connection connection) {
            this.efpb = efpb;
            this.connection = connection;
        }

        public void run() {
            try {
                chkEmpSalaryAdvanceDataThread = false;
                salaryAdvance = model.collectEmployeeSalaryAdvanceDetails(connection, efpb.getComcode(), efpb.getEmpid(), efpb.getSalaryMonth());
                chkEmpSalaryAdvanceDataThread = true;
            } catch (Exception e) {
                e.printStackTrace();
                chkEmpSalaryAdvanceDataThread = false;
            }
        }
    }

    private class empAttendanceDataThread extends Thread {

        BeanEmployee efpb;
        Connection connection;

        empAttendanceDataThread(BeanEmployee efpb, Connection connection) {
            this.efpb = efpb;
            this.connection = connection;
        }

        public void run() {
            try {
                chkEmpAttendanceDataThread = false;
                attendance = model.collectEmpAttendanceDetails(connection, efpb.getComcode(), efpb.getEmpid(), efpb.getSalaryMonth(), efpb.getAllow1());
                chkEmpAttendanceDataThread = true;
            } catch (Exception e) {
                e.printStackTrace();
                chkEmpAttendanceDataThread = false;
            }
        }
    }
}
