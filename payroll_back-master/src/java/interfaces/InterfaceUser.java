/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dao.BeanUser;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Nimesha
 */
public interface InterfaceUser {

    List<BeanUser> getUserList(Connection connection) throws Exception;
    
    BeanUser getSelectedUser(Connection connection,int userId) throws Exception;

    String saveUser(BeanUser b) throws SQLException, Exception;

    String updateUser(BeanUser b) throws SQLException, Exception;
    
    Boolean checkForUpdate(Connection connection,int empId)throws SQLException, Exception;
}
