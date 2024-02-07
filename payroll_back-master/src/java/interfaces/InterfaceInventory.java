/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dao.BeanInventory;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Nimesha
 */
public interface InterfaceInventory {
    
     List<BeanInventory> getInventoryList(Connection connection,int empid) throws Exception;

    String saveItem(BeanInventory b) throws SQLException, Exception;

    String updateItem(BeanInventory b) throws SQLException, Exception;
    
    String deleteItem(int id,String name,int empid) throws SQLException, Exception;
}
