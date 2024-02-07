/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daoImpls;

import dao.BeanDeduction;
import dao.BeanSystemLog;
import interfaces.InterfaceDeduction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import util.JDBC;

/**
 *
 * @author Nimesha
 */
public class ImplDeduction implements InterfaceDeduction {

    @Override
    public BeanDeduction getDeductionList(Connection connection) throws Exception {
        String query = "SELECT `id`,`deduct1`,`deduct2`,`deduct3`,`deduct4`,`deduct5` FROM `deduction` ;";

        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        BeanDeduction b = new BeanDeduction();

        if (rs.isBeforeFirst()) {
            rs.first();
            b.setId(rs.getInt("id"));
            b.setDeduct1(rs.getString("deduct1"));
            b.setDeduct2(rs.getString("deduct2"));
            b.setDeduct3(rs.getString("deduct3"));
            b.setDeduct4(rs.getString("deduct4"));
            b.setDeduct5(rs.getString("deduct5"));
        }
        ps.close();
        return b;
    }

    @Override
    public String saveDeduction(BeanDeduction b) throws SQLException, Exception {
        Connection connection = JDBC.con_payroll();
        connection.setAutoCommit(false);

        try {
            String sql_deduct = "INSERT INTO `deduction`(`deduct1`,`deduct2`,`deduct3`,`deduct4`,`deduct5`)VALUES (?,?,?,?,?);";
            PreparedStatement ps_deduct = connection.prepareStatement(sql_deduct);
            ps_deduct.setString(1, b.getDeduct1());
            ps_deduct.setString(2, b.getDeduct2());
            ps_deduct.setString(3, b.getDeduct3());
            ps_deduct.setString(4, b.getDeduct4());
            ps_deduct.setString(5, b.getDeduct5());
            ps_deduct.execute();
            ps_deduct.close();

            String saveQuery = "INSERT INTO `log`(`event`,`description`,`comcode`,`user`) VALUES (?,?,?,?);";
            PreparedStatement ps = connection.prepareStatement(saveQuery);
            ps.setString(1, "Save");
            ps.setString(2, "Deduction");
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
    public String updateDeduction(BeanDeduction b) throws SQLException, Exception {
        Connection connection = JDBC.con_payroll();
        connection.setAutoCommit(false);

        try {
            BeanDeduction preValues = getDeductionList(connection);
            if (!preValues.getDeduct1().isEmpty()) {
                if (!preValues.getDeduct1().matches(b.getDeduct1())) {
                    if (checkPayroll(connection, preValues.getDeduct1(), "deduct1")) {
                        connection.rollback();
                        return "Error:Deduction1 Cannot Be Edited";
                    }
                }
            }
            if (!preValues.getDeduct2().isEmpty()) {
                if (!preValues.getDeduct2().matches(b.getDeduct2())) {
                    if (checkPayroll(connection, preValues.getDeduct2(), "deduct2")) {
                        connection.rollback();
                        return "Error:Deduction2 Cannot Be Edited";
                    }
                }
            }
            if (!preValues.getDeduct3().isEmpty()) {
                if (!preValues.getDeduct3().matches(b.getDeduct3())) {
                    if (checkPayroll(connection, preValues.getDeduct3(), "deduct3")) {
                        connection.rollback();
                        return "Error:Deduction3 Cannot Be Edited";
                    }
                }
            }
            if (!preValues.getDeduct4().isEmpty()) {
                if (!preValues.getDeduct4().matches(b.getDeduct4())) {
                    if (checkPayroll(connection, preValues.getDeduct4(), "deduct4")) {
                        connection.rollback();
                        return "Error:Deduction4 Cannot Be Edited";
                    }
                }
            }
            if (!preValues.getDeduct5().isEmpty()) {
                if (!preValues.getDeduct5().matches(b.getDeduct5())) {
                    if (checkPayroll(connection, preValues.getDeduct5(), "deduct5")) {
                        connection.rollback();
                        return "Error:Deduction5 Cannot Be Edited";
                    }
                }
            }

            String sql_deduct = "UPDATE `deduction` SET `deduct1`=?,`deduct2`=?,`deduct3`=?,`deduct4`=?,`deduct5`=? WHERE `id`=?;";
            PreparedStatement ps_deduct = connection.prepareStatement(sql_deduct);
            ps_deduct.setString(1, b.getDeduct1());
            ps_deduct.setString(2, b.getDeduct2());
            ps_deduct.setString(3, b.getDeduct3());
            ps_deduct.setString(4, b.getDeduct4());
            ps_deduct.setString(5, b.getDeduct5());
            ps_deduct.setInt(6, b.getId());
            ps_deduct.executeUpdate();
            ps_deduct.close();

            String saveQuery = "INSERT INTO `log`(`event`,`description`,`comcode`,`user`) VALUES (?,?,?,?);";
            PreparedStatement ps = connection.prepareStatement(saveQuery);
            ps.setString(1, "Update");
            ps.setString(2, "Deduction");
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
    public boolean checkPayroll(Connection connection, String deductName, String deductCode) throws SQLException, Exception {
        String query = null;
        if (deductCode.equals("deduct1")) {
            query = "SELECT COUNT(*) FROM `payroll` WHERE `deduct1_name`=?;";
        } else if (deductCode.equals("deduct2")) {
            query = "SELECT COUNT(*) FROM `payroll` WHERE `deduct2_name`=?;";
        } else if (deductCode.equals("deduct3")) {
            query = "SELECT COUNT(*) FROM `payroll` WHERE `deduct3_name`=?;";
        } else if (deductCode.equals("deduct4")) {
            query = "SELECT COUNT(*) FROM `payroll` WHERE `deduct4_name`=?;";
        } else {
            query = "SELECT COUNT(*) FROM `payroll` WHERE `deduct5_name`=?;";
        }

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, deductName);
        ResultSet rs = ps.executeQuery();

        if (rs.isBeforeFirst()) {
            rs.first();
            if (rs.getInt(1) > 0) {
                return true;
            }

        }
        ps.close();
        return false;
    }
}
