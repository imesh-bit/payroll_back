/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daoImpls;

import dao.BeanDesignation;
import dao.BeanSystemLog;
import dao.BeanTypeofEmployee;
import interfaces.InterfaceTypeofEmployee;
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
public class ImplTypeofEmployee implements InterfaceTypeofEmployee {

    @Override
    public List<BeanTypeofEmployee> getEmpTypeList(Connection connection) throws Exception {
        List<BeanTypeofEmployee> list = new ArrayList<>();
        String query = "SELECT `id`,`empType`,`isExecutive` FROM `employee_typ`;";

        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                BeanTypeofEmployee b = new BeanTypeofEmployee();
                b.setId(rs.getInt("id"));
                b.setEmpType(rs.getString("empType"));
                b.setId(rs.getInt("isExecutive"));
                list.add(b);
            }
        }
        ps.close();
        return list;
    }

    @Override
    public String saveType(BeanTypeofEmployee b) throws SQLException, Exception {
        Connection connection = JDBC.con_payroll();
        connection.setAutoCommit(false);

        int typeid = 0;
        try {
            String sql_allowance = "INSERT INTO `employee_typ`(`empType`,`isExecutive`)VALUES (?,?);";
            PreparedStatement ps_allow = connection.prepareStatement(sql_allowance, Statement.RETURN_GENERATED_KEYS);
            ps_allow.setString(1, b.getEmpType());
            ps_allow.setInt(2, b.getIsExecutive());
            ps_allow.executeUpdate();

            ResultSet keys = ps_allow.getGeneratedKeys();
            keys.next();
            typeid = keys.getInt(1);

            b.setId(typeid);

            ps_allow.close();

            String saveQuery = "INSERT INTO `log`(`event`,`description`,`comcode`,`user`) VALUES (?,?,?,?);";
            PreparedStatement ps = connection.prepareStatement(saveQuery);
            ps.setString(1, "Save");
            ps.setString(2, "Employee type : " + b.getId());
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
    public String updateType(BeanTypeofEmployee b) throws SQLException, Exception {
        Connection connection = JDBC.con_payroll();
        connection.setAutoCommit(false);

        try {
            String sql_allowance = "UPDATE `employee_typ` SET `empType` = ?,`isExecutive` = ? WHERE `id` = ?;";
            PreparedStatement ps_allow = connection.prepareStatement(sql_allowance);
            ps_allow.setString(1, b.getEmpType());
            ps_allow.setInt(2, b.getIsExecutive());
            ps_allow.setInt(3, b.getId());
            ps_allow.execute();
            ps_allow.close();

            String saveQuery = "INSERT INTO `log`(`event`,`description`,`comcode`,`user`) VALUES (?,?,?,?);";
            PreparedStatement ps = connection.prepareStatement(saveQuery);
            ps.setString(1, "Update");
            ps.setString(2, "Employee type : " + b.getId());
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

}
