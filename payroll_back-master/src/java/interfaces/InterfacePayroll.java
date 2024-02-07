/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dao.BeanPayroll;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Nimesha
 */
public interface InterfacePayroll {

    List<BeanPayroll> getEmployeeList(Connection connection) throws Exception;

    BeanPayroll getSelectedEmployee(Connection connection, int empid) throws Exception;

    String savePayroll(BeanPayroll b) throws SQLException, Exception;
}
