/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daoImpls;

import dao.BeanLogin;
import dao.BeanSystemLog;
import dao.BeanUsergroup;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import interfaces.InterfaceUserLogin;

/**
 *
 * @author Nimesha
 */
public class ImplUserLogin implements InterfaceUserLogin {

    @Override
    public BeanLogin isValidUser(Connection connection, String userName, String password) throws Exception {
        String sql = "SELECT `empid`,`fullname`,`email`,comcode,loccode,usergroup FROM `user`  WHERE  `username`= ? AND `password`=? and status=1";

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, userName);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();

        BeanLogin bean = new BeanLogin();
        bean.setValiduser(false);
        //System.out.println(ps.toString());
        if (rs.isBeforeFirst()) {
            rs.first();
            bean.setValiduser(true);
            bean.setEmpId(rs.getInt("empid"));
            bean.setLoguserName(rs.getString("fullname"));
            bean.setEmail(rs.getString("email"));
            bean.setComcode(rs.getString("comcode"));
            bean.setLoccode(rs.getString("loccode"));
            bean.setUsergroup(rs.getString("usergroup"));
            bean.setUserPreviledges(getUserPermissions(connection, rs.getString("usergroup")));
        }
        ps.close();
        return bean;
    }

    @Override
    public BeanUsergroup getUserPermissions(Connection connection, String userGroup) throws SQLException {

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
    public String saveLoginDetails(Connection connection) throws SQLException, Exception {

        connection.setAutoCommit(false);

        try {
            String saveQuery = "INSERT INTO `log`(`event`,`description`,`comcode`,`user`) VALUES (?,?,?,?);";
            PreparedStatement ps = connection.prepareStatement(saveQuery);
            ps.setString(1, "Login");
            ps.setString(2, "CU HRM");
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
        }

    }

}
