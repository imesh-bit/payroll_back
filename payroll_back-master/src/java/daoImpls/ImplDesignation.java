/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daoImpls;

import dao.BeanDesignation;
import dao.BeanSystemLog;
import interfaces.InterfaceDesignation;
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
public class ImplDesignation implements InterfaceDesignation {

    @Override
    public List<BeanDesignation> getDesignationList(Connection connection) throws Exception {
        List<BeanDesignation> list = new ArrayList<>();
        String query = "SELECT `id`,`designation`,`user` FROM `designation`;";

        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                BeanDesignation b = new BeanDesignation();
                b.setId(rs.getInt("id"));
                b.setDesignation(rs.getString("designation"));
                b.setUser(rs.getString("user"));
                list.add(b);
            }
        }
        ps.close();
        return list;
    }

    @Override
    public String saveDesignation(BeanDesignation b) throws SQLException, Exception {
        Connection connection = JDBC.con_payroll();
        connection.setAutoCommit(false);

        int designationid = 0;
        try {
            String sql_allowance = "INSERT INTO `designation`(`designation`,`user`)VALUES (?,?);";
            PreparedStatement ps_allow = connection.prepareStatement(sql_allowance, Statement.RETURN_GENERATED_KEYS);
            ps_allow.setString(1, b.getDesignation());
            ps_allow.setString(2, BeanSystemLog.getUser());
            ps_allow.executeUpdate();

            ResultSet keys = ps_allow.getGeneratedKeys();
            keys.next();
            designationid = keys.getInt(1);

            b.setId(designationid);

            ps_allow.close();

            String saveQuery = "INSERT INTO `log`(`event`,`description`,`comcode`,`user`) VALUES (?,?,?,?);";
            PreparedStatement ps = connection.prepareStatement(saveQuery);
            ps.setString(1, "Save");
            ps.setString(2, "Designation : " + b.getId());
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
    public String updateDesignation(BeanDesignation b) throws SQLException, Exception {
        Connection connection = JDBC.con_payroll();
        connection.setAutoCommit(false);

        try {
            String sql_allowance = "UPDATE `designation` SET `designation` = ?,`user` = ? WHERE `id` = ?;";
            PreparedStatement ps_allow = connection.prepareStatement(sql_allowance);
            ps_allow.setString(1, b.getDesignation());
            ps_allow.setString(2, BeanSystemLog.getUser());
            ps_allow.setInt(3, b.getId());
            ps_allow.executeUpdate();
            ps_allow.close();

            String saveQuery = "INSERT INTO `log`(`event`,`description`,`comcode`,`user`) VALUES (?,?,?,?);";
            PreparedStatement ps = connection.prepareStatement(saveQuery);
            ps.setString(1, "Update");
            ps.setString(2, "Designation : " + b.getId());
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
    public String deleteDesignation(int id,String designation) throws SQLException, Exception {
        Connection connection = JDBC.con_payroll();
        connection.setAutoCommit(false);
        try {
            String query = "SELECT COUNT(*) FROM `employee_details` WHERE `designation`=?;";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, designation);
            ResultSet rs = ps.executeQuery();

            if (rs.isBeforeFirst()) {
                rs.first();
                if (rs.getInt(1) > 0) {
                    return "Error:This designation already used to update employee details";
                }
            }
            ps.close();

            String sql_attendance = "DELETE FROM `designation` WHERE `id`=?;";
            PreparedStatement ps_attendance = connection.prepareStatement(sql_attendance);
            ps_attendance.setInt(1, id);
            ps_attendance.executeUpdate();
            ps_attendance.close();

            String saveQuery = "INSERT INTO `log`(`event`,`description`,`comcode`,`user`) VALUES (?,?,?,?);";
            PreparedStatement ps_log = connection.prepareStatement(saveQuery);
            ps_log.setString(1, "Delete Designation");
            ps_log.setString(2, "Designation: " + designation + " Id:" + id);
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
    public String validateDesignation(Connection connection,String designation) throws SQLException, Exception {
        String available="Not Found";
        String query = "SELECT `designation` FROM `designation` WHERE `designation`=?;";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, designation.trim());
        ResultSet rs = ps.executeQuery();

        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                available=rs.getString("designation");
            }
        }
        ps.close();
        return available;
    }

}
