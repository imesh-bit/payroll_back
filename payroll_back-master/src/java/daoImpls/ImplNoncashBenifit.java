/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daoImpls;

import dao.BeanNoncashBenifit;
import dao.BeanSystemLog;
import interfaces.InterfaceNoncashBenifit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import util.JDBC;

/**
 *
 * @author Nimesha
 */
public class ImplNoncashBenifit implements InterfaceNoncashBenifit {

    @Override
    public BeanNoncashBenifit getNoncashList(Connection connection) throws Exception {
        String query = "SELECT * FROM `noncash`;";

        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        BeanNoncashBenifit b = new BeanNoncashBenifit();

        if (rs.isBeforeFirst()) {
            rs.first();
            b.setId(rs.getInt("id"));
            b.setNoncash1(rs.getString("noncash1"));
            b.setNoncash2(rs.getString("noncash2"));
            b.setNoncash3(rs.getString("noncash3"));
            b.setNoncash4(rs.getString("noncash4"));
            b.setNoncash5(rs.getString("noncash5"));
            b.setNoncash6(rs.getString("noncash6"));
            b.setNoncash7(rs.getString("noncash7"));
            b.setNoncash8(rs.getString("noncash8"));
            b.setNoncash9(rs.getString("noncash9"));
            b.setNoncash10(rs.getString("noncash10"));
        }
        ps.close();
        return b;
    }

