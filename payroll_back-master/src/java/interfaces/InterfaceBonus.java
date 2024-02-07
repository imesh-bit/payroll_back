/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dao.BeanBonus;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Nimesha
 */
public interface InterfaceBonus {

    List<BeanBonus> getBonustList(Connection connection, String comCode, String monthCode) throws Exception;

    String saveBonus(BeanBonus bean) throws SQLException, Exception;

    String updateBonus(BeanBonus bean) throws SQLException, Exception;

    String deleteBonus(BeanBonus b) throws SQLException, Exception;
}
