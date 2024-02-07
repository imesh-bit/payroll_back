/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dao.BeanSalaryAdvance;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Nimesha
 */
public interface InterfaceSalaryAdvance {

    List<BeanSalaryAdvance> getAdvanceList(Connection connection, String comCode, String monthCode) throws Exception;

    String saveAdvance(BeanSalaryAdvance bean) throws SQLException, Exception;

    String updateAdvance(BeanSalaryAdvance bean) throws SQLException, Exception;

    String deleteAdvance(BeanSalaryAdvance b) throws SQLException, Exception;
}
