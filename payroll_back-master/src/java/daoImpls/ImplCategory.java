/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daoImpls;

import dao.BeanCategory;
import dao.BeanSystemLog;
import interfaces.InterfaceCategory;
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
public class ImplCategory implements InterfaceCategory {

    @Override
    public List<BeanCategory> getCategoryList(Connection connection) throws Exception {
        List<BeanCategory> list = new ArrayList<>();
        String query = "SELECT `categoryid`,`categoryname`,`user` FROM `catagory`;";

        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                BeanCategory b = new BeanCategory();
                b.setCategoryid(rs.getInt("categoryid"));
                b.setCategoryname(rs.getString("categoryname"));
                b.setUser(rs.getString("user"));
                list.add(b);
            }
        }
        ps.close();
        return list;
    }

    @Override
    public String saveCategory(BeanCategory b) throws SQLException, Exception {
        Connection connection = JDBC.con_payroll();
        connection.setAutoCommit(false);

        int categoryid = 0;
        try {
            String sql_allowance = "INSERT INTO `catagory`(`categoryname`,`user`)VALUES (?,?);";
            PreparedStatement ps_allow = connection.prepareStatement(sql_allowance, Statement.RETURN_GENERATED_KEYS);
            ps_allow.setString(1, b.getCategoryname());
            ps_allow.setString(2, BeanSystemLog.getUser());
            ps_allow.executeUpdate();

            ResultSet keys = ps_allow.getGeneratedKeys();
            keys.next();
            categoryid = keys.getInt(1);

            b.setCategoryid(categoryid);

            ps_allow.close();

            String saveQuery = "INSERT INTO `log`(`event`,`description`,`comcode`,`user`) VALUES (?,?,?,?);";
            PreparedStatement ps = connection.prepareStatement(saveQuery);
            ps.setString(1, "Save");
            ps.setString(2, "Category : " + b.getCategoryid());
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
    public String updateCategory(BeanCategory b) throws SQLException, Exception {
        Connection connection = JDBC.con_payroll();
        connection.setAutoCommit(false);

        try {
            String sql_allowance = "UPDATE `catagory` SET `categoryname`=?,`user`=? WHERE `categoryid`=?;";
            PreparedStatement ps_allow = connection.prepareStatement(sql_allowance);
            ps_allow.setString(1, b.getCategoryname());
            ps_allow.setString(2, BeanSystemLog.getUser());
            ps_allow.setInt(3, b.getCategoryid());
            ps_allow.executeUpdate();
            ps_allow.close();

            String saveQuery = "INSERT INTO `log`(`event`,`description`,`comcode`,`user`) VALUES (?,?,?,?);";
            PreparedStatement ps = connection.prepareStatement(saveQuery);
            ps.setString(1, "Update");
            ps.setString(2, "Category : " + b.getCategoryid());
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
    public String deleteCategory(int categoryid,String wageboard) throws SQLException, Exception {
        Connection connection = JDBC.con_payroll();
        connection.setAutoCommit(false);
        try {
            String query = "SELECT COUNT(*) FROM `employee_details` WHERE `categoryid`=?;";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1,categoryid);
            ResultSet rs = ps.executeQuery();

            if (rs.isBeforeFirst()) {
                rs.first();
                if (rs.getInt(1) > 0) {
                    return "Error:This wageboard already used to update employee details";
                }
            }
            ps.close();

            String sql_attendance = "DELETE FROM `catagory` WHERE `categoryid`=?;";
            PreparedStatement ps_attendance = connection.prepareStatement(sql_attendance);
            ps_attendance.setInt(1, categoryid);
            ps_attendance.executeUpdate();
            ps_attendance.close();

            String saveQuery = "INSERT INTO `log`(`event`,`description`,`comcode`,`user`) VALUES (?,?,?,?);";
            PreparedStatement ps_log = connection.prepareStatement(saveQuery);
            ps_log.setString(1, "Delete Wageboard");
            ps_log.setString(2, "Wageboard: " + wageboard + " Id:" + categoryid);
            ps_log.setString(3, BeanSystemLog.getComcode());
            ps_log.setString(4, BeanSystemLog.getUser());
            ps_log.execute();
            ps_log.close();

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
    public int getCategoryId(Connection connection, String category) throws Exception  {
      int groupId = 0;
        String query = "SELECT `categoryid` FROM `catagory` WHERE `categoryname`=?;";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, category.trim());
        ResultSet rs = ps.executeQuery();

        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                groupId = rs.getInt("categoryid");
            }
        }
        ps.close();
        return groupId;
    }
}
