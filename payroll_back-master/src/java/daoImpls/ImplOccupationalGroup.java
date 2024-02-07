/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daoImpls;

import dao.BeanOccupationalGroup;
import dao.BeanSystemLog;
import interfaces.InterfaceOccupationalGroup;
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
public class ImplOccupationalGroup implements InterfaceOccupationalGroup {

    @Override
    public List<BeanOccupationalGroup> getOccupationalGroupList(Connection connection) throws Exception {
        List<BeanOccupationalGroup> list = new ArrayList<>();
        String query = "SELECT `groupid`,`groupname`,`user` FROM `occupational_group` ;";

        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                BeanOccupationalGroup b = new BeanOccupationalGroup();
                b.setGroupid(rs.getInt("groupid"));
                b.setGroupname(rs.getString("groupname"));
                b.setUser(rs.getString("user"));
                list.add(b);
            }
        }
        ps.close();
        return list;
    }

    @Override
    public String saveOccupationalGroup(BeanOccupationalGroup b) throws SQLException, Exception {
        Connection connection = JDBC.con_payroll();
        connection.setAutoCommit(false);

        int groupid = 0;
        try {
            String sql_allowance = "INSERT INTO `occupational_group`(`groupname`,`user`)VALUES (?,?);";
            PreparedStatement ps_allow = connection.prepareStatement(sql_allowance, Statement.RETURN_GENERATED_KEYS);
            ps_allow.setString(1, b.getGroupname());
            ps_allow.setString(2, BeanSystemLog.getUser());
            ps_allow.executeUpdate();

            ResultSet keys = ps_allow.getGeneratedKeys();
            keys.next();
            groupid = keys.getInt(1);

            b.setGroupid(groupid);

            ps_allow.close();

            String saveQuery = "INSERT INTO `log`(`event`,`description`,`comcode`,`user`) VALUES (?,?,?,?);";
            PreparedStatement ps = connection.prepareStatement(saveQuery);
            ps.setString(1, "Save");
            ps.setString(2, "Occupational Group : " + b.getGroupid());
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
    public String updateOccupationalGroup(BeanOccupationalGroup b) throws SQLException, Exception {
        Connection connection = JDBC.con_payroll();
        connection.setAutoCommit(false);

        try {
            String sql_allowance = "UPDATE `occupational_group` SET `groupname`=?,`user`=? WHERE `groupid`=?;";
            PreparedStatement ps_allow = connection.prepareStatement(sql_allowance);
            ps_allow.setString(1, b.getGroupname());
            ps_allow.setString(2, BeanSystemLog.getUser());
            ps_allow.setInt(3, b.getGroupid());
            ps_allow.execute();
            ps_allow.close();

            String saveQuery = "INSERT INTO `log`(`event`,`description`,`comcode`,`user`) VALUES (?,?,?,?);";
            PreparedStatement ps = connection.prepareStatement(saveQuery);
            ps.setString(1, "Update");
            ps.setString(2, "Occupational Group : " + b.getGroupid());
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
    public String deleteGroup(int groupId, String group) throws SQLException, Exception {
        Connection connection = JDBC.con_payroll();
        connection.setAutoCommit(false);
        try {
            String query = "SELECT COUNT(*) FROM `employee_details` WHERE `groupid`=?;";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, groupId);
            ResultSet rs = ps.executeQuery();

            if (rs.isBeforeFirst()) {
                rs.first();
                if (rs.getInt(1) > 0) {
                    return "Error:This occupational group already used to update employee details";
                }
            }
            ps.close();

            String sql_attendance = "DELETE FROM `occupational_group` WHERE `groupid`=?;";
            PreparedStatement ps_attendance = connection.prepareStatement(sql_attendance);
            ps_attendance.setInt(1, groupId);
            ps_attendance.executeUpdate();
            ps_attendance.close();

            String saveQuery = "INSERT INTO `log`(`event`,`description`,`comcode`,`user`) VALUES (?,?,?,?);";
            PreparedStatement ps_log = connection.prepareStatement(saveQuery);
            ps_log.setString(1, "Delete Occupational Group");
            ps_log.setString(2, "Occupational Group: " + group + " Id:" + groupId);
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
    public int getOccupationalId(Connection connection, String occupation) throws Exception {
        int groupId = 0;
        String query = "SELECT `groupid` FROM `occupational_group` WHERE `groupname`=?;";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, occupation.trim());
        ResultSet rs = ps.executeQuery();

        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                groupId = rs.getInt("groupid");
            }
        }
        ps.close();
        return groupId;
    }

}
