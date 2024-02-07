/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daoImpls;

import dao.BeanLoan;
import dao.BeanLoanSettlement;
import dao.BeanSystemLog;
import interfaces.InterfaceLoans;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import util.JDBC;

/**
 *
 * @author Nimesha
 */
public class ImplLoans implements InterfaceLoans {

    @Override
    public List<BeanLoan> getLoanDetailList(Connection connection, String comCode, int loanStatus) throws Exception {
        List<BeanLoan> list = new ArrayList<>();
        int loanid = 0;
        BigDecimal loanValue = BigDecimal.ZERO;
        BigDecimal outstanding = BigDecimal.ZERO;
        BeanLoan loan = null;

        String query = "SELECT \n"
                + "  `loanid`,\n"
                + "  `loanvalue`,\n"
                + "  `monthlydeduction`,\n"
                + "  loan.`empid` AS empid,\n"
                + "  `typeofloan`,\n"
                + "  `loandate`,\n"
                + "  employee_details.`empinitialname`,\n"
                + "  employee_details.`empcode`,\n"
                + "  employee_details.`designation`,\n"
                + "  `noofinstallments` \n"
                + "FROM\n"
                + "  `employee_details` employee_details \n"
                + "  RIGHT \n"
                + "  OUTER JOIN `loan` loan \n"
                + "    ON employee_details.`empid` = loan.`EmpId` \n"
                + "WHERE `setoff` = ? AND employee_details.`comcode`=? \n"
                + "ORDER BY `loandate` DESC ";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, loanStatus);
        ps.setString(2, BeanSystemLog.getComcode());
        ResultSet result = ps.executeQuery();
        //System.out.println("loan query "+query);
        if (result.isBeforeFirst()) {
            while (result.next()) {
                loanid = result.getInt("loanid");
                loanValue = result.getBigDecimal("loanvalue");

                loan = new BeanLoan();
                loan.setLoanid(result.getInt("loanid"));
                loan.setEmpid(result.getInt("empid"));
                loan.setLoanvalue(result.getBigDecimal("loanvalue"));
                loan.setTypeofloan(result.getString("typeofloan"));
                loan.setNoofinstallments(result.getInt("noofinstallments"));
                loan.setMonthlydeduction(result.getBigDecimal("monthlydeduction"));
                loan.setEmpcode(result.getInt("empcode"));
                loan.setEmpname(result.getString("empinitialname"));
                loan.setDesignation(result.getString("designation"));
                loan.setConsolidatedSalary(getConsolidatedSalry(connection, result.getInt("empid"), BeanSystemLog.getComcode()));

                PreparedStatement pss = connection.prepareStatement("SELECT IFNULL(SUM(`monthlyamount`),0.00) AS paid FROM `loansettlement` WHERE `loanid`='" + loanid + "'");
                ResultSet rs = pss.executeQuery();
                while (rs.next()) {
                    outstanding = loanValue.subtract(rs.getBigDecimal("paid"));
                    loan.setOutstanding(outstanding);
                }
                pss.close();
                rs.close();

                list.add(loan);
            }
        }
        ps.close();
        result.close();

