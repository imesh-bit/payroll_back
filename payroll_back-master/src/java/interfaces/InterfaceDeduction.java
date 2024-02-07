/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dao.BeanDeduction;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Nimesha
 */
public interface InterfaceDeduction {

    BeanDeduction getDeductionList(Connection connection) throws Exception;

    String saveDeduction(BeanDeduction b) throws SQLException, Exception;

    String updateDeduction(BeanDeduction b) throws SQLException, Exception;
    
    public boolean checkPayroll(Connection connection, String deductName, String deductCode) throws SQLException, Exception;

}
