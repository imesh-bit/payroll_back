/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daoImpls;

import dao.BeanBonus;
import dao.BeanSystemLog;
import interfaces.InterfaceBonus;
import java.math.BigDecimal;
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
public class ImplBonus implements InterfaceBonus {

    @Override
    public List<BeanBonus> getBonustList(Connection connection, String comCode, String monthCode) throws Exception {
        List<BeanBonus> list = new ArrayList<>();
        String query = "SELECT `bonus`.`bonusid`,`bonus`.`empid`,`employee_details`.`empcode`,`employee_details`.`empinitialname`,`reference`,`amount`,`employee_details`.`division` \n"
                + "FROM `bonus`,`employee_details` WHERE `bonus`.`empid` = `employee_details`.`empid` AND `bonus`.`comcode`= `employee_details`.`comcode` \n"
                + "AND `monthcode`=? AND `bonus`.`comcode`=?;";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, monthCode);
        ps.setString(2, comCode);
        ResultSet rs = ps.executeQuery();

        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                BeanBonus b = new BeanBonus();
                b.setBonusid(rs.getInt("bonusid"));
                b.setEmpid(rs.getInt("empid"));
                b.setEmpCode(rs.getInt("empcode"));
                b.setEmpName(rs.getString("empinitialname"));
                b.setDivision(rs.getString("division"));
                b.setReference(rs.getString("reference"));
                b.setAmount(rs.getBigDecimal("amount"));
                list.add(b);
            }
        }
        ps.close();
        return list;
    }

    @Override
    public String saveBonus(BeanBonus b) throws SQLException, Exception {
        Connection connection = JDBC.con_payroll();
        connection.setAutoCommit(false);
        try {
            String sql_attendance = "INSERT INTO `bonus`(`empid`,`monthcode`,`monthid`,`reference`,`amount`,`payetax`,`fyear`,`user`,`comcode`)\n"
                    + "VALUES (?,?,?,?,?,?,?,?,?);";
            PreparedStatement ps_attendance = connection.prepareStatement(sql_attendance);
            ps_attendance.setInt(1, b.getEmpid());
            ps_attendance.setString(2, b.getMonthcode());
            ps_attendance.setInt(3, getFYMonthId(connection, b.getMonthcode()));
            ps_attendance.setString(4, b.getReference());
            ps_attendance.setBigDecimal(5, b.getAmount());
            ps_attendance.setBigDecimal(6, BigDecimal.ZERO);//PAYE
            ps_attendance.setInt(7, getFYear(connection));
            ps_attendance.setString(8, BeanSystemLog.getUser());
            ps_attendance.setString(9, BeanSystemLog.getComcode());
            ps_attendance.execute();
            ps_attendance.close();

            String saveQuery = "INSERT INTO `log`(`event`,`description`,`comcode`,`user`) VALUES (?,?,?,?);";
            PreparedStatement ps = connection.prepareStatement(saveQuery);
            ps.setString(1, "Save Bonus");
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

    public int getFYMonthId(Connection connection, String salaryMonth) {
        int monthId = 0;
        try {

            String[] monthArray = salaryMonth.split("-");
            String month = monthArray[0].trim();

            String qery = "SELECT `month_id` FROM `fyear_months` WHERE `month_name`=?";
            PreparedStatement ps = connection.prepareStatement(qery);
            ps.setString(1, month);
            ResultSet rs = ps.executeQuery();
            if (rs.isBeforeFirst()) {
                rs.first();
                monthId = rs.getInt("month_id");
            }
            ps.close();
            rs.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return monthId;
    }

    public int getFYear(Connection connection) {
        int fyear = 0;
        try {

            String qery = "SELECT `fYear` FROM `fyear` WHERE `active`=1";
            PreparedStatement ps = connection.prepareStatement(qery);
            ResultSet rs = ps.executeQuery();
            if (rs.isBeforeFirst()) {
                rs.first();
                fyear = rs.getInt("fYear");
            }
            ps.close();
            rs.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return fyear;
    }

    @Override
    public String updateBonus(BeanBonus b) throws SQLException, Exception {
        Connection connection = JDBC.con_payroll();
        connection.setAutoCommit(false);
        try {
            String sql_attendance = "UPDATE `bonus` SET `empid`=?,`reference`=?,`amount`=?,`user`=?,`comcode`=? "
                    + "WHERE `bonusid`=?;";
            PreparedStatement ps_attendance = connection.prepareStatement(sql_attendance);
            ps_attendance.setInt(1, b.getEmpid());
            ps_attendance.setString(2, b.getReference());
            ps_attendance.setBigDecimal(3, b.getAmount());
            ps_attendance.setString(4, BeanSystemLog.getUser());
            ps_attendance.setString(5, BeanSystemLog.getComcode());
            ps_attendance.setInt(6, b.getBonusid());
            ps_attendance.executeUpdate();
            ps_attendance.close();

            String saveQuery = "INSERT INTO `log`(`event`,`description`,`comcode`,`user`) VALUES (?,?,?,?);";
            PreparedStatement ps = connection.prepareStatement(saveQuery);
            ps.setString(1, "Update Bonus");
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
    public String deleteBonus(BeanBonus b) throws SQLException, Exception {
        Connection connection = JDBC.con_payroll();
        connection.setAutoCommit(false);
        try {
            String sql_attendance = "DELETE FROM `bonus` WHERE `bonusid`=?;";
            PreparedStatement ps_attendance = connection.prepareStatement(sql_attendance);
            ps_attendance.setInt(1,b.getBonusid());
            ps_attendance.executeUpdate();
            ps_attendance.close();

            String saveQuery = "INSERT INTO `log`(`event`,`description`,`comcode`,`user`) VALUES (?,?,?,?);";
            PreparedStatement ps = connection.prepareStatement(saveQuery);
            ps.setString(1, "Delete Bonus");
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
