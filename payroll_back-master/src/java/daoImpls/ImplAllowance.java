/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daoImpls;

import dao.BeanAllowance;
import dao.BeanSystemLog;
import interfaces.InterfaceAllowance;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import util.JDBC;

/**
 *
 * @author Nimesha
 */
public class ImplAllowance implements InterfaceAllowance {

    @Override
    public BeanAllowance getAllowanceList(Connection connection) throws Exception {
        String query = "SELECT `id`,`allow1`,`allow1_status`,`allow2`,`allow2_status`,`allow3`,`allow3_status`,`allow4`,`allow4_status`,`allow5`,`allow5_status` "
                + "FROM `allowance`;";

        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        BeanAllowance b = new BeanAllowance();

        if (rs.isBeforeFirst()) {
            rs.first();
            b.setId(rs.getInt("id"));
            b.setAllow1(rs.getString("allow1"));
            b.setAllow1_status(rs.getBoolean("allow1_status"));
            if (checkPayroll(connection, rs.getString("allow1"), "allow1")) {//true
                b.setUpdate_status1(false);
            } else {
                b.setUpdate_status1(true);
            }
            b.setAllow2(rs.getString("allow2"));
            b.setAllow2_status(rs.getBoolean("allow2_status"));
            if (checkPayroll(connection, rs.getString("allow2"), "allow2")) {
                b.setUpdate_status2(false);
            } else {
                b.setUpdate_status2(true);
            }
            b.setAllow3(rs.getString("allow3"));
            b.setAllow3_status(rs.getBoolean("allow3_status"));
            if (checkPayroll(connection, rs.getString("allow3"), "allow3")) {
                b.setUpdate_status3(false);
            } else {
                b.setUpdate_status3(true);
            }
            b.setAllow4(rs.getString("allow4"));
            b.setAllow4_status(rs.getBoolean("allow4_status"));
            if (checkPayroll(connection, rs.getString("allow4"), "allow4")) {
                b.setUpdate_status4(false);
            } else {
                b.setUpdate_status4(true);
            }
            b.setAllow5(rs.getString("allow5"));
            b.setAllow5_status(rs.getBoolean("allow5_status"));
            if (checkPayroll(connection, rs.getString("allow5"), "allow5")) {
                b.setUpdate_status5(false);
            } else {
                b.setUpdate_status5(true);
            }
        } else {
            b.setUpdate_status1(true);
            b.setUpdate_status2(true);
            b.setUpdate_status3(true);
            b.setUpdate_status4(true);
            b.setUpdate_status5(true);
        }
        ps.close();
        return b;
    }

    @Override
    public String saveAllowance(BeanAllowance b) throws SQLException, Exception {
        Connection connection = JDBC.con_payroll();
        connection.setAutoCommit(false);

        try {
            String sql_allowance = "INSERT INTO `allowance`(`allow1`,`allow1_status`,`allow2`,`allow2_status`,`allow3`,`allow3_status`,`allow4`,`allow4_status`,`allow5`,`allow5_status`)\n"
                    + "VALUES(?,?,?,?,?,?,?,?,?,?);";
            PreparedStatement ps_allow = connection.prepareStatement(sql_allowance);
            ps_allow.setString(1, b.getAllow1());
            ps_allow.setBoolean(2, b.isAllow1_status());
            ps_allow.setString(3, b.getAllow2());
            ps_allow.setBoolean(4, b.isAllow2_status());
            ps_allow.setString(5, b.getAllow3());
            ps_allow.setBoolean(6, b.isAllow3_status());
            ps_allow.setString(7, b.getAllow4());
            ps_allow.setBoolean(8, b.isAllow4_status());
            ps_allow.setString(9, b.getAllow5());
            ps_allow.setBoolean(10, b.isAllow5_status());
            ps_allow.execute();
            ps_allow.close();

            String saveQuery = "INSERT INTO `log`(`event`,`description`,`comcode`,`user`) VALUES (?,?,?,?);";
            PreparedStatement ps = connection.prepareStatement(saveQuery);
            ps.setString(1, "Save");
            ps.setString(2, "Allowance");
            ps.setString(3, BeanSystemLog.getComcode());
            ps.setString(4, BeanSystemLog.getUser());
            ps.execute();
            ps.close();

            connection.commit();
            connection.setAutoCommit(true);

            return "Details saved sucessfully";
        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
            return "Error:" + e.getLocalizedMessage();
        } finally {
            connection.close();
        }

    }

