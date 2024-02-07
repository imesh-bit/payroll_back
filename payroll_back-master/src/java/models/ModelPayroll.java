/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author Nimesha
 */
import dao.BeanAllowance;
import dao.BeanAttendance;
import dao.BeanBank;
import dao.BeanBonus;
import dao.BeanEmployee;
import dao.BeanEmployeeBank;
import dao.BeanLoan;
import dao.BeanPayroll;
import dao.BeanSalaryAdvance;
import dao.BeanSystemLog;
import daoImpls.ImplBank;
import interfaces.InterfaceBank;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import threads.payroll.AutoSaveThread;

/**
 *
 * @author Nimesha
 */
public class ModelPayroll {

    Connection connection;

    public ModelPayroll(Connection connection) {
        this.connection = connection;
    }

    public AutoSaveThread CallAutoSaveThread(List<BeanEmployee> beansList, ModelPayroll model) {
        AutoSaveThread AutoSaveThread = new AutoSaveThread(beansList, model);
        AutoSaveThread.start();
        return AutoSaveThread;
    }

    public List<BeanEmployee> getSelectedEmployeeListSalary(Connection connection, String comCode, String salaryType, String salaryMonth) throws Exception {
        List<BeanEmployee> empList = new ArrayList<>();

        try {

            String sql = "SELECT `empid`,`empcode`,`allow1` FROM employee_details WHERE  comcode=? AND `salarytyp`=? AND `active`=1 ORDER BY empid DESC";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, comCode);
            ps.setString(2, salaryType);
            System.out.println("get emp list for payroll >>");
            System.out.println(ps.toString());
            ResultSet rs = ps.executeQuery();

            if (rs.isBeforeFirst()) {
                while (rs.next()) {

                    BeanEmployee employeeDetails = new BeanEmployee();
                    employeeDetails.setEmpid(rs.getInt("empid"));
                    employeeDetails.setEmpcode(rs.getInt("empcode"));
                    employeeDetails.setAllow1(rs.getBigDecimal("allow1"));//Attendance Allowance
                    employeeDetails.setComcode(comCode);
                    employeeDetails.setSalarytyp(salaryType);
                    employeeDetails.setSalaryMonth(salaryMonth);
                    empList.add(employeeDetails);
                }

            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return empList;
    }

    public String savepayroll(Connection emp_connection, BeanPayroll bean) throws SQLException {
        String message = null;
        Date date = new Date();

        emp_connection.setAutoCommit(false);

        try {
            String insert_sql = "INSERT INTO `payroll`\n"
                    + "(`monthcode`,`empid`,`status`,`categoryid`,`groupid`,`typofemp`,`division`,`designation`,`salarytyp`,`basicsalary`,`basicarreas`,`budgetallowance`,\n"
                    + "`bra2`,`nopay`,`noofattendents`,`bonus`,`nopaydates`,`allow1_name`,`allow1`,`allow2_name`,`allow2`,`allow3_name`,`allow3`,`allow4_name`,`allow4`,\n"
                    + "`allow5_name`,`allow5`,`paycut`,`saladvance`,`otpay`,`loanid`,`loanvalue`,`deduct1_name`,`deduct1`,`deduct2_name`,`deduct2`,`deduct3_name`,`deduct3`,\n"
                    + "`deduct4_name`,`deduct4`,`deduct5_name`,`deduct5`,`paye`,`etf`,`epf8`,`epf12`,`grossalary`,`netsalary`,`user`,`comcode`,`activate`,`remark`,`noncash1_name`,`noncash1`,`noncash2_name`,`noncash2`,`noncash3_name`,`noncash3`,`noncash4_name`,`noncash4`,`noncash5_name`,`noncash5`,\n"
                    + "`noncash6_name`,`noncash6`,`noncash7_name`,`noncash7`,`noncash8_name`,`noncash8`,`noncash9_name`,`noncash9`,`noncash10_name`,`noncash10`)"
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = emp_connection.prepareStatement(insert_sql);
            ps.setString(1, bean.getMonthcode());
            ps.setInt(2, bean.getEmpid());
            ps.setString(3, bean.getEmpStatus());
            ps.setInt(4, bean.getCategoryid());
            ps.setInt(5, bean.getGroupid());
            ps.setString(6, bean.getTypofemp());
            ps.setString(7, bean.getDivision());
            ps.setString(8, bean.getDesignation());
            ps.setString(9, bean.getSalarytype());
            ps.setBigDecimal(10, bean.getBasicsalary());
            ps.setBigDecimal(11, bean.getBasicarreas());
            ps.setBigDecimal(12, bean.getBudgetallowance());
            ps.setBigDecimal(13, bean.getBra2());
            ps.setBigDecimal(14, bean.getNopay());
            ps.setBigDecimal(15, bean.getNoOfAttendance());
            ps.setBigDecimal(16, bean.getBonus());
            ps.setBigDecimal(17, bean.getNopayDates());
            ps.setString(18, bean.getAllow1Name());
            ps.setBigDecimal(19, bean.getAllow1());
            ps.setString(20, bean.getAllow2Name());
            ps.setBigDecimal(21, bean.getAllow2());
            ps.setString(22, bean.getAllow3Name());
            ps.setBigDecimal(23, bean.getAllow3());
            ps.setString(24, bean.getAllow4Name());
            ps.setBigDecimal(25, bean.getAllow4());
            ps.setString(26, bean.getAllow5Name());
            ps.setBigDecimal(27, bean.getAllow5());
            ps.setBigDecimal(28, bean.getPayCutvalue());
            ps.setBigDecimal(29, bean.getSaladvance());
            ps.setBigDecimal(30, bean.getOtpay());
            ps.setInt(31, bean.getLoanid());
            ps.setBigDecimal(32, bean.getLoanvalue());
            ps.setString(33, bean.getDeduct1Name());
            ps.setBigDecimal(34, bean.getDeduct1());
            ps.setString(35, bean.getDeduct2Name());
            ps.setBigDecimal(36, bean.getDeduct2());
            ps.setString(37, bean.getDeduct3Name());
            ps.setBigDecimal(38, bean.getDeduct3());
            ps.setString(39, bean.getDeduct4Name());
            ps.setBigDecimal(40, bean.getDeduct4());
            ps.setString(41, bean.getDeduct5Name());
            ps.setBigDecimal(42, bean.getDeduct5());
            ps.setBigDecimal(43, bean.getPaye());
            ps.setBigDecimal(44, bean.getEtf());
            ps.setBigDecimal(45, bean.getEpf8());
            ps.setBigDecimal(46, bean.getEpf12());
            ps.setBigDecimal(47, bean.getGrossalary());
            ps.setBigDecimal(48, bean.getNetsalary());
            ps.setString(49, bean.getUser());
            ps.setString(50, bean.getComcode());
            ps.setBoolean(51, false);
            ps.setString(52, bean.getRemark());
            ps.setString(53, bean.getNoncash1Name());
            ps.setBigDecimal(54, bean.getNoncash1());
            ps.setString(55, bean.getNoncash2Name());
            ps.setBigDecimal(56, bean.getNoncash2());
            ps.setString(57, bean.getNoncash3Name());
            ps.setBigDecimal(58, bean.getNoncash3());
            ps.setString(59, bean.getNoncash4Name());
            ps.setBigDecimal(60, bean.getNoncash4());
            ps.setString(61, bean.getNoncash5Name());
            ps.setBigDecimal(62, bean.getNoncash5());
            ps.setString(63, bean.getNoncash6Name());
            ps.setBigDecimal(64, bean.getNoncash6());
            ps.setString(65, bean.getNoncash7Name());
            ps.setBigDecimal(66, bean.getNoncash7());
            ps.setString(67, bean.getNoncash8Name());
            ps.setBigDecimal(68, bean.getNoncash8());
            ps.setString(69, bean.getNoncash9Name());
            ps.setBigDecimal(70, bean.getNoncash9());
            ps.setString(71, bean.getNoncash10Name());
            ps.setBigDecimal(72, bean.getNoncash10());
            ps.executeUpdate();
            ps.close();

            BeanLoan l = bean.getBeanLoan();
            //Save Loan Deduction
            if (l != null) {
                String sql_loan = "INSERT INTO `loansettlement`(`loanid`,`monthcode`,`empid`,`monthlyamount`,`paymode`,`active`,`salarytype`)\n"
                        + "VALUES (?,?,?,?,?,?,?);";
                PreparedStatement psLoan = emp_connection.prepareStatement(sql_loan);
                psLoan.setInt(1, l.getLoanid());
                psLoan.setString(2, bean.getMonthcode());
                psLoan.setInt(3, l.getEmpid());
                psLoan.setBigDecimal(4, l.getMonthlydeduction());
                psLoan.setString(5, "By Salary");
                psLoan.setInt(6, 0);
                psLoan.setString(7, bean.getSalarytype());
                psLoan.executeUpdate();
                psLoan.close();

            }

            //Save Bank Deatails
            BeanEmployeeBank empBank = bean.getEmployeeBank();
            String sql_bank = "INSERT INTO `emppaybank`(`monthcode`,`empid`,`bankid1`,`accno1`,`amount1`,`bankid2`,`accno2`,`amount2`,`comcode`)\n"
                    + "VALUES (?,?,?,?,?,?,?,?,?);";
            PreparedStatement psbank = emp_connection.prepareStatement(sql_bank);
            psbank.setString(1, bean.getMonthcode());//Month
            psbank.setInt(2, empBank.getEmpid());//EMP ID
            psbank.setInt(3, empBank.getBankid1());
            psbank.setString(4, empBank.getAccno1());
            psbank.setBigDecimal(5, empBank.getAmount1());
            psbank.setInt(6, empBank.getBankid2());
            psbank.setString(7, empBank.getAccno2());
            psbank.setBigDecimal(8, empBank.getAmount2());
            psbank.setString(9, bean.getComcode());
            psbank.executeUpdate();
            psbank.close();

            emp_connection.commit();
            emp_connection.setAutoCommit(true);

            message = "Details saved sucessfully";
        } catch (Exception e) {
            e.printStackTrace();
            emp_connection.rollback();
            message = "Error:" + e.getLocalizedMessage();

        }
        return message;
    }

    public JSONArray getPayrollData(Connection connection, String SalaryMonth, String SalaryType, String Company) {
        JSONArray jsa = new JSONArray();
        try {

            String query = "SELECT COUNT(*) FROM `payroll` WHERE `monthcode`=? AND `comcode`=?  AND `salarytyp`=?;";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, SalaryMonth);
            ps.setString(2, Company);
            ps.setString(3, SalaryType);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getInt(1) > 0) {
                    String payroll = "SELECT a.`empid`,b.`empinitialname`,b.`empcode`,a.`division`,a.`designation`,a.`grossalary`,a.`netsalary` FROM `payroll` AS a,`employee_details` AS b \n"
                            + "WHERE a.`empid`=b.`empid` AND a.`salarytyp`=? AND a.`monthcode`=? AND a.`comcode`=?";
                    PreparedStatement pss = connection.prepareStatement(payroll);
                    pss.setString(1, SalaryType);
                    pss.setString(2, SalaryMonth);
                    pss.setString(3, Company);
                    ResultSet rss = pss.executeQuery();
                    while (rss.next()) {
                        LinkedHashMap mp = new LinkedHashMap();
                        mp.put("empid", rss.getInt("empid"));
                        mp.put("empcode", rss.getInt("empcode"));
                        mp.put("empinitialname", rss.getString("empinitialname"));
                        mp.put("division", rss.getString("division"));
                        mp.put("designation", rss.getString("designation"));
                        mp.put("netsalary", rss.getBigDecimal("netsalary"));
                        mp.put("grossalary", rss.getBigDecimal("grossalary"));
                        jsa.add(mp);
                    }
                    pss.close();
                    rss.close();
                } else {
                    LinkedHashMap mp = new LinkedHashMap();
                    mp.put("data", "No payroll data for selected month");
                    jsa.add(mp);
                }
            }
            ps.close();
            rs.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return jsa;
    }

    public JSONArray getExcelData(Connection connection, String SalaryMonth, String SalaryType, String Company) {
        JSONArray jsa = new JSONArray();
        try {

            String query = "SELECT COUNT(*) FROM `payroll` WHERE `monthcode`=? AND `comcode`=?  AND `salarytyp`=?;";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, SalaryMonth);
            ps.setString(2, Company);
            ps.setString(3, SalaryType);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getInt(1) > 0) {
                    String payroll = "SELECT\n"
                            + "     payroll.`empid` AS empid,\n"
                            + "     employee_details.`empcode` AS empcode,\n"
                            + "     employee_details.`empinitialname` AS empinitialname,\n"
                            + "     catagory.`categoryname` AS categoryname,\n"
                            + "     occupational_group.`groupname` AS groupname,\n"
                            + "     payroll.`status` AS empstatus,\n"
                            + "     payroll.`typofemp` AS typofemp,\n"
                            + "     payroll.`division` AS division,\n"
                            + "     payroll.`designation` AS designation,\n"
                            + "     payroll.`basicsalary` AS basicsalary,\n"
                            + "     payroll.`basicarreas` AS basicarreas,\n"
                            + "     payroll.`budgetallowance` AS budgetallowance,\n"
                            + "     payroll.`bra2` AS bra2,\n"
                            + "     payroll.`noofattendents` AS noofattendents,\n"
                            + "     payroll.`bonus` AS bonus,\n"
                            + "     payroll.`nopaydates` AS nopaydates,\n"
                            + "     CASE WHEN payroll.`allow1` >0 THEN payroll.`allow1_name` ELSE '' END AS allow1_name,\n"
                            + "     payroll.`allow1` AS allow1,\n"
                            + "     CASE WHEN payroll.`allow2`>0 THEN payroll.`allow2_name` ELSE '' END AS allow2_name,\n"
                            + "     payroll.`allow2` AS allow2,\n"
                            + "     CASE WHEN payroll.`allow3`>0 THEN payroll.`allow3_name` ELSE '' END AS allow3_name,\n"
                            + "     payroll.`allow3` AS allow3,\n"
                            + "     CASE WHEN payroll.`allow4`>0 THEN payroll.`allow4_name` ELSE '' END AS allow4_name,\n"
                            + "     payroll.`allow4` AS allow4,\n"
                            + "     CASE WHEN payroll.`allow5`>0 THEN payroll.`allow5_name` ELSE '' END AS allow5_name,\n"
                            + "     payroll.`allow5` AS allow5,\n"
                            + "     payroll.`nopay` AS nopay,\n"
                            + "     payroll.`paycut` AS paycut,\n"
                            + "     payroll.`saladvance` AS saladvance,\n"
                            + "     payroll.`otpay` AS otpay,\n"
                            + "     payroll.`loanid` AS loanid,\n"
                            + "     payroll.`loanvalue` AS loanvalue,\n"
                            + "     CASE WHEN payroll.`deduct1`>0 THEN payroll.`deduct1_name` ELSE '' END AS deduct1_name,\n"
                            + "     payroll.`deduct1` AS deduct1,\n"
                            + "     CASE WHEN payroll.`deduct2`>0 THEN payroll.`deduct2_name` ELSE '' END AS deduct2_name,\n"
                            + "     payroll.`deduct2` AS deduct2,\n"
                            + "     CASE WHEN payroll.`deduct3`>0 THEN payroll.`deduct3_name` ELSE '' END AS deduct3_name,\n"
                            + "     payroll.`deduct3` AS deduct3,\n"
                            + "     CASE WHEN payroll.`deduct4`>0 THEN payroll.`deduct4_name` ELSE '' END AS deduct4_name,\n"
                            + "     payroll.`deduct4` AS deduct4,\n"
                            + "     CASE WHEN payroll.`deduct5`>0 THEN payroll.`deduct5_name` ELSE '' END AS deduct5_name,\n"
                            + "     payroll.`deduct5` AS deduct5,\n"
                            + "     payroll.`paye` AS paye,\n"
                            + "     payroll.`etf` AS etf,\n"
                            + "     payroll.`epf8` AS epf8,\n"
                            + "     payroll.`epf12` AS epf12,\n"
                            + "     payroll.`grossalary` AS grossalary,\n"
                            + "     payroll.`netsalary` AS netsalary,\n"
                            + "     CASE WHEN payroll.`noncash1`>0 THEN payroll.`noncash1_name` ELSE '' END AS noncash1_name,\n"
                            + "     payroll.`noncash1` AS noncash1,\n"
                            + "     CASE WHEN payroll.`noncash2`>0 THEN payroll.`noncash2_name` ELSE '' END AS noncash2_name,\n"
                            + "     payroll.`noncash2` AS noncash2,\n"
                            + "     CASE WHEN payroll.`noncash3`>0 THEN payroll.`noncash3_name` ELSE '' END AS noncash3_name,\n"
                            + "     payroll.`noncash3` AS noncash3,\n"
                            + "     CASE WHEN payroll.`noncash4`>0 THEN payroll.`noncash4_name` ELSE '' END AS noncash4_name,\n"
                            + "     payroll.`noncash4` AS noncash4,\n"
                            + "     CASE WHEN payroll.`noncash5`>0 THEN payroll.`noncash5_name` ELSE '' END AS noncash5_name,\n"
                            + "     payroll.`noncash5` AS noncash5,\n"
                            + "     CASE WHEN payroll.`noncash6`>0 THEN payroll.`noncash6_name` ELSE '' END AS noncash6_name,\n"
                            + "     payroll.`noncash6` AS noncash6,\n"
                            + "     CASE WHEN payroll.`noncash7`>0 THEN payroll.`noncash7_name` ELSE '' END AS noncash7_name,\n"
                            + "     payroll.`noncash7` AS noncash7,\n"
                            + "     CASE WHEN payroll.`noncash8`>0 THEN payroll.`noncash8_name` ELSE '' END AS noncash8_name,\n"
                            + "     payroll.`noncash8` AS noncash8,\n"
                            + "     CASE WHEN payroll.`noncash9`>0 THEN payroll.`noncash9_name` ELSE '' END AS noncash9_name,\n"
                            + "     payroll.`noncash9` AS noncash9,\n"
                            + "     CASE WHEN  payroll.`noncash10`>0 THEN payroll.`noncash10_name` ELSE '' END AS noncash10_name,\n"
                            + "     payroll.`noncash10` AS noncash10,\n"
                            + "     payroll.`remark` AS remark\n"
                            + "FROM\n"
                            + "     `catagory` catagory RIGHT OUTER JOIN `payroll` payroll ON catagory.`categoryid` = payroll.`categoryid`\n"
                            + "     LEFT OUTER JOIN `occupational_group` occupational_group ON payroll.`groupid` = occupational_group.`groupid`\n"
                            + "     LEFT OUTER JOIN `employee_details` employee_details ON payroll.`empid` = employee_details.`empid`\n"
                            + "WHERE\n"
                            + "     payroll.`monthcode` = ?\n"
                            + "     AND payroll.`salarytyp` = ? \n"
                            + "     AND  payroll.`comcode`=?";
                    PreparedStatement pss = connection.prepareStatement(payroll);
                    pss.setString(1, SalaryMonth);
                    pss.setString(2, SalaryType);
                    pss.setString(3, Company);
                    ResultSet rss = pss.executeQuery();
                    while (rss.next()) {
                        LinkedHashMap mp = new LinkedHashMap();
                        mp.put("empid", rss.getInt("empid"));
                        mp.put("empcode", rss.getInt("empcode"));
                        mp.put("empinitialname", rss.getString("empinitialname"));
                        mp.put("categoryname", rss.getString("categoryname"));
                        mp.put("groupname", rss.getString("groupname"));
                        mp.put("empstatus", rss.getString("empstatus"));
                        mp.put("typofemp", rss.getString("typofemp"));
                        mp.put("division", rss.getString("division"));
                        mp.put("designation", rss.getString("designation"));
                        mp.put("basicsalary", rss.getBigDecimal("basicsalary"));
                        mp.put("basicarreas", rss.getBigDecimal("basicarreas"));
                        mp.put("budgetallowance", rss.getBigDecimal("budgetallowance"));
                        mp.put("bra2", rss.getBigDecimal("bra2"));
                        mp.put("noofattendents", rss.getBigDecimal("noofattendents"));
                        mp.put("bonus", rss.getBigDecimal("bonus"));
                        mp.put("nopaydates", rss.getBigDecimal("nopaydates"));
                        mp.put("nopay", rss.getBigDecimal("nopay"));
                        mp.put("paycut", rss.getBigDecimal("paycut"));
                        mp.put("saladvance", rss.getBigDecimal("saladvance"));
                        mp.put("otpay", rss.getBigDecimal("otpay"));
                        mp.put("loanid", rss.getInt("loanid"));
                        mp.put("loanvalue", rss.getBigDecimal("loanvalue"));
                        mp.put("allow1_name", rss.getString("allow1_name"));
                        mp.put("allow1", rss.getBigDecimal("allow1"));
                        mp.put("allow2_name", rss.getString("allow2_name"));
                        mp.put("allow2", rss.getBigDecimal("allow2"));
                        mp.put("allow3_name", rss.getString("allow3_name"));
                        mp.put("allow3", rss.getBigDecimal("allow3"));
                        mp.put("allow4_name", rss.getString("allow4_name"));
                        mp.put("allow4", rss.getBigDecimal("allow4"));
                        mp.put("allow5_name", rss.getString("allow5_name"));
                        mp.put("allow5", rss.getBigDecimal("allow5"));
                        mp.put("deduct1_name", rss.getString("deduct1_name"));
                        mp.put("deduct1", rss.getBigDecimal("deduct1"));
                        mp.put("deduct2_name", rss.getString("deduct2_name"));
                        mp.put("deduct2", rss.getBigDecimal("deduct2"));
                        mp.put("deduct3_name", rss.getString("deduct3_name"));
                        mp.put("deduct3", rss.getBigDecimal("deduct3"));
                        mp.put("deduct4_name", rss.getString("deduct4_name"));
                        mp.put("deduct4", rss.getBigDecimal("deduct4"));
                        mp.put("deduct5_name", rss.getString("deduct5_name"));
                        mp.put("deduct5", rss.getBigDecimal("deduct5"));
                        mp.put("noncash1_name", rss.getString("noncash1_name"));
                        mp.put("noncash1", rss.getBigDecimal("noncash1"));
                        mp.put("noncash2_name", rss.getString("noncash2_name"));
                        mp.put("noncash2", rss.getBigDecimal("noncash2"));
                        mp.put("noncash3_name", rss.getString("noncash3_name"));
                        mp.put("noncash3", rss.getBigDecimal("noncash3"));
                        mp.put("noncash4_name", rss.getString("noncash4_name"));
                        mp.put("noncash4", rss.getBigDecimal("noncash4"));
                        mp.put("noncash5_name", rss.getString("noncash5_name"));
                        mp.put("noncash5", rss.getBigDecimal("noncash5"));
                        mp.put("noncash6_name", rss.getString("noncash6_name"));
                        mp.put("noncash6", rss.getBigDecimal("noncash6"));
                        mp.put("noncash7_name", rss.getString("noncash7_name"));
                        mp.put("noncash7", rss.getBigDecimal("noncash7"));
                        mp.put("noncash8_name", rss.getString("noncash8_name"));
                        mp.put("noncash8", rss.getBigDecimal("noncash8"));
                        mp.put("noncash9_name", rss.getString("noncash9_name"));
                        mp.put("noncash9", rss.getBigDecimal("noncash9"));
                        mp.put("noncash10_name", rss.getString("noncash10_name"));
                        mp.put("noncash10", rss.getBigDecimal("noncash10"));
                        mp.put("noncash10", rss.getBigDecimal("noncash10"));
                        mp.put("paye", rss.getBigDecimal("paye"));
                        mp.put("etf", rss.getBigDecimal("etf"));
                        mp.put("epf8", rss.getBigDecimal("epf8"));
                        mp.put("epf12", rss.getBigDecimal("epf12"));
                        mp.put("grossalary", rss.getBigDecimal("grossalary"));
                        mp.put("netsalary", rss.getBigDecimal("netsalary"));
                        mp.put("remark", rss.getString("remark"));
                        jsa.add(mp);
                    }
                    pss.close();
                    rss.close();
                } else {
                    LinkedHashMap mp = new LinkedHashMap();
                    mp.put("data", "No payroll data for selected month");
                    jsa.add(mp);
                }
            }
            ps.close();
            rs.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return jsa;
    }

    public BeanEmployee collectEmployeeBasicDetails(Connection connection, String Company, int EmpID) {
        BeanEmployee ed = null;

        try {
            String sql_select_emp = "SELECT `empinitialname`,`status`,`categoryid`,`groupid`,`gender`,`typofemp`,`division`,`designation`,`salarytyp`,`basicsalary`,`budgetallowance`,`bra2`,\n"
                    + "`allow1`,`allow2`,`allow3`,`allow4`,`allow5`,`deduct1`,`deduct2`,`deduct3`,`deduct4`,`deduct5`,`epfno`,`empcode` FROM employee_details WHERE empid='" + EmpID + "' AND comcode='" + Company + "' AND `active`=1";
            PreparedStatement ps = connection.prepareStatement(sql_select_emp);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ed = new BeanEmployee();
                ed.setEmpid(EmpID);
                ed.setEmpinitialname(rs.getString("empinitialname").trim());
                ed.setStatus(rs.getString("status").trim());
                ed.setCategoryid(rs.getInt("categoryid"));
                ed.setGroupid(rs.getInt("groupid"));
                ed.setGender(rs.getInt("gender"));
                ed.setTypofemp(rs.getString("typofemp").trim());
                ed.setDivision(rs.getString("division").trim());
                ed.setDesignation(rs.getString("designation").trim());
                ed.setSalarytyp(rs.getString("salarytyp").trim());
                ed.setBasicsalary(rs.getBigDecimal("basicsalary"));
                ed.setBudgetallowance(rs.getBigDecimal("budgetallowance"));
                ed.setBra2(rs.getBigDecimal("bra2"));
                ed.setAllow1(rs.getBigDecimal("allow1"));//attendance
                ed.setAllow2(rs.getBigDecimal("allow2"));
                ed.setAllow3(rs.getBigDecimal("allow3"));
                ed.setAllow4(rs.getBigDecimal("allow4"));
                ed.setAllow5(rs.getBigDecimal("allow5"));
                ed.setDeduct1(rs.getBigDecimal("deduct1"));
                ed.setDeduct2(rs.getBigDecimal("deduct2"));
                ed.setDeduct3(rs.getBigDecimal("deduct3"));
                ed.setDeduct4(rs.getBigDecimal("deduct4"));
                ed.setDeduct5(rs.getBigDecimal("deduct5"));
                ed.setEpfno(rs.getString("epfno").trim());
                ed.setEmpcode(rs.getInt("empcode"));

                ed.setComcode(Company);
            }
            rs.close();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
//        if (ed != null) {
//            System.out.println("EMP ID : "+ed.getEmpId());
//        }
        return ed;
    }

    public HashMap collectAllowanceRequestDetails(Connection connection, String Company, int EmpID, String Mnth) throws Exception {
        HashMap map = new HashMap();

        try {
            String sql_serviceallowance = "SELECT `allowancecode`,`amount` FROM `allowance_request` "
                    + "WHERE `monthcode`='" + Mnth + "' AND `empid`='" + EmpID + "' AND `comcode`='" + Company + "' AND `allowtype`='A'";

            System.out.println("allowance request :" + sql_serviceallowance);
            PreparedStatement ps = connection.prepareStatement(sql_serviceallowance);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                map.put(rs.getString("allowancecode"), rs.getBigDecimal("amount"));
            }
            rs.close();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;

    }

    public HashMap getAllowanceList(Connection connection) throws Exception {
        HashMap map = new HashMap();
        String query = "SELECT `allow1`,`allow2`,`allow3`,`allow4`,`allow5` FROM `allowance`;";

        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        if (rs.isBeforeFirst()) {
            rs.first();
            map.put("allow1", rs.getString("allow1"));
            map.put("allow2", rs.getString("allow2"));
            map.put("allow3", rs.getString("allow3"));
            map.put("allow4", rs.getString("allow4"));
            map.put("allow5", rs.getString("allow5"));
        }
        ps.close();
        return map;
    }

    public HashMap getNoncashList(Connection connection) throws Exception {
        HashMap mp = new HashMap();
        String query = "SELECT * FROM `noncash`;";

        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        if (rs.isBeforeFirst()) {
            rs.first();
            mp.put("noncash1", rs.getString("noncash1"));
            mp.put("noncash2", rs.getString("noncash2"));
            mp.put("noncash3", rs.getString("noncash3"));
            mp.put("noncash4", rs.getString("noncash4"));
            mp.put("noncash5", rs.getString("noncash5"));
            mp.put("noncash6", rs.getString("noncash6"));
            mp.put("noncash7", rs.getString("noncash7"));
            mp.put("noncash8", rs.getString("noncash8"));
            mp.put("noncash9", rs.getString("noncash9"));
            mp.put("noncash10", rs.getString("noncash10"));

        }
        ps.close();
        return mp;
    }

    public HashMap collectNoncashRequestDetails(Connection connection, String Company, int EmpID, String Mnth) throws Exception {
        HashMap map = new HashMap();

        try {
            String sql_serviceallowance = "SELECT `allowancecode`,`amount` FROM `allowance_request` "
                    + "WHERE `monthcode`='" + Mnth + "' AND `empid`='" + EmpID + "' AND `comcode`='" + Company + "' AND `allowtype`='N'";

            System.out.println("allowance request :" + sql_serviceallowance);
            PreparedStatement ps = connection.prepareStatement(sql_serviceallowance);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                map.put(rs.getString("allowancecode"), rs.getBigDecimal("amount"));
            }
            rs.close();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;

    }

    public HashMap getDeductionList(Connection connection) throws Exception {
        HashMap map = new HashMap();
        String query = "SELECT `deduct1`,`deduct2`,`deduct3`,`deduct4`,`deduct5` FROM `deduction`;";

        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        BeanAllowance b = new BeanAllowance();

        if (rs.isBeforeFirst()) {
            rs.first();
            map.put("deduct1", rs.getString("deduct1"));
            map.put("deduct2", rs.getString("deduct2"));
            map.put("deduct3", rs.getString("deduct3"));
            map.put("deduct4", rs.getString("deduct4"));
            map.put("deduct5", rs.getString("deduct5"));
        }
        ps.close();
        return map;
    }

    public BeanBonus collectEmployeeBonusDetails(Connection connection, String Company, int EmpID, String SalaryMonth) {
        BeanBonus bonus = null;
        try {
            String sql_bonus = "SELECT `amount` FROM `bonus` WHERE `comcode`=? AND `monthcode`=? AND `empid`=?";
//   System.out.println(sql_emp_bank);
            PreparedStatement ps = connection.prepareStatement(sql_bonus);
            ps.setString(1, Company);
            ps.setString(2, SalaryMonth);
            ps.setInt(3, EmpID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                bonus = new BeanBonus();
                bonus.setAmount(rs.getBigDecimal("amount"));
            }
            rs.close();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return bonus;
    }

    public BeanEmployeeBank collectEmployeeBankDetails(Connection connection, String Company, int EmpID) {
        BeanEmployeeBank empBank = null;
        try {
            String sql_emp_bank = "SELECT `bankid1`,`accno1`,`amount1`,`bankid2`,`accno2`,`amount2` FROM `employee_bank`\n"
                    + "WHERE `comcode`='" + Company + "'AND `empid`='" + EmpID + "'";
//   System.out.println(sql_emp_bank);
            PreparedStatement ps = connection.prepareStatement(sql_emp_bank);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                empBank = new BeanEmployeeBank();
                empBank.setEmpid(EmpID);
                empBank.setBankid1(rs.getInt("bankid1"));
                empBank.setAccno1(rs.getString("accno1").trim());
                empBank.setAmount1(rs.getBigDecimal("amount1"));
                empBank.setBankid2(rs.getInt("bankid2"));
                empBank.setAccno2(rs.getString("accno2").trim());
                empBank.setAmount2(rs.getBigDecimal("amount2"));
            }
            rs.close();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return empBank;
    }

    public BeanEmployeeBank reEditedBankDetails(BeanEmployeeBank empBank, BigDecimal netSalary) {

        BeanEmployeeBank reEmpBank = null;
        try {
            //BigDecimal netSalaryBalance = BigDecimal.ZERO;
            BigDecimal BankAmount = BigDecimal.ZERO;
            BigDecimal Amount1 = BigDecimal.ZERO;
            BigDecimal Amount2 = BigDecimal.ZERO;

            BankAmount = empBank.getAmount1().add(empBank.getAmount2()).round(MathContext.DECIMAL128);

            //if(BankAmount > netSalary) Bank Amount1 = net salary
            if (BankAmount.compareTo(netSalary) == 1) {
                System.out.println("BankAmount " + BankAmount + " netSalary " + netSalary);
                Amount2 = BigDecimal.ZERO;
                Amount1 = netSalary;
            } else {
                // Check balance payment bank
                if (empBank.getAmount1().compareTo(BigDecimal.ZERO) == 0) {
                    Amount2 = empBank.getAmount2();
                    Amount1 = netSalary.subtract(Amount2);
                } else if (empBank.getAmount2().compareTo(BigDecimal.ZERO) == 0) {
                    Amount1 = empBank.getAmount1();
                    Amount2 = netSalary.subtract(Amount1);
                }
            }

            if ((Amount1.add(Amount2)).compareTo(netSalary) != 0) {
                System.out.println("Net salary remitance issue. Please check the employee banking details");
//                return null;
            }

            reEmpBank = new BeanEmployeeBank();
            reEmpBank.setEmpid(empBank.getEmpid());
            reEmpBank.setBankid1(empBank.getBankid1());
            reEmpBank.setAccno1(empBank.getAccno1());
            reEmpBank.setAmount1(Amount1.round(MathContext.DECIMAL128));
            reEmpBank.setBankid2(empBank.getBankid2());
            reEmpBank.setAccno2(empBank.getAccno2());
            reEmpBank.setAmount2(Amount2.round(MathContext.DECIMAL128));

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return reEmpBank;
    }

    public BeanLoan collectEmployeeLoanDetails(Connection connection, String Company, int EmpID) {
        BigDecimal loanAmount;
        BeanLoan l = null;
        try {
            String sql_selct_loan = "SELECT `loanid`,`loanvalue`,`monthlydeduction`,`noofinstallments`,`typeofloan` FROM `loan` \n"
                    + "WHERE `comcode`='" + Company + "' AND `empid`='" + EmpID + "' AND `setoff`=0 ";
            PreparedStatement ps = connection.prepareStatement(sql_selct_loan);
            ResultSet rs = ps.executeQuery();
            loanAmount = BigDecimal.ZERO;

            while (rs.next()) {
                String sql_chksettlement = "SELECT COUNT(*) AS COUNT FROM `loansettlement` WHERE `loanid`='" + rs.getInt("loanid") + "'";
                PreparedStatement ps_chksettlement = connection.prepareStatement(sql_chksettlement);
                ResultSet rs_chksettlement = ps_chksettlement.executeQuery();
                rs_chksettlement.next();
                int count = rs_chksettlement.getInt("Count");
                rs_chksettlement.close();
                ps_chksettlement.close();

                if (count == 0) { //if no any installment is deducted
                    loanAmount = loanAmount.add(rs.getBigDecimal("monthlydeduction"));
                    l = new BeanLoan();
                    l.setEmpid(EmpID);
                    l.setLoanid(rs.getInt("loanid"));
                    l.setTypeofloan(rs.getString("typeofloan").trim());
                    l.setMonthlydeduction(rs.getBigDecimal("monthlydeduction"));
                    l.setLoanvalue(rs.getBigDecimal("loanvalue"));
                } else {
                    String sql_loansettlement = "SELECT `loanid`,SUM(`monthlydeduction`) AS TotalLoanDeduction FROM `loansettlement` WHERE `loanid`='" + rs.getInt("loanid") + "' GROUP BY `loanid`";
                    PreparedStatement ps_loansettlement = connection.prepareStatement(sql_loansettlement);
                    ResultSet rs_loansettlement = ps_loansettlement.executeQuery();
                    while (rs_loansettlement.next()) {
                        if (rs.getBigDecimal("LoanValue").compareTo(rs_loansettlement.getBigDecimal("TotalLoanDeduction")) == 1) {

                            if ((rs.getBigDecimal("MothDeduct").add(rs_loansettlement.getBigDecimal("TotalLoanDeduction"))).compareTo(rs.getBigDecimal("LoanValue")) == 1) {
                                loanAmount = loanAmount.add((rs.getBigDecimal("loanvalue").subtract(rs_loansettlement.getBigDecimal("TotalLoanDeduction"))));
                                l = new BeanLoan();
                                l.setEmpid(EmpID);
                                l.setLoanid(rs.getInt("loanid"));
                                l.setTypeofloan(rs.getString("typeofloan").trim());
                                l.setMonthlydeduction((rs.getBigDecimal("loanvalue").subtract(rs_loansettlement.getBigDecimal("TotalLoanDeduction"))));
                                l.setLoanvalue(rs.getBigDecimal("loanvalue"));
                            } else {
                                loanAmount = loanAmount.add(rs.getBigDecimal("monthlydeduction"));
                                l = new BeanLoan();
                                l.setEmpid(EmpID);
                                l.setLoanid(rs.getInt("loanid"));
                                l.setTypeofloan(rs.getString("typeofloan").trim());
                                l.setMonthlydeduction(rs.getBigDecimal("monthlydeduction"));
                                l.setLoanvalue(rs.getBigDecimal("loanvalue"));
                            }

                        }
                    }
                    rs_loansettlement.close();
                    ps_loansettlement.close();
                }

            }
            rs.close();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (l != null) {
            System.out.println("loan " + l.toString());
        }
        return l;
    }

    public BeanSalaryAdvance collectEmployeeSalaryAdvanceDetails(Connection connection, String Company, int EmpID, String SalMonth) {
        BeanSalaryAdvance advance = null;
        try {

            String sql_salaryadvance = "SELECT `amount` FROM `salary_advance` WHERE `comcode`='" + Company + "' AND \n"
                    + "`monthcode`='" + SalMonth + "' AND `empid`='" + EmpID + "' ";
            PreparedStatement ps = connection.prepareStatement(sql_salaryadvance);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                advance = new BeanSalaryAdvance();
                advance.setAmount(rs.getBigDecimal("amount"));
            }
            rs.close();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return advance;
    }

    public BeanAttendance collectEmpAttendanceDetails(Connection connection, String Company, int EmpID, String SalMonth, BigDecimal attendanceAllowance) {
        BigDecimal payingAmount;
        BigDecimal noOfDates;
        BeanAttendance attendance = null;
        try {
            payingAmount = BigDecimal.ZERO;
            noOfDates = BigDecimal.ZERO;

            String sql_attendance = "SELECT `noofdates` FROM `attendance` WHERE `monthCode`=? AND `comcode`=? AND `empid`=?; ";
            PreparedStatement ps = connection.prepareStatement(sql_attendance);
            ps.setString(1, SalMonth);
            ps.setString(2, Company);
            ps.setInt(3, EmpID);
            ResultSet rs = ps.executeQuery();
            if (rs.isBeforeFirst()) {
                while (rs.next()) {

                    noOfDates = rs.getBigDecimal("noofdates");
                    System.out.println("noOfDates " + noOfDates);
                    System.out.println("attendanceAllowance " + attendanceAllowance);

                    if (noOfDates.compareTo(new BigDecimal(15)) == 1) {
                        payingAmount = attendanceAllowance;
                    } else {
                        payingAmount = ((attendanceAllowance.divide(new BigDecimal(15), MathContext.DECIMAL128)).multiply(noOfDates, MathContext.DECIMAL128));
                    }

                    attendance = new BeanAttendance();
                    attendance.setNoofdates(noOfDates);
                    attendance.setAttendanceAllowance(payingAmount);

                }
            } else {
                attendance = new BeanAttendance();
                attendance.setNoofdates(BigDecimal.ZERO);
                attendance.setAttendanceAllowance(BigDecimal.ZERO);
            }
            rs.close();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return attendance;
    }

    public HashMap EpfEtf_Calculation(Connection connection, String SalaryMonth,
            boolean EPFHave,
            BigDecimal bsal,
            BigDecimal salarre,
            BigDecimal bra1,
            BigDecimal bra2,
            BigDecimal payCut,
            BigDecimal NoPay,
            BigDecimal AttendanceIncentive,//Allow1
            BigDecimal PerformanceIncentive,//Allow2
            BigDecimal LeaveEncashment,//Allow3
            BigDecimal Allow4,
            BigDecimal Allow5,
            BigDecimal saladv,
            BigDecimal otpay,
            BigDecimal loan,
            BigDecimal Deduct1,
            BigDecimal Deduct2,
            BigDecimal Deduct3,
            BigDecimal Deduct4,
            BigDecimal Deduct5,
            BigDecimal bonus,
            BigDecimal noncash1,
            BigDecimal noncash2,
            BigDecimal noncash3,
            BigDecimal noncash4,
            BigDecimal noncash5,
            BigDecimal noncash6,
            BigDecimal noncash7,
            BigDecimal noncash8,
            BigDecimal noncash9,
            BigDecimal noncash10) throws SQLException {

        /*  Instance Variable */
        HashMap map = new HashMap();

        BigDecimal epf8, epf12, Etf;
        BigDecimal epfEmploye = BigDecimal.ZERO;
        BigDecimal epfCompany = BigDecimal.ZERO;
        BigDecimal etf = BigDecimal.ZERO;
        BigDecimal sal4Epf = BigDecimal.ZERO;
        String query = "select EpfEmp,EpfCompany,Etf from epfetf;";
        PreparedStatement ps = connection.prepareStatement(query);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            epfEmploye = rs.getBigDecimal(1);
            epfCompany = rs.getBigDecimal(2);
            etf = rs.getBigDecimal(3);
        }
        rs.close();
        ps.close();

        if (EPFHave) {
            //Total earning for epf
            sal4Epf = bsal.add(salarre).add(bra1).add(bra2).add(LeaveEncashment).subtract(NoPay.add(payCut));

            epf8 = sal4Epf.multiply(epfEmploye);
            epf12 = sal4Epf.multiply(epfCompany);
            Etf = sal4Epf.multiply(etf);
        } else {
            epf8 = BigDecimal.valueOf(0);
            epf12 = BigDecimal.valueOf(0);
            Etf = BigDecimal.valueOf(0);
        }

        map.put("epf8", epf8);
        map.put("epf12", epf12);
        map.put("etf", Etf);

        //PAYE salary
        BigDecimal TotAdd = bsal.add(salarre).add(bra1).add(bra2).add(bonus).add(AttendanceIncentive).add(PerformanceIncentive).add(LeaveEncashment).add(Allow4).add(Allow5).
                add(noncash1).add(noncash2).add(noncash3).add(noncash4).add(noncash5).add(noncash6).add(noncash7).add(noncash8).add(noncash9).add(noncash10).subtract(NoPay.add(payCut));

        map.put("totalearning", TotAdd);
        map.put("grosSal", TotAdd);
        //-------------------- Calculate PAYEE ------------------------
        BigDecimal paye = BigDecimal.ZERO;

        if (TotAdd.compareTo(new BigDecimal(100000)) == 1) {
            if (bonus.compareTo(BigDecimal.ZERO) == 1) {
                paye = calculatePayeForBonus(SalaryMonth, TotAdd, bonus);
            } else {
                paye = calculatePayeForRegularProfit(TotAdd);
            }
        } else {
            paye = calculatePayeForCumalativeGains(connection, SalaryMonth, TotAdd, bonus, false);
        }

        map.put("payee", paye);

        BigDecimal TotDedct = epf8.add(saladv).add(loan).add(paye).add(Deduct1).add(Deduct2).add(Deduct3).add(Deduct4).add(Deduct5);
        map.put("totaldeduction", TotDedct);

        BigDecimal NetSal = sal4Epf.add(AttendanceIncentive).add(PerformanceIncentive).add(Allow4).add(Allow5).subtract(TotDedct);
        map.put("netsal", NetSal);

        return map;

    }

    public JSONObject getMonthsNormalPayroll(Connection connection, String companyCode) throws SQLException {
        JSONObject jsono = new JSONObject();
        JSONArray array = new JSONArray();

        Map m = new HashMap();
        m.put("text", "");
        m.put("value", "");
        array.add(m);

        String query = "SELECT DISTINCT(monthcode) AS MonthCode FROM month WHERE comcode=? AND runpayroll=0;";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, companyCode);
        ResultSet rs = ps.executeQuery();
        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                m = new HashMap();
                m.put("text", rs.getString("MonthCode"));
                m.put("value", rs.getString("MonthCode"));
                array.add(m);
                m = null;
            }
        }
        jsono.put("salaryMonths", array);
        return jsono;
    }

    public JSONArray getEmployeePayrollData(Connection connection, String salaryMonth, int empID, String companyCode) throws Exception {
        JSONArray jsa = new JSONArray();

        LinkedHashMap map = getSelectedEmpPayrollData(connection, salaryMonth, empID, companyCode);

        jsa.add(map);

        return jsa;
    }

    public LinkedHashMap getSelectedEmpPayrollData(Connection connection, String salaryMonth, int empID, String companyCode) throws Exception {
        LinkedHashMap map = new LinkedHashMap();

        /*Get All Categories (Wages Boards from DB)*/
        LinkedHashMap<Integer, String> categoriesMap = getCategories(connection);
        /*Get All Occupational Groups ( from DB)*/
        LinkedHashMap<Integer, String> occuGroupsMap = getOccupationalGroups(connection);
        /*Get All Allowance Liist( from DB)*/
        HashMap allowanceMap = getAllowanceList(connection);
        /*Get All Deduction Liist( from DB)*/
        HashMap deductionMap = getDeductionList(connection);
        /*Get All Noncash Liist( from DB)*/
        HashMap noncashMap = getNoncashList(connection);

        HashMap bankMap = getBankDetails(connection, salaryMonth, empID, companyCode);

        String query = "SELECT `payroll`.*,employee_details.`empinitialname`,employee_details.`empcode` FROM `payroll` "
                + "LEFT OUTER JOIN `employee_details` ON payroll.`empid` = employee_details.`empid` \n"
                + "WHERE payroll.`monthcode`=? AND payroll.`empid`=?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, salaryMonth);
        ps.setInt(2, empID);
        ResultSet rs = ps.executeQuery();
        if (rs.isBeforeFirst()) {
            rs.first();
            map.put("monthcode", rs.getString("monthcode"));
            map.put("empid", rs.getInt("empid"));
            map.put("empcode", rs.getInt("empcode"));
            map.put("empname", rs.getString("empinitialname"));
            map.put("status", rs.getString("status"));
            map.put("categoryid", rs.getInt("categoryid"));
            map.put("categoryname", categoriesMap.get(rs.getInt("categoryid")));
            map.put("groupid", rs.getInt("groupid"));
            map.put("groupname", occuGroupsMap.get(rs.getInt("groupid")));
            map.put("typofemp", rs.getString("typofemp"));
            map.put("division", rs.getString("division"));
            map.put("designation", rs.getString("designation"));
            map.put("salarytyp", rs.getString("salarytyp"));
            map.put("basicsalary", rs.getBigDecimal("basicsalary"));
            map.put("basicarreas", rs.getBigDecimal("basicarreas"));
            map.put("budgetallowance", rs.getBigDecimal("budgetallowance"));
            map.put("bra2", rs.getBigDecimal("bra2"));
            map.put("nopay", rs.getBigDecimal("nopay"));
            map.put("nopaydates", rs.getBigDecimal("nopaydates"));
            map.put("noofattendents", rs.getBigDecimal("noofattendents"));
            map.put("bonus", rs.getBigDecimal("bonus"));
            map.put("allow1", rs.getBigDecimal("allow1"));
            map.put("allow1name", allowanceMap.get("allow1"));
            map.put("allow2", rs.getBigDecimal("allow2"));
            map.put("allow2name", allowanceMap.get("allow2"));
            map.put("allow3", rs.getBigDecimal("allow3"));
            map.put("allow3name", allowanceMap.get("allow3"));
            map.put("allow4", rs.getBigDecimal("allow4"));
            map.put("allow4name", allowanceMap.get("allow4"));
            map.put("allow5", rs.getBigDecimal("allow5"));
            map.put("allow5name", allowanceMap.get("allow5"));
            map.put("paycut", rs.getBigDecimal("paycut"));
            map.put("saladvance", rs.getBigDecimal("saladvance"));
            map.put("otpay", rs.getBigDecimal("otpay"));
            map.put("loanid", rs.getInt("loanid"));
            map.put("loanvalue", rs.getBigDecimal("loanvalue"));
            map.put("deduct1", rs.getBigDecimal("deduct1"));
            map.put("deduct1name", deductionMap.get("deduct1"));
            map.put("deduct2", rs.getBigDecimal("deduct2"));
            map.put("deduct2name", deductionMap.get("deduct2"));
            map.put("deduct3", rs.getBigDecimal("deduct3"));
            map.put("deduct3name", deductionMap.get("deduct3"));
            map.put("deduct4", rs.getBigDecimal("deduct4"));
            map.put("deduct4name", deductionMap.get("deduct4"));
            map.put("deduct5", rs.getBigDecimal("deduct5"));
            map.put("deduct5name", deductionMap.get("deduct5"));
            map.put("paye", rs.getBigDecimal("paye"));
            map.put("etf", rs.getBigDecimal("etf"));
            map.put("epf8", rs.getBigDecimal("epf8"));
            map.put("epf12", rs.getBigDecimal("epf12"));
            map.put("grossalary", rs.getBigDecimal("grossalary"));
            map.put("netsalary", rs.getBigDecimal("netsalary"));
            map.put("noncash1", rs.getBigDecimal("noncash1"));
            map.put("noncash1_name", noncashMap.get("noncash1"));
            map.put("noncash2", rs.getBigDecimal("noncash2"));
            map.put("noncash2_name", noncashMap.get("noncash2"));
            map.put("noncash3", rs.getBigDecimal("noncash3"));
            map.put("noncash3_name", noncashMap.get("noncash3"));
            map.put("noncash4", rs.getBigDecimal("noncash4"));
            map.put("noncash4_name", noncashMap.get("noncash4"));
            map.put("noncash5", rs.getBigDecimal("noncash5"));
            map.put("noncash5_name", noncashMap.get("noncash5"));
            map.put("noncash6", rs.getBigDecimal("noncash6"));
            map.put("noncash6_name", noncashMap.get("noncash6"));
            map.put("noncash7", rs.getBigDecimal("noncash7"));
            map.put("noncash7_name", noncashMap.get("noncash7"));
            map.put("noncash8", rs.getBigDecimal("noncash8"));
            map.put("noncash8_name", noncashMap.get("noncash8"));
            map.put("noncash9", rs.getBigDecimal("noncash9"));
            map.put("noncash9_name", noncashMap.get("noncash9"));
            map.put("noncash10", rs.getBigDecimal("noncash10"));
            map.put("noncash10_name", noncashMap.get("noncash10"));
            map.put("bank1", bankMap.get("bank1"));
            map.put("accno1", bankMap.get("accno1"));
            map.put("amount1", bankMap.get("amount1"));
            map.put("bank2", bankMap.get("bank2"));
            map.put("accno2", bankMap.get("accno2"));
            map.put("amount2", bankMap.get("amount2"));
        }
        ps.close();

        return map;
    }

    public LinkedHashMap<Integer, String> getCategories(Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT `categoryid`,`categoryname` FROM `catagory`;");
        ResultSet result = ps.executeQuery();
        LinkedHashMap<Integer, String> wageBoards = new LinkedHashMap<>();
        if (result.isBeforeFirst()) {
            while (result.next()) {
                wageBoards.put(result.getInt("categoryid"), result.getString("categoryname"));
            }
        }
        ps.close();
        result.close();
        return wageBoards;
    }

    public LinkedHashMap<Integer, String> getOccupationalGroups(Connection connection) throws SQLException {
        LinkedHashMap<Integer, String> occuGroups = new LinkedHashMap<>();
        String query = "SELECT `groupid`,`groupname`,`user` FROM `occupational_group` ;";

        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                occuGroups.put(rs.getInt("groupid"), rs.getString("groupname"));
            }
        }
        ps.close();
        return occuGroups;
    }

    private JSONArray getLoanDetails(Connection connection, String salaryMonth, int empID) throws SQLException {

        JSONArray array = new JSONArray();
        Map m = null;
        String query = "SELECT ls.`loanid`,lo.`typeofloan`,ls.`monthlyamount`,lo.`loanvalue` FROM `loan` AS lo RIGHT OUTER JOIN `loansettlement` AS ls ON lo.`loanid`=ls.`loanid`\n"
                + "WHERE ls.`empid`='" + empID + "' AND ls.`monthcode`='" + salaryMonth + "' ;";
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                m = new HashMap();
                m.put("loanid", rs.getInt("loanid"));
                m.put("typeofloan", rs.getString("typeofloan"));
                m.put("monthlyamount", rs.getString("monthlyamount"));
                m.put("loanvalue", rs.getBigDecimal("loanvalue"));
                array.add(m);
                m = null;
            }
        }
        ps.close();
        return array;
    }

    public HashMap getBankDetails(Connection connection, String salaryMonth, int empID, String companyCode) throws Exception {
        HashMap map = new HashMap();

        InterfaceBank bank = new ImplBank();
        List<BeanBank> bankList = bank.getBankList(connection);
        Map bankMap = new HashMap();

        for (int i = 0; i < bankList.size(); i++) {
            bankMap.put(bankList.get(i).getId(), bankList.get(i).getBankname());
        }

        String query = "SELECT `bankid1`,`accno1`,`amount1`,`bankid2`,`accno2`,`amount2` FROM `emppaybank`\n"
                + "WHERE `monthcode`='" + salaryMonth + "' AND `comcode`='" + companyCode + "' AND `empid`='" + empID + "';";
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        if (rs.isBeforeFirst()) {
            rs.first();
            map.put("bank1", (bankMap.get(rs.getInt("bankid1")) == null ? "-" : bankMap.get(rs.getInt("bankid1"))));
            map.put("accno1", (rs.getString("accno1").trim().isEmpty() ? "-" : rs.getString("accno1").trim()));
            map.put("amount1", rs.getBigDecimal("amount1"));
            map.put("bank2", (bankMap.get(rs.getInt("bankid2")) == null ? "-" : bankMap.get(rs.getInt("bankid2"))));
            map.put("accno2", (rs.getString("accno2").trim().isEmpty() ? "-" : rs.getString("accno2").trim()));
            map.put("amount2", rs.getBigDecimal("amount2"));

        }
        ps.close();
        return map;
    }

    public String updatePayrollData(Connection emp_connection, String SalaryMonth, int empID, BigDecimal basicSal, BigDecimal salArreas, BigDecimal payCut,
            BigDecimal NoPay, BigDecimal nopaydates, BigDecimal otpay, String remark) throws SQLException {
        try {
            emp_connection.setAutoCommit(false);

            HashMap payrollMap = getSelectedEmpPayrollData(emp_connection, SalaryMonth, empID, BeanSystemLog.getComcode());

            HashMap EpfEtf_Map = EpfEtf_Calculation(emp_connection,
                    SalaryMonth,
                    (Integer.parseInt(payrollMap.get("empcode").toString() == null ? "0" : payrollMap.get("empcode").toString())) > 0,//EPFHave
                    basicSal,
                    salArreas,
                    new BigDecimal(payrollMap.get("budgetallowance") == null ? "0" : payrollMap.get("budgetallowance").toString()).round(MathContext.DECIMAL128),
                    new BigDecimal(payrollMap.get("bra2") == null ? "0" : payrollMap.get("bra2").toString()).round(MathContext.DECIMAL128),
                    payCut,//payCut
                    NoPay,//NoPay
                    new BigDecimal(payrollMap.get("allow1") == null ? "0" : payrollMap.get("allow1").toString()).round(MathContext.DECIMAL128),//Attendance Incentive
                    new BigDecimal(payrollMap.get("allow2") == null ? "0" : payrollMap.get("allow2").toString()).round(MathContext.DECIMAL128),//Performance Incentive
                    new BigDecimal(payrollMap.get("allow3") == null ? "0" : payrollMap.get("allow3").toString()).round(MathContext.DECIMAL128),//LeaveEncashment
                    new BigDecimal(payrollMap.get("allow4") == null ? "0" : payrollMap.get("allow4").toString()).round(MathContext.DECIMAL128),
                    new BigDecimal(payrollMap.get("allow5") == null ? "0" : payrollMap.get("allow5").toString()).round(MathContext.DECIMAL128),
                    new BigDecimal(payrollMap.get("saladvance") == null ? "0" : payrollMap.get("saladvance").toString()).round(MathContext.DECIMAL128),
                    otpay,//otpay
                    new BigDecimal(payrollMap.get("loanvalue") == null ? "0" : payrollMap.get("loanvalue").toString()).round(MathContext.DECIMAL128),
                    new BigDecimal(payrollMap.get("deduct1") == null ? "0" : payrollMap.get("deduct1").toString()).round(MathContext.DECIMAL128),
                    new BigDecimal(payrollMap.get("deduct2") == null ? "0" : payrollMap.get("deduct2").toString()).round(MathContext.DECIMAL128),
                    new BigDecimal(payrollMap.get("deduct3") == null ? "0" : payrollMap.get("deduct3").toString()).round(MathContext.DECIMAL128),
                    new BigDecimal(payrollMap.get("deduct4") == null ? "0" : payrollMap.get("deduct4").toString()).round(MathContext.DECIMAL128),
                    new BigDecimal(payrollMap.get("deduct5") == null ? "0" : payrollMap.get("deduct5").toString()).round(MathContext.DECIMAL128),
                    new BigDecimal(payrollMap.get("bonus") == null ? "0" : payrollMap.get("bonus").toString()).round(MathContext.DECIMAL128),
                    new BigDecimal(payrollMap.get("noncash1") == null ? "0" : payrollMap.get("noncash1").toString()).round(MathContext.DECIMAL128),
                    new BigDecimal(payrollMap.get("noncash2") == null ? "0" : payrollMap.get("noncash2").toString()).round(MathContext.DECIMAL128),
                    new BigDecimal(payrollMap.get("noncash3") == null ? "0" : payrollMap.get("noncash3").toString()).round(MathContext.DECIMAL128),
                    new BigDecimal(payrollMap.get("noncash4") == null ? "0" : payrollMap.get("noncash4").toString()).round(MathContext.DECIMAL128),
                    new BigDecimal(payrollMap.get("noncash5") == null ? "0" : payrollMap.get("noncash5").toString()).round(MathContext.DECIMAL128),
                    new BigDecimal(payrollMap.get("noncash6") == null ? "0" : payrollMap.get("noncash6").toString()).round(MathContext.DECIMAL128),
                    new BigDecimal(payrollMap.get("noncash7") == null ? "0" : payrollMap.get("noncash7").toString()).round(MathContext.DECIMAL128),
                    new BigDecimal(payrollMap.get("noncash8") == null ? "0" : payrollMap.get("noncash8").toString()).round(MathContext.DECIMAL128),
                    new BigDecimal(payrollMap.get("noncash9") == null ? "0" : payrollMap.get("noncash9").toString()).round(MathContext.DECIMAL128),
                    new BigDecimal(payrollMap.get("noncash10") == null ? "0" : payrollMap.get("noncash10").toString()).round(MathContext.DECIMAL128));

            /*Assig return values to bean*/
            BeanPayroll bean = new BeanPayroll();
            bean.setMonthcode(SalaryMonth);
            bean.setEmpid(empID);
            bean.setComcode(BeanSystemLog.getComcode());
            bean.setEtf(new BigDecimal(EpfEtf_Map.get("etf").toString()).round(MathContext.DECIMAL128));
            bean.setEpf8(new BigDecimal(EpfEtf_Map.get("epf8").toString()).round(MathContext.DECIMAL128));
            bean.setEpf12(new BigDecimal(EpfEtf_Map.get("epf12").toString()).round(MathContext.DECIMAL128));
            bean.setPaye(new BigDecimal(EpfEtf_Map.get("payee").toString()).round(MathContext.DECIMAL128));
            bean.setGrossalary(new BigDecimal(EpfEtf_Map.get("grosSal").toString()).round(MathContext.DECIMAL128));
            bean.setNetsalary(new BigDecimal(EpfEtf_Map.get("netsal").toString()).round(MathContext.DECIMAL128));
            bean.setRemark(remark);


            /*When saving net salary cannot be minus*/
            if (new BigDecimal(EpfEtf_Map.get("netsal").toString()).compareTo(BigDecimal.ZERO) == -1) {
                return "Error:Net salary cannot be minus";
            }

            String query = "UPDATE `payroll` SET `basicsalary`=?,`basicarreas`=?,`nopay`=?,`nopaydates`=?,`paycut`=?,`paye`=?,`etf`=?,`epf8`=?,`epf12`=?,`grossalary`=?,`netsalary`=?,\n"
                    + "`user`=?,`remark`=? WHERE `monthcode`=? AND `empid`=?";
            PreparedStatement ps = emp_connection.prepareStatement(query);
            ps.setBigDecimal(1, basicSal);
            ps.setBigDecimal(2, salArreas);
            ps.setBigDecimal(3, NoPay);
            ps.setBigDecimal(4, nopaydates);
            ps.setBigDecimal(5, payCut);
            ps.setBigDecimal(6, bean.getPaye());
            ps.setBigDecimal(7, bean.getEtf());
            ps.setBigDecimal(8, bean.getEpf8());
            ps.setBigDecimal(9, bean.getEpf12());
            ps.setBigDecimal(10, bean.getGrossalary());
            ps.setBigDecimal(11, bean.getNetsalary());
            ps.setString(12, BeanSystemLog.getUser());
            ps.setString(13, remark);
            ps.setString(14, SalaryMonth);
            ps.setInt(15, empID);
            ps.executeUpdate();
            ps.close();

            String saveBank = saveBankDetails(emp_connection, bean);
            if (saveBank.startsWith("Error:")) {
                return saveBank;
            }

            String saveQuery = "INSERT INTO `log`(`event`,`description`,`comcode`,`user`) VALUES (?,?,?,?);";
            PreparedStatement pss = emp_connection.prepareStatement(saveQuery);
            pss.setString(1, "Update Payroll Data");
            pss.setString(2, "SalaryMonth: " + SalaryMonth + " EmpId:" + empID + " Reason:" + remark);
            pss.setString(3, BeanSystemLog.getComcode());
            pss.setString(4, BeanSystemLog.getUser());
            pss.execute();
            pss.close();

            emp_connection.commit();
            emp_connection.setAutoCommit(true);

            return "Details updated sucessfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error:" + e.getLocalizedMessage();
        }

    }

    private String saveBankDetails(Connection connection, BeanPayroll bean) throws SQLException {
        BeanEmployeeBank bank = collectEmployeeBankDetails(connection, bean.getComcode(), bean.getEmpid());

        if (bank == null) {
            return "Error:Employee Bank Data Not Found";
        }

        bank = reEditedBankDetails(bank, bean.getNetsalary());

        if (bank == null) {
            return "Error:Check the employee banking details / Net salary remitance issue";
        }
        BigDecimal totBankAmount = BigDecimal.ZERO;
        totBankAmount = totBankAmount.add(bank.getAmount1()).add(bank.getAmount2());

        /*--------------------------------------Check total abank amount and Net amount-----------------------------------*/
        System.out.println("Net Salary       : " + bean.getNetsalary());
        System.out.println("Tot Banks Amount : " + totBankAmount);

        if (totBankAmount.compareTo(bean.getNetsalary()) != 0) {
            return "Net amount and bank amounts do not balance";
        }

        /*------------------------First delete empID related data from emppaybank table---------------------------------*/
        String query = "DELETE FROM `emppaybank` WHERE `monthcode`=? AND `empid`=? AND `comcode`=?;";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, bean.getMonthcode());
        ps.setInt(2, bean.getEmpid());
        ps.setString(3, bean.getComcode());
        ps.execute();
        ps.close();

        String sql_bank = "INSERT INTO `emppaybank`(`monthcode`,`empid`,`bankid1`,`accno1`,`amount1`,`bankid2`,`accno2`,`amount2`,`comcode`)\n"
                + "VALUES (?,?,?,?,?,?,?,?,?);";
        ps = connection.prepareStatement(sql_bank);
        ps.setString(1, bean.getMonthcode());
        ps.setInt(2, bean.getEmpid());
        ps.setInt(3, bank.getBankid1());
        ps.setString(4, bank.getAccno1());
        ps.setBigDecimal(5, bank.getAmount1());
        ps.setInt(6, bank.getBankid2());
        ps.setString(7, bank.getAccno2());
        ps.setBigDecimal(8, bank.getAmount2());
        ps.setString(9, BeanSystemLog.getComcode());
        ps.execute();
        ps.close();

        return "Sucessfully updated";
    }

    public String finalizePayRoll(Connection connection, String salaryMonth, String salaryType, String comCode) throws SQLException {

        StringBuffer sb = chechErrorList(connection, salaryMonth, salaryType, comCode);
        if (sb != null) {
            return sb.toString();
        } else {
            /*------------------------------------------------if No errors execute payroll Method-------------------------------------*/
            String messsage = checkTotal_EPF_ETF_Gross_Net(connection, salaryMonth, salaryType, comCode);
            return messsage;
        }

    }

    private StringBuffer chechErrorList(Connection connection, String salatyMonth, String salaryType, String comCode) throws SQLException {

        StringBuffer buffer = new StringBuffer("");

        String query = "SELECT \n"
                + "  payroll.`empid` AS EmpId,\n"
                + "  employee_details.`empcode` AS EmpCode,\n"
                + "  employee_details.`empinitialname` AS EmpInitialName \n"
                + "FROM\n"
                + "  `payroll` payroll \n"
                + "  LEFT OUTER JOIN `emppaybank` emppaybank \n"
                + "    ON payroll.`monthcode` = emppaybank.`monthcode`\n"
                + "    AND payroll.`empid` = emppaybank.`empid`\n"
                + "  LEFT OUTER JOIN `employee_details` employee_details \n"
                + "    ON payroll.`empid` = employee_details.`empid`\n"
                + "WHERE payroll.`monthcode` = ? \n"
                + "  AND payroll.`salarytyp` = ? \n"
                + "  AND payroll.`comcode`= ? \n"
                + "  AND (\n"
                + "    payroll.`netsalary` - emppaybank.`amount1` - emppaybank.`amount2` > 0 \n"
                + "    OR payroll.`netsalary` - emppaybank.`amount1`- emppaybank.`amount2` < 0\n"
                + "  )";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, salatyMonth);
        ps.setString(2, salaryType);
        ps.setString(3, comCode);
        ResultSet rs = ps.executeQuery();
        System.out.println("Check Error List Query : " + ps.toString());

        buffer = buffer.append("Error:Payroll Can't Proceed").append("#");
        String result = "";

        int count = 0;

        while (rs.next()) {
            result = "Emp ID:" + rs.getString("EmpId") + " Employee:" + rs.getString("EmpInitialName") + " EPF:" + rs.getString("EmpCode");
            buffer.append(result).append("#");
            count = count + 1;
        }

        rs.close();
        ps.close();
        if (count > 0) {
            return buffer;
        }
        return null;
    }

    private String checkTotal_EPF_ETF_Gross_Net(Connection connection, String salaryMonth, String salaryType, String comCode) throws SQLException {

        /*-----------------------------------Get epf & eft percentages-----------------------------------------------*/
        BigDecimal epfEmploye = null;
        BigDecimal epfCompany = null;
        BigDecimal etf = null;

        String query = "SELECT EpfEmp,EpfCompany,Etf FROM epfetf;";
        PreparedStatement ps = connection.prepareStatement(query);

        ResultSet rs = ps.executeQuery();
        System.out.println("Get EPF,ETF percentages " + ps.toString());
        while (rs.next()) {
            epfEmploye = rs.getBigDecimal("EpfEmp");
            epfCompany = rs.getBigDecimal("EpfCompany");
            etf = rs.getBigDecimal("Etf");
        }
        ps.close();

        /*-----------------Check if EPF8 amounts are tallied----------------------------------------*/
        if (!isTotalEPF8Equal(connection, salaryMonth, salaryType, comCode, epfEmploye)) {
            return "Error: Total EPF amounts do not balance.";
        }

        /*-----------------Check if ETF amounts are tallied-----------------------------------------*/
        if (!isTotalETFEqual(connection, salaryMonth, salaryType, comCode, etf)) {
            return "Error : Total ETF amounts do not balance";
        }

        /*-----------------Check if Gross salary amounts are tallied--------------------------------*/
        if (!isTotalGrossAmountEqual(connection, salaryMonth, salaryType, comCode)) {
            return "Error : Total Gross Salary amounts do not balance";
        }

        /*-----------------Check if Net salary amounts are tallied--------------------------------*/
        if (!isTotalNetAmountEqual(connection, salaryMonth, salaryType, comCode)) {
            String invalidEmpIDs = getInvalidNetSalaryEmployees(connection, salaryMonth, comCode);
            return "Error:Net Salary.Check System ID(s) " + invalidEmpIDs;
        }

        /*Check total Gross and  Total Net Amount <0*/
        if (!isTotalNetAmountNegative(connection, salaryMonth, salaryType, comCode)) {
            return "Error: Minus Net Salary";
        }

        if (isFinalized(connection, salaryMonth, comCode)) {
            return "Error: Selected Month is already finalized...!";
        } else {
            return runPayroll(connection, salaryMonth, comCode, salaryType);
        }

    }

    public boolean isTotalEPF8Equal(Connection connection, String salaryMonth, String salaryType, String comCode, BigDecimal epfEmploye) throws SQLException {
        boolean isTallied = false;

        BigDecimal calculatedValue = BigDecimal.ZERO;
        BigDecimal realValue = BigDecimal.ZERO;

        /*-----------------------------------Calculate total epf amount according to the salary month and salary type--------------------------------------*/
        //sal4Epf = bsal.add(salarre).add(bra1).add(bra2).add(LeaveEncashment).subtract(NoPay.add(payCut))
        String query = "SELECT ((SUM(`basicsalary`+`basicarreas`+`budgetallowance`+`bra2`+`allow3`)-SUM(`nopay`+`paycut`))*?) AS totalEPF \n"
                + "FROM `payroll` WHERE `monthcode`=? AND `salarytyp`=? AND `comcode`=? AND `epf8`>0;";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setBigDecimal(1, epfEmploye);
        ps.setString(2, salaryMonth);
        ps.setString(3, salaryType);
        ps.setString(4, comCode);
        ResultSet rs = ps.executeQuery();
        System.out.println("Calculated epf Query : " + ps.toString());

        if (rs.isBeforeFirst()) {
            rs.first();
            calculatedValue = rs.getBigDecimal("totalEPF").setScale(2, RoundingMode.DOWN);
        }
        ps.close();

        /*----------------------------------------Get total EPF amount--------------------------------------------*/
        query = "SELECT SUM(`epf8`) AS totEPF FROM `payroll` WHERE `monthcode`=? AND `salarytyp`=? AND `comcode`=?;";
        ps = connection.prepareStatement(query);
        ps.setString(1, salaryMonth);
        ps.setString(2, salaryType);
        ps.setString(3, comCode);
        rs = ps.executeQuery();
        System.out.println("Total epf Query : " + ps.toString());
        if (rs.isBeforeFirst()) {
            rs.first();
            realValue = rs.getBigDecimal("totEPF");
        }
        ps.close();

        /*--------------------Check calculated total epf and saved total epf amounts--------------------------------------*/
        System.out.println("Total Calculate EPF " + calculatedValue);
        System.out.println("Real Val " + realValue);
        System.out.println("");

        /*If difference is lower than 1 or equal to 1, allow finalizing*/
 /*Get the difference of calculated value and real efp val*/
        BigDecimal difference = calculatedValue.subtract(realValue).abs();
        System.out.println("EPF  Difference : " + difference);
        System.out.println("");
        if (difference.compareTo(BigDecimal.ZERO) == 0 || difference.compareTo(BigDecimal.ONE) == -1 || difference.compareTo(BigDecimal.ONE) == 0) {
            isTallied = true;
        } else {
            isTallied = false;
        }
        return isTallied;
    }

    public boolean isTotalETFEqual(Connection connection, String salaryMonth, String salaryType, String comCode, BigDecimal etf) throws SQLException {
        boolean isTallied = false;

        BigDecimal calculatedValue = BigDecimal.ZERO;
        BigDecimal realValue = BigDecimal.ZERO;

        /*-----------------------------------Calculate total etf amount according to the salary month and salary type--------------------------------------*/
        String query = "SELECT ((SUM(`basicsalary`+`basicarreas`+`budgetallowance`+`bra2`+`allow3`)-SUM(`nopay`+`paycut`))*?) AS totalETF FROM `payroll` \n"
                + "WHERE `monthcode`=? AND `salarytyp`=? AND `comcode`=? AND `etf`>0;";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setBigDecimal(1, etf);
        ps.setString(2, salaryMonth);
        ps.setString(3, salaryType);
        ps.setString(4, comCode);
        ResultSet rs = ps.executeQuery();
        System.out.println("Calculated etf Query : " + ps.toString());

        if (rs.isBeforeFirst()) {
            rs.first();
            calculatedValue = rs.getBigDecimal("totalETF");
        }
        ps.close();

        /*----------------------------------------Get total ETF amount--------------------------------------------*/
        query = "SELECT SUM(`etf`) AS totETF FROM `payroll` WHERE `monthcode`=? AND `salarytyp`=? AND `comcode`=?;";
        ps = connection.prepareStatement(query);
        ps.setString(1, salaryMonth);
        ps.setString(2, salaryType);
        ps.setString(3, comCode);
        rs = ps.executeQuery();
        System.out.println("total etf Query : " + ps.toString());
        if (rs.isBeforeFirst()) {
            rs.first();
            realValue = rs.getBigDecimal("totETF");
        }
        ps.close();

        /*--------------------Check calculated total ETF and saved total ETF amounts--------------------------------------*/
        System.out.println("Total Calculate ETF " + calculatedValue);
        System.out.println("Real ETF Val " + realValue);

        /*If difference is lower than 1 or equal to 1, allow finalizing*/
 /*Get the difference of calculated value and real efp val*/
        BigDecimal difference = calculatedValue.subtract(realValue).abs();
        System.out.println("ETF  Difference : " + difference);
        System.out.println("");
        if (difference.compareTo(BigDecimal.ZERO) == 0 || difference.compareTo(BigDecimal.ONE) == -1 || difference.compareTo(BigDecimal.ONE) == 0) {
            isTallied = true;
        } else {
            isTallied = false;
        }
        return isTallied;
    }

    public boolean isTotalGrossAmountEqual(Connection connection, String salaryMonth, String salaryType, String comCode) throws SQLException {
        boolean isTallied = false;

        BigDecimal calculatedValue = BigDecimal.ZERO;
        BigDecimal realValue = BigDecimal.ZERO;

        /*-----------------------------------Calculate total Gross amount according to the salary month and salary type--------------------------------------*/
        String query = "SELECT SUM(`basicsalary`+`basicarreas`+`budgetallowance`+`bra2`+`bonus`+`allow1`+`allow2`+`allow3`+`allow4`+`allow5`+`noncash1`+`noncash2`+\n"
                + "`noncash3`+`noncash4`+`noncash5`+`noncash6`+`noncash7`+`noncash8`+`noncash9`+`noncash10`-`nopay`-`paycut`) AS totalGrossAmount FROM `payroll` \n"
                + "WHERE `monthcode`=? AND `salarytyp`=? AND `comcode`=?";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, salaryMonth);
        ps.setString(2, salaryType);
        ps.setString(3, comCode);
        ResultSet rs = ps.executeQuery();
        System.out.println("Calculate Gross Query : " + ps.toString());
        if (rs.isBeforeFirst()) {
            rs.first();
            calculatedValue = rs.getBigDecimal("totalGrossAmount");
        }

        ps.close();
        /*----------------------------------------Get total Gross amount--------------------------------------------*/
        query = "SELECT SUM(`grossalary`) AS totGross FROM `payroll` WHERE `monthcode`=? AND `salarytyp`=? AND `comcode`=?;";
        ps = connection.prepareStatement(query);
        ps.setString(1, salaryMonth);
        ps.setString(2, salaryType);
        ps.setString(3, comCode);
        rs = ps.executeQuery();
        System.out.println("total Gross Query : " + ps.toString());
        if (rs.isBeforeFirst()) {
            rs.first();
            realValue = rs.getBigDecimal("totGross");
        }
        ps.close();

        /*--------------------Check calculated total GrosSal and saved total GrosSal amounts--------------------------------------*/
        System.out.println("Calculate Gross " + calculatedValue);
        System.out.println("Real Gross Val " + realValue);

        /*If difference is lower than 1 or equal to 1, allow finalizing*/
 /*Get the difference of calculated value and real efp val*/
        BigDecimal difference = calculatedValue.subtract(realValue).abs();
        System.out.println("Gross Value Difference : " + difference);
        System.out.println("");
        if (difference.compareTo(BigDecimal.ZERO) == 0 || difference.compareTo(BigDecimal.ONE) == -1 || difference.compareTo(BigDecimal.ONE) == 0) {
            isTallied = true;
        } else {
            isTallied = false;
        }
        return isTallied;
    }

    public boolean isTotalNetAmountEqual(Connection connection, String salaryMonth, String salaryType, String comCode) throws SQLException {
        boolean isTallied = false;

        BigDecimal calculatedValue = BigDecimal.ZERO;
        BigDecimal realValue = BigDecimal.ZERO;

        /*-----------------------------------Calculate total Net amount according to the salary month and salary type--------------------------------------*/
        String query = "SELECT SUM(`basicsalary`+`basicarreas`+`budgetallowance`+`bra2`+`allow1`+`allow2`+`allow3`+`allow4`+`allow5`-\n"
                + "`nopay`-`paycut`-`epf8`-`saladvance`-`loanvalue`-`paye`-`deduct1`-`deduct2`-`deduct3`-`deduct4`-`deduct5`) AS totalNetAmount FROM `payroll` \n"
                + "WHERE `monthcode`=? AND `salarytyp`=? AND `comcode`=?";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, salaryMonth);
        ps.setString(2, salaryType);
        ps.setString(3, comCode);
        ResultSet rs = ps.executeQuery();

        System.out.println("Calculate Net Query : " + ps.toString());

        if (rs.isBeforeFirst()) {
            rs.first();
            calculatedValue = rs.getBigDecimal("totalNetAmount");
        }
        ps.close();

        /*----------------------------------------Get total Net amount--------------------------------------------*/
        query = "SELECT SUM(`netsalary`) AS totNet FROM `payroll` WHERE `monthcode`=? AND `salarytyp`=? AND `comcode`=?;";
        ps = connection.prepareStatement(query);
        ps.setString(1, salaryMonth);
        ps.setString(2, salaryType);
        ps.setString(3, comCode);
        rs = ps.executeQuery();
        System.out.println("Total Net Query : " + ps.toString());

        if (rs.isBeforeFirst()) {
            rs.first();
            realValue = rs.getBigDecimal("totNet");
        }
        ps.close();

        /*--------------------Check calculated total GrosSal and saved total GrosSal amounts--------------------------------------*/
        System.out.println("Calculate Net " + calculatedValue);
        System.out.println("Real Net Val " + realValue);

        /*If difference is lower than 1 or equal to 1, allow finalizing*/
 /*Get the difference of calculated value and real efp val*/
        BigDecimal difference = calculatedValue.subtract(realValue).abs();
        System.out.println("Net Value Difference : " + difference);
        System.out.println("");

        if (difference.compareTo(BigDecimal.ZERO) == 0 || difference.compareTo(BigDecimal.ONE) == -1 || difference.compareTo(BigDecimal.ONE) == 0) {
            isTallied = true;
        } else {
            isTallied = false;
        }
        return isTallied;
    }

    public boolean isTotalNetAmountNegative(Connection connection, String salaryMonth, String salaryType, String comCode) throws SQLException {
        boolean isNegative = false;

        BigDecimal calculatedValue = BigDecimal.ZERO;
        BigDecimal realValue = BigDecimal.ZERO;

        /*-----------------------------------Calculate total Net amount according to the salary month and salary type--------------------------------------*/
        String query = "SELECT SUM(`basicsalary`+`basicarreas`+`budgetallowance`+`bra2`+`allow1`+`allow2`+`allow3`+`allow4`+`allow5`-\n"
                + "`nopay`-`paycut`-`epf8`-`saladvance`-`loanvalue`-`paye`-`deduct1`-`deduct2`-`deduct3`-`deduct4`-`deduct5`) AS totalNetAmount FROM `payroll` \n"
                + "WHERE `monthcode`=? AND `salarytyp`=? AND `comcode`=?";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, salaryMonth);
        ps.setString(2, salaryType);
        ps.setString(3, comCode);
        ResultSet rs = ps.executeQuery();

        System.out.println("Check Net Amount Negative Query : " + ps.toString());

        if (rs.isBeforeFirst()) {
            isNegative = true;
        }
        ps.close();
        return isNegative;
    }

    private String runPayroll(Connection connection, String salaryMonth, String comcode, String saltyp) throws SQLException {
        String message;
        try {
            connection.setAutoCommit(false);

            /*----------------------------------------------------UPDATE PAYROLL TABLE------------------------------------------------*/
            PreparedStatement ps = connection.prepareStatement("UPDATE `payroll` SET `activate`=1 WHERE `monthcode`=? AND `salarytyp`=? AND `comcode`=?");
            ps.setString(1, salaryMonth);
            ps.setString(2, saltyp);
            ps.setString(3, comcode);
            ps.executeUpdate();
            ps.close();
            System.out.println("Update payroll done....");

            /*----------------------------------------------------UPDATE MONTH TABLE------------------------------------------------*/
            String runpay = "UPDATE `month` SET `runpayroll`=1 WHERE `monthcode`=? AND `comcode`=?";
            ps = connection.prepareStatement(runpay);
            ps.setString(1, salaryMonth);
            ps.setString(2, comcode);
            ps.executeUpdate();
            ps.close();
            System.out.println("Update month done....");

            /*----------------------------------------------------UPDATE LOAN SETTELMENT TABLE------------------------------------------------*/
            ps = connection.prepareStatement("UPDATE loansettlement SET `active`=1 WHERE `monthcode`=? AND `salarytype`=?");
            ps.setString(1, salaryMonth);
            ps.setString(2, saltyp);
            ps.executeUpdate();
            ps.close();
            System.out.println("Update Loan done....");

            /*----------------------------Save Data Into Log Table------------------------------------------------*/
            String saveQuery = "INSERT INTO `log`(`event`,`description`,`comcode`,`user`) VALUES (?,?,?,?);";
            ps = connection.prepareStatement(saveQuery);
            ps.setString(1, "Finalize Payroll");
            ps.setString(2, "Salary Month : " + salaryMonth + " | Salary Type : " + saltyp);
            ps.setString(3, BeanSystemLog.getComcode());
            ps.setString(4, BeanSystemLog.getUser());
            ps.execute();
            ps.close();

            connection.commit();
            connection.setAutoCommit(true);

            message = "Run Payroll Wizard Sucessfully";

        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
            message = "Error:" + e.getLocalizedMessage();
        }
        return message;
    }

    public boolean isFinalized(Connection connection, String salaryMonth, String comCode) throws SQLException {

        boolean isFinalized = false;
        String qery = "SELECT `runpayroll` FROM `month`  WHERE `monthcode` = ?  AND `comcode` = ? ;";
        PreparedStatement ps = connection.prepareStatement(qery);
        ps.setString(1, salaryMonth);
        ps.setString(2, comCode);
        ResultSet rs = ps.executeQuery();
        if (rs.isBeforeFirst()) {
            rs.first();
            if (rs.getInt("runpayroll") == 1) {
                isFinalized = true;
            }
        }
        ps.close();
        return isFinalized;
    }

    public boolean isSuccessfullyDeleted(Connection connection, int empID, String user, String remark, String Company, String SalaryMonth, String SalaryType) throws SQLException {
        connection.setAutoCommit(false);
        boolean isSuccess = false;
        //delete Bank Deatails
        PreparedStatement ps = connection.prepareStatement("DELETE FROM `emppaybank` WHERE `monthcode`=? AND `empid`=? AND `comcode`=?");
        ps.setString(1, SalaryMonth);//Month
        ps.setInt(2, empID);//EMP ID
        ps.setString(3, Company);
        ps.executeUpdate();
        ps.close();

        // Delete Payroll Table
        ps = connection.prepareStatement("DELETE FROM `payroll` WHERE `monthcode`=? AND `empid`=?");
        ps.setString(1, SalaryMonth);
        ps.setInt(2, empID);
        ps.executeUpdate();
        ps.close();

        //Delet Loan and FestivalAdvance
        ps = connection.prepareStatement("DELETE FROM `loansettlement` WHERE `monthcode`=? AND `empid`=?");
        ps.setString(1, SalaryMonth);//Month
        ps.setInt(2, empID);//EMP ID
        ps.executeUpdate();
        ps.close();


        /*----------------------------Save Data Into Log Table------------------------------------------------*/
        String saveQuery = "INSERT INTO `log`(`event`,`description`,`comcode`,`user`) VALUES (?,?,?,?);";
        ps = connection.prepareStatement(saveQuery);
        ps.setString(1, "Delete Payroll Data");
        ps.setString(2, "EmpID : " + empID + " | Salary Month : " + SalaryMonth + " | Salary Type : " + SalaryType + " | Remark : " + remark);
        ps.setString(3, BeanSystemLog.getComcode());
        ps.setString(4, BeanSystemLog.getUser());
        ps.execute();
        ps.close();

        isSuccess = true;

        connection.commit();
        connection.setAutoCommit(true);

        return isSuccess;
    }

    public String deleteAllPayrollEntries(Connection connection, String Company, String SalaryMonth, String SalaryType) throws SQLException {

        try {
            PreparedStatement ps;
            String query;

            connection.setAutoCommit(false);
            /* 1).--------Delete from emppaybank-------------------------*/
            query = "DELETE FROM `emppaybank` WHERE `monthcode`=? AND (SELECT `salarytyp` FROM `payroll` WHERE payroll.`empid`=`emppaybank`.`empid` AND `monthcode`=?) = ?;";
            ps = connection.prepareStatement(query);
            ps.setString(1, SalaryMonth);
            ps.setString(2, SalaryMonth);
            ps.setString(3, SalaryType);
            ps.execute();
            System.out.println("Query 1 : " + ps.toString());
            ps.close();

            /*2).------------Delete from loansettlement------------------*/
            query = "DELETE FROM `loansettlement` WHERE `monthcode`=? AND `salarytype`=?;";
            ps = connection.prepareStatement(query);
            ps.setString(1, SalaryMonth);
            ps.setString(2, SalaryType);
            ps.execute();
            System.out.println("Query 2 : " + ps.toString());
            ps.close();

            /*3).---------Delete from payroll table---------*/
            query = "DELETE FROM `payroll` WHERE `monthcode`=? AND `salarytyp`=?;";
            ps = connection.prepareStatement(query);
            ps.setString(1, SalaryMonth);
            ps.setString(2, SalaryType);
            ps.execute();
            System.out.println("Query 3 : " + ps.toString());
            ps.close();

            /*4).-----Save Data Into Log Table----------------*/
            String saveQuery = "INSERT INTO `log`(`event`,`description`,`comcode`,`user`) VALUES (?,?,?,?);";
            ps = connection.prepareStatement(saveQuery);
            ps.setString(1, "Delete Payroll Wizard");
            ps.setString(2, "Salary Month : " + SalaryMonth + " | Salary Type : " + SalaryType);
            ps.setString(3, BeanSystemLog.getComcode());
            ps.setString(4, BeanSystemLog.getUser());
            ps.execute();
            ps.close();

            connection.commit();
            connection.setAutoCommit(true);

        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
            return "Error:" + e.getLocalizedMessage();
        }
        return "Sucessfully Clear Payroll Data";
    }

    public boolean isAllStopped(Connection connection, String salaryMonth, String company) throws SQLException {
        boolean isAllstopped = false;

        String query = "SELECT  COUNT(*) FROM `month`  WHERE `monthcode` = ?  AND `comcode` = ?   AND `stopattendance` = 1  AND `stopallowance` = 1 AND `stopadvance`=1 ;";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, salaryMonth);
        ps.setString(2, company);
        ResultSet rs = ps.executeQuery();
        System.out.println("Stop All Options Query : " + ps.toString());
        rs.first();
        if (rs.getInt(1) == 1) {
            isAllstopped = true;
        }
        ps.close();
        return isAllstopped;
    }

    private String getInvalidNetSalaryEmployees(Connection connection, String salaryMonth, String comCode) throws SQLException {
        StringBuffer empIDs = new StringBuffer();
        String query;
        query = "SELECT \n"
                + " `empid` AS EID \n"
                + "FROM\n"
                + "  `payroll` \n"
                + "WHERE `monthcode` = ? \n"
                + "  AND `comcode` = ? \n"
                + "  AND `netsalary` - (`basicsalary`+`basicarreas`+`budgetallowance`+`bra2`+`allow1`+`allow2`+`allow3`+`allow4`+`allow5`-\n"
                + "`nopay`-`paycut`-`epf8`-`saladvance`-`loanvalue`-`paye`-`deduct1`-`deduct2`-`deduct3`-`deduct4`-`deduct5`)  <> 0 ";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, salaryMonth);
        ps.setString(2, comCode);
        ResultSet rs = ps.executeQuery();
        System.out.println("Invalid Net Salary EmpIDS Query " + ps.toString());
        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                empIDs.append(rs.getString("EID") + ",");
            }
        }
        ps.close();
        if (empIDs.toString().endsWith(",")) {
            empIDs = empIDs.deleteCharAt(empIDs.length() - 1);
        }
        return empIDs.toString();
    }

    //Monthly Tax Deductions from Regular Profits from Employment 
    //Reference to TAX TABLE NO 01
    public BigDecimal calculatePayeForRegularProfit(BigDecimal grossSalary) {
        BigDecimal payeAmount = BigDecimal.ZERO;
        BigDecimal taxPercentage = BigDecimal.ZERO;
        BigDecimal lessValue = BigDecimal.ZERO;

        if (grossSalary.doubleValue() < 10000) {
            taxPercentage = BigDecimal.ZERO;
            lessValue = BigDecimal.ZERO;
        } else if (grossSalary.doubleValue() > 10000 && grossSalary.doubleValue() <= 141667) {
            taxPercentage = new BigDecimal(0.06);
            lessValue = new BigDecimal(6000);
        } else if (grossSalary.doubleValue() > 141667 && grossSalary.doubleValue() <= 183333) {
            taxPercentage = new BigDecimal(0.12);
            lessValue = new BigDecimal(14500);
        } else if (grossSalary.doubleValue() > 183333 && grossSalary.doubleValue() <= 225000) {
            taxPercentage = new BigDecimal(0.18);
            lessValue = new BigDecimal(25500);
        } else if (grossSalary.doubleValue() > 225000 && grossSalary.doubleValue() <= 266667) {
            taxPercentage = new BigDecimal(0.24);
            lessValue = new BigDecimal(39000);
        } else if (grossSalary.doubleValue() > 266667 && grossSalary.doubleValue() <= 308333) {
            taxPercentage = new BigDecimal(0.30);
            lessValue = new BigDecimal(55000);
        } else if (grossSalary.doubleValue() > 308333) {
            taxPercentage = new BigDecimal(0.36);
            lessValue = new BigDecimal(73500);
        }

        //payeAmount = (grossSalary * taxPercentage)- lessValue;
        payeAmount = ((grossSalary.multiply(taxPercentage)).subtract(lessValue, MathContext.DECIMAL128));

        return payeAmount;
    }

    //Rates for the Deduction of Tax from Lump-sum Payments - Annual Leave Payment/Bonus (EGAR)
    //For the financial year
    //Reference to TAX TABLE NO 02
    public BigDecimal calculatePayeForBonus(String salaryMonth, BigDecimal grossSalary, BigDecimal bonusValue) {
        BigDecimal previousBonusValue = BigDecimal.ZERO;
        BigDecimal previousTaxForBonus = BigDecimal.ZERO;

        int paidMonths = 0;//no of months from April,2023 to bonus paid month(including)
        BigDecimal paidMonthlyGain = BigDecimal.ZERO;//A = [grossSalary * paidMonths]
        paidMonths = getFYMonthId(connection, salaryMonth);
        paidMonthlyGain = grossSalary.multiply(new BigDecimal(paidMonths));

        int payingMonths = 0;//no of months from bonus paid month(excluding) to March, 2024
        BigDecimal payingMonthlyGain = BigDecimal.ZERO;//B = [grossSalary  * payingMonths]
        payingMonths = 12 - paidMonths;
        payingMonthlyGain = grossSalary.multiply(new BigDecimal(payingMonths));

        BigDecimal totalBonus = BigDecimal.ZERO;//bonus value received + bonus value  to be received
        BigDecimal totalValueForTax = BigDecimal.ZERO;//D = A + B + C

        if (previousBonusValue.compareTo(BigDecimal.ZERO) == 1) {
            totalBonus = previousBonusValue.add(bonusValue);
            totalValueForTax = paidMonthlyGain.add(payingMonthlyGain).add(totalBonus);
        } else {
            totalBonus = bonusValue;
            totalValueForTax = paidMonthlyGain.add(payingMonthlyGain).add(totalBonus);
        }

        BigDecimal monthlyTaxValue = BigDecimal.ZERO;//Tax calculation reference to the tax table 1 
        monthlyTaxValue = calculatePayeForRegularProfit(grossSalary);

        BigDecimal taxValueForPaid = BigDecimal.ZERO;//Tax for A
        taxValueForPaid = monthlyTaxValue.multiply(new BigDecimal(paidMonths));

        BigDecimal taxValueForPaying = BigDecimal.ZERO;//Tax for B
        taxValueForPaying = monthlyTaxValue.multiply(new BigDecimal(payingMonths));

        BigDecimal taxPercentage = BigDecimal.ZERO;
        BigDecimal lessValue = BigDecimal.ZERO;

        if (totalValueForTax.doubleValue() < 1200000) {
            taxPercentage = BigDecimal.ZERO;
            lessValue = BigDecimal.ZERO;
        } else if (totalValueForTax.doubleValue() >= 1200001 && totalValueForTax.doubleValue() <= 1700000) {
            taxPercentage = new BigDecimal(0.06);
            lessValue = new BigDecimal(72000);
        } else if (totalValueForTax.doubleValue() >= 1700001 && totalValueForTax.doubleValue() <= 2200000) {
            taxPercentage = new BigDecimal(0.12);
            lessValue = new BigDecimal(174000);
        } else if (totalValueForTax.doubleValue() >= 2200001 && totalValueForTax.doubleValue() <= 2700000) {
            taxPercentage = new BigDecimal(0.18);
            lessValue = new BigDecimal(306000);
        } else if (totalValueForTax.doubleValue() >= 2700001 && totalValueForTax.doubleValue() <= 3200000) {
            taxPercentage = new BigDecimal(0.24);
            lessValue = new BigDecimal(468000);
        } else if (totalValueForTax.doubleValue() >= 3200001 && totalValueForTax.doubleValue() <= 3700000) {
            taxPercentage = new BigDecimal(0.30);
            lessValue = new BigDecimal(660000);
        } else if (totalValueForTax.doubleValue() > 3700000) {
            taxPercentage = new BigDecimal(0.36);
            lessValue = new BigDecimal(882000);
        }

        BigDecimal payeAmount = BigDecimal.ZERO;
        BigDecimal totalLess = BigDecimal.ZERO;
        totalLess = lessValue.add(taxValueForPaid).add(taxValueForPaying).add(previousTaxForBonus);
        //payeAmount = (D * taxPercentage)- totalLess;
        payeAmount = (totalValueForTax.multiply(taxPercentage)).subtract(totalLess);

        return payeAmount;
    }

    //Deduction of Tax on Cumulative Gains and Profits from Employment(resign/joining/promotion)
    //Applicable for monthly regular gains and profits from employment is less than Rs. 100,000/-)
    //Reference to TAX TABLE NO 05
    public BigDecimal calculatePayeForCumalativeGains(Connection connection, String salaryMonth, BigDecimal grossSalary, BigDecimal bonus, boolean salaryChange) {
        BigDecimal payeAmount = BigDecimal.ZERO;

        int paidMonths = 0;//no of months from finance year start to salary paid month
        BigDecimal cumalativeGain = BigDecimal.ZERO;//Sum of existing salary with bonus from financial year start to last month
        BigDecimal taxForCumalativeGain = BigDecimal.ZERO;

        paidMonths = getFYMonthId(connection, salaryMonth);
        cumalativeGain = (grossSalary.multiply(new BigDecimal(paidMonths))).add(bonus);

        BigDecimal taxPercentage = BigDecimal.ZERO;
        BigDecimal lessValue = BigDecimal.ZERO;

        if (cumalativeGain.doubleValue() <= 1200000) {
            taxPercentage = BigDecimal.ZERO;
            lessValue = BigDecimal.ZERO;
        } else if (cumalativeGain.doubleValue() > 1200000 && cumalativeGain.doubleValue() <= 1700000) {
            taxPercentage = new BigDecimal(0.06);
            lessValue = new BigDecimal(72000);
        } else if (cumalativeGain.doubleValue() > 1700000 && cumalativeGain.doubleValue() <= 2200000) {
            taxPercentage = new BigDecimal(0.12);
            lessValue = new BigDecimal(174000);
        } else if (cumalativeGain.doubleValue() > 2200000 && cumalativeGain.doubleValue() <= 2700000) {
            taxPercentage = new BigDecimal(0.18);
            lessValue = new BigDecimal(306000);
        } else if (cumalativeGain.doubleValue() > 2700000 && cumalativeGain.doubleValue() <= 3200000) {
            taxPercentage = new BigDecimal(0.24);
            lessValue = new BigDecimal(468000);
        } else if (cumalativeGain.doubleValue() > 3200000 && cumalativeGain.doubleValue() <= 3700000) {
            taxPercentage = new BigDecimal(0.30);
            lessValue = new BigDecimal(660000);
        } else if (cumalativeGain.doubleValue() > 3700000) {
            taxPercentage = new BigDecimal(0.36);
            lessValue = new BigDecimal(882000);
        }

        taxForCumalativeGain = (cumalativeGain.multiply(taxPercentage)).subtract(lessValue);

//        if (salaryChange) {
//            
// payeAmount = taxForCumalativeGain;
//        } else {
        payeAmount = taxForCumalativeGain;
//        }

        return payeAmount;
    }

    public int getFYMonthId(Connection connection, String salaryMonth) {
        int monthId = 0;
        try {
            System.out.println("salaryMonth for FYMonthId" + salaryMonth);
            String[] monthArray = salaryMonth.split("-");
            String month = monthArray[0].trim();

            String qery = "SELECT `month_id` FROM `fyear_months` WHERE `month_name`=?";
            PreparedStatement ps = connection.prepareStatement(qery);
            ps.setString(1, month);
            ResultSet rs = ps.executeQuery();
            if (rs.isBeforeFirst()) {
                rs.first();
                monthId = rs.getInt("month_id");
            }

            rs.close();
            ps.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return monthId;
    }
}