        return list;
    }

    @Override
    public List<BeanLoanSettlement> getSettlementList(Connection connection, int loanId) throws Exception {
        List<BeanLoanSettlement> list = new ArrayList<>();
        String query = "SELECT `monthcode`,`monthlyamount`,`paymode` FROM `loansettlement` WHERE `loanid`=? ORDER BY sysdatetime";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, loanId);
        ResultSet result = ps.executeQuery();
        if (result.isBeforeFirst()) {
            while (result.next()) {
                BeanLoanSettlement bls = new BeanLoanSettlement();
                bls.setMonthcode(result.getString("monthcode"));
                bls.setMonthlyAmount(result.getBigDecimal("monthlyamount"));
                bls.setPayMode(result.getString("paymode"));
                list.add(bls);
            }
        }
        ps.close();
        result.close();

        return list;
    }

    @Override
    public BeanLoan getSelectedLoanData(Connection connection, int loanId) throws Exception {
        BeanLoan loan = null;
        String query = "SELECT `loandate`,`loanvalue`,`reason`,`monthlydeduction`,`deductfrom`,`noofinstallments`,`garanteeid1`,`garanteeid2`,\n"
                + "`setoff`,`typeofloan`,`empid`,`settlementdate`,`settlementamount`,`settlementnote` FROM `loan` WHERE `loanid`=?";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, loanId);
        ResultSet result = ps.executeQuery();
        if (result.isBeforeFirst()) {
            while (result.next()) {
                loan = new BeanLoan();
                loan.setLoandate(result.getDate("loandate"));
                loan.setLoanvalue(result.getBigDecimal("loanvalue"));
                loan.setReason(result.getString("reason"));
                loan.setMonthlydeduction(result.getBigDecimal("monthlydeduction"));
                loan.setDeductfrom(result.getString("deductfrom"));
                loan.setNoofinstallments(result.getInt("noofinstallments"));
                loan.setGaranteeid1(result.getInt("garanteeid1"));
                loan.setGaranteeid2(result.getInt("garanteeid2"));
                loan.setSetoff(result.getBoolean("setoff"));
                loan.setTypeofloan(result.getString("typeofloan"));
                loan.setEmpid(result.getInt("empid"));
                loan.setSettlementdate(result.getDate("settlementdate"));
                loan.setSettlementamount(result.getBigDecimal("settlementamount"));
                loan.setSettlementnote(result.getString("settlementnote"));
                loan.setConsolidatedSalary(getConsolidatedSalry(connection, result.getInt("empid"), BeanSystemLog.getComcode()));
               // loan.setSettlementList(getSettlementList(connection, loanId));
            }
        }
        ps.close();
        result.close();

        BigDecimal loanValue = BigDecimal.ZERO;
        BigDecimal outstanding = BigDecimal.ZERO;
        ps = connection.prepareStatement("SELECT IFNULL(SUM(`monthlyamount`),0) AS paid FROM `loansettlement` WHERE `loanid`='" + loanId + "'");
        result = ps.executeQuery();
        while (result.next()) {
            outstanding = loanValue.subtract(result.getBigDecimal("paid"));
            loan.setOutstanding(outstanding);
        }
        ps.close();
        result.close();

        return loan;
    }

    @Override
    public String saveLoanData(BeanLoan b) throws SQLException, Exception {
        Connection connection = JDBC.con_payroll();
        connection.setAutoCommit(false);
        int loanid = 0;
        try {
            String sql_loan = "SELECT COUNT(*) FROM `loan` WHERE `empid`=? AND `comcode`=? AND `setoff`=0";
            PreparedStatement pss = connection.prepareStatement(sql_loan);
            pss.setInt(1, b.getEmpid());
            pss.setString(2, BeanSystemLog.getComcode());
            ResultSet result = pss.executeQuery();
            while (result.next()) {
                if (result.getInt(1) > 0) {
                    return "Error: This employee alresdy has not settled loan";
                }
            }
            pss.close();
            result.close();

            String sql_attendance = "INSERT INTO `loan`(`loandate`,`loanvalue`,`reason`,`monthlydeduction`,`deductfrom`,"
                    + "`noofinstallments`,`garanteeid1`,`garanteeid2`,`setoff`,`empid`,`typeofloan`,`comcode`,`user`)\n"
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?);";
            PreparedStatement ps_attendance = connection.prepareStatement(sql_attendance, Statement.RETURN_GENERATED_KEYS);
            ps_attendance.setDate(1, new java.sql.Date(b.getLoandate().getTime()));
            ps_attendance.setBigDecimal(2, b.getLoanvalue());
            ps_attendance.setString(3, b.getReason());
            ps_attendance.setBigDecimal(4, b.getMonthlydeduction());
            ps_attendance.setString(5, b.getDeductfrom());
            ps_attendance.setInt(6, b.getNoofinstallments());
            ps_attendance.setInt(7, b.getGaranteeid1());
            ps_attendance.setInt(8, b.getGaranteeid2());
            ps_attendance.setBoolean(9, false);
            ps_attendance.setInt(10, b.getEmpid());
            ps_attendance.setString(11, b.getTypeofloan());
            ps_attendance.setString(12, BeanSystemLog.getComcode());
            ps_attendance.setString(13, BeanSystemLog.getUser());
            ps_attendance.executeUpdate();

            ResultSet keys = ps_attendance.getGeneratedKeys();
            keys.next();
            loanid = keys.getInt(1);

            b.setLoanid(loanid);

            ps_attendance.close();

            String saveQuery = "INSERT INTO `log`(`event`,`description`,`comcode`,`user`) VALUES (?,?,?,?);";
            PreparedStatement ps = connection.prepareStatement(saveQuery);
            ps.setString(1, "Save Loan");
            ps.setString(2, "Loan ID: " + b.getLoanid() + " EmpId:" + b.getEmpid());
            ps.setString(3, BeanSystemLog.getComcode());
            ps.setString(4, BeanSystemLog.getUser());
            ps.execute();
            ps.close();

            connection.commit();
            connection.setAutoCommit(true);

            return "Details sucessfully saved.";
        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
            return "Error:" + e.getLocalizedMessage();
        } finally {
            connection.close();
        }
    }

    @Override
    public String updateLoanData(BeanLoan b) throws SQLException, Exception {
        Connection connection = JDBC.con_payroll();
        connection.setAutoCommit(false);

        try {
            String sql_loan = "SELECT COUNT(*) FROM `loansettlement` WHERE `loanid`=?";
            PreparedStatement pss = connection.prepareStatement(sql_loan);
            pss.setInt(1, b.getLoanid());
            ResultSet result = pss.executeQuery();
            while (result.next()) {
                if (result.getInt(1) > 0) {
                    return "Error: This loan cannot be edited due to it has alredy paid installements.";
                }
            }
            pss.close();
            result.close();

            String sql_attendance = "UPDATE `loan` SET `loandate`=?,`loanvalue`=?,`reason`=?,`monthlydeduction`=?,`deductfrom`=?,"
                    + "`noofinstallments`=?,`garanteeid1`=?,`garanteeid2`=?,`typeofloan`=?,`user`=? WHERE `loanid`=?;";
            PreparedStatement ps_attendance = connection.prepareStatement(sql_attendance);
            ps_attendance.setDate(1, new java.sql.Date(b.getLoandate().getTime()));
            ps_attendance.setBigDecimal(2, b.getLoanvalue());
            ps_attendance.setString(3, b.getReason());
            ps_attendance.setBigDecimal(4, b.getMonthlydeduction());
            ps_attendance.setString(5, b.getDeductfrom());
            ps_attendance.setInt(6, b.getNoofinstallments());
            ps_attendance.setInt(7, b.getGaranteeid1());
            ps_attendance.setInt(8, b.getGaranteeid2());
            ps_attendance.setString(9, b.getTypeofloan());
            ps_attendance.setString(10, BeanSystemLog.getUser());
            ps_attendance.setInt(11, b.getLoanid());
            ps_attendance.executeUpdate();
            ps_attendance.close();

            String saveQuery = "INSERT INTO `log`(`event`,`description`,`comcode`,`user`) VALUES (?,?,?,?);";
            PreparedStatement ps = connection.prepareStatement(saveQuery);
            ps.setString(1, "Update Loan");
            ps.setString(2, "Loan ID: " + b.getLoanid() + " EmpId:" + b.getEmpid());
            ps.setString(3, BeanSystemLog.getComcode());
            ps.setString(4, BeanSystemLog.getUser());
            ps.execute();
            ps.close();

            connection.commit();
            connection.setAutoCommit(true);

            return "Details sucessfully saved.";
        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
            return "Error:" + e.getLocalizedMessage();
        } finally {
            connection.close();
        }
    }

    @Override
    public String settleLoan(BeanLoan b) throws SQLException, Exception {
        Connection connection = JDBC.con_payroll();
        connection.setAutoCommit(false);

        try {

            String sql_attendance = "UPDATE `loan` SET `setoff`=?,`settlementdate`=?,`settlementamount`=?,`settlementnote`=? WHERE `loanid`=?;";
            PreparedStatement ps_attendance = connection.prepareStatement(sql_attendance);
            ps_attendance.setBoolean(1, true);
            ps_attendance.setDate(2, new java.sql.Date(b.getSettlementdate().getTime()));
            ps_attendance.setBigDecimal(3, b.getSettlementamount());
            ps_attendance.setString(4, b.getSettlementnote());
            ps_attendance.setInt(5, b.getLoanid());
            ps_attendance.executeUpdate();
            ps_attendance.close();

            String sql_loan = "INSERT INTO `loansettlement`(`loanid`,`monthcode`,`empid`,`monthlyamount`,`paymode`,`active`,`salarytype`)\n"
                    + "VALUES (?,?,?,?,?,?,?);";
            PreparedStatement psLoan = connection.prepareStatement(sql_loan);
            psLoan.setInt(1, b.getLoanid());
            psLoan.setString(2, "");
            psLoan.setInt(3, b.getEmpid());
            psLoan.setBigDecimal(4, b.getSettlementamount());
            psLoan.setString(5, "By Loan Settlement");
            psLoan.setInt(6, 0);
            psLoan.setString(7, "Normal");
            psLoan.executeUpdate();
            psLoan.close();

            String saveQuery = "INSERT INTO `log`(`event`,`description`,`comcode`,`user`) VALUES (?,?,?,?);";
            PreparedStatement ps = connection.prepareStatement(saveQuery);
            ps.setString(1, "Setoff Loan");
            ps.setString(2, "Loan ID: " + b.getLoanid() + " EmpId:" + b.getEmpid());
            ps.setString(3, BeanSystemLog.getComcode());
            ps.setString(4, BeanSystemLog.getUser());
            ps.execute();
            ps.close();

            connection.commit();
            connection.setAutoCommit(true);

            return "Loan sucessfully setoff";
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
            return "Error:" + e.getLocalizedMessage();
        } finally {
            connection.close();
        }
    }

    public BigDecimal getConsolidatedSalry(Connection connection, int empId, String comCode) throws Exception {
        BigDecimal salary = BigDecimal.ZERO;
        String query = "SELECT (`basicsalary`+`budgetallowance`+`bra2`) AS Salary \n"
                + "FROM `employee_details` WHERE `empid`=? AND `comcode`=?";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, empId);
        ps.setString(2, comCode);
        ResultSet rs = ps.executeQuery();

        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                salary = rs.getBigDecimal("Salary");
            }
        }
        ps.close();
        return salary;
    }

    @Override
    public List<BeanLoan> getLoanOutstandingList(Connection connection, String company, int empId) throws Exception {
        List<BeanLoan> list = new ArrayList<>();
        int loanid = 0;
        BigDecimal loanValue = BigDecimal.ZERO;
        BigDecimal outstanding = BigDecimal.ZERO;
        BeanLoan loan = null;

        String query = "SELECT \n"
                + "  `loanid`,\n"
                + "  `loanvalue`,\n"
                + "  `monthlydeduction`,\n"
                + "  loan.`empid` AS empid,\n"
                + "  `typeofloan`,\n"
                + "  `loandate`,\n"
                + "  employee_details.`empinitialname`,\n"
                + "  employee_details.`empcode`,\n"
                + "  employee_details.`designation`,\n"
                + "  `noofinstallments` \n"
                + "FROM\n"
                + "  `employee_details` employee_details \n"
                + "  RIGHT \n"
                + "  OUTER JOIN `loan` loan \n"
                + "    ON employee_details.`empid` = loan.`EmpId` \n"
                + "WHERE `setoff` = ? AND  loan.`EmpId`=? \n"
                + "ORDER BY `loandate` DESC ";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, 0);
        ps.setInt(2, empId);
        ResultSet result = ps.executeQuery();
        //System.out.println("loan query "+query);
        if (result.isBeforeFirst()) {
            while (result.next()) {
                loanid = result.getInt("loanid");
                loanValue = result.getBigDecimal("loanvalue");

                loan = new BeanLoan();
                loan.setLoanid(result.getInt("loanid"));
                loan.setEmpid(result.getInt("empid"));
                loan.setLoanvalue(result.getBigDecimal("loanvalue"));
                loan.setTypeofloan(result.getString("typeofloan"));
                loan.setNoofinstallments(result.getInt("noofinstallments"));
                loan.setMonthlydeduction(result.getBigDecimal("monthlydeduction"));
                loan.setEmpcode(result.getInt("empcode"));
                loan.setEmpname(result.getString("empinitialname"));
                loan.setDesignation(result.getString("designation"));
                loan.setConsolidatedSalary(getConsolidatedSalry(connection, result.getInt("empid"), BeanSystemLog.getComcode()));

                PreparedStatement pss = connection.prepareStatement("SELECT IFNULL(SUM(`monthlyamount`),0.00) AS paid FROM `loansettlement` WHERE `loanid`='" + loanid + "'");
                ResultSet rs = pss.executeQuery();
                while (rs.next()) {
                    outstanding = loanValue.subtract(rs.getBigDecimal("paid"));
                    loan.setOutstanding(outstanding);
                }
                pss.close();
                rs.close();

                list.add(loan);
            }
        }
        ps.close();
        result.close();

        return list;
    }
}