    @Override
    public String saveNoncash(BeanNoncashBenifit b) throws SQLException, Exception {
        Connection connection = JDBC.con_payroll();
        connection.setAutoCommit(false);

        try {
            String sql_deduct = "INSERT INTO `noncash`(`noncash1`,`noncash2`,`noncash3`,`noncash4`,`noncash5`,`noncash6`,`noncash7`,`noncash8`,`noncash9`,`noncash10`)"
                    + "VALUES (?,?,?,?,?,?,?,?,?,?);";
            PreparedStatement ps_deduct = connection.prepareStatement(sql_deduct);
            ps_deduct.setString(1, b.getNoncash1());
            ps_deduct.setString(2, b.getNoncash2());
            ps_deduct.setString(3, b.getNoncash3());
            ps_deduct.setString(4, b.getNoncash4());
            ps_deduct.setString(5, b.getNoncash5());
            ps_deduct.setString(6, b.getNoncash6());
            ps_deduct.setString(7, b.getNoncash7());
            ps_deduct.setString(8, b.getNoncash8());
            ps_deduct.setString(9, b.getNoncash9());
            ps_deduct.setString(10, b.getNoncash10());
            ps_deduct.execute();
            ps_deduct.close();

            String saveQuery = "INSERT INTO `log`(`event`,`description`,`comcode`,`user`) VALUES (?,?,?,?);";
            PreparedStatement ps = connection.prepareStatement(saveQuery);
            ps.setString(1, "Save");
            ps.setString(2, "Noncash");
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
    public String updateNoncash(BeanNoncashBenifit b) throws SQLException, Exception {
        Connection connection = JDBC.con_payroll();
        connection.setAutoCommit(false);

        try {
            BeanNoncashBenifit preValues = getNoncashList(connection);
            if (!preValues.getNoncash1().isEmpty()) {
                if (!preValues.getNoncash1().matches(b.getNoncash1())) {
                    if (checkPayroll(connection, preValues.getNoncash1(), "noncash1")) {
                        connection.rollback();
                        return "Error:Noncash1 Cannot Be Edited";
                    }
                }
            }
            if (!preValues.getNoncash2().isEmpty()) {
                if (!preValues.getNoncash2().matches(b.getNoncash2())) {
                    if (checkPayroll(connection, preValues.getNoncash2(), "noncash2")) {
                        connection.rollback();
                        return "Error:Noncash2 Cannot Be Edited";
                    }
                }
            }
            if (!preValues.getNoncash3().isEmpty()) {
                if (!preValues.getNoncash3().matches(b.getNoncash3())) {
                    if (checkPayroll(connection, preValues.getNoncash3(), "noncash3")) {
                        connection.rollback();
                        return "Error:Noncash3 Cannot Be Edited";
                    }
                }
            }
            if (!preValues.getNoncash4().isEmpty()) {
                if (!preValues.getNoncash4().matches(b.getNoncash4())) {
                    if (checkPayroll(connection, preValues.getNoncash4(), "noncash4")) {
                        connection.rollback();
                        return "Error:Noncash4 Cannot Be Edited";
                    }
                }
            }
            if (!preValues.getNoncash5().isEmpty()) {
                if (!preValues.getNoncash5().matches(b.getNoncash5())) {
                    if (checkPayroll(connection, preValues.getNoncash5(), "noncash5")) {
                        connection.rollback();
                        return "Error:Noncash5 Cannot Be Edited";
                    }
                }
            }
            if (!preValues.getNoncash6().isEmpty()) {
                if (!preValues.getNoncash6().matches(b.getNoncash6())) {
                    if (checkPayroll(connection, preValues.getNoncash6(), "noncash6")) {
                        connection.rollback();
                        return "Error:Noncash6 Cannot Be Edited";
                    }
                }
            }
            if (!preValues.getNoncash7().isEmpty()) {
                if (!preValues.getNoncash7().matches(b.getNoncash7())) {
                    if (checkPayroll(connection, preValues.getNoncash7(), "noncash7")) {
                        connection.rollback();
                        return "Error:Noncash7 Cannot Be Edited";
                    }
                }
            }

            String sql_deduct = "UPDATE`noncash` SET `noncash1`=?,`noncash2`=?,`noncash3`=?,`noncash4`=?,`noncash5`=?,`noncash6`=?,`noncash7`=?,`noncash8`=?,`noncash9`=?,`noncash10`=? "
                    + "WHERE `id`=?;";
            PreparedStatement ps_deduct = connection.prepareStatement(sql_deduct);
            ps_deduct.setString(1, b.getNoncash1());
            ps_deduct.setString(2, b.getNoncash2());
            ps_deduct.setString(3, b.getNoncash3());
            ps_deduct.setString(4, b.getNoncash4());
            ps_deduct.setString(5, b.getNoncash5());
            ps_deduct.setString(6, b.getNoncash6());
            ps_deduct.setString(7, b.getNoncash7());
            ps_deduct.setString(8, b.getNoncash8());
            ps_deduct.setString(9, b.getNoncash9());
            ps_deduct.setString(10, b.getNoncash10());
            ps_deduct.setInt(11, b.getId());
            ps_deduct.executeUpdate();
            ps_deduct.close();

            String saveQuery = "INSERT INTO `log`(`event`,`description`,`comcode`,`user`) VALUES (?,?,?,?);";
            PreparedStatement ps = connection.prepareStatement(saveQuery);
            ps.setString(1, "Update");
            ps.setString(2, "Noncash");
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
    public boolean checkPayroll(Connection connection, String NoncashName, String noncashCode) throws SQLException, Exception {
        String query = null;
        if (noncashCode.equals("noncash1")) {
            query = "SELECT COUNT(*) FROM `payroll` WHERE `noncash1_name`=?;";
        } else if (noncashCode.equals("noncash2")) {
            query = "SELECT COUNT(*) FROM `payroll` WHERE `noncash2_name`=?;";
        } else if (noncashCode.equals("noncash3")) {
            query = "SELECT COUNT(*) FROM `payroll` WHERE `noncash3_name`=?;";
        } else if (noncashCode.equals("noncash4")) {
            query = "SELECT COUNT(*) FROM `payroll` WHERE `noncash4_name`=?;";
        } else if (noncashCode.equals("noncash5")) {
            query = "SELECT COUNT(*) FROM `payroll` WHERE `noncash5_name`=?;";
        } else if (noncashCode.equals("noncash6")) {
            query = "SELECT COUNT(*) FROM `payroll` WHERE `noncash6_name`=?;";
        } else if (noncashCode.equals("noncash7")) {
            query = "SELECT COUNT(*) FROM `payroll` WHERE `noncash7_name`=?;";
        } else if (noncashCode.equals("noncash8")) {
            query = "SELECT COUNT(*) FROM `payroll` WHERE `noncash8_name`=?;";
        } else if (noncashCode.equals("noncash9")) {
            query = "SELECT COUNT(*) FROM `payroll` WHERE `noncash9_name`=?;";
        } else if (noncashCode.equals("noncash10")) {
            query = "SELECT COUNT(*) FROM `payroll` WHERE `noncash10_name`=?;";
        }

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, NoncashName);
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
