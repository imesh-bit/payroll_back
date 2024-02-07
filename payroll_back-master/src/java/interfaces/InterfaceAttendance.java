/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dao.BeanAttendance;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Nimesha
 */
public interface InterfaceAttendance {

    List<BeanAttendance> getAttendancesList(Connection connection, String comCode, String monthCode) throws Exception;

    String saveAttendance(BeanAttendance bean) throws SQLException, Exception;

    String saveAttendanceList(List<BeanAttendance> list) throws SQLException, Exception;

    String updateAttendance(BeanAttendance bean) throws SQLException, Exception;

    String deleteAttendance(int Id, String monthCode, int empId) throws SQLException, Exception;
}
