/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daoImpls;

import dao.BeanSalaryAdvance;
import dao.BeanSystemLog;
import interfaces.InterfaceSalaryAdvance;
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
public class ImplSalaryAdvance implements InterfaceSalaryAdvance {

    @Override
    public List<BeanSalaryAdvance> getAdvanceList(Connection connection, String comCode, String monthCode) throws Exception {
        List<BeanSalaryAdvance> list = new ArrayList<>();
        String query = "SELECT `salary_advance`.`empid`,`employee_details`.`empcode`,`employee_details`.`empinitialname`,`employee_details`.`division`,\n"
                + " ((`employee_details`.`basicsalary`+`employee_details`.`budgetallowance`+`employee_details`.`bra2`+`employee_details`.`allow1`+`employee_details`.`allow2`+\n"
                + " `employee_details`.`allow3`+`employee_details`.`allow4`+`employee_details`.`allow5`)-\n"
                + " (`employee_details`.`deduct1`+`employee_details`.`deduct2`+`employee_details`.`deduct3`+`employee_details`.`deduct4`+`employee_details`.`deduct5`)) AS Salary,\n"
                + " `salary_advance`.`amount`,`salary_advance`.`reason` \n"
                + "FROM `salary_advance`,`employee_details` WHERE `salary_advance`.`empid` = `employee_details`.`empid` AND `salary_advance`.`comcode`= `employee_details`.`comcode` \n"
                + "AND `monthCode`=? AND `salary_advance`.`comcode`=?";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, monthCode);
        ps.setString(2, comCode);
        ResultSet rs = ps.executeQuery();

        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                BeanSalaryAdvance b = new BeanSalaryAdvance();
                b.setEmpid(rs.getInt("empid"));
                b.setEmpCode(rs.getInt("empcode"));
                b.setEmpName(rs.getString("empinitialname"));
                b.setDivision(rs.getString("division"));
                b.setSalary(rs.getBigDecimal("Salary"));
                b.setReason(rs.getString("reason"));
                b.setAmount(rs.getBigDecimal("amount"));
                list.add(b);
            }
        }
        ps.close();
        return list;
    }

    @Override
    public String saveAdvance(BeanSalaryAdvance b) throws SQLException, Exception {
        Connection connection = JDBC.con_payroll();
        connection.setAutoCommit(false);
        try {
            String sql_attendance = "INSERT INTO `salary_advance`(`date`,`monthcode`,`empid`,`reason`,`amount`,`comcode`,`user`)\n"
                    + "VALUES(?,?,?,?,?,?,?);";
            PreparedStatement ps_attendance = connection.prepareStatement(sql_attendance);
            ps_attendance.setDate(1, new java.sql.Date(new Date().getTime()));
            ps_attendance.setString(2, b.getMonthcode());
            ps_attendance.setInt(3, b.getEmpid());
            ps_attendance.setString(4, b.getReason());
            ps_attendance.setBigDecimal(5, b.getAmount());
            ps_attendance.setString(6, BeanSystemLog.getComcode());
            ps_attendance.setString(7, BeanSystemLog.getUser());
            ps_attendance.execute();
            ps_attendance.close();

            String saveQuery = "INSERT INTO `log`(`event`,`description`,`comcode`,`user`) VALUES (?,?,?,?);";
            PreparedStatement ps = connection.prepareStatement(saveQuery);
            ps.setString(1, "Save Salary Advance");
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
    public String updateAdvance(BeanSalaryAdvance b) throws SQLException, Exception {
        Connection connection = JDBC.con_payroll();
        connection.setAutoCommit(false);
        try {
            String sql_attendance = "UPDATE `salary_advance` SET `reason`=?,`amount`=?,`user`=? WHERE `monthcode`=? AND `empid`=? AND `comcode`=?";
            PreparedStatement ps_attendance = connection.prepareStatement(sql_attendance);
            ps_attendance.setString(1, b.getReason());
            ps_attendance.setBigDecimal(2, b.getAmount());
            ps_attendance.setString(3, BeanSystemLog.getUser());
            ps_attendance.setString(4, b.getMonthcode());
            ps_attendance.setInt(5, b.getEmpid());
            ps_attendance.setString(6, BeanSystemLog.getComcode());

            ps_attendance.executeUpdate();
            ps_attendance.close();

            String saveQuery = "INSERT INTO `log`(`event`,`description`,`comcode`,`user`) VALUES (?,?,?,?);";
            PreparedStatement ps = connection.prepareStatement(saveQuery);
            ps.setString(1, "Update Salary Advance");
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
    public String deleteAdvance(BeanSalaryAdvance b) throws SQLException, Exception {
        Connection connection = JDBC.con_payroll();
        connection.setAutoCommit(false);
        try {
            String sql_attendance = "DELETE FROM `salary_advance` WHERE `monthcode`=? AND `empid`=? AND `comcode`=?;";
            PreparedStatement ps_attendance = connection.prepareStatement(sql_attendance);
            ps_attendance.setString(1, b.getMonthcode());
            ps_attendance.setInt(2, b.getEmpid());
            ps_attendance.setString(3, BeanSystemLog.getComcode());
            ps_attendance.executeUpdate();
            ps_attendance.close();

            String saveQuery = "INSERT INTO `log`(`event`,`description`,`comcode`,`user`) VALUES (?,?,?,?);";
            PreparedStatement ps = connection.prepareStatement(saveQuery);
            ps.setString(1, "Delete Salary Advance");
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
