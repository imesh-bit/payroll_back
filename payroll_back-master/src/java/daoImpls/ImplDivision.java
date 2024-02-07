/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daoImpls;

import dao.BeanDivision;
import dao.BeanSystemLog;
import interfaces.InterfaceDivision;
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
public class ImplDivision implements InterfaceDivision {

    @Override
    public List<BeanDivision> getDivisionList(Connection connection) throws Exception {
        List<BeanDivision> list = new ArrayList<>();
        String query = "SELECT `id`,`division`,`user` FROM `division`;";

        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                BeanDivision b = new BeanDivision();
                b.setId(rs.getInt("id"));
                b.setDivision(rs.getString("division"));
                b.setUser(rs.getString("user"));
                list.add(b);
            }
        }
        ps.close();
        return list;
    }

    @Override
    public String saveDivision(BeanDivision b) throws SQLException, Exception {
        Connection connection = JDBC.con_payroll();
        connection.setAutoCommit(false);

        int divisionid = 0;
        try {
            String sql_allowance = "INSERT INTO `division`(`division`,`user`)VALUES (?,?);";
            PreparedStatement ps_allow = connection.prepareStatement(sql_allowance, Statement.RETURN_GENERATED_KEYS);
            ps_allow.setString(1, b.getDivision());
            ps_allow.setString(2, BeanSystemLog.getUser());
            ps_allow.executeUpdate();

            ResultSet keys = ps_allow.getGeneratedKeys();
            keys.next();
            divisionid = keys.getInt(1);

            b.setId(divisionid);

            ps_allow.close();

            String saveQuery = "INSERT INTO `log`(`event`,`description`,`comcode`,`user`) VALUES (?,?,?,?);";
            PreparedStatement ps = connection.prepareStatement(saveQuery);
            ps.setString(1, "Save");
            ps.setString(2, "Division : " + b.getId());
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
    public String updateDivision(BeanDivision b) throws SQLException, Exception {
        Connection connection = JDBC.con_payroll();
        connection.setAutoCommit(false);

        try {
            String sql_allowance = "UPDATE `division` SET `division`=?,`user`=? WHERE `id`=?;";
            PreparedStatement ps_allow = connection.prepareStatement(sql_allowance);
            ps_allow.setString(1, b.getDivision());
            ps_allow.setString(2, BeanSystemLog.getUser());
            ps_allow.setInt(3, b.getId());
            ps_allow.execute();
            ps_allow.close();

            String saveQuery = "INSERT INTO `log`(`event`,`description`,`comcode`,`user`) VALUES (?,?,?,?);";
            PreparedStatement ps = connection.prepareStatement(saveQuery);
            ps.setString(1, "Update");
            ps.setString(2, "Division : " + b.getId());
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
    public String deleteDivision(int divisionId, String division) throws SQLException, Exception {
        Connection connection = JDBC.con_payroll();
        connection.setAutoCommit(false);
        try {
            String query = "SELECT COUNT(*) FROM `employee_details` WHERE `division`=?;";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, division.trim());
            ResultSet rs = ps.executeQuery();

            if (rs.isBeforeFirst()) {
                rs.first();
                if (rs.getInt(1) > 0) {
                    return "Error:This department already used to update employee details";
                }
            }
            ps.close();

            String sql_attendance = "DELETE FROM `division` WHERE `id`=?;";
            PreparedStatement ps_attendance = connection.prepareStatement(sql_attendance);
            ps_attendance.setInt(1, divisionId);
            ps_attendance.executeUpdate();
            ps_attendance.close();

            String saveQuery = "INSERT INTO `log`(`event`,`description`,`comcode`,`user`) VALUES (?,?,?,?);";
            PreparedStatement ps_log = connection.prepareStatement(saveQuery);
            ps_log.setString(1, "Delete Division");
            ps_log.setString(2, "Division: " + division + " Id:" + divisionId);
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
    public String validateDivision(Connection connection, String division) throws SQLException, Exception {
        String available = "Not Found";
        String query = "SELECT `division` FROM `division` WHERE `division`=?;";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, division.trim());
        ResultSet rs = ps.executeQuery();

        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                available = rs.getString("division");
            }
        }
        ps.close();
        return available;
    }

}
