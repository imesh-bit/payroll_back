/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daoImpls;

import dao.BeanBank;
import dao.BeanSystemLog;
import interfaces.InterfaceBank;
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
public class ImplBank implements InterfaceBank {

    @Override
    public List<BeanBank> getBankList(Connection connection) throws Exception {
        List<BeanBank> bankList = new ArrayList();
        /*String query = "SELECT `id`,`bankcode`,`bankname`,`branchcode`,`branchname` FROM `bank`;";

        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                BeanBank b = new BeanBank();
                b.setId(rs.getInt("id"));
                b.setBankcode(rs.getString("bankcode"));
                b.setBankname(rs.getString("bankname"));
                b.setBranchcode(rs.getString("branchcode"));
                b.setBranchname(rs.getString("branchname"));
                bankList.add(b);
            }
        }
        ps.close();*/
        String query = "SELECT `id`,`bankName`,`bankSwiftCode` FROM `bankdata`;";

        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                BeanBank b = new BeanBank();
                b.setId(rs.getInt("id"));
                b.setBankcode(rs.getString("bankSwiftCode"));
                b.setBankname(rs.getString("bankName"));
                b.setBranchcode("NA");
                b.setBranchname("NA");
                bankList.add(b);
            }
        }
        ps.close();
        return bankList;
    }

    @Override
    public String saveBank(BeanBank b) throws SQLException, Exception {
        Connection connection = JDBC.con_payroll();
        connection.setAutoCommit(false);

        try {
            String sql_allowance = "INSERT INTO `bankdata`(`bankName`,`bankSwiftCode`) VALUES(?,?);";
            PreparedStatement ps_allow = connection.prepareStatement(sql_allowance);
            ps_allow.setString(1, b.getBankname());
            ps_allow.setString(2, b.getBankcode());
            ps_allow.execute();
            ps_allow.close();

            String saveQuery = "INSERT INTO `log`(`event`,`description`,`comcode`,`user`) VALUES (?,?,?,?);";
            PreparedStatement ps = connection.prepareStatement(saveQuery);
            ps.setString(1, "Save");
            ps.setString(2, "Bank " + b.getId());
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
    public String updateBank(BeanBank b) throws SQLException, Exception {
        Connection connection = JDBC.con_payroll();
        connection.setAutoCommit(false);

        try {
            String sql_allowance = "UPDATE `bankdata` SET `bankName`=?,`bankSwiftCode`=?\n"
                    + "WHERE `id` = ?;";
            PreparedStatement ps_allow = connection.prepareStatement(sql_allowance);
            ps_allow.setString(1, b.getBankname());
            ps_allow.setString(2, b.getBankcode());
            ps_allow.setInt(3, b.getId());
            ps_allow.execute();
            ps_allow.close();

            String saveQuery = "INSERT INTO `log`(`event`,`description`,`comcode`,`user`) VALUES (?,?,?,?);";
            PreparedStatement ps = connection.prepareStatement(saveQuery);
            ps.setString(1, "Update");
            ps.setString(2, "Bank " + b.getId());
            ps.setString(3, BeanSystemLog.getComcode());
            ps.setString(4, BeanSystemLog.getUser());
            ps.execute();
            ps.close();

            connection.commit();
            connection.setAutoCommit(true);

            return "Details sucessfully updated.";
        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
            return "Error:" + e.getLocalizedMessage();
        } finally {
            connection.close();
        }

    }

    @Override
    public String deleteBank(int bankId, String bank) throws SQLException, Exception {
        Connection connection = JDBC.con_payroll();
        connection.setAutoCommit(false);
        try {
            String query1 = "SELECT COUNT(*) FROM `employee_bank` WHERE `bankid1`=?;";
            PreparedStatement ps1 = connection.prepareStatement(query1);
            ps1.setInt(1, bankId);
            ResultSet rs1 = ps1.executeQuery();

            if (rs1.isBeforeFirst()) {
                rs1.first();
                if (rs1.getInt(1) > 0) {
                    return "Error:This bank already used to update employee details";
                }
            }
            ps1.close();

            String query2 = "SELECT COUNT(*) FROM `employee_bank` WHERE `bankid2`=?;";
            PreparedStatement ps2 = connection.prepareStatement(query2);
            ps2.setInt(1, bankId);
            ResultSet rs2 = ps2.executeQuery();

            if (rs2.isBeforeFirst()) {
                rs2.first();
                if (rs2.getInt(1) > 0) {
                    return "Error:This bank already used to update employee details";
                }
            }
            ps2.close();

            String sql_attendance = "DELETE FROM `bankdata` WHERE `id`=?;";
            PreparedStatement ps_attendance = connection.prepareStatement(sql_attendance);
            ps_attendance.setInt(1,bankId);
            ps_attendance.executeUpdate();
            ps_attendance.close();

            String saveQuery = "INSERT INTO `log`(`event`,`description`,`comcode`,`user`) VALUES (?,?,?,?);";
            PreparedStatement ps_log = connection.prepareStatement(saveQuery);
            ps_log.setString(1, "Delete Bank");
            ps_log.setString(2, "Bank: " + bank+ " Id:" + bankId);
            ps_log.setString(3, BeanSystemLog.getComcode());
            ps_log.setString(4, BeanSystemLog.getUser());
            ps_log.execute();
            ps_log.close();

            connection.commit();
            connection.setAutoCommit(true);

            return "Record sucessfully deleted.";
        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
            return "Error:" + e.getLocalizedMessage();
        } finally {
            connection.close();
        }
    }

    @Override
    public int getBankId(Connection connection,String swiftCode) throws Exception {
      int bankId = 0;
        String query = "SELECT `id` FROM `bankdata` WHERE `bankSwiftCode`=?;";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, swiftCode.trim());
        ResultSet rs = ps.executeQuery();

        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                bankId = rs.getInt("id");
            }
        }
        ps.close();
        return bankId;
    }

}
