/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dao.BeanDesignation;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Nimesha
 */
public interface InterfaceDesignation {

    List<BeanDesignation> getDesignationList(Connection connection) throws Exception;

    String saveDesignation(BeanDesignation b) throws SQLException, Exception;

    String updateDesignation(BeanDesignation b) throws SQLException, Exception;
    
    String deleteDesignation(int id,String designation) throws SQLException, Exception;
    
    String validateDesignation(Connection connection,String designation) throws SQLException, Exception;
}
