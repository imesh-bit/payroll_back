/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daoImpls;

import dao.BeanInventory;
import dao.BeanSystemLog;
import interfaces.InterfaceInventory;
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
public class ImplInventory implements InterfaceInventory {

    @Override
    public List<BeanInventory> getInventoryList(Connection connection, int empid) throws Exception {
        List<BeanInventory> list = new ArrayList<>();
        String query = "SELECT `employee_inventory`.`empid`,`employee_details`.`empinitialname`,`itemid`,`item`,`qty`,`description` FROM `employee_inventory`,`employee_details` \n"
                + "WHERE `employee_inventory`.`empid`=`employee_details`.`empid` AND `employee_inventory`.`empid`=?";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, empid);
        ResultSet rs = ps.executeQuery();

        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                BeanInventory b = new BeanInventory();
                b.setEmpid(rs.getInt("empid"));
                b.setEmpname(rs.getString("empinitialname"));
                b.setItemid(rs.getInt("itemid"));
                b.setItem(rs.getString("item"));
                b.setQty(rs.getInt("qty"));
                b.setDescription(rs.getString("description"));
                list.add(b);
            }
        }
        ps.close();
        return list;
    }

    @Override
    public String saveItem(BeanInventory b) throws SQLException, Exception {
        Connection connection = JDBC.con_payroll();
        connection.setAutoCommit(false);

        int itemid = 0;
        try {
            String sql_allowance = "INSERT INTO `employee_inventory`(`empid`,`item`,`qty`,`description`,`comcode`,`user`)\n"
                    + "VALUES (?,?,?,?,?,?);";
            PreparedStatement ps_allow = connection.prepareStatement(sql_allowance, Statement.RETURN_GENERATED_KEYS);
            ps_allow.setInt(1, b.getEmpid());
            ps_allow.setString(2, b.getItem());
            ps_allow.setInt(3, b.getQty());
            ps_allow.setString(4, b.getDescription());
            ps_allow.setString(5, BeanSystemLog.getComcode());
            ps_allow.setString(6, BeanSystemLog.getUser());
            ps_allow.executeUpdate();

            ResultSet keys = ps_allow.getGeneratedKeys();
            keys.next();
            itemid = keys.getInt(1);

            b.setItemid(itemid);

            ps_allow.close();

            String saveQuery = "INSERT INTO `log`(`event`,`description`,`comcode`,`user`) VALUES (?,?,?,?);";
            PreparedStatement ps = connection.prepareStatement(saveQuery);
            ps.setString(1, "Save");
            ps.setString(2, "Inventory Item : " + b.getItemid() + " Emp Id : " + b.getEmpid());
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
    public String updateItem(BeanInventory b) throws SQLException, Exception {
        Connection connection = JDBC.con_payroll();
        connection.setAutoCommit(false);

        try {
            String sql_allowance = "UPDATE `employee_inventory` SET `item`=?,`qty`=?,`description`=?,`user`=? WHERE `itemid`=?;";
            PreparedStatement ps_allow = connection.prepareStatement(sql_allowance, Statement.RETURN_GENERATED_KEYS);
            ps_allow.setString(1, b.getItem());
            ps_allow.setInt(2, b.getQty());
            ps_allow.setString(3, b.getDescription());
            ps_allow.setString(4, BeanSystemLog.getUser());
            ps_allow.setInt(5, b.getItemid());
            ps_allow.executeUpdate();

            String saveQuery = "INSERT INTO `log`(`event`,`description`,`comcode`,`user`) VALUES (?,?,?,?);";
            PreparedStatement ps = connection.prepareStatement(saveQuery);
            ps.setString(1, "Update");
            ps.setString(2, "Inventory Item : " + b.getItemid() + " Emp Id : " + b.getEmpid());
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
    public String deleteItem(int itemid, String item, int empid) throws SQLException, Exception {
        Connection connection = JDBC.con_payroll();
        connection.setAutoCommit(false);
        try {

            String sql_attendance = "DELETE FROM `employee_inventory` WHERE `itemid`=?;";
            PreparedStatement ps_attendance = connection.prepareStatement(sql_attendance);
            ps_attendance.setInt(1, itemid);
            ps_attendance.executeUpdate();
            ps_attendance.close();

            String saveQuery = "INSERT INTO `log`(`event`,`description`,`comcode`,`user`) VALUES (?,?,?,?);";
            PreparedStatement ps_log = connection.prepareStatement(saveQuery);
            ps_log.setString(1, "Delete Inventory");
            ps_log.setString(2, "Inventory Item : " + item + " Emp Id : " + empid);
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

}
