/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dao.BeanUsergroup;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Nimesha
 */
public interface InterfaceUserGroup {

    BeanUsergroup getSelectedUserGroup(Connection connection, String userGroup) throws SQLException;

    List<BeanUsergroup> getUserGroupList(Connection connection) throws SQLException;

    String saveUserGroup(BeanUsergroup b) throws SQLException, Exception;

    String updateUserGroup(BeanUsergroup b) throws SQLException, Exception;
    
    Boolean checkExistingUsergroup(Connection connection,String UserGroup) throws Exception;

}
