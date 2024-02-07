/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daoImpls;

import dao.BeanAttendance;
import dao.BeanSystemLog;
import interfaces.InterfaceAttendance;
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
public class ImplAttendance implements InterfaceAttendance {

    @Override
    public List<BeanAttendance> getAttendancesList(Connection connection, String comCode, String monthCode) throws Exception {
        List<BeanAttendance> list = new ArrayList<>();
        String query = "SELECT `attendance`.`id`,`attendance`.`empid`,`employee_details`.`empcode`,`employee_details`.`empinitialname`,`noofdates`,`employee_details`.`division` \n"
                + "FROM `attendance`,`employee_details` WHERE `attendance`.`empid` = `employee_details`.`empid` \n"
                + "AND `attendance`.`comcode`= `employee_details`.`comcode` \n"
                + "AND `monthCode`='" + monthCode + "' AND `attendance`.`comcode`='" + comCode + "';";

        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                BeanAttendance b = new BeanAttendance();
                b.setId(rs.getInt("id"));
                b.setEmpid(rs.getInt("empid"));
                b.setEmpCode(rs.getInt("empcode"));
                b.setEmpName(rs.getString("empinitialname"));
                b.setDivision(rs.getString("division"));
                b.setNoofdates(rs.getBigDecimal("noofdates"));
                list.add(b);
            }
        }
        ps.close();
        return list;
    }

    @Override
    public String saveAttendance(BeanAttendance b) throws SQLException, Exception {
        Connection connection = JDBC.con_payroll();
        connection.setAutoCommit(false);
        try {
            String sql_attendance = "INSERT INTO `attendance`(`monthCode`,`empid`,`noofdates`,`comcode`,`user`) VALUES (?,?,?,?,?);";
            PreparedStatement ps_attendance = connection.prepareStatement(sql_attendance);
            ps_attendance.setString(1, b.getMonthCode());
            ps_attendance.setInt(2, b.getEmpid());
            ps_attendance.setBigDecimal(3, b.getNoofdates());
            ps_attendance.setString(4, BeanSystemLog.getComcode());
            ps_attendance.setString(5, BeanSystemLog.getUser());
            ps_attendance.execute();
            ps_attendance.close();

            String saveQuery = "INSERT INTO `log`(`event`,`description`,`comcode`,`user`) VALUES (?,?,?,?);";
            PreparedStatement ps = connection.prepareStatement(saveQuery);
            ps.setString(1, "Save");
            ps.setString(2, "Attendance Month: " + b.getMonthCode() + " EmpId:" + b.getEmpid());
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
    public String updateAttendance(BeanAttendance b) throws SQLException, Exception {
        Connection connection = JDBC.con_payroll();
        connection.setAutoCommit(false);
        try {
            String sql_attendance = "UPDATE `attendance` SET `noofdates`=?,`user`=? WHERE `id`=?;";
            PreparedStatement ps_attendance = connection.prepareStatement(sql_attendance);
            ps_attendance.setBigDecimal(1, b.getNoofdates());
            ps_attendance.setString(2, BeanSystemLog.getUser());
            ps_attendance.setInt(3, b.getId());
            ps_attendance.executeUpdate();
            ps_attendance.close();

            String saveQuery = "INSERT INTO `log`(`event`,`description`,`comcode`,`user`) VALUES (?,?,?,?);";
            PreparedStatement ps = connection.prepareStatement(saveQuery);
            ps.setString(1, "Update");
            ps.setString(2, "Attendance Month: " + b.getMonthCode() + " EmpId:" + b.getEmpid());
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
    public String deleteAttendance(int Id, String monthCode, int empId) throws SQLException, Exception {
        Connection connection = JDBC.con_payroll();
        connection.setAutoCommit(false);
        try {
            String sql_attendance = "DELETE FROM `attendance` WHERE `id`=?;";
            PreparedStatement ps_attendance = connection.prepareStatement(sql_attendance);
            ps_attendance.setInt(1, Id);
            ps_attendance.executeUpdate();
            ps_attendance.close();

            String saveQuery = "INSERT INTO `log`(`event`,`description`,`comcode`,`user`) VALUES (?,?,?,?);";
            PreparedStatement ps = connection.prepareStatement(saveQuery);
            ps.setString(1, "Delete");
            ps.setString(2, "Attendance Month: " + monthCode + " EmpId:" + empId);
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

    @Override
    public String saveAttendanceList(List<BeanAttendance> list) throws SQLException, Exception {
        Connection connection = JDBC.con_payroll();
        connection.setAutoCommit(false);
        try {
            for (BeanAttendance b : list) {
                String sql_attendance = "INSERT INTO `attendance`(`monthCode`,`empid`,`noofdates`,`comcode`,`user`) VALUES (?,?,?,?,?);";
                PreparedStatement ps_attendance = connection.prepareStatement(sql_attendance);
                ps_attendance.setString(1, b.getMonthCode());
                ps_attendance.setInt(2, b.getEmpid());
                ps_attendance.setBigDecimal(3, b.getNoofdates());
                ps_attendance.setString(4, BeanSystemLog.getComcode());
                ps_attendance.setString(5, BeanSystemLog.getUser());
                ps_attendance.execute();
                ps_attendance.close();

                String saveQuery = "INSERT INTO `log`(`event`,`description`,`comcode`,`user`) VALUES (?,?,?,?);";
                PreparedStatement ps = connection.prepareStatement(saveQuery);
                ps.setString(1, "Save Excel");
                ps.setString(2, "Attendance Month: " + b.getMonthCode() + " EmpId:" + b.getEmpid());
                ps.setString(3, BeanSystemLog.getComcode());
                ps.setString(4, BeanSystemLog.getUser());
                ps.execute();
                ps.close();
            }

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

}
