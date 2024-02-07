/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dao.BeanSalaryMonth;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Nimesha
 */
public interface InterfaceSalaryMonth {

    String getActiveSalaryMonth(Connection connection) throws Exception;

    List<BeanSalaryMonth> getMonthList(Connection connection) throws Exception;
    
    BeanSalaryMonth getSelectedMonthStatus(Connection connection,String SalaryMonth)throws Exception;

    String saveMonthDetails(BeanSalaryMonth b) throws SQLException, Exception;

    String updateMonthDetails(BeanSalaryMonth b) throws SQLException, Exception;
    
    Boolean checkCreateMonth(Connection connection,String SalaryMonth) throws Exception;
}
