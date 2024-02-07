/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daoImpls;

import dao.BeanSalaryMonth;
import dao.BeanSystemLog;
import interfaces.InterfaceSalaryMonth;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.JDBC;

/**
 *
 * @author Nimesha
 */
public class ImplSalaryMonth implements InterfaceSalaryMonth {

    @Override
    public String getActiveSalaryMonth(Connection connection) throws Exception {
        String SalaryMonth = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT `monthcode` FROM `month` WHERE `runpayroll`=0 AND `comcode`='" + BeanSystemLog.getComcode() + "'";
            pstmt = connection.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                SalaryMonth = rs.getString("monthcode");
            }

            pstmt.close();
            rs.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            pstmt.close();
            rs.close();
        }
        return SalaryMonth;
    }

    @Override
    public List<BeanSalaryMonth> getMonthList(Connection connection) throws Exception {
        List<BeanSalaryMonth> monthList = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT `monthcode`,`year`,`monthid`,`monthname`,`runpayroll`,`stopattendance`,`stopallowance`,`stopadvance` "
                    + "FROM `month` WHERE  `comcode`='" + BeanSystemLog.getComcode() + "'";
            pstmt = connection.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                BeanSalaryMonth bsm = new BeanSalaryMonth();
                bsm.setMonthcode(rs.getString("monthcode"));
                bsm.setMonthid(rs.getInt("monthid"));
                bsm.setMonthname(rs.getString("monthname"));
                bsm.setYear(rs.getInt("year"));
                bsm.setRunpayroll(rs.getBoolean("runpayroll"));
                bsm.setStopattendance(rs.getBoolean("stopattendance"));
                bsm.setStopallowance(rs.getBoolean("stopallowance"));
                bsm.setStopadvance(rs.getBoolean("stopadvance"));
                monthList.add(bsm);
            }

            pstmt.close();
            rs.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            pstmt.close();
            rs.close();
        }

        return monthList;
    }

    @Override
    public String saveMonthDetails(BeanSalaryMonth b) throws SQLException, Exception {
        Connection connection = JDBC.con_payroll();
        connection.setAutoCommit(false);

        try {
            //b.setMonthcode(b.getMonthname() + "-" + b.getYear());

            String sql_allowance = "INSERT INTO `month`(`monthcode`,`year`,`monthid`,`monthname`,`dat`,`runpayroll`,`stopattendance`,`stopallowance`,`stopadvance`,`comcode`,`user`)\n"
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?);";
            PreparedStatement ps_allow = connection.prepareStatement(sql_allowance);
            ps_allow.setString(1, b.getMonthcode());
            ps_allow.setInt(2, b.getYear());
            ps_allow.setInt(3, b.getMonthid());
            ps_allow.setString(4, b.getMonthname());
            ps_allow.setDate(5, new java.sql.Date(b.getCreateDate().getTime()));
            ps_allow.setBoolean(6, b.isRunpayroll());
            ps_allow.setBoolean(7, b.isStopattendance());
            ps_allow.setBoolean(8, b.isStopallowance());
            ps_allow.setBoolean(9, b.isStopadvance());
            ps_allow.setString(10, BeanSystemLog.getComcode());
            ps_allow.setString(11, BeanSystemLog.getUser());
            ps_allow.execute();
            ps_allow.close();

            String saveQuery = "INSERT INTO `log`(`event`,`description`,`comcode`,`user`) VALUES (?,?,?,?);";
            PreparedStatement ps = connection.prepareStatement(saveQuery);
            ps.setString(1, "Save");
            ps.setString(2, "SalaryMonth: " + b.getMonthcode());
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
    public String updateMonthDetails(BeanSalaryMonth b) throws SQLException, Exception {
        Connection connection = JDBC.con_payroll();
        connection.setAutoCommit(false);

        try {
            String sql_allowance = "UPDATE `month` SET `runpayroll`=?,`stopattendance`=?,`stopallowance`=?,`stopadvance`=?,`comcode`=?,`user`=? "
                    + "WHERE `monthcode`=?;";
            PreparedStatement ps_allow = connection.prepareStatement(sql_allowance);
            ps_allow.setBoolean(1, b.isRunpayroll());
            ps_allow.setBoolean(2, b.isStopattendance());
            ps_allow.setBoolean(3, b.isStopallowance());
            ps_allow.setBoolean(4, b.isStopadvance());
            ps_allow.setString(5, BeanSystemLog.getComcode());
            ps_allow.setString(6, BeanSystemLog.getUser());
            ps_allow.setString(7, b.getMonthcode());
            ps_allow.execute();
            ps_allow.close();

            String saveQuery = "INSERT INTO `log`(`event`,`description`,`comcode`,`user`) VALUES (?,?,?,?);";
            PreparedStatement ps = connection.prepareStatement(saveQuery);
            ps.setString(1, "Update");
            ps.setString(2, "SalaryMonth: " + b.getMonthcode());
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
    public BeanSalaryMonth getSelectedMonthStatus(Connection connection, String SalaryMonth) throws Exception {
        BeanSalaryMonth bsm = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT `monthcode`,`runpayroll`,`stopattendance`,`stopallowance`,`stopadvance` FROM `month` "
                    + "WHERE  `comcode`=? AND `monthcode`=?";
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, BeanSystemLog.getComcode());
            pstmt.setString(2, SalaryMonth);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                bsm = new BeanSalaryMonth();
                bsm.setMonthcode(rs.getString("monthcode"));
                bsm.setRunpayroll(rs.getBoolean("runpayroll"));
                bsm.setStopattendance(rs.getBoolean("stopattendance"));
                bsm.setStopallowance(rs.getBoolean("stopallowance"));
                bsm.setStopadvance(rs.getBoolean("stopadvance"));
            }

            pstmt.close();
            rs.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            pstmt.close();
            rs.close();
        }

        return bsm;
    }

    @Override
    public Boolean checkCreateMonth(Connection connection, String SalaryMonth) throws Exception {
        boolean createMonth = false;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT COUNT(*) FROM `month` WHERE `monthcode`=? AND `comcode`='" + BeanSystemLog.getComcode() + "'";
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, SalaryMonth);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (rs.getInt(1) > 0) {
                    createMonth = true;
                }
            }

            pstmt.close();
            rs.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            pstmt.close();
            rs.close();
        }
        return createMonth;
    }

}
