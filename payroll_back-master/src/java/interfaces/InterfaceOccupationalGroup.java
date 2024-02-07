/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dao.BeanOccupationalGroup;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Nimesha
 */
public interface InterfaceOccupationalGroup {

    List<BeanOccupationalGroup> getOccupationalGroupList(Connection connection) throws Exception;

    String saveOccupationalGroup(BeanOccupationalGroup b) throws SQLException, Exception;

    String updateOccupationalGroup(BeanOccupationalGroup b) throws SQLException, Exception;

    String deleteGroup(int groupid, String group) throws SQLException, Exception;
    
    int getOccupationalId(Connection connection,String occupation) throws Exception;
}
