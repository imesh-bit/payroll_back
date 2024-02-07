/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dao.BeanDivision;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Nimesha
 */
public interface InterfaceDivision {

    List<BeanDivision> getDivisionList(Connection connection) throws Exception;

    String saveDivision(BeanDivision b) throws SQLException, Exception;

    String updateDivision(BeanDivision b) throws SQLException, Exception;

    String deleteDivision(int divisionId, String division) throws SQLException, Exception;

    String validateDivision(Connection connection, String division) throws SQLException, Exception;
}
