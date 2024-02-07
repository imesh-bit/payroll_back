/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dao.BeanLoan;
import dao.BeanLoanSettlement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Nimesha
 */
public interface InterfaceLoans {

    List<BeanLoan> getLoanDetailList(Connection connection, String comCode, int loanStatus) throws Exception;

    List<BeanLoanSettlement> getSettlementList(Connection connection, int loanId) throws Exception;

    BeanLoan getSelectedLoanData(Connection connection, int loanId) throws Exception;

    String saveLoanData(BeanLoan bean) throws SQLException, Exception;

    String updateLoanData(BeanLoan bean) throws SQLException, Exception;

    String settleLoan(BeanLoan bean) throws SQLException, Exception;
    
     List<BeanLoan> getLoanOutstandingList(Connection connection, String comCode, int empId) throws Exception;

}
