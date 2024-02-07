/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daoImpls;

import dao.BeanSystemLog;
import dao.BeanUser;
import interfaces.InterfaceUser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import util.JDBC;

/**
 *
 * @author Nimesha
 */
public class ImplUser implements InterfaceUser {

    @Override
    public String saveUser(BeanUser b) throws SQLException, Exception {
        Connection connection = JDBC.con_payroll();
        connection.setAutoCommit(false);

        try {
            String sql = "select count(*) from `user` where `username`='" + b.getUsername() + "'";
            PreparedStatement ps_chk = connection.prepareStatement(sql);
            ResultSet rs = ps_chk.executeQuery();
            while (rs.next()) {
                if (rs.getInt(1) > 0) {
                    return "Error:This username already registered in the system";
                }
            }
            rs.close();
            ps_chk.close();

            String sql_allowance = "INSERT INTO `user`(`empid`,`username`,`password`,`fullname`,`comcode`,`loccode`,`regdate`,`usergroup`,"
                    + "`email`,`mobileno`,`status`,`dataentryuser`)\n"
                    + "VALUES (?,?,md5(?),?,?,?,?,?,?,?,?,?);";
            PreparedStatement ps_allow = connection.prepareStatement(sql_allowance);
            ps_allow.setInt(1, b.getEmpid());
            ps_allow.setString(2, b.getUsername());
            ps_allow.setString(3, b.getPassword());
            ps_allow.setString(4, b.getFullname());
            ps_allow.setString(5, BeanSystemLog.getComcode());
            ps_allow.setString(6, "NA");
            ps_allow.setDate(7, new java.sql.Date(new Date().getTime()));
            ps_allow.setString(8, b.getUsergroup());
            ps_allow.setString(9, b.getEmail());
            ps_allow.setString(10, b.getMobileno());
            ps_allow.setBoolean(11, b.isStatus());
            ps_allow.setString(12, BeanSystemLog.getUser());
            ps_allow.execute();
            ps_allow.close();

            String saveQuery = "INSERT INTO `log`(`event`,`description`,`comcode`,`user`) VALUES (?,?,?,?);";
            PreparedStatement ps = connection.prepareStatement(saveQuery);
            ps.setString(1, "Save");
            ps.setString(2, "User : " + b.getEmpid());
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
    public String updateUser(BeanUser b) throws SQLException, Exception {
        Connection connection = JDBC.con_payroll();
        connection.setAutoCommit(false);

        try {
            String sql_allowance = "UPDATE `user` SET `usergroup`=?,`email`=?,`mobileno`=?,`status`=?,`dataentryuser`=? WHERE `username`=?;";
            PreparedStatement ps_allow = connection.prepareStatement(sql_allowance);
            ps_allow.setString(1, b.getUsergroup());
            ps_allow.setString(2, b.getEmail());
            ps_allow.setString(3, b.getMobileno());
            ps_allow.setBoolean(4, b.isStatus());
            ps_allow.setString(5, BeanSystemLog.getUser());
            ps_allow.setString(6, b.getUsername());
            ps_allow.execute();
            ps_allow.close();

            String saveQuery = "INSERT INTO `log`(`event`,`description`,`comcode`,`user`) VALUES (?,?,?,?);";
            PreparedStatement ps = connection.prepareStatement(saveQuery);
            ps.setString(1, "Update");
            ps.setString(2, "User : " + b.getEmpid());
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
    public BeanUser getSelectedUser(Connection connection, int empid) throws Exception {
        String sql = "SELECT `empid`,`username`,`fullname`,`usergroup`,`email`,`mobileno`,`status` FROM `user` WHERE `empid`=?";

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, empid);
        ResultSet rs = ps.executeQuery();

        BeanUser b = new BeanUser();
        if (rs.isBeforeFirst()) {
            rs.first();
            b.setEmpid(rs.getInt("empid"));
            b.setUsername(rs.getString("username"));
            b.setFullname(rs.getString("fullname"));
            b.setUsergroup(rs.getString("usergroup"));
            b.setEmail(rs.getString("email"));
            b.setMobileno(rs.getString("mobileno"));
            b.setStatus(rs.getBoolean("status"));
        }
        ps.close();
        return b;
    }

    @Override
    public List<BeanUser> getUserList(Connection connection) throws Exception {
        List<BeanUser> list = new ArrayList<>();
        String query = "SELECT `empid`,`username`,`fullname`,`usergroup`,`email`,`mobileno`,`status` FROM `user` WHERE comcode=?;";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, BeanSystemLog.getComcode());
        ResultSet rs = ps.executeQuery();

        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                BeanUser b = new BeanUser();
                b.setEmpid(rs.getInt("empid"));
                b.setUsername(rs.getString("username"));
                b.setFullname(rs.getString("fullname"));
                b.setUsergroup(rs.getString("usergroup"));
                b.setEmail(rs.getString("email"));
                b.setMobileno(rs.getString("mobileno"));
                b.setStatus(rs.getBoolean("status"));
                list.add(b);
            }
        }
        ps.close();
        return list;
    }

    @Override
    public Boolean checkForUpdate(Connection connection, int empid) throws SQLException, Exception {

        try {
            String sql = "select count(*) from `user` where `empid`='" + empid + "'";
            PreparedStatement ps_chk = connection.prepareStatement(sql);
            ResultSet rs = ps_chk.executeQuery();
            while (rs.next()) {
                if (rs.getInt(1) > 0) {
                    return true;
                }
            }
            rs.close();
            ps_chk.close();
        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
            return false;
        }
        return false;
    }

}
