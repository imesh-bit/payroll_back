/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daoImpls;

import dao.BeanAllowanceRequest;
import dao.BeanSystemLog;
import interfaces.InterfaceAllowanceRequest;
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
public class ImplAllowanceRequest implements InterfaceAllowanceRequest {

    @Override
    public List<BeanAllowanceRequest> getAllowanceRequestList(Connection connection, String comCode, String monthCode, String reqType) throws Exception {
        List<BeanAllowanceRequest> list = new ArrayList<>();
        String query = "SELECT `allowance_request`.`id`,`allowance_request`.`empid`,`employee_details`.`empcode`,`employee_details`.`empinitialname`,`allowtype`,`allowancecode`,`amount`,`employee_details`.`division` \n"
                + "FROM `allowance_request`,`employee_details` WHERE `allowance_request`.`empid` = `employee_details`.`empid` AND `allowance_request`.`comcode`= `employee_details`.`comcode` \n"
                + "AND `monthCode`='" + monthCode + "' AND `allowance_request`.`comcode`='" + comCode + "' AND `allowance_request`.`allowtype`='" + reqType + "';";

        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                BeanAllowanceRequest b = new BeanAllowanceRequest();
                b.setId(rs.getInt("id"));
                b.setEmpid(rs.getInt("empid"));
                b.setEmpCode(rs.getInt("empcode"));
                b.setEmpName(rs.getString("empinitialname"));
                b.setDivision(rs.getString("division"));
                b.setAllowanceType(rs.getString("allowtype"));
                b.setAllowanceCode(rs.getString("allowancecode"));
                b.setAmount(rs.getBigDecimal("amount"));
                list.add(b);
            }
        }
        ps.close();
        return list;
    }

    @Override
    public String saveAllowanceRequest(BeanAllowanceRequest b) throws SQLException, Exception {
        Connection connection = JDBC.con_payroll();
        connection.setAutoCommit(false);
        try {
            String sql_attendance = "INSERT INTO `allowance_request`(`empid`,`allowancecode`,`monthcode`,`amount`,`user`,`comcode`,`allowtype`)\n"
                    + "VALUES(?,?,?,?,?,?,?);";
            PreparedStatement ps_attendance = connection.prepareStatement(sql_attendance);
            ps_attendance.setInt(1, b.getEmpid());
            ps_attendance.setString(2, b.getAllowanceCode());
            ps_attendance.setString(3, b.getMonthcode());
            ps_attendance.setBigDecimal(4, b.getAmount());
            ps_attendance.setString(5, BeanSystemLog.getUser());
            ps_attendance.setString(6, BeanSystemLog.getComcode());
            ps_attendance.setString(7, b.getAllowanceType());
            ps_attendance.execute();
            ps_attendance.close();

            String saveQuery = "INSERT INTO `log`(`event`,`description`,`comcode`,`user`) VALUES (?,?,?,?);";
            PreparedStatement ps = connection.prepareStatement(saveQuery);
            ps.setString(1, b.getAllowanceType().equals("A") ? "Save Allowanace Request" : "Save Noncash Request");
            ps.setString(2, "Salary Month: " + b.getMonthcode() + " EmpId:" + b.getEmpid());
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
    public String updateAllowanceRequest(BeanAllowanceRequest b) throws SQLException, Exception {
        Connection connection = JDBC.con_payroll();
        connection.setAutoCommit(false);
        try {
            String sql_attendance = "UPDATE `allowance_request` SET `allowancecode`=?,`amount`=?,`user`=? WHERE `id`=?";
            PreparedStatement ps_attendance = connection.prepareStatement(sql_attendance);
            ps_attendance.setString(1, b.getAllowanceCode());
            ps_attendance.setBigDecimal(2, b.getAmount());
            ps_attendance.setString(3, BeanSystemLog.getUser());
            ps_attendance.setInt(4, b.getId());
            ps_attendance.executeUpdate();
            ps_attendance.close();

            String saveQuery = "INSERT INTO `log`(`event`,`description`,`comcode`,`user`) VALUES (?,?,?,?);";
            PreparedStatement ps = connection.prepareStatement(saveQuery);
            ps.setString(1, b.getAllowanceType().equals("A") ? "Update Allowanace Request" : "Update Noncash Request");
            ps.setString(2, "Salary Month: " + b.getMonthcode() + " EmpId:" + b.getEmpid());
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
    public String deleteAllowanceRequest(BeanAllowanceRequest b) throws SQLException, Exception {
        Connection connection = JDBC.con_payroll();
        connection.setAutoCommit(false);
        try {
            String sql_attendance = "DELETE FROM `allowance_request` WHERE `id`=?;";
            PreparedStatement ps_attendance = connection.prepareStatement(sql_attendance);
            ps_attendance.setInt(1, b.getId());
            ps_attendance.executeUpdate();
            ps_attendance.close();

            String saveQuery = "INSERT INTO `log`(`event`,`description`,`comcode`,`user`) VALUES (?,?,?,?);";
            PreparedStatement ps = connection.prepareStatement(saveQuery);
            ps.setString(1, b.getAllowanceType().equals("A") ? "Delete Allowanace Request" : "Delete Noncash Request");
            ps.setString(2, "Salary Month: " + b.getMonthcode() + " EmpId:" + b.getEmpid());
            ps.setString(3, BeanSystemLog.getComcode());
            ps.setString(4, BeanSystemLog.getUser());
            ps.execute();
            ps.close();

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

}
