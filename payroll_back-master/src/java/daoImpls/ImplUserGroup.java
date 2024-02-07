/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daoImpls;

import dao.BeanSystemLog;
import dao.BeanUsergroup;
import interfaces.InterfaceUserGroup;
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
public class ImplUserGroup implements InterfaceUserGroup {

    @Override
    public BeanUsergroup getSelectedUserGroup(Connection connection, String userGroup) throws SQLException {
        String query = "SELECT * FROM `usergroup` WHERE usergroup=?;";//UserGroup_hrm

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, userGroup);
        ResultSet rs = ps.executeQuery();
        BeanUsergroup permission = new BeanUsergroup();
        //  System.out.println(ps.toString());
        if (rs.isBeforeFirst()) {
            rs.first();
            permission.setP1(rs.getBoolean("p1"));
            permission.setP2(rs.getBoolean("p2"));
            permission.setP3(rs.getBoolean("p3"));
            permission.setP4(rs.getBoolean("p4"));
            permission.setP5(rs.getBoolean("p5"));
            permission.setP6(rs.getBoolean("p6"));
            permission.setP7(rs.getBoolean("p7"));
            permission.setP8(rs.getBoolean("p8"));
            permission.setP9(rs.getBoolean("p9"));
            permission.setP10(rs.getBoolean("p10"));
            permission.setP11(rs.getBoolean("p11"));
            permission.setP12(rs.getBoolean("p12"));
            permission.setP13(rs.getBoolean("p13"));
            permission.setP14(rs.getBoolean("p14"));
            permission.setP15(rs.getBoolean("p15"));
            permission.setP16(rs.getBoolean("p16"));
            permission.setP17(rs.getBoolean("p17"));
            permission.setP18(rs.getBoolean("p18"));
            permission.setP19(rs.getBoolean("p19"));
        }
        ps.close();
        return permission;
    }

    @Override
    public List<BeanUsergroup> getUserGroupList(Connection connection) throws SQLException {
        List<BeanUsergroup> groupList = new ArrayList<>();

        String query = "SELECT usergroup FROM `usergroup`;";//UserGroup_hrm

        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            BeanUsergroup permission = new BeanUsergroup();
            permission.setUsergroup(rs.getString("usergroup"));
            groupList.add(permission);

        }
        rs.close();
        ps.close();
        return groupList;
    }

    @Override
    public String saveUserGroup(BeanUsergroup b) throws SQLException, Exception {
        Connection connection = JDBC.con_payroll();
        connection.setAutoCommit(false);

        try {
            String sql_allowance = "INSERT INTO `usergroup`(`usergroup`,`p1`,`p2`,`p3`,`p4`,`p5`,`p6`,`p7`,`p8`,`p9`,`p10`,`p11`,`p12`,`p13`,`p14`,`p15`,`p16`,`p17`,`p18`,`p19`,`user`)\n"
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
            PreparedStatement ps_allow = connection.prepareStatement(sql_allowance);
            ps_allow.setString(1, b.getUsergroup());
            ps_allow.setBoolean(2, b.isP1());
            ps_allow.setBoolean(3, b.isP2());
            ps_allow.setBoolean(4, b.isP3());
            ps_allow.setBoolean(5, b.isP4());
            ps_allow.setBoolean(6, b.isP5());
            ps_allow.setBoolean(7, b.isP6());
            ps_allow.setBoolean(8, b.isP7());
            ps_allow.setBoolean(9, b.isP8());
            ps_allow.setBoolean(10, b.isP9());
            ps_allow.setBoolean(11, b.isP10());
            ps_allow.setBoolean(12, b.isP11());
            ps_allow.setBoolean(13, b.isP12());
            ps_allow.setBoolean(14, b.isP13());
            ps_allow.setBoolean(15, b.isP14());
            ps_allow.setBoolean(16, b.isP15());
            ps_allow.setBoolean(17, b.isP16());
            ps_allow.setBoolean(18, b.isP17());
            ps_allow.setBoolean(19, b.isP18());
            ps_allow.setBoolean(20, b.isP19());
            ps_allow.setString(21, BeanSystemLog.getUser());
            ps_allow.execute();
            ps_allow.close();

            String saveQuery = "INSERT INTO `log`(`event`,`description`,`comcode`,`user`) VALUES (?,?,?,?);";
            PreparedStatement ps = connection.prepareStatement(saveQuery);
            ps.setString(1, "Save");
            ps.setString(2, "UserGroup: " + b.getUsergroup());
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
    public String updateUserGroup(BeanUsergroup b) throws SQLException, Exception {
        Connection connection = JDBC.con_payroll();
        connection.setAutoCommit(false);

        try {
            String sql_allowance = "UPDATE `usergroup`SET `p1`=?,`p2`=?,`p3`=?,`p4`=?,`p5`=?,`p6`=?,`p7`=?,`p8`=?,`p9`=?,`p10`=?,`p11`=?,`p12`=?,`p13`=?,`p14`=?,`p15`=?,`p16`=?,`p17`=?,`p18`=?,`p19`=?,`user`=? "
                    + "WHERE `usergroup`=?;";
            PreparedStatement ps_allow = connection.prepareStatement(sql_allowance);
            ps_allow.setBoolean(1, b.isP1());
            ps_allow.setBoolean(2, b.isP2());
            ps_allow.setBoolean(3, b.isP3());
            ps_allow.setBoolean(4, b.isP4());
            ps_allow.setBoolean(5, b.isP5());
            ps_allow.setBoolean(6, b.isP6());
            ps_allow.setBoolean(7, b.isP7());
            ps_allow.setBoolean(8, b.isP8());
            ps_allow.setBoolean(9, b.isP9());
            ps_allow.setBoolean(10, b.isP10());
            ps_allow.setBoolean(11, b.isP11());
            ps_allow.setBoolean(12, b.isP12());
            ps_allow.setBoolean(13, b.isP13());
            ps_allow.setBoolean(14, b.isP14());
            ps_allow.setBoolean(15, b.isP15());
            ps_allow.setBoolean(16, b.isP16());
            ps_allow.setBoolean(17, b.isP17());
            ps_allow.setBoolean(18, b.isP18());
            ps_allow.setBoolean(19, b.isP19());
            ps_allow.setString(20, BeanSystemLog.getUser());
            ps_allow.setString(21, b.getUsergroup());
            ps_allow.executeUpdate();
            ps_allow.close();

            String saveQuery = "INSERT INTO `log`(`event`,`description`,`comcode`,`user`) VALUES (?,?,?,?);";
            PreparedStatement ps = connection.prepareStatement(saveQuery);
            ps.setString(1, "Update");
            ps.setString(2, "UserGroup: " + b.getUsergroup());
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
    public Boolean checkExistingUsergroup(Connection connection, String UserGroup) throws Exception {
        boolean existingGroup = false;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT COUNT(*) FROM `usergroup` WHERE `usergroup`=? ";
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, UserGroup);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (rs.getInt(1) > 0) {
                    existingGroup = true;
                }
            }

            pstmt.close();
            rs.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            pstmt.close();
            rs.close();
        }
        return existingGroup;
    }

}
