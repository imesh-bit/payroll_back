/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dao.BeanBank;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Nimesha
 */
public interface InterfaceBank {

    List<BeanBank> getBankList(Connection connection) throws Exception;

    String saveBank(BeanBank b) throws SQLException, Exception;

    String updateBank(BeanBank b) throws SQLException, Exception;

    String deleteBank(int bankId, String bank) throws SQLException, Exception;
    
    int getBankId(Connection connection,String swiftCode) throws Exception;
}
