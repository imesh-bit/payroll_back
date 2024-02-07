/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dao.BeanEmployee;
import dao.BeanEmployeeBank;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Nimesha
 */
public interface InterfaceEmployeeProfile {

    String getEpfRegNo(Connection connection, String comCode) throws Exception;

    int getNextEpfNo(Connection connection, String comCode) throws Exception;

    boolean validateNIC(Connection connection, String ComCode, String NIC) throws Exception;

    boolean validateEPF(Connection connection, String ComCode, int EmpCode) throws Exception;

    String saveEmployeeProfile(BeanEmployee employeeDetails) throws SQLException, Exception;

    String saveEmpDataExcel(List<BeanEmployee> employeeDetails) throws SQLException, Exception;

    String updateEmployeeProfile(BeanEmployee employeeDetails) throws SQLException, Exception;

    BeanEmployeeBank getEmployeeBankDetails(Connection connection, int empId, String comCode);

    BeanEmployee getSelectedEmployeeProfile(Connection connection, int empId, String comCode) throws Exception;

    List<BeanEmployee> getEmployeeList(Connection connection, String comCode) throws Exception;

    List<BeanEmployee> getActiveEmployeeList(Connection connection, String comCode) throws Exception;

    BigDecimal getConsolidatedSalry(Connection connection, int empId, String comCode) throws Exception;

    List<BeanEmployee> getTerminateEmployeeList(Connection connection) throws Exception;

    String terminateEmployee(int empId, String terminateNote) throws SQLException, Exception;
}
