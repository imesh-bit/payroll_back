/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dao.BeanAllowance;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Nimesha
 */
public interface InterfaceAllowance {

    BeanAllowance getAllowanceList(Connection connection) throws Exception;

    String saveAllowance(BeanAllowance b) throws SQLException, Exception;

    String updateAllowance(BeanAllowance b) throws SQLException, Exception;

    boolean checkPayroll(Connection connection ,String allowName,String allowCode)throws SQLException, Exception;

}
