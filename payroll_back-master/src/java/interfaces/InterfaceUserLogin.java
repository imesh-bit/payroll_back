/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dao.BeanLogin;
import dao.BeanUsergroup;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Nimesha
 */
public interface InterfaceUserLogin {

    BeanLogin isValidUser(Connection connection, String userName, String password) throws Exception;

    BeanUsergroup getUserPermissions(Connection connection, String userGroup) throws SQLException;

    String saveLoginDetails(Connection connection) throws SQLException, Exception;

}
