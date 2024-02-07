/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dao.BeanNoncashBenifit;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Nimesha
 */
public interface InterfaceNoncashBenifit {

    BeanNoncashBenifit getNoncashList(Connection connection) throws Exception;

    String saveNoncash(BeanNoncashBenifit b) throws SQLException, Exception;

    String updateNoncash(BeanNoncashBenifit b) throws SQLException, Exception;

    boolean checkPayroll(Connection connection, String NoncashName, String noncashCode) throws SQLException, Exception;
}
