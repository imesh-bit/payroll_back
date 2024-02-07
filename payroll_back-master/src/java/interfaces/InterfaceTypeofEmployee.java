/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dao.BeanTypeofEmployee;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Nimesha
 */
public interface InterfaceTypeofEmployee {

    List<BeanTypeofEmployee> getEmpTypeList(Connection connection) throws Exception;

    String saveType(BeanTypeofEmployee b) throws SQLException, Exception;

    String updateType(BeanTypeofEmployee b) throws SQLException, Exception;
}
