/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dao.BeanCategory;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Nimesha
 */
public interface InterfaceCategory {

    List<BeanCategory> getCategoryList(Connection connection) throws Exception;

    String saveCategory(BeanCategory b) throws SQLException, Exception;

    String updateCategory(BeanCategory b) throws SQLException, Exception;

    String deleteCategory(int categoryid,String wageboard) throws SQLException, Exception;
    
    int getCategoryId(Connection connection, String category)throws SQLException, Exception;

}