    @Override
    public String updateAllowance(BeanAllowance b) throws SQLException, Exception {
        Connection connection = JDBC.con_payroll();
        connection.setAutoCommit(false);

        try {
            BeanAllowance preValues = getAllowanceList(connection);
            if (!preValues.getAllow1().isEmpty()) {
                if (!preValues.getAllow1().matches(b.getAllow1()) || preValues.isAllow1_status() != b.isAllow1_status()) {
                    if (checkPayroll(connection, preValues.getAllow1(), "allow1")) {
                        connection.rollback();
                        return "Error:Allowance1 Cannot Be Edited";
                    }
                }
            }
            if (!preValues.getAllow2().isEmpty()) {
                if (!preValues.getAllow2().matches(b.getAllow2()) || preValues.isAllow2_status() != b.isAllow2_status()) {
                    if (checkPayroll(connection, preValues.getAllow2(), "allow2")) {
                        connection.rollback();
                        return "Error:Allowance2 Cannot Be Edited";
                    }
                }
            }
            if (!preValues.getAllow3().isEmpty()) {
                if (!preValues.getAllow3().matches(b.getAllow3()) || preValues.isAllow3_status() != b.isAllow3_status()) {
                    if (checkPayroll(connection, preValues.getAllow3(), "allow3")) {
                        connection.rollback();
                        return "Error:Allowance3 Cannot Be Edited";
                    }
                }
            }
            if (!preValues.getAllow4().isEmpty()) {
                if (!preValues.getAllow4().matches(b.getAllow4()) || preValues.isAllow4_status() != b.isAllow4_status()) {
                    if (checkPayroll(connection, preValues.getAllow4(), "allow4")) {
                        connection.rollback();
                        return "Error:Allowance4 Cannot Be Edited";
                    }
                }
            }
            if (!preValues.getAllow5().isEmpty()) {
                if (!preValues.getAllow5().matches(b.getAllow5()) || preValues.isAllow5_status() != b.isAllow5_status()) {
                    if (checkPayroll(connection, preValues.getAllow5(), "allow5")) {
                        connection.rollback();
                        return "Error:Allowance5 Cannot Be Edited";
                    }
                }
            }

            String sql_allowance1 = "UPDATE `allowance` SET `allow1`=?,`allow1_status`=?,`allow2`=?,`allow2_status`=?,`allow3`=?,`allow3_status`=?,`allow4`=?,`allow4_status`=?,`allow5`=?,`allow5_status`=?  "
                    + "WHERE `id`=?;";
            PreparedStatement ps_allow = connection.prepareStatement(sql_allowance1);
            ps_allow.setString(1, b.getAllow1());
            ps_allow.setBoolean(2, b.isAllow1_status());
            ps_allow.setString(3, b.getAllow2());
            ps_allow.setBoolean(4, b.isAllow2_status());
            ps_allow.setString(5, b.getAllow3());
            ps_allow.setBoolean(6, b.isAllow3_status());
            ps_allow.setString(7, b.getAllow4());
            ps_allow.setBoolean(8, b.isAllow4_status());
            ps_allow.setString(9, b.getAllow5());
            ps_allow.setBoolean(10, b.isAllow5_status());
            ps_allow.setInt(11, b.getId());
            ps_allow.executeUpdate();
            ps_allow.close();

            String saveQuery5 = "INSERT INTO `log`(`event`,`description`,`comcode`,`user`) VALUES (?,?,?,?);";
            PreparedStatement ps5 = connection.prepareStatement(saveQuery5);
            ps5.setString(1, "Update");
            ps5.setString(2, "Allowance");
            ps5.setString(3, BeanSystemLog.getComcode());
            ps5.setString(4, BeanSystemLog.getUser());
            ps5.execute();
            ps5.close();

            connection.commit();
            connection.setAutoCommit(true);

            return "Details updated sucessfully";

        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
            return "Error:" + e.getLocalizedMessage();
        } finally {
            connection.close();
        }

    }

    @Override
    public boolean checkPayroll(Connection connection, String allowName, String allowCode) throws SQLException, Exception {
        String query = null;
        if (allowCode.equals("allow1")) {
            query = "SELECT COUNT(*) FROM `payroll` WHERE `allow1_name`=?;";
        } else if (allowCode.equals("allow2")) {
            query = "SELECT COUNT(*) FROM `payroll` WHERE `allow2_name`=?;";
        } else if (allowCode.equals("allow3")) {
            query = "SELECT COUNT(*) FROM `payroll` WHERE `allow3_name`=?;";
        } else if (allowCode.equals("allow4")) {
            query = "SELECT COUNT(*) FROM `payroll` WHERE `allow4_name`=?;";
        } else {
            query = "SELECT COUNT(*) FROM `payroll` WHERE `allow5_name`=?;";
        }

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, allowName);
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
