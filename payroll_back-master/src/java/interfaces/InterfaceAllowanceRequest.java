/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dao.BeanAllowanceRequest;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Nimesha
 */
public interface InterfaceAllowanceRequest {

    List<BeanAllowanceRequest> getAllowanceRequestList(Connection connection, String comCode, String monthCode,String reqType) throws Exception;

    String saveAllowanceRequest(BeanAllowanceRequest bean) throws SQLException, Exception;

    String updateAllowanceRequest(BeanAllowanceRequest bean) throws SQLException, Exception;

    String deleteAllowanceRequest(BeanAllowanceRequest b) throws SQLException, Exception;
}
